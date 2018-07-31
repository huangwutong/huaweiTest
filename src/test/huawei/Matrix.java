package test.huawei;
public class Matrix {
	private final int OPERATION_ADD = 1;
	private final int OPERATION_SUB = 2;
	private final int OPERATION_MUL = 3;
	/**
	 * 判断矩阵是否可以进行加、减、乘运算
	 * @param x 矩阵x
	 * @param y 矩阵y
	 * @param type 进行运算的种类
	 * @return 符合运算规则返回true，否则false
	 */
	private boolean legalOperation(double[][] x, double[][] y, int type) {
		boolean legal = true;
		if (type == OPERATION_ADD || type == OPERATION_SUB) {
			if (x.length != y.length || x[0].length != y[0].length) {
				legal = false;
			}
		} else if (type == OPERATION_MUL) {
			if (x[0].length !=y.length) {
				legal = false;
			}
		}
		return legal;
	}
	/**
	 * 矩阵的加法
	 * @param x 矩阵x
	 * @param y 矩阵y
	 * @return 运算合法，返回结果，运算不合法抛出参数异常的错误
	 */
	public double[][] matrixAdd(double[][] x, double[][] y) {
		double[][] result = new double[x.length][x[0].length];
		if (legalOperation(x, y , OPERATION_ADD)) {
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					result[i][j] = x[i][j] + y[i][j];
				}
			}
			return result;
		} else {
			throw new IllegalArgumentException("参数矩阵行列不等");
		}
		
	}
	/**
	 * 矩阵的减法
	 * @param x 矩阵x
	 * @param y 矩阵y
	 * @return 运算合法，返回结果，运算不合法抛出参数异常的错误
	 */
	public double[][] matrixSub(double[][] x, double[][] y) {
		double[][] result = new double[x.length][x[0].length];
		if (legalOperation(x, y , OPERATION_SUB)) {
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					result[i][j] = x[i][j] - y[i][j];
				}
			}
			return result;
		} else {
			throw new IllegalArgumentException("参数矩阵行列不等");
		}
		
	}
	/**
	 * 矩阵的乘法
	 * @param x 矩阵x x.length表示行数，x[0].length表示列数
	 * @param y 矩阵y
	 * @return 运算合法，返回结果，运算不合法抛出参数异常的错误
	 */
	public double[][] matrixMul(double[][] x, double[][] y) {
		double[][] result = new double[x.length][y[0].length];
		if (legalOperation(x, y , OPERATION_MUL)) {
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < y[0].length; j++) {
					double temp = 0;
					for(int m = 0; m < x[0].length; m++) {
						temp += x[i][m] * y[m][j];
					}
					result[i][j] = temp;
				}
			}
			return result;
		}
		throw new IllegalArgumentException("参数矩阵行列不等");
	}
	/**
	 * 
	 * @param x 矩阵x
	 * @return 返回x的转置
	 */
	public double[][] matrixTra(double[][] x) {
		double[][] result = new double[x[0].length][x.length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				result[j][i] = x[i][j];
			}
		}
		return result;
	}
	/**
	 * 求矩阵的逆
	 * @param x 矩阵x
	 * @return 符合规则返回result，参数不合法抛出参数异常的错误,参数不可逆抛出参数异常的错误
	 */
	public double[][] matrixConv(double[][] x) {
		if (x.length == x[0].length) {
			double[][] result = new double[x.length][x[0].length];
			//temp_matrix存放该矩阵和单位矩阵
			double[][] temp_matrix = new double[x.length][x.length * 2];
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					temp_matrix[i][j] = x[i][j];
				}
				//将单位矩阵放在左边
				for (int j = x[0].length; j < temp_matrix[0].length; j++) {
					if (j == i + x[0].length) {
						temp_matrix[i][j] = 1;
					} else {
						temp_matrix[i][j] = 0;
					}
				}
			}
			// 初等行变换
			for (int i=0; i < temp_matrix.length; i++) {
				if (temp_matrix[i][i] == 0) {
					for (int j = i+1; j < temp_matrix.length; j++) {
						if (temp_matrix[j][i] != 0) {
							double[] temp_vector = temp_matrix[i];
							temp_matrix[i] = temp_matrix[j];
							temp_matrix[j] = temp_vector;
							break;
						}
					}
				} 
				if (temp_matrix[i][i] == 0) {
					throw new IllegalArgumentException("矩阵不可逆");
				}
				double temp = temp_matrix[i][i];
				for (int j = 0; j < temp_matrix[0].length; j++) {
					temp_matrix[i][j] /= temp;
				}
				for (int j = 0; j < temp_matrix.length;j++) {
					temp = temp_matrix[j][i];
					for (int m = i; m < temp_matrix[0].length; m++) {
						if (j == i) continue;
						temp_matrix[j][m] -= temp * temp_matrix[i][m];
					}
				}
			}
			//赋值给result
			for (int i = 0; i < temp_matrix.length; i++) {
				for(int j = 0; j < temp_matrix.length; j++)
				result[i][j] = temp_matrix[i][j + temp_matrix.length];  
			} 
			return result;
		} else {
			throw new IllegalArgumentException("参数矩阵行列不等");
		}
	}
	public static void main(String[] args) {
		Matrix yunsuan = new Matrix();
		//double[][] x = {{1,2,3,4,5},{1,2,3,4,5}};
		double[][] y = {{1,2},{1,2},{1,2},{1,2},{1,2}};
		// double[][] z = yunsuan.matrixMul(x, y);
		// double[][] z = yunsuan.matrixTra(x);
		double[][] x = {{0,0},{0,0}};
		// double[][] z = yunsuan.matrixMul(x, yunsuan.matrixConv(x));
		double[][] z =  yunsuan.matrixConv(x);
//		double[][] z = new double[1][1];
//		z = new double[2][2];
		System.out.println(z);
		for(int i = 0; i < z.length; i++) {
			for(int j = 0; j <z[0].length; j++) {
				System.out.println(z[i][j]);
			}
		}
	}
}
