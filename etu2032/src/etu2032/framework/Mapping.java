/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2032.framework;

/**
 *
 * @author sarobidy
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
