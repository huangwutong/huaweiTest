package huaweiTest;

public class OurPso {
		
	OurParticle[] OurPars;
	double global_best; // ȫ�����Ž�
	int pcount; // ��������
	int dims; //����ά��
	
	public void init(int n, double[] mount_input, double ECS_cpu, double[][] vm_count, double ECS_memory, String choose) {
		dims = mount_input.length;
		pcount = n;
		global_best = 500000000;
		int index = -1;
		OurPars = new OurParticle[pcount];
		// ��ľ�̬��Ա�ĳ�ʼ��
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
	 * ����Ⱥ������
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
				// ÿ�����Ӹ���λ�ú���Ӧֵ
				for (int i = 0; i < pcount; i++) {
					OurPars[i].updatev(mount_input, ECS_cpu, ECS_memory, choose);
					OurPars[i].evaluate(dims, ECS_cpu, vm_count, ECS_memory, choose);
					if (global_best > OurPars[i].fitness) {
						global_best = OurPars[i].fitness;
						index = i;
					} 
				}
				// ���ָ��õĽ�
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
     * ��ʾ��������� 
     */  
    public void showresult() {  
        System.out.println("������õ����Ž���" + global_best);  
        System.out.println("ÿһά��ֵ��");  
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
        System.out.println("������õ����Ž���" + pso.global_best);  
        System.out.println("ÿһά��ֵ��");  
//        for (int i = 0; i < result.length; i++) {  
//            System.out.print(result[i] + ",");  
//        }
        System.out.println();
        System.out.println("\r<br>ִ�к�ʱ : "+(System.currentTimeMillis()-a)/1000f+" �� ");
	}
}
