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
	private static String smalipath="E:/LibDetect实验groundtruth/反编译后的apk/";
	public static void main(String args[]) throws IOException{
	List<String> dexname=new ArrayList<>();
	aar2dex aa=new aar2dex();
	dexname.addAll(aa.traverseFolder1("G:\\libdetectiongroundtruth\\lib340"));//找到该目录下的所有dex文件路径
	Random ra =new Random(); 
	List<String> apknameList=FindFolder("E:\\LibDetect实验groundtruth\\反编译后的apk");  	
	for(String aaa:apknameList){
		List<String> liblist=new ArrayList<>();
		System.out.println(aaa);
		creattxtFile(aaa);//创建lib.txt文档
		for(int i=0;i<10;i++){//随机选取10个dex放到lib.txt中
			String dexString=dexname.get(ra.nextInt(339)+1).substring(34);
			if(!liblist.contains(dexString))
			writeTxtFile(dexString,"E:/LibDetect实验groundtruth/反编译后的apk/"+aaa+"/lib.txt");
			System.out.println(dexString);
		}
	}
	System.out.println(apknameList.size());
	//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar所在目录

	//creattxtFile("b9430d8cc42230938a353a4b3e4c92f3.malware");
	}	
	
	
	public static int RandomNum() {
		Random random = new Random();
        int[] arr = new int[4];
        arr[0] = random.nextInt(9);
        int i = 1;
        //外循环定义四个数
        while(i <=3) {
            int x = random.nextInt(9);
            /*内循环：新生成随机数和已生成的比较，
             *相同则跳出内循环，再生成一个随机数进行比较
             *和前几个生成的都不同则这个就是新的随机数
            */
            for (int j = 0; j <= i - 1; j++) {
                //相同则跳出内循环，再生成一个随机数进行比较
                if (arr[j] == x) {
                    break;
                }
                //执行完循环和前几个生成的都不同则这个就是新的随机数
                if(j+1==i){
                    arr[i] = x;
                    i++;
                }
            }
        }    
        //打印出来生成的随机数
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
	
	
	
	
	
	
}
