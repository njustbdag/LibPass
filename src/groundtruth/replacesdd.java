package groundtruth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class replacesdd {
	static Boolean flagBoolean;
	public static void main(String[] args) throws IOException{
		//extraLogas();
		//extraLogspecial();
		//createMD5bat();
		extrasss();
	}
	private static void extraLogas() throws IOException {
		File file = new File("H:\\实验基准\\q11.txt");     
	    BufferedReader reader1 = new BufferedReader(new FileReader(file)); 
	    String line;   
	    /**
	    /**
	     **/ while ((line = reader1.readLine()) != null){
	    	if (line.contains("*/*")) {
				//System.out.println(line);
				String apkString=line.substring(3, line.indexOf(".apk")+4);
				String MD5=line.substring(line.indexOf(".apk")+5);
				System.out.println(apkString+"="+apkString+","+MD5);
			}
	    }
	     
	   /** List<String> files = new ArrayList<String>();
	    while ((line = reader1.readLine()) != null){
	    	if (line.contains("keytool 错误:")&&!line.contains("H:")) {
				System.out.println("H:\\豌豆荚1\\"+line.substring(11)+"\\META-INF");
				files.addAll(getFiles("H:\\豌豆荚1\\"+line.substring(11)+"\\META-INF"));
			}
	    }
	    executeMD5(files);**/
	}
	
	private static void extrasss() throws IOException {
		TreeSet<String> apknameSet=new TreeSet<>();
		File file = new File("H:\\实验基准\\给老师\\基准ansi.txt");     
	    BufferedReader reader1 = new BufferedReader(new FileReader(file)); 
	    String line;   
	    /**
	    /**
	     **/ while ((line = reader1.readLine()) != null){
	    	 //System.out.println(line);
	    	 String[] aa=line.split(",");
	    	 if (!aa[1].contains(".")&&aa[1].length()==32) {		
	    	 apknameSet.add(aa[0]+":"+aa[1]);
			}else {
				apknameSet.add(aa[0]);
			}
	    	 
	    	 if (aa.length==4) {
	    		 //System.out.println(aa[0]+","+aa[1]+","+aa[3]);
			}else if (aa.length==3) {
				//System.out.println(line);
			}
	    }
	     for (String string : apknameSet) {
			System.out.println(string);
		}
	}
	
	private static void createMD5bat() throws IOException{
		List<String> files = new ArrayList<String>();
		for (String string : getFiles("H:\\带开源代码的apk",".apk")) {
			//System.out.println(string.substring(0,string.lastIndexOf(".apk")));
			files.addAll(getFiles(string.substring(0,string.lastIndexOf(".apk"))+"\\META-INF","RSA"));
		}
		for (String string : files) {
			System.out.println(string);
		}
		 executeMD5(files);
	}
	
    private static List<String> getFiles(String path,String type) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList!=null) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()&&tempList[i].toString().contains(type)) {//"RSA"		        
                    //文件名，不包含路径tempList[i].toString()
                	files.add(tempList[i].toString());
                	System.out.println(tempList[i].toString());
                }
                if (tempList[i].isDirectory()) {
                    //这里就不递归了，
                }
            } 
		}
        return files;
    }
    

	private static void executeMD5(List<String> apknameList) throws IOException {
		for(String jar:apknameList){
			System.out.println(jar);
			String dexfilename=jar.substring(jar.indexOf("的apk\\")+5, jar.indexOf("META")-1);
			//jar2dex.creatbatFile(dexfilename);
			System.out.println(jar.substring(jar.indexOf("的apk\\")+5, jar.indexOf("META")-1));			
	        /****/String batfilepath = jar2dex.creatbatFile(dexfilename);
			String batcontent="cd /d %~dp0\r\nkeytool -printcert -file "+jar;
			System.out.println(batfilepath);
			System.out.println(batcontent);
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        System.out.println(jar+"转dex已成功");
		}
 
	}
	public static void extraLogspecial() throws IOException {
		TreeSet<String> apsSet=new TreeSet<>();
		flagBoolean=false;
		Map<String, String> kpMap=new HashMap<String, String>();
		File file = new File("H:\\实验基准\\Q1.txt");   
	    File file2 = new File("H:\\实验基准\\33.txt");   
	    BufferedReader reader1 = new BufferedReader(new FileReader(file));   
	    BufferedReader reader2 = new BufferedReader(new FileReader(file2));  
	    String line;   
	    while ((line = reader1.readLine()) != null) { 
	    	kpMap.put(line.substring(0,line.indexOf("=")), line.substring(line.indexOf("=")+1)); 
	    	//System.out.println(line);
	    }
	    while ((line = reader2.readLine()) != null) {  
	    	flagBoolean=false;
		    for (String kString: kpMap.keySet()) {
				if(line.contains(kString)){
					flagBoolean=true;
					line.replace(kString, kpMap.get(kString));
			    	//System.out.println(kpMap.get(kString)+line.substring(line.indexOf(",")));
			    	apsSet.add(kpMap.get(kString)+line.substring(line.indexOf(",")));
				}
			}
		    if (!flagBoolean) {
				//System.out.println(line);
				apsSet.add(line);
			}
	    }
	    reader1.close();   
	    reader2.close();  
	    // 删除老文件   
	    //file.delete();   
	    //file2.renameTo(file); 
	    for (String string : apsSet) {
			System.out.println(string);
		}
	}
}
