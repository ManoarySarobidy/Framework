/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu2032.framework.servlet;

import etu2032.framework.Mapping;
import etu2032.framework.annotation.Url;
import etu2032.framework.utility.ClassUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sarobidy
 */
public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> mappingUrl;
//    My Changes
    String MODEL_PATH;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String url = request.getRequestURI();
        url = url.substring(request.getContextPath().length());
        PrintWriter out = response.getWriter();
        out.println("===== All Availables URL : ===== ");
        out.println("===> Main Url ===> " + url);
        for( Map.Entry<String , Mapping> sets : this.getMappingUrl().entrySet() ){
           out.println("(url ==>'" + sets.getKey() + "') ===>('" + (sets.getValue()).getClassName()+"/"+(sets.getValue()).getMethod() +"')");
        }
        try{
            Mapping urls = this.getMappingUrl().get(url);
//            Alaina ny méthode sy ny class
            Class tr = Class.forName(urls.getClassName());
//            Azo ilay class de alaina le methode
            Method method = tr.getDeclaredMethod(urls.getMethod(), (Class[]) null);
//            Azo ilay methode de executena fotsiny
            method.invoke(tr.getConstructor().newInstance(), (Object[]) null);
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

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            String packages = "etu2032.test"; // Avadika dynamique fotsiny ito
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
