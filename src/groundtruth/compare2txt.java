package groundtruth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njust.lib.Service.ApkInfoService;
import njust.lib.Service.ApkLibInfosService;
import njust.lib.Service.LibdetectionResultService;
import njust.lib.Service.LibpeckerResultService;
import cn.fudan.libpecker.main.aar2dex;
public class compare2txt {
	public static void main(String[] args) throws IOException{
		readlibsearchertxt(new File("D:\\LibSearcher源码\\LibSearcher日志\\LibSeacher日志 - 副本.txt"));
	}
	
	
	private static void FindDif2TxtContent(List<String> libList1,
			List<String> libList2) {
		int cou = 0;
		for (String a : libList1) {
			if (!libList2.contains(a)&a.contains("guava")) {
				System.out.println(a);
				cou++;
			}
		}
		System.out.println(cou);
	}


	private static void FindDifTxtContent(List<String> libList1,
			List<String> libList12, List<String> libList3) {	
		int cou=0;
		List<String> diflibdetect=new ArrayList<>();
		for (String a : libList1) {
			if (!libList12.contains(a)) {
				System.out.println(a);
				System.out.println("不存在");
				cou++;
				String bString=a.substring(a.lastIndexOf("---")+3);
				System.out.println(bString);
				if (!diflibdetect.contains(bString)) {
					System.out.println("sdd");
					diflibdetect.add(bString);
				}
				
			}
		}
		System.out.println(libList1.size());
		System.out.println(cou);
		System.out.println(diflibdetect.size());
		for (String string : diflibdetect) {
			if (string.contains("-")) {
				int count=0;
				double time=0;
				String qwString=string.substring(0, string.lastIndexOf("-"));
				//System.out.println(qwString);
				for (String as : libList3) {
					if (as.contains(qwString)) {
						//System.out.println(as);
						String aString=as.substring(as.lastIndexOf("*")+1);
						double time1=Double.parseDouble(aString);
						if (time1>0.40) {
							time+=time1;
							count++;
						}
					}
				}
				time=time/count;
				System.out.println(time);
				//System.out.println(count);
			}
		}
		//System.out.println("dsfhdsifhzu");
	}

	private static void FindSameTxtContent(List<String> LibList1,List<String> LibList2) {
		int count=0;
		Map<String,Integer> sameMap=new HashMap<>();
		List<String> sameapkList=new ArrayList<>();
		List<String> lackList=new ArrayList<>();
		List<String> sameList=new ArrayList<>();//file1中跟file2相同的字符串
		//List<String>result1=readTxt("G:\\LibPecker测试日志\\12.20我的快的1对340.txt");
				//List<String>result2=readTxt("G:\\LibPecker测试日志\\12.20libpecker1对340.txt");
				System.out.println("比对开始");
				for(String a:LibList2){
					if(LibList1.contains(a)){
						sameList.add(a);
					System.out.println(a);
					String apknameString=a.substring(0, a.indexOf("-"));
					if(!sameapkList.contains(apknameString))
						sameapkList.add(apknameString);
					
					count++;
					}

				}
				for(String a:sameapkList){
					int c=0;
					for(String b:sameList){
						if(b.contains(a))
							c++;
					}
					sameMap.put(a, c);			
				}
				
				for(String a:sameMap.keySet()){
					if(sameMap.get(a)!=10)
					System.out.println(a+sameMap.get(a));
					for(String b:LibList2){
						if (!LibList1.contains(b)) {
							lackList.add(b);		
							//System.out.println(b);
						}
					}
				}
				

				
				System.out.println("libdetect:"+LibList1.size());
				System.out.println("groundtruth:"+LibList2.size());
				System.out.println(count);
				System.out.println(sameMap.size());
		
	}

	public static List<String> readTxt(String filePath) {
		List<String>result1=new ArrayList<>();
		  try {
		    File file = new File(filePath);
		    if(file.isFile() && file.exists()) {
		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		      BufferedReader br = new BufferedReader(isr);
		      String lineTxt = null;
		      while ((lineTxt = br.readLine()) != null) {
		    	  if(lineTxt.contains("dex：similarity:")){
		    		  String resultString=lineTxt.substring(0, lineTxt.indexOf("：similarity:"));
				        System.out.println(resultString);	
				        result1.add(resultString);
					     // String libname=lineTxt.substring();  
		    	  }
		      }
		      br.close();
		    } else {
		      System.out.println("文件不存在!");
		    }
		  } catch (Exception e) {
		    System.out.println("文件读取错误!");
		  }
		return result1;
		 
		  }
	

	
	public static List<String> readtxt2(File file){
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//resultList.add(s.substring(0, s.lastIndexOf(".")));
            	if(s.contains("*")){
             	//System.out.println(s.substring(1, s.indexOf("*")));
            	resultList.add(s.substring(1, s.indexOf("*")));
            	}
            	else{
               	//System.out.println(s);
            	resultList.add(s);	
            	}

                //result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	public static List<String> readtxt3(File file){
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	resultList.add(s);	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	public static int readlibsearchertxt(File file){
		double time=0;
		int cnt=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	/**if (s.contains("模块解析time: ")) {
            		int temp=Integer.valueOf(s.substring(s.indexOf(":")+2));
            		if (temp<2000) {
                		cnt++;
                		time+=temp;
					}
				} else **/
            	//System.out.println(s);
            	if (s.contains("包结构匹配time")) {
					System.out.println(s.substring(s.indexOf("e")+3));
            		int temp=Integer.valueOf(s.substring(s.indexOf("e")+3));
            		if (temp<8000) {
                		cnt++;
                		time+=temp;
					}
				}     
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(time/cnt);
        System.out.println(cnt);
        return 0;
    }
}
