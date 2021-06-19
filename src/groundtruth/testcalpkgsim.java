package groundtruth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xmlpull.v1.XmlPullParserException;

import cn.fudan.libpecker.main.getdexfilepath;

public class testcalpkgsim {
	
	
	public void run(String apkpath,String libpath){
		calpkgsim pecker = new calpkgsim();
		System.out.println(apkpath.substring(apkpath.lastIndexOf("\\")).substring(1));
		System.out.println(apkpath);
		//pecker.execute(apkpath,libpath);
		
	}
	
	
	 public static void main(String[] args) throws IOException, XmlPullParserException {
		 List<String> apkfilepath=new ArrayList<>();
		 getdexfilepath aa=new getdexfilepath();
		 apkfilepath.addAll(aa.traverseFolder1("G:\\libdetection实验标准采集\\APK库\\test1"));
		 
		 ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
			for(String s:apkfilepath){
				fixedThreadPool.execute(new AnalysisRunnable(s));
			}
		 //execute("G:\\libdetection实验标准采集\\APK库\\test\\0b9fa0dd7ef8b2fef57c84a83e95bb4c.apk","G:\\libdetection实验标准采集\\lib340");
	 } 
}
