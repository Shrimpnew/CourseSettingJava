package org.fatmansoft.teach.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassUtil {
    /**
    * @Description: 通过类泛型获取class
    * @Date: 2022-04-04 21:46
    * @Param clazz:
    * @Param index:
    * @return: java.lang.Class
    **/
    public static Class getGenericClass(Class clazz,Integer index){
        if(clazz == null)return null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if(actualTypeArguments.length > index)return (Class) actualTypeArguments[index];
        }
        return null;
    }


    /**
    * @Description: 通过接口泛型获取class
    * @Date: 2022-04-05 18:18
    * @Param clazz:
    * @Param index:
    * @return: java.lang.Class
    **/
    public static Class getGenericInterface(Class clazz,Integer index){
        if(clazz == null)return null;
        for (Type genericInterface : clazz.getGenericInterfaces()) {
            if(genericInterface instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if(actualTypeArguments.length > index)return (Class) actualTypeArguments[index];
            }
        }
        return null;
    }

    /**
    * @Description: 扫描给定包下的所有类
    * @Date: 2022-04-04 21:46
    * @Param packageName:
    * @return: java.util.Set<java.lang.Class<?>>
    **/
    public static Set<Class<?>> scanPackage(String packageName){
        Set<Class<?>> classSet = new LinkedHashSet<>();
        String packageDirName = packageName.replace('.','/');
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if(protocol.equals("file")){
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    findClassByFile(classSet,packageName,filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }


    /**
    * @Description: 递归获取目录下的类
    * @Date: 2022-04-04 21:47
    * @Param classSet:
    * @Param packageName:
    * @Param filePath:
    * @return: void
    **/
    private static void findClassByFile(Set<Class<?>> classSet,String packageName,String filePath){
        File dir = new File(filePath);
        if(!dir.exists() || !dir.isDirectory()){
            System.out.println("No files!");
            return;
        }
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(".class");
            }
        });
        if(files == null) return;
        for(File file:files){
            if(file.isDirectory()){
                findClassByFile(classSet,packageName + "." + file.getName(),file.getAbsolutePath());
            }
            else{
                String className = file.getName().substring(0,file.getName().length() - 6);
                try {
                    classSet.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
