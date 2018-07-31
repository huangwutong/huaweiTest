
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Alaph{//Alaph��delta����һ��������һ��ʼ��ʱ��delta˼·���ˣ������Ͳ�����
    double pro;//���ڴ�Ÿ���
    int state;//���״ֵ̬
    public String toString(){
        return "pro:"+pro+" state:"+state;
    }
}

class Delta{
    public double pro;
    public int pos;
    public String toString(){
        return "pro is "+pro+" pos is "+pos;
    }
}

class Utils{
    public static ArrayList<ArrayList<Double>> loadMatrix(String filename) throws IOException{//��ȡ����
        ArrayList<ArrayList<Double>> dataSet=new ArrayList<ArrayList<Double>>();
        FileInputStream fis=new FileInputStream(filename);
        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
        BufferedReader br=new BufferedReader(isr);
        String line="";
        
        while((line=br.readLine())!=null){
            ArrayList<Double> data=new ArrayList<Double>();
            String[] s=line.split(" ");
            
            for(int i=0;i<s.length;i++){
                data.add(Double.parseDouble(s[i]));
            }
            dataSet.add(data);
        }
        return  dataSet;
    }
    
    public static ArrayList<Double> loadState(String filename)throws IOException{//��ȡ���ݣ�����������Ǹ�����
        FileInputStream fis=new FileInputStream(filename);
        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
        BufferedReader br=new BufferedReader(isr);
        String line="";
        ArrayList<Double> data=new ArrayList<Double>();
        while((line=br.readLine())!=null){
            
            String[] s=line.split(" ");
            
            for(int i=0;i<s.length;i++){
                data.add(Double.parseDouble(s[i]));
            }
            
        }
        return  data;
    }
    
    
    public static ArrayList<Double> getColData(ArrayList<ArrayList<Double>> A,int index){//����indexֵ����ȡ��Ӧ���е����ݣ���������ûʲô�õ���������
        ArrayList<Double> col=new ArrayList<Double>();
        for(int i=0;i<A.size();i++){
            col.add(A.get(i).get(index));
        }
        return col;
    }
    
    
    public static void showData(ArrayList<ArrayList<Double>> data){//debug��ʱ���õģ���ӡ
        for(ArrayList<Double> a:data){
            System.out.println(a);
        }
    }
    
    public static void showAlaph(ArrayList<Alaph> list){
        for(Alaph a:list){
            System.out.println(a);
        }
    }
    
    public static ArrayList<Alaph> copy(ArrayList<Alaph> list){//����
        ArrayList<Alaph> temp=new ArrayList<Alaph>();
        for(Alaph a:list){
            Alaph b=new Alaph();
            b.pro=a.pro;
            b.state=a.state;
            temp.add(b);
        }
        return temp;
    }
    
    public static Delta copyDelta(Delta src){//������һ����û��ʲô��
        Delta d=new Delta();
        d.pro=src.pro;
        d.pos=src.pos;
        return d;
    }
    
    public static ArrayList<Delta> copyDeltaList(Delta[] list){//����
        ArrayList<Delta> deltaList=new ArrayList<Delta>();
        for(Delta delta:list){
            Delta temp=copyDelta(delta);
            deltaList.add(temp);
        }
        return deltaList;
    }
    
    public static void showDeltaList(ArrayList<Delta> list){//debug
        for(Delta d:list){
            System.out.println(d);
        }
    }
    
    public static int getMaxIndex(ArrayList<Delta> list){//��list��ֵ�����±�
        double max=-1.0;
        int index=-1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).pro>max){
                max=list.get(i).pro;
                index=i;
            }
        }
        return index;
    }
    
}



public class HMM {
    public static ArrayList<Alaph> getInitAlaph(ArrayList<Double> initState,ArrayList<ArrayList<Double>> B,int index){//��һ����ʱ�����������״̬�µĳ�ʼ���
        ArrayList<Double> col=Utils.getColData(B,index);
        ArrayList<Alaph> alaphSet=new ArrayList<Alaph>();
        for(int i=0;i<col.size();i++){
            Alaph a=new Alaph();
            a.pro=col.get(i)*initState.get(i);//��ʼ���Ϊ��ʼ״̬*��Ӧ�Ĺ۲���ʾ����ֵ
            a.state=i;
            alaphSet.add(a);
        }
        return alaphSet;
    }
    public static ArrayList<Delta> getInitDelta(ArrayList<Double> initState,ArrayList<ArrayList<Double>> B,int index){//������һ��
        ArrayList<Double> col=Utils.getColData(B,index);
        ArrayList<Delta> alaphSet=new ArrayList<Delta>();
        for(int i=0;i<col.size();i++){
            Delta d=new Delta();
            d.pro=col.get(i)*initState.get(i);
            d.pos=i;
            alaphSet.add(d);
        }
        return alaphSet;
    }
    
    //���������ģ�ͺ͹۲��������󣬸�ģ���µĹ۲����г��ֵĸ���
    public static double calProb(ArrayList<ArrayList<Double>> A,ArrayList<ArrayList<Double>> B,ArrayList<Double> initState,String[] observe,Map<String,Integer> map){
      //observe��һ�������ݵ���ɫ��ʼ���ı��
    	int index=map.get(observe[0]);
        ArrayList<Alaph> alaphList=getInitAlaph(initState,B,index);//�����һ����״̬����
        for(int i=1;i<observe.length;i++){//�Ը����۲�ֵ�������
            String s=observe[i];
            int tag=map.get(s);
            ArrayList<Alaph> temp=Utils.copy(alaphList);
            for(Alaph alaph:alaphList){
                int destState=alaph.state;
                double pro=0;
                for(Alaph a:temp){
                    int srcState=a.state;
                    pro+=a.pro*A.get(srcState).get(destState);
                }
                pro=pro*B.get(destState).get(tag);
                alaph.pro=pro;
            }
        }
        double result=0;
        for(Alaph alaph:alaphList){
            result+=alaph.pro;
        }
        return result;
    }
    
    //���������ģ�ͺ͹۲������£������������Ե�״̬����
    public static void  decoding(ArrayList<ArrayList<Double>> A,ArrayList<ArrayList<Double>> B,ArrayList<Double> initState,String[] observe,Map<String,Integer> map){
        int index=map.get(observe[0]);
        
        ArrayList<Delta> deltaList=getInitDelta(initState,B,index);
        int length=B.size();
        Delta maxDeltaList[]=new Delta[B.size()];//���ڴ�Ÿ���״̬�µ������ʶ�Ӧ��deltaֵ
        ArrayList<ArrayList<Integer>> posList=new ArrayList<ArrayList<Integer>>();//���ڴ�Ÿ���״̬�µ����״ֵ̬
        
        for(int i=0;i<B.size();i++){
            ArrayList<Integer> a=new ArrayList<Integer>();
            a.add(i);
            posList.add(a);
        }
        
        for(int j=1;j<3;j++){
            ArrayList<Delta> maxList=new ArrayList<Delta>();
            String s=observe[j];
            int tag=map.get(s);
            for(int i=0;i<B.size();i++){
                Delta max=new Delta();
                double maxPro=-1.0;
                int maxPos=-1;
                int maxIndex=-1;
                for(int k=0;k<deltaList.size();k++){
                    Delta delta=deltaList.get(k);
                    double pro=delta.pro*A.get(delta.pos).get(i)*B.get(i).get(tag);
                    if(pro>maxPro){
                        maxPro=pro;
                        maxPos=i;
                        maxIndex=k;
                    }
                }
                max.pro=maxPro;
                max.pos=maxPos;
                maxDeltaList[i]=max;
                posList.get(i).add(maxIndex);
            }
            
            deltaList=Utils.copyDeltaList(maxDeltaList);
            System.out.println("  ");
        }
        
        System.out.println(posList.get(Utils.getMaxIndex(deltaList)));
        
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String dataA="C:/Users/Administrator/Desktop/upload/HMM/A.txt";
        String dataB="C:/Users/Administrator/Desktop/upload/HMM/B.txt";
        String state="C:/Users/Administrator/Desktop/upload/HMM/init.txt";
        ArrayList<ArrayList<Double>> A=Utils.loadMatrix(dataA);
        ArrayList<ArrayList<Double>> B=Utils.loadMatrix(dataB);
        ArrayList<Double> initState=Utils.loadState(state);
        String[] s={"Red","White","Red"};
        Map<String,Integer> map=new HashMap();
        map.put("Red",0);
        map.put("White",1);
        double pro=calProb(A,B,initState,s,map);
//        System.out.println("pro is "+pro);
        decoding(A,B,initState,s,map);
    }

}