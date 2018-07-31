package huaweiTest;

import java.util.Random;

public class OurParticle {
	public double[] pos; // 粒子的位置，求解问题多少维，则数组多少维
	public double[] v; // 粒子的速度，维数同位置
	public double fitness; // 粒子的适应度
	public double[] pbest; //粒子的历史最好位置
	public static double[] gbest; //所有粒子找到的最好位置
	public static Random rnd; //初始化位置时候的随机函数
	public static int dims; // 表示粒子的维数
	public static double w; // 惯性权重，调节对解空间的搜索能力
	// 初步推断本文中c1,c2取0.5-1之间比较好
	public static double c1; // 学习因子 一般为2
	public static double c2; // 学习因子一般为2
	double pbest_fitness;// 历史最优解
	double constraintConditionCoefficient; // 约束条件系数
	/**
	 * 返回low-uper之间的数
	 * @param low 下限
	 * @param uper 上限
	 * @return 返回low-uper之间的数
	 */
	// 粒子群的取法和初始值有很大关系，所以可以优化粒子群取法
	double[] rand(double[] mount_input) {
		double[] result = new double[mount_input.length];
		rnd = new Random();
		for (int i = 0; i < result.length; i++) {
			result[i] =  Math.round(rnd.nextDouble() * mount_input[i] / 2.0);
		}
		return result;
	} 
	double[] randv(double[] mount_input) {
		double[] result = new double[mount_input.length];
		rnd = new Random();
		for (int i = 0; i < result.length; i++) {
			result[i] =  Math.round(rnd.nextDouble() * mount_input[i] * 2 - mount_input[i]);
		}
		return result;
	} 
	double[] rand_optimize(double[] mount_input, int dim, double[][] vm_count, double array_choose_index, int choose_index) {
		double[] result = new double[mount_input.length];
		rnd = new Random();
		for (int i = 0; i < result.length; i++) {
			result[i] =  Math.round(rnd.nextDouble() * 2 + Math.round(array_choose_index / vm_count[choose_index][i] /dim));
			//System.out.println("result[i]: " + result[i]);
			if (result[i] > mount_input[i]) {
				//result[i] =  Math.round(rnd.nextDouble() * mount_input[i] / 2);
				result[i] =  Math.round(rnd.nextDouble() * mount_input[i]);
			}
		}
		return result;
	} 
	/**
	 * 初始化粒子
	 * @param dim 表示粒子的维数
	 */
	public void initial(int dim, double[]mount_input, double ECS_cpu, double[][] vm_count, double ECS_memory, String choose) {
		double[] choose_array = new double[]{ECS_cpu, ECS_memory};
		int choose_index;
		if (choose.equals("CPU")) {
			choose_index = 0;
		} else {
			choose_index = 1;
		}
		pos = new double[dim];
		v = new double[dim];
		pbest = new double[dim];
		//fitness = 50000;
		dims = dim;
		//pos = rand(mount_input);
		pos = rand_optimize(mount_input, dim, vm_count, choose_array[choose_index], choose_index);
		v = randv(mount_input);
		// sum:cpu， constraintSum:内存
		double sum = 0.0;
		for (int i = 0; i < dim; i++) {
			sum += vm_count[0][i] * pos[i];
		}
		double constraintSum = 0.0;
		for (int i = 0; i < dim; i++) {
			constraintSum += vm_count[1][i] * pos[i];
		}
		double[] sum_array = new double[]{sum, constraintSum};
		
		if (constraintSum > ECS_memory || sum > ECS_cpu) {
			pbest_fitness = 500000000;
			fitness = 500000000;
		} else {		
			pbest_fitness = choose_array[choose_index] - sum_array[choose_index];
			fitness = choose_array[choose_index] - sum_array[choose_index];
		}
	}
	/**
	 * 评估函数值，同时记录历史最优位置和历是最优目标函数值
	 */
	public void evaluate(int dim, double ECS_cpu, double[][]vm_count, double ECS_memory, String choose) {
		double[] choose_array = new double[]{ECS_cpu, ECS_memory};
		int choose_index;
		if (choose.equals("CPU")) {
			choose_index = 0;
		} else {
			choose_index = 1;
		}
		double sum = 0.0;
		for (int i = 0; i < dim; i++) {
			sum += vm_count[0][i] * pos[i];
		}
		double constraintSum = 0.0;
		for (int i = 0; i < dim; i++) {
			constraintSum += vm_count[1][i] * pos[i];
		}
		double[] sum_array = new double[]{sum, constraintSum};
		if (constraintSum > ECS_memory || sum > ECS_cpu) {
			fitness = 500000000;
		} else {
			fitness = choose_array[choose_index] - sum_array[choose_index];
		}
		if (fitness < pbest_fitness) {
			for (int i = 0; i < dims; i++) {
				pbest[i] = pos[i];
			}
			pbest_fitness = fitness;
		}
	}
	/**
	 * 更新速度和位置
	 * Random.NextDouble返回一个大于0小于1的随机浮点数
	 */
	public void updatev(double[] mount_input, double ECS_cpu, double ECS_memory, String choose) {
		double[] choose_array = new double[]{ECS_cpu, ECS_memory};
		int choose_index;
		if (choose.equals("CPU")) {
			choose_index = 0;
		} else {
			choose_index = 1;
		}
		if (fitness == 500000000) {
			for (int i = 0; i < pos.length; i++) {
				//System.out.println("wai:" + pos[i]);
				if (pos[i] > 0) {
					pos[i] = pos[i] - 1;
					//System.out.println(pos[i]);
				}
			}
		} else if (fitness == choose_array[choose_index]) {
			for (int i = 0; i < mount_input.length; i++) {
				if (mount_input[i] != 0) {
					pos[i] = pos[i] + 1;
				}
			}
		} else {
			for (int i = 0; i < dims; i++) {
				v[i] = w * v[i] + c1 * rnd.nextDouble() * (pbest[i] - pos[i]) 
						+ c2 * rnd.nextDouble() * (gbest[i] - pos[i]);
				if (v[i] > mount_input[i]) {
					v[i] = mount_input[i];
				}
				if (v[i] < -mount_input[i]) {
					v[i] = -mount_input[i];
				}
				pos[i] = Math.round(pos[i] + v[i]);
				if (pos[i] > mount_input[i]) {
					pos[i] = mount_input[i];
				}
				if (pos[i] < 0) {
					pos[i] = 0;
				}
			}
		}
	}
	public static void main(String[] args) {
		System.out.println(Math.round(0.5));
	}
}
