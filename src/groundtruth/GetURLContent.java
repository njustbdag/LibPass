package groundtruth;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import njust.lib.Service.LibRootpackageInfoService;
import cn.fudan.libpecker.main.getdexfilepath;
import cn.fudan.libpecker.model.ApkProfile;
 
public class GetURLContent {
	public static void main(String[] args) throws Exception {
		/**String apkPath = null;
 	     String libPath =null;
        if (args == null || args.length == 2) {
        	 apkPath = args[0];
             libPath = args[1];
        }
        else {
            fail("Usage: java -cp LibPecker3.jar cn.fudan.libpecker.mainProfileBasedLibPecker <apk_path> <lib_path>");
        }**/
    //List<String> singleapkpath=traverseFolder1("G:\\libdetectiongroundtruth\\APKset\\bacth2");
		//Extractapk("G://libdetectiongroundtruth//APKset//bacth2//85_Cafe_v1.0.8_apkpure.com.apk");	
		//getrootpkg(apkPath);
		List<String> apkroot=readallTxt("C://Users//ZJY//Desktop//a.txt");
		for (String astring : apkroot) {
			if (LibRootpackageInfoService.findonebylibRootpackagename(astring)==null&!astring.equals("autovalue.shaded.com.google.common.common")) {
				System.out.println(astring);
				String address = "http://search.maven.org/solrsearch/select?q="+astring;
				String a = searchUrlAs(address); 
				String URLList=dealwithoneresponse(a);//获取每个jar包的下载地址
				if (URLList!=null) {
					System.out.println(URLList);	
				}
			}
		}
    }
	public static List<String> readallTxt(String filePath) {
		Map<String, Integer> apkrootMap=new HashMap<>();
		List<String> dexpathList=new ArrayList<>();
		List<String> dexpathsingleList=new ArrayList<>();
		  try {
		    File file = new File(filePath);
		    if(file.isFile() && file.exists()) {
		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		      BufferedReader br = new BufferedReader(isr);
		      String lineTxt = null;
		      while ((lineTxt = br.readLine()) != null) {
		    	  System.out.println(lineTxt);
		    	  if (!dexpathsingleList.contains(lineTxt)) {
					dexpathsingleList.add(lineTxt);
				}
		    		  dexpathList.add(lineTxt);
		      }
		      br.close();
		    } else {
		      System.out.println("文件不存在!");
		    }
		  } catch (Exception e) {
		    System.out.println("文件读取错误!");
		  }
		  System.out.println(dexpathList.size()); 
		  for (String astring : dexpathsingleList) {
			int count=0;
			for (String bstring : dexpathList) {
				if (astring.equals(bstring)) {
					count++;
				}
			}
			apkrootMap.put(astring, count);
		}
		return dexpathList;
		 
		  }

    private static void getrootpkg(String apkpathString) throws IOException {
    	long current = System.currentTimeMillis();
    	List<String> NonPrimarymodule=ApkProfile.getrootpkg(apkpathString);
		for (String astring : NonPrimarymodule) {
			if (JudgeObfuscatedState(astring)==0) {
				System.out.println(astring);
				writeTxtFile(astring,"C://Users//ZJY//Desktop//apkrootname.txt");		
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - current));
	}


	private static void fail(String message) {
        System.err.println(message);
        System.exit(0);
    }
	private static void Extractapk(String apkpathString) throws IOException {  	
    	List<String> NonPrimarymodule=ApkProfile.getrootpkg(apkpathString);
		for (String astring : NonPrimarymodule) {
			if (JudgeObfuscatedState(astring)==0&LibRootpackageInfoService.findonebylibRootpackagename(astring)==null) {
				System.out.println(astring);
				String address = "http://search.maven.org/solrsearch/select?q="+astring;
				String a = searchUrlAs(address); 
				String URLList=dealwithoneresponse(a);//获取每个jar包的下载地址
				if (URLList!=null) {
					System.out.println(URLList);	
				}
			}
		}
	}
	
	
	public static List<String> traverseFolder1(String path) {
		List<String> dexfilepath=new ArrayList<String>();
	    int fileNum = 0, folderNum = 0;
	    File file = new File(path);
	    if (file.exists()) {
	        LinkedList<File> list = new LinkedList<File>();
	        File[] files = file.listFiles();
	        for (File file2 : files) {
	            if (file2.isDirectory()) {
	                System.out.println("文件夹:" + file2.getAbsolutePath());
	                list.add(file2);
	                folderNum++;
	            } else {
	                //System.out.println("文件:" + file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
	                dexfilepath.add( file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
	                fileNum++;
	            }
	        }
	        File temp_file;
	        while (!list.isEmpty()) {
	            temp_file = list.removeFirst();
	            files = temp_file.listFiles();
	            for (File file2 : files) {
	                if (file2.isDirectory()) {
	                    System.out.println("文件夹:" + file2.getAbsolutePath());
	                    list.add(file2);
	                    folderNum++;
	                } else {
	                    //System.out.println("文件:" + file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
	                    dexfilepath.add( file2.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
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
			System.out.println("latestVersion:"+vString);
			String ecString=restring.substring(restring.indexOf("p:")+2,restring.indexOf(",timestamp:"));
			System.out.println("format:"+ecString);
			System.out.println("详细信息地址：");
			String detailaddress = "https://search.maven.org/artifact/"+gString+"/"+aString+"/"+vString+"/"+ecString;
			System.out.println(detailaddress);
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
	public static String dealwithoneresponse(String input) throws IOException{
		List<String> resultList=new ArrayList<>();
		List<String> URLList=new ArrayList<>();
		String oneresult=null;
		input=input.substring(input.indexOf("["));
		int count=1;
		while (input.contains("latestVersion")) {
			//System.out.println("第"+count+"个结果");
			String aString=input.substring(input.indexOf("{"), input.indexOf("}")+1);
			input=input.substring(input.indexOf("}")+2);
			//System.out.println(input);
			//System.out.println("分隔");
			aString=aString.replace("\"", "");
			//System.out.println(aString);
			resultList.add(aString);
			count++;
		}
		for (String restring : resultList) {
			String gString=restring.substring(restring.indexOf("g:")+2,restring.indexOf(",a:"));
			//System.out.println("groupid:"+gString);
			String aString=restring.substring(restring.indexOf("a:")+2,restring.indexOf(",latestVersion"));
			//System.out.println("artefactid:"+aString);
			String vString=restring.substring(restring.indexOf("latestVersion:")+14,restring.indexOf(",repositoryId"));
			//System.out.println("latestVersion:"+vString);
			String ecString=restring.substring(restring.indexOf("p:")+2,restring.indexOf(",timestamp:"));
			//System.out.println("format:"+ecString);
			//System.out.println("详细信息地址：");
			String detailaddress = "https://search.maven.org/artifact/"+gString+"/"+aString+"/"+vString+"/"+ecString;
			if (ecString.equals("jar")||ecString.equals("aar")) { 
				aString="*"+aString+"*";
				gString="*"+gString+"*";
				//writeTxtFile("{","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"name\""+":"+aString+",","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"category\""+":"+"\"Android\""+",","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"comment\""+":"+"\"NotFound!\""+",","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"groupid\""+":"+gString+",","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"artefactid\""+":"+aString+",","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("       "+"\"repo\""+":"+"\"mvn-central\"","C://Users//ZJY//Desktop//test.txt");
				writeTxtFile("    "+"},{","C://Users//ZJY//Desktop//test.txt");
				System.out.println("groupid:"+gString);
				System.out.println("artefactid:"+aString);
				return detailaddress;
			}
			//System.out.println(detailaddress);
			if (ecString.equals("jar")||ecString.equals("aar")) {
				gString=gString.replace(".", "/");
				//System.out.println(gString);
				String reString2="https://search.maven.org/remotecontent?filepath="+gString+"/"+aString+"/"+vString+"/"+aString+"-"+vString+"."+ecString;
				//System.out.println(reString2);
				URLList.add(reString2);
			}
		}
		return null;
	}
	
	 public static int JudgeObfuscatedState(String pkgname){
	    	String endString=null;
	    	String startString=null;
	    	if (pkgname.contains(".")) {
	        	endString=pkgname.substring(pkgname.lastIndexOf(".")+1);
	        	startString=pkgname.substring(0, pkgname.indexOf("."));
	        	//System.out.println(startString);	
	        	//System.out.println(endString);	
			}
	    	if (startString!=null) {
		    	if(startString.equals("a")||startString.equals("b")||startString.equals("c")||startString.equals("d")||startString.equals("e")||startString.equals("f")
			||startString.equals("g")||startString.equals("h")||startString.equals("i")||startString.equals("j")||startString.equals("k")||startString.equals("l")
			||startString.equals("m")||startString.equals("n")||startString.equals("o")||startString.equals("p")||startString.equals("q")||startString.equals("r")
			||startString.equals("s")||startString.equals("t")||startString.equals("u")||startString.equals("v")||startString.equals("w")||startString.equals("x")
			||startString.equals("y")||startString.equals("z"))
		return 4;
	}
	    	if(pkgname.contains(".a.")||pkgname.contains(".b.")||pkgname.contains(".c.")||pkgname.contains(".d.")||pkgname.contains(".e.")||pkgname.contains(".f.")
	    			||pkgname.contains(".f.")||pkgname.contains(".g.")||pkgname.contains(".h.")||pkgname.contains(".i.")||pkgname.contains(".j.")||pkgname.contains(".k.")||pkgname.contains(".l.")
	    			||pkgname.contains(".m.")||pkgname.contains(".n.")||pkgname.contains(".o.")||pkgname.contains(".p.")||pkgname.contains(".q.")||pkgname.contains(".r.")||pkgname.contains(".s.")||pkgname.contains(".t.")
	    			||pkgname.contains(".u.")||pkgname.contains(".v.")||pkgname.contains(".w.")||pkgname.contains(".x.")||pkgname.contains(".y.")||pkgname.contains(".z."))
	    		return 1;
	    	if (endString!=null) {
				    	if(endString.equals("a")||endString.equals("b")||endString.equals("c")||endString.equals("d")||endString.equals("e")||endString.equals("f")
	    			||endString.equals("g")||endString.equals("h")||endString.equals("i")||endString.equals("j")||endString.equals("k")||endString.equals("l")
	    			||endString.equals("m")||endString.equals("n")||endString.equals("o")||endString.equals("p")||endString.equals("q")||endString.equals("r")
	    			||endString.equals("s")||endString.equals("t")||endString.equals("u")||endString.equals("v")||endString.equals("w")||endString.equals("x")
	    			||endString.equals("y")||endString.equals("z"))
	    		return 2;
			}
	    	if(pkgname.equals("a")||pkgname.equals("b")||pkgname.equals("c")||pkgname.equals("d")||pkgname.equals("e")||pkgname.equals("f")
	    			||pkgname.equals("g")||pkgname.equals("h")||pkgname.equals("i")||pkgname.equals("j")||pkgname.equals("k")||pkgname.equals("l")
	    			||pkgname.equals("m")||pkgname.equals("n")||pkgname.equals("o")||pkgname.equals("p")||pkgname.equals("q")||pkgname.equals("r")
	    			||pkgname.equals("s")||pkgname.equals("t")||pkgname.equals("u")||pkgname.equals("v")||pkgname.equals("w")||pkgname.equals("x")
	    			||pkgname.equals("y")||pkgname.equals("z"))
	    		return 3;

	    		    	
	    	return 0;
	    }
}
