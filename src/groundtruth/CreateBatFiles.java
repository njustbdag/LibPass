package groundtruth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateBatFiles {
	
	public static void main(String[] args) throws IOException{
		creatbatfile("H:\\17800��apk�ļ�");
		//creatMD5batfile("H:\\����Դ�����apk");
		//creatapk2smalibatfile("H:\\sample");
	}
	
	private static void creatMD5batfile(String apksPath) throws IOException {
		Set<String> apkPathList=TheFirstPartOfLibSearcher.getFiles(apksPath);
		List<String>apknameList=new ArrayList<>();
		for (String string : apkPathList) {
			System.out.println(string);
			apknameList.add(string);
		} 
		//executeMD5(apknameList);
	}

	private static void executeMD5(List<String> apknameList) throws IOException {
		for(String jar:apknameList){
			System.out.println(jar);
			String dexfilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1);
			//jar2dex.creatbatFile(dexfilename);
			System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1));			
	        String batfilepath = jar2dex.creatbatFile(dexfilename);
			String batcontent="cd /d %~dp0\r\nkeytool -printcert -file H:\\GooglePlay\\"+dexfilename+"\\META-INF\\CERT.RSA";
			System.out.println(batfilepath);
			System.out.println(batcontent);
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        System.out.println(jar+"תdex�ѳɹ�");
		}
 
	}
	

	private static void creatapk2smalibatfile(String apksPath) throws IOException {
		Set<String> apkPathList=TheFirstPartOfLibSearcher.getFiles(apksPath);
		List<String>apknameList=new ArrayList<>();
		for (String string : apkPathList) {
			System.out.println(string);
			apknameList.add(string);
		}
		executeapk2smali(apknameList);
	}

	public static void executeapk2smali(List<String> apknameList) throws IOException {
		String apkfolderString="I:\\LibPass�ڶ���ʵ��\\1000��apk����\\";
		String smalifolderString="I:\\LibPass�ڶ���ʵ��\\1000��apk��smali����\\";
		for(String jar:apknameList){
			System.out.println("sdd"+jar);
			String dexfilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1);
			//jar2dex.creatbatFile(dexfilename);
			System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1));			
	        String batfilepath = jar2dex.creatbatFile(dexfilename);
			String batcontent="@echo off\r\nE:\\LibDetectʵ��groundtruth\\apktool d -f "+jar+" -o "+jar.substring(0, jar.lastIndexOf("."));
			System.out.println(batfilepath);
			System.out.println(batcontent);
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        System.out.println(jar+"תdex�ѳɹ�");
		}
 
	}
	

	public static void creatbatfile(String apksPath) throws IOException {
		Set<String> apkPathList=TheFirstPartOfLibSearcher.getFiles(apksPath);
		List<String>apknameList=new ArrayList<>();
		for (String string : apkPathList) {
			System.out.println(string);
			apknameList.add(string);
		}
		execute(apknameList);
	}
	public static void execute(List<String> jarpath) throws IOException{
		for(String jar:jarpath){
			System.out.println(jar);
			String dexfilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1);
			//jar2dex.creatbatFile(dexfilename);
			System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1));			
	        String batfilepath = jar2dex.creatbatFile(dexfilename);
			String batcontent="cd /d %~dp0\r\njava -jar G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\libdetect3.jar "+jar+" G:\\libdetectionʵ���׼�ɼ�\\APK�����������\\lib340";
			System.out.println(batfilepath);
			System.out.println(batcontent);
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        System.out.println(jar+"תdex�ѳɹ�");
		}
 
	}
}
