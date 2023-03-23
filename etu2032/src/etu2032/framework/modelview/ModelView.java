/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2032.framework.modelview;

public class ModelView {
    
    String view;
    
    public ModelView(){
        
    }
    public ModelView( String view ){
           this.setView(view);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
    
}
