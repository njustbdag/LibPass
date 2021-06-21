package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Statisticalversionsim {
	public static void main(String[] args) throws IOException {
		//Analysis("E:\\LibDetectʵ��groundtruth\\���н���汾֮��������\\wearable-2.1.0.dex.txt");
		Map<String, Double> statisMap=new HashMap<>();
		int cou1 = 0;
    	int cou2 = 0;
		int cou3 = 0;
    	int cou4 = 0;
		int cou5 = 0;
    	int cou6 = 0;
		int cou7 = 0;
    	int cou8 = 0;
		int cou9 = 0;
    	int cou10 = 0;
		List<Integer> statisList=new ArrayList<>();
		List<String> apknameList=findfile("E:\\LibDetectʵ��groundtruth\\���н���汾֮��������"); 
		for (String pathString : apknameList) {
			double a=Analysis(pathString);
			statisMap.put(pathString.substring(41), a);
		}
		for (String satring :statisMap.keySet()) {
			if (statisMap.get(satring)<0.1) {
				cou1++;
			}
			else if (statisMap.get(satring)<0.2) {
				cou2++;
			}
			else if (statisMap.get(satring)<0.3) {
				cou3++;
			}
			else if (statisMap.get(satring)<0.4) {
				cou4++;
			}
			else if (statisMap.get(satring)<0.5) {
				cou5++;
			}
			else if (statisMap.get(satring)<0.6) {
				cou6++;
			}
			else if (statisMap.get(satring)<0.7) {
				cou7++;
			}
			else if (statisMap.get(satring)<0.8) {
				cou8++;
			}
			else if (statisMap.get(satring)<0.9) {
				cou9++;
			}
			else {
				cou10++;
			}
			
		}
		System.out.println(cou1);
		System.out.println(cou2);
		System.out.println(cou3);
		System.out.println(cou4);
		System.out.println(cou5);
		System.out.println(cou6);
		System.out.println(cou7);
		System.out.println(cou8);
		System.out.println(cou9);
		System.out.println(cou10);		
		System.out.println(statisMap.size());
}
	
	
	
	private static double Analysis(String path) throws IOException {
	    double time=1;
		File file1 = new File(path);
		List<String> LibList=txt2String(file1);
	    int vercou=LibList.size();
		for(String libname:LibList){
				String aString=libname.substring(libname.lastIndexOf("*")+1);
				//System.out.println(aString);
				double time1=Double.parseDouble(aString);
				if (time>time1) {//ȡ��Сֵ
					time=time1;
				}
				//time+=time1;
			}
		//System.out.println(vercou);
		//time=time/vercou;
		System.out.println("�����"+time);
		return time;
		}	
    public static List<String> txt2String(File file){
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            	//resultList.add(s.substring(0, s.lastIndexOf(".")));
            	resultList.add(s);
                //result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	public static List<String> findfile(String path){//�ҵ�path��һ���ļ���Ŀ¼
		List<String> fileList=new ArrayList<>();
		System.out.println("=========ָ��Ŀ¼�µ������ļ�==========");
		File file = new File(path);
		File[] aa = file.listFiles();
		for (int i = 0; i < aa.length; i++) {
			if (aa[i].isFile()) {
				if(aa[i].toString().contains("txt")){
					//if(!aa[i].toString().contains("apktool.bat")){
	            System.out.println(aa[i].toString());
				fileList.add(aa[i].toString().replaceAll("\\\\", "\\\\\\\\"));
					//}
	
				}

			}
		}
		return fileList;
		
	}
 
}
