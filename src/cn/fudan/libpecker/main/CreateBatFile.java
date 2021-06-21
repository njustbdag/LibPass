package cn.fudan.libpecker.main;

import groundtruth.GetURLContent;
import groundtruth.smali2apk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.fudan.libpecker.model.ApkProfile;

public class CreateBatFile {
	 public static String createbatFile(String path,String filename) throws IOException{
	        File file=new File(path+"/"+filename);
	        if(!file.exists())
	            file.createNewFile();
			return path+"//"+filename;
	    }
	 
		public static List<String> readtxt3(String filepath){
			File file = new File(filepath);
	        //StringBuilder result = new StringBuilder();
	        List<String> resultList=new ArrayList<>();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
	            String s = null;
	            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
	            	resultList.add(s);	           
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return resultList;
	    }
	    public static boolean writeTxtFile(String newStr,String filenameTemp) throws IOException {
			// �ȶ�ȡԭ���ļ����ݣ�Ȼ�����д�����
			boolean flag = false;
			String filein = newStr + "\r\n";
			String temp = "";
	 
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
	 
			FileOutputStream fos = null;
			PrintWriter pw = null;
			try {
				// �ļ�·��
				File file = new File(filenameTemp);
				// ���ļ�����������
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				StringBuffer buf = new StringBuffer();

	            
	            
				// ������ļ�ԭ�е�����
				for (int j = 1; (temp = br.readLine()) != null; j++) {
					buf = buf.append(temp);
					// System.getProperty("line.separator")
					// ������֮��ķָ��� �൱�ڡ�\n��
					buf = buf.append(System.getProperty("line.separator"));
				}
				
				buf.append(filein);
	 
				fos = new FileOutputStream(file);
				pw = new PrintWriter(fos);
				pw.write(buf.toString().toCharArray());
				pw.flush();
				flag = true;
			} catch (IOException e1) {
				// TODO �Զ����� catch ��
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
	    
	    
	
	
	
	public static void main(String[] args) throws IOException{
		smali2apk batSmali2apk=new smali2apk();
		List<String> a=batSmali2apk.findfile("E:\\groundtruth\\createdex");
		int count=0;
		while (count<a.size()) {
			System.out.println("����ִ��"+a.get(count));
			runbatfile(a.get(count));
			count++;
		}
		/**long current = System.currentTimeMillis();
		String apkPath="E:\\groundtruth\\createdex";//apk����Ŀ¼
		String batPath="E:\\groundtruth\\createdex";//bat����Ŀ¼
		aCreateBatFile(apkPath,batPath);
		System.out.println("time: " + (System.currentTimeMillis() - current));   **/ 	
    }


	private static void StartCreateBatFile(String apkPath,String batPath) throws IOException {
		ApkProfile apkProfile=new ApkProfile();    	
        List<String> apkfilepath=new ArrayList<>();
        GetURLContent GetURLContent=new GetURLContent();
        getdexfilepath aa=new getdexfilepath();
    	apkfilepath.addAll(aa.traverseFolder1(apkPath));
    	for (String apkstring : apkfilepath) {
    		String apkname=apkstring.substring(apkstring.lastIndexOf("\\")+1);
    			System.out.println(apkname);
    			System.out.println(apkstring);
    			String batpath=batPath+"//"+apkname+".bat";
    			createbatFile(batPath,apkname+".bat");
    			writeTxtFile("cd /d %~dp0",batpath);
    			String content="java -jar G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\libdetect3.jar H:\\\\test\\\\"+apkname+" G:\\\\libdetectionʵ���׼�ɼ�\\\\APK�����������\\\\lib340";
    			writeTxtFile(content,batpath);
		}
		
	}
	 public static List<String> findfolder(String path){//�ҵ�path��һ���ļ���Ŀ¼
			List<String> folderList=new ArrayList<>();
			System.out.println("=========ָ��Ŀ¼�µ������ļ���==========");
			File fileDirectory = new File(path);
			for (File temp : fileDirectory.listFiles()) {
				if (temp.isDirectory()) {
					System.out.println(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
					folderList.add(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
				}
			}
			return folderList;
			
		}
	 
	 public static boolean runbatfile(String batfilepath) throws IOException{
	        Runtime rt = Runtime.getRuntime();
	        Process ps = null;
	       try {
	      ps = rt.exec("cmd.exe /C start /b " + batfilepath);
	      InputStream in = ps.getInputStream();
	      InputStreamReader isr=new InputStreamReader(in);
	      BufferedReader br=new BufferedReader(isr);
	      String line=null;
	     // while((line=br.readLine())!=null&!line.contains("time:")) {
	         //System.out.println(line);
	     // }
	      in.close();
	      ps.waitFor();
	       } catch (IOException e1) {
	      e1.printStackTrace();
	        } catch (InterruptedException e) {
	       e.printStackTrace();
	       }
	       catch (Exception e){
	    	   return false;
	       }
	       //String apkName = batfilepath.substring(batfilepath.lastIndexOf("/") + 1, batfilepath.lastIndexOf("."));
	      // String txtPath = "G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\���н��\\" + apkName + ".apk.txt";    
	      // System.out.println(txtPath);
		return true;

		}
	private static void aCreateBatFile(String apkPath,String batPath) throws IOException {   	
        List<String> apkfilepath=findfolder(apkPath);
    	for (String apkstring : apkfilepath) {
    		String apkname=apkstring.substring(apkstring.lastIndexOf("\\")+1);
    			System.out.println(apkname);
    			//System.out.println(apkstring);
    			String batpath=batPath+"//"+apkname+".bat";
    			createbatFile(batPath,apkname+".bat");
    			writeTxtFile("cd /d %~dp0",batpath);
    			String content="java -jar smali-2.1.3.jar -o "+apkname+".dex "+apkname;
    			writeTxtFile(content,batpath);
		}
		
	}
}
