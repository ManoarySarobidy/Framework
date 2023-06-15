package etu2032.packages;

import etu2032.framework.annotation.*;
import etu2032.framework.modelview.ModelView;

public class Login{

	String logins;
	String password;

	public Login(){

	}

	public void setLogins( String login ){
		this.logins = login;
	}
	public void setPassword( String password ){
		this.password = password;
	}

	public String getLogins(){
		return this.logins;
	}
	public String getPassword(){
		return this.password;
	}

	@Url( url="/login" )
	public ModelView home(){
		ModelView view = new ModelView("login.jsp");
		return view;
	}

	@Url( url = "/log")
	public ModelView login(){
		ModelView view = new ModelView();
		String v = "login.jsp";
		if( this.getLogins().equals("Manoary Sarobidy") && this.getPassword().equals("admin") ){
			v = "index.jsp";
			view.addSession( "isConnected" , true );
			view.addSession( "profil" , "admin" );
		}else if( this.getLogins().equals("Manoary Sarobidy") && this.getPassword().equals("Admin") ){
			v = "index.jsp";
			view.addSession( "isConnected" , true );
			view.addSession( "profil" , "super-admin" );
		}
		view.setView(v);
		return view;
	}

}