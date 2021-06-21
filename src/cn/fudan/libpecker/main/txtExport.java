package cn.fudan.libpecker.main;

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
 
public class txtExport {
 
	private static String batpath = "G:/LibPeckerԴ��/LibPecker-masteroriginal/LibPecker-master/bin/batfile/";//G:/LibPeckerԴ��/LibPecker-masteroriginal/LibPecker-master/bin
	private static String dexpath = "G:/LibPeckerԴ��/LibPecker-masteroriginal/LibPecker-master/bin/dexfile/";
 
	public static void main(String[] args) throws IOException {
		List<String> a=new ArrayList<>();
		aar2dex aa=new aar2dex();
		//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar����Ŀ¼
		a.addAll(aa.traverseFolder1("G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\aarset"));		
        //execute(a);
		executeaar(a);
    }
	
	public static void execute(List<String> jarpath) throws IOException{
		for(String jar:jarpath){
			String dexfilename=jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".jar")).substring(1);
			txtExport.creatdexFile(dexfilename);
			//System.out.println(jar.substring(jar.lastIndexOf("\\"), jar.indexOf(".jar")).substring(1));			
	        String batfilepath = txtExport.creatbatFile(dexfilename);
			String batcontent="@echo off\r\ndx --dex --output=G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile\\"+dexfilename+".dex "+jar;
			//txtExport.clearInfoForFile(batfilepath);
			txtExport.writeTxtFile(batcontent,batfilepath);
	        runbatfile(batfilepath); 
	        System.out.println(jar+"תdex�ѳɹ�");
		}
 
	}
	
	public static void executeaar(List<String> jarpath) throws IOException{//����classes.jar�ļ�
		for(String jar:jarpath){
			String dexfilename=jar.substring(jar.lastIndexOf("aarset\\"), jar.indexOf("\\classes")).substring(7);
			txtExport.creatdexFile(dexfilename);
			//System.out.println(jar.substring(jar.indexOf("aarset"), jar.indexOf("\\classes")).substring(7));			
	        String batfilepath = txtExport.creatbatFile(dexfilename);
			String batcontent="@echo off\r\ndx --dex --output=G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile\\"+dexfilename+".dex "+jar;
			//txtExport.clearInfoForFile(batfilepath);
			txtExport.writeTxtFile(batcontent,batfilepath);
	        runbatfile(batfilepath); 
	        System.out.println(jar+"תdex�ѳɹ�");
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
      System.out.println("ִ�����.") ;
        } else {
       System.out.println("ִ��ʧ��.") ;
        }

	}
	/**
	 * �����ļ�
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
	 * д�ļ�
	 * 
	 * @param newStr
	 *            ������
	 * @throws IOException
	 */
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
	
	/**
     * ����ļ�����
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
 

    


