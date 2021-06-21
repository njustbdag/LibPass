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
	private static String batpath = "G:/libpecker����/Ԭٻ��/LibPecker_11.28/";

	public static void main(String[] args) throws IOException {
		//runbatfile("E:\\LibDetectʵ��groundtruth\\04c9e05d0f626cc3f47dc0bc9b65a8cf.malware.bat");
		aar2dex aa=new aar2dex();
		//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar����Ŀ¼
		List<String> a=getFiles(batpath);
		//List<String> a=findfile("E:\\LibDetectʵ��groundtruth\\");
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
                //�ļ�����������·��tempList[i].toString()
            	files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
            }
        } 
        return files;
    }
	
	public static List<String> findfolder(String path){//�ҵ�path��һ���ļ���Ŀ¼
		List<String> folderList=new ArrayList<>();
		System.out.println("=========ָ��Ŀ¼�µ������ļ���==========");
		File fileDirectory = new File(path);
		for (File temp : fileDirectory.listFiles()) {
			if (temp.isDirectory()) {
				System.out.println(temp.toString());
				folderList.add(temp.toString().replaceAll("\\\\", "\\\\\\\\"));
			}
		}
		return folderList;
		
	}
	
	public  List<String> findfile(String path){//�ҵ�path��һ���ļ���Ŀ¼
		List<String> fileList=new ArrayList<>();
		System.out.println("=========ָ��Ŀ¼�µ������ļ�==========");
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
			String batcontent="@echo off\r\nE:\\LibDetectʵ��groundtruth\\apktool b E:\\LibDetectʵ��groundtruth\\��������apk\\"+smalifilename+" -o E:\\LibDetectʵ��groundtruth\\smali2apk\\"+smalifilename2+".apk";
			//System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
			//runbatfile0(batfilepath); 
	        //System.out.println(jar+"תdex�ѳɹ�");
		}
 
	}
	
	
	public static void execute1(List<String> smalipath1) throws IOException{
		for(String smalipath:smalipath1){
			String smalifilename=smalipath.substring(36);	
			//String smalifilename2=smalifilename.substring(0, smalifilename.indexOf("."));
	       String batfilepath = creatbatFile(smalifilename);
	       System.out.println(smalifilename);
			String batcontent="@echo off\r\njava -jar G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\libdetect3.jar E:\\LibDetectʵ��groundtruth\\smali2apk\\"+smalifilename+" G:\\libdetectiongroundtruth\\lib340";
			//System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
			//runbatfile0(batfilepath); 
	        //System.out.println(jar+"תdex�ѳɹ�");
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
      System.out.println("ִ�����.") ;
        } else {
       System.out.println("ִ��ʧ��.") ;
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
