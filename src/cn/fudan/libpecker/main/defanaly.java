package cn.fudan.libpecker.main;

import cn.fudan.common.util.PackageNameUtil;
import cn.fudan.libpecker.core.LibApkMapper;
import cn.fudan.libpecker.core.PackageMapEnumerator;
import cn.fudan.libpecker.core.PackagePairCandidate;
import cn.fudan.libpecker.model.*;
import cn.fudan.libpecker.core.ParseApkTest;
import cn.njust.analysis.dep.ConcreteDepNode;
import cn.njust.analysis.dep.DepAnalysis;
import cn.njust.analysis.dep.DepEdge;
import cn.njust.analysis.dep.DepNode;
import cn.njust.analysis.tree.PackageNode;
import cn.njust.common.Apk;
import cn.njust.common.Lib;
import cn.njust.common.LibPeckerConfig;
import cn.njust.common.Sdk;
import groundtruth.calpkgsim;

import java.io.IOException;
import java.util.*;

import org.xmlpull.v1.XmlPullParserException;


public class defanaly {
	 Set<String> targetSdkClassNameSet;
	 private static Set<String> systemClassSet;
	   // LibProfile libProfile;
	    //DepAnalysis pecker1;
	    DepAnalysis pecker2;
	    public Map<String, ApkPackageProfile> apkPackageProfileMap;//pkg name -> ApkPackageProfile
	    public Map<String, List<String>> groupRoot=new HashMap<>();//�Ӱ� -> ���Ӱ���������ϵ�������Ӱ�
	    //public Map<String, LibPackageProfile> libPackageProfileMap;//pkg name -> LibPackageProfile

	    public defanaly(ApkProfile apkProfile, Set<String> targetSdkClassNameSet,DepAnalysis pecker2) {
	        this.targetSdkClassNameSet = targetSdkClassNameSet;
	        //this.libProfile = libProfile;
	        //this.pecker1=pecker1;
	        this.pecker2=pecker2;
	        this.apkPackageProfileMap = apkProfile.packageProfileMap;
	        //this.libPackageProfileMap = this.libProfile.packageProfileMap;
	    }
	    
	    public void defanalylib(){
	        List<String> libClassname = new ArrayList<>();
	        List<String> apkClassname = new ArrayList<>();
	        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
	            for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values())
	            	apkClassname.add(simpleClassProfile.getClassName());
	        }
	        //for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
	           // for (SimpleClassProfile simpleClassProfile : libPackageProfile.classProfileMap.values())
	            	//libClassname.add(simpleClassProfile.getClassName());
	        //}
	        for (String libClassname0: libClassname){
	        	//System.out.println(libClassname0);	        	
	        }
	        System.out.println("������apk������");
	        for (String apkClassname0: apkClassname){
	        	//System.out.println(apkClassname0);	        	
	        }
	    }
	    
	    public int weightcalculate(String packagename1,String packagename2){
	    	int weight=0;
	    	List<String> apkClassdependname1 = new ArrayList<>();
	    	List<String> apkClassname1 = new ArrayList<>();
	    	List<String> apkClassdependname2 = new ArrayList<>();
	    	List<String> apkClassname2 = new ArrayList<>();
	    	//apkClassdependname1.addAll(packagenametodependclassname(packagename1));
	    	//apkClassdependname2.addAll(packagenametodependclassname(packagename2));
	    	apkClassname1.addAll(packagenametoclassname(packagename1));
	    	apkClassname2.addAll(packagenametoclassname(packagename2));
	    	for(String qq:apkClassname1){
	    		if(apkClassdependname2.contains(qq))
	    			weight++;
	    	}
	    	for(String qq:apkClassname2){
	    		if(apkClassdependname1.contains(qq))
	    			weight++;
	    	}
			return weight; 	
	    }
	    public int weightcalculate111(String packagename1,String packagename2,List<String> package2classname, List<String> subpkg){
	    	Map<String, Integer> set = new HashMap<>();
	    	List<String> dependsubpkgname=new ArrayList<>();
	    	int weight=0;
	    	List<String> apkClassdependname2 = new ArrayList<>();
	    	set.putAll(packagenametodependclassname(packagename1,packagename2,subpkg));
	    	//apkClassdependname2.addAll(packagenametodependclassname(packagename2));

	    		for(String key : set.keySet()){
	    			if(key.contains(packagename1)&&!package2classname.contains(key)){
		    			weight+=set.get(key);
	    		}
	    		} 
		        for (String classnamestring : set.keySet()) {//classnamestring��packagename2����������ļ���
		            //if(classnamestring.contains(pkgname)&!classnamestring.contains(packagename2)){
		            	//System.out.println(classnamestring);
		           // }	            		        	
			        for (String astring :subpkg) {
		                if (classnamestring.contains(astring)&!dependsubpkgname.contains(astring)) {
		              	  dependsubpkgname.add(astring);            	  
		        		}
		  		}
				}
		        if (dependsubpkgname.size()!=0) {
		        	System.out.println("���Ӱ�"+packagename2+"�����������Ӱ����ϣ�");
		        	for (String astring : dependsubpkgname) {
		        		System.out.println(astring);	
					}     
			        if (groupRoot==null) {//&dependsubpkgname.size()>1
			        	groupRoot.put(packagename2, dependsubpkgname);
					}
			        else if (!groupRoot.containsValue(dependsubpkgname)) {//&dependsubpkgname.size()>1
			        	groupRoot.put(packagename2, dependsubpkgname);
					}
				}
			return weight; 	
	    }

	    //�ҵ�pkgname�������༰������Ȩ��
	    public Map<String, Integer>  packagenametodependclassname(String pkgname,String packagename2, List<String> subpkg){
	    	Map<String, Integer> set1 = new HashMap<>();	    	
	    	List<String> apkClassdependname = new ArrayList<>();
	    	List<String> apkpackagename = new ArrayList<>();
	    	List<String> apkClassname = new ArrayList<>();
	        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
	        	
	        	apkpackagename.add(apkPackageProfile.packageName);
	        	if(apkPackageProfile.packageName.contentEquals(packagename2))
	        	{	        		
	        		 for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values())
	        		 {	        			 
	 	             apkClassname.add(simpleClassProfile.getClassName());//���￪ʼ����Ӱ�����������������������
	 	            set1.putAll(pecker2.getAllDependedClassName(pkgname,packagename2,simpleClassProfile.getClassName(),subpkg));
	 	            set1.putAll(pecker2.getAllDependingClassName(pkgname,packagename2,simpleClassProfile.getClassName(),subpkg));
	        		 }
	        		 for(String test:apkClassdependname){
	        			 System.out.println(test);
	        		 }
	        	}
	        }
			return set1;
	    	
	    }
	    
	    public List<String>  packagenametoclassname(String packagename){
	    	List<String> apkClassname = new ArrayList<>();
	        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
	        	if(apkPackageProfile.packageName.contentEquals(packagename))
	        	{	        		
	        		 for (SimpleClassProfile simpleClassProfile : apkPackageProfile.classProfileMap.values())
	        		 {
	        		 //System.out.println(simpleClassProfile.getClassName());
	 	             apkClassname.add(simpleClassProfile.getClassName());
	        		 }

	        	}
	        }	    	
			return apkClassname;	    	
	    }
	    
	    public Map<String, Integer> defanalypackage(DepAnalysis pecker2,String rootpkgname){
	    	List<String> subpkg1=null;
	        List<String> libpackagename = new ArrayList<>();
	        List<String> apkpackagename = new ArrayList<>();
	        List<String> apkClassname = new ArrayList<>();
	        List<String> apk0Classdependname = new ArrayList<>();
	        List<String> apk0packagename = new ArrayList<>();
	        Map<String, Integer>set= new HashMap<>();
	        
	        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
	        	apkpackagename.add(apkPackageProfile.packageName);
	        	if(apkPackageProfile.packageName.contains(rootpkgname)&&!apkPackageProfile.packageName.contentEquals(rootpkgname))
	        	{
	        		apk0packagename.add(apkPackageProfile.packageName);
	        	}
	        	if(apkPackageProfile.packageName.contentEquals(rootpkgname))
	        	{
	        		System.out.println("���Ǹ�apk��������:"+apkPackageProfile.packageName);
	        		System.out.println("���Ǹ�apk���Լ�����:");
	        		packagenametoclassname(apkPackageProfile.packageName);
	        		//System.out.println("���Ǹ�apk����������:");
	        		//packagenametodependclassname(pkgname,apkPackageProfile.packageName);
	        	}
	        }
	        for(String tt:apk0packagename){
	        	int weight0=0;
	        	System.out.println("���Ǹ�apk�Ĵΰ���:"+tt);
	        	System.out.println("���Ǹ�apk���Լ�����:");	 
	        	System.out.println(packagenametoclassname(tt));	        	
	        	//System.out.println(pecker2.getDependedweight(tt));	        	
	        	System.out.println(rootpkgname);
	        	weight0=weightcalculate111(rootpkgname,tt,packagenametoclassname(tt),subpkg1);//��ʼ��������
	        	if(weight0>0){
	        		System.out.println("����������ôΰ��������Ȩ��ֵ:");
	        		System.out.println(weight0);
	        		set.put(tt, weight0);
	        	}	        	
	        	//System.out.println("���Ǹ�apk����������:");
                //packagenametodependclassname(tt);
	        	System.out.println();
	        }
	        
	       // for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
	        //	libpackagename.add(libPackageProfile.packageName);
	        //}
			return set;
	    }
	    
	    
	    public Map<String, List<String>> defanalyrootpackage(DepAnalysis pecker2,String rootpkgname, List<String> subpkg){
	        List<String> libpackagename = new ArrayList<>();
	        List<String> apkpackagename = new ArrayList<>();
	        List<String> apkClassname = new ArrayList<>();
	        List<String> apk0Classdependname = new ArrayList<>();
	        List<String> apk0packagename = new ArrayList<>();
	        Map<String, Integer>set= new HashMap<>();
	        
	        for (ApkPackageProfile apkPackageProfile : apkPackageProfileMap.values()) {
	        	apkpackagename.add(apkPackageProfile.packageName);
	        	if(apkPackageProfile.packageName.contains(rootpkgname)&&!apkPackageProfile.packageName.contentEquals(rootpkgname))
	        	{
	        		apk0packagename.add(apkPackageProfile.packageName);
	        	}
	        	if(apkPackageProfile.packageName.contentEquals(rootpkgname))
	        	{
	        		System.out.println("���Ǹ�apk��������:"+apkPackageProfile.packageName);
	        		System.out.println("���Ǹ�apk���Լ�����:");
	        		packagenametoclassname(apkPackageProfile.packageName);
	        		//System.out.println("���Ǹ�apk����������:");
	        		//packagenametodependclassname(pkgname,apkPackageProfile.packageName);
	        	}
	        }
	        for(String tt:apk0packagename){
	        	int weight0=0;
	        	System.out.println("���Ǹ�apk�Ĵΰ���:"+tt);
	        	//System.out.println("���Ǹ�apk���Լ�����:");	 
	        	//System.out.println(packagenametoclassname(tt));	        	
	        	//System.out.println(pecker2.getDependedweight(tt));	        	
	        	//System.out.println(rootpkgname);
	        	weight0=weightcalculate111(rootpkgname,tt,packagenametoclassname(tt),subpkg);
	        	if(weight0>0){
	        		System.out.println("����������ôΰ��������Ȩ��ֵ:");
	        		System.out.println(weight0);
	        		set.put(tt, weight0);
	        	}	        	
	        	//System.out.println("���Ǹ�apk����������:");
                //packagenametodependclassname(tt);
	        	System.out.println();
	        }
	        
	       // for (LibPackageProfile libPackageProfile : libPackageProfileMap.values()) {
	        //	libpackagename.add(libPackageProfile.packageName);
	        //}
			return groupRoot;
	    }

	    private static void ModelDC(String apkPath) throws IOException, XmlPullParserException {
	       // String apkPath = "G:\\libdetectiongroundtruth\\APKset\\bacth2\\104_Job_Search_v1.11.0_apkpure.com.apk";//C:\\Users\\ZJY\\Desktop\\test.apk
	        //String libPath = "C:\\Users\\ZJY\\Desktop\\test.dex";
	    	System.out.println(apkPath);
	        Map<String, Integer>set=new HashMap<>();
	        long current = System.currentTimeMillis();
	        String pkgname=ParseApkTest.test(apkPath);
	        //String pkgname="com.How.todraw3d.activities";
	        System.out.println(pkgname);
	        set.putAll(singleMain(apkPath,pkgname));
	       // System.out.println("�������������");
	        int cou=set.size();
	        for(String key : set.keySet()){
	        	//System.out.println(key);
	        	//System.out.println(set.get(key));
	        	if (set.get(key)<4) {
					cou--;
				}
	        }
	        System.out.println("��ģ������"+cou);
	        System.out.println("time: " + (System.currentTimeMillis() - current));	        
	        calpkgsim pecker = new calpkgsim();
	        String apkname=apkPath.substring(apkPath.lastIndexOf("\\")).substring(1);
	        String filenameTemp=pecker.creatdexFile(apkname);
			pecker.writeTxtFile("��"+cou+"------time:"+(System.currentTimeMillis() - current),filenameTemp);
			pecker.writeTxtFile("��1"+cou+"------time:"+(System.currentTimeMillis() - current),filenameTemp);
		}

		public static Map<String, Integer> singleMain(String apkPath,String pkgname) {
	        Apk apk = Apk.loadFromFile(apkPath);
	        if (apk == null) {
	            fail("apk not parsed");
	        }
	        /**Lib lib = Lib.loadFromFile(libPath);
	        if (lib == null) {
	            fail("lib not parsed");
	        }**/
	        Sdk sdk = Sdk.loadDefaultSdk();
	        if (sdk == null) {
	            fail("default sdk not parsed");
	        }
	        Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
	        //LibProfile libProfile = LibProfile.create(lib, targetSdkClassNameSet);
	        //DepAnalysis pecker1=new DepAnalysis(lib);

	        ApkProfile apkProfile = ApkProfile.create(apk, targetSdkClassNameSet);
	        DepAnalysis pecker2=new DepAnalysis(apk);
	        //System.out.println(pecker2.getClassProfile("com.xiyili.youjia.ui.base.BaseActivity").getBasicHash());
	        //System.out.println(pecker2.getAllDependingClassName("com.xiyili.youjia.ui.base.BaseActivity"));        
	        //System.out.println(pecker2.getAllDependingClassName("com.xiyili.youjia.model.BaseLogin"));
	        defanaly pecker = new defanaly(apkProfile, targetSdkClassNameSet,pecker2);
	        return pecker.defanalypackage(pecker2,pkgname);
	    }
		
		public static void main(String[] args) {
			Map<String, Set<String>> classINsubPKG=new HashMap<String, Set<String>>();
			Map<String, String> classTOrootPKG=new HashMap<String, String>();
			//String apkpathString="G:\\libdetectionʵ���׼�ɼ�\\APK��\\bacth2\\85_Cafe_v1.0.8_apkpure.com.apk";// 	G:\\libdetectiongroundtruth\\APKset\\bacth2\\85_Cafe_v1.0.8_apkpure.com.apk
			String apkpathString="E:\\libdetection����\\LibPass����\\ʵ��\\0c19dc80f0190c6c29345bf01bc840f0.apk";// 	G:\\libdetectiongroundtruth\\APKset\\bacth2\\85_Cafe_v1.0.8_apkpure.com.apk
			System.out.println(apkpathString); 
			Sdk sdk = Sdk.loadDefaultSdk();
		        if (sdk == null) {
		            fail("default sdk not parsed");
		        }
		        Apk apk = Apk.loadFromFile(apkpathString);
		        if (apk == null) {
		            fail("apk not parsed");
		        }
		        DepAnalysis teDepAnalysis=new DepAnalysis(apk);
		        Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
		        ApkProfile apkProfile= ApkProfile.create(apk, targetSdkClassNameSet); 
		        for (String rootPKGname: apkProfile.rootPackageMap.keySet()) {
					System.out.println("����:"+rootPKGname);
					for (String subPKGname: apkProfile.rootPackageMap.get(rootPKGname)) {
						System.out.println("�Ӱ���"+subPKGname); 						
						Set<String> classinsubPKGname=apkProfile.packageProfileMap.get(subPKGname).classProfileMap.keySet();//�Ӱ������е�����
						classINsubPKG.put(subPKGname,classinsubPKGname);
						for (String classNAME: classinsubPKGname) {
							if (classTOrootPKG.keySet().contains(classNAME)) {
								classTOrootPKG.put(classNAME, "Repeat matching!");
							}else {
								classTOrootPKG.put(classNAME, subPKGname);
							}
						}
					}
				}
		        //for (String rootPKGname:classINsubPKG.keySet()) {
					teDepAnalysis.printdepGraph(classINsubPKG,classTOrootPKG,apkProfile.rootPackageMap); 
				//}
		}
	    
	    private static void fail(String message) {
	        System.err.println(message);
	        System.exit(0);
	    }

}
