## Instructions for how to setup the frameworks 
1. First make sure that you have put the <b> Jar </b> path to your system CLASSPATH variable
  - How to do it :
    - For Windows users:
        - Press the key with Windows Logo
        - Search for "Edit the system environment variable"
        - Then click on "Environment variables" button
        - In the System variables , check if there is already a CLASSPATH variable
          - if it exist , add the path to the framework at the end of it
            - Ex : ;Some;Path;Before;Framework_Jar_Path
          
          - If it doesn't exist then ADD a new CLASSPATH variables
            - Make sure that you write a dot as the first path then add your path separated by semi-colons
    
    - For Linux Users :
      - First, open a terminal 
      - Check which terminal you use by using : 
        ``` Bash
          echo $0 
        ```
        - If you are using szh , then use the command :
            ```Bash 
              sudo nano ~/.zshrc 
            ```
        - If you are using bash, then use the command :
        ```Bash 
          sudo nano ~/.bashrc 
        ```
      - After opening one of the file
      - Append to the end of the file the following code :
        ```Bash
          CLASSPATH=$CLASSPATH:Path_to_the_jar_framework 
        ```
      ```Bash 
          export CLASSPATH 
        ```
      - Then quit the nano editor by pressing 
      ```Bash
          CTRL + X 
        ```
      - Then :
          - If you are in zsh :
            ```Bash 
              source ~/.zshrc 
            ```
          - If You are in Bash :
            ```Bash 
              source ~/.basjrc 
            ```

2. Import the framework to your web-app project by placing it in the WEB-INF/lib/ folder of your web app root directory
3. All classes must be placed in one package
      - Like If there is 5 model classes : They must be under the same package
      - Like "com.mymodels"
    - The package must be writed in the web.xml file
    - In the web.xml you must include the front servlet class as the main servlet which is : <b> etu2032.framework.servlet.FrontServlet     </b> and set to init parameter the name of your packages with the name "packages"
      - Example :
            ```xml
                <servlet>
                    <servlet-name>TheNameYouLike</servlet-name>
                    <servlet-class>etu2032.framework.servlet.FrontServlet</servlet-class>
                    <init-param>  
                        <param-name>packages</param-name>
                        <param-value>YourPackageName</param-value>
                    </init-param>
                </servlet>
            ```
    - Then send all links to the front servlet by mapping the servlet to "/"

4. You need Java 8 and later and Use the servlet-api.jar