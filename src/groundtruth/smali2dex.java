package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class smali2dex {
	public static void main(String[] args) throws IOException{
		String smaliPath = "H:\\smali文件-豌豆荚\\45360.apk\\smali\\net\\youmi\\android";
		String dexPath = "45360.apk.net.youmi.android";
		smali22dex(smaliPath,dexPath);
		//runbatfile("E:\\groundtruth\\createdex\\batfileset2\\App_Annie_v1.39.0_(8680)_apkpure.com.apk.android.arch.bat"); 
	}

	public static String smali22dex(String smaliPath, String dexPath) throws IOException {
		/** System.out.println(smaliPath);
		System.out.println(dexPath);**/
			//String dexfilename=dexPath.substring(dexPath.lastIndexOf("\\")+1);
		    String dexfilename=dexPath;
			System.out.println(dexfilename);
			String dexfileTemp=creatdexFile(dexfilename);			
	        String batfilepath = creatbatFile(dexfilename);
			String batcontent="cd /d %~dp0\r\njava -jar E:\\groundtruth\\createdex\\smali-2.1.3.jar -o "+dexfileTemp+" "+smaliPath;
			jar2dex.writeTxtFile(batcontent,batfilepath);
	        runbatfile(batfilepath); 
	        System.out.println(dexfilename+"转dex已成功");/****/
	        return dexfileTemp;
	}
	
	public static String creatdexFile(String name) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp = "E:\\groundtruth\\createdex\\dexfileset21\\"  + name + ".dex";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
	}
	public static String creatbatFile(String name) throws IOException {
		boolean flag = false;
		String filenameTemp;
		filenameTemp ="E:\\groundtruth\\createdex\\batfileset2\\" + name + ".bat";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return filenameTemp;
	}
	
	
	public static boolean runbatfile(String batfilepath) throws IOException{
        Runtime rt = Runtime.getRuntime();
        Process ps = null;
       try {
      ps = rt.exec("cmd.exe /C start /b " + batfilepath);
      InputStream in = ps.getInputStream();
      InputStreamReader isr=new InputStreamReader(in);
      BufferedReader br=new BufferedReader(isr);
      String line=null;
      //System.out.println("test");

	      while((line=br.readLine())!=null&!line.contains(".dex")) {
         System.out.println(line);
      }	

      in.close();
      ps.waitFor();
       } catch (IOException e1) {
      e1.printStackTrace();
        } catch (InterruptedException e) {
       e.printStackTrace();
       }
       catch (Exception e){
    	   return false;
       }
	return true;

	}
}
