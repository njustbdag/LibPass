package groundtruth;

public class AnalysisRunnable implements Runnable{
	private String apkPath;
	public AnalysisRunnable(String apkPath){
		this.apkPath = apkPath;
	}
	
	@Override
	public void run(){
		// TODO Auto-generated method stub
		testcalpkgsim po=new testcalpkgsim();
		po.run(apkPath, "G:\\libdetection实验标准采集\\lib340");
	}
}
