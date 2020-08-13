package simple.src.etl;

import java.io.File;

public class FileUtils {
    private static final String DEFAULTPATH = "C:\\Dev\\SampleData\\testCSV\\ansi.csv";

    public static File getFile() {
        return getFile(DEFAULTPATH);
    }

    public static File getFile(String path) {
        return new File(path);
    }
}
