package etu2032.packages;

import etu2032.framework.modelview.ModelView;

import etu2032.framework.annotation.Url;
import java.util.Vector;

public class Employe{

	String name;

	public Employe(){}
	public Employe(String name){
		this.setName(name);
	}

	public void setName(String n){
		this.name = n;
	}
	public String getName(){
		return this.name;
	}

	@Url( url="/emp-index" )
	public ModelView index(){
		Vector<Employe> emps = new Vector<Employe>();
		emps.add( new Employe("Sarobidy") );
		emps.add( new Employe("Rodolphe") );
		emps.add( new Employe("Fanilo") );
		emps.add( new Employe("Rota") );
		ModelView returns = new ModelView("employe.jsp");
		returns.addItem("emp-list" , emps); 
		return returns;
	}

}