package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.fudan.libpecker.main.LibSeacher;

public class LibsearcherT {
	static Boolean flagBoolean;
	public static void main(String[] args) throws IOException {   
		Set<String> a=getFiles("G:/libpecker备份/袁倩婷/LibPecker_11.28/",".bat");
		Set<String> b=getFiles("E:/LibDetect实验groundtruth/运行结果lp/",".apk.txt");
		System.out.println(a);
		System.out.println(b);
		String doneString = null;
		for (String string : a) {
			if (b.contains(string)) {
				doneString=string;
				//System.out.println("test"+string);
				System.out.println(string);
				//System.out.println("qw");
			}
		}
		extraLogspecial(doneString+".apk");
		deleteFile(doneString+".bat");
	  } 
	
	public static void extraLogspecial(String apkName) throws IOException {
		flagBoolean=false;
		File file = new File("C:\\Users\\ZJY\\Desktop\\日志\\myeclipse日志.txt");   
	    File file2 = new File("E:\\LibDetect实验groundtruth\\运行结果lp\\"+apkName+".txt");   
	    BufferedReader reader = new BufferedReader(new FileReader(file));   
	    PrintWriter writer = new PrintWriter(file2);   
	    String line;   
	    while ((line = reader.readLine()) != null) {   
	      // 判断条件，根据自己的情况书写，会删除所有符合条件的行   
	      if (line.startsWith(apkName+"*begin")) {     
	    	  flagBoolean=true;
	      }   
	      if (flagBoolean) {
		      writer.println(line);   
		      writer.flush(); 
		}  
	      if (line.startsWith(apkName+"*end")) {
	    	  flagBoolean=false;
		}
	    }
	    flagBoolean=false;
	    reader.close();   
	    writer.close();   
	       
	    // 删除老文件   
	    //file.delete();   
	    //file2.renameTo(file);   
	}
	  
	public static void extraLog(String apkName) throws IOException {
		flagBoolean=false;
		File file = new File("C:\\Users\\ZJY\\Desktop\\日志\\myeclipse日志.txt");   
	    File file2 = new File("E:\\LibDetect实验groundtruth\\运行结果lp\\"+apkName+".txt");   
	    BufferedReader reader = new BufferedReader(new FileReader(file));   
	    PrintWriter writer = new PrintWriter(file2);   
	    String line;   
	    while ((line = reader.readLine()) != null) {   
	      // 判断条件，根据自己的情况书写，会删除所有符合条件的行   
	      if (line.startsWith(apkName+"*begin")) {     
	    	  flagBoolean=true;
	      }   
	      if (flagBoolean) {
		      writer.println(line);   
		      writer.flush(); 
		}  
	      if (line.startsWith(apkName+"*end")) {
	    	  flagBoolean=false;
		}
	    }   
	    reader.close();   
	    writer.close();   
	       
	    // 删除老文件   
	    //file.delete();   
	    //file2.renameTo(file);   
	}
	
    public static  boolean deleteFile(String fileName) {//删除单个文件
        File file = new File("G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\"+fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	public static void CopyLog(String apkName) throws IOException {
		File file = new File("C:\\Users\\ZJY\\Desktop\\日志\\myeclipse日志.txt");   
	    File file2 = new File("E:\\LibDetect实验groundtruth\\运行结果lp\\"+apkName+".txt");   
	    BufferedReader reader = new BufferedReader(new FileReader(file));   
	    PrintWriter writer = new PrintWriter(file2);   
	    String line;   
	    while ((line = reader.readLine()) != null) {   
	      // 判断条件，根据自己的情况书写，会删除所有符合条件的行   
		      writer.println(line);   
		      writer.flush(); 
	    }   
	    reader.close();   
	    writer.close();   
	       
	    // 删除老文件   
	    //file.delete();   
	    //file2.renameTo(file);   
	}
	
	  private static Set<String> getFiles(String path,String fileType) {
	        Set<String> files = new HashSet<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()&&tempList[i].toString().contains(fileType)) {		        
	                //文件名，不包含路径tempList[i].toString()
	            	String path1=tempList[i].toString();
	            	files.add(path1.substring(path1.lastIndexOf("\\")+1, path1.lastIndexOf(fileType)));
	            }
	            if (tempList[i].isDirectory()) {
	                //这里就不递归了，
	            }
	        } 
	        return files;
	    }
	  
	  
	  private static List<String> getFilestemp(String path) {
	        List<String> files = new ArrayList<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()) {		        
	                //文件名，不包含路径tempList[i].toString()
	            	files.add(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\"),tempList[i].toString().indexOf(".")));
	            	System.out.println(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\"),tempList[i].toString().indexOf(".")));
	            }
	            if (tempList[i].isDirectory()) {
	                //这里就不递归了，
	            }
	        } 
	        return files;
	    } 
}
