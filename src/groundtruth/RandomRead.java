package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.fudan.libpecker.main.aar2dex;

public class RandomRead {
	private static String smalipath="E:/LibDetectʵ��groundtruth/��������apk/";
	public static void main(String args[]) throws IOException{
	List<String> dexname=new ArrayList<>();
	aar2dex aa=new aar2dex();
	dexname.addAll(aa.traverseFolder1("G:\\libdetectiongroundtruth\\lib340"));//�ҵ���Ŀ¼�µ�����dex�ļ�·��
	Random ra =new Random(); 
	List<String> apknameList=FindFolder("E:\\LibDetectʵ��groundtruth\\��������apk");  	
	for(String aaa:apknameList){
		List<String> liblist=new ArrayList<>();
		System.out.println(aaa);
		creattxtFile(aaa);//����lib.txt�ĵ�
		for(int i=0;i<10;i++){//���ѡȡ10��dex�ŵ�lib.txt��
			String dexString=dexname.get(ra.nextInt(339)+1).substring(34);
			if(!liblist.contains(dexString))
			writeTxtFile(dexString,"E:/LibDetectʵ��groundtruth/��������apk/"+aaa+"/lib.txt");
			System.out.println(dexString);
		}
	}
	System.out.println(apknameList.size());
	//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar����Ŀ¼

	//creattxtFile("b9430d8cc42230938a353a4b3e4c92f3.malware");
	}	
	
	
	public static int RandomNum() {
		Random random = new Random();
        int[] arr = new int[4];
        arr[0] = random.nextInt(9);
        int i = 1;
        //��ѭ�������ĸ���
        while(i <=3) {
            int x = random.nextInt(9);
            /*��ѭ����������������������ɵıȽϣ�
             *��ͬ��������ѭ����������һ����������бȽ�
             *��ǰ�������ɵĶ���ͬ����������µ������
            */
            for (int j = 0; j <= i - 1; j++) {
                //��ͬ��������ѭ����������һ����������бȽ�
                if (arr[j] == x) {
                    break;
                }
                //ִ����ѭ����ǰ�������ɵĶ���ͬ����������µ������
                if(j+1==i){
                    arr[i] = x;
                    i++;
                }
            }
        }    
        //��ӡ�������ɵ������
        for (int aaa : arr) {
            System.out.print(aaa);
        }
		return i;	
	}
	
	public static String creattxtFile(String apkname) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp = smalipath + apkname + "/lib.txt";
		System.out.println(filenameTemp);
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
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
	
	
	 public static boolean writeTxtFile(String newStr,String filenameTemp) throws IOException {
			// �ȶ�ȡԭ���ļ����ݣ�Ȼ�����д�����
			boolean flag = false;
			String filein = newStr + "\r\n";
			String temp = "";
	 
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
	 
			FileOutputStream fos = null;
			PrintWriter pw = null;
			try {
				// �ļ�·��
				File file = new File(filenameTemp);
				// ���ļ�����������
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				StringBuffer buf = new StringBuffer();
	 
				// ������ļ�ԭ�е�����
				for (int j = 1; (temp = br.readLine()) != null; j++) {
					buf = buf.append(temp);
					// System.getProperty("line.separator")
					// ������֮��ķָ��� �൱�ڡ�\n��
					buf = buf.append(System.getProperty("line.separator"));
				}
				buf.append(filein);
	 
				fos = new FileOutputStream(file);
				pw = new PrintWriter(fos);
				pw.write(buf.toString().toCharArray());
				pw.flush();
				flag = true;
			} catch (IOException e1) {
				// TODO �Զ����� catch ��
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
	
	
	
	
	
	
}
