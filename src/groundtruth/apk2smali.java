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
	public apk2smali(String apkFilePath) {//apkFilePathҪ�����apk·��
		this.apkFilePath = apkFilePath;
	}
	
	private String decompileTempPath;
	
	//private static ApkDecompilation apkDecompilation = new ApkDecompilation(apk);
	/**
	 * 
	 * @param apkFilePath
	 *            Ҫ����apk·��
	 * @return ����apk�����ļ�Ŀ¼
	 * @throws IOException
	 */
	public  String doDecompileCommand(String location, String tempDir) throws IOException {//location��apktool��·����tempDirΪ�������ļ�����Ŀ¼
		// String locationStr="chdir";
		File apkFile = new File(apkFilePath);

		// ��������õ���ǰ����Ŀ¼,ִ���������ļ���Ҫ��ǰ·��
		/*
		 * Process process = Runtime.getRuntime().exec("cmd /k " +
		 * locationStr);//ͨ��cmd����ִ��cmd���� BufferedReader bf = new
		 * BufferedReader(new InputStreamReader(process.getInputStream()));
		 * String location=bf.readLine(); process.destroy();
		 * System.out.println("location:"+location);
		 */
		/******* locationΪnull�����Ǵ�Java�����ύ�� *******/
		if (location == null) {
			File directory = new File("WebRoot\\tool");// �趨Ϊ��ǰ�ļ���
			try {
				System.out.println(directory.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// ��ȡ��׼��·��
			location = directory.getAbsolutePath();

		}
		System.out.println("�����Ƿ����뷽����location:" + location);// ��ȡ����·��
		/**
		 * ������������д��test.bat�ļ� ���磺 E: cd apktool apktool d E:\malware2.malware
		 * apkTemp
		 * 
		 * @param content��д���ļ�������
		 * @param drive�ǵ�ǰ�̷�
		 *            ��Ϊcontent��������
		 * @param tempDirΪ�������ļ�����Ŀ¼
		 *            ������ڵ�ǰĿ¼��
		 * */
		String drive = location.substring(0, 1);
		// String tempDir="F:\\apkTemp\\"+apkFile.getName();
		String apktoolPath = location + "\\apktool";
		decompileTempPath = tempDir + "\\" + apkFile.getName();
		String content = drive + ":\ncd " + apktoolPath + "\napktool d -f "
				+ apkFilePath + " -o " + decompileTempPath;
		System.out.println(content);
		// content=content.replaceAll(" ", "\" \"");//·�����пո�ᱨ��
		String batPath = location + "\\apktool\\test.bat";
		System.out.println("batPath:" + batPath);
		File file = new File(batPath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file, false); // ���׷�ӷ�ʽ��true
		out.write(content.getBytes("utf-8"));// ע����Ҫת����Ӧ���ַ���
		out.close();

		try {
			ExecuteCommand.doBat(batPath);
			//runbatfile(batPath);
			System.out.println(apkFilePath + " ������ɹ��������ļ��ڸ�·���£�\n" + decompileTempPath
					+ "\n");
			return decompileTempPath;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("test.bat�ļ�ִ��ʧ��");
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
       //String txtPath = "G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\���н��\\" + apkName + ".apk.txt";    
       //System.out.println(txtPath);

	}
	
	public static void main(String[] args) throws IOException {
		List<String> apkpath=new ArrayList<>();
		List<String> apkpath1=new ArrayList<>();
		aar2dex aa=new aar2dex();
		apkpath.addAll(aa.traverseFolder1("H:\\test"));//�ҵ���Ŀ¼�µ�����apk�ļ�·��
		for(String a:apkpath){
			System.out.println(a.replaceAll("\\\\", "\\\\\\\\"));
			apkpath1.add(a.replaceAll("\\\\", "\\\\\\\\"));
		}
		for(String a:apkpath1){
			apk2smali apk2smali=new apk2smali(a);
			apk2smali.doDecompileCommand("E:\\myeclipse\\apache-tomcat-7.0.23\\webapps\\apk\\tool", "H:\\smali�ļ�-�㶹��");
		}
		
	}

	
}
