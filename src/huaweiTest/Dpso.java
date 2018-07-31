package huaweiTest;

import java.util.ArrayList;
public class Dpso {
	public ArrayList<String> optimize(double[][] x, int[][] vm,
		String[] vm_name, int phsicalServer_cpu_count, int phsicalServer_memory, String choose) {
		ArrayList<String> result = new ArrayList<String>();
		OurPso pso=new OurPso();
		double total = 0.0;
		double ECS_cpu = phsicalServer_cpu_count;
		double ECS_memory = phsicalServer_memory;
		double[] choose_array = new double[]{ECS_cpu, ECS_memory};
		int choose_index;
		if (choose.equals("CPU")) {
			choose_index = 0;
		} else {
			choose_index = 1;
		}
		System.out.println("choose_index:" + choose_index);
		double[] mount_input = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				total += x[i][j];
				mount_input[i] += x[i][j];
			}
		}
		
		double[][] vm_count = new double[vm.length][vm[0].length];
		for (int i = 0; i < vm_count.length; i++) {
			for (int j = 0; j < vm_count[0].length; j++) {
				vm_count[i][j] = vm[i][j];
			}
		}
		while (total != 0.0) {
			double cpu_sum = 0.0;
			double memory_sum = 0.0;
			for (int i = 0; i < mount_input.length; i++) {
				cpu_sum += (mount_input[i] * vm[0][i]);
				memory_sum += (mount_input[i] * vm[1][i]);
			}
			
			if (cpu_sum <= phsicalServer_cpu_count && memory_sum <= phsicalServer_memory) {
				String temp = "";
				for (int i = 0; i < mount_input.length; i++) {
					total = total - mount_input[i];
					while (mount_input[i] != 0) {
						temp += (vm_name[i] + " ");	
						mount_input[i] = mount_input[i] - 1;
					}	
				}
				result.add(temp);
				return result;
			} else {
				String temp = "";
				double[] ECS_array = new double[x.length];
				pso.init(30, mount_input, ECS_cpu, vm_count, ECS_memory, choose);
				ECS_array = pso.run(mount_input, ECS_cpu, vm_count, ECS_memory, choose);
				double global_best = pso.global_best;
				
				while (global_best == choose_array[choose_index]) {
					pso.init(40, mount_input, ECS_cpu, vm_count, ECS_memory, choose);
					ECS_array = pso.run(mount_input, ECS_cpu, vm_count, ECS_memory, choose);
					global_best = pso.global_best;					
				}
				for (int i = 0; i < ECS_array.length; i++) {
					mount_input[i] = mount_input[i] - ECS_array[i];
					total = total - ECS_array[i];
					while (ECS_array[i] != 0) {
						temp += (vm_name[i] + " ");
						ECS_array[i] = ECS_array[i] - 1;
					}
				}
				result.add(temp);
			}
		}
		return result;
	}
	public static void main(String[] args) {
		ArrayList<String> result = new ArrayList<String>();
		long a=System.currentTimeMillis();
		Dpso dpso= new Dpso();
		int[][] vm = new int[][]{{8,4,2,1,1},{1024,2048,1024,1024,1024}};
		String[] vm_name = new String[]{"flavor1","flavor2","flavor3","flavor4","flavor5"};
		int ECS_memory = 8 * 1024;
		int ECS_cpu = 23;
		double[][] x = new double[][]{{1.0,0.0,0.0,4.0,0.0,1.0,0.0},{0.0,2.0,0.0,1.0,0.0,1.0,1.0},{1.0,0.0,0.0,1.0,1.0,0.0,1.0},
			{0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
		result = dpso.optimize(x, vm, vm_name, ECS_cpu, ECS_memory, "neicun");
		System.out.println(result.size());
		for (int i = 0; i < result.size(); i++) {
			System.out.println("" + i + result.get(i));
		}
		//System.out.println("\r<br>Ö´ÐÐºÄÊ± : "+(System.currentTimeMillis()-a)/1000f+" Ãë ");
	}
}
