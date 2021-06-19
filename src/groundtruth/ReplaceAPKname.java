package groundtruth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.hibernate.event.spi.MergeEvent;

public class ReplaceAPKname {
	private static HashMap<String, String> apkHashMap=new HashMap<>(32768);
	private static HashSet<String> reHashSet=new HashSet<>();
	private static HashSet<String> rzeHashSet=new HashSet<>();
	public static void main(String[] args){
		//replacestart("I:\\实验基准\\新基准\\myeclipse日志.txt");
		//mergent();
		
		//readtxt3("I:\\实验基准\\新基准\\基准补充实验结果分析\\基准集\\基准带ARP和MD5补充版 1000.txt");

	}
	
	
	private static void mergent() {
		// TODO Auto-generated method stub
		HashSet<String> reasHashSet=new HashSet<>();
		readtxt4("I:\\实验基准\\新基准\\基准补充实验结果分析\\LibSearcher检测结果集\\未混淆结果.txt");
		readtxt4("I:\\实验基准\\新基准\\基准补充实验结果分析\\LibSearcher检测结果集\\已混淆结果.txt");
		TreeSet<String> sdSet=new TreeSet<>();
		sdSet.addAll(reHashSet);
		for (String string : sdSet) {
			if (!reasHashSet.contains(string.substring(0, string.lastIndexOf(",")))) {
				//System.out.println(string);
				reasHashSet.add(string.substring(0, string.lastIndexOf(",")));
				System.out.println(string);
			}
		}
	}
	
	private static boolean JudgeObfuscated(String pkgname){
    	String endString=null;
    	if (pkgname.contains(".")) {
        	endString=pkgname.substring(pkgname.lastIndexOf(".")+1);
        	//System.out.println(endString);	
		}
    	if(pkgname.contains(".a.")||pkgname.contains(".b.")||pkgname.contains(".c.")||pkgname.contains(".d.")||pkgname.contains(".e.")||pkgname.contains(".f.")
    			||pkgname.contains(".f.")||pkgname.contains(".g.")||pkgname.contains(".h.")||pkgname.contains(".i.")||pkgname.contains(".j.")||pkgname.contains(".k.")||pkgname.contains(".l.")
    			||pkgname.contains(".m.")||pkgname.contains(".n.")||pkgname.contains(".o.")||pkgname.contains(".p.")||pkgname.contains(".q.")||pkgname.contains(".r.")||pkgname.contains(".s.")||pkgname.contains(".t.")
    			||pkgname.contains(".u.")||pkgname.contains(".v.")||pkgname.contains(".w.")||pkgname.contains(".x.")||pkgname.contains(".y.")||pkgname.contains(".z."))
    		return true;
    	if (endString!=null) {
			    	if(endString.equals("a")||endString.equals("b")||endString.equals("c")||endString.equals("d")||endString.equals("e")||endString.equals("f")
    			||endString.equals("g")||endString.equals("h")||endString.equals("i")||endString.equals("j")||endString.equals("k")||endString.equals("l")
    			||endString.equals("m")||endString.equals("n")||endString.equals("o")||endString.equals("p")||endString.equals("q")||endString.equals("r")
    			||endString.equals("s")||endString.equals("t")||endString.equals("u")||endString.equals("v")||endString.equals("w")||endString.equals("x")
    			||endString.equals("y")||endString.equals("z"))
    		return true;
		}

    	if(pkgname.equals("a")||pkgname.equals("b")||pkgname.equals("c")||pkgname.equals("d")||pkgname.equals("e")||pkgname.equals("f")
    			||pkgname.equals("g")||pkgname.equals("h")||pkgname.equals("i")||pkgname.equals("j")||pkgname.equals("k")||pkgname.equals("l")
    			||pkgname.equals("m")||pkgname.equals("n")||pkgname.equals("o")||pkgname.equals("p")||pkgname.equals("q")||pkgname.equals("r")
    			||pkgname.equals("s")||pkgname.equals("t")||pkgname.equals("u")||pkgname.equals("v")||pkgname.equals("w")||pkgname.equals("x")
    			||pkgname.equals("y")||pkgname.equals("z"))
    		return true;
    	if(pkgname.contains(".")){
    		String lastnameString=pkgname.substring(pkgname.lastIndexOf(".")+1);
    		//System.out.println(lastnameString);
    	if(lastnameString.equals("a")||lastnameString.equals("b")||lastnameString.equals("c")||lastnameString.equals("d")||lastnameString.equals("e")||lastnameString.equals("f")){
    		return true;
    	}
    		
    	}
    	return false;
    }

	private static void readtxt4(String filepath) {
		 File file = new File(filepath);
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	reHashSet.add(s);
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
	}
	
	private static void readtxt3(String filepath) {
		 File file = new File(filepath);
		 int cnt=0,vnt=0;
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	String string;
	            	String[] aStrings=s.split(",");
	            	if (aStrings.length==4) {
	            		string=(aStrings[2]);	            		
					}else {
						string=(aStrings[1]);
					}
	            	if (JudgeObfuscated(string)) {
        				cnt++;
        			}else {
        				System.out.println(s.substring(0, s.lastIndexOf(",")));
        				vnt++;
        			}
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
    		System.out.println(cnt);
    		System.out.println(vnt);
	}
	
	
	private static void replacestart(String filepath) {
		readtxt("I:\\实验基准\\新基准\\基准中的apk名单.txt");
		 File file = new File(filepath);
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	//System.out.println(s.substring(0, s.indexOf(",")));
	            	//System.out.println(apkHashMap.get(s.substring(0, s.indexOf(","))));
	            	if (apkHashMap.get(s.substring(0, s.indexOf(",")))!=null) {
						System.out.println(apkHashMap.get(s.substring(0, s.indexOf(",")))+s.substring(s.indexOf(",")));
					} else {
						System.out.println(s);
					}       
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
	}

	private static void readtxt(String filepath) {
		 File file = new File(filepath);
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	if (s.contains(",")) {
	            		apkHashMap.put(s.substring(0, s.indexOf(",")), s);  
	            		//System.out.println(s.substring(0, s.indexOf(",")));
					}
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }	
	}

}
