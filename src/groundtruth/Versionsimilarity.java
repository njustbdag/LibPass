package groundtruth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Versionsimilarity {
	  public static void main(String[] args) {
		  List<String> version=Findversion();
		  for(String v:version){
			  List<String> versionseries=Findversionseries(v); 
		  }
	  }
	  
	  public static List<String> Findversion(){
			List<String> version=new ArrayList<>();
			String pathString="G:\\libdetectiongroundtruth\\dex��\\���е�aarת������dex";
			List<String> allPathList=findfile(pathString);
			for (String path:allPathList) {
				String verString=path.substring(50, path.lastIndexOf("-"));
				if (!version.contains(verString)) {
					version.add(verString);		
				}
				//System.out.println(verString);
			}
			for(String v:version){
				System.out.println(v);
			}
			System.out.println(version.size());
		return version;
		  
	  }
	
	
	public static List<String> Findversionseries(String version) {
		List<String> versionseries=new ArrayList<>();
		//String version;
		String pathString="G:\\libdetectiongroundtruth\\dex��\\���е�aarת������dex";
		List<String> allPathList=findfile(pathString);
		for (String path:allPathList) {
			if (path.contains(version)) {
				System.out.println("����"+version);
				System.out.println(path);
				versionseries.add(path);
			}
		}
		System.out.println("versionseries.size()"+versionseries.size());
		return versionseries;
		
	}
	public static List<String> findfile(String path){//�ҵ�path��һ���ļ���Ŀ¼
		List<String> fileList=new ArrayList<>();
		System.out.println("=========ָ��Ŀ¼�µ������ļ�==========");
		File file = new File(path);
		File[] aa = file.listFiles();
		for (int i = 0; i < aa.length; i++) {
			if (aa[i].isFile()) {
				if(aa[i].toString().contains("dex")){
					//if(!aa[i].toString().contains("apktool.bat")){
	           // System.out.println(aa[i].toString());
				fileList.add(aa[i].toString().replaceAll("\\\\", "\\\\\\\\"));
					//}
	
				}

			}
		}
		return fileList;
		
	}
}
