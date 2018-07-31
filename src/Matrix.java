import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import sun.net.www.content.audio.wav;

public class Matrix {
	
	public static void main(String[] args) throws ParseException {
		test2 t2 = new test2();
		Matrix rm = new Matrix();
		ArrayList<String> resultlist = new ArrayList<String>();
		resultlist = t2.handleDays(30, 1);
		int lenth = resultlist.size();
		String[] timObjects = new String[lenth];
		// �������������ӡ
		for (int i = 0; i < lenth; i++) {
			timObjects[i] = resultlist.get(i);
		}
		int row_number = 0;
		int column_number = 0;

		for (int i = 1; i < timObjects.length; i++) {
			row_number = i; // ���������
			column_number = timObjects.length - i - 1;
			float[][] x = create_Matrix(row_number, column_number); // ��������
			float[][] y = create_Matrix(row_number, 1);
			/*// ��������
			rm.getReverseMartrix(rm.create_Matrix(row_number, column_number));
			// ת��
			rm.transformMatrix(rm.create_Matrix(row_number, column_number));
			rm.plusMatrix(x, y);*/
			float[][]w=rm.plusMatrix(rm.plusMatrix(
					rm.getReverseMartrix(
							rm.plusMatrix(
									rm.transformMatrix(x), x)),
					rm.transformMatrix(x)),y);
			for (int j = 0; j < w.length; j++) {
				for (int j2 = 0; j2 < w[j].length; j2++) {
					System.out.println(w[j][j2]);
				}
			}
			
		}
		

	}

	// �������
	public float[][] plusMatrix(float[][] matrix1, float[][] matrix2) {
		// TODO Auto-generated method stub
		if (matrix1.length != matrix2[0].length) {// ���޷�������˳�
			System.out.println("ivalid input");
			System.exit(0);
		}

		float[][] r = new float[matrix1[0].length][matrix2.length];
		for (int i = 0; i < r.length; ++i) {
			for (int j = 0; j < r[i].length; ++j) {// ÿһ��r[i][j]�����㣺
				r[i][j] = 0;// ��ʼ��
				for (int k = 0; k < matrix2.length; ++k)
					r[i][j] += matrix1[i][k] * matrix2[k][j];
			}
		}
		// ������
		return r;
	}

	public float[][] getReverseMartrix(float[][] data) {
		float[][] newdata = new float[data.length][data[0].length];
		float A = getMartrixResult(data);
		System.out.println(A);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if ((i + j) % 2 == 0) {
					newdata[i][j] = getMartrixResult(getConfactor(data, i + 1, j + 1)) / A;
				} else {
					newdata[i][j] = -getMartrixResult(getConfactor(data, i + 1, j + 1)) / A;
				}

			}
		}
		newdata = trans(newdata);

		//showMaxtrix(newdata);
		return newdata;
	}

	private void showMaxtrix(float[][] newdata) {
		for (int i = 0; i < newdata.length; i++) {
			for (int j = 0; j < newdata[0].length; j++) {
				System.out.print(newdata[i][j] + "   ");
			}
			System.out.println();
		}
	}

	private float[][] trans(float[][] newdata) {
		// TODO Auto-generated method stub
		float[][] newdata2 = new float[newdata[0].length][newdata.length];
		for (int i = 0; i < newdata.length; i++)
			for (int j = 0; j < newdata[0].length; j++) {
				newdata2[j][i] = newdata[i][j];
			}
		return newdata2;
	}

	/*
	 * ��������ʽ��ֵ
	 */
	public float getMartrixResult(float[][] data) {
		/*
		 * ��ά�������
		 */
		if (data.length == 2) {
			return data[0][0] * data[1][1] - data[0][1] * data[1][0];
		}
		/*
		 * ��ά���ϵľ������
		 */
		float result = 0;
		int num = data.length;
		float[] nums = new float[num];
		for (int i = 0; i < data.length; i++) {
			if (i % 2 == 0) {
				nums[i] = data[0][i] * getMartrixResult(getConfactor(data, 1, i + 1));
			} else {
				nums[i] = -data[0][i] * getMartrixResult(getConfactor(data, 1, i + 1));
			}
		}
		for (int i = 0; i < data.length; i++) {
			result += nums[i];
		}

		// System.out.println(result);
		return result;
	}

	/*
	 * ��(h,v)�����λ�õ�����ʽ
	 */
	public float[][] getConfactor(float[][] data, int h, int v) {
		int H = data.length;
		int V = data[0].length;
		float[][] newdata = new float[H - 1][V - 1];
		for (int i = 0; i < newdata.length; i++) {
			if (i < h - 1) {
				for (int j = 0; j < newdata[i].length; j++) {
					if (j < v - 1) {
						newdata[i][j] = data[i][j];
					} else {
						newdata[i][j] = data[i][j + 1];
					}
				}
			} else {
				for (int j = 0; j < newdata[i].length; j++) {
					if (j < v - 1) {
						newdata[i][j] = data[i + 1][j];
					} else {
						newdata[i][j] = data[i + 1][j + 1];
					}
				}
			}
		}

		// for(int i=0; i<newdata.length; i ++)
		// for(int j=0; j<newdata[i].length; j++) {
		// System.out.println(newdata[i][j]);
		// }
		return newdata;
	}

	// ����һ��n�׾���A,����ӡA
	public static float[][] create_Matrix(int row_number, int column_number) {
		float[][] matrixA = new float[row_number][column_number];
		for (int i = 0; i <= row_number - 1; i++) {
			for (int j = 0; j <= column_number - 1; j++) {
				matrixA[i][j] = random_number();
				//matrixA[i][j] = {1,2,3,3,1,2,2,2,9};
				System.out.print(matrixA[i][j] + " ");
			}
			System.out.println();
		}
		return matrixA;
	}

	// �����������
	public static int random_number() {
		int number;
		number = new java.util.Random().nextInt(100) + 1;
		return number;
	}

	// ������ת��
	public static float[][] transformMatrix(float matrix[][])

	{
		float a[][] = new float[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				a[i][j] = matrix[j][i];
			}
		}
		return a;
	}

}
