package etu2032.framework;

/**
 * This class is used by the Front Servlet
 * to map method or function with the url annotation
 * see also the {@link etu2032.framework.annotation.Url} Url annotation 
 * @author Manoary Sarobidy
 */
public class Mapping {
    String className;
    String method;
    
    public Mapping(){
        
    }
    
    public Mapping(String  name , String method){
        this.setClassName(name);
        this.setMethod(method);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
}
