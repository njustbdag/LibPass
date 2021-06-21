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
    		String jarPath=decompressZip(zipPath,"D:\\��������jar\\"+jarNameString);
    		jarPathList.add(jarPath);
		}
		return jarPathList;
    }
	 /**
     * ��ѹ�ļ�
     * @param zipPath Ҫ��ѹ��Ŀ���ļ�
     * @param descDir ָ����ѹĿ¼
     * @return ��ѹ������ɹ���ʧ��
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
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//��ֹ����Ŀ¼������
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //ָ����ѹ����ļ���+��ǰzip�ļ�������
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                if (outPath.contains("classes.jar")) {
                	jarPath=outPath;
                    //�ж�·���Ƿ����,�������򴴽��ļ�·��
                    File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    //�ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
                    if(new File(outPath).isDirectory()){
                        continue;
                    }
                    //�����ļ�·����Ϣ��������md5.zip���Ƶ�Ψһ�ԣ����ж��Ƿ��Ѿ���ѹ��
                    System.err.println("��ǰzip��ѹ֮���·��Ϊ��" + outPath);
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
            //����رգ�Ҫ��Ȼ���zip�ļ�һֱ��ռ���ţ�Ҫɾɾ����������Ҳ�����ԣ��ƶ�Ҳ���У������ˣ�ϵͳ�����ˡ�
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarPath;
    }
    public static void main(String[] args) throws Exception {
    	DecompressZipp.decompressZip("D:\\airmapview-1.8.0.zip","D:\\��������jar\\airmapview-1.8.0");
    }
    }

