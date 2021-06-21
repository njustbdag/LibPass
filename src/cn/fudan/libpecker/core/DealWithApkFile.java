package cn.fudan.libpecker.core;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

//import edu.njust.bean.Apk;


public class DealWithApkFile {

	private  String apkFilePath;
	public DealWithApkFile(String apkFilePath) {//apkFilePathҪ�����apk·��
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
			System.out.println(apkFilePath + " ������ɹ��������ļ��ڸ�·���£�\n" + decompileTempPath
					+ "\n");
			return decompileTempPath;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("test.bat�ļ�ִ��ʧ��");
			return null;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		DealWithApkFile dealWithApkFile=new DealWithApkFile("G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\test\\apk\\12.apk");
		dealWithApkFile.doDecompileCommand("E:\\myeclipse\\apache-tomcat-7.0.23\\webapps\\apk\\tool", "E:\\apktool\\test0");
	}

	
}
