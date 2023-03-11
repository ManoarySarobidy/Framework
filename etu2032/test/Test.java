
package etu2032.test;

import etu2032.framework.annotation.Url;

/**
 *
 * @author sarobidy
 */
public class Test {
    
    public void select(){
        
    }
    public void insert(){
        
    }
    @Url(url = "/update-emp" , method = "GET")
    public void update(){
        
    }
    @Url( url = "/" , method = "GET")
    public void index(){
        System.out.println("Ato lc baiby eh");
    }
    
}
