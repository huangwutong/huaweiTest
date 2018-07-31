package com.elasticcloudservice.predict;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.elasticcloudservice.predict.Backpack;
import com.elasticcloudservice.predict.Denoising;
import com.elasticcloudservice.predict.OurPredict;

public class Predict {
	/*public static void main(String[] args) throws ParseException {
		Predict main=new Predict();
		 ArrayList<String> resultlist=new ArrayList<String>(); 
			resultlist=main.handleDays(50,1);
			for (int i = 0; i < resultlist.size(); i++) {
				System.out.println(resultlist.get(i));
			}			
	}*/
	public static String[] readFileByLines(String fileName) {
		String contentString = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				contentString += (tempString + ",");
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
		return array;
	}

	public ArrayList<String> handleDays(int days,int span) throws ParseException{  
		  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> datelist = new ArrayList<String>();  
        Date date = null;  
        date=toDate("2015-1-1");
        Calendar calendar = new GregorianCalendar();  
        calendar.setTime(date);  
        //把日期后推一天.(正数往后推,负数往前移动)，这样得到的结果就是从前一天往后排列。如果想带上今天，就不需要。  
        calendar.add(calendar.DATE,0);  
        date=calendar.getTime();  
        datelist.add(dateformat.format(date));//得到"yyyy-MM-dd"的日期格式的字符串  
        for(int i=0,l=days-1;i<l;i++){  
            calendar.setTime(date);  
            calendar.add(calendar.DATE,+span);//把日期减去span天.（隔span天，记录一次）  
            date=calendar.getTime();  
            String dateString = dateformat.format(date);//转化成想要的日期格式  
            datelist.add(dateString);  
        }  
        return datelist;  
    }  
	public Date toDate1(String str){
		Date dt = new Date();
		String[] parts = str.split("-");
		if(parts.length >= 3){
		int years = Integer.parseInt(parts[0]);
		int months = Integer.parseInt(parts[1]) - 1;
		int days = Integer.parseInt(parts[2]);
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		Calendar gc = new GregorianCalendar(years,months,
		days,hours,minutes,seconds);
		dt = gc.getTime();
		}
		return dt;
		}

	public Date toDate(String str){
		Date dt = new Date();
		String[] parts = str.split("-");
		if(parts.length >= 3){
		int years = Integer.parseInt(parts[0]);
		int months = Integer.parseInt(parts[1]) - 1;
		int days = Integer.parseInt(parts[2]);

		Calendar gc = new GregorianCalendar(years,months,
		days);
		dt = gc.getTime();
		}
		return dt;
		}
	
	
	public class user {
		private Integer id;

		private String flourName;

		private String times;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFlourName() {
			return flourName;
		}

		public void setFlourName(String flourName) {
			this.flourName = flourName;
		}

		public String getTimes() {
			return times;
		}

		public void setTimes(String times) {
			this.times = times;
		}

		public user(Integer id, String flourName, String times) {
			super();
			this.id = id;
			this.flourName = flourName;
			this.times = times;
		}
	}

	public static String[] predictVm(String[] ecsContent, String[] inputContent) throws ParseException {

		/** =========do your work here========== **/
		Predict main2=new Predict();
		String[] FinallyOutPut = new String[ecsContent.length];

		/*String[] array = inputContent;
		String[] array1 = ecsContent;*/
		String[] array = ecsContent;
		String[] array1 = inputContent;
		Set<String> set = new TreeSet<String>();
		Set<String> set1 = new TreeSet<String>();
		List<Integer> list = new ArrayList<Integer>();
		int phsicalServer_cpu_count = Integer.parseInt(array[0].trim().split(" ")[0]);
		int phsicalServer_memory = Integer.parseInt(array[0].trim().split(" ")[1]) * 1024;

		int vm_count = Integer.parseInt(array[2].trim().split(" ")[0]);
		String[] vm_name = new String[vm_count];
		int[][] vm = new int[2][vm_count];
		for (int i = 0; i < vm_count; i++) {
			vm_name[i] = array[3 + i].trim().split(" ")[0];
			vm[0][i] = Integer.parseInt(array[3 + i].trim().split(" ")[1]);
			vm[1][i] = Integer.parseInt(array[3 + i].trim().split(" ")[2]);
			// System.out.println(vm_name[i]);
		}

		String optimize_type = array[4 + vm_count];

		String start_time = array[array.length - 2];
		String end_time = array[array.length - 1];
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		
		Date start = main2.toDate1(start_time);
		Date end = main2.toDate1(start_time);;
		long diff1 = end.getTime() - start.getTime();
		int day1 = (int) (diff1 / (24 * 60 * 60 * 1000));
		int len = array1.length;
		String[] flour = new String[len];
		String[] time1 = new String[len];
		String[] time2 = new String[len];

		for (int i = 0; i < len; i++) {
			flour[i] = array1[i].trim().split("	")[1];
			time1[i] = array1[i].trim().split("	")[2].split(" ")[0];
			time2[i] = array1[i].trim().split("	")[2].split(" ")[1];
			set.add(flour[i]);
			set1.add(time1[i]);
		}
		String start1 = time1[0];
		String end1 = time1[len - 1];
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = main2.toDate(start1);
		Date d2 = main2.toDate(end1);
		long diff = d2.getTime() - d1.getTime();
		int day = (int) (diff / (24 * 60 * 60 * 1000) + 1);
		for (int i = 0; i < day; i++) {
			Date d3 = main2.toDate("2015-01-01");
			Date[] d4 = new Date[day];

		}
		// System.out.println(day);
		String[] flourSinger = set.toArray(new String[0]);

		/*
		 * for (int i = 0; i < flourSinger.length; i++) {
		 * System.out.println(flourSinger[i]); }
		 */

		String[] timSinger = set1.toArray(new String[0]);
		int lenTim = timSinger.length;
		// System.out.println(flourSinger[2]);
		Predict main = new Predict();
		ArrayList<String> resultlist = new ArrayList<String>();
		resultlist = main.handleDays(50, 1);

		// Map<String, Integer>flourcount=new LinkedHashMap<String, Integer>();
		Map<String, Integer> flourcount = new TreeMap<String, Integer>(new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				return obj1.compareTo(obj2);
			}
		});

		Map<Integer, user> map = new LinkedHashMap<Integer, user>();
		for (int i = 0; i < len; i++) {
			map.put(i, main.new user(i, flour[i], time1[i]));
		}

		Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();
		Map<String, double[]> countMap = new LinkedHashMap<String, double[]>();
		double[][] flourCount = new double[flourSinger.length][];
		int max = 0;
		for (int i = 0; i < flourSinger.length; i++) {
			user user1 = null;
			user user2 = null;
			// System.out.println(flourSinger[i]);
			for (int j = 0; j < len; j++) {
				user1 = map.get(j);
				// System.out.println(user1.getFlourName());
				// System.out.println(flourSinger[i]);
				// System.out.println(user1.getTimes());
				Integer count = resultMap.get(user1.getTimes());
				// System.out.println(count);
				if (flourSinger[i].equals(user1.getFlourName())) {
					if (count != null) {
						resultMap.put(user1.getTimes(), count + 1);
					} else {
						resultMap.put(user1.getTimes(), 1);
					}
				} else {
					if (count != null) {
						resultMap.put(user1.getTimes(), count);
					} else {
						resultMap.put(user1.getTimes(), 0);
					}
				}
				if (j == len - 1) {
					flourcount.put(user1.getTimes(), resultMap.get(user1.getTimes()));
				} else {
					user2 = map.get(j + 1);
					if (!(user1.getTimes().equals(user2.getTimes()))) {
						// flourCount[i][]=resultMap.get(user1.getTimes());
						// list.add(resultMap.get(user1.getTimes()));
						flourcount.put(user1.getTimes(), resultMap.get(user1.getTimes()));
					}
				}
			}
			for (int k = 0; k < resultlist.size(); k++) {
				if (!(flourcount.containsKey(resultlist.get(k)))) {
					flourcount.put(resultlist.get(k), 0);
				}
			}

			// System.out.println(list.size());,resultlist鏄椂闂寸殑50澶�
			for (int j = 0; j < resultlist.size(); j++) {
				flourCount[i] = new double[resultlist.size()];
				for (int j1 = 0; j1 < resultlist.size(); j1++) {
					// System.out.println(list.get(j));
					flourCount[i][j1] = flourcount.get(resultlist.get(j1));
				}
			}
			countMap.put(flourSinger[i], flourCount[i]);
			resultMap.clear();
		}

		double[][] flourZong = new double[vm_name.length][];
		for (int j = 0; j < vm_name.length; j++) {
			if (countMap.containsKey(vm_name[j])) {
				flourZong[j] = countMap.get(vm_name[j]);
			}
		}
		// System.out.println(flourZong[2][0]);

		/*
		 * for (int i = 0; i < vm_name.length; i++) {
		 * System.out.print(vm_name[i]+":"); for (int j = 0; j < 50; j++) {
		 * System.out.print(flourZong[i][j]+" "); } System.out.println(); }
		 */

		Backpack backpack = new Backpack();
		double[] weight = new double[vm_name.length];
		OurPredict predict = new OurPredict();
		Denoising denoising = new Denoising();

		double[][] predictResult = new double[vm_name.length][];
		for (int i = 0; i < vm_name.length; i++) {
			flourZong[i] = denoising.dataDenoising(flourZong[i]);

			for (int j = 0; j < flourZong[i].length; j++) {
				System.out.print(flourZong[i][j] + " ");
			}
			System.out.println();

			predictResult[i] = predict.predictResult(flourZong[i], day1);
			/*
			 * System.out.println(day1); for (int j = 0; j <
			 * predictResult[i].length; j++) {
			 * System.out.print(predictResult[i][j] + " "); }
			 * System.out.println();
			 */
		}

		if (optimize_type.equals("CPU")) {

			// Map<String, Integer>flourcount=new LinkedHashMap<String,
			// Integer>();
			Map<String, Integer> Vm_ResultMap = new TreeMap<String, Integer>(new Comparator<String>() {
				public int compare(String obj1, String obj2) {
					return obj1.compareTo(obj2);
				}
			});

			for (int i = 0; i < vm_name.length; i++) {
				double temp_v0 = vm[0][i];
				double temp_v1 = vm[1][i];
				weight[i] = temp_v0 / temp_v1;
			}

			ArrayList<String> result = backpack.optimize(predictResult, vm, vm_name, weight, phsicalServer_cpu_count,
					phsicalServer_memory);
			String[][] result1 = new String[result.size()][];
			//FinallyOutPut = new String[vm_name.length + 3 + result.size()];

			for (int i = 0; i < result.size(); i++) {
				result1[i] = result.get(i).split(" ");
				// System.out.println(result.get(i));
			}
			int lenResult = 0;
			for (int i = 0; i < result1.length; i++) {
				lenResult += result1[i].length;
			}
			// System.out.println(lenResult);
			FinallyOutPut[0] = lenResult + "";

			Set<String> flour_countSet = new LinkedHashSet<String>();
			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < result1[i].length; j++) {
					flour_countSet.add(result1[i][j]);
				}
			}
			Object[] flourStrings = flour_countSet.toArray();
			// System.out.println(flourStrings.length);
			int[][] result2 = new int[result.size()][flourStrings.length];
			// int[][] result2 = new int[result.size()][];
			int count = 0;

			/*
			 * for (int i = 0; i < flourStrings.length; i++) {
			 * System.out.println(flourStrings[i]); }
			 */
			for (int i = 0; i < flourStrings.length; i++) {

				for (int j = 0; j < result.size(); j++) {

					for (int j2 = 0; j2 < result1[j].length; j2++) {
						// System.out.println(result1[j][j2]);
						if (flourStrings[i].equals(result1[j][j2])) {
							count += 1;
						}
					}
					result2[j][i] = count;
					count = 0;
				}
			}

			for (int i = 0; i < flourStrings.length; i++) {
				int count1 = 0;
				for (int j = 0; j < result.size(); j++) {
					count1 += result2[j][i];
				}
				System.out.println(flourStrings[i] + " " + count1);
				Vm_ResultMap.put(flourStrings[i].toString(), count1);
			}
			System.out.println();
			// FinallyOutPut[vm_name.length+1]="";
			for (int i = 0; i < vm_name.length; i++) {
				if (!Vm_ResultMap.containsKey(vm_name[i])) {
					Vm_ResultMap.put(vm_name[i], 0);
				}
			}
			for (int i = 0; i < Vm_ResultMap.size(); i++) {
				// System.out.println(vm_name[i]+"
				// "+Vm_ResultMap.get(vm_name[i]));
				FinallyOutPut[i + 1] = vm_name[i] + " " + Vm_ResultMap.get(vm_name[i]);
			}
			FinallyOutPut[vm_name.length + 1] = "";

			FinallyOutPut[vm_name.length + 2] = result.size() + "";
			Map<String, Integer> map2 = new LinkedHashMap<String, Integer>();

			int count3 = 0;
			for (int j = 0; j < result.size(); j++) {
				for (int i = 0; i < flourStrings.length; i++) {
					for (int j2 = 0; j2 < result1[j].length; j2++) {
						// System.out.println(result1[j][j2]);
						if (flourStrings[i].equals(result1[j][j2])) {
							count3 += 1;
							map2.put(result1[j][j2], count3);
						}
					}
					count3 = 0;
				}
				Set set2 = map2.keySet();
				Iterator iterator = set2.iterator();
				String k = "";
				boolean flag=false;
				String finallyString="";
				while (iterator.hasNext()) {
					 k = (String) iterator.next();
					 if (flag==false) {
						 //System.out.print(j+1+" "+k + " " + map2.get(k));
						 finallyString=j+1+" "+k + " " + map2.get(k);
						 flag=true;
					}else {
						finallyString+=" "+k + " " + map2.get(k);
						//System.out.print(" "+k + " " + map2.get(k));
					}
				}
				// System.out.println();
				//System.out.println(j);
				FinallyOutPut[vm_name.length + 2 + j+1] =finallyString;
				map2.clear();
				count3 = 0;
			}
			
			for (int i = 0; i < FinallyOutPut.length; i++) {
				System.out.println(FinallyOutPut[i]);
			}			
			
		} else if (optimize_type.equals("鍐呭瓨")) {
			Map<String, Integer> Vm_ResultMap = new TreeMap<String, Integer>(new Comparator<String>() {
				public int compare(String obj1, String obj2) {
					return obj1.compareTo(obj2);
				}
			});
			for (int i = 0; i < vm_name.length; i++) {
				double temp_v0 = vm[0][i];
				double temp_v1 = vm[1][i];
				weight[i] = temp_v1 / temp_v0;
			}
			ArrayList<String> result = backpack.optimize(predictResult, vm, vm_name, weight, phsicalServer_cpu_count,
					phsicalServer_memory);
			String[][] result1 = new String[result.size()][];

			FinallyOutPut = new String[vm_name.length + 3 + result.size()];

			for (int i = 0; i < result.size(); i++) {
				result1[i] = result.get(i).split(" ");
				// System.out.println(result.get(i));
			}

			int lenResult = 0;
			for (int i = 0; i < result1.length; i++) {
				lenResult += result1[i].length;
			}

			// System.out.println(lenResult);
			FinallyOutPut[0] = lenResult + "";

			Set<String> flour_countSet = new LinkedHashSet<String>();
			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < result1[i].length; j++) {
					flour_countSet.add(result1[i][j]);
				}
			}
			Object[] flourStrings = flour_countSet.toArray();
			// System.out.println(flourStrings.length);
			int[][] result2 = new int[result.size()][flourStrings.length];
			// int[][] result2 = new int[result.size()][];
			int count = 0;

			/*
			 * for (int i = 0; i < flourStrings.length; i++) {
			 * System.out.println(flourStrings[i]); }
			 */
			for (int i = 0; i < flourStrings.length; i++) {

				for (int j = 0; j < result.size(); j++) {

					for (int j2 = 0; j2 < result1[j].length; j2++) {
						// System.out.println(result1[j][j2]);
						if (flourStrings[i].equals(result1[j][j2])) {
							count += 1;
						}
					}
					result2[j][i] = count;
					count = 0;
				}
			}
			for (int i = 0; i < flourStrings.length; i++) {
				int count1 = 0;
				for (int j = 0; j < result.size(); j++) {
					count1 += result2[j][i];
				}
				System.out.println(flourStrings[i] + " " + count1);
				Vm_ResultMap.put(flourStrings[i].toString(), count1);
			}
			System.out.println();
			// FinallyOutPut[vm_name.length+1]="";
			for (int i = 0; i < vm_name.length; i++) {
				if (!Vm_ResultMap.containsKey(vm_name[i])) {
					Vm_ResultMap.put(vm_name[i], 0);
				}
			}
			for (int i = 0; i < Vm_ResultMap.size(); i++) {
				// System.out.println(vm_name[i]+"
				// "+Vm_ResultMap.get(vm_name[i]));
				FinallyOutPut[i + 1] = vm_name[i] + " " + Vm_ResultMap.get(vm_name[i]);
			}
			FinallyOutPut[vm_name.length + 1] = "";
			FinallyOutPut[vm_name.length + 2] = result.size() + "";
			Map<String, Integer> map2 = new LinkedHashMap<String, Integer>();
			int count3 = 0;
			for (int j = 0; j < result.size(); j++) {
				for (int i = 0; i < flourStrings.length; i++) {
					for (int j2 = 0; j2 < result1[j].length; j2++) {
						// System.out.println(result1[j][j2]);
						if (flourStrings[i].equals(result1[j][j2])) {
							count3 += 1;
							map2.put(result1[j][j2], count3);
						}
					}
					count3 = 0;
				}
				Set set2 = map2.keySet();
				Iterator iterator = set2.iterator();
				String k = "";
				boolean flag=false;
				String finallyString="";
				while (iterator.hasNext()) {
					 k = (String) iterator.next();
					 if (flag==false) {
						 //System.out.print(j+1+" "+k + " " + map2.get(k));
						 finallyString=j+1+" "+k + " " + map2.get(k);
						 flag=true;
					}else {
						finallyString+=" "+k + " " + map2.get(k);
						//System.out.print(" "+k + " " + map2.get(k));
					}
				}
				// System.out.println();
				//System.out.println(j);
				FinallyOutPut[vm_name.length + 2 + j+1] =finallyString;
				map2.clear();
				count3 = 0;
			}
			
			for (int i = 0; i < FinallyOutPut.length; i++) {
				System.out.println(FinallyOutPut[i]);
			}			
		} else {
			System.out.println("cuowu");
		}
		return FinallyOutPut;
	}

}
