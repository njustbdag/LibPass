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

//�ҵ���Ŀ¼�µ�����apk��
    	List<String> apknameList=getFiles("E:\\libdetection����\\LibSearcher����\\ʵ��\\���ϵ�67��apk��smali");
    	//delAllFile("E:/libdetection����/LibSearcher����/ʵ��/smali�ļ�/Hello.apk/smali");
    	for(String aaa:apknameList){
    		System.out.println(aaa);
        	delAllFile("E:/libdetection����/LibSearcher����/ʵ��/smali�ļ�/"+aaa+"/smali");
        	System.out.println("ɾ����ִ�����");
    	}
		System.out.println(apknameList.size());
    }
    
    static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {		        
                //�ļ�����������·��tempList[i].toString()
            	files.add(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")+1));
            	System.out.println(tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")));
            }
           // if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
            //}
        } 
        return files;
    }
    
    public static List<String> FindFolder(String path) {
    	List<String> apknameList=new ArrayList<>();
    	DeleteLibPackage aa=new DeleteLibPackage();
    	List<String> a=aa.traverseFolder1("E:\\LibDetectʵ��groundtruth\\��������apk");
    	for(String folder:a){
    		if(folder.contains("\\smali")){
    			String apkname=folder.substring(folder.indexOf("��������apk\\")+9, folder.indexOf("\\smali"));
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
    	for (int i = 0; i < array.length; i++) {                                                                            //�ݹ�
    	if (array[i].listFiles().length>0&&"reportlets".equals(array[i].getParentFile().getName())) {               //�ж�
    	System.out.println("�ļ��A"+array[i].getName());
    	String list = array[i].getName();                                                                                                        //�ŵ�list���棬
    	System.out.println(list);

    	}
    	}
    	}
    public  boolean deleteFile(String fileName) {//ɾ�������ļ�
        File file = new File(fileName);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
                return true;
            } else {
                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
                return false;
            }
        } else {
            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
            return false;
        }
    }
    /***
	 * ɾ��ָ���ļ����������ļ�
	 * 
	 * @param path �ļ�����������·��
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
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
	
	/***
	 * ɾ���ļ���
	 * 
	 * @param folderPath�ļ�����������·��
	 */
	public  static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


public static List<String> traverseFolder1(String path) {//�ҵ���classes.jarΪ��׺���������ļ�
	List<String> dexfilepath=new ArrayList<String>();
    int fileNum = 0, folderNum = 0;
    File file = new File(path);
    if (file.exists()) {
        LinkedList<File> list = new LinkedList<File>();
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                System.out.println("�ļ���:" + file2.getAbsolutePath());
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
                    System.out.println("�ļ���:" + file2.getAbsolutePath());
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
        System.out.println("�ļ�������!");
    }
    System.out.println("�ļ��й���:" + folderNum + ",�ļ�����:" + fileNum);
	return dexfilepath;

}
}
