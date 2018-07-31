package test.java.huawei;

import java.util.ArrayList;   
import java.util.Collections;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;

public class Backpack {
	
	public ArrayList<String> optimize(double[][] x, int[][] vm, String[] vm_name, double[] weight, int phsicalServer_cpu_count, int phsicalServer_memory) {
		ArrayList<String> result = new ArrayList<String>();
		Map<String,Double> vm_name_count = new HashMap<String,Double>();
		Map<String,Double> vm_name_weight = new HashMap<String,Double>();
		Map<String,Double> vm_cpu_count = new HashMap<String,Double>();
		Map<String,Double> vm_size_count = new HashMap<String,Double>();
		double total = 0.0;
		for (int i = 0; i < x.length; i++) {
			double temp_vm_sum = 0.0;
			for (int j = 0; j < x[0].length; j++) {
				temp_vm_sum += x[i][j];
				total += x[i][j];
			}
			double cpu_count_temp = vm[0][i];
			double size_count_temp = vm[1][i];
			vm_cpu_count.put(vm_name[i], cpu_count_temp);
			vm_size_count.put(vm_name[i], size_count_temp);
			vm_name_count.put(vm_name[i], temp_vm_sum);
			vm_name_weight.put(vm_name[i], weight[i]);
		}
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(vm_name_weight.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
            //Ωµ–Ú≈≈–Ú  
            @Override  
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {  
                //return o1.getValue().compareTo(o2.getValue());  
                return o2.getValue().compareTo(o1.getValue());  
            }  
        });  
        double[] vm_count = new double[x.length];
        String[] vm_sort_name = new String[x.length];
        double[] vm_cpu = new double[x.length];
        double[] vm_size = new double[x.length];        
        int i = 0;
        for (Map.Entry<String, Double> mapping : list) {
        	vm_sort_name[i] = mapping.getKey();
        	vm_count[i] = vm_name_count.get(mapping.getKey());
        	vm_cpu[i] = vm_cpu_count.get(mapping.getKey());
        	vm_size[i] = vm_size_count.get(mapping.getKey());
        	i= i + 1;
            //System.out.println(mapping.getKey() + ":" + mapping.getValue());  
        }        
		while(total != 0) {
			String temp = "";
			double temp_cpu = phsicalServer_cpu_count;
			double temp_size = phsicalServer_memory;
			for (int j = 0; j < vm_count.length; j++) {
				while (vm_count[j] != 0) {
					if ((temp_cpu - vm_cpu[j]) >= 0 && (temp_size -vm_size[j]) >= 0) {
						vm_count[j] -= 1;
						total -= 1;
						temp += vm_sort_name[j] + " ";
						temp_cpu -= vm_cpu[j];
						temp_size -= vm_size[j];
					} else {
						if (j < vm_count.length - 1) {
							boolean flag = false;
							for (int m = j; m < vm_count.length; m++) {
								if ((temp_cpu - vm_cpu[m]) >= 0 && (temp_size -vm_size[m]) >= 0) {
									vm_count[m] -= 1;
									total -= 1;
									temp += vm_sort_name[m] + " ";
									temp_cpu -= vm_cpu[m];
									temp_size -= vm_size[m];
									flag = true;
									break;
								} else {
									continue;
								}
							}
							if (!flag) {
								temp_cpu = phsicalServer_cpu_count;
								temp_size = phsicalServer_memory;
								result.add(temp);
								temp = "";
							}
						} else {
							temp_cpu = phsicalServer_cpu_count;
							temp_size = phsicalServer_memory;
							result.add(temp);
							temp = "";
						}
						
					}
				}
			}
			result.add(temp);
		}
		return result;
	}
	public static void main(String[] args) {
		Map<String,Double> vm_name_weight = new HashMap<String,Double>();
		vm_name_weight.put("flavor1", 2.0);
		vm_name_weight.put("flavor2", 3.0);
		Map<String,Double> vm_name_count = new HashMap<String,Double>();
		vm_name_count.put("flavor1", 10.0);
		vm_name_count.put("flavor2", 5.0);
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(vm_name_weight.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
            //Ωµ–Ú≈≈–Ú  
            @Override  
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {  
                //return o1.getValue().compareTo(o2.getValue());  
                return o2.getValue().compareTo(o1.getValue());  
            }  
        });  
        double[] vm_count = new double[2];
        String[] vm_sort_name = new String[2];
        int i = 0;
        for (Map.Entry<String, Double> mapping : list) {
        	vm_sort_name[i] = mapping.getKey();
        	vm_count[i] = vm_name_count.get(mapping.getKey());
        	i =i + 1;
            System.out.println(mapping.getKey() + ":" + mapping.getValue());  
        }
        for (int j = 0; j < vm_count.length; j++) {
        	System.out.print(vm_sort_name[j]);
        	System.out.print(":");
        	System.out.println(vm_count[j]);
        }
	}
}
