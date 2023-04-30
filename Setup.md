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
      - <code> echo $0 </code>
        - If you are using szh , then use the command :
        - <code> sudo nano ~/.zshrc </code>
        - If you are using bash, then use the command :
        - <code> sudo nano ~/.bashrc </code>
      - After opening one of the file
      - Append to the end of the file the following code :
      - <code> CLASSPATH=$CLASSPATH:Path_to_the_jar_framework </code>
      - <code> export CLASSPATH </code>
      - Then quit the nano editor by pressing 
      - <code> CTRL + X </code>
      - Then :
          - If you are in zsh :
            - <code> source ~/.zshrc </code>
          - If You are in Bash :
            - <code> source ~/.basjrc </code>

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
                    <servlet-class>etu2032.framework.servlet.FrontServlet</>
                    <init-param>
                        <param-name>packages</param-name>
                        <param-value>YourPackageName</param-value>
                    </init-param>
                </servlet>
            ```
    - Then send all links to the front servlet by mapping the servlet to "/"