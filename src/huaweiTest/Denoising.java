package huaweiTest;
public class Denoising {
	public double[] dataDenoising(double[] x) {
		double mean = 0.0;
		double standard = 0.0;
		for (int i = 0; i < x.length; i++) {
			mean += x[i];
		}
		mean = mean / x.length;
		for (int i = 0; i < x.length; i++) {
			standard += Math.pow((x[i]-mean), 2); 
		}
		standard = Math.pow(standard / x.length,0.5);
		for (int i = 0; i < x.length; i++) {
			if (Math.abs((x[i] - mean)/standard) > 6.0) {
				if (i == 0) {
					x[i] = x[i+1];
				} else if (i > 0 && i < x.length -1) {
					x[i] = (x[i-1] + x[i+1]) / 2;
				} else {
					x[i] = x[i-1];
				}
			}
		}
		return x;
	}
}
