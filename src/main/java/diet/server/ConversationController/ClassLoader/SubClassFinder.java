/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController.ClassLoader;

/**
 *
 * Taken from http://nonstop-rp.blogspot.nl/2010/05/java-reflection-know-subclasses-of.html
 */
    import java.io.File;  
    import java.io.FileFilter;  
    import java.io.IOException;  
    import java.net.JarURLConnection;  
    import java.net.MalformedURLException;  
    import java.net.URL;  
    import java.util.ArrayList;  
    import java.util.Comparator;  
    import java.util.Enumeration;  
    import java.util.HashMap;  
    import java.util.Iterator;  
    import java.util.List;  
    import java.util.Map;  
    import java.util.StringTokenizer;  
    import java.util.TreeMap;  
    import java.util.Vector;  
    import java.util.jar.JarEntry;  
    import java.util.jar.JarFile;  
      
    /** 
     * Will scan and list all the subclasses of a given class or Interface, 
     * Its even search for classes in a jar file too..  
     * <li>Get a list of all class names that exist on the class path</li> 
     *  <li> 
     * Load each class and test to see if</li> 
     * While scanning all the classes it serializes the results,  
     *  
     *  
     * @author rajendra 
     */  
    public class SubClassFinder {  
     private Class<?> classToSearch = null;  
     private Map<URL, String> classpathLocations = new HashMap<URL, String>();  
     private Map<Class<?>, URL> results = new HashMap<Class<?>, URL>();  
     private List<Throwable> errors = new ArrayList<Throwable>();  
      
     public SubClassFinder() {  
       System.out.println("(CA) ");
      refreshLocations();  
     }  
      
     /** 
      * Rescan the classpath, cacheing all possible file locations. 
      */  
     public final void refreshLocations() {  
      synchronized (classpathLocations) {  
           System.out.println("(CB) ");
       classpathLocations = getClasspathLocations();  
      }  
     }  
      
     /** 
      * @param fqcn 
      *            Name of superclass/interface on which to search 
      */  
     public final Vector<Class<?>> findSubclasses(String fqcn) {  
      synchronized (classpathLocations) {  
       synchronized (results) {  
        try {  
         classToSearch = null;  
         errors = new ArrayList<Throwable>();  
         results = new TreeMap<Class<?>, URL>(CLASS_COMPARATOR);  
         // filter malformed FQCN  
         if (fqcn.startsWith(".") || fqcn.endsWith(".")) {  
          return new Vector<Class<?>>();  
         }  
      
         // Determine search class from fqcn  
         try {  
          classToSearch = Class.forName(fqcn);  
         } catch (ClassNotFoundException ex) {  
          errors.add(ex);  
          return new Vector<Class<?>>();  
         }  
      
         return findSubclasses(classToSearch, classpathLocations);  
        } finally {  
        }  
       }  
      }  
     }  
      
     public final List<Throwable> getErrors() {  
      return new ArrayList<Throwable>(errors);  
     }  
      
     /** 
      * The result of the last search is cached in this object, along with the 
      * URL that corresponds to each class returned. This method may be called to 
      * query the cache for the location at which the given class was found. 
      * <code>null</code> will be returned if the given class was not found 
      * during the last search, or if the result cache has been cleared. 
      */  
     public final URL getLocationOf(Class<?> cls) {  
      if (results != null)  
       return results.get(cls);  
      else  
       return null;  
     }  
      
     /** 
      * Determine every URL location defined by the current classpath, and it's 
      * associated package name. 
      */  
     public final Map<URL, String> getClasspathLocations() {  
      Map<URL, String> map = new TreeMap<URL, String>(URL_COMPARATOR);  
      File file = null;  
      
      String pathSep = System.getProperty("path.separator");  
      String classpath = System.getProperty("java.class.path");  
      // System.out.println ("classpath=" + classpath);  
       System.err.println("(CD) ");
      StringTokenizer st = new StringTokenizer(classpath, pathSep);  
      while (st.hasMoreTokens()) {  
      
       String path = st.nextToken();  
       System.err.println("(CE)MAP: "+path);
       file = new File(path);  
       include(null, file, map);  
      }  
      return map;  
     }  
      
     private final static FileFilter DIRECTORIES_ONLY = new FileFilter() {  
      public boolean accept(File f) {  
       if (f.exists() && f.isDirectory())  
        return true;  
       else  
        return false;  
      }  
     };  
      
     private final static Comparator<URL> URL_COMPARATOR = new Comparator<URL>() {  
      public int compare(URL u1, URL u2) {  
       return String.valueOf(u1).compareTo(String.valueOf(u2));  
      }  
     };  
      
     private final static Comparator<Class<?>> CLASS_COMPARATOR = new Comparator<Class<?>>() {  
      public int compare(Class<?> c1, Class<?> c2) {  
       return String.valueOf(c1).compareTo(String.valueOf(c2));  
      }  
     };  
      
     private final void include(String name, File file, Map<URL, String> map) {  
      if (!file.exists())  
       return;  
      if (!file.isDirectory()) {  
       // could be a JAR file  
       includeJar(file, map);  
       return;  
      }  
      
      if (name == null)  
       name = "";  
      else  
       name += ".";  
      
      // add subpackages  
      File[] dirs = file.listFiles(DIRECTORIES_ONLY);  
      for (int i = 0; i < dirs.length; i++) {  
       try {  
        // add the present package  
        map.put(new URL("file://" + dirs[i].getCanonicalPath()), name  
          + dirs[i].getName());  
       } catch (IOException ioe) {  
        return;  
       }  
      
       include(name + dirs[i].getName(), dirs[i], map);  
      }  
     }  
      
     private void includeJar(File file, Map<URL, String> map) {  
      if (file.isDirectory())  
       return;  
      
      URL jarURL = null;  
      JarFile jar = null;  
      try {  
       jarURL = new URL("file:/" + file.getCanonicalPath());  
       jarURL = new URL("jar:" + jarURL.toExternalForm() + "!/");  
       JarURLConnection conn = (JarURLConnection) jarURL.openConnection();  
       jar = conn.getJarFile();  
      } catch (Exception e) {  
       // not a JAR or disk I/O error  
       // either way, just skip  
       return;  
      }  
      
      if (jar == null || jarURL == null)  
       return;  
      
      // include the jar's "default" package (i.e. jar's root)  
      map.put(jarURL, "");  
      
      Enumeration<JarEntry> e = jar.entries();  
      while (e.hasMoreElements()) {  
       JarEntry entry = e.nextElement();  
      
       if (entry.isDirectory()) {  
        if (entry.getName().toUpperCase().equals("META-INF/"))  
         continue;  
      
        try {  
         map.put(new URL(jarURL.toExternalForm() + entry.getName()),  
           packageNameFor(entry));  
        } catch (MalformedURLException murl) {  
         // whacky entry?  
         continue;  
        }  
       }  
      }  
     }  
      
     private static String packageNameFor(JarEntry entry) {  
      if (entry == null)  
       return "";  
      String s = entry.getName();  
      if (s == null)  
       return "";  
      if (s.length() == 0)  
       return s;  
      if (s.startsWith("/"))  
       s = s.substring(1, s.length());  
      if (s.endsWith("/"))  
       s = s.substring(0, s.length() - 1);  
      return s.replace('/', '.');  
     }  
      
     private final Vector<Class<?>> findSubclasses(Class<?> superClass,  
       Map<URL, String> locations) {  
      Vector<Class<?>> v = new Vector<Class<?>>();  
      
      Vector<Class<?>> w = null; // new Vector<Class<?>> ();  
      
      Iterator<URL> it = locations.keySet().iterator();  
      while (it.hasNext()) {  
       URL url = it.next();  
       // System.out.println (url + "-->" + locations.get (url));  
      
       w = findSubclasses(url, locations.get(url), superClass);  
       if (w != null && (w.size() > 0))  
        v.addAll(w);  
      }  
      
      return v;  
     }  
      
     private final Vector<Class<?>> findSubclasses(URL location,  String packageName, Class<?> superClass) {  
       if(!packageName.contains("ConversationController"))return new Vector();
       // System.out.println ("looking in package:" + packageName);  
      // System.out.println ("looking for  class:" + superClass);  
      
      synchronized (results) {  
      
       // hash guarantees unique names...  
       Map<Class<?>, URL> thisResult = new TreeMap<Class<?>, URL>(  
         CLASS_COMPARATOR);  
       Vector<Class<?>> v = new Vector<Class<?>>(); // ...but return a  
       // vector  
      
       // TODO: double-check for null search class  
       String fqcn = classToSearch.getName();  
      
       List<URL> knownLocations = new ArrayList<URL>();  
       knownLocations.add(location);  
       // TODO: add getResourceLocations() to this list  
      
       // iterate matching package locations...  
       for (int loc = 0; loc < knownLocations.size(); loc++) {  
        URL url = knownLocations.get(loc);  
      
        // Get a File object for the package  
        File directory = new File(url.getFile());  
      
        // System.out.println ("\tlooking in " + directory);  
      
        if (directory.exists()) {  
         // Get the list of the files contained in the package  
         String[] files = directory.list();  
         for (int i = 0; i < files.length; i++) {  
          // we are only interested in .class files  
          if (files[i].endsWith(".class")) {  
           // removes the .class extension  
           String classname = files[i].substring(0, files[i]  
             .length() - 6);  
      
           // System.out.println ("\t\tchecking file " +  
           // classname);  
      
           try {  
            System.err.println("PACKAGENAME IS "+packageName);
            Class<?> c = Class.forName(packageName + "."  + classname);  
            if (superClass.isAssignableFrom(c)  
              && !fqcn.equals(packageName + "."  
                + classname)) {  
             thisResult.put(c, url);  
            }  
           } catch (ClassNotFoundException cnfex) {  
           /// errors.add(cnfex); 
               cnfex.printStackTrace();
            // System.err.println(cnfex);  
           } catch (Exception ex) { 
               ex.printStackTrace();
            //errors.add(ex);  
            // System.err.println (ex);  
           }  
          }  
         }  
        } else {  
         try {  
          // It does not work with the filesystem: we must  
          // be in the case of a package contained in a jar file.  
          JarURLConnection conn = (JarURLConnection) url  
            .openConnection();  
          // String starts = conn.getEntryName();  
          JarFile jarFile = conn.getJarFile();  
      
          // System.out.println ("starts=" + starts);  
          // System.out.println ("JarFile=" + jarFile);  
      
          Enumeration<JarEntry> e = jarFile.entries();  
          while (e.hasMoreElements()) {  
           JarEntry entry = e.nextElement();  
           String entryname = entry.getName();  
      
           // System.out.println ("\tconsidering entry: " +  
           // entryname);  
      
           if (!entry.isDirectory()  
             && entryname.endsWith(".class")) {  
            String classname = entryname.substring(0,  
              entryname.length() - 6);  
            if (classname.startsWith("/"))  
             classname = classname.substring(1);  
            classname = classname.replace('/', '.');  
      
            // System.out.println ("\t\ttesting classname: "  
            // + classname);  
      
            try {  
             // TODO: verify this block  
             Class<?> c = Class.forName(classname);  
      
             if (superClass.isAssignableFrom(c)  
               && !fqcn.equals(classname)) {  
              thisResult.put(c, url);  
             }  
            } catch (ClassNotFoundException cnfex) {  
             // that's strange since we're scanning  
             // the same classpath the classloader's  
             // using... oh, well  
             errors.add(cnfex);  
            } catch (NoClassDefFoundError ncdfe) {  
             // dependency problem... class is  
             // unusable anyway, so just ignore it  
             errors.add(ncdfe);  
            } catch (UnsatisfiedLinkError ule) {  
             // another dependency problem... class is  
             // unusable anyway, so just ignore it  
             errors.add(ule);  
            } catch (Exception exception) {  
             // unexpected problem  
             // System.err.println (ex);  
             errors.add(exception);  
            } catch (Error error) {  
             // lots of things could go wrong  
             // that we'll just ignore since  
             // they're so rare...  
             errors.add(error);  
            }  
           }  
          }  
         } catch (IOException ioex) {  
          // System.err.println(ioex);  
          errors.add(ioex);  
         }  
        }  
       } // while  
      
       // System.out.println ("results = " + thisResult);  
      
       results.putAll(thisResult);  
      
       Iterator<Class<?>> it = thisResult.keySet().iterator();  
       while (it.hasNext()) {  
        v.add(it.next());  
       }  
       return v;  
      
      } // synch results  
     }  
      
     public static void main(String[] args) {  
      args = new String[] { "java.lang.Object" };  
      
      SubClassFinder finder = null;  
      Vector<Class<?>> v = null;  
      List<Throwable> errors = null;  
      
      if (args.length == 1) {  
       finder = new SubClassFinder();  
       v = finder.findSubclasses(args[0]);  
       errors = finder.getErrors();  
      } else {  
       System.out  
         .println("Usage: java SubClassFinder <fully.qualified.superclass.name>");  
       return;  
      }  
      
      System.out.println("RESULTS:");  
      if (v != null && v.size() > 0) {  
       for (Class<?> cls : v) {  
        System.out.println(cls  
          + " in "  
          + ((finder != null) ? String.valueOf(finder  
            .getLocationOf(cls)) : "?"));  
       }  
      } else {  
       System.out.println("No subclasses of " + args[0] + " found.");  
      }  
     }  
    }  