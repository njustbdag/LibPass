package groundtruth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.fudan.libpecker.core.ExecuteCommand;
import cn.fudan.libpecker.main.aar2dex;

//import edu.njust.bean.Apk;


public class apk2smali {

	private  String apkFilePath;
	public apk2smali(String apkFilePath) {//apkFilePath要编译的apk路径
		this.apkFilePath = apkFilePath;
	}
	
	private String decompileTempPath;
	
	//private static ApkDecompilation apkDecompilation = new ApkDecompilation(apk);
	/**
	 * 
	 * @param apkFilePath
	 *            要编译apk路径
	 * @return 返回apk编译文件目录
	 * @throws IOException
	 */
	public  String doDecompileCommand(String location, String tempDir) throws IOException {//location是apktool的路径，tempDir为反编译文件缓存目录
		// String locationStr="chdir";
		File apkFile = new File(apkFilePath);

		// 这段用来得到当前工作目录,执行批处理文件需要当前路径
		/*
		 * Process process = Runtime.getRuntime().exec("cmd /k " +
		 * locationStr);//通过cmd程序执行cmd命令 BufferedReader bf = new
		 * BufferedReader(new InputStreamReader(process.getInputStream()));
		 * String location=bf.readLine(); process.destroy();
		 * System.out.println("location:"+location);
		 */
		/******* location为null代表是从Java程序提交的 *******/
		if (location == null) {
			File directory = new File("WebRoot\\tool");// 设定为当前文件夹
			try {
				System.out.println(directory.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 获取标准的路径
			location = directory.getAbsolutePath();

		}
		System.out.println("这里是反编译方法，location:" + location);// 获取绝对路径
		/**
		 * 将批处理命令写入test.bat文件 形如： E: cd apktool apktool d E:\malware2.malware
		 * apkTemp
		 * 
		 * @param content是写入文件的内容
		 * @param drive是当前盘符
		 *            ，为content所需内容
		 * @param tempDir为反编译文件缓存目录
		 *            ，存放在当前目录下
		 * */
		String drive = location.substring(0, 1);
		// String tempDir="F:\\apkTemp\\"+apkFile.getName();
		String apktoolPath = location + "\\apktool";
		decompileTempPath = tempDir + "\\" + apkFile.getName();
		String content = drive + ":\ncd " + apktoolPath + "\napktool d -f "
				+ apkFilePath + " -o " + decompileTempPath;
		System.out.println(content);
		// content=content.replaceAll(" ", "\" \"");//路径中有空格会报错
		String batPath = location + "\\apktool\\test.bat";
		System.out.println("batPath:" + batPath);
		File file = new File(batPath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
		out.write(content.getBytes("utf-8"));// 注意需要转换对应的字符集
		out.close();

		try {
			ExecuteCommand.doBat(batPath);
			//runbatfile(batPath);
			System.out.println(apkFilePath + " 反编译成功，编译文件在该路径下：\n" + decompileTempPath
					+ "\n");
			return decompileTempPath;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("test.bat文件执行失败");
			return null;
		}
	}
	
	public static void runbatfile(String batfilepath) throws IOException{
        Runtime rt = Runtime.getRuntime();
        Process ps = null;
       try {
      ps = rt.exec("cmd.exe /C start /b " + batfilepath);
      InputStream in = ps.getInputStream();
      InputStreamReader isr=new InputStreamReader(in);
      BufferedReader br=new BufferedReader(isr);
      String line=null;
      while((line=br.readLine())!=null&!line.contains("time:")) {
         System.out.println(line);
      }
      in.close();
      ps.waitFor();
       } catch (IOException e1) {
      e1.printStackTrace();
        } catch (InterruptedException e) {
       e.printStackTrace();
       }
       //String apkName = batfilepath.substring(batfilepath.lastIndexOf("/") + 1, batfilepath.lastIndexOf("."));
       //String txtPath = "G:\\libpecker备份\\袁倩婷\\LibPecker_11.28\\运行结果\\" + apkName + ".apk.txt";    
       //System.out.println(txtPath);

	}
	
	public static void main(String[] args) throws IOException {
		List<String> apkpath=new ArrayList<>();
		List<String> apkpath1=new ArrayList<>();
		aar2dex aa=new aar2dex();
		apkpath.addAll(aa.traverseFolder1("H:\\test"));//找到该目录下的所有apk文件路径
		for(String a:apkpath){
			System.out.println(a.replaceAll("\\\\", "\\\\\\\\"));
			apkpath1.add(a.replaceAll("\\\\", "\\\\\\\\"));
		}
		for(String a:apkpath1){
			apk2smali apk2smali=new apk2smali(a);
			apk2smali.doDecompileCommand("E:\\myeclipse\\apache-tomcat-7.0.23\\webapps\\apk\\tool", "H:\\smali文件-豌豆荚");
		}
		
	}

	
}
