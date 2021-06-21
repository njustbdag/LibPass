package cn.fudan.libpecker.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import cn.fudan.common.util.HashHelper;
import cn.fudan.libpecker.main.LibSeacher;
import cn.fudan.libpecker.model.ApkProfile;
import cn.njust.common.Apk;
import cn.njust.common.Sdk;

public class ConfusedAPKAnalysis {
	public static void main (String[] args) {
		File file=new File("C:\\Users\\ZJY\\Desktop\\test.txt");
		List<String> apkNameList=readtxt(file);
			List<String> hashList1=apkAnalysis("C:\\Users\\ZJY\\Desktop\\E\\000EE4E31CCCADDB3B9E301BD803CEED512B06F31D149C2E248545B49F4EE967.apk");
			List<String> hashList2=apkAnalysis("C:\\Users\\ZJY\\Desktop\\E\\repackage\\D73C16E1BE06B4360EA13B1E25E2933BBC99AD5CB824B31B424A6BA09B3436AC.apk");
			System.out.println("����apk�İ��ṹ���ƶȣ�"+compare2list(hashList1,hashList2));
	}
 
	private static Double compare2list(List<String> hashList1,
			List<String> hashList2) {
		int sameNUM=0;
		if (hashList1.get(hashList1.size()-1).equals(hashList2.get(hashList1.size()-1))) {
			return (double) 1;
		}
		for (String string : hashList2) {
			if (hashList1.contains(string)) {
				sameNUM++; 
			}
		}
		return (double) (sameNUM/hashList2.size());
	}

	private static List<String> apkAnalysis(String apkpathString){
		List<String> hashList=new ArrayList<>();
		StringBuilder wholeapkBuilder=new StringBuilder();
		List<String> notallASPcon=new ArrayList<>();
	    Map<String, List<String>> rootPackageMap;//root package -> [package names, ...]
		//String apkpathString="C:\\Users\\ZJY\\Desktop\\E\\000EE4E31CCCADDB3B9E301BD803CEED512B06F31D149C2E248545B49F4EE967.apk"; 	
		//System.out.println(apkpathString); 
		Sdk sdk = Sdk.loadDefaultSdk(); 
	        if (sdk == null) {
	            fail("default sdk not parsed");
	        }
	        Apk apk = Apk.loadFromFile(apkpathString);
	        if (apk == null) {
	            fail("apk not parsed");
	        }
		Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
         rootPackageMap = RootPackageAnalysis.previousextractRootPackages(apk);
         Iterator<Entry<String, List<String>>> rootpIterator=rootPackageMap.entrySet().iterator();
         while (rootpIterator.hasNext()) {
			Map.Entry<java.lang.String, java.util.List<java.lang.String>> entry = (Map.Entry<java.lang.String, java.util.List<java.lang.String>>) rootpIterator
					.next();
			System.out.println(entry.getKey()+"����������ʼ");
			StringBuilder PackagestructureHashList0 = new StringBuilder();
			//System.out.println(entry.getValue());
			Set<String>tempList=new HashSet<>();
			tempList.addAll(entry.getValue());
			PackageSortAnalysis packageSortAnalysis=new PackageSortAnalysis(tempList);
			TreeSet<String> conversedList=packageSortAnalysis.sortBegin();
			StringBuilder PackagestructureHashList = new StringBuilder();
			String PackagestructureList = null;
			for (String astring : conversedList) { 
				PackagestructureList=PackagestructureList+astring+",";
				PackagestructureHashList0.append(astring);	
				PackagestructureHashList.append(astring);
		}				
			PackagestructureList=PackagestructureList.substring(4);
			String PackagestructureHash=HashHelper.hash(PackagestructureHashList0.toString());
			//System.out.println("�ø����ṹ��"+PackagestructureList);
			System.out.println("�ø�����ϣֵ��"+PackagestructureHash);
			hashList.add(PackagestructureHash);
			wholeapkBuilder.append(PackagestructureHash);
			System.out.println();
		}
         //System.out.println(wholeapkBuilder.toString());
         System.out.println("����apk�İ��ṹ��ϣֵ��"+HashHelper.hash(wholeapkBuilder.toString()));
         hashList.add(HashHelper.hash(wholeapkBuilder.toString()));
         return hashList;
	}

	private static void ConfusedAnalysis(String apkName) {
		List<String> notallASPcon=new ArrayList<>();
	    Map<String, List<String>> rootPackageMap;//root package -> [package names, ...]
		String apkpathString="G:\\libdetectiongroundtruth\\APKset\\bacth2\\"+apkName; 	
		//System.out.println(apkpathString); 
		Sdk sdk = Sdk.loadDefaultSdk(); 
	        if (sdk == null) {
	            fail("default sdk not parsed");
	        }
	        Apk apk = Apk.loadFromFile(apkpathString);
	        if (apk == null) {
	            fail("apk not parsed");
	        }
		Set<String> targetSdkClassNameSet = sdk.getTargetSdkClassNameSet();
         rootPackageMap = RootPackageAnalysis.previousextractRootPackages(apk);
         Iterator<Entry<String, List<String>>>pkgIterator=rootPackageMap.entrySet().iterator();
         while (pkgIterator.hasNext()) {
        	 int numOFconASP=0;
        	 int numOFunconASP=0;
			Map.Entry<java.lang.String, java.util.List<java.lang.String>> entry = (Map.Entry<java.lang.String, java.util.List<java.lang.String>>) pkgIterator
					.next();
			if (ApkProfile.JudgeObfuscated(entry.getKey())) {//���ARP�Ǳ������ģ��ͽ��з���
				//System.out.println(entry.getKey()+"������...");
				boolean allASPconFLAG=true; 
				for (String string : entry.getValue()) {
					if (!ApkProfile.JudgeObfuscated(string)) {//���ASPû�б������ģ��˳�
						//System.out.println("apk:"+apkName+"�е�ARP:"+entry.getKey()+"�е�ASP��������"+string);
						numOFconASP++;
						allASPconFLAG=false;
					}else {
						//System.out.println("apk:"+apkName+"�е�ARP:"+entry.getKey()+"�е�ASPδ��������"+string);
						numOFunconASP++;
					}
				}
				if (allASPconFLAG==false) {
					notallASPcon.add(entry.getKey());
				}
			}
			if (numOFconASP!=0) {
				System.out.println("apk:"+apkName+"�е�ARP:"+entry.getKey()+"���������");
				System.out.println("δ��������ASP��Ŀ��"+numOFunconASP);
				System.out.println("��������ASP��Ŀ��"+numOFconASP);
			}
		}
		
	}

	private static List<String> readtxt(File file){
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            	//System.out.println(s);
            	resultList.add(s);	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    } static void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
}
