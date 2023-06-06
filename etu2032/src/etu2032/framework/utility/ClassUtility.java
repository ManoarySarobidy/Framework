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
import java.text.SimpleDateFormat;

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
    public static List<Class<?>> getClassFrom(String  packages) throws Exception{
            String path = packages.replaceAll("[.]", "/");
            URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);
            File packDir =new File(packageUrl.toURI());
            File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
            List<Class<?>> lists = new ArrayList<>();
            for(File f : inside){
                   String c = packages+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
                   lists.add(Class.forName(c));
            }
            return lists;
    }
    
    private static Class<?> getClass(String className , String packages ){
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

    public static Object cast( Parameter parameter, String toCast ) throws Exception{
        if( parameter.getType() == Integer.class ){
            return Integer.parseInt(toCast);
        }else if( parameter.getType() == Double.class ){
            return Double.parseDouble(toCast);
        }else if( parameter.getType() == Boolean.class ){
            return Boolean.parseBoolean(toCast);
        }
        try{
            return ClassUtility.castDate(toCast, parameter.getType());
        }catch(Exception e){
        }
        return toCast;
    }

    private static Object castDate( String date , Class<?> field) throws Exception{
        if( field == java.util.Date.class ){
            java.util.Date v = new SimpleDateFormat("YYYY-MM-DD").parse(date);
            return v;

        }else if( field == java.sql.Date.class ){
            return java.sql.Date.valueOf(date);
        }
        throw new Exception("Not a date");
    }

    public static Object cast( Object object , Class<?> type ) throws Exception{
        if( type == Integer.class ){
            return Integer.valueOf(String.valueOf(object));
        }else if( type == Double.class){
            return Double.valueOf(String.valueOf(object));
        }else if( type == Boolean.class){
            return Boolean.valueOf(String.valueOf(object));
        }
        try{
            return ClassUtility.castDate( String.valueOf(object) , type );
            // if( e == null ){
            //     throw new Exception("Date");
            // }
        }catch(Exception e){

        }
        return object;
    }

}
