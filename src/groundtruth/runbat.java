package groundtruth;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.fudan.libpecker.core.ExecuteCommand;
import cn.fudan.libpecker.main.aar2dex;

public class runbat {
	public static void main(String[] args) throws IOException {
		//LinkedList<String> batpath1=Getfilesize.Ascendingsort();
		Lock lock=new ReentrantLock();
		//LinkedList<String> batpath1=Getfilesize.Descendingsort();
		smali2apk batSmali2apk=new smali2apk();
		List<String> batpath1=batSmali2apk.getFiles("G:/libpecker����/Ԭٻ��/LibPecker_11.28/");//
		//System.out.println(batpath1.size());
		PriorityQueue<Integer> tInteger=new PriorityQueue<>();
		
		int count=0;
		while(count<batpath1.size()){
			System.out.println(batpath1.get(count)+"��ʼִ��");
			try {
			runbatfile(batpath1.get(count));
			}catch (Exception e) {
				e.printStackTrace();
			}
			String batfilepath=batpath1.get(count);
			  //String txtPath = "E:\\LibDetectʵ��groundtruth\\ass\\" + apkName + ".apk.txt";
		      //System.out.println(txtPath);
		     /** while(flag==0){
		    	  File file=new File(txtPath);
		    	  if (file.exists()){
			    	   flag=1;
				    	  System.out.println(txtPath+"�Ѵ���");
		    	  }
		       }**/
			//System.out.println(b.substring(b.lastIndexOf("/") + 1, b.lastIndexOf(".")));
		      //if(flag==1){
		   //System.out.println(batpath1.get(count)+"ִ�����");
		   deleteFile(batpath1.get(count));
		  // System.out.println("��"+count+"��batִ�����");
			count++;
		     // }
			
		}
		//runbatfile("G:/libdetectionʵ���׼�ɼ�/baksmali/batfile/lib1.bat");
//G:/LibPeckerԴ��/LibPecker-masteroriginal/LibPecker-master/bin/batfile/acra-4.8.3.bat
		//runbat test1=new runbat();
		//test1.runbat("news");
		
    }
	
	public static  boolean runFile(String fileName) {//�����ļ������bat
		smali2apk batSmali2apk=new smali2apk();
		List<String> batpath1=batSmali2apk.getFiles(fileName);//G:/libpecker����/Ԭٻ��/LibPecker_11.28/
		int count=0;
		while(count<batpath1.size()){
			System.out.println(batpath1.get(count)+"��ʼִ��");
			try {
			runbatfile(batpath1.get(count));
			}catch (Exception e) {
				e.printStackTrace();
			}
		   deleteFile(batpath1.get(count));
			count++;	
	}
		return false;
	}

    public static  boolean deleteFile(String fileName) {//ɾ�������ļ�
        File file = new File(fileName);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
                return true;
            } else {
                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
                return false;
            }
        } else {
            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
            return false;
        }
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
      String apkName = batfilepath.substring(batfilepath.lastIndexOf("\\") + 1, batfilepath.lastIndexOf("."));
	  System.out.println(apkName);
      //long t1 = System.currentTimeMillis();
      //boolean flag=true;
      while((line=br.readLine())!=null&&!line.contains("����ǿ��")) {//apk*endtime endtime:keytool ����:apk*endtimeI: Baksmaling
        System.out.println(line);
         // if (line.contains("MD5:")) {
			//System.out.println("*/*"+apkName+".apk"+line.replace(":", "").substring(5));
		//}
        // if (line.contains("keytool ����:")) {
			//System.out.println("keytool ����:"+apkName);
		//	flag=false;
		//}
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
       //String apkName = batfilepath.substring(batfilepath.lastIndexOf("/") + 1, batfilepath.lastIndexOf("."));
      // String txtPath = "G:\\libpecker����\\Ԭٻ��\\LibPecker_11.28\\���н��\\" + apkName + ".apk.txt";    
      // System.out.println(txtPath);
	return true;

	}
	
	public void runbat(String batName) {
        String cmd = "cmd /k start G:\\libdetectionʵ���׼�ɼ�\\baksmali\\1ddc4f3804cdf219ae7feaf4647a5e1d79bfc1863208fac98cba54bf4b282994.bat";// pass
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
           InputStream in = ps.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                System.out.print(c);// ����㲻��Ҫ����������п���ע����
            }
            in.close();
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("child thread donn");
    }
	
}

