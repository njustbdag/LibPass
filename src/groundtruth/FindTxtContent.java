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
		      System.out.println("文件不存在!");
		    }
		  } catch (Exception e) {
		    System.out.println("文件读取错误!");
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
		      System.out.println("文件不存在!");
		    }
		  } catch (Exception e) {
		    System.out.println("文件读取错误!");
		  }
		return dexpathList;
		 
		  }
	
	public static boolean writeTxtFile(String newStr,String filenameTemp) throws IOException {
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
				// System.getProperty("line.separator")
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
	
	public static boolean writeutfTxtFile(String newStr,String filenameTemp) throws IOException {
	    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filenameTemp), "UTF-8"); 
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
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
  
		    osw.append(System.getProperty("line.separator"));
			osw.append("ffff适当的的速度酒店简介");			
            osw.flush(); //将内容刷新到文件中存储
            osw.close();
			//buf.append(filein);
 
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
	
	public static void main(String[] args) throws IOException{
		FindTxtContent iContent=new FindTxtContent();
		List<String> a=new ArrayList<>();
		List<String> txtpath=new ArrayList<>();
		//ApkLibInfosService apkLibService=new ApkLibInfosService();
		aar2dex aa=new aar2dex();
		a.addAll(aa.traverseFolder1("E:\\LibDetect实验groundtruth\\test1"));//找到该目录下的所有txt文件路径
		for(String a1:a){
			System.out.println("现在开始存"+a1.replaceAll("\\\\","\\\\\\\\"));
			iContent.readTxt(a1.replaceAll("\\\\","\\\\\\\\"));
			System.out.println(a1.replaceAll("\\\\","\\\\\\\\")+"已存完");
		//txtpath.add(a1);
	}
		//System.out.println(txtpath.size());
		  //String filePath = "G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\运行结果\\30_Day_Fitness_Challenge_Workout_at_Home_v1.0.41_apkpure.com.apk.txt";
		    //readTxt(filePath);
		    //writeutfTxtFile("麥卡ssdddd速度速度等等55電視","G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\1ure.com.apk.txt");
		    System.out.println("写入完成");
}
	
	
}
