package groundtruth;


import java.io.File;

public class ClearFile {

	static int iFile = 0;
	
	public static void main(String[] args) {
	// TODO Auto-generated method stub
// dir_str�����ļ��е�·�� 
		String dir_str = "D:\\��������jar";
       //String dir_str = "E:\\groundtruth\\����dex\\test";
       File dir = new File( dir_str );
       clear( dir );
       System.out.println( "����ɹ���" );
       System.out.println( "��������" + iFile + "�����ļ���" );
	}
	
	public static void clear( File dir )
   {
       File[] dirs = dir.listFiles();
       for( int i = 0; i < dirs.length; i++ )
       {
           if( dirs[i].isDirectory() )
           {
               clear( dirs[i] );
           }
       }
       System.out.println(dir);
       if( dir.isDirectory() && dir.delete() )
           iFile++;
       System.out.println( dir + "����ɹ�" );

   }

}
