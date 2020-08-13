package src.utils;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TikaUtils {

    private static Tika tika = new Tika();

    public static String getCharset(String path) {
        String charset = null;

        try {
            byte[] arr = Files.readAllBytes(Paths.get(path));
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(arr);
            charsetDetector.enableInputFilter(true);
            CharsetMatch cm = charsetDetector.detect();
            charset = cm.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return charset;
    }

    public static void printMimeType(File file) {
        String mimeType = "";
        try {
            mimeType = tika.detect(file);
            System.out.println(mimeType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void printContents(String path) {

        Metadata metadata = new Metadata();
        try (TikaInputStream reader = TikaInputStream.get(Paths.get(path))){

            // 파일 본문
            String contents = tika.parseToString(reader, metadata);
            System.out.println(contents);

        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }

    }

    public static void printMetadata(String path) {

        Metadata metadata = new Metadata();
        try (TikaInputStream reader = TikaInputStream.get(Paths.get(path))){

            tika.parseToString(reader, metadata);

            /*
             * 파일 메타데이터 X-Parsed-By: org.apache.tika.parser.DefaultParser
             * Content-Encoding: UTF-8
             * csv:delimiter: comma
             * Content-Type: text/csv; charset=UTF-8; delimiter=comma
             */

            for(String name : metadata.names()) {
                System.out.println(name + ": " + metadata.get(name));
            }

        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }

    }
}
