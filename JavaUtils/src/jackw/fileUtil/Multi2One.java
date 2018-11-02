package jackw.fileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Multi2One {

	/**
	 * 从传入的文件或者文件夹中的所有内容复制到一个新文件里
	 * 
	 * @param src 目标文件/文件夹
	 * @return 新文件路径
	 */
	public static String files2File(String src) {
		File file = new File("file.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File files = new File(src);
		if (!files.exists()) {
			return "";
		}
		copyFile(file, files);

		return file.getAbsolutePath();

	}

	private static void copyFile(File file, File files) {
		if (files.isDirectory()) {
			File[] fs = files.listFiles();
			for (File f : fs) {
				copyFile(file, f);
			}
		} else {
			String charSet = FileCharsetParser.get_charset(files);
			System.out.println(charSet);
			InputStreamReader isr = null;
            BufferedReader br = null;
           // OutputStreamWriter fw = null;     
            BufferedWriter fw = null;
			try {
				
				isr = new InputStreamReader(new FileInputStream(files), charSet);
				br = new BufferedReader(isr);
				fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), charSet));
				fw.write("\r\n/***\r\n" + files.getName() + "\r\n***/\r\n");
				// 循环读和循环写
				int len = 0;
				while ((len = br.read()) != -1) {
					fw.write((char) len);
				}
				fw.write("\r\n");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

	public static void main(String[] args) {
		files2File("F:\\JAVA\\JAVA\\汪建明第四次大作业");
	}
}
