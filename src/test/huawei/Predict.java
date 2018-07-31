
package test.huawei;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import test.huawei.Regression;

public class Predict {
	/**
	 * ����ĳ����ʷ�������ݣ��ҵ����ŵ�w
	 * @param x
	 * @return
	 */
	public double[][] searchW(double[] x) {
		Regression regression = new Regression();
		// ����һ���ܴ��ֵ�����������£���С���, ����x_min,y_min��¼��ʱ������
		double MSE_min = 500000.0;
		double[][] w = new double[1][1];
		// ά������Ϊ1�� ��������������ڱ�����������Ȼ�����ľ�����ת�ó���������,13��Ĭ��Ч���ȽϺõ�ֵ
		for(int i = 1; i < 11; i++) {
			// �����������x,  y��i��Ϊ��������������������x.length-i,x����Ϊ����������������Ϊ��������yΪ��x.length-i��x 1�ľ���
			double[][] input_x = new double[x.length - i][i];
			double[][] input_y = new double[x.length - i][1];
			// ��ʼ��input_x,input_y
			for(int m = 0; m < x.length - i; m++) {
				for(int n = 0; n < i; n++) {
					// ��������
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
				// EMS�Ƚ�
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
// ����wֵ��û�иı�
//		System.out.println(MSE_min);
//		System.out.println(w.length);
//		System.out.println();
//		for (int a = 0; a < w.length; a++) {
//		for (int b = 0; b < w[0].length; b++) {
//			System.out.print(w[a][b]);
//			System.out.print(" ");
//		}
//		System.out.println();
//	}
//	System.out.println();
		return w;
	}
	/**
	 * ĳ��������������ʽ���룬������wһ�����룬����ʱ�䳤�ȣ�ÿ��ֻԤ��һ�����ݣ���Ԥ��������ݼ������ݼ������½���ѵ��
	 * @param x ĳ�����ݵ���ʷ����
	 * @param w ������ʷ���ݶ�Ӧ�����Ž�w
	 * @param predictTime ҪԤ���ʱ��
	 * @return
	 */
	public double[] predictResult(double[] x, int predictTime) {
		double[] result = new double[predictTime];
		double[] predictArray = new double[predictTime];
		int len = x.length;
		double[] predictInputArray = x;
		for (int i = 0; i < predictTime; i++) {
			double predictOneDayValue = 0.0;
			//System.out.println(predictInputArray[predictInputArray.length - 6]);
			double[][] w = searchW(predictInputArray);
			for(int j = 0; j < w.length; j++) {
				predictOneDayValue += predictInputArray[predictInputArray.length - w.length + j] * w[j][0];
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
	public static void main(String[] args) {
		Predict predict = new Predict();

		// 1
		double[] input1 = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		// 2
		double[] input2 = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0};
		// 3
		double[] input3 = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		//4
		double[] input4 = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		// 5 
		double[] input5 = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 12.0, 2.0, 0.0, 1.0, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 1.0, 0.0, 3.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0};  
		double[] input = new double[input1.length];
		input = input5;
		for (int i = 0; i < predict.predictResult(input,7).length; i++) {
			System.out.println(predict.predictResult(input,7)[i]);
		}
//		predict.predictResult(input,7);	
	}
}
