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
import java.util.HashSet;
import java.util.List;

import njust.lib.Service.LibpeckerResultService;
 
public class CopylibToapk {
	private static int a = 5;
 
	public static void main(String[] args) throws Exception {
		//findfolder1("G:\\libdetectiongroundtruth\\APKset\\apk2smali\\CM_Browser_Ad_Blocker_Fast_Download_Privacy_v5.22.15.0002_apkpure.com.apk");
		//Oldpathparse("CM_Browser_Ad_Blocker_Fast_Download_Privacy_v5.22.15.0002_apkpure.com.apk","net.simonvt.numberpicker");
		///findfirstclassfolder("apk.io.realm");
		List<String> apkfiles =getFiles("E:\\LibDetect实验groundtruth\\smali文件");
		List<String> dexfiles =getFiles("G:\\libdetection实验标准采集\\baksmali\\dex2smali");
		List<String> groundtruthHashSet=readtxt1("E:\\libdetection论文\\LibSearcher论文\\实验\\GROUNDTRUTH.txt");
		for (String groundtruth: groundtruthHashSet) {
			String apknameString=groundtruth.substring(0,groundtruth.indexOf("----"));
			String dexnameString=groundtruth.substring(groundtruth.indexOf("----")+5);
			System.out.println(apknameString);
			System.out.println(findapk(apknameString,apkfiles));
			System.out.println(dexnameString);
			System.out.println(finddex(dexnameString,dexfiles));
			File dexfile=new File(finddex(dexnameString,dexfiles));
			File apkfile=new File(findapk(apknameString,apkfiles)+"\\smali");
			copy(dexfile,apkfile);
			System.out.println("复制完成");
		}
	}
    private static String finddex(String dexnameString, List<String> dexfiles) {
    	//System.out.println("sdsd"+dexnameString);
    	String dexnameString2=dexnameString.substring(0,dexnameString.lastIndexOf("."));
		for (String string : dexfiles) {
			if (string.contains(dexnameString2)) {
				return string;
			}
		}
		return null;
		
	}
	private static String findapk(String apknameString, List<String> apkfiles) {
		for (String string : apkfiles) {
			if (string.contains(apknameString)) {
				return string;
			}
		}
		return null;
		
	}
	public static List<String> Oldpathparse(String apkname,String ARPname) throws Exception {
    	List<String> oldPathList=new ArrayList<>();
    	List<String> dexPathList=new ArrayList<>();
    	List<String> SPPathList=new ArrayList<>();//处理android.support，找到下一级目录
    	oldPathList.addAll(findfolder1("G:\\libdetectiongroundtruth\\APKset\\apk2smali\\"+apkname));
    	for (String oldstring : oldPathList) {
		String oldpath=apkname+"\\\\"+oldstring+"\\\\"+ARPname.replace(".", "\\\\");
    	String newPathString=apkname+"."+ARPname;
		System.out.println(oldpath);
		//SPPathList.addAll(findfolder1(oldpath));
		//for (String sPPathList : SPPathList) {
			try {
		copypath(oldpath,newPathString);
		String dexfileTemp=smali2dex.smali22dex("E:\\groundtruth\\createdex\\smalifileset2\\"+newPathString,"E:\\groundtruth\\createdex\\dexfileset2\\"+newPathString);
		dexPathList.add(dexfileTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//}
		

    	}
    	return dexPathList;
	}
	public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdir();
    }
	
   private static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&tempList[i].toString().contains("txt")) {		        
                //文件名，不包含路径tempList[i].toString()

            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            	files.add(tempList[i].toString().replace("\\", "\\\\"));
            	System.out.println(tempList[i].toString().replace("\\", "\\\\"));
            }
        } 
        return files;
    }
	
	
	public static List<String> readtxt1(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	//if (!resultList.contains(s)) {
                	resultList.add(s);	
                	//System.out.println(s);
				//}	           
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	
    public static void copypath(String oldstring, String tostring2) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("G:\\libdetectiongroundtruth\\APKset\\apk2smali\\"+oldstring);
		createDir("E:\\groundtruth\\createdex\\smalifileset2\\"+tostring2);
		File tofile = new File("E:\\groundtruth\\createdex\\smalifileset2\\"+tostring2);
		copy(file, tofile);
		System.out.println("复制完成");
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
				if (temp.isDirectory()&temp.toString().substring(temp.toString().lastIndexOf("\\")+1).contains("smali")) {
					System.out.println(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
					folderList.add(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
				}
			}
			return folderList;			
		}
	 
	 
	 public static List<String> findfirstclassfolder(String root,String apknameString){//找到path的一级文件夹目录
			List<String> folderList=new ArrayList<>();
			System.out.println("=========指定目录下的所有文件夹==========");
			File fileDirectory = new File("D:\\所有的dex集合");
			for (File temp : fileDirectory.listFiles()) {
				if (!temp.isDirectory()&temp.toString().contains(root)&!temp.toString().contains(apknameString)) {
					System.out.println("findfolder结果："+temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
					folderList.add(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
				}
			}
			return folderList;
			
		}
	 public static void copyFile(String oldPath, String newPath) throws IOException {
	        File oldFile = new File(oldPath);
	        File file = new File(newPath);
	        FileInputStream in = new FileInputStream(oldFile);
	        FileOutputStream out = new FileOutputStream(file);;

	        byte[] buffer=new byte[2097152];
	        int readByte = 0;
	        while((readByte = in.read(buffer)) != -1){
	            out.write(buffer, 0, readByte);
	        }
	    
	        in.close();
	        out.close();
	    }
	 

	 
	public static void copy(File file, File toFile) throws Exception {
		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath=filepath.replaceAll("\\\\", "/");
			//System.out.println(filepath);
			String toFilepath = toFile.getAbsolutePath();
			toFilepath=toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
			//System.out.println(toFilepath);
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
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
				} catch (Exception e) {
					throw new Exception(e);
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
