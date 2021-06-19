package groundtruth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import cn.fudan.libpecker.core.ParseApkTest;
import cn.fudan.libpecker.core.ProfileComparator;
import cn.fudan.libpecker.main.ProfileBasedLibPecker;
import cn.fudan.libpecker.main.defanaly;
import cn.fudan.libpecker.main.getdexfilepath;
import cn.fudan.libpecker.model.ApkPackageProfile;
import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.LibPackageProfile;
import cn.fudan.libpecker.model.LibProfile;
import cn.fudan.libpecker.model.SimpleClassProfile;
import cn.njust.common.Apk;
import cn.njust.common.Lib;
import cn.njust.common.LibPeckerConfig;
import cn.njust.common.Sdk;
import njust.lib.Service.ApkLibInfosService;
import njust.lib.Service.ApkLibService;
import njust.lib.Service.ApkLibinfoService;

public class calpkgsim {

    //Set<String> targetSdkClassNameSet;
    //LibProfile libProfile;
    //List<String> libClassBasicSigList = new ArrayList<>();
    //List<String> apkClassBasicSigList = new ArrayList<>();
   // public Map<String, ApkPackageProfile> apkPackageProfileMap;//pkg name -> ApkPackageProfile
    //public Map<String, LibPackageProfile> libPackageProfileMap;//pkg name -> LibPackageProfile
    //static Map<String, Integer>set=new HashMap<>();

    /**public calpkgsim(LibProfile libProfile, ApkProfile apkProfile, Set<String> targetSdkClassNameSet) {
        this.targetSdkClassNameSet = targetSdkClassNameSet;
        this.libProfile = libProfile;
        this.apkPackageProfileMap = apkProfile.packageProfileMap;
        this.libPackageProfileMap = this.libProfile.packageProfileMap;
    }**/
	
    private static void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
    
    private  double getClassMatchSimilarityThreshold(SimpleClassProfile libClassProfile) {
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
    
    
    public  double rawPackageSimilarity(LibPackageProfile libPackageProfile, ApkPackageProfile apkPackageProfile,
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
}

double Similarity = 0.0;
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
Similarity += classRawSimilarity.get(libClassName)/classRawSimilarity.size();//*libPackageProfile.getClassWeight(libClassName);
}

return Similarity;
}
    
    /**public double calculatelibapkpkgsim(ApkPackageProfile apkPackageProfile,LibPackageProfile libPackageProfile){
	        int matchLibClassBasicHashSize = 0;
        for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values())           	
            apkClassBasicSigList.add(simpleClassProfile.getClassHashStrict());        
        for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values()){
        	 libClassBasicSigList.add(simpleClassProfile.getClassHashStrict());           	 
        }          
        //System.out.println("apkClassBasicSigList的大小："+apkClassBasicSigList.size());
        //System.out.println("libClassBasicSigList的大小："+libClassBasicSigList.size());
        for (String basicClassHash : libClassBasicSigList) {
            if (apkClassBasicSigList.contains(basicClassHash)) {
                matchLibClassBasicHashSize ++;
                apkClassBasicSigList.remove(basicClassHash);
            }
        }
        //System.out.println("matchLibClassBasicHashSize 的大小："+matchLibClassBasicHashSize);
        double classBasicHashRatioUpperBound = 1.0*matchLibClassBasicHashSize/libClassBasicSigList.size();
        
	return classBasicHashRatioUpperBound;
	
}**/
    /**
     * 下面的函数是筛选出matchpkgcount==libpkgname.size()&libsim>0.8.
     */

    public  Map<String, String> calculateMaxProbabilitytest010(Map<String, ApkPackageProfile> apkPackageProfileMap,Map<String, LibPackageProfile> libPackageProfileMap){//这里是比较包名&相似度极高来确定apk---lib对的
    	double libsim=0;
    	List<ApkPackageProfile>apkPackageProfileset=new ArrayList<>();
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>apkpkgname=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	List<SimpleClassProfile>libSimpleClassProfile=new ArrayList<>();
    	List<SimpleClassProfile>apkSimpleClassProfile=new ArrayList<>();
    	Map<String, Double> classRawSimilarity0= new HashMap<>();
    	Map<String, String> pkgNameMap = new HashMap<>();
    	//System.out.println("这里是第一步，通过匹配包名快速找到该lib是否存在于apk中：");
        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
        	apkpkgname.add(apkPackageProfile.packageName);
        	apkPackageProfileset.add(apkPackageProfile);
            for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values()) {
               // apkClassBasicSigList.add(simpleClassProfile.getClassHashStrict());
                apkSimpleClassProfile.add(simpleClassProfile);
            }

        }
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);
            for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values()){
            	 //libClassBasicSigList.add(simpleClassProfile.getClassHashStrict());  
            	 libSimpleClassProfile.add(simpleClassProfile);
            }                      
        }
        
        int matchpkgcount=0;
        for(String a:libpkgname){
        	for(String b:apkpkgname){
        		if(b.equals(a)){
               		pkgNameMap.put(a, b);
               		 matchpkgcount++;
               		 //System.out.println("匹配到的lib-apk包：---lib包："+a+"---apk包"+b);
               	 }
        	}
       	 
        }
        if(matchpkgcount==libpkgname.size()){
            for(LibPackageProfile a:libPackageProfileset){
               	// System.out.println("lib包名"+a);
               	 for(ApkPackageProfile b:apkPackageProfileset){
               		 if(a.packageName.equals(b.packageName)){
               			 //double sim=calculatelibapkpkgsim(b,a);
               			 Map<String, String> classNameMap = new HashMap<>();
             	        Map<String, Double> classRawSimilarity = new HashMap<>();
             	        double sim0=rawPackageSimilarity(a,b, classNameMap, classRawSimilarity);
             	      /**  if(sim0>sim){
             	        	sim=sim0;
             	        }**/
               			 libsim+=sim0;
               			// System.out.println("lib包："+a.packageName+"和匹配到的apk包："+b.packageName+"相似度为："+sim0);
               		 }
               		 
               	 }
                }
        }

        libsim=libsim/matchpkgcount;
        if(matchpkgcount==libpkgname.size()&libsim>0.8){       
        	
        	 //System.out.println("---------第一步结束，该lib所有包名都存在于该apk中,而且相似度高达"+libsim+"--------------");
        	 return pkgNameMap;
        }
        if(matchpkgcount==libpkgname.size()&libsim<0.8){       
        	
       	 //System.out.println("---------第一步结束，虽然该lib所有包名都存在于该apk中,但是相似度达不到阈值，只有"+libsim+"--------------");
       	 return null;
       }
        if(matchpkgcount!=0){       
        	
          	// System.out.println("---------第一步结束，只有部分lib在apk中，不能完全匹配到包名--------------");
          	 return null;
          }
        else{
        	//System.out.println("-------------第一步结束，没有在该apk中找到apk--lib对-----------------");
        	return null;
        }

        
	
    }
   
	
  //下面的函数是单纯比较包名确定apk---lib对的
    public  boolean calculateMaxProbabilitytest00(Map<String, ApkPackageProfile> apkPackageProfileMap,Map<String, LibPackageProfile> libPackageProfileMap){
    	List<ApkPackageProfile>apkPackageProfileset=new ArrayList<>();
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>apkpkgname=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	List<SimpleClassProfile>libSimpleClassProfile=new ArrayList<>();
    	List<SimpleClassProfile>apkSimpleClassProfile=new ArrayList<>();
    	//Map<String, String> pkgNameMap = new HashMap<>();
    	System.out.println("这里是第一步，通过匹配包名快速找到该lib是否存在于apk中：");
        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
        	apkpkgname.add(apkPackageProfile.packageName);
        	//apkPackageProfileset.add(apkPackageProfile);       
        }
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	//libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);
                                 
        }      
        int matchpkgcount=0;
        for(String a:libpkgname){
        	for(String b:apkpkgname){
        		if(b.equals(a)){
               		//pkgNameMap.put(a, b);
               		 matchpkgcount++;
               		 System.out.println("匹配到的lib-apk包：---lib包："+a+"---apk包"+b);
               	 }
        	}
       	 
        }
        if(matchpkgcount==libpkgname.size()){       	
        	 System.out.println("---------第一步结束，该lib所有包名都存在于该apk中--------------");
        	 return false ;
        }
 
        else{
        	System.out.println("-------------第一步结束，没有在该apk中找到与apk--lib对-----------------");
        	return true;
        }     
    }
	
	public  void execute(String apkpath1,String dexpath1) throws IOException{
    	Map<String, String> pkgNameMap=new HashMap<>();
    	Map<String, String> pkgNameMap1=new HashMap<>();
        getdexfilepath aa=new getdexfilepath();
        List<String> dexfilepath=new ArrayList<>();
        //ApkLibInfosService apkLibService1=new ApkLibInfosService();
    	dexfilepath.addAll(aa.traverseFolder1(dexpath1));//"G:\\libdetection实验标准采集\\lib340"
    	long current = System.currentTimeMillis();
        Sdk sdk = Sdk.loadDefaultSdk();
        if (sdk == null) {
            fail("default sdk not parsed");
        }
        Apk apk = Apk.loadFromFile(apkpath1);
        if (apk == null) {
            fail("apk not parsed");
        }
        Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
        ApkProfile apkProfile = ApkProfile.create(apk, targetSdkClassNameSet);
    	for(String libPath:dexfilepath){
            Lib lib = Lib.loadFromFile(libPath);
            if (lib == null) {
                fail("lib not parsed");
            }
            LibProfile libProfile = LibProfile.create(lib, targetSdkClassNameSet);
            calpkgsim pecker = new calpkgsim();
            ProfileBasedLibPecker pecker1 = new ProfileBasedLibPecker(libProfile, apkProfile, targetSdkClassNameSet);
           Map<String, ApkPackageProfile> apkPackageProfileMap= apkProfile.packageProfileMap;//pkg name -> ApkPackageProfile
           Map<String, LibPackageProfile> libPackageProfileMap= libProfile.packageProfileMap;
            if(pecker.calculateMaxProbabilitytest010(apkPackageProfileMap,libPackageProfileMap)!=null){
            	pkgNameMap.put(libPath.substring(libPath.lastIndexOf("\\")).substring(1),apkpath1.substring(apkpath1.lastIndexOf("\\")).substring(1));
            }
           // double fastmatchsim1 = pecker1.calculateMaxProbability();
    			
        	//System.out.println("--------------"+libPath.substring(libPath.lastIndexOf("\\"))+"\\进程完毕--------------");	        		        	
        }
		String filenameTemp=creatdexFile(apkpath1.substring(apkpath1.lastIndexOf("\\")).substring(1));
	
    	System.out.println("最终检测结果为：");
    	for(String aaa:pkgNameMap.keySet()){
    		//apkLibService1.addapklibbyname(pkgNameMap.get(aaa), aaa);
    		//apkLibService2.addapklibbyname(pkgNameMap.get(aaa), aaa);
			//System.out.println("插入数据完成");   		
    		System.out.println("apk名---lib名："+pkgNameMap.get(aaa)+"------"+aaa);
    		writeTxtFile("："+pkgNameMap.get(aaa)+"------"+aaa,filenameTemp);
    	}
    	/**for(String aaa:pkgNameMap1.keySet()){  		
    		System.out.println("apk名---lib名："+pkgNameMap1.get(aaa)+"------"+aaa);
    		writeTxtFile("："+pkgNameMap1.get(aaa)+"------"+aaa+"*2",filenameTemp);
    	}**/
        System.out.println("time: " + (System.currentTimeMillis() - current));
        
    }
	public  String creatdexFile(String name) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp = "E:/LibDetect实验groundtruth/运行结果/" + name + ".txt";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
	}
	public  boolean writeTxtFile(String newStr,String filenameTemp) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "\r\n";
		String temp = "";
 
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
 
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
             
            
			// 保存该文件原有的内容
			for (int j = 1; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				 System.getProperty("line.separator");
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}

			buf.append(filein);
 
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}
	
	
	 public static void main(String[] args) throws IOException, XmlPullParserException {
		 //execute("G:\\libdetection实验标准采集\\APK库\\test\\0b9fa0dd7ef8b2fef57c84a83e95bb4c.apk","G:\\libdetection实验标准采集\\lib340");
			//String apkPath = null;//"G:\\libdetection实验标准采集\\APK库\\test1\\75道广东靓汤汤谱_v1.3_apkpure.com.apk"
	       // String libPath = null;//"G:\\libdetection实验标准采集\\APK插入第三方库\\lib340"
		 String apkPath0 ="G:\\libdetection实验标准采集\\APK插入第三方库\\03449.apk";
		 String libPath0="G:\\libdetection实验标准采集\\APK插入第三方库\\lib340";
		 String apkPath = null;
	        String libPath = null;

	        if (args == null || args.length == 2) {
	            apkPath = args[0];
	            libPath = args[1];
	        }
	        else {
	            fail("Usage: java -cp LibPecker3.jar cn.fudan.libpecker.mainProfileBasedLibPecker <apk_path> <lib_path>");
	        }
	       // Scanner sc = new Scanner(System.in);
	        //System.out.println("请输入apkPath");
	        //String path=sc.next();
	        //String apkPath=path.substring(0, path.indexOf("*"));
			//System.out.println("请输入libPath");
	        //String libPath=path.substring(path.indexOf("*")).substring(1);	
			//String libPath=sc.nextLine();		
	        //System.out.println(apkPath+"          "+libPath);
					calpkgsim pecker = new calpkgsim();
					pecker.execute(apkPath0,libPath0);
			    	String apkname=apkPath.substring(apkPath.lastIndexOf("\\")).substring(1);
			    	String filenameTemp=pecker.creatdexFile("03449.apk");
			    	pecker.writeTxtFile("：03449.apk------acra-4.6.2.dex*1","G:/libpecker备份/袁倩婷/LibPecker_11.28/运行结果/03449.apk.txt");
	    }
	 
	 } 
	 

