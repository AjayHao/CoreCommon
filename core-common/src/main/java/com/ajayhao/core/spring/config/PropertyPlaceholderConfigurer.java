package com.ajayhao.core.spring.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by AjayHao on 2017/6/30.
 */
public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer{

    public static final String USER_HOME = System.getProperty("user.home");
    public static final String WEB_RES_CLASS = "org.springframework.web.context.support.ServletContextResource";

    public PropertyPlaceholderConfigurer() {
    }

    public void setLocations(Resource[] locations) {
        ArrayList list = new ArrayList(locations.length);
        Resource[] arr = locations;
        int len = locations.length;

        for(int i = 0; i < len; ++i) {
            Resource location = arr[i];
            if(location instanceof FileSystemResource) {
                list.add(fromFile(location));
            } else if(location instanceof UrlResource) {
                list.add(fromUrl(location));
            } else if(isWebResource(location)) {
                Resource newLoc = fromUrl(location);
                list.add(newLoc == location?fromFile(location):newLoc);
            } else {
                list.add(location);
            }
        }

        super.setLocations((Resource[])list.toArray(new Resource[list.size()]));
    }

    public void setLocation(Resource location) {
        this.setLocations(new Resource[]{location});
    }

    private static boolean isWebResource(Resource location) {
        if(location == null) {
            return false;
        } else if(WEB_RES_CLASS.equals(location.getClass().getName())) {
            return true;
        }
        return false;
    }

    private static Resource fromFile(Resource location) {
        try {
            File e = location.getFile();
            String path = e.getPath();
            if(needFilter(path)) {
                path = filter(path);
                copyToSystem(path);
                return new FileSystemResource(path);
            } else {
                return location;
            }
        } catch (IOException var3) {
            return location;
        }
    }

    private static Resource fromUrl(Resource location) {
        try {
            final URL url = location.getURL();
            String path = url.getPath();

            if(needFilter(path) && url.getProtocol().equals("file")) {
                path = filter(path);
                copyToSystem(path);
                return new FileSystemResource(path);
            } else {
                return location;
            }
        } catch (IOException e) {
            return location;
        }
    }

    private static void copyToSystem(String path) {
        InputStream in = null;
        try {
            Properties p = new Properties();
            in = new BufferedInputStream(new FileInputStream(new File(path)));
            p.load(in);
            System.getProperties().putAll(p);
        } catch (Exception e) {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException e1) {
                ;
            }
        }
    }

    private static boolean needFilter(String path) {
        return path != null && !path.isEmpty() ? path.startsWith("~") || path.startsWith("/~") : false;
    }

    private static String filter(String file) {
        if(file.startsWith("~")) {
            file = USER_HOME + file.substring(1);
        } else if(file.startsWith("/~")) {
            file = USER_HOME + file.substring(2);
        }

        return file;
    }

}
