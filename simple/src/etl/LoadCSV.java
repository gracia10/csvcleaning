package simple.src.etl;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadCSV {
	private static final String DEFAULTPATH = "C:\\Dev\\SampleData\\testCSV\\ansi.csv";
	
	public static void main(String[] args) {
		System.out.println("hello world");
	}

	public static void getCharset() {
		try {
			byte[] arr = Files.readAllBytes(Paths.get(DEFAULTPATH));
			
			CharsetDetector charsetDetector = new CharsetDetector();
			charsetDetector.setText(arr);
			charsetDetector.enableInputFilter(true);
			CharsetMatch cm = charsetDetector.detect();
			
			System.out.println(cm.getName());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getMimeType(File file) {
		String mimeType;
		Tika tika = new Tika();
		
		try {
			mimeType = tika.detect(file);
			System.out.println(mimeType);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getMetadata() {
		
		Tika tika = new Tika();
		Metadata metadata = new Metadata();

		try (TikaInputStream reader = TikaInputStream.get(Paths.get(DEFAULTPATH))){
			
			
			// 파일 본문
			String contents = tika.parseToString(reader, metadata);
			System.out.println(contents);

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

	public static File getFile() {
		return getFile(DEFAULTPATH);
	}
	
	public static File getFile(String path) {
		return new File(path);
	}
	
	public static void cleaning(File file) {
		List<File> dirs = new ArrayList<>();

		for(File f: file.listFiles()) {
			if(f.isDirectory()) {
				dirs.add(f);
			}
		}
		
		for(File f: dirs) {
			trim(f.listFiles());
		}
		
	} 
	
	public static void print(File[] files) {
		for(File f: files) {
			if(f.isDirectory()) {
				System.out.println("This is Dir: "+f.getAbsolutePath());
			}else {				
				System.out.println("This is File: "+f.getAbsolutePath());
			}
			
		}
	}
	
	public static void trim(File[] files) {
		
		System.out.println("시작");
		
		String line = "";
		StringBuilder strb = new StringBuilder();
		BufferedWriter bw2 = null;
		
		
		for(File f: files) {
			if(f.isFile()) {
				try (
						BufferedReader br = new BufferedReader(new FileReader(f));
						BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
					){
					
					while((line = br.readLine())!= null) {
						strb.append(line).append("\n");
					};
					
					line  = strb.toString().trim();
					
					//if(f.getName().contains("sc_info")) {System.out.println(line);}
					
					bw2 = new BufferedWriter(new FileWriter(f));
					bw2.write(line);
					strb.setLength(0);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {
					try {
						bw2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		System.out.println("끝");
	}
	
}


//  20건 이상 없이 수행됨
//  2만건 이상 없이 수행됨
//  10만건 이상 없이 수행됨
//  50만건 이상 없이 수행됨
// 100만건 돌렸는데 마지막 파일 글자 깨짐
// -가설1: 파일 목록 출력하는 print 메소드를 실행해서 깨졌다 -> 여전히 깨짐
// -가설2: 한 파일에 담긴 데이터가 만건 이상이라 발생했다(1.5만) 9천건으로 줄여서 테스트 -> 여전히 깨짐...
// -데이터 양의 문제가 맞는지 3건의 테이터만으로 테스트 -> 성공, 만건 -> 성공...
// - 만건 성공데이터 4배로 돌려봄(4만건) -> 로우 길이의 문제!
// -가설3: 로우 길이와 상관없이 인코딩 차이의 문제 (문제파일 모드 ANSI 타입)



