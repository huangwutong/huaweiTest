package test.huawei;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.omg.CORBA.PUBLIC_MEMBER;

import test.java.huawei.Backpack;

public class Main {
	// 读取文件的方法
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

	public static void main(String[] args) throws ParseException {
		// 读配置文件
		String file = "D:\\input_5flavors_cpu_7days.txt";
		String[] array = readFileByLines(file);
		String file1 = "D:\\TrainData_2015.1.1_2015.2.19.txt";
		String[] array1 = readFileByLines(file1);
		Set<String> set = new TreeSet<String>();
		Set<String> set1 = new TreeSet<String>();
		List<Integer>list=new ArrayList<Integer>();
		// 物理服务器的cpu及内存定义
		int phsicalServer_cpu_count = Integer.parseInt(array[0].trim().split(" ")[0]);
		int phsicalServer_memory = Integer.parseInt(array[0].trim().split(" ")[1]) * 1024;
		// System.out.println(phsicalServer_memory);
		// 虚拟机数量
		int vm_count = Integer.parseInt(array[2].trim().split(" ")[0]);
		// 虚拟机种类及规格大小存储数组,第一行代表对应name的CPU数量，第二行对应name的cpu的内存
		String[] vm_name = new String[vm_count];
		int[][] vm = new int[2][vm_count];
		// 虚拟机初始化
		for (int i = 0; i < vm_count; i++) {
			vm_name[i] = array[3 + i].trim().split(" ")[0];
			vm[0][i] = Integer.parseInt(array[3 + i].trim().split(" ")[1]);
			vm[1][i] = Integer.parseInt(array[3 + i].trim().split(" ")[2]);
			System.out.println(vm_name[i]);
		}

		// 优化选项
		String optimize_type = array[4 + vm_count];
		// System.out.println(optimize_type);
		// 开始时间，结束时间
		String start_time = array[array.length - 2];
		String end_time = array[array.length - 1];
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		// Date date = dateFormat.parse(start_time);
		int len = array1.length;
		String[] flour = new String[len];
		String[] time1 = new String[len];
		String[] time2 = new String[len];
		
		for (int i = 0; i < len; i++) {
			flour[i] = array1[i].trim().split("	")[1];
			time1[i] = array1[i].trim().split("	")[2].split(" ")[0];
			time2[i] = array1[i].trim().split("	")[2].split(" ")[1];
			set.add(flour[i]);
			//System.out.print(flour[i]);
			set1.add(time1[i]);
		}
		 String start1=time1[0];
		 String end1=time1[len-1];
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date d1 = df.parse(start1);
		 Date d2 = df.parse(end1);
		 long diff = d2.getTime() - d1.getTime();
		 int day=(int) (diff/(24*60*60*1000)+1);
		 for (int i = 0; i < day; i++) {
			Date d3=df.parse("2015-01-01");
			Date[]d4=new Date[day];
			
		}
		 //System.out.println(day);
		String[] flourSinger = set.toArray(new String[0]);
		
		/*for (int i = 0; i < flourSinger.length; i++) {
			System.out.println(flourSinger[i]);
		}*/
		String[] timSinger = set1.toArray(new String[0]);
		int lenTim=timSinger.length;
		// System.out.println(flourSinger[2]);
		Main main = new Main();
		
		//获得list的时间的集合,获得string类型的时间变量
	    ArrayList<String> resultlist=new ArrayList<String>(); 
		resultlist=main.handleDays(50,1);
		
		//Map<String, Integer>flourcount=new LinkedHashMap<String, Integer>();
		Map<String, Integer> flourcount = new TreeMap<String, Integer>(
		        new Comparator<String>() {
		          public int compare(String obj1, String obj2) {
		            // 升序排序
		            return obj1.compareTo(obj2);
		          }
		        });

		Map<Integer, user> map = new LinkedHashMap<Integer, user>();
		for (int i = 0; i < len; i++) {
			map.put(i, main.new user(i, flour[i], time1[i]));
		}
		
		Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();
		int[][]flourCount=new int[flourSinger.length][];
		int max=0;
		for (int i = 0; i < flourSinger.length; i++) {
			user user1=null;
			user user2=null;
			 //System.out.println(flourSinger[i]);
			for (int j = 0; j < len; j++) {
				user1 = map.get(j);
				// System.out.println(user1.getFlourName());
				// System.out.println(flourSinger[i]);
				// System.out.println(user1.getTimes());
				Integer count = resultMap.get(user1.getTimes());
				//System.out.println(count);
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
				if (j==len-1) {
					flourcount.put(user1.getTimes(),resultMap.get(user1.getTimes()));
				}else {
					 user2=map.get(j+1);
					if (!(user1.getTimes().equals(user2.getTimes()))) {
						//flourCount[i][]=resultMap.get(user1.getTimes());
					//list.add(resultMap.get(user1.getTimes()));
					flourcount.put(user1.getTimes(),resultMap.get(user1.getTimes()));
				}
				}
			}
			//resultlist是系统的时间函数
			for (int k = 0; k < resultlist.size(); k++) {
				if (!(flourcount.containsKey(resultlist.get(k)))){
					flourcount.put(resultlist.get(k), 0);
				}
			}
			//System.out.println(list.size());
			for (int j = 0; j < resultlist.size(); j++) {
				flourCount[i]=new int[resultlist.size()];
				for (int j1 = 0; j1< resultlist.size(); j1++) {
					//System.out.println(list.get(j));
					flourCount[i][j1]=flourcount.get(resultlist.get(j1));
				}
			}
			resultMap.clear();
		}
		
		int[][]flourZong=new int[5][];
		for (int j = 0; j < flourCount.length; j++) {
			flourZong[0]=flourCount[0];
			flourZong[1]=flourCount[9];
			flourZong[2]=flourCount[13];
			flourZong[3]=flourCount[14];
			flourZong[4]=flourCount[15];
		}
		//System.out.println(flourZong[2][0]);
		
		for (int i = 0; i < vm_name.length; i++) {
			System.out.print(vm_name[i]+":");
			for (int j = 0; j < 50; j++) {
				System.out.print(flourZong[i][j]+" ");
			}
			System.out.println();
		}

		
		Backpack backpack = new Backpack();
		double[] weight = new double[vm_name.length];
		double[][] x = new double[][]{{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},{1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0},{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0},{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		if (optimize_type.equals("CPU")) {
			for (int i = 0; i < vm_name.length; i++) {
				double temp_v0 = vm[0][i];
				double temp_v1 = vm[1][i];
				weight[i] = temp_v0 / temp_v1;
			}
			//把这里的x替换成预测里面的输出结果
			ArrayList<String> result = backpack.optimize(x, vm, vm_name, weight, phsicalServer_cpu_count, phsicalServer_memory);
			System.out.println(result.size());
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i));
			}
		} else if (optimize_type.equals("内存")) {
			for (int i = 0; i < vm_name.length; i++) {
				double temp_v0 = vm[0][i];
				double temp_v1 = vm[1][i];
				weight[i] = temp_v1 / temp_v0;
			}
			//把这里的x替换成预测里面的输出结果
			ArrayList<String> result = backpack.optimize(x, vm, vm_name, weight, phsicalServer_cpu_count, phsicalServer_memory);
			System.out.println(result.size());
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i));
			}
		} else {
			System.out.println("优化种类判断错误");
		}
	}
	 public ArrayList<String> handleDays(int days,int span) throws ParseException{  
	        ArrayList<String> datelist = new ArrayList<String>();  
	  
	        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");  
	        Date date = null;  
	        date=dateformat.parse("2015-1-1");
	  
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

}
