package njust.lib.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import edu.njust.bean.LibInfo;
import edu.njust.bean.LibPackagestructure;
import edu.njust.bean.LibRootpackageInfo;
import edu.njust.bean.LibRootpackageInfo;
import njust.lib.dao.LibRootpackageInfoDAO;

public class LibRootpackageInfoService {
	private static LibRootpackageInfoDAO  LibRootpackageInfoDAO = new  LibRootpackageInfoDAO();
	
	public LibRootpackageInfo addLibRootpackage(String LibRootpackagename,String Libname){
	     LibRootpackageInfo libRootpackage = new LibRootpackageInfo();
	     libRootpackage.setLibRootpackagename(LibRootpackagename);
	     LibInfoService libInfoService=new LibInfoService();
	     LibInfo libInfo=libInfoService.getOneBycontent1(Libname);
	     libRootpackage.setLibId(libInfo.getId());
	     libRootpackage.setLibName(libInfo.getLibName());
		 LibRootpackageInfoDAO.add(libRootpackage);
	    return libRootpackage;				
}
	public static void updatepackagestructureHash(String libname,String packagestructureHash){
		String hql = " from  LibRootpackageInfo where libName='"+libname+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		System.out.println(Lib.getLibName());
		Lib.setPackagestructureHash(packagestructureHash);
		LibRootpackageInfoDAO.update(Lib);
		System.out.println("������");
	}
	
	public static void updatelibname(Integer id,String libname){
		String hql = " from  LibRootpackageInfo where id='"+id+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		System.out.println(Lib.getLibName());
		Lib.setLibName(libname);
		LibRootpackageInfoDAO.update(Lib);
		System.out.println("�������");
	}
	
	public static void updatepackage(String libname,String libRootpackagename,int subpckNum,int directorynum,String pckStructure,String packagestructureHash){
		String hql = " from  LibRootpackageInfo where libName='"+libname+"'and libRootpackagename='"+libRootpackagename+"'";;
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		Lib.setDirectorynum(directorynum);
		Lib.setPckStructure(pckStructure);
		Lib.setSubpckNum(subpckNum);
		Lib.setPackagestructureHash(packagestructureHash);
		LibRootpackageInfoDAO.update(Lib);
		System.out.println("������");
	}
	
	public static void addonepackage(String LibType,String libname,String libRootpackagename,int subpckNum,int directorynum,String pckStructure,String packagestructureHash){
		String hql = " from  LibRootpackageInfo where libName='"+libname+"'and libRootpackagename='"+libRootpackagename+"'and packagestructureHash='"+packagestructureHash+"'";
		LibRootpackageInfo  Lib1 =  LibRootpackageInfoDAO.findOneByHql(hql);
		if (Lib1==null) {
		int libId=LibInfoService.findOneBylibname(libname);
		if (libId==0) {
			System.out.println("libinfo���ݿ���û�����lib�����ڲ��룡");
			libId=LibInfoService.addLib(libname);
		}
		LibRootpackageInfo  Lib= new LibRootpackageInfo(); 
		Lib.setDirectorynum(directorynum);
		Lib.setPckStructure(pckStructure);
		Lib.setSubpckNum(subpckNum); 
		Lib.setPackagestructureHash(packagestructureHash);
		Lib.setLibRootpackagename(libRootpackagename); 
		Lib.setLibId(libId); 
		Lib.setLibName(libname);
		Lib.setLibType(LibType);
		LibRootpackageInfoDAO.add(Lib);
		System.out.println("������");
		}else {
			System.out.println("�Ѿ����ˣ�����Ҫ��ӣ�");
		}
		
	}
	
	public static void filllibtype(){
		for (int id = 15; id < 6221; id++) {
		String hql = " from  LibRootpackageInfo where id='"+id+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		String libType= Pattern.compile("[\\d]").matcher(Lib.getLibName()).replaceAll("");;
		Lib.setLibType(libType);
		LibRootpackageInfoDAO.update(Lib);
		}
		System.out.println("������");
}
	public String  findonebylibname(String libName){
		String hql = " from  LibRootpackageInfo where libName='"+libName+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
	    return Lib.getLibRootpackagename();				
}
	
	public static String  findonebylibRootpackagename(String libRootpackagename){
		String hql = " from  LibRootpackageInfo where libRootpackagename='"+libRootpackagename+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		if (Lib!=null) {
			  return Lib.getLibRootpackagename();
		}
		return null;
}
	
	public static String  findonebylibType(String libType){
		String hql = " from  LibRootpackageInfo where libType='"+libType+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
	    return Lib.getLibRootpackagename();				
}
	public static List<LibRootpackageInfo>  findallbyRootandHash(String Root,String Hash){
		String hql = " from  LibRootpackageInfo where libRootpackagename='"+Root+"'and packagestructureHash='"+Hash+"'";
		List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql);
		if (Lib.size()!=0) {
			System.out.println("�����ݿ����ҵ������Ͱ�Hashֵƥ���lib:");
			for (LibRootpackageInfo libPackagestructure : Lib) {
				String libType=libPackagestructure.getLibName();
				System.out.println(libType);
			}
		}
	    return Lib;				 
}
	public static List<LibRootpackageInfo>  findallbyunobfusDirandHash(String root, String unobfusDir,int rootDirnum, String Hash, List<String> conversedList){
		List<LibRootpackageInfo>  libRootpackageInfos=new ArrayList<>();
		if (root.equals(unobfusDir)) {
			String hql2 = " from  LibRootpackageInfo where libRootpackagename ='"+root+"'and packagestructureHash='"+Hash+"'";  
			List<LibRootpackageInfo>  Lib2 =  LibRootpackageInfoDAO.findByHql(hql2);
			String hql4 = " from  LibRootpackageInfo where libRootpackagename ='"+root+"'";  
			List<LibRootpackageInfo>  Lib4 =  LibRootpackageInfoDAO.findByHql(hql4);
			if (Lib2.size()>0) { 
				for (LibRootpackageInfo libRootpackageInfo : Lib2) {
						System.out.println("����δ�����������ݿ����ҵ���ȷ������Hashֵƥ���lib:"+libRootpackageInfo.getLibName());
						libRootpackageInfos.add(libRootpackageInfo);							  	
								}
				 return libRootpackageInfos;
		}if (Lib4.size()>0&Lib2.size()==0) {
			for (LibRootpackageInfo libRootpackageInfo : Lib4){
				String PackagestructureList =libRootpackageInfo.getPckStructure();
			 	int matchnum=0;
					/**
				 * ���ַ������List<String>�����ṹƥ���
				 */if (PackagestructureList!=null) {
					    String[] strArr =PackagestructureList.split(",");
					    //System.out.println(strArr.length); 
				        for (int i = 0; i < strArr.length; ++i){
				        	if (conversedList.contains(strArr[i])) {
								matchnum++;
							}
				        }
				        if (conversedList.size()!=0&&matchnum/conversedList.size()>0.8) {
				        	System.out.println("����δ�����������ݿ����ҵ���ȷ�����Ͱ��ṹƥ���lib:"+libRootpackageInfo.getLibName());
							libRootpackageInfos.add(libRootpackageInfo);	
						}else {
							System.out.println("����δ�����������ݿ����ҵ���ȷ���������ṹ��ƥ���lib:"+libRootpackageInfo.getLibName()+"---"+libRootpackageInfo.getPackagestructureHash());
							libRootpackageInfos.add(libRootpackageInfo);
						}	 
				 }				  	
			}
			 return libRootpackageInfos;	
		}
			else {
			String hql3 = " from  LibRootpackageInfo libRootpackageInfo where libRootpackageInfo.libRootpackagename like '%"+unobfusDir+"%'and packagestructureHash='"+Hash+"'";   
			List<LibRootpackageInfo>  Lib3 =  LibRootpackageInfoDAO.findByHql(hql3);
			String hql5 = " from  LibRootpackageInfo libRootpackageInfo where libRootpackageInfo.libRootpackagename like '%"+unobfusDir+"%'";  
			List<LibRootpackageInfo>  Lib5 =  LibRootpackageInfoDAO.findByHql(hql5);
			if (Lib3.size()>0) { 
				for (LibRootpackageInfo libRootpackageInfo : Lib3) {
						System.out.println("����δ�����������ݿ����ҵ�ģ��������Hashƥ���lib:"+libRootpackageInfo.getLibName());
						libRootpackageInfos.add(libRootpackageInfo);
						
				}
				return libRootpackageInfos;
		}if (Lib5.size()>0&Lib3.size()==0) {
			for (LibRootpackageInfo libRootpackageInfo : Lib5){
				System.out.println("����δ�����������ݿ����ҵ�ģ��������lib:"+libRootpackageInfo.getLibName()+"---"+libRootpackageInfo.getPackagestructureHash());
				libRootpackageInfos.add(libRootpackageInfo);
				  
			}
			 return libRootpackageInfos;	
		}
		}
		}else {
				String hql = " from  LibRootpackageInfo where packagestructureHash='"+Hash+"'";
		List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql);
		if (Lib.size()>0) { 
				for (LibRootpackageInfo libRootpackageInfo : Lib) {
			if (libRootpackageInfo.getLibRootpackagename().contains(unobfusDir)&libRootpackageInfo.getDirectorynum()==rootDirnum) {
			        	System.out.println("�����������������ݿ����ҵ������ṹ�Ͱ�Hashֵƥ���lib:"+libRootpackageInfo.getLibName());
						libRootpackageInfos.add(libRootpackageInfo);						
			}
		}	
		}else {
			String hql1 = " from  LibRootpackageInfo libRootpackageInfo where libRootpackageInfo.libRootpackagename like '%"+unobfusDir+"%'";  
			//String hql1 = " from  LibRootpackageInfo where directorynum='"+rootDirnum+"'";
			List<LibRootpackageInfo>  Lib1 =  LibRootpackageInfoDAO.findByHql(hql1);	
			for (LibRootpackageInfo libRootpackageInfo : Lib1) {
		if (libRootpackageInfo.getLibRootpackagename().contains(unobfusDir)) {
			String PackagestructureList =libRootpackageInfo.getPckStructure();
		 	int matchnum=0;
				/**
			 * ���ַ������List<String>�����ṹƥ���
			 */
		 	if (PackagestructureList!=null) {
			    String[] strArr =PackagestructureList.split(",");
			    //System.out.println(strArr.length); 
		        for (int i = 0; i < strArr.length; ++i){
		        	if (conversedList.contains(strArr[i])) {
						matchnum++;
					}
		        }
		        if (conversedList.size()!=0&&matchnum/conversedList.size()>0.8) {
		        	System.out.println("�����������������ݿ����ҵ������ṹ�Ͱ��ṹƥ���lib:"+libRootpackageInfo.getLibName());
					libRootpackageInfos.add(libRootpackageInfo);	
				}	
			}

		}
	}
		}	
		}
	    return libRootpackageInfos;				 
}
	
	
	  public static int Directorycount(String Directory) {
	    	if (Directory.contains("\\")) {
	    	  	Directory=Directory.substring(Directory.lastIndexOf("\\"));
			}
	    	int num = 0;
	        // ѭ������ÿ���ַ����ж��Ƿ����ַ� a ������ǣ��ۼӴ���
	       for (int i=0;i<Directory.length();i++)
	       {
	           // ��ȡÿ���ַ����ж��Ƿ����ַ�a
	           if (Directory.charAt(i)=='.') {
	               // �ۼ�ͳ�ƴ���
	               num++; 
	           }
	       }
	      // System.out.println(num+"��Ŀ¼");
			return num;
			
		}
	
	public static List<LibRootpackageInfo>  findallbyRoot(String unobfusDir, int rootDirnum){
		List<LibRootpackageInfo>  libRootpackageInfos=new ArrayList<>();
		String hql = " from  LibRootpackageInfo where id>0";
		List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql);
		for (LibRootpackageInfo libRootpackageInfo : Lib) {
			if (libRootpackageInfo.getLibRootpackagename().contains(unobfusDir)) {
				libRootpackageInfos.add(libRootpackageInfo);
			}
		}
	    return Lib;				 
}
	public static void main(String[] args){
		String libName="App_Annie_v1.39.0_(8680)_apkpure.com.apk.rx.dex";
		String hql = " from  LibRootpackageInfo where libName='"+libName+"'";
		List<LibRootpackageInfo>  Lib =  LibRootpackageInfoDAO.findByHql(hql);
		for (LibRootpackageInfo libRootpackageInfo : Lib) {
			System.out.println(libRootpackageInfo.getId());
		}
		//String hql = " from  LibRootpackageInfo where libName=firebase-analytics-impl-10.0.0.dex";
		//String hql1 = " from  LibRootpackageInfo where directorynum='"+rootDirnum+"'";
		//LibRootpackageInfo  Lib1 =  LibRootpackageInfoDAO.findOneByHql(hql);	

	//if (libRootpackageInfo.getLibRootpackagename().contains("com.crashlytics.android")) {
	//	System.out.println(Lib1.getId());
	//}
		//filllibtype();
		/**LibRootpackageInfoService libRootpackageInfoService=new LibRootpackageInfoService();
		
	     LibInfoService libInfoService=new LibInfoService();
	     String Libname="967af7be58c4efde245efea26e544d4dabb351f09f396f41b40c78d32c2723a0.dex";
		LibInfo libInfo=libInfoService.getOneBycontent1(Libname);
		System.out.println(libInfo.getId());
		libRootpackageInfoService.addLibRootpackage("uk.co.senab.photoview.log","967a51f09f396f41b40c78d32c2723a0.dex");
	    System.out.println("����LibRootpackage���");**/
	}
	public static LibRootpackageInfo getOneByrootpackagename(String rootpackagename,String libname) {
		String hql = " from  LibRootpackageInfo where libRootpackagename='"+rootpackagename+"'and libName='"+libname+"'";
		LibRootpackageInfo  Lib =  LibRootpackageInfoDAO.findOneByHql(hql);
		return Lib;
	}
}
