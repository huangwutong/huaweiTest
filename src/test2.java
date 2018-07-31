import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sun.security.action.PutAllAction;

import java.util.Set;
import java.util.TreeSet;
public class test2 {
	public static void main(String[] args) throws Exception {        
       File file = new File("D:\\input_5flavors_cpu_7days.txt");
       String[] strs = file2StringArray(file);
       jisuan(strs);
		test2 t=new test2();
       File file1 = new File("D:\\TrainData_2015.1.1_2015.2.19.txt");
       String[] strs1 = file2StringArray(file1);
       //获得list的时间的集合
       ArrayList<String> resultlist=new ArrayList<String>(); 
       resultlist=t.handleDays(30,1);
       System.out.println(resultlist);
       int lenth=resultlist.size();
       String[]timObjects=new String[lenth];
       //遍历结果集，打印  
       for (int i=0;i<lenth;i++) {  
            timObjects[i]=resultlist.get(i);
            //System.out.println(timObjects[i]);
       }  
       jisuanTrain(strs1,timObjects);
    }
	//处理时间
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
	 
	 
	 
	private static float[][] jisuanTrain(String[] strs1, String[] timObjects) {
		 //flour--counts
		 Map<String, String>map=new HashMap();
		//时间--flour
		 Map<String,String>map1=new IdentityHashMap<String, String>();
		 map.putAll(map1);
		 //timObj-count
		 Map<String,String>map2=new IdentityHashMap<String, String>();
		 Map<String, int[]>map3=new HashMap<String, int[]>();
		 List<String>list=new ArrayList<String>();
		for (int i = 0; i < strs1.length; i++) {
			if (strs1[i].split("	") != null ) {
				String[]strings=strs1[i].split("	");
				for (int j = 0; j < strings.length; j++) {
					if (j%3==2) {
						String[]sr=strings[j].split(" ");
						for (int k = 0; k < sr.length; k++) {
							list.add(sr[k]);
						}
					}else {
						list.add(strings[j]);
					}
					
					
				}
			}
		}
		
		int lenth=list.size();
		String[]objects=new String[lenth];
		for (int i = 0; i < lenth; i++) {
			objects[i]=list.get(i);
		}
		
		//obj是把数据进行的拆分
		for (int i = 1; i < objects.length;) {
			map.put(objects[i],"0");
			i=i+4;
		}
		
		for (int j = 2; j < objects.length;) {
			map1.put(objects[j],objects[j-1]);
			j=j+4;
		}
		for (int i = 0; i < timObjects.length; i++) {
			map2.put(timObjects[i], "0");
		}
		boolean isAdd=false;
		
		Set<String>set2=new HashSet();
		Set<String>set3=new TreeSet<String>();
		Set<Entry<String, String>> keys =map1.entrySet() ;// 得到全部的key
		Iterator<Entry<String, String>> iter = keys.iterator() ;
		while(iter.hasNext()){
		Entry<String, String> str = iter.next() ;
		set2.add(str.getKey());
		}
		
		List<String>list2=new ArrayList<String>();
		Set<Entry<String, String>> keys1 =map1.entrySet() ;// 得到全部的key
		Iterator<Entry<String, String>> iter1 = keys.iterator() ;
		while(iter1.hasNext()){
		Entry<String, String> str = iter1.next() ;
		list2.add(str.getKey());
		}
		//flour里面的数据都存在了strings数组里面
		String[]strings=set2.toArray(new String[0]);
		for (int i = 0; i < strings.length; i++) {
			//System.out.println(strings[i]);
		}
		//对时间进行排序
		Collections.sort(list2);
		//时间的key值放在数组里面
		String[]strings1=list2.toArray(new String[0]);
		//遍历我们的时间的数据
		for (int i = 0; i < strings1.length; i++) {
			map1.put(strings1[i],map1.get(strings1[i])) ;
			map.put(map1.get(strings1[i]), map.get(map1.get(strings1[i]))+1) ;
			set3.add(strings1[i]);
		}
		//不重复的时间的值
		String[]strings2=set3.toArray(new String[0]);
		int[]timCount=new int[timObjects.length];
		/*for (int i = 0; i < strings2.length; i++) {
			System.out.println(strings2[i]);
		}*/
		for (int i = 0; i < strings.length; i++) {
			
			//这里面可以获得flour得值timObjects
			int j=0;
				for (int j2 = 0; j2 < timObjects.length; j2++) {
					//System.out.println(timObjects[j2]+" "+strings2[j]+" ");
					j=j+1;
					if (timObjects[j2].contains(strings2[j])) {
						System.out.println(map.get(strings2[j]));
						timCount[j2]=Integer.parseInt(map.get(strings2[j]));
					}else {
						timCount[j2]=0;
					}
			}
			//flour-数组
			map3.put(strings[i], timCount);
		}
		   /* int[] is = map3.get(strings[2]);
		    for (int i = 0; i < is.length; i++) {
				System.out.println(is[i]);
			}*/
		return null;
		}
		
		

	private static void jisuan(String[] strs) throws Exception {
		String[] s1=strs[0].split(" ");
        //String[] s2=strs[1].split(" ");
        String[] s3=strs[2].split(" ");
        String[] s4=strs[3].split(" ");
        String[] s5=strs[4].split(" ");
        String[] s6=strs[5].split(" ");
        String[] s7=strs[6].split(" ");
        String[] s8=strs[7].split(" ");
        //物理服务器
        int wuli_cpu=Integer.parseInt(s1[0]);
        int neicun=Integer.parseInt(s1[1])*1024;
        int yingpan=Integer.parseInt(s1[2]);
        //虚拟机
        String xuniji1_num=strs[1];
        String xuniji1_name=s3[0];
        int cpu_1=Integer.parseInt(s3[1]);
        int neicun_1=Integer.parseInt(s3[2])*1024;
        
        String xuniji2_name=s4[0];
        int cpu_2=Integer.parseInt(s4[1]);
        int neicun_2=Integer.parseInt(s4[2]);
        
        String xuniji3_name=s5[0];
        int cpu_3=Integer.parseInt(s5[1]);
        int neicun_3=Integer.parseInt(s5[2]);
        
        String xuniji4_name=s6[0];
        int cpu_4=Integer.parseInt(s6[1]);
        int neicun_4=Integer.parseInt(s6[2]);
        
        String xuniji5_name=s7[0];
        int cpu_5=Integer.parseInt(s7[1]);
        int neicun_5=Integer.parseInt(s7[2]);
        //需要优化资源
        String youhua=strs[7];
        //开始时间
        String start=strs[8];
        //结束时间
        String end=strs[9];
        
        System.out.println(start);
        //times
        int week=dayForWeek(start);
        time(week);
	}

	private static void time(int week) {
		// TODO Auto-generated method stub
		switch (week) {
		case 1:
			System.out.println("星期一");
			break;
		case 2:
			System.out.println("星期二");
			break;
		case 3:
			System.out.println("星期三");
			break;
		case 4:
			System.out.println("星期四");
			break;
		case 5:
			System.out.println("星期五");
			break;
		case 6:
			System.out.println("星期六");
			break;
		case 7:
			System.out.println("星期日");
			break;
		}
	}

	private static String[] file2StringArray(File file) {
        BufferedReader br = null;
        List<String> list = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(file));
            String str = null;            
            while( (str = br.readLine()) != null) {
            	if(str.equals(""));
            	else {
            		list.add(str);   
				}
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return list.toArray(new String[0]);        
    }
	
	public static int dayForWeek(String pTime) throws Exception {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		 Calendar c = Calendar.getInstance();  
		 c.setTime(format.parse(pTime));  
		 int dayForWeek = 0;  
		 if(c.get(Calendar.DAY_OF_WEEK) == 1){  
		  dayForWeek = 7;  
		 }else{  
		  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
		 }  
		 return dayForWeek;  
		}
	
	
}
