package etu2032.framework.modelview;

import java.util.*;

public class ModelView {
    
    String view;
    HashMap<String , Object> data;
    HashMap<String , Object> sessions = new HashMap<String, Object>();

    boolean json = false;
    boolean sessionInvalidate = false;

    List<String> removeSession = new ArrayList<String>();

    public ModelView(){
        this.setData();
    }
    public ModelView( String view ){
        this.setView(view);
        this.setData();
    }

    public boolean invalidate(){
        return this.sessionInvalidate;      
    }

    public void invalidateSession( boolean inv ){
        this.sessionInvalidate = inv;
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
    
    public void removeSessions( String ...sessions ){
        for(String session : sessions){
            this.getRemoveSession().add(session);
        }
    }

    public void setRemoveSession( List<String> session ){
        this.removeSession = session;
    }

    public List<String> getRemoveSession() {
        return this.removeSession;
    }

}
