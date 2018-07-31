package uestc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	// ��ȡ�ļ��ķ���
	public static String[] readFileByLines(String fileName) {
		String contentString = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				contentString += (tempString + ",");
				System.out.println(contentString);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
				}
			}
		}
		String[] array = contentString.split(",");
//		int len = array.length;
//		for (int i = 0; i < len; i++) {
//			System.out.println(array[i]);
//		}
		return array;
	}
	
	public static void main(String[] args) {
		// �������ļ�
		String file = "�����ĵ�\\����ʾ��\\input_5flavors_cpu_7days.txt";	
		String[] array = readFileByLines(file);
		
		// �����������cpu���ڴ涨��
		int phsicalServer_cpu_count = Integer.parseInt(array[0].trim().split(" ")[0]);
		int phsicalServer_memory = Integer.parseInt(array[0].trim().split(" ")[0]) * 1024;
		
		// ���������		
		int vm_count =  Integer.parseInt(array[2].trim().split(" ")[0]);
		// ��������༰����С�洢����,��һ�д����Ӧname��CPU�������ڶ��ж�Ӧname��cpu���ڴ�
		String[] vm_name = new String[vm_count];
		int[][] vm = new int[2][vm_count];
		// �������ʼ��
		for (int i = 0; i < vm_count; i++) {
			vm_name[i] = array[3+i].trim().split(" ")[0];
			vm[0][i] = Integer.parseInt(array[3+i].trim().split(" ")[1]);
			vm[1][i] = Integer.parseInt(array[3+i].trim().split(" ")[2]);
		}
		
		//�Ż�ѡ��
		String optimize_type = array[4 + vm_count];
		System.out.println(optimize_type);
		
		// ��ʼʱ�䣬����ʱ��
		String start_time = array[array.length - 2];
		String end_time = array[array.length - 1];
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		// Date date = dateFormat.parse(start_time);
		
		
	}
}
