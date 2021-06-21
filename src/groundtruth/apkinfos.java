package groundtruth;

import njust.lib.Service.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.fudan.libpecker.main.aar2dex;
import edu.njust.bean.LibInfo;
import njust.lib.dao.LibInfoDAO;
import edu.njust.bean.LibInfo;




public class apkinfos {
	
	private static String dexpath = "G:/libdetectionʵ���׼�ɼ�/dex2smali/";
	
	public static void main(String[] args){
		List<String> a=getdexname("G:\\libdetectionʵ���׼�ɼ�\\APK��");
		ApkInfoService apkinfoservice=new ApkInfoService();
		for(String a1:a){
			if(apkinfoservice.findOneBylibname(a1)==0){
				apkinfoservice.addapklib(a1);
				System.out.println("����"+a1+"���");
			}
		}
		//LibInfo  Lib=apkinfoservice.findOneBylibname("edu.testshared1");
		//System.out.println(Lib.getId());

	}
	
	
	
	public static List<String> getdexname(String apkpath){
		List<String> a=new ArrayList<>();
		List<String> b=new ArrayList<>();
		aar2dex aa=new aar2dex();
		//a.addAll(aa.traverseFolder1("E:\\GoogletplsCloud"));//jar����Ŀ¼
		a.addAll(aa.traverseFolder1(apkpath));//�ҵ���Ŀ¼�µ�����dex�ļ�·��
		for(String a1:a){
		b.add(a1.substring(a1.lastIndexOf("\\")).substring(1));
		//System.out.println(a1.substring(a1.lastIndexOf("\\")).substring(1));
	}
		for(String b1:b){
			System.out.println(b1);
		}
		return b;
		
	}
}
