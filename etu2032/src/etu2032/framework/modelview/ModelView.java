/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2032.framework.modelview;

import java.util.*;

public class ModelView {
    
    String view;
    HashMap<String , Object> data;
    HashMap<String , Object> sessions = new HashMap<String, Object>();

    boolean json = false;
    
    public ModelView(){
        this.setData();
    }
    public ModelView( String view ){
        this.setView(view);
        this.setData();
    }

    public void addItem( String key , Object value ){
        this.getData().put(key , value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setData(){
        this.data = new HashMap<String , Object>();
    }
    public void setData( HashMap<String , Object> ob ){
        this.data = new HashMap<String , Object>();
    }
    
    public HashMap<String, Object> getData(){
        return this.data;
    }

    public void setSession( HashMap<String, Object> session ){
        this.sessions = session;
    }
    public HashMap<String, Object> getSession(){
        return this.sessions;
    }

    public void addSession( String name , Object value ){
        this.getSession().put( name , value );
    }

    public void setJson( boolean json ){
        this.json = json;
    }

    public boolean isJson(){
        return this.json;
    }
    
}
