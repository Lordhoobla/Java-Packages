import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyListen implements KeyListener{
	public void keyTyped(KeyEvent e){
		Main.in=e.getKeyChar();
		Main.input=false;
	}
	public void keyPressed(KeyEvent e){
	}
	public void keyReleased(KeyEvent e) {
	}
	public void run(){
		System.out.println("has started");
	}
	
}