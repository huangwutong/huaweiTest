package test.java.huawei;
 

public class Regression {
	/**
	 * 求解w的值
	 * @param x
	 * @param y
	 * @return 有解就返回result,无解返回null
	 */
	public double[][] predictW(double[][] x, double[][] y) {
		double[][] result = new double[x[0].length][1];
		Matrix matrix = new Matrix();
		double[][] temp_result = matrix.matrixMul(matrix.matrixTra(x), x);
		temp_result = matrix.matrixConv(temp_result);
		temp_result = matrix.matrixMul(temp_result, matrix.matrixTra(x));
		temp_result = matrix.matrixMul(temp_result, y);
		result = temp_result;
		return result;
	}
	/**
	 * 计算每个w对应的MSE值，用于选择最优w
	 * @param x 矩阵x
	 * @param y 矩阵y
	 * @param w 矩阵w
	 * @return 每个w的均方差（MSE）
	 */
	public double MSE_value(double[][]x, double[][] y, double[][] w) {
		double result = 0.0;
		double template_count = x.length;
		Matrix matrix = new Matrix();
		double[][] template_MSE = matrix.matrixSub(y, matrix.matrixMul(x, w));
		for (int i = 0; i < template_MSE.length; i++) {
			result += Math.pow(template_MSE[i][0], 2);
		}
		result = result / template_count;
		return result;
	}
}
