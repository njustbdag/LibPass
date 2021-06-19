package groundtruth;

import java.io.BufferedReader;
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException; 
import java.io.File; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReadFile { 
        public ReadFile() { 
        } 
        /** 
         * ��ȡĳ���ļ����µ������ļ� 
         */ 
        public static boolean readfile(String filepath) throws FileNotFoundException, IOException { 
                try { 

                        File file = new File(filepath); 
                        if (!file.isDirectory()) { 
                                /**System.out.println("�ļ�"); 
                                System.out.println("path=" + file.getPath()); 
                                System.out.println("absolutepath=" + file.getAbsolutePath()); 
                                System.out.println("name=" + file.getName()); **/

                        } else if (file.isDirectory()) { 
                                System.out.println("�ļ���"); 
                                String[] filelist = file.list(); 
                                for (int i = 0; i < filelist.length; i++) { 
                                        File readfile = new File(filepath + "\\" + filelist[i]); 
                                        if (!readfile.isDirectory()) { 
                                                System.out.println("path=" + readfile.getPath()); 
                                                System.out.println("absolutepath=" 
                                                                + readfile.getAbsolutePath()); 
                                                System.out.println("name=" + readfile.getName()); 

                                        } else if (readfile.isDirectory()) { 
                                                readfile(filepath + "\\" + filelist[i]); 
                                        } 
                                } 
 
                        } 

                } catch (FileNotFoundException e) { 
                        System.out.println("readfile()   Exception:" + e.getMessage()); 
                } 
                return true; 
        }  

        /** 
         * ɾ��ĳ���ļ����µ������ļ��к��ļ� 
         */ 
        
        
        /*public static boolean deletefile(String delpath) 
                        throws FileNotFoundException, IOException { 
                try { 

                        File file = new File(delpath); 
                        if (!file.isDirectory()) { 
                                System.out.println("1"); 
                                file.delete(); 
                        } else if (file.isDirectory()) { 
                                System.out.println("2"); 
                                String[] filelist = file.list(); 
                                for (int i = 0; i < filelist.length; i++) { 
                                        File delfile = new File(delpath + "\\" + filelist[i]); 
                                        if (!delfile.isDirectory()) { 
                                                System.out.println("path=" + delfile.getPath()); 
                                                System.out.println("absolutepath=" 
                                                                + delfile.getAbsolutePath()); 
                                                System.out.println("name=" + delfile.getName()); 
                                                delfile.delete(); 
                                                System.out.println("ɾ���ļ��ɹ�"); 
                                        } else if (delfile.isDirectory()) { 
                                                deletefile(delpath + "\\" + filelist[i]); 
                                        } 
                                } 
                                file.delete(); 

                        } 

                } catch (FileNotFoundException e) { 
                        System.out.println("deletefile()   Exception:" + e.getMessage()); 
                } 
                return true; 
        }*/ 
        
        /**
		    * @Author��
		    * @Description����ȡĳ��Ŀ¼������ֱ���¼��ļ���������Ŀ¼�µ���Ŀ¼���µ��ļ������Բ��õݹ��ȡ
		    * @Date��
		    */
		    private static List<String> getFiles(String path) {
		        List<String> files = new ArrayList<String>();
		        File file = new File(path);
		        File[] tempList = file.listFiles();

		        for (int i = 0; i < tempList.length; i++) {
		            if (tempList[i].isDirectory()) {		        
		                //�ļ�����������·��tempList[i].toString()
		                String fileName = tempList[i].getName();
		                //System.out.println(fileName);
		                if (!files.contains(fileName)) {
			                files.add(fileName);	
						}
		            }
		            if (tempList[i].isFile()) {
		                //����Ͳ��ݹ��ˣ�
		            }
		        } 
		        return files;
		    }
		    
			public static List<String> readtxt3(File file){
		        //StringBuilder result = new StringBuilder();
		        List<String> resultList=new ArrayList<>();
		        try{
		            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
		            String s = null;
		            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
		            	if (s.contains(".dex")) {
		            		if (!resultList.contains(s.substring(0,s.indexOf(".dex")))) {
				            	resultList.add(s.substring(0,s.indexOf(".dex")));
							}	
			            	//System.out.println(s.substring(0,s.indexOf(".dex")));
						}
		            }
		            br.close();    
		        }catch(Exception e){
		            e.printStackTrace();
		        }
		        return resultList;
		    }
        
        public static void main(String[] args) { 
        	List<String> realLibList=getFiles("E:\\libdetection����\\LibPass����\\ʵ��\\0c19dc80f0190c6c29345bf01bc840f0.malware\\smali");
                File file=new File("E:\\libdetection����\\LibPass����\\ʵ��\\test.txt");
                int count=0; 
                List<String> LibList=readtxt3(file);
                ListIterator<String> iterator=realLibList.listIterator();
                while (iterator.hasNext()) {
					String string = (String) iterator.next();
					 for (String result : LibList) {
						if (result.equals(string)) {
							System.out.println(string);
							count++;
						}
					}
				}
                // deletefile("D:/file"); 
                int FP=LibList.size()-count;
                int LJ=realLibList.size()-count;
                System.out.println("TP:"+count);
                System.out.println("FP:"+FP);
                System.out.println("©��:"+LJ);
                System.out.println("ok"); 
        } 

} 