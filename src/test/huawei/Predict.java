
package test.huawei;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import test.huawei.Regression;

public class Predict {
	/**
	 * 根据某类历史输入数据，找到最优的w
	 * @param x
	 * @return
	 */
	public double[][] searchW(double[] x) {
		Regression regression = new Regression();
		// 设置一个很大的值用来迭代更新，最小误差, 设置x_min,y_min记录此时的样本
		double MSE_min = 500000.0;
		double[][] w = new double[1][1];
		// 维度至少为1， 样本数量必须大于变量数量，不然构建的矩阵，其转置乘自身不可逆,13是默认效果比较好的值
		for(int i = 1; i < 11; i++) {
			// 构建输入矩阵x,  y，i，为变量个数，则样本数有x.length-i,x的行为样本的样本数，列为变量数，y为（x.length-i）x 1的矩阵
			double[][] input_x = new double[x.length - i][i];
			double[][] input_y = new double[x.length - i][1];
			// 初始化input_x,input_y
			for(int m = 0; m < x.length - i; m++) {
				for(int n = 0; n < i; n++) {
					// 。。。。
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
				// EMS比较
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
// 测试w值有没有改变
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
	 * 某类数据以数组形式输入，和最优w一起输入，还有时间长度，每次只预测一个数据，将预测玩的数据加入数据集，重新进行训练
	 * @param x 某类数据的历史数据
	 * @param w 该类历史数据对应的最优解w
	 * @param predictTime 要预测的时间
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
