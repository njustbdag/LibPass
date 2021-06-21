package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RenameFile {
	/** 
     * �������ļ� 
     * @param fileName 
     * @return 
	 * @throws FileNotFoundException 
     */  
	private static List<String> resultList;
	public static void main(String[] args) throws FileNotFoundException{	   
		/****/List<String> jarfilepath=getFiles("I:\\ʵ���׼\\�»�׼\\apk��ѡlib���");
		resultList=readtxt1("I:\\ʵ���׼\\�»�׼\\δ����repositoryʱ���������.txt");
		for (String string : jarfilepath) {
			readtxt(string);
		}
    	/**for(String libPath:jarfilepath){
    		System.out.println(libPath);
    		String newdexnameString=libPath;
    		String libname=libPath.substring(libPath.lastIndexOf("\\")).substring(1);  		
    		if (libname.contains("classes.dex")) {
    			libname=libname.substring(0, libname.lastIndexOf(".")-7)+".dex";
    			newdexnameString="D:\\\\���е�dex����\\\\"+libname;
    			renameFile(libPath,newdexnameString);
			}
    		System.out.println(newdexnameString);
    	}**/
		//renameFile("E:\\javadsaaa\\com\\bumptech","E:\\javadsaaa\\com\\bum");
	}
    public static void renameFile(String oldfilePath, String newfilePath) {
		 File oldName = new File(oldfilePath);
	        File newName = new File(newfilePath);
	        if(oldName.renameTo(newName)) {
	            System.out.println("��������");
	        } else {
	            System.out.println("Error");
	        }
    }
    
    public static List<String> readtxt1(String filepath){
		File file = new File(filepath);
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            	String[] pStrings=s.split(",");
            	if (pStrings.length==4) {	
            		resultList.add(pStrings[2]+"-----"+pStrings[0]);
				}	
            	if (pStrings.length==3) {	
            		resultList.add(pStrings[1]+"-----"+pStrings[0]);
				}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
    
	private static List<String> readtxt(String filepath) throws FileNotFoundException{
		//List<String> jarfilepath=getFiles("I:\\ʵ���׼\\�»�׼\\��׼�е�apk");
		File file = new File(filepath);
		String apkName=filepath.substring(filepath.lastIndexOf("\\")+1, filepath.lastIndexOf("."));
		System.out.println(apkName);
		List<String> now=new ArrayList<>();
		for (String s : resultList) {
			if (s.contains(apkName)) {
				now.add(s.substring(0, s.indexOf("-")));
			}
		}
		File file1 = new File("I:\\ʵ���׼\\�»�׼\\test\\"+apkName+".txt"); 
		 PrintWriter writer = new PrintWriter(file1); 
        //StringBuilder result = new StringBuilder();
        List<String> resultList=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                	//System.out.println(s.substring(0, s.indexOf("=")));	        
                	//System.out.println(s.substring(s.lastIndexOf("=")+1));
                	//if (jarfilepath.contains(s.substring(s.lastIndexOf("=")+1))) {
						//System.out.println("yes");
                		//renameFile("I:\\ʵ���׼\\�»�׼\\��׼�е�apk\\"+s.substring(s.lastIndexOf("=")+1), "I:\\ʵ���׼\\�»�׼\\��׼�е�apk\\"+s.substring(0, s.indexOf("=")));
				//if (s.split(",").length>2||s.contains(".apk")) {
            	boolean flag=false;
            	if (!s.contains(apkName)) {
            		for (String string : now) {
						if (s.contains(string)) {
							flag=true;
						}
					}
            		if (!flag) {
                		System.out.println(s);
    	            	writer.println(s);   
             		     writer.flush();
					}
				}
				//}		
					//}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        writer.close();  
        return resultList;
    }
    /**
     * ����ת��ĸ
     * @param num
     * @return
     */
    private static String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'a')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);

        return letter;
    }

    
    public static void renameListFile(List<String> oldfilePathl) {
    	int oldfilenum=oldfilePathl.size();
    	int newfilenum=1;
    	for (String oldfilePath : oldfilePathl) {
    		File oldName = new File(oldfilePath);
    		String Upperdirectory=oldfilePath.substring(0, oldfilePath.lastIndexOf("\\\\")+2);
			File newName = new File(Upperdirectory+numberToLetter(newfilenum));
			System.out.println("oldName:"+oldName);
			System.out.println("newName:"+newName);
	        /**if(oldName.renameTo(newName)) {
	            System.out.println("��������");
	        } else {
	            System.out.println("Error");
	        }**/
		}
    }
    
    private  static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&tempList[i].toString().contains("apk")) {		        
                //�ļ�����������·��tempList[i].toString()
            	//System.out.println(tempList[i].toString());
            	//System.out.println(tempList[i].toString().substring(tempList[i].toString().indexOf(".apk")+5,tempList[i].toString().lastIndexOf(".dex")));
            	//if(JudgeObfuscated(tempList[i].toString().substring(tempList[i].toString().indexOf(".apk")+5,tempList[i].toString().lastIndexOf(".dex")))){
            		//System.out.println(tempList[i].toString().substring(20));
            		files.add(tempList[i].toString());
            		//deleteFile(tempList[i].toString());
            	//}
            		
            }
            if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
            }
        } 
        return files;
    }
    
    public static List<String> traverseFolder(String path) {
    	List<String> dexfilepath=new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            List<String> filepathList0=new ArrayList<>();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    String oldfilePath=file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
                    System.out.println("�ļ���:" +  oldfilePath);
                    filepathList0.add(oldfilePath);
					//System.out.println("��һ���ļ��У�"+oldfilePath.substring(0, oldfilePath.lastIndexOf("\\\\")));
                    list.add(file2); 
                    folderNum++;
                } else {
                    System.out.println("�ļ�:" + file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
                    dexfilepath.add( file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
                    fileNum++;
                }
                if (filepathList0.size()!=0) {
                	renameListFile(filepathList0);
                    System.out.println(filepathList0.size());
				}
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                List<String> filepathList=new ArrayList<>();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	String oldfilePath=file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
                        System.out.println("�ļ���:" +  oldfilePath);
                        filepathList.add(oldfilePath);
                        list.add(file2); 
                        folderNum++;
                    } else {
                        System.out.println("�ļ�:" + file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
                        dexfilepath.add( file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
                        fileNum++;
                    }
                }
                if (filepathList.size()!=0) {
                	renameListFile(filepathList);
                    System.out.println(filepathList.size());
				}
            }
        } else {
            System.out.println("�ļ�������!");
        }
        System.out.println("�ļ��й���:" + folderNum + ",�ļ�����:" + fileNum);
    	return dexfilepath;

    }
}
