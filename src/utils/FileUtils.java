package src.utils;

import java.io.File;

public class FileUtils {

    public static File getFile(String path) {
        return new File(path);
    }

    public static void printFileOrDir(File[] files) {
        for(File f: files) {
            printFileOrDir(f);
        }
    }

    public static void printFileOrDir(File f) {
        if(f.isDirectory()) {
            System.out.println("This is Dir: "+f.getAbsolutePath());
        }else {
            System.out.println("This is File: "+f.getAbsolutePath());
        }
    }
}
