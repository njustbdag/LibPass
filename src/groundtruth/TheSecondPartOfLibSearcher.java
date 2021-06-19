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
import java.util.regex.Pattern;

import njust.lib.Service.LibRootpackageInfoService;

import org.hibernate.service.spi.Startable;

import cn.fudan.common.util.HashHelper;
import cn.fudan.libpecker.core.ProcessingDirectory;
import cn.fudan.libpecker.core.ProfileComparator;
import cn.fudan.libpecker.main.defanaly;
import cn.fudan.libpecker.model.ApkPackageProfile;
import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.ConversionPkgName;
import cn.fudan.libpecker.model.LibPackageProfile;
import cn.fudan.libpecker.model.LibProfile;
import cn.fudan.libpecker.model.SimpleClassProfile;
import cn.njust.common.Apk;
import cn.njust.common.Lib;
import cn.njust.common.LibPeckerConfig;
import cn.njust.common.Sdk;
import edu.njust.bean.LibRootpackageInfo;

public class TheSecondPartOfLibSearcher {

    private static Set<String> targetSdkClassNameSet;
	private static Map<String, ApkPackageProfile> apkPackageProfileMap;
	 public static Map<String, List<String>> rootPackageMap;//root package -> [package names, ...]
	    public static Map<String, ApkPackageProfile> apkPackageProfileMapcom;//pkg name -> ApkPackageProfile
	    public static String apkName;
	    private static   Map<String, List<String>> depGraphtoARP;
	    private static PrintWriter writer;
	    
	public TheSecondPartOfLibSearcher(ApkProfile apkProfile, Set<String> targetSdkClassNameSet) throws FileNotFoundException {
        this.targetSdkClassNameSet = targetSdkClassNameSet;
        this.apkPackageProfileMap = apkProfile.packageProfileMap;
        this.rootPackageMap=apkProfile.rootPackageMap;
    }
	private static void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
	
	public static void main(String[] args) throws Exception{
		long current = System.currentTimeMillis();
		String apkPath = "I:\\����dex\\lords-mobile.apk";
		/**String apkPath =null;
 	     String libPath =null;
        if (args == null || args.length == 2) {
        	 apkPath = args[0];
             libPath = args[1];
        }
        else {
            fail("Usage: java -cp LibPecker3.jar cn.fudan.libpecker.mainProfileBasedLibPecker <apk_path> <lib_path>");
        }**/
		apkName=apkPath.substring(apkPath.lastIndexOf("\\")).substring(1);
	    File file2 = new File("E:\\LibDetectʵ��groundtruth\\���н��lp\\"+apkName+".txt");     
	    writer = new PrintWriter(file2);  
		//String apkpathString="G:\\libdetectionʵ���׼�ɼ�\\APK��\\bacth2\\"+apkName;// 	G:\\libdetectiongroundtruth\\APKset\\bacth2\\85_Cafe_v1.0.8_apkpure.com.apk
		System.out.println(apkName+"*begin"+"time: " + (System.currentTimeMillis() - current));
		writer.println(apkName+"*begin"+"time: " + (System.currentTimeMillis() - current));   
	      writer.flush();
	      try {
	  		Start(apkPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(apkName+"*end"+"time: " + (System.currentTimeMillis() - current));
		writer.println(apkName+"*end"+"time: " + (System.currentTimeMillis() - current));   
	      writer.flush();
		System.out.println("time: " + (System.currentTimeMillis() - current));
		writer.close();
		LibsearcherT.extraLog(apkName);
		}

	public static void Start(String apkpathString) throws Exception {
		Map<String,List<String>> matchResultMap=new HashMap<>();
		File file = new File("E:\\LibDetectʵ��groundtruth\\���н��first\\"+apkName+".txt");
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
		    for (String root : matchResultMap.keySet()) {
				System.out.println(root);
				System.out.println(matchResultMap.get(root));
			}
			//System.out.println(apkpathString); 
			Sdk sdk = Sdk.loadDefaultSdk();
		        if (sdk == null) {
		            fail("default sdk not parsed");
		        } 
		        Apk apk = Apk.loadFromFile(apkpathString);
		        if (apk == null) {
		            fail("apk not parsed");
		        }
		        //apkName=apkpathString.substring(apkpathString.lastIndexOf("\\")+1);
			Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
	    	ApkProfile apkProfile= ApkProfile.create(apk, targetSdkClassNameSet); 
	        ApkProfile apkProfile1 = ApkProfile.create1(apk, targetSdkClassNameSet);
	        depGraphtoARP=defanaly.DEFAnalysis(apk, apkProfile);
	        apkPackageProfileMapcom=apkProfile1.packageProfileMap;
	        TheSecondPartOfLibSearcher libSeacher=new TheSecondPartOfLibSearcher(apkProfile,targetSdkClassNameSet);
	    	Set<String> tempList=new HashSet<>();
    	VersionDetermine(matchResultMap);		
	}
	
	
	 private static void experiment(Map<String, List<String>> matchResultMap, String apkname, long current) throws IOException {
	        calpkgsim pecker1 = new calpkgsim();
	        String filenameTemp=pecker1.creatdexFile(apkname);
	        for (String rootName:matchResultMap.keySet()) {
	        	for (String candidateLib:matchResultMap.get(rootName)) {
		        	pecker1.writeTxtFile(rootName+"---"+candidateLib,filenameTemp);
				}
			}
	        pecker1.writeTxtFile("time: " + (System.currentTimeMillis() - current),filenameTemp);
	        System.out.println("time: " + (System.currentTimeMillis() - current));
	}
	public static Map<String,List<String>>  rootanalysisMap(String root) {
	    	Map<String,List<String>> matchResultMap=new HashMap<String, List<String>>();
	    	ProcessingDirectory processingDirectory=new ProcessingDirectory();
	    	int cou=rootPackageMap.size();
	    		if (!root.equals(".")) {
	        		String unobfusDir=processingDirectory.InterceptunobfusDir(root);
	        		int rootDirnum=Directorycount(root);
	        		List<String> subpkg=rootPackageMap.get(root);
	            	System.out.println("�Ӱ���Ŀ"+subpkg.size());
	            	for (String subpkgstring : subpkg) {
	            		ApkPackageProfile apkPackageProfile=apkPackageProfileMap.get(subpkgstring);
	            		Map<String, SimpleClassProfile> classProfileMap=apkPackageProfile.classProfileMap;
	    			}
	    			Map<String,Integer> levelListtest=new HashMap<>();
	    			ConversionPkgName conversionPkgName=new ConversionPkgName(subpkg,levelListtest);
	    			List<String> conversedList=conversionPkgName.PackageTreeGenerator();
	    				StringBuilder PackagestructureHashList = new StringBuilder();
	    				String PackagestructureList = null;
	    				for (String astring : conversedList) {
	    					//System.out.println(astring); 
	    					PackagestructureHashList.append(astring);	
	    			}				
	    				String PackagestructureHash=HashHelper.hash(PackagestructureHashList.toString());
	    				System.out.println("��ϣֵ��"+PackagestructureHash);
	    				List<LibRootpackageInfo>  Lib1=LibRootpackageInfoService.findallbyunobfusDirandHash(root,unobfusDir,rootDirnum, PackagestructureHash,conversedList);
	    				List<String> libname=new ArrayList<>();
	    				if (Lib1!=null) {
						 for (LibRootpackageInfo astring : Lib1) {
							libname.add(astring.getLibName());
						}	
						}
	    				matchResultMap.put(root, libname);
	        		System.out.println();
				}
			return matchResultMap;  
		}
	 
	 public static int Directorycount(String Directory) {
	    	if (Directory.contains("\\")) {
	    	  	Directory=Directory.substring(Directory.lastIndexOf("\\"));
			}
	    	int num = 0;
	        // ѭ������ÿ���ַ����ж��Ƿ����ַ� a ������ǣ��ۼӴ���
	       for (int i=0;i<Directory.length();i++)
	       {
	           // ��ȡÿ���ַ����ж��Ƿ����ַ�a
	           if (Directory.charAt(i)=='.') {
	               // �ۼ�ͳ�ƴ���
	               num++; 
	           }
	       }
	       System.out.println(num+"��Ŀ¼");
			return num;
			
		}
	private static void VersionDetermine(Map<String, List<String>> matchResultMap) throws Exception {
		long current = System.currentTimeMillis();
		int count=1;
		for (String apkrootPKG : matchResultMap.keySet()) {
			System.out.println("��"+count+"������"+apkrootPKG+"����ֵ���㿪ʼ��");
			writer.println();   
		      writer.flush();
			writer.println("��"+count+"������"+apkrootPKG+"����ֵ���㿪ʼ��");   
		      writer.flush(); 
			List<String> subPCKset=rootPackageMap.get(apkrootPKG);
			//for (String string : subPCKset) {
				//System.out.println(string); 
			//}
			System.out.println("������ϵ�����");
			writer.println("������ϵ�����");   
		      writer.flush(); 
			for (String string : depGraphtoARP.get(apkrootPKG)) {
				System.out.println(string);
				writer.println(string);   
			      writer.flush(); 
			}
			System.out.println("������ϵ�������");
			HashSet<String>tempList=new HashSet<>();
			tempList.addAll(subPCKset);
			//System.out.println(tempList);
			//PackageSortAnalysis tempAnalysis=new PackageSortAnalysis(tempList);
			//tempAnalysis.sortBegin();
			if (matchResultMap.get(apkrootPKG).size()!=0&&matchResultMap.get(apkrootPKG).size()<8000) {//!apkrootPKG.equals("com.google.android")&&!apkrootPKG.equals("android.support.v4")&&&matchResultMap.get(apkrootPKG).size()<60!apkrootPKG.contains("android.support")&!apkrootPKG.contains("com.google")&!apkrootPKG.contains("com.facebook")&
				//&&matchResultMap.get(apkrootPKG).size()<200
				List<String> resultlibList=matchResultMap.get(apkrootPKG);				
				System.out.println("ƥ�䵽�ĺ�ѡlib������"+resultlibList.size());
				List<String> finallibList=new ArrayList<>();
				for (String libname:resultlibList) {
					if (libname.equals("fromapkAnalysis")) {
					System.out.println("�����ݿ����ҵ�ƥ���lib,��lib����apk�����������ԭ�ͣ��޷����������Է���");
				}else {
					finallibList.add(libname);
				}	
				}
			CarefulInspection(apkrootPKG,finallibList);	 
			}else {
				List<String> resultlibList=matchResultMap.get(apkrootPKG);				
				System.out.println("ƥ�䵽�ĺ�ѡlib������"+resultlibList.size());
				System.out.println("û����lib�����ҵ����ƥ�䣬ֻ��apk���������ҵ�������ͬ��ARP");
			}
			matchResultMap.get(apkrootPKG).clear();
			/**else if (apkrootPKG.contains("com.google")||apkrootPKG.contains("android.support")) {
				Map<String, List<String>> dexfileMap=DealWithSpecialPKG(apkrootPKG,matchResultMap.get(apkrootPKG));
				for (String dextypestring: dexfileMap.keySet()) {
					//System.out.println(dextypestring); 
					//SpecialCarefulInspection(apkrootPKG,dexfileMap.get(dextypestring)); 
				}
			}**/
			count++;
			/**System.out.println("���濪ʼ��ȫ�Է�����");
			String TPLPath=apkName+"\\\\smali\\\\"+apkrootPKG.replace(".", "\\\\");
			System.out.println(TPLPath);
			Set<String> allThreat =hazardfunctionAnalysis.StartThreatAnalysis(TPLPath);
		**/
			System.out.println();
			System.out.println("time: " + (System.currentTimeMillis() - current));
		}
		
	}
 
	
	private static void SpecialCarefulInspection(String apkrootPKG,List<String> dexfilename) {
		double MaxIntegrityscore=0.0;
		double MaxSimilarityscore=0.0;
		String SimilarityBestmatchLib=null;
		String IntegrityBestmatchLib=null;
		double SimilaritymatchLib=0.0;
		double IntegritymatchLib=0.0;
		List<String> subPCKset=rootPackageMap.get(apkrootPKG);
		for(String libname:dexfilename){ 
    		String libPath="D:\\\\���е�dex����\\\\"+libname;
            Lib lib = Lib.loadFromFile(libPath);
            if (lib == null) {
                fail("lib not parsed");
            }
            LibProfile libProfile = LibProfile.create(lib, targetSdkClassNameSet); 
            double Integrityscore=0.0;
    		double Similarityscore=0.0;
            Map<Double,Double> scoreMap=SpecialSingleapkSinglelib(subPCKset,libProfile);
            System.out.println(libname+"---"+scoreMap.keySet());
            for (double astring : scoreMap.keySet()) {
                Similarityscore=astring;
        		Integrityscore=scoreMap.get(astring); 
			}
              if (MaxSimilarityscore<Similarityscore) {
            	  MaxSimilarityscore=Similarityscore;
            	  SimilarityBestmatchLib=libname;
            	  IntegritymatchLib=Integrityscore;
			}
              if (MaxIntegrityscore<Integrityscore) {
            	  MaxIntegrityscore=Integrityscore;
            	  IntegrityBestmatchLib=libname;
            	  SimilaritymatchLib=Similarityscore;
			} 
		}
		if (SimilarityBestmatchLib!=null&MaxSimilarityscore>0.3) {
			String libPath="D:\\\\���е�dex����\\\\"+SimilarityBestmatchLib;
	        Lib lib = Lib.loadFromFile(libPath);
	        if (lib == null) {
	            fail("lib not parsed");
	        }
	        LibProfile libProfilematch = LibProfile.create(lib, targetSdkClassNameSet);
	         int matchsubpkgsum=(int) (MaxIntegrityscore*libProfilematch.packageProfileMap.size());
			System.out.println("�ҵ���ø�����ƥ���lib��");
			 System.out.println(SimilarityBestmatchLib);
			 System.out.println("apk�Ӱ�����"+subPCKset.size()+"     lib�Ӱ�����"+libProfilematch.packageProfileMap.size()+"     �����Է�����"+matchsubpkgsum+"/"+libProfilematch.packageProfileMap.size());
			 System.out.println("�����Է�����"+MaxSimilarityscore);	
			 System.out.println("�����Է�����"+IntegritymatchLib);
				writer.println(apkName+"*----RT----*"+apkrootPKG+"*----LIB----*"+SimilarityBestmatchLib+"*----MS----*"+MaxSimilarityscore+"*----IM----*"+IntegritymatchLib);   
			      writer.flush(); 
			 System.out.println(apkName+"*----RT----*"+apkrootPKG+"*----LIB----*"+SimilarityBestmatchLib+"*----MS----*"+MaxSimilarityscore+"*----IM----*"+IntegritymatchLib);
				if (IntegritymatchLib!=MaxIntegrityscore) {
				System.out.println("��������Է�����");
			    System.out.println(apkrootPKG+"------"+IntegrityBestmatchLib+":"+MaxIntegrityscore+"�������Է�����"+SimilaritymatchLib);				
				}
		}
		
	}
	private static Map<Double, Double> SpecialSingleapkSinglelib(List<String> subPCKset, LibProfile libProfile) {
		Map<String, LibPackageProfile> libPackageProfileMap=libProfile.packageProfileMap;
		Map<Double,Double> scoreMap=new HashMap<>();
		double libsim=0;
    	double libweightsum = 0;
    	int Matchnum=0;
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	Map<String, Double> classRawSimilarity0= new HashMap<>();
    	Map<String, Double> libpkgweighted= new HashMap<>();
    	Map<String, String> classNameMap0 = new HashMap<>();
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	double pkgweight=libPackageProfile.getPackageWeight1();
        	libweightsum+=pkgweight;
        	libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);                  
        }
        for (String libpkgnamestring : libpkgname) {
			if (subPCKset.contains(libpkgnamestring)) {
				Matchnum++;
			}
		}
        double MatchScore=(double)Matchnum/libpkgname.size();
        if (MatchScore<0.3) {
        	scoreMap.put(MatchScore, MatchScore);
        	return scoreMap;
		}
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()){   
        	double pkgweight=libPackageProfile.getPackageWeight1();
        	double weighted=pkgweight/libweightsum;
        	libpkgweighted.put(libPackageProfile.packageName, weighted);        	
        	double maxsim=0;
        	String matchpkgname = null;
        	for (ApkPackageProfile apkPackageProfile :  apkPackageProfileMapcom.values()) {
        		if (subPCKset.contains(apkPackageProfile.packageName)&apkPackageProfile.packageName.equals(libPackageProfile.packageName)) {
        			Map<String, String> classNameMap = new HashMap<>();
        	        Map<String, Double> classRawSimilarity = new HashMap<>();
        	        double similarity=rawPackageSimilaritythird(libPackageProfile,apkPackageProfile, classNameMap, classRawSimilarity);
        	        if(similarity>=0){
        	        	if(maxsim<similarity||maxsim==similarity){
              	    	   maxsim=similarity;
              	    	   matchpkgname=apkPackageProfile.packageName;             	    	   
              	       }
         		}	
				}	        
        	}
        	if(maxsim==0){
        		matchpkgname=null;
        	}
	        	
        	if(maxsim>0){
            	classRawSimilarity0.put(matchpkgname, maxsim);//matchpkgname��apk����
            	classNameMap0.put(matchpkgname, libPackageProfile.packageName);
        	}
        } 
        for(String pkgname:classRawSimilarity0.keySet()){
            String libnameString=classNameMap0.get(pkgname);
        	//System.out.println("apk����"+pkgname+"---"+"lib����"+libnameString);
        	libsim+=classRawSimilarity0.get(pkgname)*libpkgweighted.get(libnameString);
      }
        double IntegrityScore=(double)classRawSimilarity0.size()/libpkgname.size();
        //System.out.println(IntegrityScore);
        //if (libsim!=0) {
          //  System.out.println("apk�Ӱ�����"+subPCKset.size()+"     lib�Ӱ�����"+libPackageProfileMap.size()+"     �����Է�����"+classRawSimilarity0.size()+"/"+subPCKset.size());
		//}
        scoreMap.put(libsim,IntegrityScore);
		return scoreMap;
	}
	private static Map<String, List<String>> DealWithSpecialPKG(String apkrootPKG, List<String> dexfilename) {
		Map<String, List<String>> dexfileMap=new HashMap<>();
		List<String> dextype=new ArrayList<>();
		for (String dexfile : dexfilename) {
			String description = Pattern.compile("[\\d]").matcher(dexfile).replaceAll("");
			description=description.substring(0, description.indexOf(".")-1); 
			if (!dextype.contains(description)) {
				//System.out.println(description+"�����");
				dextype.add(description);
			} 
		}
		for (String dextypestring : dextype) {
			List<String> classfyList=new ArrayList<>();
			int num=0;
			for (String dexfilestring : dexfilename) {
				if (dexfilestring.contains(dextypestring)&num<10) {
					//System.out.println(dexfilestring+"�����");
					classfyList.add(dexfilestring);
					num++;
				}
			}			
			dexfileMap.put(dextypestring, classfyList);
		}
		return dexfileMap;
	}
	private static void CarefulInspection(String apkrootPKG, List<String> dexfilename) {
		//System.out.println("CarefulInspection");
		double MaxIntegrityscore=0.0;
		double MaxSimilarityscore=0.0;
		String SimilarityBestmatchLib=null;
		String IntegrityBestmatchLib=null;
		double SimilaritymatchLib=0.0;
		double IntegritymatchLib=0.0;
		List<String> subPCKset=rootPackageMap.get(apkrootPKG);
		Map<String, List<String>> libTypeMap=new HashMap<>();
		System.out.println("subnum:"+subPCKset.size()); 
		//System.out.println(subPCKset);
		if (apkPackageProfileMap.get(apkrootPKG)!=null) {
			//System.out.println("includeClassNum:"+apkPackageProfileMap.get(apkrootPKG).includeClassNum);	
		}
		for (String libname:dexfilename) {
            String description = Pattern.compile("[\\d]").matcher(libname).replaceAll("");
            if (!libTypeMap.keySet().contains(description)) {
                libTypeMap.put(description, new ArrayList<String>());
			}
            libTypeMap.get(description).add(libname);
            //System.out.println("ɾ���汾�ź�lib��:"+description);
		}
		third:for (String libType : libTypeMap.keySet()) {
			second: for(String libname:libTypeMap.get(libType)){
				try {
		    		String libPath="I:\\\\����dex\\\\dex����\\\\"+libname;
		            Lib lib = Lib.loadFromFile(libPath);
		            if (lib == null) {
		                fail("lib not parsed");
		            }
		            LibProfile libProfile =  LibProfile.create(lib, targetSdkClassNameSet); 
		            double Integrityscore=0.0;
		    		double Similarityscore=0.0;
		            Map<Double,Double> scoreMap=SingleapkSinglelib(subPCKset,libProfile);
		            for (double astring : scoreMap.keySet()) {
		                Similarityscore=astring;
		        		Integrityscore=scoreMap.get(astring);
		        		System.out.println(libname+"---"+astring); 
		               if (astring>0.6) {
		            	   System.out.println(libname+"-*-*-"+astring); 
		            	   //System.out.println("����ѭ��");
						//break second;
					}
					}
		            scoreMap.clear();;
		              if (MaxSimilarityscore<Similarityscore) {
		            	  MaxSimilarityscore=Similarityscore; 
		            	  SimilarityBestmatchLib=libname;
		            	  IntegritymatchLib=Integrityscore;
					}
		              if (MaxIntegrityscore<Integrityscore) {
		            	  MaxIntegrityscore=Integrityscore;
		            	  IntegrityBestmatchLib=libname;
		            	  SimilaritymatchLib=Similarityscore;
					} 
		               if (MaxSimilarityscore>=0.9) {
		            	   System.out.println("����ѭ��");
						break third; 
					}
		              if (MaxSimilarityscore>0.8&&dexfilename.size()>200) {
		            	  System.out.println("����ѭ��");
							break third; 
					}		              
		              if (MaxSimilarityscore>0.8&&dexfilename.size()>1000) {
		            	  System.out.println("����ѭ��");
							break third; 
					}
		          	libProfile=null;
		          	lib=null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}

		if (SimilarityBestmatchLib!=null) {
			String libPath="I:\\\\����dex\\\\dex����\\\\"+SimilarityBestmatchLib;
	        Lib lib = Lib.loadFromFile(libPath);
	        if (lib == null) {
	            fail("lib not parsed");
	        }
	         int matchsubpkgsum=(int) (MaxIntegrityscore*subPCKset.size());
			LibProfile libProfilematch = LibProfile.create(lib, targetSdkClassNameSet);
			System.out.println("�ҵ���ø�����ƥ���lib��");
			 System.out.println(SimilarityBestmatchLib);
			 System.out.println("apk�Ӱ�����"+subPCKset.size()+"     lib�Ӱ�����"+libProfilematch.packageProfileMap.size()+"     �����Է�����"+matchsubpkgsum+"/"+subPCKset.size());
			 System.out.println("�����Է�����"+MaxSimilarityscore);	 
			 System.out.println("�����Է�����"+IntegritymatchLib);
				writer.println(apkName+"*----RT----*"+apkrootPKG+"*----LIB----*"+SimilarityBestmatchLib+"*----MS----*"+MaxSimilarityscore+"*----IM----*"+IntegritymatchLib);   
			      writer.flush(); 
			 System.out.println(apkName+"*----RT----*"+apkrootPKG+"*----LIB----*"+SimilarityBestmatchLib+"*----MS----*"+MaxSimilarityscore+"*----IM----*"+IntegritymatchLib);
				if (IntegritymatchLib!=MaxIntegrityscore) {
				System.out.println("��������Է�����");
			    System.out.println(apkrootPKG+"------"+IntegrityBestmatchLib+":"+MaxIntegrityscore+"�������Է�����"+SimilaritymatchLib);				
				}
		}else {
			System.out.println("�����ݿ����ҵ�ƥ���lib���������ƶ�Ϊ0���������������ο���");
			/**for (String string : dexfilename) {
				System.out.println(string);
			}**/
			//rootanalysisMap(apkrootPKG);//����ο�����
		}


		 }
	 
	
	private static Map<Double,Double> SingleapkSinglelib(List<String> subPCKset, LibProfile libProfile) {
		Map<String, LibPackageProfile> libPackageProfileMap=libProfile.packageProfileMap;
		Map<Double,Double> scoreMap=new HashMap<>();
		double libsim=0;
    	double libweightsum = 0;
    	double apkweightsum = 0;
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	Map<String, Double> classRawSimilarity0= new HashMap<>();
    	Map<String, Double> libpkgweighted= new HashMap<>();
    	Map<String, Double> apkpkgweight= new HashMap<>();
    	Map<String, String> classNameMap0 = new HashMap<>();
    	for (ApkPackageProfile apkPackageProfile1 :  apkPackageProfileMapcom.values()) {
    		if (subPCKset.contains(apkPackageProfile1.packageName)) {
    			//System.out.println(apkPackageProfile1.packageName+apkPackageProfile1.getPackageWeight());
    			if (!apkpkgweight.keySet().contains(apkPackageProfile1.packageName)) {
    				apkweightsum+=apkPackageProfile1.getPackageWeight();
    				apkpkgweight.put(apkPackageProfile1.packageName, apkPackageProfile1.getPackageWeight());
    				//System.out.println(apkweightsum);
				}
    		}
    	}
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	double pkgweight=libPackageProfile.getPackageWeight1();
        	libweightsum+=pkgweight;
        	libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);                  
        }
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()){   
        	double pkgweight=libPackageProfile.getPackageWeight1();
        	double weighted=pkgweight/libweightsum;
        	libpkgweighted.put(libPackageProfile.packageName, weighted);        	
        	double maxsim=0;
        	String matchpkgname = null;
        	for (ApkPackageProfile apkPackageProfile :  apkPackageProfileMapcom.values()) {
        		if (subPCKset.contains(apkPackageProfile.packageName)) {
        			Map<String, String> classNameMap = new HashMap<>();
        	        Map<String, Double> classRawSimilarity = new HashMap<>();
        	        double similarity=rawPackageSimilaritythird(libPackageProfile,apkPackageProfile, classNameMap, classRawSimilarity);
        	        if(similarity>=0){
        	        	if(maxsim<similarity||maxsim==similarity){
              	    	   maxsim=similarity;
              	    	   matchpkgname=apkPackageProfile.packageName;             	    	   
              	       }
         		}	 
				}	        
        	}
        	if(maxsim==0){
        		matchpkgname=null;
        	}
	        	
        	if(maxsim>0){
            	classRawSimilarity0.put(matchpkgname, maxsim);//matchpkgname��apk����
            	classNameMap0.put(matchpkgname, libPackageProfile.packageName);
        	}
        	//System.out.println(libPackageProfile.packageName+"***"+maxsim+"***"+matchpkgname);
        }
        for(String pkgname:classRawSimilarity0.keySet()){ 
            String libnameString=classNameMap0.get(pkgname);
        	System.out.println("apk����"+pkgname+"---"+"  apk��Ȩ�أ�"+apkpkgweight.get(pkgname)+"  apk��Ȩ����ֵ��"+apkweightsum+"  lib����"+libnameString);
        	System.out.println("apk����lib��������ֵ��"+classRawSimilarity0.get(pkgname));
        	System.out.println(libPackageProfileMap.get(libnameString).includeClassNum+"/"+apkPackageProfileMap.get(pkgname).includeClassNum);
        	libsim+=classRawSimilarity0.get(pkgname)*libpkgweighted.get(libnameString);//��lib�İ�Ȩ�ؼ���
        	//System.out.println("libsim:"+libsim+"  libpkgweighted.get(libnameString):"+libpkgweighted.get(libnameString));
        	//libsim+=classRawSimilarity0.get(pkgname)*apkpkgweight.get(pkgname)/apkweightsum;//��apk�İ�Ȩ�ؼ���
        	//System.out.println("libsim:"+libsim+"  apkpkgweight.get(pkgname)/apkweightsum:"+apkpkgweight.get(pkgname)/apkweightsum);
      }
        double IntegrityScore=(double)classRawSimilarity0.size()/subPCKset.size();
        //System.out.println(IntegrityScore);
        if (libsim!=0) {
            System.out.println("apk�Ӱ�����"+subPCKset.size()+"     lib�Ӱ�����"+libPackageProfileMap.size()+"     �����Է�����"+classRawSimilarity0.size()+"/"+subPCKset.size());
		}
        scoreMap.put(libsim,IntegrityScore);
        libPackageProfileMap.clear();
        libPackageProfileMap=null;
		return scoreMap; 
	}
	
	
	
    private static Set<String> getFiles(String path) {
        Set<String> files = new HashSet<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {		        
                //�ļ�����������·��tempList[i].toString()
            	files.add(tempList[i].toString().replace("\\", "\\\\"));
            	System.out.println(tempList[i].toString().replace("\\", "\\\\"));
            }
            if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
            }
        } 
        return files;  
    }
	
    private static List<String> getFilestemp(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {		        
                //�ļ�����������·��tempList[i].toString()
            	files.add(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\"),tempList[i].toString().indexOf(".")));
            	System.out.println(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\"),tempList[i].toString().indexOf(".")));
            }
            if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
            }
        } 
        return files;
    } 
    
	public static double rawPackageSimilaritythird(LibPackageProfile libPackageProfile, ApkPackageProfile apkPackageProfile,
            /*as return value*/Map<String, String> classNameMap, /*as return value*/Map<String, Double> classRawSimilarity) {
List<String> ClassList = libPackageProfile.getWeightClassList();
      for (String libClassName : ClassList) {
SimpleClassProfile simpleLibClassProfile = libPackageProfile.classProfileMap.get(libClassName);
      double RAW_CLASS_SIMILARITY_THRESHOLD = getClassMatchSimilarityThreshold(simpleLibClassProfile);

SimpleClassProfile bestMatchApkClassProfile = null;
        double maxSimilarity = 0;

for (SimpleClassProfile apkClassProfile : apkPackageProfile.classProfileMap.values()) {
if (classNameMap.values().contains(apkClassProfile.getClassName()))
continue;

double similarity = ProfileComparator.rawClassSimilarity(simpleLibClassProfile, apkClassProfile);
if (similarity >= RAW_CLASS_SIMILARITY_THRESHOLD) {
if (similarity > maxSimilarity) {
maxSimilarity = similarity;
bestMatchApkClassProfile = apkClassProfile;
}
}
}

if (bestMatchApkClassProfile != null) {
classNameMap.put(libClassName, bestMatchApkClassProfile.getClassName());
classRawSimilarity.put(libClassName, maxSimilarity);
}
else {
classNameMap.put(libClassName, null);
classRawSimilarity.put(libClassName, 0.0);
} 
//System.out.println(libClassName+"***"+bestMatchApkClassProfile+"***" +maxSimilarity);
}
    //  System.out.println("classRawSimilarity.size():"+classRawSimilarity.size());
double Similarity = 0.0;
double libSimilarity = 0.0;
for (String libClassName : ClassList) {
if (LibPeckerConfig.DEBUG_LIBPECKER) {
if (libPackageProfile.packageName.equals(LibPeckerConfig.DEBUG_LIBPECKER_LIB_PKG_NAME)
&& apkPackageProfile.packageName.equals(LibPeckerConfig.DEBUG_LIBPECKER_APK_PKG_NAME)) {
System.out.println("\t class name: "+libClassName);
System.out.println("\t\t class weight: "+libPackageProfile.getClassWeight(libClassName));
System.out.println("\t\t class similarity: "+classRawSimilarity.get(libClassName));
System.out.println("\t\t class match: "+classNameMap.get(libClassName));
}
}
if (classNameMap.get(libClassName)!=null) {
	//System.out.println("Similarity:"+Similarity);
	//System.out.println(classRawSimilarity.get(libClassName)+"+"+apkPackageProfile.getClassWeight(classNameMap.get(libClassName))+"+"+libPackageProfile.getClassWeight(libClassName));
	Similarity += classRawSimilarity.get(libClassName)*apkPackageProfile.getClassWeight(classNameMap.get(libClassName));	
	//System.out.println("Similarity:+"+Similarity);
}  
//if (classNameMap.get(libClassName)!=null) {
	//System.out.println("libSimilarity:"+libSimilarity);
	//System.out.println(classRawSimilarity.get(libClassName)+"+"+apkPackageProfile.getClassWeight(classNameMap.get(libClassName))+"+"+libPackageProfile.getClassWeight(libClassName));
	//libSimilarity += classRawSimilarity.get(libClassName)*libPackageProfile.getClassWeight(libClassName);	
	//System.out.println("libSimilarity:+"+libSimilarity);
//} 
}
//System.out.println("��������Similarity"+Similarity);
//System.out.println("��������libSimilarity"+libSimilarity);
//return Similarity>libSimilarity?Similarity:libSimilarity;
return Similarity;
}
    private static double getClassMatchSimilarityThreshold(SimpleClassProfile libClassProfile) {
        int memberCount = 1 + libClassProfile.getMethodHashList().size() + libClassProfile.getFieldHashList().size();
        if (memberCount <= 5)
            return 1.0;
        if (memberCount <= 10)
            return 0.9;
        if (memberCount <= 15)
            return 0.8;
        if (memberCount <= 20)
            return 0.7;
        if (memberCount <= 25)
            return 0.6;
        else
            return 0.5;
    }
}
