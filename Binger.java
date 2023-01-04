import java.io.IOException;

public class Binger{
	public static void main(String[] args) throws InterruptedException{
		Runtime rt=Runtime.getRuntime();
		Process[] p=new Process[40];
		for(int i=0;i<40;i++){
			String str="";
			for(int j=0;j<5;j++){
				str+=(char)((int)(Math.random()*26+65));
			}
			//System.out.println(str);
			try {
				p[i]=rt.exec("rundll32 url.dll,FileProtocolHandler " + "https://www.bing.com/search?q="+str+"&FORM=INCOH2&PC=1VIV&PTAG=ICO-c9d0fc87");
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		Thread.sleep(10000);
		for(Process pi:p){
			pi.destroyForcibly();
		}
	}
}