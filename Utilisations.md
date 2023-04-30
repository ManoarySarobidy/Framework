
How To use It :
    - All classes must be placed in one package
      - Like If there is 5 model classes : They must be under the same package
      - Like "com.mymodels"
    - The package must be writed in the web.xml file
    - In the web.xml you must include the front servlet class as the main servlet which is : <b> etu2032.framework.servlet.FrontServlet     </b> and set to init parameter the name of your packages with the name "packages"
      - Example :
            <code>
                <servlet>
                    <servlet-name>TheNameYouLike</servlet-name>
                    <servlet-class>etu2032.framework.servlet.FrontServlet</>
                    <init-param>
                        <param-name>packages</param-name>
                        <param-value>YourPackageName</param-value>
                    </init-param>
                </servlet>
            </code>
    - Then send all links to the front servlet by mapping the servlet to "/"
    - All of your functions except Getter/Setter/Constructor must return a ModelView Class that is already in the framework
    - You need to import etu2032.framework.modelview packages
    - 
    - Example :
        <code>
            public ModelView function_name(){}
        </code>

    - You can set the view in which you want to be redirected by setting it in the ModelViw that you will return
    - Example :
        <code>
            ...
                ModelView mv = new ModelView("The_path_to_your_jsp_file.jsp");
            ...
        </code>
    - You can also add Data to it by using the addItem function in the model view
    - Example :
        <code>
            ...
                ModelView mv = new ModelView("The_path_to_your_jsp_file.jsp");
                mv.addItem("A_name_for_your_data" , your_data);
            ...
        </code> 
    