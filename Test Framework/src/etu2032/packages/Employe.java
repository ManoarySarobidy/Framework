package etu2032.packages;

import etu2032.framework.modelview.ModelView;

import etu2032.framework.annotation.Url;

public class Employe{

	@Url( url="/emp-index" )
	public ModelView index(){
		return new ModelView("employe.jsp");
	}

}