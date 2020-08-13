package src.etl;

import src.utils.FileUtils;
import src.utils.TikaUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadCSV {

	private static String path = "D:\\Dev\\data\\testCSV";

	public static void main(String[] args) {
		File file = FileUtils.getFile(path);

		for(File f: file.listFiles()){
			if(f.isFile()) trim(f);
		}
	}
	
	public static void trim(File f) {
		String line = "";
		String charset = TikaUtils.getCharset(f.getPath());
		StringBuilder strb = new StringBuilder();
		BufferedWriter bw = null;

		try (BufferedReader br = Files.newBufferedReader(Paths.get(f.getPath()),Charset.forName(charset))){

			while((line = br.readLine())!= null) {
				strb.append(line).append("\n");
			};

			line  = strb.toString().trim();

			bw = Files.newBufferedWriter(Paths.get(f.getPath()),Charset.forName(charset));
			bw.write(line);
			strb.setLength(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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
// - Tika로 charset 추출 후 타입에 맞에 read-write -> 성공



