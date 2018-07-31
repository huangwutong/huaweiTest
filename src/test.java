import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class test {
	public static void readTxtFile(String filePath){
        try {
                String encoding="utf-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    
                    String[]arrStrings=null;
                    int a=0;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        //System.out.println(lineTxt);
                    	
                    	for (int i =a; i < lineTxt.length(); i++) {
                    		arrStrings=lineTxt.split(" ");
                    		 
						}
                    	 a=a+lineTxt.length()-1;
                    }
                    System.out.println(arrStrings[0]);
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
      
    }
    public static void main(String argv[]){
        String filePath = "D:\\input_5flavors_cpu_7days.txt";
        readTxtFile(filePath);
        
    }

    
}
