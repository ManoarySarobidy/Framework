/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu2032.framework.servlet;

import etu2032.framework.Mapping;
import etu2032.framework.annotation.RequestParameter;
import etu2032.framework.annotation.Url;
import etu2032.framework.modelview.ModelView;
import etu2032.framework.utility.ClassUtility;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sarobidy
 */
public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> mappingUrl;
//    My Changes
    String MODEL_PATH;

    @SuppressWarnings("unchecked")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("text/plain;charset=UTF-8");
        String url = request.getRequestURI().trim();
        url = url.substring(request.getContextPath().length()).trim();
        PrintWriter out = response.getWriter();
        out.println("===== All Availables URL : ===== ");
        out.println("===> Main Url ===> " + url);
        for( Map.Entry<String , Mapping> sets : this.getMappingUrl().entrySet() ){
           out.println("(url ==>'" + sets.getKey() + "') ===>('" + (sets.getValue()).getClassName()+"/"+(sets.getValue()).getMethod() +"')");
        }

        try{
        
            Mapping urls = this.getMappingUrl().get(url);
//            Alaina ny méthode sy ny class
            out.println(urls);
            Class tr = Class.forName(urls.getClassName());

            // Eto no miampy kely fotsiny mila teneniko aloha hoe alaivo ny Field rehetra

            Field[] fields = tr.getDeclaredFields();

            Method[] methods = tr.getDeclaredMethods();
            String methodName = urls.getMethod();
            Method willBeinvoked = null;
            Object[] params = null;
            Enumeration<String> attribs = request.getParameterNames();
            List<String> att = Collections.list(attribs);
            for( Method m : methods){
                boolean isPresent = m.isAnnotationPresent( Url.class ) && ( ((Url)m.getAnnotation(Url.class)).url().equals(url) );
                if( m.getName().equals(methodName) && isPresent){
                    willBeinvoked = m;
                    break;
                }
            }

            if( willBeinvoked != null ){
                Parameter[] parameters = null;
                parameters = willBeinvoked.getParameters();
                params = ( parameters.length == 0 ) ? null : new Object[parameters.length];
                int size = parameters.length;
                out.println("Tafiditra ato");
                // Alaina daholo ny object rehetra
                for( int i = 0 ; i < size ; i++ ){
                    Parameter pa = parameters[i];
                    if( pa.isAnnotationPresent(RequestParameter.class) ){
                        RequestParameter para = pa.getAnnotation(RequestParameter.class);
                        if( this.contains( att , para.name() ) ){
                            Object p = request.getParameter(para.name());
                            p = ClassUtility.cast(pa , String.valueOf(p));
                            params[i] = p;
                        }
                    }
                }
            }
            // Azo ny liste avy any
            Object object = tr.getConstructor().newInstance();

            out.println(Arrays.toString(params));

            for( Field f : fields ){
                if( this.contains(att ,  f.getName() ) ){
                    // Raha misy ilay attributs de Alaivo ny setter mifanaraka aminy
                    Method m = tr.getMethod( ClassUtility.getSetter( f ) , f.getType() );
                    Object o = request.getParameter(f.getName());
                    o = ClassUtility.cast( o , f.getType());
                    m.invoke( object , o );
                }
            }
//            Azo ilay class de alaina le methode
            Method method = willBeinvoked;
//            Azo ilay methode de executena fotsiny
            Object res =  method.invoke(object,  params);

            // Inona moa no atao ato
            // Alaina daholo ny objet an'ilay Classe de asiana hoe raha mitovy amin'ito de tazomy

            if( res instanceof ModelView ){
                ModelView view = (ModelView) res;
                RequestDispatcher r = request.getRequestDispatcher( view.getView() );
                HashMap<String , Object> data = view.getData();
                this.setDatas(request , data );
                r.forward(request , response);
            }
            
        }catch(NullPointerException nu){
                out.println("Désolé cette url n'existe pas ");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace(out);
        } catch(Exception e){
            e.printStackTrace(out);
        }

        // Rehefa azo ilay url de aseho
//        Rehefa aseho de alefa ily izy
        
        // Rehefa azo ilay url de aseho
//        Rehefa aseho de alefa ily izy
        
    }

    private boolean contains( List<String> datas , String data ){
        // Okey mahazo data
        for(String e : datas){
            // String e = datas.nextElement();
            e = e.trim();
            if( e.equalsIgnoreCase(data) ){
                return true;
            }
        }
        return false;
    }

    private void setDatas(HttpServletRequest request , HashMap<String , Object> data) throws Exception{
        for( Map.Entry<String , Object> sets : data.entrySet()){
            request.setAttribute( sets.getKey() , sets.getValue() );
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            String packages = String.valueOf(getInitParameter("packages")); // Avadika dynamique fotsiny ito
            System.out.println(packages);
            this.setMappingUrl();
            List<Class> cs = ClassUtility.getClassFrom(packages);
            for(Class c : cs){
                Method[] methods = c.getDeclaredMethods();
                for( Method m : methods ){
                    if( m.isAnnotationPresent(Url.class) ){
                        Url url = m.getAnnotation(Url.class);
                        if( !url.url().isEmpty() && url.url() != null ){
                           Mapping map = new Mapping( c.getName() , m.getName() );
                           this.getMappingUrl().put(url.url(), map);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HashMap<String, Mapping> getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl() {
        this.mappingUrl = new HashMap<String , Mapping>();
    }
    
    public void setModelPath(String path){
        this.MODEL_PATH = path;
    }
    public String getModelPath(){
        return this.MODEL_PATH;
    }
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
}
