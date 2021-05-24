package JFrameExtension;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextArea;
public class JConsole extends JTextArea{
	//I wanted to get rid of the warning
	private static final long serialVersionUID = -3639626612651463342L;
	private String start,command="";
	private int carLength;
	public JConsole(){
		super(50,10);
		start="<JConsole :: "+System.getProperty("user.name")+"> ";
		carLength=start.length();
		super.setBackground(Color.BLACK);
		super.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){
				if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&carLength<=start.length()){
					appendText(" ");
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER){
					if(command.equals("clear")){clear();
					}else{
						appendText(sendCommand(command)+start);
					}
					carLength=start.length();
					command="";
				}else if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE){
					carLength--;
					command=command.substring(0,command.length()-1);
				}else{
					command+=e.getKeyChar();
					carLength++;}
			}public void keyPressed(KeyEvent e){if(e.getKeyChar()==KeyEvent.CTRL_DOWN_MASK) {e.consume();}}
			public void keyReleased(KeyEvent e){}
		});
		super.setForeground(new Color(125,125,125));
		Thread thread=new Thread(){public void run(){init();}};
		thread.start();
	}
	private void init(){
		super.setText(start);
		super.setText("You are using the JConsole API\nNote that this is still under development\nthis will delete in 2 seconds");
		try{Thread.sleep(1000);}catch(InterruptedException ie){super.setText("");ie.printStackTrace();}
		super.setText(start);
		super.repaint();
		super.setCaretPosition(start.length());
	}
	private void appendText(String str){super.setText(super.getText()+str);}
	private void clear(){super.setText(start);}
	private String sendCommand(String comm){
		try{
			ProcessBuilder builder=new ProcessBuilder(comm);
			builder.redirectErrorStream(true);
			BufferedReader reader=new BufferedReader(new InputStreamReader(builder.start().getInputStream()));
			String line,oof="";
			while(reader.readLine()!=null&&(line=reader.readLine())!=null){
				oof+=line+"\n";}
			return oof;
		}catch(IOException ioe){ioe.printStackTrace();return "cannot run (improper syntax)\n";}
	}
}