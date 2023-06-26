package etu2032.framework.servlet;

import etu2032.framework.Mapping;
import etu2032.framework.annotation.Auth;
import etu2032.framework.annotation.RequestParameter;
import etu2032.framework.annotation.Scope;
import etu2032.framework.annotation.Rest;
import etu2032.framework.annotation.Session;
import etu2032.framework.annotation.Url;
import etu2032.framework.exception.AuthFailedException;
import etu2032.framework.modelview.ModelView;
import etu2032.framework.utility.ClassUtility;
import etu2032.framework.utility.FileUpload;
import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 *
 * @author Manoary Sarobidy
 */

@MultipartConfig

public class FrontServlet extends HttpServlet {

// Attribute declaration

    String MODEL_PATH;
    int BYTE_SIZE = 8192;
    PrintWriter out;
    Gson gson = new Gson();
    String session_name;
    String session_profile;

    String session_attribute;

    HashMap<String, Mapping> mappingUrl;
    HashMap<String, Object> singleton = new HashMap<String, Object>();

// Process Request

    @SuppressWarnings("unchecked")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.out = response.getWriter();
        String url = request.getRequestURI().trim();
        url = url.substring(request.getContextPath().length()).trim();
        try {
            Mapping urls = this.getMappingUrl().get(url);
            Class<?> tr = Class.forName( urls.getClassName() );
            Field[] fields = tr.getDeclaredFields();
            Method[] methods = tr.getDeclaredMethods();
            String methodName = urls.getMethod();
            Method willBeinvoked = null;
            Object[] params = null;

            Object object = this.getInstance(urls.getClassName());
            List<String> att = Collections.list(request.getParameterNames());

            for (Method m : methods) {
                boolean isPresent = m.isAnnotationPresent(Url.class) && (((Url) m.getAnnotation(Url.class)).url().equals(url));
                if (m.getName().equals(methodName) && isPresent) {
                    willBeinvoked = m;
                    break;
                }
            }

            // For setting function parameters
            if( willBeinvoked.isAnnotationPresent(Auth.class) ){
                Auth auth = willBeinvoked.getAnnotation(Auth.class);
                Object sessionN = request.getSession().getAttribute(this.getSessionName());
                Object sessionP = request.getSession().getAttribute(this.getSessionProfile());
                if( sessionN == null || (sessionN != null && !auth.user().isEmpty()  && !((String) sessionP).equalsIgnoreCase(auth.user()) ) ){
                    throw new AuthFailedException("Sorry You can't access that url with your privileges : " + sessionP);
                }
            }

            Parameter[] parameters = willBeinvoked.getParameters();
            params = (parameters.length == 0) ? null : new Object[parameters.length];
            int size = parameters.length;
            for (int i = 0; i < size; i++) {
                Parameter pa = parameters[i];
                if (pa.isAnnotationPresent(RequestParameter.class)) {
                    RequestParameter para = pa.getAnnotation(RequestParameter.class);
                    String name = ((pa.getType().isArray()) ? para.name() + "[]" : para.name());
                    if (this.contains(att, name)) {
                        Object p = (pa.getType().isArray()) ? request.getParameterValues(name) : request.getParameter(name);
                        p = ClassUtility.cast(pa, String.valueOf(p));
                        params[i] = p;
                    }
                }
            }

            for (Field f : fields) {
                String name = ((f.getType().isArray()) ? f.getName() + "[]" : f.getName());
                if (this.contains(att, name)) {
                    Method m = tr.getMethod(ClassUtility.getSetter(f), f.getType());
                    Object o = (f.getType().isArray()) ? request.getParameterValues(name) : request.getParameter(name);
                    o = ClassUtility.cast(o, f.getType());
                    if( tr.isAnnotationPresent(Scope.class) && tr.getAnnotation(Scope.class).name().equalsIgnoreCase("singleton") ){
                        Object e = null;
                        m.invoke(object , e);
                    }
                    m.invoke(object, o);
                }
            }

            try {
                Collection<Part> files = request.getParts();
                for (Field f : fields) {
                    if (f.getType() == etu2032.framework.utility.FileUpload.class) {
                        Method m = tr.getMethod(ClassUtility.getSetter(f), f.getType());
                        Object o = this.fileTraitement(files, f);
                        if( tr.isAnnotationPresent(Scope.class) && tr.getAnnotation(Scope.class).name().equalsIgnoreCase("singleton") ){
                            Object e = null;
                            m.invoke( object, e );
                        }
                        m.invoke(object, o);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Method method = willBeinvoked;

            if( method.isAnnotationPresent(Session.class) ){
                String sessionAttribute = "set" + "Sessions";
                ArrayList<String> sessions = Collections.list(request.getSession().getAttributeNames());
                HashMap<String, Object> sessionCopy = new HashMap<String, Object>();
                for (String attribute : sessions) {
                    Object value = request.getSession().getAttribute(attribute);
                    sessionCopy.put( attribute , value );
                }
                Method sessionMethod = object.getClass().getDeclaredMethod(sessionAttribute, HashMap.class);
                sessionMethod.invoke( object, sessionCopy );
            }

            Object res = method.invoke(object, params);

            if( method.isAnnotationPresent(Session.class) ){
                String sessionAttribute = "get" + "Sessions";
                Method sessionMethod = object.getClass().getDeclaredMethod(sessionAttribute);
                HashMap<String, Object> sessions = (HashMap<String, Object>) sessionMethod.invoke( object );
                for ( Map.Entry<String, Object> ob : sessions.entrySet() ) {
                    // Tokony hoe raha navadikako null ilay izy de afaka fafana
                    if( ob.getValue() == null ){
                        request.getSession().removeAttribute( ob.getKey() );
                    }else{
                        request.getSession().setAttribute( ob.getKey() , ob.getValue() );
                    }
                }
            }
            if (res instanceof ModelView) {
                ModelView view = (ModelView) res;
                HashMap<String, Object> data = view.getData();
                HashMap<String, Object> sessions = view.getSession();
                if( !view.isJson() ){
                    RequestDispatcher r = request.getRequestDispatcher(view.getView());
                    this.setDatas(request, data);
                    this.setSessions(request, sessions);
                    r.forward(request, response);
                }else{
                    response.setContentType("application/json");
                    out.println( gson.toJson(data) );
                }
            }else if( method.isAnnotationPresent(Rest.class)  ){
                response.setContentType("application/json");
                out.println( gson.toJson(res) );
            }

        } catch (NullPointerException nu) {
            out.println("Désolé cette url n'existe pas ");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            ex.printStackTrace(out);
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

// Display part

    public void displayUrls(String url) {
        this.out.println("===== All Availables URL : ===== ");
        this.out.println("===> Main Url ===> " + url);
        for (Map.Entry<String, Mapping> sets : this.getMappingUrl().entrySet()) {
            out.println("(url ==>'" + sets.getKey() + "') ===>('" + (sets.getValue()).getClassName() + "/"
                    + (sets.getValue()).getMethod() + "')");
        }
    }

// File traitement part
    private FileUpload fileTraitement(Collection<Part> files, Field field) {
        FileUpload file = new FileUpload();
        String name = field.getName();
        boolean exists = false;
        String filename = null;
        Part filepart = null;
        for (Part part : files) {
            if (part.getName().equals(name)) {
                filepart = part;
                exists = true;
                break;
            }
        }
        try (InputStream io = filepart.getInputStream()) {
            ByteArrayOutputStream buffers = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int) filepart.getSize()];
            int read;
            while ((read = io.read(buffer, 0, buffer.length)) != -1) {
                buffers.write(buffer, 0, read);
            }
            file.setName(this.getFileName(filepart));
            file.setBytes(buffers.toByteArray());
            return file;
        } catch (Exception e) {
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

// Additional function

    private Object getInstance( String className ) throws 
        ClassNotFoundException, InstantiationException,NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        if( this.getSingletons().containsKey( className ) ){
            Object instance = this.getSingletons().get(className);
            if( instance == null ){
                Object newInstance = Class.forName( className ).getConstructor().newInstance();
                this.getSingletons().put( className, newInstance );
                instance = newInstance;
            }
            return instance;
        }
        return Class.forName( className ).getConstructor().newInstance();
    }

    private boolean contains(List<String> datas, String data) {
        for (String e : datas) {
            if (e.trim().equalsIgnoreCase(data)) return true;
        }
        return false;
    }

// init method
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            String packages = String.valueOf(this.getInitParameter("packages"));
            
            String auth_session = String.valueOf( this.getInitParameter("sessionName") );
            String auth_profile = String.valueOf( this.getInitParameter("sessionProfile") );
            String sess_var = String.valueOf( this.getInitParameter("sessionAttribute") );

            this.setSessionName(auth_session);
            this.setSessionProfile(auth_profile);
            this.setSessionAttribute(sess_var);

            this.setMappingUrl();

            List<Class<?>> cs = ClassUtility.getClassFrom(packages);
            for (Class<?> c : cs) {
                Method[] methods = c.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.isAnnotationPresent(Url.class)) {
                        Url url = m.getAnnotation(Url.class);
                        if (!url.url().isEmpty() && url.url() != null) {
                            Mapping map = new Mapping(c.getName(), m.getName());
                            this.getMappingUrl().put(url.url(), map);
                        }
                    }
                }
                if (c.isAnnotationPresent(Scope.class)
                        && (c.getAnnotation(Scope.class)).name().equalsIgnoreCase("singleton")) {
                    this.getSingletons().put(c.getName(),  null );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

// getters and setters

    private void setSessionName(String name){
        this.session_name = name;
    }

    private void setSessionProfile(String name){
        this.session_profile = name;
    }

    private String getSessionName(){
        return this.session_name;
    }

    private String getSessionProfile(){
        return this.session_profile;
    }

    private void setDatas(HttpServletRequest request, HashMap<String, Object> data) throws Exception {
        for (Map.Entry<String, Object> sets : data.entrySet()) {
            request.setAttribute(sets.getKey(), sets.getValue());
        }
    }
    private void setSessions(HttpServletRequest request, HashMap<String, Object> sessions) throws Exception {
        HttpSession session = request.getSession();
        for (Map.Entry<String, Object> sets : sessions.entrySet()) {
            session.setAttribute(sets.getKey(), sets.getValue());
        }
    }

    public HashMap<String, Object> getSingletons() {
        return this.singleton;
    }

    public void setSingletons(HashMap<String, Object> singleton) {
        this.singleton = singleton;
    }

    public HashMap<String, Mapping> getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl() {
        this.mappingUrl = new HashMap<String, Mapping>();
    }

    public void setModelPath(String path) {
        this.MODEL_PATH = path;
    }

    public String getModelPath() {
        return this.MODEL_PATH;
    }

    public void setSessionAttribute( String session ){
        this.session_attribute = session;
    }
    public String getSessionAttribute(){
        return this.session_attribute;
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
