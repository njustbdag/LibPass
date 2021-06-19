package groundtruth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.fudan.common.util.HashHelper;
import cn.fudan.libpecker.core.ParserXML;
import cn.fudan.libpecker.main.LibSeacher;
import cn.fudan.libpecker.main.defanaly;
import cn.fudan.libpecker.model.ApkPackageProfile;
import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.ConversionPkgName;
import cn.fudan.libpecker.model.SimpleClassProfile;
import cn.njust.common.Apk;
import cn.njust.common.Sdk;

public class GetPrimaryModule {
	    public static Map<String, ApkPackageProfile> apkPackageProfileMapcom;//pkg name -> ApkPackageProfile
	    private static   Map<String, List<String>> depGraphtoARP;
	public static void main(String[] args) throws Exception{
		/**String apkPath = null;
	     String libPath =null;
       if (args == null || args.length == 2) {
       	 apkPath = args[0];
            libPath = args[1];
       }
       else {
           fail("Usage: java -cp LibPecker3.jar cn.fudan.libpecker.mainProfileBasedLibPecker <apk_path> <lib_path>");
       }**/
		String apkPath = "F:\\smali文件-豌豆荚\\34340.apk";
		for (String folder : getFolders("F:\\smali文件-豌豆荚")) {
			long current = System.currentTimeMillis();
			System.out.println("F:\\17800个apk文件"+folder.substring(folder.lastIndexOf("\\")));
			SimplerootanalysisMap1("F:\\\\smali文件-豌豆荚\\"+folder.substring(folder.lastIndexOf("\\")));
			System.out.println("元信息识别时间："+(System.currentTimeMillis()-current));
		}
		//SimplerootanalysisMap1(apkPath);
	}
	/**
	 * 根据xml获取主模块名
	 * @param apkpathString apk的路径
	 * @throws IOException
	 */
	 public static void  SimplerootanalysisMap1(String apkpathString) throws IOException {
	    	Sdk sdk = Sdk.loadDefaultSdk();
	        if (sdk == null) {
	            fail("default sdk not parsed");
	        }
	        //changeAPK2Smali(apkpathString);//未转smali的需要先转一下
	        List<String> Primarymodule=new ArrayList<>();
	        List<String> NonPrimarymodule=new ArrayList<>();
	        String apkpath=apkpathString;       
	        String apknameString=apkpath.substring(apkpath.lastIndexOf("\\")+1);
	        String apkAndroidManifestPathString=apkpathString+"\\AndroidManifest.xml";
	        //String apkAndroidManifestPathString=apkpathString.substring(0, apkpathString.lastIndexOf("."))+"\\AndroidManifest.xml";	   
	        System.out.println(apkAndroidManifestPathString);
			File file1 = new File(apkAndroidManifestPathString);
			ParserXML parserXML=new ParserXML();
			String LibList1=parserXML.readtxt(file1);
			Primarymodule.add(LibList1);
			System.out.println(apkpathString+"第一种方法检测到的主模块："+LibList1);	
			//getandDELFolders(apkpathString.substring(0, apkpathString.lastIndexOf(".")));//删除smali文件
		}
	 /**
	  * 根据依赖关系图获取主模块名
	  * @param apkpathString apk路径
	  * @throws Exception
	  */
	 
	 public static void  SimplerootanalysisMap2(String apkpathString) throws Exception{
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
     depGraphtoARP=defanaly.DEFAnalysis(apk, apkProfile);//第二种方法检测的主模块在这，teDepAnalysis.printdepGraph
     apkPackageProfileMapcom=apkProfile1.packageProfileMap;
     depGraphtoARP.clear();
     apkPackageProfileMapcom.clear();
	 }
	    private static void fail(String message) {
	        System.err.println(message);
	        System.exit(0);
	    }
	    
	    public static void  changeAPK2Smali(String apkpathString) throws IOException{
	    	 ArrayList<String> tmpArrayList=new ArrayList<String>();
		        tmpArrayList.add(apkpathString);
		        CreateBatFiles.executeapk2smali(tmpArrayList);//输入apk路径就可转成smali
		        runbat.runFile("G:\\libdetection实验标准采集\\bat");
	    }
	    
	    /**
		 * 迭代删除文件夹
		 * @param dirPath 文件夹路径
		 */
		public static void deleteDir(String dirPath)
		{
			File file = new File(dirPath);
			if(file.isFile())
			{
				file.delete();
			}else
			{
				File[] files = file.listFiles();
				if(files == null)
				{
					file.delete();
				}else
				{
					for (int i = 0; i < files.length; i++) 
					{
						deleteDir(files[i].getAbsolutePath());
					}
					file.delete();
				}
			}
		}
		private static Set<String> getandDELFolders(String path) {
	        Set<String> files = new HashSet<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()&&tempList[i].toString().contains(".apk")) {		        
	            }
	            if (tempList[i].isDirectory()) {
	            	files.add(tempList[i].toString());
	            	System.out.println("正在清除文件："+tempList[i].toString()+"...");
	            	deleteDir(tempList[i].toString());
	            }
	        } 
	        return files;  
	    }
		
		private static Set<String> getFolders(String path) {
	        Set<String> files = new HashSet<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()&&tempList[i].toString().contains(".apk")) {		        
	            }
	            if (tempList[i].isDirectory()) {
	            	files.add(tempList[i].toString());
	            	//System.out.println(tempList[i].toString());
	            }
	        } 
	        return files;  
	    }
	    
}
