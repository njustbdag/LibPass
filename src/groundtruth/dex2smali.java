package groundtruth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.fudan.libpecker.main.aar2dex;
import cn.fudan.libpecker.main.getdexfilepath;
 
public class dex2smali {
 
	private static String batpath = "I:/libdetectiongroundtruth/";//G:/LibPecker源码/LibPecker-masteroriginal/LibPecker-master/bin
	private static String dexpath = "G:/libdetection实验标准采集/dex2smali/";
 
	public static void main(String[] args) throws IOException {
		/**List<String> apkfilepath=new ArrayList<>();
		 getdexfilepath aa=new getdexfilepath();
		 apkfilepath.addAll(aa.traverseFolder1("G:\\libdetection实验标准采集\\APK库\\bacth2"));
			for(String a1:apkfilepath){
			System.out.println(a1);
		}
			executeapk(apkfilepath);**/
		List<String> a=new ArrayList<>();
		aar2dex aa=new aar2dex();
		//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar所在目录
		a.addAll(aa.traverseFolder1("I:\\混淆成功的jar"));//找到该目录下的所有dex文件路径
		for(String a1:a){
			String batfile=a1.replaceAll("\\\\", "\\\\\\\\");
			System.out.println(batfile);
			//runbatfile(batfile);
			execute(a);
		}
		 
		//runbatfile("G:/libdetection实验标准采集/baksmali/1ddc4f3804cdf219ae7feaf4647a5e1d79bfc1863208fac98cba54bf4b282994.bat");
       // execute(a);
		//executeaar(a);
    }
	
	public static void execute(List<String> dexpath) throws IOException{
		for(String jar:dexpath){
			String smalifilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".dex")).substring(1);
			//dex2smali.creatdexFile(dexfilename);
			//System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".jar")).substring(1));			
	       String batfilepath = dex2smali.creatbatFile(smalifilename);
	       System.out.println(batfilepath);
			String batcontent="@echo off\r\njava -jar G:\\libdetectiongroundtruth\\baksmali\\baksmali-2.1.3.jar -o G:\\libdetectiongroundtruth\\baksmali\\smali\\"+smalifilename+" "+jar;
			//System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
	        runbatfile(batfilepath); 
	        System.out.println(jar+"转dex已成功");
		}
 
	}
	
	public static void executeapk(List<String> apkpath) throws IOException{
		for(String jar:apkpath){
			String smalifilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".apk")).substring(1);
			//dex2smali.creatdexFile(dexfilename);
			//System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".jar")).substring(1));			
	       String batfilepath = dex2smali.creatbatFile(smalifilename);
	       System.out.println(batfilepath);
			String batcontent="cd /d %~dp0\r\njava -jar G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\libdetect3.jar "+jar+" G:\\\\libdetection实验标准采集\\\\APK插入第三方库\\\\lib340";
			System.out.println(batcontent);
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
	        //runbatfile(batfilepath); 
	        //System.out.println(jar+"转dex已成功");
		}
 
	}
	
	public static void executebat(List<String> batpath) throws IOException{
		for(String batpath1:batpath)
		runbatfile(batpath1);
		
	}
	public static void executeaar(List<String> jarpath) throws IOException{//处理classes.jar文件
		for(String jar:jarpath){
			String dexfilename=jar.substring(jar.lastIndexOf("aarset\\"), jar.indexOf("\\classes")).substring(7);
			dex2smali.creatdexFile(dexfilename);
			//System.out.println(jar.substring(jar.indexOf("aarset"), jar.indexOf("\\classes")).substring(7));			
	        String batfilepath = dex2smali.creatbatFile(dexfilename);
			String batcontent="@echo off\r\ndx --dex --output=G:\\LibPecker源码\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile\\"+dexfilename+".dex "+jar;
			//dex2smali.clearInfoForFile(batfilepath);
			dex2smali.writeTxtFile(batcontent,batfilepath);
	        runbatfile(batfilepath); 
	        System.out.println(jar+"转dex已成功");
		}
 
	}

	public static void runbatfile(String batfilepath){
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
	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static String creatdexFile(String name) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp = dexpath + name + ".dex";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
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


	/**
	 * 写文件
	 * 
	 * @param newStr
	 *            新内容
	 * @throws IOException
	 */
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
	
	/**
     * 清空文件内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 

    


