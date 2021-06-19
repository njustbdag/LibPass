package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class mytemoo extends Temp {
	private static HashMap<String, ArrayList<String>> Groundtruth=new HashMap<>();
	public static void main(String[] args) throws IOException{
		//libradar();
		libD();
		//tongji();
		//for (String pathString : getFiles("I:\\LibD-master\\LibD�ļ����")){
			//String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//System.out.println(pathString.replace("\\", "/")+","+"E:/p"+",E:/wancheng/"+apkNameString+".apk.txt,"+"SD");
		/**HashSet<String> libdHashSet=new HashSet<>();
		for (String line : readtxta2("C:\\Users\\ZJY\\Desktop\\��־\\libD.txt")) {
				if (line.contains(": {")) {
					libdHashSet.add(line);
					//String lib=line.substring(0,line.indexOf(":")).replace("/", ".");
					//System.out.println(line);
				} 
				}
		int cnt=0;
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\LibD")){
		for (String line : readtxta2(pathString)) {//I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\LidD
			if (line.contains(": {")&&libdHashSet.contains(line)) {
				System.out.println(line);
				cnt++;
			} 
			}
		}
		System.out.println(cnt);
		//}
		HashSet<String> masterHashSet=new HashSet<>();
		for (String masterpathString : getFolders("I:\\orlis-orcis-master\\proguard_origin_apk\\project")){
			masterHashSet.add(masterpathString);
			//System.out.println(masterpathString);
		}
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\FDroid_original")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//System.out.println(pathString.replace("\\", "/")+","+"E:/p"+",E:/wancheng/"+apkNameString+".apk.txt,"+"SD");
			//System.out.print(apkNameString+".apk:");
			boolean flag=false;
			for (String string : masterHashSet) {
				if (string.contains(apkNameString)) {
					//System.out.println(string);
					flag=true;
					for (String gradleString : traverseFolder(string)) {
						if (!gradleString.contains("gradle-wrapper")) {
							System.out.println(apkNameString+"***"+gradleString.substring(gradleString.lastIndexOf("\\")+1,gradleString.lastIndexOf(".")));	
						}
						boolean flagm=false;
						for (String line : readtxta2(gradleString)) {
							//System.out.println();
							if (line.contains("dependencies {")) {
								flagm=true;
							} 
							if (line.contains("fileTree(dir:")) {
								System.out.println(apkNameString);
							}
							if (flagm&&!line.contains("fileTree(dir:")&&!line.contains(".tools.build:gradle:")&&!line.contains("exclude")&&!line.contains("[")&&!line.contains("dependencies {")&&!line.contains("//")&&line.contains(":")&&line.contains("'")&&!line.contains("}")) {
								//System.out.println(line);
								String lib=line.substring(line.indexOf("'")+1, line.lastIndexOf("'")).replace(":", ".");
								System.out.print(lib+",");
							}
							if (line.contains("}")) {
								flagm=false;
							}
						}
					}
					break;
				}
			}
			if (flag) {
				//System.out.println("no such master");
			}
			System.out.println();
			}**/
		/**int cnt=0;
		int count=0, cct=0;
		int sdp=0;
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\dasho_data\\second")){
			boolean flag=false;
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//System.out.println(pathString.replace("\\", "/")+","+"E:/p"+",E:/wancheng/"+apkNameString+".apk.txt,"+"SD");
			//System.out.println(apkNameString);
			for (String line : readtxta2(pathString)) {
				if (line.contains("*----RT----*")) {
					sdp++;
				}
				if (line.contains("����:")){
					count++;
					System.out.println(line);
					String arpString=line.substring(3);
					System.out.println(arpString);
					String[] aStrings=arpString.split("\\.");
					//System.out.println(arpString+aStrings[0]);
					int d=aStrings.length;
					for (String shortdir : aStrings) {
						if (shortdir.length()<2) {
							d--;
						}
					}
					if (d<aStrings.length&&!aStrings[0].equals("rx")) {//d==0&&
						cct++;
						System.out.println(apkNameString+"---"+arpString);
						flag=true; 
					}
				//	if (JudgeObfuscated(arpString)&&JudgeObfuscated(aStrings[0])) {

					//}	
				}
			//if (line.contains("��ʼ****************")&&JudgeObfuscated(arpString)) {	
				//System.out.println(arpString);
			//}
		}
			if (flag) {
				cnt++;
			}
		}
		System.out.println(cnt);
		System.out.println(cct);
		System.out.println(count);
		System.out.println(sdp);
		int cnt=0;
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\second")){
			boolean flag=false;
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//copyFile("I:\\orlis-orcis-master\\Libradar_Apk\\proguard_obfuscated_third\\"+apkNameString+".apk.txt", "I:\\orlis-orcis-master\\Libradar_Apk\\proguard_obfuscated_third1\\"+apkNameString+".apk.txt");
			for (String line : readtxta2("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\second\\"+apkNameString+".txt")) {
				if (line.contains("����:")){
					cnt++;
				}
			}
		}
		System.out.println(cnt);**/
		}
	
	private static HashMap<String, ArrayList<String>> getlibradar() {
		int alltp=0;
		int allfp=0;
		int alldetected=0;
		int allgroundtruth=0;
		HashMap<String, ArrayList<String>> lIBRADARHashMap=new HashMap<>();
		setGroundtruth();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\Libradar_Apk\\proguard_obfuscated_third")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			lIBRADARHashMap.put(apkNameString, new ArrayList<String>());
			for (String line : readtxta2(pathString)) {
				if (line.startsWith("Package :")) {
					String lib=line.substring(line.indexOf(":")+3).replace("/", ".");
					lIBRADARHashMap.get(apkNameString).add(lib);
					System.out.println(apkNameString+"Package :"+lib);
				}
				} 
		}
		return lIBRADARHashMap;
	}
	
	private static void libradar() throws IOException {
		int alltp=0;
		int allfp=0;
		int alldetected=0;
		int allgroundtruth=0;
		HashMap<String, ArrayList<String>> lIBRADARHashMap=new HashMap<>();
		HashMap<String, ArrayList<String>> lIBsearcherHashMap=getlibsearcher();
		setGroundtruth();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\Libradar_Apk\\proguard_obfuscated_third")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			lIBRADARHashMap.put(apkNameString, new ArrayList<String>());
			for (String line : readtxta2(pathString)) {
				if (line.startsWith("Package :")) {
					String lib=line.substring(line.indexOf(":")+2).replace("/", ".");
					lIBRADARHashMap.get(apkNameString).add(lib);
				}
				}
		}
			/**for (String line : readtxta2("I:\\orlis-orcis-master\\Libradar_Apk\\dasho_data_third.txt")) {
				//System.out.println(line);
				String apknameString=line.substring(0, line.indexOf(","));
				String lib=line.substring(line.indexOf(",")+2).replace("/", ".");
				//System.out.println(apknameString);
				//System.out.println(lib);
				if (!lIBRADARHashMap.keySet().contains(apknameString)) {
					lIBRADARHashMap.put(apknameString, new ArrayList<String>());
				}
				lIBRADARHashMap.get(apknameString).add(lib);
			}**/
			for (String apkNameString: lIBRADARHashMap.keySet()) {
				System.out.println();
				System.out.println(apkNameString+":");
				if (Groundtruth.get(apkNameString)!=null) {
					for (String string : Groundtruth.get(apkNameString)) {
						//if (!string.equals("")) {
							System.out.println(apkNameString+"--"+string+"�̡̡̡̡̡�");
					//	}
					}
				}else {
					System.out.println(apkNameString+"��groundtruth������");
				}
				for (String string1 : lIBRADARHashMap.get(apkNameString)) {
					System.out.println(apkNameString+":"+string1);
				}
				if (Groundtruth.get(apkNameString)==null) {
					System.out.println("groundtruth.size():"+0);
				}else {
					System.out.println("groundtruth.size():"+Groundtruth.get(apkNameString).size());
					allgroundtruth+=Groundtruth.get(apkNameString).size();
				}
				System.out.println("LibRadar.size():"+lIBRADARHashMap.get(apkNameString).size());
				alldetected+=lIBRADARHashMap.get(apkNameString).size();
				int tp=0;
				HashSet<String> fpHashSet=new HashSet<>();
				fpHashSet.addAll(lIBRADARHashMap.get(apkNameString));
					if (Groundtruth.get(apkNameString)!=null) {
						for (String gtString : Groundtruth.get(apkNameString)) {
							String matchlib=null;
							int matchscount=0;
							//System.out.println(gtString);
							String gtString1 = Pattern.compile("[\\d]").matcher(gtString).replaceAll("");
							gtString1=gtString1.replace("-", ".");
							gtString1=gtString1.replace("_", ".");
							String gtString2 = gtString.replace("-", ".");
							gtString2 = gtString2.replace("_", ".");
							//System.out.println(gtString);
							first:for (String RTString : lIBRADARHashMap.get(apkNameString)) {
								int cn=0;
								if (gtString1.contains(RTString)||RTString.contains(gtString1.subSequence(0, gtString1.length()-4))) {
									matchlib=RTString;
									break first;
								}
								//System.out.println(gtString2);
								for (String string : gtString2.split("\\.")) {
									//System.out.println(string); 
									if (RTString.contains(string)&&!isNumericZidai(string)&&!string.equals("support")&&!string.equals("android")&&!string.equals("com")) {
										cn++;	
									} 
								}
								if (cn>matchscount) {
									matchscount=cn;
									matchlib=RTString;
								}
								//System.out.println(RTString+"**"+cn);
							}
							if (gtString.contains("vector-drawable")&&lIBRADARHashMap.get(apkNameString).contains("android.support.graphics")) {
								matchlib="android.support.graphics";
							}
							if (gtString.contains("netcipher")&&lIBRADARHashMap.get(apkNameString).contains("info.guardianproject")) {
								matchlib="info.guardianproject";
							}
							if (gtString.contains("eventbus")&&lIBRADARHashMap.get(apkNameString).contains("de.greenrobot.event")) {
								matchlib="de.greenrobot.event";
							}
							if (gtString.contains("annotation")&&lIBRADARHashMap.get(apkNameString).contains("android.support.annotation")) {
								matchlib="android.support.annotation";
							}
							System.out.println(gtString+"--match--"+matchlib);
							if (matchlib!=null) {
								tp++;
								fpHashSet.remove(matchlib);
							}
						}
					}
					if (Groundtruth.get(apkNameString)!=null&&Groundtruth.get(apkNameString).size()==0) {
						fpHashSet.clear();
						//System.out.println("Groundtruth.get(apkNameString).size()"+Groundtruth.get(apkNameString).size());
					}
					for (String arp : lIBsearcherHashMap.get(apkNameString)) { 
						//System.out.println("arp of lIBRADAR:"+arp);
						HashSet<String> needtoremoveHashSet=new HashSet<>();
						for (String string : fpHashSet) {
							if (string.equals(arp)||arp.contains(string)||string.contains(arp)) {
								System.out.println("LibSearcher��LibRadar����⵽��TPL��"+string);
								needtoremoveHashSet.add(string);
							} 
						}
						fpHashSet.removeAll(needtoremoveHashSet);
					}
					for (String string : fpHashSet) {
						System.out.println("LibSearcher��fp��"+string);
					}
				System.out.println("TP.size():"+tp);
				System.out.println("FP.size():"+fpHashSet.size());
				alltp+=tp; 
				allfp+=fpHashSet.size(); 
				double k=1;
				if (Groundtruth.get(apkNameString)!=null) {
					k=tp*1.0/Groundtruth.get(apkNameString).size();
				}
				System.out.println("tp:"+k);
			}
			System.out.println("alltp:"+alltp);
			System.out.println("allfp:"+allfp);
			System.out.println("alldetected:"+alldetected);
			System.out.println("allgroundtruth:"+allgroundtruth);
	}
	
	private static HashMap<String, ArrayList<String>> getlibD() {
		int alltp=0;
		int allfp=0;
		int alldetected=0;
		int allgroundtruth=0;
		HashMap<String, ArrayList<String>> lIBRADARHashMap=new HashMap<>();
		setGroundtruth();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\ԭapks\\LibD")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//System.out.println(apkNameString);
			lIBRADARHashMap.put(apkNameString, new ArrayList<String>());
			//System.out.println("reading..."+pathString);
			for (String line : readtxta2(pathString)) {
				if (line.contains(": {")) {
					String lib=line.substring(0,line.indexOf(":")).replace("/", ".");
					//System.out.println("ddddd:"+lib);
					lIBRADARHashMap.get(apkNameString).add(lib);
				} 
				}
		}
		return lIBRADARHashMap;
	}
	
	private static void libD() {
		int alltp=0;
		int allfp=0;
		int alldetected=0;
		int allgroundtruth=0;
		HashMap<String, ArrayList<String>> lIBRADARHashMap=new HashMap<>();
		HashMap<String, ArrayList<String>> lIBDHashMap=getlibradar();
		setGroundtruth();
		HashSet<String> libdHashSet=getRealresult();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\LibD")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			//System.out.println(apkNameString);
			lIBRADARHashMap.put(apkNameString, new ArrayList<String>());
			//System.out.println("reading..."+pathString);
			for (String line : readtxta2(pathString)) {
				if (line.contains(": {")) {
					String lib=line.substring(0,line.indexOf(":")).replace("/", ".");
					if (libdHashSet.contains(line)) {
						lIBRADARHashMap.get(apkNameString).add(lib);
					}
					//System.out.println("ddddd:"+lib);
				} 
				}
		}
		HashMap<String, ArrayList<String>> lIBradarcHashMap=getlibradar();
			/**for (String line : readtxta2("I:\\orlis-orcis-master\\Libradar_Apk\\dasho_data_third.txt")) {
				//System.out.println(line);
				String apknameString=line.substring(0, line.indexOf(","));
				String lib=line.substring(line.indexOf(",")+2).replace("/", ".");
				//System.out.println(apknameString);
				//System.out.println(lib);
				if (!lIBRADARHashMap.keySet().contains(apknameString)) {
					lIBRADARHashMap.put(apknameString, new ArrayList<String>());
				}
				lIBRADARHashMap.get(apknameString).add(lib);
			}**/
			for (String apkNameString: lIBRADARHashMap.keySet()) {
				int Groundtruthsize=0;
				System.out.println();
				System.out.println(apkNameString+":");
				if (Groundtruth.get(apkNameString)!=null) {
					for (String string : Groundtruth.get(apkNameString)) {
						//if (!string.equals("")) {
							System.out.println(apkNameString+"--"+string+"�̡̡̡̡̡�");
					//	}
					}
				}else {
					System.out.println(apkNameString+"��groundtruth������");
				}
				for (String string1 : lIBRADARHashMap.get(apkNameString)) {
					System.out.println(apkNameString+":"+string1);
				}
				if (Groundtruth.get(apkNameString)==null) {
					System.out.println("groundtruth.size():"+0);
				}else {
					Groundtruthsize=Groundtruth.get(apkNameString).size();
					System.out.println("groundtruth.size():"+Groundtruth.get(apkNameString).size());
					allgroundtruth+=Groundtruth.get(apkNameString).size();
				}
				System.out.println("LibD	.size():"+lIBRADARHashMap.get(apkNameString).size());
				alldetected+=lIBRADARHashMap.get(apkNameString).size();
				int tp=0;
				HashSet<String> fpHashSet=new HashSet<>();
				fpHashSet.addAll(lIBRADARHashMap.get(apkNameString));
					if (Groundtruth.get(apkNameString)!=null) {
						for (String gtString : Groundtruth.get(apkNameString)) {
							String matchlib=null;
							int matchscount=0;
							//System.out.println(gtString);
							String gtString1 = Pattern.compile("[\\d]").matcher(gtString).replaceAll("");
							gtString1=gtString1.replace("-", ".");
							gtString1=gtString1.replace("_", ".");
							String gtString2 = gtString.replace("-", ".");
							gtString2 = gtString2.replace("_", ".");
							//System.out.println(gtString);
							first:for (String RTString : lIBRADARHashMap.get(apkNameString)) {
								int cn=0;
								if (gtString1.contains(RTString)||RTString.contains(gtString1.subSequence(0, gtString1.length()-4))) {
									matchlib=RTString;
									break first;
								}
								//System.out.println(gtString2);
								for (String string : gtString2.split("\\.")) {
									//System.out.println(string); 
									if (RTString.contains(string)&&!isNumericZidai(string)&&!string.equals("support")&&!string.equals("android")&&!string.equals("com")) {
										cn++;	
									} 
								}
								if (cn>matchscount) {
									matchscount=cn;
									matchlib=RTString;
								}
								//System.out.println(RTString+"**"+cn);
							} 
							System.out.println(gtString+"--match--"+matchlib);
							if (matchlib!=null) {
								tp++;
								fpHashSet.remove(matchlib);
							}
						}
					}
				System.out.println("TP.size():"+tp);
				for (String string : fpHashSet) {
					System.out.println(string);
				}
				if (Groundtruthsize==0) { 
					fpHashSet.clear();
					//System.out.println("Groundtruth.get(apkNameString).size()"+Groundtruth.get(apkNameString).size());
				}
				for (String arp : lIBDHashMap.get(apkNameString)) { 
					//System.out.println("arp of lIBRADAR:"+arp);
					HashSet<String> needtoremoveHashSet=new HashSet<>();
					for (String string : fpHashSet) {
						if (string.equals(arp)||arp.contains(string)||string.contains(arp)) {
							System.out.println("LibSearcher��LibRadar����⵽��TPL��"+string);
							needtoremoveHashSet.add(string);
						} 
					}
					fpHashSet.removeAll(needtoremoveHashSet);
				}
				for (String string : fpHashSet) {
					System.out.println("LibSearcher��fp��"+string);
				}
				System.out.println("FP.size():"+fpHashSet.size());
				alltp+=tp; 
				allfp+=fpHashSet.size(); 
				double k=1;
				if (Groundtruth.get(apkNameString)!=null) {
					k=tp*1.0/Groundtruth.get(apkNameString).size();
				}
				System.out.println("tp:"+k);
			}
			System.out.println("alltp:"+alltp);
			System.out.println("allfp:"+allfp);
			System.out.println("alldetected:"+alldetected);
			System.out.println("allgroundtruth:"+allgroundtruth);
	}

	private static HashSet<String> getRealresult() {
		HashSet<String> libdHashSet=new HashSet<>();
		for (String line : readtxta2("C:\\Users\\ZJY\\Desktop\\��־\\libD.txt")) {
				if (line.contains(": {")) {
					libdHashSet.add(line);
					//String lib=line.substring(0,line.indexOf(":")).replace("/", ".");
					//System.out.println(line);
				} 
				}
		/**int cnt=0;
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\ԭapks\\LibD")){
		for (String line : readtxta2(pathString)) {//I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\LidD
			if (line.contains(": {")&&libdHashSet.contains(line)) {
				System.out.println(line);
				
			} 
			cnt++;
			}
		}
		System.out.println(cnt);**/
		return libdHashSet;
	}

	public boolean isObfucated(String ARPname) {
			boolean flag=false;
					String[] aStrings=ARPname.split("\\.");
					//System.out.println(arpString+aStrings[0]);
					int d=aStrings.length;
					for (String shortdir : aStrings) {
						if (shortdir.length()<3) {
							d--;
						}
					}
					if (d==0&&!aStrings[0].equals("rx")) {//
						flag=true; 
					}
		return flag;
		
	}
	
	private static HashMap<String, ArrayList<String>> getlibsearcher() throws IOException {
		int alltp=0;
		int allfp=0;
		int alldetected=0;
		int allgroundtruth=0;
		HashMap<String, ArrayList<String>> lIBsearcherHashMap=new HashMap<>();
		setGroundtruth();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\second")){
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			lIBsearcherHashMap.put(apkNameString, new ArrayList<String>());
			for (String line : readtxta2(pathString)) {
				if (line.contains("*----RT----*")) {
					String MSString=line.substring(line.indexOf("*----MS----*")+12,line.indexOf("*----IM----*"));
					String RTString=line.substring(line.indexOf("*----RT----*")+12,line.indexOf("*----LIB----*"));
					String libString=line.substring(line.indexOf("*----LIB----*")+13,line.indexOf("*----MS----*"));
					String IMString=line.substring(line.indexOf("*----IM----*")+12);
					String libnameString=libString.substring(0, libString.lastIndexOf(".dex"));
					if (libnameString.contains(".apk.")) {
						libnameString=libString.substring(libString.lastIndexOf(".apk")+5);
					}
					double MS=Double.parseDouble(MSString);
					double IM=Double.parseDouble(IMString);
					lIBsearcherHashMap.get(apkNameString).add(RTString);
					}
				}
		}
		return lIBsearcherHashMap; 
		}

	private static void tongji() throws IOException {
		HashSet<String> researchHashSet=new HashSet<>();
		int alltp=0;
		int alldetected=0;
		int allgroundtruth=0;
		int allFP=0;
		HashMap<String, Integer> arpcnt=new HashMap<>();
		HashMap<String, ArrayList<String>> lIBRADARHashMap=getlibradar();
		//setarpcnt(arpcnt); 
		setGroundtruth();
		for (String pathString : getFiles("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\second")){
			//System.out.println(string);
			int cnt=0;
			int Groundtruthsize=0;
			System.out.println();
			HashSet<String> libsHashSet=new HashSet<>();
			HashSet<String> fpHashSet=new HashSet<>();
			//String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("-"))+".apk";
			String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
			String simpleapkNameString=apkNameString.substring(0,apkNameString.indexOf("."));
			//System.out.println(simpleapkNameString);
			System.out.println(apkNameString+"��");
			Map<String,List<String>> matchResultMap=getmatchResultMap(apkNameString);
			if (Groundtruth.get(apkNameString)!=null) {
				for (String string : Groundtruth.get(apkNameString)) {
					//if (!string.equals("")) {
						System.out.println(apkNameString+"--"+string+"�̡̡̡̡̡�");
				//	}
				}
			}else if (!Groundtruth.keySet().contains(apkNameString)) {
				System.out.println("groundtruth��û��"+apkNameString+"������");
			}else{
				System.out.println(apkNameString+"��groundtruth������");
			}
			//System.out.println(Groundtruth.get(apkNameString));
			for (String line : readtxta2(pathString)) {
				if (line.contains("*----RT----*")) {
					String MSString=line.substring(line.indexOf("*----MS----*")+12,line.indexOf("*----IM----*"));
					String RTString=line.substring(line.indexOf("*----RT----*")+12,line.indexOf("*----LIB----*"));
					String libString=line.substring(line.indexOf("*----LIB----*")+13,line.indexOf("*----MS----*"));
					String IMString=line.substring(line.indexOf("*----IM----*")+12);
					String libnameString=libString.substring(0, libString.lastIndexOf(".dex"));
					if (libnameString.contains(".apk.")) {
						libnameString=libString.substring(libString.lastIndexOf(".apk")+5);
					}
					double MS=Double.parseDouble(MSString);
					double IM=Double.parseDouble(IMString);
					if (MS>=0.6&&!simpleapkNameString.contains(RTString)) {
						libsHashSet.add(RTString);
						if (MS<0.9) {
							fpHashSet.add(RTString);
						}
						System.out.println(apkNameString+"--"+RTString+"--"+libnameString+"--"+MS);
						cnt++;
						}else if (matchResultMap.get(RTString).size()>1&&!simpleapkNameString.contains(RTString)) {
							libsHashSet.add(RTString);
							System.out.println(apkNameString+"--"+RTString+"--"+libnameString+"--"+MS+"������");
						}
				}
			}
			if (Groundtruth.get(apkNameString)==null||!Groundtruth.keySet().contains(apkNameString)) {
				System.out.println("groundtruth.size():"+0);
			}else {
				Groundtruthsize=Groundtruth.get(apkNameString).size();
				System.out.println("groundtruth.size():"+Groundtruth.get(apkNameString).size());
				allgroundtruth+=Groundtruth.get(apkNameString).size();
			}
			System.out.println("LibSearcher.size():"+cnt);
			alldetected+=cnt;
			//fpHashSet.addAll(libsHashSet);
			int tp=0;
				if (Groundtruth.get(apkNameString)!=null) {
					for (String gtString : Groundtruth.get(apkNameString)) {
						String matchlib=null;
						int matchscount=0;
						//System.out.println(gtString);
						String gtString1 = Pattern.compile("[\\d]").matcher(gtString).replaceAll("");
						gtString1=gtString1.replace("-", ".");
						gtString1=gtString1.replace("_", ".");
						String gtString2 = gtString.replace("-", ".");
						gtString2 = gtString2.replace("_", ".");
						//System.out.println(gtString);
						first:for (String RTString : libsHashSet) {
							int cn=0;
							if (gtString1.contains(RTString)||RTString.contains(gtString1.subSequence(0, gtString1.length()-4))) {
								matchlib=RTString;
								break first;
							}
							//System.out.println(gtString2);
							for (String string : gtString2.split("\\.")) {
								//System.out.println(string); 
								if (RTString.contains(string)&&!isNumericZidai(string)&&!string.equals("support")&&!string.equals("android")&&!string.equals("com")) {
									cn++;	
								} 
							}
							if (cn>matchscount) {
								matchscount=cn;
								matchlib=RTString;
							}
							//System.out.println(RTString+"**"+cn);
						}
						if (gtString.contains("vector-drawable")&&libsHashSet.contains("android.support.graphics")) {
							matchlib="android.support.graphics";
						}
						if (gtString.contains("netcipher")&&libsHashSet.contains("info.guardianproject")) {
							matchlib="info.guardianproject";
						}
						if (gtString.contains("eventbus")&&libsHashSet.contains("de.greenrobot.event")) {
							matchlib="de.greenrobot.event";
						}
						if (gtString.contains("annotation")&&libsHashSet.contains("android.support.annotation")) {
							matchlib="android.support.annotation";
						}
						System.out.println(gtString+"--match--"+matchlib);
						if (matchlib!=null) {
							tp++;
							if (fpHashSet.contains(matchlib)) {
								cnt--;
								fpHashSet.remove(matchlib);
							}
						}
					}
				}
			System.out.println("TP.size():"+tp);
			if (Groundtruthsize==0) { 
				fpHashSet.clear();
				//System.out.println("Groundtruth.get(apkNameString).size()"+Groundtruth.get(apkNameString).size());
			}
			for (String arp : lIBRADARHashMap.get(apkNameString)) { 
				//System.out.println("arp of lIBRADAR:"+arp);
				HashSet<String> needtoremoveHashSet=new HashSet<>();
				for (String string : fpHashSet) {
					if (string.equals(arp)||arp.contains(string)||string.contains(arp)) {
						System.out.println("LibSearcher��LibRadar����⵽��TPL��"+string);
						needtoremoveHashSet.add(string);
					} 
				}
				fpHashSet.removeAll(needtoremoveHashSet);
			}
			for (String string : fpHashSet) {
				System.out.println("LibSearcher��fp��"+string);
			}
			allFP+=(fpHashSet.size());
			System.out.println("FP.size():"+fpHashSet.size());
			//allFP+=(fpHashSet.size());
			double k=1;
			if (Groundtruth.get(apkNameString)!=null) {
				k=tp*1.0/Groundtruth.get(apkNameString).size();
			}
			if (k<1) {
				researchHashSet.add(apkNameString);
			}
			System.out.println("tp:"+k);
			if (k>0) {
				//System.out.println(apkNameString+"�ɹ��ҵ�ԭ���̵�apk");
			}
			alltp+=tp;
		}
		System.out.println("alldetected:"+alldetected);
		System.out.println("allgroundtruth:"+allgroundtruth);
		System.out.println("alltp:"+alltp);
		double tp=alltp/allgroundtruth;
		System.out.println("tp:"+tp);
		System.out.println("allFP:"+allFP);
		System.out.println("precision:"+alltp/alldetected);
		
		//for (String string : researchHashSet) {
			//System.out.println(string.substring(0, string.lastIndexOf("."))+".bat");
		//copyFile("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\dasho_data\\bat\\"+string.substring(0, string.lastIndexOf("."))+".bat", "I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\dasho_data\\rebat\\"+string.substring(0, string.lastIndexOf("."))+".bat");
		//}
	}

	public static boolean isNumericZidai(String str) {
		for (int i = 0; i < str.length(); i++) {
		//System.out.println(str.charAt(i));
		if (!Character.isDigit(str.charAt(i))) {
			//System.out.println(str+"��������");
		return false;
		}
		}
		//System.out.println(str+"������");
		return true;
		}

	private static Map<String, List<String>> getmatchResultMap(
			String apkName) throws IOException {
		Map<String,List<String>> matchResultMap=new HashMap<>();
		File file = new File("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\proguard_obfuscated\\first\\"+apkName+".txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		 String line;   
		    while ((line = reader.readLine()) != null) {  
		    	//System.out.println(line);
		    	if (!matchResultMap.keySet().contains(line.substring(0, line.indexOf("-")))) {
		    		matchResultMap.put(line.substring(0, line.indexOf("-")), new ArrayList<String>());
				}
		    	//System.out.println(line.substring(0, line.indexOf("-")));
		    	matchResultMap.get(line.substring(0, line.indexOf("-"))).add(line.substring(line.lastIndexOf("-----")+5));
		    	//System.out.println(line.substring(line.lastIndexOf("-----")+5));
		    }
		    reader.close(); 
		   /** for (String root : matchResultMap.keySet()) {
				System.out.println(root);
				System.out.println(matchResultMap.get(root));
			}**/
		return matchResultMap;
	}

	private static void setGroundtruth() {
		for (String line : readtxta2("I:\\orlis-orcis-master\\orlis\\open_source_benchmarks\\ground_truth-proguard_obfuscated.txt")) {
			String apknameString=line.substring(0,line.indexOf(":"));
			//apknameString=apknameString.substring(0, apknameString.lastIndexOf("."))+"-obf.apk";
			line=line.substring(line.indexOf(":")+1);
			ArrayList<String> tplArrayList=new ArrayList<>();
			String[] tplStrings=line.split(",");
			for (int i = 0; i < tplStrings.length; i++) {
				if (!tplStrings[i].equals("")&&!tplArrayList.contains(tplStrings[i])&&!tplStrings[i].contains("internal_impl")&&!tplStrings[i].contains("library-")) {
					tplArrayList.add(tplStrings[i]);
				}
			}
			//System.out.println(tplArrayList.size());
			//System.out.println(apknameString);
			//System.out.println(tplArrayList);
			Groundtruth.put(apknameString, tplArrayList);
			//System.out.println(apknameString);
		}
		for (String string : Groundtruth.keySet()) {
			//System.out.println(string);
			for (String string1 : Groundtruth.get(string)) {
				//System.out.println(string1);
			}
		}
		//System.out.println(Groundtruth.size());
		//fangfa();
		
	}

	private static void fangfa() throws IOException {
		// TODO Auto-generated method stub
		/**for (String string : getFiles("I:\\17000��apkARP�������")) {
		String apkNameString=string.substring(string.lastIndexOf("\\")+1, string.lastIndexOf("."));
		//System.out.println();
		for (String stringq : readtxta2(string)) {
			if (stringq.contains("������ʼ*")) {
				String aRPString=stringq.substring(stringq.lastIndexOf("����")+2, stringq.lastIndexOf("����"));
				String firseDIRString=aRPString.contains(".")?aRPString.substring(0, aRPString.indexOf(".")):aRPString;
				if (JudgeObfuscated(aRPString)&&!JudgeObfuscated(firseDIRString)) {
					String smaliPath="I:\\smali�ļ�-�㶹��\\"+apkNameString+"\\smali\\"+aRPString.replace(".", "\\");
					String newPath="I:\\smali�ļ�-�㶹��\\"+apkNameString+"\\smali\\"+aRPString.replace(".", "\\");
					String dexPath = apkNameString+"."+aRPString;
					System.out.println(smaliPath);
					System.out.println(dexPath);
					//smali2dex.smali22dex(smaliPath,dexPath);
				}
			}
		}
		System.out.println("I:\\Androidsdk"+string.substring(string.lastIndexOf("\\")));
		if (string.substring(string.lastIndexOf(".")).contains("jar")) {
			//mkdir("I:\\Androidsdk"+string.substring(string.lastIndexOf("\\")));
			//copyFile(string,"I:\\Androidsdk"+string.substring(string.lastIndexOf("\\")));
		}
	ArrayList<String> sdArrayList=new ArrayList<>();
	for (String pathString : getFiles("E:\\LibDetectʵ��groundtruth\\���н��lp")){
		//System.out.println(string);
		String apkNameString=pathString.substring(pathString.lastIndexOf("\\")+1,pathString.lastIndexOf("."));
		for (String line : readtxta2(pathString)) {
			if (line.contains("*----RT----*")) {
				String MSString=line.substring(line.indexOf("*----MS----*")+12,line.indexOf("*----IM----*"));
				String RTString=line.substring(line.indexOf("*----RT----*")+12,line.indexOf("*----LIB----*"));
				String libString=line.substring(line.indexOf("*----LIB----*")+13,line.indexOf("*----MS----*"));
				String IMString=line.substring(line.indexOf("*----IM----*")+12);
				String libnameString=libString.substring(0, libString.lastIndexOf(".dex"));
				//if (libnameString.contains(".apk.")) {
				//	libnameString=libString.substring(libString.lastIndexOf(".apk")+5);
				//}
				double MS=Double.parseDouble(MSString);
				double IM=Double.parseDouble(IMString);
				if (MS>=0.9&&IM>=1) {
					System.out.println(apkNameString+"--"+libnameString+"--"+MS);
					//if (!sdArrayList.contains(libnameString)) {
						copyFile("I:\\����dex\\dexfileset21\\"+apkNameString,"I:\\������������\\0.9���ϵĻ���dex��IMС��0.9\\"+apkNameString+"--"+libnameString+".dex");
						sdArrayList.add(libnameString);
					//}
					}
					//System.out.println(line);
					//
			}
		}
		//System.out.println(sdArrayList.size());
	}**/
		/**for (String string : traverseFoldera("E:\\Androidgame\\PuzzleGame-master")) {
		if (string.endsWith(".jar")) {
			System.out.println(string);
		}
	}**/
	HashSet<String> poHashSet=new HashSet<>();
	for (String string : getFiles("I:\\��Դapk���\\LibPassʵ����-����Դ�����apk")) {
		String apkNameString=string.substring(string.lastIndexOf("\\")+1, string.lastIndexOf("."));
		//System.out.println();
		for (String stringq : readtxta2(string)) {
			if (stringq.contains("����:")) {
				String aRPString=stringq.substring(3);
				String firstString=aRPString.contains(".")?aRPString.substring(0, aRPString.indexOf(".")):aRPString;
				if (JudgeObfuscated(aRPString)&&JudgeObfuscated(firstString)){
					poHashSet.add(apkNameString);
					System.out.println(apkNameString+"--"+aRPString);
				}
			}
		}
			
		}
	for (String string : poHashSet) {
		copyFile("I:\\��Դapk���\\��Դapk�ļ�\\"+string,"I:\\��Դapk���\\�Ӹ�Ŀ¼��ʼ�����Ŀ�Դapk\\"+string);
		System.out.println(string);
	}
	System.out.println(poHashSet.size());
	}
	
		//copyFile("C:\\Users\\ZJY\\Desktop\\��־\\we.txt","I:\\Androidsdk\\we.txt");
	}

