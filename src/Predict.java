import java.util.ArrayList;
import java.util.List;

import javatest.main1;

public class Predict {
	
	public static List<Object> markovChain(List<List<Double>> rawData){  
	    //存放统计数据  
	    List<Object> theStatisticsArray = new ArrayList<Object>();  
	    //存放概率P  
	    List<Object> theProbabilityArray = new ArrayList<Object>();
	    int len=rawData.size()-1; 
	    int times = 1;  
	    double sum=0;  
	    double p=0;  
	   if(len==0||len==-1){  
	    	theStatisticsArray.add("数据量不够，请导入足够数据！");  
	    	return theStatisticsArray;  
	    	    }  
	    else{  
	        for(int i=0;i<len;i++){  
	            for(int j=0;j<rawData.get(i).size();j++){  
	                boolean status = true;  
	                    if(theStatisticsArray.size()==0){  
	                    	System.out.println(1);
	                        theStatisticsArray.add(rawData.get(i).get(j));  
	                        theStatisticsArray.add(rawData.get(i+1).get(j));  
	                        theStatisticsArray.add(times);  
	                    }else{  
	                        for(int k=0;k<theStatisticsArray.size();k+=3){  
	                            if(rawData.get(i).get(j).equals(theStatisticsArray.get(k))){  
	                                if(rawData.get(i+1).get(j).equals(theStatisticsArray.get(k+1))){  
	                                    theStatisticsArray.set(k+2, (Integer)theStatisticsArray.get(k+2)+1);  
	                                    status = false;  
	                                    }  
	                                }  
	                            }  
	                        if(status){  
	                            theStatisticsArray.add(rawData.get(i).get(j));  
	                            theStatisticsArray.add(rawData.get(i+1).get(j));  
	                            theStatisticsArray.add(times);  
	                    }  
	      
	                }  
	            }  
	        }  
	    
	    
	    for(int i=0;i<theStatisticsArray.size();i+=3){  
            sum = sum+(Integer)theStatisticsArray.get(i+2);  
        }  
        for(int i=0;i<theStatisticsArray.size();i+=3){  
            p = (Integer)theStatisticsArray.get(i+2)/sum;  
            for(int j=i;j<i+3;j++){  
                if(j==i+2) continue;  
                theProbabilityArray.add(theStatisticsArray.get(j));  
            }  
            theProbabilityArray.add(p);  
        }  
        return theProbabilityArray;  
    } 
	}
	public static void main(String[] args) {
		Predict predict=new Predict();
		List<Double>rawData1=new ArrayList<Double>();
		List<Double>rawData3=new ArrayList<Double>();
		List<List<Double>>rawData2=new ArrayList<List<Double>>();
		double[]num={0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,
				5.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,2.0,1.0,
				0.0,0.0,0.0,1.0,0.0,1.0,0.0,1.0};
		double[]num2={4.0,0.0,8.0,4.0,4.0,0.0,0.0,1.0,0.0,7.0,
				1.0,0.0,0.0,0.0,0.0,0.0,5.0,1.0,0.0,4.0,0.0,
				0.0,4.0,0.0,0.0,0.0,0.0};
		double[]num3={0.0,0.0,0.0,1.0,0.0,1.0,0.0,0.0,0.0,0.0,
				2.0,0.0,0.0,6.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,
				2.0,0.0,0.0,0.0,0.0,0.0};
		double[]num4={0.0,0.0,0.0,1.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,
				0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,
				0.0,1.0,0.0};
		double[]num5={0.0,4.0,1.0,0.0,0.0,0.0,0.0,5.0,8.0,5.0,
				0.0,17.0,0.0,1.0,16.0,5.0,7.0,15.0,0.0,0.0,1.0,
				0.0,5.0,3.0,12.0,0.0,0.0};
		for (int i = 0; i < num.length; i++) {
			rawData1.add(num[i]);
			rawData2.add(rawData1);
		}
		/*for (int i = 0; i < num5.length; i++) {
			rawData3.add(num[i]);
			rawData2.add(rawData3);
		}*/
		//rawData2.add(rawData1);
	    
		/*{   0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 1.0 
			0.0 0.0 0.0 0.0 2.0 0.0 0.0 6.0 0.0 1.0
			0.0 0.0 0.0 0.0 0.0 2.0 0.0 0.0 0.0 0.0 
			0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 1.0 0.0
			0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 
			0.0 0.0 1.0 0.0 0.0 0.0 1.0 0.0 0.0 4.0 
			1.0 0.0 0.0 0.0 0.0 5.0 8.0 5.0 0.0 17.0
			0.0 1.0 16.0 5.0 7.0 15.0 0.0 0.0 1.0 0.0
			5.0 3.0 12.0 0.0 0.0};*/
		List<Object>obj=predict.markovChain(rawData2);
		for (int j = 0; j < obj.size(); j++) {
			System.out.print(obj.get(j)+" ");
		}
	}
}

	
	
