package groundtruth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import cn.fudan.libpecker.core.ParseApkTest;
import cn.fudan.libpecker.core.ProfileComparator;
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
import njust.lib.Service.ApkLibService;

public class matchpkgname {

    Set<String> targetSdkClassNameSet;
    LibProfile libProfile;
    List<String> libClassBasicSigList = new ArrayList<>();
    List<String> apkClassBasicSigList = new ArrayList<>();
    public Map<String, ApkPackageProfile> apkPackageProfileMap;//pkg name -> ApkPackageProfile
    public Map<String, LibPackageProfile> libPackageProfileMap;//pkg name -> LibPackageProfile
    static Map<String, Integer>set=new HashMap<>();

    public matchpkgname(LibProfile libProfile, ApkProfile apkProfile, Set<String> targetSdkClassNameSet) {
        this.targetSdkClassNameSet = targetSdkClassNameSet;
        this.libProfile = libProfile;
        this.apkPackageProfileMap = apkProfile.packageProfileMap;
        this.libPackageProfileMap = this.libProfile.packageProfileMap;
    }
	
    private static void fail(String message) {
        System.err.println(message);
        System.exit(0);
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
    
    
    public static double rawPackageSimilarity(LibPackageProfile libPackageProfile, ApkPackageProfile apkPackageProfile,
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
    
    public double calculatelibapkpkgsim(ApkPackageProfile apkPackageProfile,LibPackageProfile libPackageProfile){
	        int matchLibClassBasicHashSize = 0;
        for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values())           	
            apkClassBasicSigList.add(simpleClassProfile.getClassHashStrict());        
        for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values()){
        	 libClassBasicSigList.add(simpleClassProfile.getClassHashStrict());           	 
        }          
        //System.out.println("apkClassBasicSigList�Ĵ�С��"+apkClassBasicSigList.size());
        //System.out.println("libClassBasicSigList�Ĵ�С��"+libClassBasicSigList.size());
        for (String basicClassHash : libClassBasicSigList) {
            if (apkClassBasicSigList.contains(basicClassHash)) {
                matchLibClassBasicHashSize ++;
                apkClassBasicSigList.remove(basicClassHash);
            }
        }
        //System.out.println("matchLibClassBasicHashSize �Ĵ�С��"+matchLibClassBasicHashSize);
        double classBasicHashRatioUpperBound = 1.0*matchLibClassBasicHashSize/libClassBasicSigList.size();
        
	return classBasicHashRatioUpperBound;
	
}
    
    public  Map<String, String> calculateMaxProbabilitytest010(){//�����ǵ����Ƚϰ���&���ƶȼ�����ȷ��apk---lib�Ե�
    	double libsim=0;
    	List<ApkPackageProfile>apkPackageProfileset=new ArrayList<>();
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>apkpkgname=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	List<SimpleClassProfile>libSimpleClassProfile=new ArrayList<>();
    	List<SimpleClassProfile>apkSimpleClassProfile=new ArrayList<>();
    	Map<String, Double> classRawSimilarity0= new HashMap<>();
    	Map<String, String> pkgNameMap = new HashMap<>();
    	System.out.println("�����ǵ�һ����ͨ��ƥ����������ҵ���lib�Ƿ������apk�У�");
        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
        	apkpkgname.add(apkPackageProfile.packageName);
        	apkPackageProfileset.add(apkPackageProfile);
            for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values()) {
                apkClassBasicSigList.add(simpleClassProfile.getClassHashStrict());
                apkSimpleClassProfile.add(simpleClassProfile);
            }

        }
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);
            for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values()){
            	 libClassBasicSigList.add(simpleClassProfile.getClassHashStrict());  
            	 libSimpleClassProfile.add(simpleClassProfile);
            }                      
        }
        
        int matchpkgcount=0;
        for(String a:libpkgname){
        	for(String b:apkpkgname){
        		if(b.equals(a)){
               		pkgNameMap.put(a, b);
               		 matchpkgcount++;
               		 System.out.println("ƥ�䵽��lib-apk����---lib����"+a+"---apk��"+b);
               	 }
        	}
       	 
        }
        
        for(LibPackageProfile a:libPackageProfileset){
       	// System.out.println("lib����"+a);
       	 for(ApkPackageProfile b:apkPackageProfileset){
       		 if(a.packageName.equals(b.packageName)){
       			 double sim=calculatelibapkpkgsim(b,a);
       			 Map<String, String> classNameMap = new HashMap<>();
     	        Map<String, Double> classRawSimilarity = new HashMap<>();
     	        double sim0=rawPackageSimilarity(a,b, classNameMap, classRawSimilarity);
     	        if(sim0>sim){
     	        	sim=sim0;
     	        }
       			 libsim+=sim;
       			 System.out.println("lib����"+a.packageName+"��ƥ�䵽��apk����"+b.packageName+"���ƶ�Ϊ��"+sim);
       		 }
       		 
       	 }
        }
        if(matchpkgcount==libpkgname.size()){       	
        	 System.out.println("---------��һ����������lib���а����������ڸ�apk��--------------");
        	 return pkgNameMap;
        }
 
        else{
        	System.out.println("-------------��һ��������û���ڸ�apk���ҵ���apk--lib��-----------------");
        	return null;
        }

        
	
    }
	
	
    public  Map<String, String> calculateMaxProbabilitytest00(){//�����ǵ����Ƚϰ���ȷ��apk---lib�Ե�
    	double libsim=0;
    	List<ApkPackageProfile>apkPackageProfileset=new ArrayList<>();
    	List<LibPackageProfile>libPackageProfileset=new ArrayList<>();
    	List<String>apkpkgname=new ArrayList<>();
    	List<String>libpkgname=new ArrayList<>();
    	List<SimpleClassProfile>libSimpleClassProfile=new ArrayList<>();
    	List<SimpleClassProfile>apkSimpleClassProfile=new ArrayList<>();
    	Map<String, Double> classRawSimilarity0= new HashMap<>();
    	Map<String, String> pkgNameMap = new HashMap<>();
    	System.out.println("�����ǵ�һ����ͨ��ƥ����������ҵ���lib�Ƿ������apk�У�");
        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
        	apkpkgname.add(apkPackageProfile.packageName);
        	apkPackageProfileset.add(apkPackageProfile);
            for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values()) {
                apkClassBasicSigList.add(simpleClassProfile.getClassHashStrict());
                apkSimpleClassProfile.add(simpleClassProfile);
            }

        }
        for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
        	libPackageProfileset.add(libPackageProfile);
        	libpkgname.add(libPackageProfile.packageName);
            for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values()){
            	 libClassBasicSigList.add(simpleClassProfile.getClassHashStrict());  
            	 libSimpleClassProfile.add(simpleClassProfile);
            }                      
        }
        
        int matchpkgcount=0;
        for(String a:libpkgname){
        	for(String b:apkpkgname){
        		if(b.equals(a)){
               		pkgNameMap.put(a, b);
               		 matchpkgcount++;
               		 System.out.println("ƥ�䵽��lib-apk����---lib����"+a+"---apk��"+b);
               	 }
        	}
       	 
        }
        if(matchpkgcount==libpkgname.size()){       	
        	 System.out.println("---------��һ����������lib���а����������ڸ�apk��--------------");
        	 return pkgNameMap;
        }
 
        else{
        	System.out.println("-------------��һ��������û���ڸ�apk���ҵ���apk--lib��-----------------");
        	return null;
        }

        
	
    }
	
	
	
	 public static void main(String[] args) throws IOException, XmlPullParserException {
	    	Map<String, String> pkgNameMap=new HashMap<>();
	        String apkPath = "C:\\Users\\ZJY\\Desktop\\23.apk";
	        getdexfilepath aa=new getdexfilepath();
	        List<String> dexfilepath=new ArrayList<>();
	    	dexfilepath.addAll(aa.traverseFolder1("G:\\libdetectionʵ���׼�ɼ�\\lib"));
	    	long current = System.currentTimeMillis();
	        Sdk sdk = Sdk.loadDefaultSdk();
	        if (sdk == null) {
	            fail("default sdk not parsed");
	        }
	        Apk apk = Apk.loadFromFile(apkPath);
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
	            matchpkgname pecker = new matchpkgname(libProfile, apkProfile, targetSdkClassNameSet);
	            if(pecker.calculateMaxProbabilitytest00()!=null){
	            	pkgNameMap.put(libPath.substring(libPath.lastIndexOf("\\")).substring(1),apkPath.substring(apkPath.lastIndexOf("\\")).substring(1));
	            }
	        	        	
	        	System.out.println("--------------"+libPath.substring(libPath.lastIndexOf("\\"))+"\\�������--------------");	        		        	
	        }
	    	System.out.println("���ռ����Ϊ��");
	    	for(String aaa:pkgNameMap.keySet()){
	    		//apkLibService.addapklib(pkgNameMap.get(aaa), aaa);
				//System.out.println("�����������");
	    		System.out.println("apk��---lib����"+pkgNameMap.get(aaa)+"------"+aaa);
	    	}	    
	        System.out.println("time: " + (System.currentTimeMillis() - current));
	    }

}
