package cn.fudan.libpecker.main;
import cn.fudan.common.util.PackageNameUtil;
import cn.fudan.libpecker.core.LibApkMapper;
import cn.fudan.libpecker.core.PackageMapEnumerator;
import cn.fudan.libpecker.core.PackagePairCandidate;
import cn.fudan.libpecker.core.ParseApkTest;
import cn.fudan.libpecker.core.ProfileComparator;
import cn.fudan.libpecker.model.*;
import cn.njust.analysis.tree.PackageNode;
import cn.njust.common.Apk;
import cn.njust.common.Lib;
import cn.njust.common.LibPeckerConfig;
import cn.njust.common.Sdk;

import java.io.IOException;
import java.util.*;

import njust.lib.Service.ApkLibInfosService;
import njust.lib.Service.LibdetectionResultService;
import njust.lib.Service.LibpeckerResultService;

import org.xmlpull.v1.XmlPullParserException;

import edu.njust.bean.ApkLibInfos;
import edu.njust.bean.LibdetectionResult;
import edu.njust.bean.LibpeckerResult;


public class resultcompare {
	
	public static List<String> findLibpeckerResultbyapkname(String apkname){
		LibpeckerResultService apkLibService=new LibpeckerResultService();
		List<String> q=new ArrayList<>();
		List<LibpeckerResult> a=apkLibService.getallByapkname(apkname);
		System.out.println("Libpecker�ҵ���"+a.size()+"��");
		for(LibpeckerResult aa:a){
			System.out.println(aa.getLibname());
			q.add(aa.getLibname());
		}
		return q;
	}
	
	public static List<String> findLibdetectionResultbyapkname(String apkname){
		LibdetectionResultService apkLibService1=new LibdetectionResultService();
		List<String> q=new ArrayList<>();
		List<LibdetectionResult> b=apkLibService1.getallByapkname(apkname);
		System.out.println("Libdetection�ҵ���"+b.size()+"��");
		for(LibdetectionResult aa:b){
			System.out.println(aa.getLibname());
			q.add(aa.getLibname());
		}
		return q;
		
	}
	
	public static List<String> findApkLibInfosbyapkname(String apkname){
		ApkLibInfosService apkLibService1=new ApkLibInfosService();
		List<String> q=new ArrayList<>();
		List<ApkLibInfos> b=apkLibService1.getallByapkname(apkname);
		System.out.println("ApkLibInfos�ҵ���"+b.size()+"��");
		for(ApkLibInfos aa:b){
			System.out.println(aa.getLibname());
			q.add(aa.getLibname());
		}
		return q;
		
	}
	
	public static void main(String[] args){
		String apkname="03449.apk";
		LibdetectionResultService apkLibService1=new LibdetectionResultService();
		List<String> a=findLibpeckerResultbyapkname(apkname);
		System.out.println("         ");
		List<String> b=findLibdetectionResultbyapkname(apkname);
		System.out.println("         ");
		List<String> c=findApkLibInfosbyapkname(apkname);
		System.out.println("         ");
		System.out.println("Libpecker��Libdetection��groundtruth���Ƚ�");
		for(String q:c){
			if(!a.contains(q))
				System.out.println("Libpeckerû���ҵ����lib��"+q);
			if(!b.contains(q))
				System.out.println("Libdetectionû���ҵ����lib��"+q);
		}
		System.out.println("-----------�ȽϽ���-------------");
		System.out.println("Libpecker��Libdetection���Ƚ�");
		for(String w:b){
			if(!a.contains(w)){
				System.out.println("Libpeckerû���ҵ����lib��"+w);
				System.out.println(w+"������ֵΪ"+apkLibService1.getOneBycontent2(apkname,w));
			}
				
				
		}
		System.out.println("-----------�ȽϽ���-------------");
		
	}
	
	
	
}
