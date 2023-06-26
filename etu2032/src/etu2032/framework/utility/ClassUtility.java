package etu2032.framework.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;

/**
 * This class is an utility class used by the Front Servlet
 * 
 * @author Manoary Sarobidy ETU 002032
 */
public class ClassUtility {

    public static String getOS(){
        String os = System.getProperty("os.name");
        return os;
    }

    public static boolean isWindows(){
        String os = ClassUtility.getOS();
        return os.contains("win") || os.contains("Win");
    }
    public static boolean isMac(){
        String os = ClassUtility.getOS();
        return os.contains("mac") || os.contains("Mac");
    }
    public static boolean isUnix(){
        String os = ClassUtility.getOS();
        return os.contains("nix") || os.contains("nux") || os.contains("aix");
    }

    private static String replaceUrl( String toReplace ){
        toReplace = toReplace.replaceAll("[.]", ( ClassUtility.isWindows() ) ?  "\\\\" : "/" );
        return toReplace;
    }
    
    public static List<Class<?>> getClassFrom(String  packages) throws Exception{
            String path = ClassUtility.replaceUrl(packages);
            URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);
            File packDir = new File(packageUrl.toURI());
            File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
            List<Class<?>> lists = new ArrayList<>();
            for(File f : inside){
                   String c = packages+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
                   lists.add(Class.forName(c));
            }
            return lists;
    }

    public static String getSetter( Field f ){

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
        }catch(Exception e){}
        return object;
    }

    public static FileUpload fileTraitement( Collection<jakarta.servlet.http.Part> files, Field field ){
        FileUpload file = new FileUpload();
        String name = field.getName();
        boolean exists = false;
        String filename = null;
        jakarta.servlet.http.Part filepart = null;
        for (jakarta.servlet.http.Part part : files) {
            if (part.getName().equals(name)) {
                filepart = part;
                exists = true;
                break;
            }
        }
        file = ClassUtility.fillFileUpload(file, filepart);
        return file;
    }

    private static FileUpload fillFileUpload( FileUpload file, jakarta.servlet.http.Part filepart ){
        try (InputStream io = filepart.getInputStream()) {
            ByteArrayOutputStream buffers = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int) filepart.getSize()];
            int read;
            while ((read = io.read(buffer, 0, buffer.length)) != -1) {
                buffers.write(buffer, 0, read);
            }
            file.setName(ClassUtility.getFileName(filepart));
            file.setBytes(buffers.toByteArray());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFileName(jakarta.servlet.http.Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        for (String partStr : parts) {
            if (partStr.trim().startsWith("filename"))
                return partStr.substring(partStr.indexOf('=') + 1).trim().replace("\"", "");
        }
        return null;
    }

}
