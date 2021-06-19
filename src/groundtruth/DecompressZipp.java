package groundtruth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.fudan.libpecker.model.ApkProfile;
import cn.fudan.libpecker.model.LibProfile;

public class DecompressZipp {
	List<String> zipPathList;
    public DecompressZipp(List<String> zipPathList) {
        this.zipPathList =zipPathList;
    }
    public List<String> startdecompressZip(){
    	List<String> jarPathList=new ArrayList<>();
    	for (String zipPath : zipPathList) {
    		String jarNameString=zipPath.substring(zipPath.lastIndexOf("\\")+1,zipPath.indexOf(".zip"));
    		System.out.println(jarNameString);
    		String jarPath=decompressZip(zipPath,"D:\\测试下载jar\\"+jarNameString);
    		jarPathList.add(jarPath);
		}
		return jarPathList;
    }
	 /**
     * 解压文件
     * @param zipPath 要解压的目标文件
     * @param descDir 指定解压目录
     * @return 解压结果：成功，失败
     */
    @SuppressWarnings("rawtypes")
    public static String decompressZip(String zipPath, String descDir) {
    	String jarPath = null;
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                if (outPath.contains("classes.jar")) {
                	jarPath=outPath;
                    //判断路径是否存在,不存在则创建文件路径
                    File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                    if(new File(outPath).isDirectory()){
                        continue;
                    }
                    //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                    System.err.println("当前zip解压之后的路径为：" + outPath);
                    OutputStream out = new FileOutputStream(outPath);
                    byte[] buf1 = new byte[2048];
                    int len;
                    while((len=in.read(buf1))>0){
                        out.write(buf1,0,len);
                    }
                    in.close();
                    out.close();
				}
            }
            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarPath;
    }
    public static void main(String[] args) throws Exception {
    	DecompressZipp.decompressZip("D:\\airmapview-1.8.0.zip","D:\\测试下载jar\\airmapview-1.8.0");
    }
    }

