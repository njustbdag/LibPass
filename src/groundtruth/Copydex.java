package groundtruth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.fudan.libpecker.main.aar2dex;

public class Copydex {
	public static void main(String[] args) throws Exception {
		//File file = new File("G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile");
		//File tofile = new File("E:\\groundtruth\\����dex\\104_Job_Search_v1.11.0_apkpure.com\\dexfile");
		//File tofile1 = new File("E:\\groundtruth\\����dex\\104_Job_Search_v1.11.0_apkpure.com");
		// ָ���ļ���·��
//		copy("D:/code/eclipse-workspace/crawl_dmtp", "D:/copy");
		//getFiles("E:\\libdetection����\\LibPass����\\ʵ��\\0c19dc80f0190c6c29345bf01bc840f0.malware\\smali");
		/**List<String> copydexname=randomget("G:\\libdetectiongroundtruth\\dexlib\\allaartodex");
		for (String dexname : copydexname) {
			copyFile("D:\\���е�dex����\\"+dexname, "E:\\libdetection����\\LibPass����\\ʵ��\\lib300\\"+dexname);
						//��������ԭ�ļ���·��  
		}**/
		List<String> apkPathList=getFiles("H:\\sample");
		int cnt=1;
		for (String string : apkPathList) {
			 File file1 = new File(string);  
            //��ԭ�ļ��и���ΪA������·���Ǳ�Ҫ�ġ�ע��  
            file1.renameTo(new File(string.substring(0, string.lastIndexOf("\\"))+"\\"+cnt+".apk"));
			System.out.println(string.substring(0, string.lastIndexOf("\\")));
			cnt++;
		}
				//File file = new File("H:\\"+string);  
		        //��ԭ�ļ�����Ϊf:\a\b.xlsx������·���Ǳ�Ҫ�ġ�ע��  
		       // file.renameTo(new File("H:\\2"+string)); 
		        //System.out.println("�������ɹ�");
				//System.out.println(string.substring(string.indexOf("-")));
		//copyFile("D:\\���е�dex����\\zxing-1.0.0.dex", "E:\\libdetection����\\LibPass����\\ʵ��\\lib900\\zxing-1.0.0.dex");
		//delAllFile("G:/LibPeckerԴ��/LibPecker-masteroriginal/LibPecker-master/bin/dexfile");
		//System.out.println("���ƶ��ļ���Ŀ��" + size);
	}
	/**
	 * ���ѡȡ300��dex�ļ�
	 * @param string
	 */
	 private static List<String> randomget(String alldexPath) {
		 List<String> dexname=new ArrayList<>();
		 List<String> copydexname=new ArrayList<>(300);
			dexname.addAll(findfile(alldexPath));//�ҵ���Ŀ¼�µ�����dex�ļ�·��
			dexname.addAll(findfile("G:\\libdetectiongroundtruth\\dexlib\\allaartodex"));
			dexname.addAll(findfile("G:\\libdetectiongroundtruth\\dexlib\\GoogletplsAnalytics"));
			dexname.addAll(findfile("G:\\libdetectiongroundtruth\\dexlib\\GoogletplsAndroid"));
			dexname.addAll(findfile("G:\\libdetectiongroundtruth\\dexlib\\GoogletplsCloud"));
			dexname.addAll(findfile("G:\\libdetectiongroundtruth\\dexlib\\GoogletplsUtilities"));
			dexname.removeAll(findfile("G:\\libdetectiongroundtruth\\lib340"));
			dexname.removeAll(findfile("G:\\libdetectiongroundtruth\\lib586"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib900"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib1200"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib1500"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib1800"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib2100"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib2400"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib2700"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib3000"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib3300"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib3600"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib4200"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib4500"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib4800"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib5100"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib5400"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib5700"));
			dexname.removeAll(findfile("E:\\libdetection����\\LibPass����\\ʵ��\\lib6000"));
			Random ra =new Random(); 
			System.out.println(dexname.size());
			for (int i = 0; i < 4; i++) {
				int ran=ra.nextInt(dexname.size());
				System.out.println(ran+dexname.get(ran));
				copydexname.add(dexname.get(ran)); 
			}
			System.out.println(dexname.size());
			return copydexname;
	}
		private static List<String> findfile(String path){//�ҵ�path��һ���ļ���Ŀ¼
			List<String> fileList=new ArrayList<>();
			System.out.println("=========ָ��Ŀ¼�µ������ļ�==========");
			File file = new File(path);
			File[] aa = file.listFiles();
			for (int i = 0; i < aa.length; i++) {
				if (aa[i].isFile()) {
					if(aa[i].toString().contains("dex")){
						//if(!aa[i].toString().contains("apktool.bat")){
		            //System.out.println(aa[i].toString().substring(aa[i].toString().lastIndexOf("\\")+1));
					fileList.add(aa[i].toString().substring(aa[i].toString().lastIndexOf("\\")+1));
						//}
					}

				} 
			}
			System.out.println(fileList.size());
			return fileList;
			
		}

		
		private static  List<String> findsmalifile(String path){//�ҵ�path��һ���ļ���Ŀ¼
					List<String> folderList=new ArrayList<>();
					System.out.println("=========ָ��Ŀ¼�µ������ļ���==========");
					File fileDirectory = new File(path);
					for (File temp : fileDirectory.listFiles()) {
						if (temp.isDirectory()&temp.toString().substring(temp.toString().lastIndexOf("\\")+1).contains("smali")) {
							System.out.println(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
							folderList.add(temp.toString().substring(temp.toString().lastIndexOf("\\")+1));
						}
					}
					return folderList;			
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
					//delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
					flag = true;
				}
			}
			System.out.println("ɾ���ļ����������");
			return flag;
		}
		
		/**
		    * @Author��
		    * @Description����ȡĳ��Ŀ¼������ֱ���¼��ļ���������Ŀ¼�µ���Ŀ¼���µ��ļ������Բ��õݹ��ȡ
		    * @Date��
		    */
		    private static List<String> getFiles(String path) {
		    	if (path==null) {
					return null;
				}
		        List<String> files = new ArrayList<String>();
		        List<String> filenames = new ArrayList<String>();
		        File file = new File(path);
		        File[] tempList = file.listFiles();

		        for (int i = 0; i < tempList.length; i++) {
		            if (tempList[i].isFile()) {
		                files.add(tempList[i].toString());
		                //�ļ�����������·��
		                String fileName = tempList[i].getName();
		                //System.out.println(tempList[i].toString());
		               String patha=tempList[i].toString();
		               //System.out.println(patha);
		                filenames.add(tempList[i].toString());
		               /**  String tempString=null;
		                String[] strings=patha.split("\\\\");
							tempString=strings[3]	;	
							//System.out.println(tempString);
		                 filenames.add(fileName);
		                String newpathString="H:\\sample\\"+tempString+"\\"+fileName;
		                System.out.println(newpathString);
		                copyFile(tempList[i].toString(),  newpathString);**/
		            }
		            if (tempList[i].isDirectory()) {
		            	//System.out.println(tempList[i].toString());
		            	//��������ԭ�ļ��е�·��  
		               // File file1 = new File("f:/A");  
		                //��ԭ�ļ��и���ΪA������·���Ǳ�Ҫ�ġ�ע��  
		                //file1.renameTo(new File("f:/B"));
		                //����Ͳ��ݹ��ˣ�
		            	//filenames.add(tempList[i].toString());
		            	filenames.addAll(getFiles(tempList[i].toString()));
		            }
		        }
		        return filenames;
		    }
		
	public static void copyFolder(String oldPath, String newPath) { 

		try { 
		(new File(newPath)).mkdirs(); //����ļ��в����� �������ļ��� 
		File a=new File(oldPath); 
		String[] file=a.list(); 
		File temp=null; 
		for (int i = 0; i < file.length; i++) { 
		if(oldPath.endsWith(File.separator)){ 
		temp=new File(oldPath+file[i]); 
		} 
		else{ 
		temp=new File(oldPath+File.separator+file[i]); 
		} 

		if(temp.isFile()){ 
		FileInputStream input = new FileInputStream(temp); 
		FileOutputStream output = new FileOutputStream(newPath + "/" + 
		(temp.getName()).toString()); 
		byte[] b = new byte[1024 * 5]; 
		int len; 
		while ( (len = input.read(b)) != -1) { 
		output.write(b, 0, len); 
		} 
		output.flush(); 
		output.close(); 
		input.close(); 
		} 
		if(temp.isDirectory()){//��������ļ��� 
		copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
		} 
		} 
		} 
		catch (Exception e) { 
		System.out.println("���������ļ������ݲ�������"); 
		e.printStackTrace(); 

		} 
		System.out.println("�����ļ������");
	}
 
	/**
	 * �����ļ���������������
	 * 
	 * @param sourcePath
	 * @param newPath
	 */
	public static void copy(String sourcePath, String newPath) {
		File file = new File(sourcePath);
		if (file != null && file.exists()) {
			String name = newPath + "/" + sourcePath.substring(sourcePath.lastIndexOf("/") + 1, sourcePath.length());
			// �����ļ���
			File dir = new File(name);
			if (!dir.exists()) {
				dir.mkdir();
			}
 
			// ���ÿ����ļ���������
			System.out.println("******"+name);
			copyDir(sourcePath, name);
		} else {
			return;
		}
	}
 
	private static int size;
 
	/**
	 * �����ļ���
	 * 
	 * @param sourcePathԭ�ļ���
	 * @param newPathָ���ļ���
	 */
	private static void copyDir(String sourcePath, String newPath) {
		File sourceFile = new File(sourcePath);
		if (sourceFile.exists() && sourceFile != null) {// �ļ�����
			if (sourceFile.isFile()) {
				copyFile(sourcePath, newPath);
				System.out.println(sourcePath + "  --->  " + newPath);
				size++;
			} else if (sourceFile.isDirectory()) {
				// �����ļ���
				File dir = new File(newPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				// ��ȡ�ļ����ڲ����ļ�
				// �ݹ����
				for (File con : sourceFile.listFiles()) {
					copyDir(sourcePath + "/" + con.getName(), newPath + "/" + con.getName());
//					System.out.println(sourcePath + "/" + con.getName() + "-----" + newPath + "/" + con.getName());
				}
			}
		} else {
			return;
		}
	}
	
	
	/**
	 * ����ı�����
	 * @param fileName�ļ���
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
 
	/**
	 * �����ļ�
	 * 
	 * @param oldFilePath��Դ�ļ�
	 * @param newPathָ���ļ�
	 */
	public static void copyFile(String soucePath, String newPath) {
		// 1��ȷ��Դ
		File sourceFile = new File(soucePath);
		File newFile = new File(newPath);

		File file=new File(newPath.substring(0, newPath.lastIndexOf("\\")));
		if(!file.exists()){//����ļ��в�����
			file.mkdir();//�����ļ���
		}
		// 2��ȷ����
		InputStream fin = null;
		OutputStream fout = null;
 
		try {
			fin = new FileInputStream(sourceFile);
			fout = new FileOutputStream(newFile);
 
			// 3��ȷ������
			byte[] flush = new byte[1024];
			int len = -1;
 
			while ((len = fin.read(flush)) != -1) {
				fout.write(flush, 0, len);
			}
 
			// ��ջ�����
			fout.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 4���ر������ȴ򿪵ĺ�ر�
 
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 
		}
	}

}
