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
	 * @param srcDir 压缩文件夹路径
	 * @param out 压缩文件输出流
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 			true:保留目录结构;
	 *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
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
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
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
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos zip输出流
	 * @param name 压缩后的名称
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 			true:保留目录结构;
	 *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
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
	   * @Description (解压)
	   * @param zipPath zip路径
	   * @param charset 编码
	   * @param outPutPath 输出路径
	   */
	public static void decompression(String zipPath, String outPutPath) {
	       long startTime=System.currentTimeMillis();  
	       try {  
	           ZipInputStream Zin=new ZipInputStream(new FileInputStream(zipPath), Charset.forName("gbk"));//输入源zip路径  
	           BufferedInputStream Bin=new BufferedInputStream(Zin);  
	           String Parent = outPutPath; //输出路径（文件夹目录）  
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
	                   System.out.println(Fout+"解压成功");      
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
	       System.out.println("耗费时间： "+(endTime-startTime)+" ms");
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
