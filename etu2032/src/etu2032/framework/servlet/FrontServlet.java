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
import etu2032.framework.utility.FileUpload;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 *
 * @author sarobidy
 */
@MultipartConfig
public class FrontServlet extends HttpServlet {
    
    String MODEL_PATH;
    int BYTE_SIZE = 8192;
    HashMap<String, Mapping> mappingUrl;
    PrintWriter out;

    @SuppressWarnings("unchecked")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("text/plain;charset=UTF-8");
        this.out = response.getWriter();
        String url = request.getRequestURI().trim();
        url = url.substring(request.getContextPath().length()).trim();
        this.displayUrls(url);
        try{
            Mapping urls = this.getMappingUrl().get(url);
            Class<?> tr = Class.forName(urls.getClassName());
            Field[] fields = tr.getDeclaredFields();
            Method[] methods = tr.getDeclaredMethods();
            String methodName = urls.getMethod();
            Method willBeinvoked = null;
            Object[] params = null;
            List<String> att = Collections.list(request.getParameterNames());
            for( Method m : methods){
                boolean isPresent = m.isAnnotationPresent( Url.class ) && ( ((Url)m.getAnnotation(Url.class)).url().equals(url) );
                if( m.getName().equals(methodName) && isPresent){
                    willBeinvoked = m;
                    break;
                }
            }
            if( willBeinvoked != null ){
                Parameter[] parameters = willBeinvoked.getParameters();
                params = ( parameters.length == 0 ) ? null : new Object[parameters.length];
                int size = parameters.length;
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
            Object object = tr.getConstructor().newInstance();
            for( Field f : fields ){
                if( this.contains(att ,  f.getName() ) ){
                    Method m = tr.getMethod( ClassUtility.getSetter( f ) , f.getType() );
                    Object o = request.getParameter(f.getName());
                    o = ClassUtility.cast( o , f.getType() );
                    m.invoke( object , o );
                }
            }

            try{
                Collection<Part> files = request.getParts();
                for( Field f : fields ){
                    if(f.getType() == etu2032.framework.utility.FileUpload.class){
                        Method m = tr.getMethod( ClassUtility.getSetter( f ) , f.getType() );
                        Object o = this.fileTraitement(files, f);
                        m.invoke(object , o);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Method method = willBeinvoked;
            Object res =  method.invoke(object,  params);

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
    }

    public void displayUrls( String url ){
        this.out.println("===== All Availables URL : ===== ");
        this.out.println("===> Main Url ===> " + url);
        for( Map.Entry<String , Mapping> sets : this.getMappingUrl().entrySet() ){
           out.println("(url ==>'" + sets.getKey() + "') ===>('" + (sets.getValue()).getClassName()+"/"+(sets.getValue()).getMethod() +"')");
        }
    }

    private FileUpload fileTraitement( Collection<Part> files, Field field){
        FileUpload file = new FileUpload();
        String name = field.getName();
        boolean exists = false;
        String filename = null;
        Part filepart = null;
        for( Part part : files ){
            if( part.getName().equals(name) ){
                filepart = part;
                exists = true;
                break;
            }
        }
        try(InputStream io = filepart.getInputStream()){
            ByteArrayOutputStream buffers = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int)filepart.getSize()];
            int read;
            while( ( read = io.read( buffer , 0 , buffer.length )) != -1 ){
                buffers.write( buffer , 0, read );
            }
            file.setName( this.getFileName(filepart) );
            file.setBytes( buffers.toByteArray() );
            return file;
        }catch (Exception e) {
            e.printStackTrace(this.out);
            return null;
        }
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        for (String partStr : parts) {
            if (partStr.trim().startsWith("filename"))
                return partStr.substring(partStr.indexOf('=') + 1).trim().replace("\"", "");
        }
        return null;
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

}
