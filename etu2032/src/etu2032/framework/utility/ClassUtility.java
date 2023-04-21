/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2032.framework.utility;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
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

    public static String getSetter( Field f ){
        // Inona ny ataoko ato
        // Maka Setter
        String prefix = ( f.getType() == Boolean.TYPE ) ? "is" : "set";
        String fieldName = f.getName();
        fieldName = fieldName.substring(0,1).toUpperCase().concat(fieldName.substring(1));
        String methodName = prefix + fieldName;
        return methodName;
    }

    public static Object cast( Parameter parameter, String toCast ){
        if( parameter.getType() == int.class ){
            return Integer.parseInt(toCast);
        }else if( parameter.getType() == double.class ){
            return Double.parseDouble(toCast);
        }else if( parameter.getType() == boolean.class ){
            return Boolean.parseBoolean(toCast);
        }
        return toCast;
    }
    public static Object cast( Object object ){
        if( object.getClass() == Integer.TYPE ){
            return Integer.parseInt(String.valueOf(object));
        }else if( object.getClass() == Double.TYPE){
            return Double.parseDouble(String.valueOf(object));
        }else if( object.getClass() == Boolean.TYPE){
            return Boolean.parseBoolean(String.valueOf(object));
        }
        return object;
    }

}
