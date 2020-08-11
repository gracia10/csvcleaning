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

//		File file = new File("C:\\sampledb\\psql");
		File file = new File("C:\\sampledb\\testCSV");
		
		List<File> dirs = new ArrayList<>();
		
		trim(file.listFiles());
		
//		for(File f: file.listFiles()) {
//			if(f.isDirectory()) dirs.add(f);
//		}
//		
//		for(File f: dirs) {
//			print(f.listFiles());
//		}
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
		
		String line = "";
		StringBuilder strb = new StringBuilder();
		
		for(File f: files) {
			if(f.isFile()) {
				try (
						BufferedReader br = new BufferedReader(new FileReader(f));
						BufferedWriter bw = new BufferedWriter(new FileWriter(f,true))
					){
					
					while((line = br.readLine())!= null) {
						strb.append(line).append("\n");
					};
					
					line  = strb.toString().trim();
					System.out.println(line);
					bw.write(line);
					strb.setLength(0);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
	}
	
}


// 기존값 읽음 정제한 값 생성
// 기존값 덮어쓰기 불가
// temp 값저장
// java empty






