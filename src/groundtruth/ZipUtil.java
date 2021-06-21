package groundtruth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 * @param srcDir ѹ���ļ���·��
	 * @param out ѹ���ļ������
	 * @param KeepDirStructure �Ƿ���ԭ����Ŀ¼�ṹ,
	 * 			true:����Ŀ¼�ṹ;
	 *			false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
	 * @throws RuntimeException ѹ��ʧ�ܻ��׳�����ʱ�쳣
	 */
	public static void toZip(String[] srcDir, String outDir,
			boolean KeepDirStructure) throws RuntimeException, Exception {

		OutputStream out = new FileOutputStream(new File(outDir));

		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			List<File> sourceFileList = new ArrayList<File>();
			for (String dir : srcDir) {
				File sourceFile = new File(dir);
				sourceFileList.add(sourceFile);
			}
			compress(sourceFileList, zos, KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("ѹ����ɣ���ʱ��" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * �ݹ�ѹ������
	 * @param sourceFile Դ�ļ�
	 * @param zos zip�����
	 * @param name ѹ���������
	 * @param KeepDirStructure �Ƿ���ԭ����Ŀ¼�ṹ,
	 * 			true:����Ŀ¼�ṹ;
	 *			false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos,
			String name, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			zos.putNextEntry(new ZipEntry(name));
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				if (KeepDirStructure) {
					zos.putNextEntry(new ZipEntry(name + "/"));
					zos.closeEntry();
				}

			} else {
				for (File file : listFiles) {
					if (KeepDirStructure) {
						compress(file, zos, name + "/" + file.getName(),
								KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}

				}
			}
		}
	}

	private static void compress(List<File> sourceFileList,
			ZipOutputStream zos, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		for (File sourceFile : sourceFileList) {
			String name = sourceFile.getName();
			if (sourceFile.isFile()) {
				zos.putNextEntry(new ZipEntry(name));
				int len;
				FileInputStream in = new FileInputStream(sourceFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			} else {
				File[] listFiles = sourceFile.listFiles();
				if (listFiles == null || listFiles.length == 0) {
					if (KeepDirStructure) {
						zos.putNextEntry(new ZipEntry(name + "/"));
						zos.closeEntry();
					}

				} else {
					for (File file : listFiles) {
						if (KeepDirStructure) {
							compress(file, zos, name + "/" + file.getName(),
									KeepDirStructure);
						} else {
							compress(file, zos, file.getName(),
									KeepDirStructure);
						}

					}
				}
			}
		}
	}

	/**
	   *
	   * @Description (��ѹ)
	   * @param zipPath zip·��
	   * @param charset ����
	   * @param outPutPath ���·��
	   */
	public static void decompression(String zipPath, String outPutPath) {
	       long startTime=System.currentTimeMillis();  
	       try {  
	           ZipInputStream Zin=new ZipInputStream(new FileInputStream(zipPath), Charset.forName("gbk"));//����Դzip·��  
	           BufferedInputStream Bin=new BufferedInputStream(Zin);  
	           String Parent = outPutPath; //���·�����ļ���Ŀ¼��  
	           File Fout=null;  
	           ZipEntry entry;  
	           try {  
	               while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
	                   Fout=new File(Parent,entry.getName());  
	                   if(!Fout.exists()){  
	                       (new File(Fout.getParent())).mkdirs();  
	                   }  
	                   FileOutputStream out=new FileOutputStream(Fout);  
	                   BufferedOutputStream Bout=new BufferedOutputStream(out);  
	                   int b;  
	                   while((b=Bin.read())!=-1){  
	                       Bout.write(b);  
	                   }  
	                   Bout.close();  
	                   out.close();  
	                   System.out.println(Fout+"��ѹ�ɹ�");      
	               }  
	               Bin.close();  
	               Zin.close();  
	           } catch (IOException e) {  
	               // TODO Auto-generated catch block  
	               e.printStackTrace();  
	           }  
	       } catch (FileNotFoundException e) {  
	           // TODO Auto-generated catch block  
	           e.printStackTrace();  
	       }  
	       long endTime=System.currentTimeMillis();  
	       System.out.println("�ķ�ʱ�䣺 "+(endTime-startTime)+" ms");
	   }

	
	public static void main(String[] args) throws Exception {

		String[] srcDir = { "path\\Desktop\\java",
				"path\\Desktop\\java2",
				"path\\Desktop\\fortest.txt" };
		String outDir = "path\\Desktop\\aaa.zip";
		//ZipUtil.toZip(srcDir, outDir, true);
		decompression("D:\\airmapview-1.8.0.zip","D:\\airmapview-1.8.0");
	}
}
