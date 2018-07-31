package javatest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class main1 {
	public static String[] readFileByLines(String fileName) {
		String contentString = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// System.out.println("以字符为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				contentString += (tempString + ",");
				// System.out.println(contentString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}

		String[] array = contentString.split(",");
		/*
		 * int len = array.length; for (int i = 0; i < len; i++) {
		 * System.out.println(array[0]); }
		 */
		return array;
	}
}
