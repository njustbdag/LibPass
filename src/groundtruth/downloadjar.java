package groundtruth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cn.fudan.libpecker.main.aar2dex;
import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.LibProfile;
import groundtruth.jar2dex;

public class downloadjar {
	static String dexFilepathString="G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile";
	static String jaraarFilepathString="D:\\��������jar";
	static List<String> targetretestRootPackageList;
    public downloadjar(List<String> targetretestRootPackageList) {
        this.targetretestRootPackageList = targetretestRootPackageList;
    }
    
	public static void main(String[] args) throws Exception  {  
		/**CopylibToapk copylibToapk=new CopylibToapk();
		File file = new File("G:\\LibPeckerԴ��\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile");
		File tofile = new File("E:\\groundtruth\\����dex\\104_Job_Search_v1.11.0_apkpure.com\\dexfile");
		File tofile1 = new File("E:\\groundtruth\\����dex\\104_Job_Search_v1.11.0_apkpure.com");
		File fileParent = tofile.getParentFile();
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}
		tofile.createNewFile();		
		copylibToapk.copy(file,tofile1);**/
		//List<String> retestRootPackageList=new ArrayList<>();
		//downloadjar downLoadjar=new downloadjar(retestRootPackageList);
		//downLoadjar.startDownload();
		//startDownload();
	    String photoUrl = "https://search.maven.org/remotecontent?filepath="+"io/workflowengine/workflow-ravendb/2.0/workflow-ravendb-2.0.jar";   //�ļ�URL��ַ                                     
		String address1 = "http://search.maven.org/solrsearch/select?q="+"de.tavendo.autobahn";
		String address = "http://search.maven.org/solrsearch/select?q="+"react-native";
		String address0 = "http://search.maven.org/solrsearch/select?q=a:"+"de.tavendo.autobahn";
		String a = searchUrlAs(address); 
		List<String> URLList=dealwithresponse(a);//��ȡÿ��jar�������ص�ַ
		List<String> jarpathList=download(URLList,"kl");
		//jaroraartodex(jarpathList);

	}
	public static HashMap<String, Integer> CountDownload() throws Exception {
		Copydex copydex=new Copydex();
		HashMap<String, Integer> aRPHashMap=new HashMap<>();
		int count=1;
		for (String retestRootPackagestring : targetretestRootPackageList) {
			System.out.println("*****��"+count+"������"+retestRootPackagestring+"����������ʼ*****");
			String address = "http://search.maven.org/solrsearch/select?q="+retestRootPackagestring;
			String a = searchUrlAs(address); 
			List<String> URLList=dealwithresponse(a);//��ȡÿ��jar�������ص�ַ
			//List<String> jarpathList=download(URLList,apkname);
			//jaroraartodex(jarpathList);	
			System.out.println("*****��"+count+"�����������ҵ�"+URLList.size()+"��lib*****");
			aRPHashMap.put(retestRootPackagestring, URLList.size());
			System.out.println("*****��"+count+"������"+retestRootPackagestring+"�����������*****");
			count++;
		}
		return aRPHashMap;
	}
	public static String startDownload(String apkname) throws Exception {
		Copydex copydex=new Copydex();
		int count=1;
		for (String retestRootPackagestring : targetretestRootPackageList) {
			System.out.println("*****��"+count+"������"+retestRootPackagestring+"����������ʼ*****");
			String address = "http://search.maven.org/solrsearch/select?q="+retestRootPackagestring;
			String a = searchUrlAs(address); 
			List<String> URLList=dealwithresponse(a);//��ȡÿ��jar�������ص�ַ
			List<String> jarpathList=download(URLList,apkname);
			jaroraartodex(jarpathList);	
			System.out.println("*****��"+count+"�����������ҵ�"+jarpathList.size()+"��lib*****");
			System.out.println("*****��"+count+"������"+retestRootPackagestring+"�����������*****");
			count++;
		}
		String newdexPath="E:\\groundtruth\\����dex\\"+apkname;
		String newjaraarPath="E:\\groundtruth\\����JAR��AAR\\"+apkname;
		copydex.copyFolder(dexFilepathString,newdexPath);
		copydex.delAllFile(dexFilepathString);
		copydex.copyFolder(jaraarFilepathString,newjaraarPath);
		copydex.delAllFile(jaraarFilepathString);
		System.out.println("���ص�JAR��AAR����·����"+newjaraarPath);
		System.out.println("���ص�DEX����·����"+newdexPath);
		Cleanupemptyfile(newdexPath);
		return newdexPath;
	}

	private static void Cleanupemptyfile(String newdexPath) {
		List<String> dexfilepath=new ArrayList<String>();
		List<String> needdeldexfilepath=new ArrayList<String>();
		dexfilepath.addAll(traverseFolder(newdexPath));
		for (String dexfile : dexfilepath) {
			System.out.println(dexfile);
			String resourceSizeMb=getFileSize(dexfile);
		    if (resourceSizeMb.equals("0KB")) {
		    	needdeldexfilepath.add(dexfile);
		    	 System.out.println("���ļ���СΪ0KB,�쳣ɾ����");
			}
		}
	}
	
	public static List<String> traverseFolder(String path) {//�ҵ���classes.jarΪ��׺���������ļ�
    	List<String> dexfilepath=new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("�ļ���:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else if(file2.getAbsolutePath().contains("dex")){
                    System.out.println(file2.getAbsolutePath());
                    dexfilepath.add(file2.getAbsolutePath());//.replace("\\", "\\\\")
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("�ļ���:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else if(file2.getAbsolutePath().contains("dex")){
                        System.out.println(file2.getAbsolutePath());
                        dexfilepath.add( file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("�ļ�������!");
        }
        System.out.println("�ļ��й���:" + folderNum + ",�ļ�����:" + fileNum);
    	return dexfilepath;

    }

	public static String getFileSize(String path) {
	    String resourceSizeMb = null;
	    try {
	        // ָ��·������
	        File f = new File(path);

	        FileInputStream fis = new FileInputStream(f);

	        DecimalFormat df = new DecimalFormat("#.##");

	        // double resourceSize = (double)((double) fis.available() / 1024);
	        // ELog.e(TAG, "resourceSize:" + resourceSize);

	        if((double)((double) fis.available() / 1024) > 1000) {
	            resourceSizeMb = df.format((double)((double) fis.available() / 1024 / 1024)) + "MB";
	        } else {
	            resourceSizeMb= df.format((double)((double) fis.available() / 1024)) + "KB";
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        resourceSizeMb = null;
	    } 
	    return resourceSizeMb;
	}


	private static void jaroraartodex(List<String> jaraarpathList) throws IOException {
		//System.out.println("kais");
		jar2dex jartodex=new jar2dex();
		aar2dex aartodex=new aar2dex();
		List<String> jarpathList=new ArrayList<>();
		List<String> aarpathList=new ArrayList<>();
		for (String jarPathstring : jaraarpathList) {
			if (jarPathstring.contains(".jar")) {
				jarpathList.add(jarPathstring);
			}
			else if (jarPathstring.contains(".aar")) {
				aarpathList.add(jarPathstring);
			}							
		}
		jarpathList.addAll(aartodex.execute(aarpathList));
		jartodex.execute(jarpathList);				
	}

	private static List<String> download(List<String> URLList,String apkName) {
		List<String> jarpathList=new ArrayList<>();
		for (String URLstring : URLList) {//��������ÿ��jar�� 
			//postMethodHanya(address);
		    String fileName = URLstring.substring(URLstring.lastIndexOf("/"));     //Ϊ���ص��ļ�����
		    System.out.println("fileName:"+fileName);
		    String filePath = "D:\\��������jar";     //����Ŀ¼
		    File file = saveUrlAs(URLstring, filePath,fileName,"GET");  
		    System.out.println(fileName+"������ɣ�");
		    System.out.println("D:\\\\��������jar\\\\"+fileName.substring(1));
		    jarpathList.add("D:\\\\��������jar\\\\"+fileName.substring(1));
		}
		return jarpathList;
	}

	public static List<String> dealwithresponse(String input){
		List<String> resultList=new ArrayList<>();
		List<String> URLList=new ArrayList<>();
		input=input.substring(input.indexOf("["));
		int count=1;
		while (input.contains("latestVersion")) {
			System.out.println("��"+count+"�����");
			String aString=input.substring(input.indexOf("{"), input.indexOf("}")+1);
			input=input.substring(input.indexOf("}")+2);
			//System.out.println(input);
			//System.out.println("�ָ�");
			aString=aString.replace("\"", "");
			System.out.println(aString);
			resultList.add(aString);
			count++;
		}
		for (String restring : resultList) {
			String gString=restring.substring(restring.indexOf("g:")+2,restring.indexOf(",a:"));
			System.out.println("groupid:"+gString);
			String aString=restring.substring(restring.indexOf("a:")+2,restring.indexOf(",latestVersion"));
			System.out.println("artefactid:"+aString);
			String vString=restring.substring(restring.indexOf("latestVersion:")+14,restring.indexOf(",repositoryId"));
			//System.out.println(vString);
			String ecString=restring.substring(restring.indexOf("p:")+2,restring.indexOf(",timestamp:"));
			//System.out.println(ecString);
			if (ecString.equals("jar")||ecString.equals("aar")) {
				gString=gString.replace(".", "/");
				//System.out.println(gString);
				String reString2="https://search.maven.org/remotecontent?filepath="+gString+"/"+aString+"/"+vString+"/"+aString+"-"+vString+"."+ecString;
				System.out.println(reString2);
				URLList.add(reString2);
			}
		}
		return URLList;
	}
/*�����ļ�*/
	public static File saveUrlAs(String url,String filePath, String fileName,String method){  
	     //System.out.println("fileName---->"+filePath);  
	     //������ͬ���ļ���Ŀ¼  
	     File file=new File(filePath);  
	     //�ж��ļ����Ƿ����  
	     if (!file.exists())  
	    {  
	        //����ļ��в����ڣ��򴴽��µĵ��ļ���  
	         file.mkdirs();  
	    }  
	     FileOutputStream fileOut = null;  
	     HttpURLConnection conn = null;  
	     InputStream inputStream = null;  
	     try  
	    {  
	         // ��������  
	         URL httpUrl=new URL(url);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //��Post��ʽ�ύ����Ĭ��get��ʽ  
	         conn.setRequestMethod(method);  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post��ʽ����ʹ�û���   
	         conn.setUseCaches(false);  
	         //����ָ������Դ   
	         conn.connect();  
	         //��ȡ����������  
	         inputStream=conn.getInputStream();  
	         BufferedInputStream bis = new BufferedInputStream(inputStream);  
	         //�ж��ļ��ı���·�������Ƿ���/��β  
	         if (!filePath.endsWith("/")) {  
	  
	             filePath += "/";  
	  
	             }  
	         //д�뵽�ļ���ע���ļ�����·���ĺ���һ��Ҫ�����ļ������ƣ�  
	         fileOut = new FileOutputStream(filePath+fileName);  
	         BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
	           
	         byte[] buf = new byte[4096];  
	         int length = bis.read(buf);  
	         //�����ļ�  
	         while(length != -1)  
	         {  
	             bos.write(buf, 0, length);  
	             length = bis.read(buf);  
	         }  
	         bos.close();  
	         bis.close();  
	         conn.disconnect();  
	    } catch (Exception e)  
	    {  
	         e.printStackTrace();  
	         System.out.println("�׳��쳣����");  
	    }  
	       
	     return file;  
	       
	 } 
	
	public static String searchUrlAs(String address) throws IOException{
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(input);
        String line = "";
        StringBuffer stringBuffer = new StringBuffer();
        while((line = reader.readLine()) != null){
        	//System.out.println(line);
            stringBuffer.append(line);
        }
        
        String string = stringBuffer.toString();
		return string;
	}
	
	public static InputStream postMethodHanya(String urlStr) {
 
		InputStream is = null;
		try {			
			URL url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			urlCon.setDoOutput(false);
			urlCon.setDoInput(true);
			urlCon.setConnectTimeout(40000);
			urlCon.setReadTimeout(40000);
			urlCon.setUseCaches(false);
			
//			urlCon.setRequestProperty("Content-Type", contentType);
//			urlCon.setRequestProperty("Content-length",
//					String.valueOf(xmlData.length));
//			// urlCon.setFixedLengthStreamingMode(xmlData.length);
//			DataOutputStream printout = new DataOutputStream(
//					urlCon.getOutputStream());
//			printout.write(xmlData);
//			printout.flush();
//			printout.close();
			is = urlCon.getInputStream();
			System.out.println(is);
		} catch (IOException e) {
		
			e.printStackTrace();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return is;
	}


}
