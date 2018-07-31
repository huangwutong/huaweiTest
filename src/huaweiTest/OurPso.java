package huaweiTest;

public class OurPso {
		
	OurParticle[] OurPars;
	double global_best; // 全局最优解
	int pcount; // 粒子数量
	int dims; //粒子维数
	
	public void init(int n, double[] mount_input, double ECS_cpu, double[][] vm_count, double ECS_memory, String choose) {
		dims = mount_input.length;
		pcount = n;
		global_best = 500000000;
		int index = -1;
		OurPars = new OurParticle[pcount];
		// 类的静态成员的初始化
		OurParticle.c1 = 2;
		OurParticle.c2 = 2;
		OurParticle.w = 0.8;
		OurParticle.dims = dims;
		for (int i = 0; i < pcount; i++) {
			OurPars[i] = new OurParticle();
			OurPars[i].initial(dims, mount_input, ECS_cpu, vm_count, ECS_memory, choose);
			OurPars[i].evaluate(dims, ECS_cpu, vm_count, ECS_memory, choose);
			if (global_best > OurPars[i].fitness) {
				global_best = OurPars[i].fitness;
				index = i;
 			} 
		}
		if (index == -1) {
			index = 0;
		}
		OurParticle.gbest = new double[OurParticle.dims];
		for (int i = 0; i < OurParticle.dims; i++) {
			OurParticle.gbest[i] = OurPars[index].pos[i];
		}
	}
	/**
	 * 粒子群的运行
	 * @param args
	 */
	public double[] run(double[] mount_input, double ECS_cpu, double[][] vm_count, double ECS_memory, String choose) {
		double[] result = new double[dims];
		if (global_best == 0) {
			result = OurParticle.gbest;
		} else {
			int runtimes = 2000;
			int index;
			while (runtimes > 0 && global_best != 0) {
				index = -1;
				// 每个粒子更新位置和适应值
				for (int i = 0; i < pcount; i++) {
					OurPars[i].updatev(mount_input, ECS_cpu, ECS_memory, choose);
					OurPars[i].evaluate(dims, ECS_cpu, vm_count, ECS_memory, choose);
					if (global_best > OurPars[i].fitness) {
						global_best = OurPars[i].fitness;
						index = i;
					} 
				}
				// 发现更好的解
				if (index != -1) {
					for (int i = 0; i < dims; i++) {
						OurParticle.gbest[i] = OurPars[index].pos[i];
					}	
				}
				--runtimes;
			}
		}
		result = OurParticle.gbest;
		return result;
	}
    /** 
     * 显示程序求解结果 
     */  
    public void showresult() {  
        System.out.println("程序求得的最优解是" + global_best);  
        System.out.println("每一维的值是");  
        for (int i = 0; i < OurParticle.dims; i++) {  
            System.out.print(OurParticle.gbest[i] + ",");  
        }
        System.out.println();
    } 
	public static void main(String[] args) {
		double[] mount_input = new double[]{3,2,2,2,2,3,3,3};
		//double[] mount_input = new double[]{1,0,1,0,1,0,1,0};
		double[][] vm_count = new double[][]{{6,3,2,2,5,2,2,2},{1024,1024,1024,1024,1024,1024,1024,1024}};
		double ECS_memory = 150 * 1024;
		double ECS_cpu = 24;
		long a=System.currentTimeMillis();
        OurPso pso=new OurPso();  
        pso.init(30, mount_input, ECS_cpu, vm_count, ECS_memory, "CPU");
        pso.run(mount_input, ECS_cpu, vm_count, ECS_memory, "CPU");  
        System.out.println("程序求得的最优解是" + pso.global_best);  
        System.out.println("每一维的值是");  
//        for (int i = 0; i < result.length; i++) {  
//            System.out.print(result[i] + ",");  
//        }
        System.out.println();
        System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	}
}
