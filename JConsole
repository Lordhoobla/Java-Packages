import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class JConsole extends TextArea{
	//I wanted to get rid of the warning
	private static final long serialVersionUID = -3639626612651463342L;
	private String start,command="";
	private int carLength;
	public JConsole(){
		super(50,10);
    //I want the user to be able to run other things on the program while this is doing its thing
		Thread thread=new Thread(){
			public void run(){
				initialize();
			}
		};
		thread.start();
	}
	private void initialize(){
		start="<JConsole :: "+System.getProperty("user.name")+"> ";
		carLength=start.length();
		super.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){e.consume();}
			public void mouseMoved(MouseEvent e){}});
		super.setBackground(Color.BLACK);
		super.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){
				if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&carLength<=start.length()){
					append(" ");
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER){
					if(command.equals("clear")){clear();
					}else if(command.startsWith("echo")){append(command.substring(5)+"\n"+start);}
					else if(command.equals("cmd")){
						append(start);
					}
					else{
						sendCommand(command);
						append(start);
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
	private void clear(){super.setText(start);super.setCaretPosition(carLength);}
	private void sendCommand(String comm){
		try{
			Process process = Runtime.getRuntime().exec(comm);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		        append(line+"\n");
		    }
		}catch(IOException ioe){ioe.printStackTrace();append("cannot run (improper syntax or permission)\n");}
	}
}
