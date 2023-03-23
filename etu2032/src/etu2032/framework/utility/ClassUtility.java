/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2032.framework.utility;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarobidy
 */
public class ClassUtility {
    
//    public static Set<Class> getClassFrom(String  packages){
//          InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(packages.replaceAll("[.]","/"));
//          BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//          return reader.lines().filter(line->line.endsWith(".class")).map(line->getClass(line ,packages)).collect(Collectors.toSet());
//    }
    public static List<Class> getClassFrom(String  packages) throws Exception{
            String path = packages.replaceAll("[.]", "/");
            URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);
            File packDir =new File(packageUrl.toURI());
            File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
            List<Class> lists = new ArrayList<>();
            for(File f : inside){
                   String c = packages+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
                   lists.add(Class.forName(c));
            }
            return lists;
    }
    
    private static Class getClass(String className , String packages ){
        try{
            return Class.forName(packages+"."+className.substring(0,className.lastIndexOf('.')));
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
