package com.mooc.ydq.core;

//import com.sun.tools.doclets.formats.html.EnumConstantWriterImpl;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

//功能：类扫描器
public class ClassScanner {
    //传入的参数是包名---> 进行类的加载
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {

        List<Class<?>> classList = new ArrayList<>();

        //将包名转化为文件路径
        String path = packageName.replace(".", "/");

        //使用类加载器--->通过路径来加载文件
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //遍历类加载器的url方法，返回值是一个可遍历的url资源
        Enumeration<URL>  resources = classLoader.getResources(path);

        while(resources.hasMoreElements()){
            URL resource = resources.nextElement();
            //如果resource的类型是jar包类型,则获取jar包的绝对路径
            if(resource.getProtocol().contains("jar")){
                //需要强转为jar型
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();

                String jarFilePath = jarURLConnection.getJarFile().getName();

                classList.addAll(getClassesFromJar(jarFilePath, path));
            }else{
                //todo
            }

        }
        return classList;

    }

    //功能：通过jar包的路径来获取到jar包下全部的类
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {

        //初始化容器来存储类
        List<Class<?>> classes = new ArrayList<>();

        //将jar包路径转化为jarFile实例
        JarFile jarFile = new JarFile(jarFilePath);

        //进行遍历
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements()){

            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();//例如com/mooc/ydq/test/Test.class
            //目标是取出路径的开头与我们传入的path参数相同的jar文件,并且结尾一定是class,这样即可拿到对应的jar包了
            if(entryName.startsWith(path) && entryName.endsWith(".class")){
                //将/换成.  将.class后缀去掉
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);

                //通过class.forName方法将类加载到jvm中
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
