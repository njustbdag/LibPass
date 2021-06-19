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
	static String dexFilepathString="G:\\LibPecker源码\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile";
	static String jaraarFilepathString="D:\\测试下载jar";
	static List<String> targetretestRootPackageList;
    public downloadjar(List<String> targetretestRootPackageList) {
        this.targetretestRootPackageList = targetretestRootPackageList;
    }
    
	public static void main(String[] args) throws Exception  {  
		/**CopylibToapk copylibToapk=new CopylibToapk();
		File file = new File("G:\\LibPecker源码\\LibPecker-masteroriginal\\LibPecker-master\\bin\\dexfile");
		File tofile = new File("E:\\groundtruth\\新增dex\\104_Job_Search_v1.11.0_apkpure.com\\dexfile");
		File tofile1 = new File("E:\\groundtruth\\新增dex\\104_Job_Search_v1.11.0_apkpure.com");
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
	    String photoUrl = "https://search.maven.org/remotecontent?filepath="+"io/workflowengine/workflow-ravendb/2.0/workflow-ravendb-2.0.jar";   //文件URL地址                                     
		String address1 = "http://search.maven.org/solrsearch/select?q="+"de.tavendo.autobahn";
		String address = "http://search.maven.org/solrsearch/select?q="+"react-native";
		String address0 = "http://search.maven.org/solrsearch/select?q=a:"+"de.tavendo.autobahn";
		String a = searchUrlAs(address); 
		List<String> URLList=dealwithresponse(a);//获取每个jar包的下载地址
		List<String> jarpathList=download(URLList,"kl");
		//jaroraartodex(jarpathList);

	}
	public static HashMap<String, Integer> CountDownload() throws Exception {
		Copydex copydex=new Copydex();
		HashMap<String, Integer> aRPHashMap=new HashMap<>();
		int count=1;
		for (String retestRootPackagestring : targetretestRootPackageList) {
			System.out.println("*****第"+count+"个根包"+retestRootPackagestring+"在线搜索开始*****");
			String address = "http://search.maven.org/solrsearch/select?q="+retestRootPackagestring;
			String a = searchUrlAs(address); 
			List<String> URLList=dealwithresponse(a);//获取每个jar包的下载地址
			//List<String> jarpathList=download(URLList,apkname);
			//jaroraartodex(jarpathList);	
			System.out.println("*****第"+count+"个根包在线找到"+URLList.size()+"个lib*****");
			aRPHashMap.put(retestRootPackagestring, URLList.size());
			System.out.println("*****第"+count+"个根包"+retestRootPackagestring+"在线搜索完成*****");
			count++;
		}
		return aRPHashMap;
	}
	public static String startDownload(String apkname) throws Exception {
		Copydex copydex=new Copydex();
		int count=1;
		for (String retestRootPackagestring : targetretestRootPackageList) {
			System.out.println("*****第"+count+"个根包"+retestRootPackagestring+"在线搜索开始*****");
			String address = "http://search.maven.org/solrsearch/select?q="+retestRootPackagestring;
			String a = searchUrlAs(address); 
			List<String> URLList=dealwithresponse(a);//获取每个jar包的下载地址
			List<String> jarpathList=download(URLList,apkname);
			jaroraartodex(jarpathList);	
			System.out.println("*****第"+count+"个根包在线找到"+jarpathList.size()+"个lib*****");
			System.out.println("*****第"+count+"个根包"+retestRootPackagestring+"在线搜索完成*****");
			count++;
		}
		String newdexPath="E:\\groundtruth\\新增dex\\"+apkname;
		String newjaraarPath="E:\\groundtruth\\新增JAR或AAR\\"+apkname;
		copydex.copyFolder(dexFilepathString,newdexPath);
		copydex.delAllFile(dexFilepathString);
		copydex.copyFolder(jaraarFilepathString,newjaraarPath);
		copydex.delAllFile(jaraarFilepathString);
		System.out.println("下载的JAR或AAR保存路径："+newjaraarPath);
		System.out.println("下载的DEX保存路径："+newdexPath);
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
		    	 System.out.println("该文件大小为0KB,异常删除！");
			}
		}
	}
	
	public static List<String> traverseFolder(String path) {//找到以classes.jar为后缀名的所有文件
    	List<String> dexfilepath=new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
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
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
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
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    	return dexfilepath;

    }

	public static String getFileSize(String path) {
	    String resourceSizeMb = null;
	    try {
	        // 指定路径即可
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
		for (String URLstring : URLList) {//可是下载每个jar包 
			//postMethodHanya(address);
		    String fileName = URLstring.substring(URLstring.lastIndexOf("/"));     //为下载的文件命名
		    System.out.println("fileName:"+fileName);
		    String filePath = "D:\\测试下载jar";     //保存目录
		    File file = saveUrlAs(URLstring, filePath,fileName,"GET");  
		    System.out.println(fileName+"下载完成！");
		    System.out.println("D:\\\\测试下载jar\\\\"+fileName.substring(1));
		    jarpathList.add("D:\\\\测试下载jar\\\\"+fileName.substring(1));
		}
		return jarpathList;
	}

	public static List<String> dealwithresponse(String input){
		List<String> resultList=new ArrayList<>();
		List<String> URLList=new ArrayList<>();
		input=input.substring(input.indexOf("["));
		int count=1;
		while (input.contains("latestVersion")) {
			System.out.println("第"+count+"个结果");
			String aString=input.substring(input.indexOf("{"), input.indexOf("}")+1);
			input=input.substring(input.indexOf("}")+2);
			//System.out.println(input);
			//System.out.println("分隔");
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
/*下载文件*/
	public static File saveUrlAs(String url,String filePath, String fileName,String method){  
	     //System.out.println("fileName---->"+filePath);  
	     //创建不同的文件夹目录  
	     File file=new File(filePath);  
	     //判断文件夹是否存在  
	     if (!file.exists())  
	    {  
	        //如果文件夹不存在，则创建新的的文件夹  
	         file.mkdirs();  
	    }  
	     FileOutputStream fileOut = null;  
	     HttpURLConnection conn = null;  
	     InputStream inputStream = null;  
	     try  
	    {  
	         // 建立链接  
	         URL httpUrl=new URL(url);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //以Post方式提交表单，默认get方式  
	         conn.setRequestMethod(method);  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post方式不能使用缓存   
	         conn.setUseCaches(false);  
	         //连接指定的资源   
	         conn.connect();  
	         //获取网络输入流  
	         inputStream=conn.getInputStream();  
	         BufferedInputStream bis = new BufferedInputStream(inputStream);  
	         //判断文件的保存路径后面是否以/结尾  
	         if (!filePath.endsWith("/")) {  
	  
	             filePath += "/";  
	  
	             }  
	         //写入到文件（注意文件保存路径的后面一定要加上文件的名称）  
	         fileOut = new FileOutputStream(filePath+fileName);  
	         BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
	           
	         byte[] buf = new byte[4096];  
	         int length = bis.read(buf);  
	         //保存文件  
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
	         System.out.println("抛出异常！！");  
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
