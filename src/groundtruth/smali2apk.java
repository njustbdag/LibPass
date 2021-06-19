package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.fudan.libpecker.main.aar2dex;

public class smali2apk {
	private static String batpath = "G:/libpecker备份/袁倩婷/LibPecker_11.28/";

	public static void main(String[] args) throws IOException {
		//runbatfile("E:\\LibDetect实验groundtruth\\04c9e05d0f626cc3f47dc0bc9b65a8cf.malware.bat");
		aar2dex aa=new aar2dex();
		//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar所在目录
		List<String> a=getFiles(batpath);
		//List<String> a=findfile("E:\\LibDetect实验groundtruth\\");
		//DeleteLibPackage delete=new DeleteLibPackage();
		for(String batfile:a){
			System.out.println(batfile);
			runbatfile(batfile);
			runbat.deleteFile(batfile);
			}
		System.out.println(a.size());
		//execute1(a);
		}
	
    static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&tempList[i].toString().contains("bat")) {		        
                //文件名，不包含路径tempList[i].toString()
            	files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        } 
        return files;
    }
	
	public static List<String> findfolder(String path){//找到path的一级文件夹目录
		List<String> folderList=new ArrayList<>();
		System.out.println("=========指定目录下的所有文件夹==========");
		File fileDirectory = new File(path);
		for (File temp : fileDirectory.listFiles()) {
			if (temp.isDirectory()) {
				System.out.println(temp.toString());
				folderList.add(temp.toString().replaceAll("\\\\", "\\\\\\\\"));
			}
		}
		return folderList;
		
	}
	
	public  List<String> findfile(String path){//找到path的一级文件夹目录
		List<String> fileList=new ArrayList<>();
		System.out.println("=========指定目录下的所有文件==========");
		File file = new File(path);
		File[] aa = file.listFiles();
		for (int i = 0; i < aa.length; i++) {
			if (aa[i].isFile()) {
				if(aa[i].toString().contains("bat")){
					//if(!aa[i].toString().contains("apktool.bat")){
	            System.out.println(aa[i].toString());
				fileList.add(aa[i].toString().replaceAll("\\\\", "\\\\\\\\"));
					//}
	
				}

			}
		}
		return fileList;
		
	}
	
	public static void execute(List<String> smalipath1) throws IOException{
		for(String smalipath:smalipath1){
			String smalifilename=smalipath.substring(38);	
			String smalifilename2=smalifilename.substring(0, smalifilename.indexOf("."));
	       String batfilepath = creatbatFile(smalifilename);
	       System.out.println(smalifilename2);
			String batcontent="@echo off\r\nE:\\LibDetect实验groundtruth\\apktool b E:\\LibDetect实验groundtruth\\反编译后的apk\\"+smalifilename+" -o E:\\LibDetect实验groundtruth\\smali2apk\\"+smalifilename2+".apk";
			//System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
			//runbatfile0(batfilepath); 
	        //System.out.println(jar+"转dex已成功");
		}
 
	}
	
	
	public static void execute1(List<String> smalipath1) throws IOException{
		for(String smalipath:smalipath1){
			String smalifilename=smalipath.substring(36);	
			//String smalifilename2=smalifilename.substring(0, smalifilename.indexOf("."));
	       String batfilepath = creatbatFile(smalifilename);
	       System.out.println(smalifilename);
			String batcontent="@echo off\r\njava -jar G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\libdetect3.jar E:\\LibDetect实验groundtruth\\smali2apk\\"+smalifilename+" G:\\libdetectiongroundtruth\\lib340";
			//System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
			//runbatfile0(batfilepath); 
	        //System.out.println(jar+"转dex已成功");
		}
 
	}
	
	public static void executebat(List<String> batpath) throws IOException{
		for(String batpath1:batpath)
		runbatfile(batpath1);
		
	}
	
	public static void runbatfile(String batfilepath) throws IOException{
		long current = System.currentTimeMillis();
        Runtime rt = Runtime.getRuntime();
        Process ps = null;
       try {
      ps = rt.exec("cmd.exe /C start /b " + batfilepath);
      InputStream in = ps.getInputStream();
      InputStreamReader isr=new InputStreamReader(in);
      BufferedReader br=new BufferedReader(isr);
      String line=null;
      //(line=br.readLine())!=null&!line.contains("Building apk")&!line.contains("Smaling smali")&System.currentTimeMillis() - current<30000) {
      while((line=br.readLine())!=null&&!line.contains("time")) {
         System.out.println(line);
      }
      in.close();
      ps.waitFor();
       } catch (IOException e1) {
      e1.printStackTrace();
        } catch (InterruptedException e) {
       e.printStackTrace();
       }

	}
	
	public static void runbatfile0(String batfilepath){
        Runtime rt = Runtime.getRuntime();
        Process ps = null;
       try {
      ps = rt.exec("cmd.exe /C start /b " + batfilepath);
      ps.waitFor();
       } catch (IOException e1) {
      e1.printStackTrace();
        } catch (InterruptedException e) {
       e.printStackTrace();
       }
       int i = ps.exitValue();
     if (i == 0) {
      System.out.println("执行完成.") ;
        } else {
       System.out.println("执行失败.") ;
        }

	}
	
	public static String creatbatFile(String name) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp = batpath + name + ".bat";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
	}
}
