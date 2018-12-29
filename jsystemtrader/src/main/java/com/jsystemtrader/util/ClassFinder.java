package com.jsystemtrader.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;


public class ClassFinder {

    /**
     * Searches the classpath (including JAR files) to find classes that extend
     * the specified superclass. The intent is to be able to implement new
     * strategy classes as "plug-and-play" units of JSystemTrader. That is,
     * JSystemTrader will know how to run the trading strategy as long as this
     * strategy is implemented in a class that extends the base Strategy class.
     */
    public List<Class> getClasses(String packageName, String superClassName) throws URISyntaxException, IOException,
            ClassNotFoundException {

        String packagePath = packageName.replace('.', '/');
        URL[] classpath = ( (URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
        List<Class> classes = new ArrayList<Class> ();

        for (URL url : classpath) {
            List<String> classNames = new ArrayList<String> ();

            ClassLoader classLoader = new URLClassLoader(new URL[] {url});
            URI uri = url.toURI();
            File file = new File(uri);

            if (file.getPath().endsWith(".jar")) {
                if (file.exists()) {
                    JarFile jarFile = new JarFile(file);
                    for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                        String entryName = (entries.nextElement()).getName();
                        if (entryName.matches(packagePath + "/\\w*\\.class")) { // get only class files in package dir
                            String className = entryName.replace('/', '.').substring(0, entryName.lastIndexOf('.'));
                            classNames.add(className);
                        }
                    }
                }
            } else { // directory
                File packageDirectory = new File(file.getPath() + "/" + packagePath);
                if (packageDirectory.exists()) {
                    for (File f : packageDirectory.listFiles()) {
                        if (f.getPath().endsWith(".class")) {
                            String className = packageName + "." + f.getName().substring(0, f.getName().lastIndexOf('.'));
                            classNames.add(className);
                        }
                    }
                }
            }

            // make sure the strategy extends the base Strategy class
            for (String className : classNames) {
                Class clazz = classLoader.loadClass(className);
                if (clazz.getSuperclass().getName().equals(superClassName)) {
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }
}
