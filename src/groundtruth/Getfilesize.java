package groundtruth;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Getfilesize {
	public static void main (String[] args){
		Ascendingsort();
		//Descendingsort();
	}
	/**
	 * 实现按照文件大小降序排序
	 */
	public static LinkedList<String> Descendingsort() {
		LinkedList<String> batLinkedList=new LinkedList<>();
		smali2apk batSmali2apk=new smali2apk();
		List<String> a=batSmali2apk.getFiles("G:/libpecker备份/袁倩婷/LibPecker_11.28/");
		HashMap<String, Long> bigFileMap=new HashMap<>();
		LinkedList<String> fileLinkedList=new LinkedList<>();
		LinkedList<Long> filesizeLinkedList=new LinkedList<>();
		TreeSet<Long> filesizeSet=new TreeSet<>();
		 List<String> files = getFiles("E:\\LibDetect实验groundtruth\\运行结果first");
		 for (String filepath : files) {
			//System.out.println(filepath);
			filesizeSet.add(getFileSize(filepath));
			bigFileMap.put(filepath, getFileSize(filepath));
		}
		 for (Long string : filesizeSet) {
			filesizeLinkedList.add(string);
		}
		 for (int i = filesizeLinkedList.size()-1; i >=0; i--) {
				for (String string : bigFileMap.keySet()) {
					if (bigFileMap.get(string).equals(filesizeLinkedList.get(i))) {
						fileLinkedList.add(string.substring(string.lastIndexOf("\\")+1, string.indexOf(".apk.txt")));
					}
				}
		}
		 for (String string : fileLinkedList) {
				for (String string1 : a) {
					if (string1.contains(string)) {
						batLinkedList.add(string1.replaceAll("\\\\","/"));
					}
				}
			//System.out.println(string);//+bigFileMap.get(string)
		}
		 for (String string : batLinkedList) {
			System.out.println(string);
		}
		return batLinkedList;
		
	}
	
	/**
	 * 实现按照文件大小升序排序
	 */
	public static LinkedList<String> Ascendingsort() {
		LinkedList<String> batLinkedList=new LinkedList<>();
		smali2apk batSmali2apk=new smali2apk();
		List<String> a=batSmali2apk.getFiles("G:/libpecker备份/袁倩婷/LibPecker_11.28/");//G:/libpecker备份/袁倩婷/LibPecker_11.28
		HashMap<String, Long> bigFileMap=new HashMap<>();
		LinkedList<String> fileLinkedList=new LinkedList<>();
		LinkedList<Long> filesizeLinkedList=new LinkedList<>();
		TreeSet<Long> filesizeSet=new TreeSet<>();
		 List<String> files = getFiles("E:\\LibDetect实验groundtruth\\运行结果first");//E:\\LibDetect实验groundtruth\\运行结果first
		 for (String filepath : files) {
			//System.out.println(filepath);
			filesizeSet.add(getFileSize(filepath));
			bigFileMap.put(filepath, getFileSize(filepath));
		}
		 for (Long string : filesizeSet) {
			filesizeLinkedList.add(string);
		}
		 for (int i = 0; i <=filesizeLinkedList.size()-1; i++) {
				for (String string : bigFileMap.keySet()) {
					if (bigFileMap.get(string).equals(filesizeLinkedList.get(i))) {
						fileLinkedList.add(string.substring(string.lastIndexOf("\\")+1, string.indexOf(".apk.txt")));
					}
				}
		}
		 for (String string : fileLinkedList) {
				for (String string1 : a) {
					if (string1.contains(string)) {
						batLinkedList.add(string1.replaceAll("\\\\","/"));
					}
				}
			//System.out.println(string);//+bigFileMap.get(string)
		}
		 for (String string : batLinkedList) {
			System.out.println(string);
		}
		return batLinkedList;
		
	}
	/**
     * 获取文件长度
     * @param file
	 * @return 
     */
    public static long getFileSize(String pathname) {
    	File file=new File(pathname);
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            //System.out.println("文件"+fileName+"的大小是："+file.length());
        }
        return file.length();
    }
    /**
     * 获取文件夹中的所有文件路径
     */
    private static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&tempList[i].toString().contains("txt")) {		        
                //文件名，不包含路径tempList[i].toString()
            	files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        } 
        return files;
    }
}
