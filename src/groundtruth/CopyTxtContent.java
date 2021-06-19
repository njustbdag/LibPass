package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CopyTxtContent {
	private static int a = 5;
	 
	public static void main(String[] args) throws IOException {
		//Analysisofdecoupling();
		AnalysisofTP();
		//Analysis();
}
	private static void Analysisofdecoupling() throws IOException {
		List<String> apknameList=findfile("E:\\LibDetect实验groundtruth\\运行结果"); 
		int TP = 0;
		int time=0;
		int modlecout=0;
		for(String apkname:apknameList){
			String TxtPath=apkname;//.substring(38);
			System.out.println(TxtPath);
			File file1 = new File(apkname);
			List<String> LibList=txt2String(file1);
			for(String libname:LibList){
				String tString=libname.substring(libname.lastIndexOf(":")+1);
				String a=libname.substring(1, libname.indexOf("---"));
				if (!a.equals("0")) {
					TP++;
					System.out.println(a);
					int time1=Integer.parseInt(a);
					time+=time1;
					//System.out.println(a);
					writeTxtFile(libname,"E:/LibDetect实验groundtruth/TP阈值.txt");
				}
			}
		}	
		System.out.println(time);
		double o=time/TP;
		System.out.println(o);
		System.out.println(TP);
		
	}
	
	
	private static void Analysis() throws IOException {
		    int time=0;
			File file1 = new File("E:\\LibDetect实验groundtruth\\test日志.txt");
			List<String> LibList=txt2String(file1);
			for(String libname:LibList){
				if (libname.contains("筛选排除后共有")) {
					String aString=libname.substring(7, libname.indexOf("个"));
					System.out.println(aString);
					int time1=Integer.parseInt(aString);
					time+=time1;
				}
				}
			System.out.println(time);
			}	
		
	
	private static void AnalysisofTP() throws IOException {
		List<String> apknameList=findfile("E:\\LibDetect实验groundtruth\\运行结果"); 
		int TP = 0;
		int time=0;
		int modlecout=0;
    	int feld95 = 0;
    	int feld90= 0;
    	int feld85= 0;
    	int feld80= 0;
    	int feld75= 0;
    	int feld70= 0;
    	int feld65= 0;
    	int feld60= 0;
    	int feld55= 0;
    	int feld50= 0;
    	int feld45= 0;
    	int feld40= 0;
    	int feld35= 0;
    	int feld30= 0;
    	int feld25= 0;
    	int feld20= 0;
    	int feld15= 0;
    	int feld10= 0;
    	int feld05= 0;
    	int feld00= 0;
		for(String apkname:apknameList){
			String TxtPath=apkname;//.substring(38);
			System.out.println(TxtPath);
			//File file1 = new File("E:\\LibDetect实验groundtruth\\反编译后的apk\\"+TxtPath+".apk.txt");//lib.txt所在的目录
			File file1 = new File(apkname);
			List<String> LibList=txt2String(file1);
			for(String libname:LibList){
				if (libname.contains("feld95：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld95+=time1;
					//System.out.println(feld95);
				}
				if (libname.contains("feld90：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld90+=time1;
					//System.out.println(feld90);
				}
				if (libname.contains("feld85：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld85+=time1;
					//System.out.println(feld85);
				}
				if (libname.contains("feld80：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld80+=time1;
					//System.out.println(feld80);
				}
				if (libname.contains("feld75：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld75+=time1;
					//System.out.println(feld75);
				}
				if (libname.contains("feld70：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld70+=time1;
					//System.out.println(feld70);
				}
				if (libname.contains("feld65：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld65+=time1;
					//System.out.println(feld65);
				}
				if (libname.contains("feld60：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld60+=time1;
					//System.out.println(feld60);
				}
				if (libname.contains("feld55：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld55+=time1;
					//System.out.println(feld55);
				}
				if (libname.contains("feld50：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld50+=time1;
					//System.out.println(feld50);
				}
				if (libname.contains("feld45：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld45+=time1;
					//System.out.println(feld45);
				}
				if (libname.contains("feld40：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld40+=time1;
					//System.out.println(feld40);
				}
				if (libname.contains("feld35：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld35+=time1;
					//System.out.println(feld35);
				}
				if (libname.contains("feld30：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld30+=time1;
					//System.out.println(feld30);
				}
				if (libname.contains("feld25：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld25+=time1;
					
				}
				if (libname.contains("feld20：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld20+=time1;
				
				}
				if (libname.contains("feld15：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld15+=time1;
					
				}
				if (libname.contains("feld10：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld10+=time1;
					
				}
				if (libname.contains("feld05：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld05+=time1;
					
				}
				if (libname.contains("feld00：")) {
					String feld915=libname.substring(7);
					int time1=Integer.parseInt(feld915);
					feld00+=time1;
					
				}
			}
			System.out.println(4127-feld95);
			System.out.println(4127-feld90);
			System.out.println(4127-feld85);
			System.out.println(4127-feld80);
			System.out.println(4127-feld75);
			System.out.println(4127-feld70);
			System.out.println(4127-feld65);
			System.out.println(4127-feld60);
			System.out.println(4127-feld55);
			System.out.println(4127-feld50);
			System.out.println(4127-feld45);
			System.out.println(4127-feld40);
			System.out.println(4127-feld35);
			System.out.println(4127-feld30);
			System.out.println(4127-feld25);
			System.out.println(4127-feld20);
			System.out.println(4127-feld15);
			System.out.println(4127-feld10);
			System.out.println(4127-feld05);
			System.out.println(4127-feld00);
				//System.out.println(libname);
		}			
	}
	
    public static List<String> txt2String(File file){
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//resultList.add(s.substring(0, s.lastIndexOf(".")));
            	resultList.add(s);
                //result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
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
    
	 public static List<String> FindFolder(String path) {
	    	List<String> apknameList=new ArrayList<>();
	    	DeleteLibPackage aa=new DeleteLibPackage();
	    	List<String> a=aa.traverseFolder1("E:\\LibDetect实验groundtruth\\反编译后的apk");
	    	for(String folder:a){
	    		if(folder.contains("\\smali")){
	    			String apkname=folder.substring(folder.indexOf("反编译后的apk\\")+9, folder.indexOf("\\smali"));
	    			if(!apknameList.contains(apkname))
	    			apknameList.add(apkname);
	    			System.out.println(folder);
	    		}    				
	    	}
			return apknameList;
	    	
			
		}
	 
	 
		public static List<String> findfile(String path){//找到path的一级文件夹目录
			List<String> fileList=new ArrayList<>();
			System.out.println("=========指定目录下的所有文件==========");
			File file = new File(path);
			File[] aa = file.listFiles();
			for (int i = 0; i < aa.length; i++) {
				if (aa[i].isFile()) {
					if(aa[i].toString().contains("txt")){
						//if(!aa[i].toString().contains("apktool.bat")){
		            System.out.println(aa[i].toString());
					fileList.add(aa[i].toString().replaceAll("\\\\", "\\\\\\\\"));
						//}
		
					}

				}
			}
			return fileList;
			
		}
	 
	 public static List<String> findfolder1(String path){//找到path的一级文件夹目录
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
 
	public static void copy(File file, File toFile) throws Exception {
		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath=filepath.replaceAll("\\\\", "/");
			String toFilepath = toFile.getAbsolutePath();
			toFilepath=toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
			File copy=new File(toFilepath);
			//复制文件夹
			if (!copy.exists()) {
				copy.mkdir();
			}
			//遍历文件夹
			for (File f : file.listFiles()) {
				copy(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath=filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath=toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
				
				//写文件
				File newFile = new File(toFilepath);
				fis = new FileInputStream(file);
				fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			} else {
				//写文件
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			}
 
		}
	}
}
