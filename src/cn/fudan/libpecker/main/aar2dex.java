package cn.fudan.libpecker.main;

import groundtruth.DecompressZipp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
 
/**
 * java�����޸�ָ���ļ��������к�׺�����ļ�Ϊ�����׺���Ĵ���
 * @author yangwenxue(vashon)
 *
 */
public class aar2dex {
	public static void main(String args[]){
		aar2dex aa=new aar2dex();
		String aarPathString="D:\\��������jar";//aar����Ŀ¼
		//aa.execute(aarPathString);//��ʼ����aar
		//a.addAll(aa.traverseFolder1("E:\\pachongTPLSAnalytics\\Amazon-AWS-Mobile-Analytics"));
		
		/**List<String>po=new ArrayList<>();
		List<String>real=new ArrayList<>();
		for(String aarpath:a){//��a�е��ļ�������copydest��
			if(!po.contains(aarpath.substring(aarpath.lastIndexOf("\\"),aarpath.lastIndexOf("-"))))
				po.add(aarpath.substring(aarpath.lastIndexOf("\\"),aarpath.lastIndexOf("-")));
			String copysour=aarpath;			
			
		}
		for(String p:po){
			System.out.println(p+"�ҵ���");			
			real.add(find(a,p));
			String copydest="G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\lib";
			copyFile(find(a,p),copydest);
			}
		System.out.println(real.size());**/
		}
	
	public  List<String> execute(List<String> aarPathString) {
		List<String> zipPathList=trans(aarPathString);//��aarת��zip
		DecompressZipp decompressZip=new DecompressZipp(zipPathList);
		List<String> jarPathList=decompressZip.startdecompressZip();//��ʼ��ѹ��zip		
		return jarPathList;
	}

	private static List<String> trans(List<String> aarPathString) {
		List<String> zipPathList=new ArrayList<>();
		//List<String> a=new ArrayList<>();
		//a.addAll(traverseFolder2(aarPathString));//
		//String dir="G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\4.8.0"+File.separator+"aar";
		//File file=new File(dir);
		String srcSuffix="aar";
		String dstSuffix="zip";//��a�е�aar�ļ��ĺ�׺����Ϊzip
		//List<String> paths=listPath(file,srcSuffix);
		for(String path : aarPathString){//��a�е�aar�ļ��ĺ�׺����Ϊzip
			zipPathList.add(path.replace("aar", "zip"));
			File srcFile=new File(path);
			String name=srcFile.getName();
			int idx=name.lastIndexOf(".");
			String prefix=name.substring(0, idx);
			System.out.println(srcFile.getParent());
			File dstFile=new File(srcFile.getParent()+"/"+prefix+"."+dstSuffix);
			if(dstFile.exists()){
				srcFile.delete();
				continue;
			}
			srcFile.renameTo(dstFile);
		}
		return zipPathList;
		
	}

	public static String find(List<String> a,String p){
		String po = null;
		for(String aarpath:a){
			if(aarpath.contains(p))
				po=aarpath;
				//System.out.println(aarpath);
		}
		System.out.println(po);
		return po;
		
	}
		
	
	

public List<String> traverseFolder1(String path) {//�ҵ���classes.jarΪ��׺���������ļ�
	List<String> dexfilepath=new ArrayList<String>();
    int fileNum = 0, folderNum = 0;
    File file = new File(path);
    if (file.exists()) {
        LinkedList<File> list = new LinkedList<File>();
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                //System.out.println("�ļ���:" + file2.getAbsolutePath());
                list.add(file2);
                folderNum++;
            } else if(file2.getAbsolutePath().contains("aar")){
                System.out.println(file2.getAbsolutePath());
                dexfilepath.add(file2.getAbsolutePath());//.replace("\\", "\\\\")
                fileNum++;
            }
        }
        File temp_file;
        while (!list.isEmpty()) {
            temp_file = list.removeFirst();
            files = temp_file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("�ļ���:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else if(file2.getAbsolutePath().contains("apk")){
                    System.out.println(file2.getAbsolutePath());
                    dexfilepath.add( file2.getAbsolutePath());
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


public static List<String> traverseFolder2(String path) {//�ҵ���classes.jarΪ��׺���������ļ�
	List<String> dexfilepath=new ArrayList<String>();
    int fileNum = 0, folderNum = 0;
    File file = new File(path);
    if (file.exists()) {
        LinkedList<File> list = new LinkedList<File>();
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                //System.out.println("�ļ���:" + file2.getAbsolutePath());
                list.add(file2);
                folderNum++;
            } else if(file2.getAbsolutePath().contains("aar")){
                System.out.println(file2.getAbsolutePath());
                dexfilepath.add(file2.getAbsolutePath());//.replace("\\", "\\\\")
                fileNum++;
            }
        }
        File temp_file;
        while (!list.isEmpty()) {
            temp_file = list.removeFirst();
            files = temp_file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("�ļ���:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else if(file2.getAbsolutePath().contains("aar")){
                    System.out.println(file2.getAbsolutePath());
                    dexfilepath.add( file2.getAbsolutePath());
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

	/**
	 * ��ȡָ��·���µ����з���������·��
	 * @param file ·��
	 * @param srcSuffix ��׺��
	 * @return
	 */
	private static List<String> listPath(File path, String srcSuffix) {
		List<String> list=new ArrayList<String>();
		File[] files=path.listFiles();
		Arrays.sort(files);
		for(File file : files){
			if(file.isDirectory()){//�����Ŀ¼
				//�ؼ������������������(�ݹ��ж��¼�Ŀ¼)
				List<String> _list= listPath(file, srcSuffix);//�ݹ����
				list.addAll(_list);//��������ӵ�������
			}else{//����Ŀ¼
				String name=file.getName();
				int idx=name.lastIndexOf(".");
				String suffix=name.substring(idx+1);
				if(suffix.equals(srcSuffix)){
					list.add(file.getAbsolutePath());//���ļ��ľ���·����ӵ�������
					System.out.println(file.getAbsolutePath());
				}
			}
		}
		return list;
	}
	/**
	 * ����ָ��·���µ��ļ�����һĿ¼
	 * @param sour ·��
	 * @param dest ��׺��
	 * @return
	 */
	private static void copyFile(String sour, String dest) {
		//��ȡ����
		Runtime run = Runtime.getRuntime();
		Process p = null;
		//�õ�Ŀ���ļ���
		//File sourFile =new File(sour); 
		//String  filename = sourFile.list()[0];
		String inputname = sour;//+filename;
		String command = "cmd /c copy  "+inputname+"  "+dest;
		System.out.println(command);
		//ִ��doc����
		try {
			p = run.exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



}
