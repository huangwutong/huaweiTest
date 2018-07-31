package huaweiTest;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javatest.main1;


public class OurPredict {
	Regression regression = new Regression();
	public double[][] searchW(double[] x) {
		double MSE_min = 500000.0;
		double[][] w = new double[1][1];
		for(int i = 1; i <13; i++) {
			double[][] input_x = new double[x.length - i][i];
			double[][] input_y = new double[x.length - i][1];
			for(int m = 0; m < x.length - i; m++) {
				for(int n = 0; n < i; n++) {
					input_x[m][n] = x[m + n];
				} 
			}
			for(int j = 0; j < x.length - i;j++) {
				input_y[j][0] = x[j + input_x[0].length];
			}
			try{
				double[][] temp_w = new double[regression.predictW(input_x,input_y).length][1];				
				temp_w = regression.predictW(input_x,input_y);
				double temp_EMS = regression.MSE_value(input_x, input_y, temp_w);
//				System.out.println(MSE_min);
//				System.out.println(temp_EMS);
//				System.out.println();
				if (temp_EMS <= MSE_min) {
					MSE_min = temp_EMS;
					w = new double[regression.predictW(input_x,input_y).length][1];
					w = temp_w;
				}
			} catch(IllegalArgumentException e) {
				// System.out.println(e);
				continue;
			}	
		}
		return w;
	}
	
	
	public double[] predictResult(double[] x, int predictTime) {
		double[] result = new double[predictTime];
		double[] predictArray = new double[predictTime];
		double[] predictInputArray = x;
		for (int i = 0; i < predictTime; i++) {
			double predictOneDayValue = 0.0;
			//System.out.println(predictInputArray[predictInputArray.length - 6]);
			//z¼ÆËãÈ¨ÖØ
			System.out.println("*************************");
			double[][] w = searchW(predictInputArray);
			for (int j = 0; j < w.length; j++) {
				for (int j2 = 0; j2 < w[0].length; j2++) {
					System.out.print(w[j][j2]+" ");
				}
			}
			System.out.println("*************************");
			for(int j = 0; j < w.length; j++) {
				predictOneDayValue += predictInputArray[predictInputArray.length - w.length + j] * w[j][0];
			}
			if (predictOneDayValue < 0.0) {
				predictOneDayValue = 0.0;
			}
			//System.out.println(predictOneDayValue);
			predictArray[i] = Math.round(predictOneDayValue + 0.49);
			double[] tempArray = predictInputArray;
			predictInputArray = new double[tempArray.length + 1];
			for (int j = 0; j < tempArray.length; j++) {
				predictInputArray[j] = tempArray[j];
			}
			predictInputArray[predictInputArray.length - 1] = Math.round(predictOneDayValue + 0.49);
		} 
		result = predictArray;
		return result;
	}
	
}