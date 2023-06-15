
## How To use It :
- You need to import the packages :
    - etu2032.framework.modelview : 
  It contains the ModelView class package
    - etu2032.framework.annotation  

- All of your functions except Getter/Setter/Constructor must return a ModelView Class that is already in the framework
- Also your function must be annoted with the @Url annotation in the etu2032.framework.annotation
- It will map it to a link that your browser and Front servlet can access
    - Example :
        ```Java
            @Url( name = "/your_link" )
            public ModelView function_name(){}
        ```

- You can set the view in which you want to be redirected by setting it in the ModelView that you will return
    - Example :
        ```Java
            ...  
                ModelView mv = new ModelView("The_path_to_your_jsp_file.jsp");  
            ...
        ```

- You can also add Data to it by using the addItem function in the model view
    - Example :
        ```Java
            ...
                ModelView mv = new ModelView("The_path_to_your_jsp_file.jsp");
                mv.addItem("A_name_for_your_data" , your_data);
                return mv;
            ...
        ```

<!-- - You can also pass a parameter in the function but ** You must ** add the @RequestParameter() annotation from the etu2032.framework.annotation  to it -->
- You can also pass a parameter in the function 
    - Example :
        ```Java
            @Url( name = "/your_link")
            public ModelView function_name( Integer id )
        ```
    <!-- - Example :
        ```Java
            @Url( name = "/your_link")
            public ModelView function_name( @RequestParameter( name = "Name_of_the_parameter_you_have_send" ) Integer id )
     -->    ```

## Some Constraints
1. Don't use a primitive like int,double,bool...,short but Use Class Instead like Integer , Boolean, Double, ... for the class field and the function parameter
2. When Sending a Form data make sure that the class field name is the same as the name that you put in the input of the form
   -   Example :
        - In the class
            ```Java
                ....
                    Integer id;
                ....
            ```
        - In the form field
            ```html
                ...
                    <input type="text" name="id">               
                ...
            ```
3. It doesn't actually support Css so please bear with it
4. When Compiling your class add the "-parameters" option in the javac cmd
    - Example :
        ```bash
            javac -parameters ...
        ```
## Some Helps
1. When Using not primitive type , Instanciate them to avoid a null Pointer Exception