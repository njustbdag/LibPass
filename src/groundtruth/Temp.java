package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.metamodel.relational.Size;
import org.omg.CORBA.INTERNAL;

import njust.lib.Service.LibRootpackageInfoService;
import njust.lib.Service.PermissionClassnameService;
import njust.lib.dao.ApkRootpackageInfoDAO;
import njust.lib.dao.BaseDAO;
import njust.lib.dao.LibRootpackageInfoDAO;
import cn.fudan.libpecker.main.CreateBatFile;
import cn.fudan.libpecker.main.SupplementDetective;
import cn.fudan.libpecker.main.aar2dex;
import cn.fudan.libpecker.model.ApkPackageProfile;
import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.LibProfile;
import cn.njust.common.Apk;
import cn.njust.common.Lib;
import cn.njust.common.Sdk;
import edu.njust.bean.ApkRootpackageInfo;
import edu.njust.bean.LibRootpackageInfo;

public class Temp {
	//private static ApkRootpackageInfoDAO  ApkRootpackageInfoDAO = new  ApkRootpackageInfoDAO();
	//private static LibRootpackageInfoDAO  LibRootpackageInfoDAO = new  LibRootpackageInfoDAO();
	private static HashMap<String, String> apkHashMap=new HashMap<>(32768);
	private static Map<String,List<String>> matchResultMap=new HashMap<>();
	private static Map<String,List<String>> GTmatchResultMap=new HashMap<>();
	private static ArrayList<String> apkresultList=new ArrayList<>();
	private static HashSet<String> reHashSet=new HashSet<>();
	private static int count=0;
	public static void main(String[] args) throws Exception{
		int cnt=0;
		readground_truthproguard_obfuscatedtxt("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\ground_truth-proguard_obfuscated.txt");
		for (String key : GTmatchResultMap.keySet()) {
			System.out.println(key+":");
			for (String value : GTmatchResultMap.get(key)) {
				System.err.println(value);
			}
		}
		/**readtxt4("I:\\实验基准\\新基准\\apk与对应序号.txt");
		for (String string : apkHashMap.keySet()) {
			//System.out.println(string+":"+apkHashMap.get(string));
		}
		for (String string : readtxta2("I:\\实验基准\\新基准\\基准补充实验结果分析\\LibSearcher检测结果集\\已混淆结果.txt")) {
			//System.out.println(string);
			String[] soStrings=string.split(",");
			if (apkHashMap.keySet().contains(soStrings[0])) {
				//System.out.println(apkHashMap.get(soStrings[0])+"\\smali\\"+soStrings[1].replace(".", "\\"));
				String oldpathString="I:\\smali文件-豌豆荚\\"+apkHashMap.get(soStrings[0])+"\\smali\\"+soStrings[1].replace(".", "\\");
				System.out.println(oldpathString);
				String newPath="I:\\自制jar\\"+soStrings[2].replace(".", "\\");
				System.out.println(newPath+"\\"+soStrings[1].replace(".", "\\"));
				mkdir(newPath);
				copyDir(oldpathString,newPath);
				//copy(new File(oldpathString), new File(newPath));
			}else {
				//System.out.println(string);
				System.out.println(soStrings[0]+"\\smali\\"+soStrings[1].replace(".", "\\"));
			}
		}
		 ArrayList<String> files = getFiles("I:\\自制dex\\dex集合");
		for (String string : files) {
			if (string.contains("com.sina.weibo.sdk.log")) {
				System.out.println(string);
				 File file11=new File("I:\\com.sina.weibo.sdk\\"+string.substring(string.lastIndexOf("\\")));
					createFile(file11);
					copyFile(string, "I:\\com.sina.weibo.sdk\\"+string.substring(string.lastIndexOf("\\")));
			}
		}
		System.out.println(files.size());
		ArrayList<String> oArrayList=new ArrayList<>();
		oArrayList.add("cn.sharesdk.tencent");
		online(oArrayList);**/
	}
	
	
	 public static void mkdir(String path) {  
	        File fd = null;  
	        try {  
	            fd = new File(path);  
	            if (!fd.exists()) {  
	                fd.mkdirs();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            fd = null;  
	        }  
	    }  
	public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);        //文件名称列表
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }
            
            if (new File(oldPath  + file.separator + filePath[i]).isFile()) {
                copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }
            
        }
    }
	 protected static void copyFile(String oldPath, String newPath) throws IOException {
	        File oldFile = new File(oldPath);
	        File file = new File(newPath);
	        FileInputStream in = new FileInputStream(oldFile);
	        FileOutputStream out = new FileOutputStream(file);;

	        byte[] buffer=new byte[2097152];
	        int readByte = 0;
	        while((readByte = in.read(buffer)) != -1){
	            out.write(buffer, 0, readByte);
	        }
	    
	        in.close();
	        out.close();
	    }
	 private static void online(List<String> retestRootPackageList) throws Exception {
		//doneapknameList=getdoneFiles("E:\\groundtruth\\createdex\\dexfileset2");
			//s200shiyan();
			/**HashSet<String> sTreeSet=new HashSet<>();
			sTreeSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第二组14000个lib\\1.txt")); 
			sTreeSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第一组7000个lib\\1.txt"));
			//sTreeSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第三组21000个lib\\1.txt"));
			//sTreeSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第四组28000个lib\\1.txt"));
			//sTreeSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第五组35000个lib\\1.txt"));
			for (String string : sTreeSet) {
				System.out.println(string);
			}
			//for (String string : getFolders("I:\\实验基准\\新基准\\重新做")) 
			for (int i = 19; i < 20; i++) {
				for (String string1 : getFolders("I:\\实验基准\\新基准\\重新做s\\"+i)) {
					System.out.println(string1);
					reHashSet.clear();
					zancun(string1);
				}
			}**/
			//zancun("I:\\实验基准\\apk候选lib结果");
			/**for (int i = 1; i < 20; i++) {
				String pastring = "I:\\实验基准\\新基准\\重新做\\"+i;
				for (String string : getFolders(pastring)){
					System.out.println(string);
					System.out.println("合并***");
					ArrayList<String> mergepathArrayList=new ArrayList<>();
					for (int j = 1; j < i; j++) {
						mergepathArrayList.add("I:\\实验基准\\新基准\\重新做\\"+j+string.substring(string.lastIndexOf("\\")));
					}
					hebing(string, mergepathArrayList);
					System.out.println("合并&&&");
				}
				
			}**/
			//hebing();
			/**for (String string : getFolders("I:\\实验基准\\新基准\\重新做结果")) {
				count=0;
				for (String string1 : getFiles(string)) {
					System.out.println(string1);
					readtxt2(string1);
				}
				System.out.println(count/10);
			}

			for (String string : getFiles("I:\\实验基准\\新基准\\200组实验\\第十组70000个lib")) {
				readtxt2(string);
			}
			HashSet<String> reqHashSet=new HashSet<>();
			readtxt4("I:\\实验基准\\新基准\\apk与对应序号.txt");
			for (String pathString: getFiles("I:\\实验基准\\新基准\\200组实验\\第一组7000个lib\\1.1")) {
				
			} 
			for (int i = 1; i < 20; i++) {
				for (int j = 1; j < 11; j++) {
					//List<String> resultList=readtxt122("I:\\实验基准\\新基准\\重新做ssas\\"+i+"\\"+j+".txt");
				
				}
			}
			TreeSet<String> dfSet=new TreeSet<>();
			for (String result :  readtxt122("I:\\实验基准\\新基准\\基准补充实验结果分析\\LibSearcher检测结果集\\标准结果.txt")) {
				//System.out.println(result);
				String[] saStrings=result.split(",");
				if (!JudgeObfuscated(saStrings[saStrings.length-2])) {
					dfSet.add(saStrings[saStrings.length-2]);
				}
				}
			 HashMap<String, Integer> aRPHashMap=new HashMap<>();
			 int cnt=1;
			 String noeString = null;
			for (String result :  readtxt122("C:\\Users\\ZJY\\Desktop\\日志\\we.txt")) {
				if (result.contains("在线搜索")) {
					noeString=result.substring(result.lastIndexOf("包")+1,result.indexOf("在"));
					//System.out.println(result.substring(result.lastIndexOf("包")+1,result.indexOf("在")));
				}else {
					int i=Integer.valueOf(result.substring(result.lastIndexOf("到")+1,result.lastIndexOf("个")));
					//System.out.println(i);
					aRPHashMap.put(noeString, i);
				}
				}
			for (String string2 : aRPHashMap.keySet()) {
				System.out.println(string2+","+aRPHashMap.get(string2));
			}
			for (int i = 1; i < 20; i++) {
				for (int j = 1; j < 11; j++) {
					List<String> resultList=readtxt122("I:\\实验基准\\新基准\\200组实验结果\\"+i+"\\"+"1."+j+".txt");
					 File file11=new File("I:\\实验基准\\新基准\\200组实验结果\\"+i+"\\"+j+"online.txt");
					 deleteFile("I:\\实验基准\\新基准\\200组实验结果\\"+i+"\\"+j+"online.txt");
						createFile(file11); 
						 PrintWriter writer = new PrintWriter(file11); 
				for (String string : resultList) {
					if (aRPHashMap.keySet().contains(string.substring(string.indexOf(",")+1))&&aRPHashMap.get(string.substring(string.indexOf(",")+1))>0) {
						System.out.println(string);
						writer.println(string);   
					      writer.flush();
					}
					
				}
				writer.close();
				}
			}
			//List<String> tpList=readtxt122("I:\\实验基准\\新基准\\基准补充实验结果分析\\基准集\\只带ARP的基准.txt");
			for (int i = 1; i < 20; i++) {
				int cnt=0;
				for (int j = 1; j < 11; j++) {
					List<String> resultList=readtxt122("I:\\实验基准\\新基准\\200组实验结果\\"+i+"\\"+j+"tp.txt");
					cnt+=resultList.size();
				}
				System.out.println(cnt/10);
			}***/ 
		 downloadjar downLoadjar=new downloadjar(retestRootPackageList);
		 HashMap<String, Integer> aRPHashMap=downLoadjar.CountDownload();
		 for (String string : aRPHashMap.keySet()) {
			System.out.println(string+aRPHashMap.get(string));
		}
	    	//SupplementDetective supplementDetective=new SupplementDetective();
	    	//IntegrityfinalMatchedrootpkg.putAll(supplementDetective.start(apkPath, newdexPath));*/
	    	//System.out.println("***************************现在开始完整性检测*****************************");
	    	/**for (String astring : IntegrityfinalMatchedrootpkg.keySet()) {
				System.out.println(astring+"---------"+IntegrityfinalMatchedrootpkg.get(astring));
				System.out.println();
			}**/
	    	//Integrityquickmatch integrityquickmatch=new Integrityquickmatch(apkPath,apkProfile,IntegrityfinalMatchedrootpkg);
	    	//integrityquickmatch.IntegrityAnalysis();;
		
	}

	private static void aso(Integer yu,int we) throws IOException {
		 File file11=new File("I:\\实验基准\\新基准\\重新做ssas\\"+yu+"\\"+we+".txt");
			createFile(file11); 
			 PrintWriter writer = new PrintWriter(file11); 
		 HashMap<String, ArrayList<String>> sdHashMap=new HashMap<>();
		 for (String result :  readtxt122("I:\\实验基准\\新基准\\基准补充实验结果分析\\LibSearcher检测结果集\\标准结果.txt")) {
			//System.out.println(result);  
			String[] saStrings=result.split(",");
			if (!sdHashMap.keySet().contains(saStrings[0])) {
				sdHashMap.put(saStrings[0], new ArrayList<String>());
			}
			sdHashMap.get(saStrings[0]).add(saStrings[saStrings.length-2]);
		}
		 for (String pathString: getFiles("I:\\实验基准\\结果second未混淆ARP")) {
				//String pathString="I:\\实验基准\\结果second未混淆ARP\\3d_drawing_v2.0.1_apkpure.com.apk.txt";
				if (!pathString.contains("null")) {
					String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
				readtxt5("I:\\实验基准\\新基准\\重新做s\\"+yu+"\\"+we+"\\"+apkNameString+".txt");
			   /** for (String root : matchResultMap.keySet()) {
					System.out.println(root);
					System.out.println(matchResultMap.get(root).size());
				}**/	 
				//System.out.println(apkNameString);
				
					Set<String> arpSet=new HashSet<>();
					if (apkHashMap.get(apkNameString)!=null) {
						apkNameString=apkHashMap.get(apkNameString);
					}
				
				for (String RTString : sdHashMap.get(apkNameString)) {
					//System.out.println(RTString);
					if (matchResultMap.get(RTString)!=null&&matchResultMap.get(RTString).size()>6) {
						System.out.println(apkNameString+","+RTString);
						writer.println(apkNameString+","+RTString);   
					      writer.flush();
					}
					
				}
				}
		 }
		 writer.close();
		 }
		

	private static void hebing(String path, ArrayList<String> mergepathArrayList) throws FileNotFoundException {
				for (String string1 : getFiles(path)) {
					System.out.println(string1.substring(string1.lastIndexOf("\\")));
					TreeSet<String> sTreeSet=new TreeSet<>();
					sTreeSet.addAll(readtxt2(string1));
					for (String string2 : mergepathArrayList) {
						sTreeSet.addAll(readtxt2(string2+string1.substring(string1.lastIndexOf("\\"))));
					}
					//deleteFile(string1);
					File file11=new File(string1);
					createFile(file11); 
					PrintWriter writer = new PrintWriter(file11);
					for (String string : sTreeSet) {
						//System.out.println(string);
						writer.println(string);   
					      writer.flush();
					}
					writer.close();
				}		
	}

	private static void zancun(String pathString1) throws IOException {
		// TODO Auto-generated method stub
			File file11=new File(pathString1.replace("apk候选lib结果", "重新做结果")+".txt");
			createFile(file11); 
			 PrintWriter writer = new PrintWriter(file11); 
		 HashSet<String> reqHashSet=new HashSet<>();
			readtxt4("I:\\实验基准\\新基准\\apk与对应序号.txt");
			for (String pathString: getFiles("I:\\实验基准\\结果second未混淆ARP")) {
				//String pathString="I:\\实验基准\\结果second未混淆ARP\\3d_drawing_v2.0.1_apkpure.com.apk.txt";
				String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
				System.out.println(apkNameString);
				if (!pathString.contains("null")) {
					Set<String> arpSet=new HashSet<>();
					readtxt5(pathString1+"\\"+apkNameString+".txt");
					if (apkHashMap.get(apkNameString)!=null) {
						apkNameString=apkHashMap.get(apkNameString);
					}
					 for (String line : readtxt1(pathString)) {
						// if (line.contains("根包:")) {
					    		//cou++;
								//arpSet.add(apkNameString+line.substring(line.indexOf(":")+1));
								//System.out.println(apkNameString+","+line.substring(line.indexOf(":")+1));
					    	//}
						//System.out.println(line);
						String MSString=line.substring(line.indexOf("*----MS----*")+12,line.indexOf("*----IM----*"));
						String RTString=line.substring(line.indexOf("*----RT----*")+12,line.indexOf("*----LIB----*"));
						String libString=line.substring(line.indexOf("*----LIB----*")+13,line.indexOf("*----MS----*"));
						String libnameString=libString.substring(0, libString.lastIndexOf(".dex"));
						double MS=Double.parseDouble(MSString);
						if (libnameString.contains(".apk.")) {
							//System.out.println(libnameString.substring(0, libnameString.lastIndexOf(".apk.")+4));
							/****/if (matchResultMap.get(RTString)==null){
								MS=0; 
							}else  if (matchResultMap.get(RTString).size()<2&&apkHashMap.get(libnameString.substring(0, libnameString.lastIndexOf(".apk.")+4))!=null&&apkHashMap.get(libnameString.substring(0, libnameString.lastIndexOf(".apk.")+4)).equals(apkNameString)) {
								MS=0;
								System.out.println("ERROR:"+apkNameString+","+RTString+","+libnameString);
							}
							libnameString=libnameString.substring(libnameString.lastIndexOf(".apk.")+5);
							libnameString=libnameString.replace(".", "-");
							
						}
						if (MS>0.6&&!reqHashSet.contains(apkNameString+","+RTString)) {//&&!JudgeObfuscated(RTString)
							//if (matchResultMap.get(RTString)!=null&&matchResultMap.get(RTString).size()>1) {
								reHashSet.add(apkNameString+","+RTString+","+libnameString);//+"---"+MS+"-----"+matchResultMap.get(RTString).size()
								reqHashSet.add(apkNameString+","+RTString);
							//}
							System.out.println(apkNameString+","+RTString+","+libnameString);
						}
					}
				}

			}
			TreeSet<String> sdSet=new TreeSet<>();
			sdSet.addAll(reHashSet);
			for (String string : sdSet) {
				//System.out.println(string);
				writer.println(string);   
			      writer.flush();
			}
			writer.close();
	}

	private static void readtxt5(String filepath) throws IOException {
		 File file = new File(filepath);
		 matchResultMap.clear();
	            BufferedReader reader = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String line = null;
	            while((line = reader.readLine())!=null){//使用readLine方法，一次读一行
			    	if (!matchResultMap.keySet().contains(line.substring(0, line.indexOf("-")))) {
			    		matchResultMap.put(line.substring(0, line.indexOf("-")), new ArrayList<String>());
					}
			    	//System.out.println(line.substring(0, line.indexOf("-")));
			    	matchResultMap.get(line.substring(0, line.indexOf("-"))).add(line.substring(line.lastIndexOf("-----")+5));
			    	//System.out.println(line.substring(line.lastIndexOf("-----")+5));
			    }
			    reader.close();  
			    for (String root : matchResultMap.keySet()) {
					//System.out.println(filepath+root);
					//System.out.println(matchResultMap.get(root).size());
				}	 
	        }


	private static void readtxt4(String filepath) {
		 File file = new File(filepath);
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	//if (!resultList.contains(s)) {
	            	if (s.contains("=")) {
	            		apkHashMap.put(s.substring(s.indexOf("=")+1), s.substring(0, s.indexOf("=")));
	            		//System.out.println(s.substring(0, s.indexOf("=")));
	            		//System.out.println(s.substring(s.indexOf("=")+1));
	            		//System.out.println(apkHashMap);
					}
					//}	           
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
	}
	public static void readground_truthproguard_obfuscatedtxt(String filepath) {
		 File file = new File(filepath);
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	//if (!resultList.contains(s)) {
	            	if (s.contains("apk:")) {
	            		//System.out.println(s.substring(0, s.indexOf("=")));
	            		String apknameString=s.substring(0,s.indexOf(":"));
	            		s=s.substring(s.indexOf(":")+1);
	            		String[] strings=s.split(",");
	            		for (String string : strings) {
							System.out.println(string);
						}
	            		//System.out.println(apkHashMap);
	            		GTmatchResultMap.put(apknameString, Arrays.asList(strings));
					}
					//}	           
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
	}
	private static void s200shiyan() throws FileNotFoundException {
		// TODO Auto-generated method stub
		 List<String> folders =getFolders("I:\\实验基准\\新基准\\重新做");
		 List<String> filess=getFiles("I:\\实验基准\\apk候选lib结果");
		 for (String folder : folders) {
			 System.out.println(folder);
				for (String file : getFiles(folder)) {
					List<String> dex =readtxt2(file); 
					String filenameString=file.substring(file.lastIndexOf("\\")+1, file.lastIndexOf("."));
					System.out.println(filenameString);
					for (String ss : filess) {
						String filessnameString=ss.substring(ss.lastIndexOf("\\")+1);
						File file11=new File(folder+"\\"+filenameString+"\\"+filessnameString);
						createFile(file11);
						System.out.println(folder+"\\"+filenameString+"\\"+filessnameString);
						 PrintWriter writer = new PrintWriter(file11); 
						if(!file11.exists()){//如果文件夹不存在
							file11.mkdir();//创建文件夹
						}
						List<String> as =readtxt2(ss);
						System.out.println(dex);
						for (String string : as) {
							System.out.println(string.substring(string.lastIndexOf("-")+1));
							if (dex.contains(string.substring(string.lastIndexOf("-")+1))) {
							      writer.println(string);   
							      writer.flush();
							}
						}
						writer.close();
					}
				}
		}
		
		/**apkresultList=getFiles("I:\\自制dex\\dex集合");
		for (int j = 111; j < 121; j++) {
			Random r = new Random(j);
			HashSet<String> ranHashSet=new HashSet<>();
			HashSet<String> doneranHashSet=new HashSet<>();
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十九组133000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十八组126000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十七组119000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十六组112000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十五组105000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十四组98000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十三组91000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十二组84000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十一组77000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第十组70000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第九组63000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第八组56000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第七组49000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第六组42000个lib\\"+(j-110)+".txt"));
			doneranHashSet.addAll(readtxt2("I:\\实验基准\\新基准\\重新做\\第一组7000个lib\\"+(j-110)+".txt"));
		    File file2 = new File("I:\\实验基准\\新基准\\重新做\\第二组14000个lib\\"+(j-110)+".txt");   
			createFile(file2);
			 PrintWriter writer = new PrintWriter(file2); 
			if(!file2.exists()){//如果文件夹不存在
				file2.mkdir();//创建文件夹
			}
			for (int i = 0; i < 139569; i++) {
				int ran1 = r.nextInt(139569);
				//System.out.println(ran1);
				ranHashSet.add(apkresultList.get(ran1));
			}
			int cnt=0;
			for (String string : ranHashSet) {
				if (cnt!=7000&&!doneranHashSet.contains(string)) {
				      writer.println(string);   
				      writer.flush();
					System.out.println(string);
					cnt++;
				}
			}
			System.out.println(ranHashSet.size());
			ranHashSet=null;
			writer.close();
		}**/
	}
	
	public static void copy(File file, File toFile) throws Exception {
		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath=filepath.replaceAll("\\\\", "/");
			//System.out.println(filepath);
			String toFilepath = toFile.getAbsolutePath();
			toFilepath=toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
			//System.out.println(toFilepath);
			File copy=new File(toFilepath);
			//复制文件夹
			if (!copy.exists()) {
				copy.mkdir();
			}
			//遍历文件夹
			for (File f : file.listFiles()) {
				copy(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath=filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath=toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
				
				//写文件
				File newFile = new File(toFilepath);
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
				} catch (Exception e) {
					throw new Exception(e);
				}
				

			} else {
				//写文件
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			}
 
		}
	}

	private static void createFile(File file) {
	        if (file.exists() && file.isFile()) {
	            file.delete();
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return;
	        }
	        File parentFile = file.getParentFile();
	        if (parentFile.exists()) {
	            if (parentFile.isFile()) {
	                parentFile.delete();
	                parentFile.mkdirs();
	            }
	        } else {
	            parentFile.mkdirs();
	        }
	        try {
	            file.createNewFile();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	public static void temp() throws Exception{
		/**deleteFile("E:\\groundtruth\\新增dex\\test\\aa.txt");
		List<String> dexfilepath=new ArrayList<String>();
		List<String> needdeldexfilepath=new ArrayList<String>();
		dexfilepath.addAll(traverseFolder("E:\\groundtruth\\新增dex\\test"));
		for (String dexfile : dexfilepath) {
			System.out.println(dexfile);
			String resourceSizeMb=getFileSize(dexfile);
		    if (resourceSizeMb.equals("0KB")) {
		    	needdeldexfilepath.add(dexfile);
		    	 System.out.println("该文件大小为0KB,异常删除！");
		    	 //String path1=path.replace("\\","\\\\");
			}
		}
		for (String dex : needdeldexfilepath) {
			dex=dex.replace("\\","\\\\");
			deleteFile(dex);
		}
		deleteFile("E:\\\\groundtruth\\\\新增dex\\\\test\\\\collectd-fast-jmx-1.0.0.dex");
		deleteFile("E:\\groundtruth\\新增dex\\test\\aa.txt");**/
		//findclass();
		//jaroraartodex(jaraarpathList);
		//findmissinglib("C:\\Users\\ZJY\\Desktop\\a.txt");
		HashMap<String, List<String>> groundtruthHashMap=new HashMap<>();
		HashSet<String> libusedHashSet=new HashSet<>();
		List<String> poList=new ArrayList<>(300);
		List<String> usefulapkList=new ArrayList<>(300);
		List<String> groulibList=new ArrayList<>();
		List<String> a=getFiles("E:\\libdetection论文\\LibSearcher论文\\实验\\补上的67个apk运行结果");
		List<String> b=readtxt1("E:\\libdetection论文\\LibSearcher论文\\实验\\补上的67个groundtruth.txt");
		for (String astring : b) {
			String apknameString=astring.substring(0,astring.indexOf("-"));
			System.out.println(apknameString);
			String libString =astring.substring(astring.lastIndexOf("---")+3);
			System.out.println(libString);
			if (!groundtruthHashMap.keySet().contains(apknameString)) {
				groundtruthHashMap.put(apknameString, new ArrayList<String>());
			}
			groundtruthHashMap.get(apknameString).add(libString);
		}
		float avgTime=0;
		double ave=0;
		int acount=0;
		int bcount=0;
		List<Integer> timeIntegers=new ArrayList<>(300);
		for (String string : a) {
			String apknameString=string.substring(string.lastIndexOf("\\")+1,string.indexOf(".apk"));
			int count=0;
			List<String> templibList=new ArrayList<>();
			for (String tep: readtxt1(string)) {
				String lib=tep.substring(tep.indexOf("---")+3);
				if (groundtruthHashMap.get(apknameString)!=null&&groundtruthHashMap.get(apknameString).contains(lib)&&!templibList.contains(apknameString+"-----"+lib)) {
					System.out.println(apknameString+"-----"+lib);
					templibList.add(apknameString+"-----"+lib);
					groulibList.add(apknameString+".apk-----"+lib);
					libusedHashSet.add(lib);
					count++;
				}
			}
			if (groundtruthHashMap.get(apknameString)!=null&&count!=0){
				List<String> tempgroList=new ArrayList<>();
				System.out.println(count);
				System.out.println(groundtruthHashMap.get(apknameString).size());
				acount+=count;
				bcount+=groundtruthHashMap.get(apknameString).size();
				double s=(double)count/groundtruthHashMap.get(apknameString).size();
				System.out.println(s);
				if (s<1) {
					System.out.println("缺少");
					for (String lib : groundtruthHashMap.get(apknameString)) {
						if (!templibList.contains(apknameString+"-----"+lib)) {
							System.out.println(apknameString+"-----"+lib);
							groulibList.add(apknameString+".apk-----"+lib);
						}
					}
					System.out.println("完毕");
					poList.add(apknameString);
				}
				if (s>0.8) {
					System.out.println(s);
					System.out.println("添加");
					usefulapkList.add(apknameString);
				}
			}
		}
		for (String po:poList) {
			System.out.println(po);
			//File file=new File("E:\\LibDetect实验groundtruth\\反编译后的apk\\"+po+".apk");
			//File toFile=new File("E:\\libdetection论文\\LibSearcher论文\\实验\\成功115个自定义apk\\"+po+".apk");
			//CopylibToapk.copy(file, toFile);
		}
		for (String ap : libusedHashSet) {
			System.out.println(ap);
		}
		System.out.println("libusedHashSet:"+libusedHashSet.size());
		System.out.println("acount"+acount);  
		System.out.println("bcount"+bcount);
		double s=(double)acount/bcount;
		System.out.println("s"+s);
		System.out.println(poList.size());
		System.out.println(timeIntegers.size());
		System.out.println(avgTime);
		System.out.println(avgTime/300);
		//List<String> qw=getFiles("G:\\libdetection实验标准采集\\dexlib\\GoogletplsUtilities");
		System.out.println("输出usefulapkList：");
		for (String usefulapk : usefulapkList) {
			System.out.println(usefulapk);
			//File file=new File("E:\\libdetection论文\\LibSearcher论文\\实验\\第二版运行结果\\"+usefulapk+".apk.txt");
			//File toFile=new File("E:\\libdetection论文\\LibSearcher论文\\实验\\成功233个自定义apk第二版运行结果\\"+usefulapk+".apk.txt");
			//CopylibToapk.copy(file, toFile);
			System.out.println("复制成功");
		}
		System.out.println(usefulapkList.size());
		System.out.println("groulibList开始");
		for (String groulib : groulibList) {
			System.out.println(groulib);
		}
		System.out.println(groulibList.size());
	}
	
  protected static ArrayList<String> getFiles(String path) {
	  ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles(); 
       // System.out.println(tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {	//&&tempList[i].toString().contains("apk")	        
                //文件名，不包含路径tempList[i].toString()
            	//System.out.println(tempList[i].toString());
            	//System.out.println(tempList[i].toString().substring(tempList[i].toString().indexOf(".apk")+5,tempList[i].toString().lastIndexOf(".dex")));
            	//if(JudgeObfuscated(tempList[i].toString().substring(tempList[i].toString().indexOf(".apk")+5,tempList[i].toString().lastIndexOf(".dex")))){
            		//System.out.println(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")));
            		files.add(tempList[i].toString());
            		//deleteFile(tempList[i].toString());
            	//}
            		
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        } 
        return files;
    }
  
  private  static int random(int min ,int max){
	  long randomNum = System.currentTimeMillis();  
	  int ran3 = (int) (randomNum%(max-min)+min);  
	  //System.out.println(apkresultList.get(ran3));
	  return ran3;
  }
  
  
  private  static List<String> getdoneFiles(String path) {
      List<String> files = new ArrayList<String>();
      File file = new File(path);
      File[] tempList = file.listFiles();

      for (int i = 0; i < tempList.length; i++) {
          if (tempList[i].isFile()&&tempList[i].toString().contains("dex")) {		        
              //文件名，不包含路径tempList[i].toString()
          	files.add(tempList[i].toString().substring(37,tempList[i].toString().lastIndexOf(".")));
          	//System.out.println(tempList[i].toString().substring(37,tempList[i].toString().lastIndexOf(".")));
          }
          if (tempList[i].isDirectory()) {
              //这里就不递归了，
          }
      } 
      return files;
  }
  
  public static List<String> getFolders(String path) {
      List<String> files = new ArrayList<String>();
      File file = new File(path);
      File[] tempList = file.listFiles();

      for (int i = 0; i < tempList.length; i++) {
          if (tempList[i].isFile()&&tempList[i].toString().contains("txt")) {		        
              //文件名，不包含路径tempList[i].toString()

          }
          if (tempList[i].isDirectory()) {
              //这里就不递归了，
              	//if (Integer.parseInt(tempList[i].toString().substring(15,20))>35037) {
              		files.add(tempList[i].toString().replace("\\", "\\\\"));
              		//System.out.println(tempList[i].toString());
				//}
          }
      } 
      return files;
  }
	
	private static void findmissinglib(String txtPathString) {
		List<String> apkpkg=new ArrayList<>();
		Map<String, Integer> apkRootpackageInfomMap=new HashMap<>();
		/**int i=25633;
			String hql = " from  ApkRootpackageInfo where id<'"+i+"'";
			List<ApkRootpackageInfo>  Apk =  ApkRootpackageInfoDAO.findByHql(hql);
			for (ApkRootpackageInfo apkRootpackageInfo : Apk) {
				if (!apkpkg.contains(apkRootpackageInfo.getApkRootpackagename())) {
					apkpkg.add(apkRootpackageInfo.getApkRootpackagename());
				}
			}
			for (String apkRootpackage : apkpkg) {
				int count=0; 
			  for (ApkRootpackageInfo apkRootpackageInfo : Apk) {
				if (apkRootpackage.equals(apkRootpackageInfo.getApkRootpackagename())) {
					count++;
				}
			}	
			  apkRootpackageInfomMap.put(apkRootpackage, count);
			}
			
			for (String apkRootpackage:apkRootpackageInfomMap.keySet()) {
				if (!JudgeObfuscated(apkRootpackage)) {
					System.out.println(apkRootpackage+":"+apkRootpackageInfomMap.get(apkRootpackage));
				}
				//+":"+apkRootpackageInfomMap.get(apkRootpackage)
			}**/

		 List<String> resultList= readtxt3(txtPathString);
		 for (String PKGstring : resultList) {
			 //System.out.println(PKGstring);
			 List<String> hash=new ArrayList<>();
				String hql = " from  LibRootpackageInfo where libRootpackagename='"+PKGstring+"'";
				/**List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql);
				/**String hql2 = " from  LibRootpackageInfo where libRootpackagename='"+PKGstring+"'";
				List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql2);
				if (Lib.size()==0) {
					System.out.println(PKGstring);
				}
				if (Lib.size()==0) {
					String hql1 = " from  ApkRootpackageInfo where apkRootpackagename='"+PKGstring+"'";
					List<ApkRootpackageInfo>  Apk =  ApkRootpackageInfoDAO.findByHql(hql1);
					for (ApkRootpackageInfo apkRootpackageInfo : Apk) {
						System.out.println("开始填充");
						LibRootpackageInfoService.addonepackage("fromapkAnalysis","fromapkAnalysis", PKGstring,apkRootpackageInfo.getSubpckNum(),apkRootpackageInfo.getDirectorynum(),apkRootpackageInfo.getPckStructure(), apkRootpackageInfo.getPackagestructureHash());
					}**/
				}
		 }
	
	public static boolean JudgeObfuscated(String pkgname){
    	String endString=null;
    	if (pkgname.contains(".")) {
        	endString=pkgname.substring(pkgname.lastIndexOf(".")+1);
        	//System.out.println(endString);	
		}
    	if(pkgname.contains(".a.")||pkgname.contains(".b.")||pkgname.contains(".c.")||pkgname.contains(".d.")||pkgname.contains(".e.")||pkgname.contains(".f.")
    			||pkgname.contains(".f.")||pkgname.contains(".g.")||pkgname.contains(".h.")||pkgname.contains(".i.")||pkgname.contains(".j.")||pkgname.contains(".k.")||pkgname.contains(".l.")
    			||pkgname.contains(".m.")||pkgname.contains(".n.")||pkgname.contains(".o.")||pkgname.contains(".p.")||pkgname.contains(".q.")||pkgname.contains(".r.")||pkgname.contains(".s.")||pkgname.contains(".t.")
    			||pkgname.contains(".u.")||pkgname.contains(".v.")||pkgname.contains(".w.")||pkgname.contains(".x.")||pkgname.contains(".y.")||pkgname.contains(".z."))
    		return true;
    	if (endString!=null) {
			    	if(endString.equals("a")||endString.equals("b")||endString.equals("c")||endString.equals("d")||endString.equals("e")||endString.equals("f")
    			||endString.equals("g")||endString.equals("h")||endString.equals("i")||endString.equals("j")||endString.equals("k")||endString.equals("l")
    			||endString.equals("m")||endString.equals("n")||endString.equals("o")||endString.equals("p")||endString.equals("q")||endString.equals("r")
    			||endString.equals("s")||endString.equals("t")||endString.equals("u")||endString.equals("v")||endString.equals("w")||endString.equals("x")
    			||endString.equals("y")||endString.equals("z"))
    		return true;
		}

    	if(pkgname.equals("a")||pkgname.equals("b")||pkgname.equals("c")||pkgname.equals("d")||pkgname.equals("e")||pkgname.equals("f")
    			||pkgname.equals("g")||pkgname.equals("h")||pkgname.equals("i")||pkgname.equals("j")||pkgname.equals("k")||pkgname.equals("l")
    			||pkgname.equals("m")||pkgname.equals("n")||pkgname.equals("o")||pkgname.equals("p")||pkgname.equals("q")||pkgname.equals("r")
    			||pkgname.equals("s")||pkgname.equals("t")||pkgname.equals("u")||pkgname.equals("v")||pkgname.equals("w")||pkgname.equals("x")
    			||pkgname.equals("y")||pkgname.equals("z"))
    		return true;
    	if(pkgname.contains(".")){
    		String lastnameString=pkgname.substring(pkgname.lastIndexOf(".")+1);
    		//System.out.println(lastnameString);
    	if(lastnameString.equals("a")||lastnameString.equals("b")||lastnameString.equals("c")||lastnameString.equals("d")||lastnameString.equals("e")||lastnameString.equals("f")){
    		return true;
    	}
    		
    	}
    	return false;
    }
	private static void findclass() {
		List<String> jaraarpathList=new ArrayList<String>();
		CreateBatFile createBatFile=new CreateBatFile();
		jaraarpathList.addAll(traverseFolder("C:\\Users\\ZJY\\Desktop\\bat"));//E://Googletpls
		Sdk sdk = Sdk.loadDefaultSdk();
		Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
		System.out.println("一共有："+jaraarpathList.size()+"个");
		for (String txtstring : jaraarpathList) {
			txtstring=txtstring.replace("\\","\\\\");
			System.out.println(txtstring);
			 List<String> resultList= readtxt3(txtstring);
			String apkNameString=txtstring.substring(txtstring.lastIndexOf("\\")+1,txtstring.lastIndexOf("."));
			System.out.println(apkNameString);
			String apkPathString="H:\\\\test\\\\"+apkNameString;
			System.out.println(apkPathString);
			 Apk apk = Apk.loadFromFile(apkPathString);
			 ApkProfile apkProfile = ApkProfile.create(apk, targetSdkClassNameSet);
			 Map<String, ApkPackageProfile> rootPackageMap=apkProfile.packageProfileMap;
			 for (String PKGstring : resultList) {
				 System.out.println(PKGstring);
				for (String apkpkgstring :rootPackageMap.keySet()) {
					if (apkpkgstring.contains(PKGstring)) {
						Set<String> ClassList = rootPackageMap.get(apkpkgstring).classProfileMap.keySet();
						for (String string : ClassList) {
							//createBatFile.writeTxtFile("类名："+string, txtstring);
						}
					}
				}
			}
		}
		
	} 

	public static List<String> readtxt3(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//if (!resultList.contains(s)) {
            	if (s.contains("time0")) {
                	resultList.add(s.substring(s.indexOf(":")+1));	
				}
				//}	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	
	public static List<String> readtxta2(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
        	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"UTF-8"));
            //BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//if (!resultList.contains(s)) {
                	resultList.add(s);	
				//}	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	
	public static List<String> readtxt122(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//if (!resultList.contains(s)) {
                	resultList.add(s.substring(s.indexOf(":")+1));	
				//}	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	public static List<String> readtxt1(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	if (s.contains("*----RT----*")) {
                	resultList.add(s);	
                	//System.out.println(s);
				}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	private static List<String> readtxt2(String filepath){
		File file = new File(filepath);
		int cnt=0;
		//String s1 ="30016.apk*com.tencent.a*1*2*A.A.A.A.A.A,*1e743e60d7b25cac";
		//System.out.println(s1.split("0"));
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	resultList.add(s);     
            	cnt++;
            	count++;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        //deleteFile(filepath);
        System.out.println(cnt);
        return resultList;
    }
	
	
	private static void jaroraartodex(List<String> jaraarpathList) throws IOException {
		//System.out.println("kais");
		jar2dex jartodex=new jar2dex();
		aar2dex aartodex=new aar2dex();
		List<String> jarpathList=new ArrayList<>();
		List<String> aarpathList=new ArrayList<>();
		for (String jarPathstring : jaraarpathList) {
			if (jarPathstring.contains(".jar")) {
				jarpathList.add(jarPathstring);
			}
			else if (jarPathstring.contains(".aar")) {
				aarpathList.add(jarPathstring);
			}							
		}
		jarpathList.addAll(aartodex.execute(aarpathList));
		jartodex.execute(jarpathList);				
	}
	
    public static int Directorycount(String Directory) {
    	Directory=Directory.substring(Directory.lastIndexOf("\\"));
    	//System.out.println(Directory);
    	int num = 0;
        // 循环遍历每个字符，判断是否是字符 a ，如果是，累加次数
       for (int i=0;i<Directory.length();i++)
       {
           // 获取每个字符，判断是否是字符a
           if (Directory.charAt(i)=='.') {
               // 累加统计次数
               num++; 
           }
       }
		return num;
		
	}
    public static  boolean deleteFile(String fileName) {//删除单个文件
        File file = new File(fileName);
        //System.out.println(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
       // if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
       /** } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }**/
    }
    public static String getFileSize(String path) {
	    String resourceSizeMb = null;
	    try {
	        // 指定路径即可
	        File f = new File(path);

	        FileInputStream fis = new FileInputStream(f);

	        DecimalFormat df = new DecimalFormat("#.##");

	        // double resourceSize = (double)((double) fis.available() / 1024);
	        // ELog.e(TAG, "resourceSize:" + resourceSize);

	        if((double)((double) fis.available() / 1024) > 1000) {
	            resourceSizeMb = df.format((double)((double) fis.available() / 1024 / 1024)) + "MB";
	        } else {
	            resourceSizeMb= df.format((double)((double) fis.available() / 1024)) + "KB";
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        resourceSizeMb = null;
	    } 
	    //System.out.println("该文件大小为"+resourceSizeMb);
	    return resourceSizeMb;
	}
    public static List<String> traverseFolder(String path) {//找到以classes.jar为后缀名的所有文件
    	List<String> dexfilepath=new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;//||file2.getAbsolutePath().contains("aar")
                } else if(file2.getAbsolutePath() != null&&file2.getAbsolutePath().contains(".txt")){
                   // System.out.println(file2.getAbsolutePath());
                    dexfilepath.add(file2.getAbsolutePath());//.replace("\\", "\\\\")
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;//||file2.getAbsolutePath().contains("aar")build.gradle
                    } else if(file2.getAbsolutePath() != null&&file2.getAbsolutePath().contains(".txt")){
                        //System.out.println(file2.getAbsolutePath());
                        dexfilepath.add( file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            } 
        } else {
           // System.out.println("文件不存在!");
        }
       // System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    	return dexfilepath;

    }
    public static List<String> traverseFoldera(String path) {//找到以classes.jar为后缀名的所有文件
    	List<String> dexfilepath=new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;//||file2.getAbsolutePath().contains("aar")
                } else if(file2.getAbsolutePath().contains("jar")){
                    System.out.println(file2.getAbsolutePath());
                    dexfilepath.add(file2.getAbsolutePath());//.replace("\\", "\\\\")
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;//||file2.getAbsolutePath().contains("aar")
                    } else if(file2.getAbsolutePath().contains("jar")){
                        System.out.println(file2.getAbsolutePath());
                        dexfilepath.add( file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    	return dexfilepath;

    }

	public static void execute(List<String> jarpath) throws IOException{
		for(String jar:jarpath){
			System.out.println(jar);
			String dexfilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1);
			//jar2dex.creatbatFile(dexfilename);
			System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1));			
	        String batfilepath = jar2dex.creatbatFile(dexfilename);
			String batcontent="cd /d %~dp0\r\njava -jar G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\libdetect3.jar "+"E:\\新增120个豌豆荚下的apk\\"+dexfilename+".apk G:\\libdetection实验标准采集\\APK插入第三方库\\lib340";
			System.out.println(batfilepath);
			System.out.println(batcontent);
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        System.out.println(jar+"转dex已成功");
		}
 
	}
	
	public static void findsomething(List<String> lib,String content1,String content2){
		List<String> sameapkList=new ArrayList<>();
		int cou=0;
		for(String l:lib){
			String apknameString=l.substring(0, l.indexOf(".apk"));
			if(l.contains(content1)&l.contains(content2)){
				if(!sameapkList.contains(apknameString))
					sameapkList.add(apknameString);
				System.out.println(l);
				cou++;
			}
		}
		for(String a:sameapkList)
			System.out.println(a);
		System.out.println(cou);
		System.out.println(sameapkList.size());
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
             	System.out.println(s.substring(1, s.indexOf("*")));
            	resultList.add(s.substring(1, s.indexOf("*")));
            	}
            	else{
               	System.out.println(s);
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
}
