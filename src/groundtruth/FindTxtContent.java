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
import java.util.List;

import njust.lib.Service.ApkInfoService;
import njust.lib.Service.ApkLibInfosService;
import njust.lib.Service.LibdetectionResultService;
import njust.lib.Service.LibpeckerResultService;
import cn.fudan.libpecker.main.aar2dex;

public class FindTxtContent {
	public void readTxt(String filePath) {
		ApkLibInfosService apkLibService=new ApkLibInfosService();
		LibdetectionResultService libdetectionResultService=new LibdetectionResultService();
		LibpeckerResultService libpeckerResultService=new LibpeckerResultService();
		  try {
		    File file = new File(filePath);
		    if(file.isFile() && file.exists()) {
		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		      BufferedReader br = new BufferedReader(isr);
		      String lineTxt = null;
		      while ((lineTxt = br.readLine()) != null) {
		        System.out.println(lineTxt.substring(1));
		        String apkname="**"+lineTxt.substring(2, lineTxt.indexOf("-"));
		        String libname=lineTxt.substring(lineTxt.lastIndexOf("------")+6,lineTxt.indexOf("*"));
		        //String atrr=lineTxt.substring(lineTxt.indexOf("*")+1);
		        //int atr=Integer.parseInt(atrr);
		        int atr=12;
		        if(atr==12){//atr!=5&atr!=6
			        System.out.println(apkname);
			        System.out.println(libname);
			        System.out.println(atr);
			       //libdetectionResultService.addapklibbyname(apkname, libname,atr);
			          libpeckerResultService.addapklibbyname(apkname, libname,atr);
			        //apkLibService.addapklibbyattr(apkname, libname,atr);
		        }
		    /**  if(atr!=5){//atr!=5&atr!=1
			        System.out.println(apkname);
			        System.out.println(libname);
			        System.out.println(atr);
			        //apkLibService.addapklibbyattr(apkname, libname,atr);
		        }  **/   
		      }
		      br.close();
		    } else {
		      System.out.println("�ļ�������!");
		    }
		  } catch (Exception e) {
		    System.out.println("�ļ���ȡ����!");
		  }
		 
		  }
	
	public static List<String> readallTxt(String filePath,String typestring) {
		typestring=typestring.replace(".", "");
		typestring=typestring.replace("dex", "");
		System.out.println(typestring);
		List<String> dexpathList=new ArrayList<>();
		  try {
		    File file = new File(filePath);
		    if(file.isFile() && file.exists()) {
		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		      BufferedReader br = new BufferedReader(isr);
		      String lineTxt = null;
		      while ((lineTxt = br.readLine()) != null) {
		    	  if (lineTxt.contains(typestring)) {
		    		  dexpathList.add(lineTxt);
				}
		      }
		      br.close();
		    } else {
		      System.out.println("�ļ�������!");
		    }
		  } catch (Exception e) {
		    System.out.println("�ļ���ȡ����!");
		  }
		return dexpathList;
		 
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
	
	public static boolean writeutfTxtFile(String newStr,String filenameTemp) throws IOException {
	    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filenameTemp), "UTF-8"); 
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
  
		    osw.append(System.getProperty("line.separator"));
			osw.append("ffff�ʵ��ĵ��ٶȾƵ���");			
            osw.flush(); //������ˢ�µ��ļ��д洢
            osw.close();
			//buf.append(filein);
 
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
		FindTxtContent iContent=new FindTxtContent();
		List<String> a=new ArrayList<>();
		List<String> txtpath=new ArrayList<>();
		//ApkLibInfosService apkLibService=new ApkLibInfosService();
		aar2dex aa=new aar2dex();
		a.addAll(aa.traverseFolder1("E:\\LibDetectʵ��groundtruth\\test1"));//�ҵ���Ŀ¼�µ�����txt�ļ�·��
		for(String a1:a){
			System.out.println("���ڿ�ʼ��"+a1.replaceAll("\\\\","\\\\\\\\"));
			iContent.readTxt(a1.replaceAll("\\\\","\\\\\\\\"));
			System.out.println(a1.replaceAll("\\\\","\\\\\\\\")+"�Ѵ���");
		//txtpath.add(a1);
	}
		//System.out.println(txtpath.size());
		  //String filePath = "G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\���н��\\30_Day_Fitness_Challenge_Workout_at_Home_v1.0.41_apkpure.com.apk.txt";
		    //readTxt(filePath);
		    //writeutfTxtFile("����ssdddd�ٶ��ٶȵȵ�55�ҕ","G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\1ure.com.apk.txt");
		    System.out.println("д�����");
}
	
	
}
