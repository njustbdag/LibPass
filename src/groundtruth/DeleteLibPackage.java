package groundtruth;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.fudan.libpecker.main.aar2dex;

public class DeleteLibPackage {
 
    public static void main(String[] args) throws Exception {
    	//getFile("G:\\libdetectiongroundtruth\\APKset\\apk2smali");
    	//delFolder("G:/libdetectiongroundtruth/baksmali/dex2smali/1ddc4f3804cdf219ae7feaf4647a5e1d79bfc1863208fac98cba54bf4b282994/org/apache/http/entity/mime/content");

//找到该目录下的所有apk名
    	List<String> apknameList=getFiles("E:\\libdetection论文\\LibSearcher论文\\实验\\补上的67个apk的smali");
    	//delAllFile("E:/libdetection论文/LibSearcher论文/实验/smali文件/Hello.apk/smali");
    	for(String aaa:apknameList){
    		System.out.println(aaa);
        	delAllFile("E:/libdetection论文/LibSearcher论文/实验/smali文件/"+aaa+"/smali");
        	System.out.println("删除包执行完成");
    	}
		System.out.println(apknameList.size());
    }
    
    static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {		        
                //文件名，不包含路径tempList[i].toString()
            	files.add(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")+1));
            	System.out.println(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")));
            }
           // if (tempList[i].isDirectory()) {
                //这里就不递归了，
            //}
        } 
        return files;
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
    			//System.out.println(folder);
    		}    				
    	}
		return apknameList;
    	
		
	}
    
    private static void getFile(String path) {
    	File file = new File(path);
    	File[] array = file.listFiles();
    	for (int i = 0; i < array.length; i++) {                                                                            //递归
    	if (array[i].listFiles().length>0&&"reportlets".equals(array[i].getParentFile().getName())) {               //判断
    	System.out.println("文件夾"+array[i].getName());
    	String list = array[i].getName();                                                                                                        //放到list里面，
    	System.out.println(list);

    	}
    	}
    	}
    public  boolean deleteFile(String fileName) {//删除单个文件
        File file = new File(fileName);
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
    /***
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path 文件夹完整绝对路径
	 * @return
	 */
	public static  boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	/***
	 * 删除文件夹
	 * 
	 * @param folderPath文件夹完整绝对路径
	 */
	public  static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


public static List<String> traverseFolder1(String path) {//找到以classes.jar为后缀名的所有文件
	List<String> dexfilepath=new ArrayList<String>();
    int fileNum = 0, folderNum = 0;
    File file = new File(path);
    if (file.exists()) {
        LinkedList<File> list = new LinkedList<File>();
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                System.out.println("文件夹:" + file2.getAbsolutePath());
                list.add(file2);
                dexfilepath.add(file2.getAbsolutePath());
                folderNum++;
            } else if(file2.getAbsolutePath().contains(".txt")){
               // System.out.println(file2.getAbsolutePath());
                //.replace("\\", "\\\\")
                fileNum++;
            }
        }
        File temp_file;
        while (!list.isEmpty()) {
            temp_file = list.removeFirst();
            files = temp_file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    dexfilepath.add( file2.getAbsolutePath());
                    folderNum++;
                } else if(file2.getAbsolutePath().contains(".txt")){
                    //System.out.println(file2.getAbsolutePath());
                    
                    fileNum++;
                }
            }
        }
    } else {
        System.out.println("文件不存在!");
    }
    System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
	return dexfilepath;

}
}
