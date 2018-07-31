package huaweiTest;
 
//进行预测的函数
public class Regression {
	Matrix matrix = new Matrix();
	public double[][] predictW(double[][] x, double[][] y) {
		double[][] result = new double[x[0].length][1];
		double[][] temp_result = matrix.matrixMul(matrix.matrixTra(x), x);
		temp_result = matrix.matrixConv(temp_result);
		temp_result = matrix.matrixMul(temp_result, matrix.matrixTra(x));
		temp_result = matrix.matrixMul(temp_result, y);
		result = temp_result;
		return result;
	}

	public double MSE_value(double[][]x, double[][] y, double[][] w) {
		double result = 0.0;
		double template_count = x.length;
		double[][] template_MSE = matrix.matrixSub(y, matrix.matrixMul(x, w));
		for (int i = 0; i < template_MSE.length; i++) {
			result += Math.pow(template_MSE[i][0], 2);
		}
		result = result / template_count;
		return result;
	}
}
