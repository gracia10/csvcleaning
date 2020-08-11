package simple.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadCSV {

	public static void main(String[] args) {

		File file = new File("C:\\sampledb\\testCSV");
		List<File> dirs = new ArrayList<>();

		for(File f: file.listFiles()) {
			if(f.isDirectory()) dirs.add(f);
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
// - 




