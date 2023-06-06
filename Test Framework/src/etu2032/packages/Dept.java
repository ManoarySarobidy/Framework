package etu2032.packages;

import etu2032.framework.annotation.Scope;
import etu2032.framework.modelview.ModelView;
import etu2032.framework.utility.FileUpload;
import etu2032.framework.annotation.Url;
import etu2032.framework.annotation.RequestParameter;
import java.util.Vector;
import java.sql.Date;

public class Dept{
	String dept;

	public Dept(){}

	public void setDept(String name){
		this.dept = name;
	}
	public String getDept(){
		return this.dept;
	}

	@Url( url="/insert-dept" )
	public ModelView insertDept(){
		ModelView view = new ModelView("DeptList.jsp");
		view.addItem("employe" , this);
		return view;
	}
}