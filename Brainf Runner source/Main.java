import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
public class Main extends Thread{
	protected static JFrame gui=new JFrame("Brainf Runner");
	public static void main(String[] args){
		createGUI();
	}
	protected static JButton run = new JButton(""),convert=new JButton("");;
	private static boolean running=false;
	private static void setOp(){running=!running;}
	private static int cursor,i=1,z;
	protected static TextArea output=new TextArea(),code = new TextArea();
	protected static boolean input=true;
	protected static char in='a';
	private char getInput(Thread ret){
		run.setSelected(false);
		run.transferFocus();
		output.requestFocus();
		output.setCaretPosition(output.getText().length()-1);
		KeyListen k=new KeyListen();
		output.addKeyListener(k);
		synchronized(ret){
			try{
				while(input){ret.wait(1000);}
			}catch(Exception e){e.printStackTrace();}
			output.removeKeyListener(k);
			input=true;}
		return in;
	}
	protected static char[] loop(char[] list,String choice){
	    int index=1;
	    i=1;
	    String choice1=choice;
	    while(index!=0){
	      switch(choice1.charAt(i)){
	        case '[':index++;break;
	        case ']':index--;break;
	      }
	      i++;
	    }
	    choice1=choice1.substring(1,i-1);
	    String choice2=choice1;
	    while(list[cursor]!=0){
	      choice1=choice2;
	      while(choice1.length()!=0){
	        if(choice1.indexOf(']')<choice1.indexOf('[')||choice1.indexOf('[')==-1&&choice1.indexOf(']')!=-1){
	          choice1=choice1.substring(choice1.indexOf(']'));
	        }
	        switch(choice1.charAt(0)){
	          case '>':cursor++;break;
	          case '<':cursor--;break;
	          case '+':list[cursor]++;break;
	          case '-':list[cursor]--;break;
	          case '.':output.setText(output.getText()+list[cursor]);break;
	          case ',':
	        	  list[cursor]=new Main().getInput(new Main());
	          break;
	          case '[':
	            list=loop(list,choice1);
	            choice1=choice1.substring(1);
	            z=0;i=0;
	            while(z!=-1){
	              switch(choice1.charAt(i)){
	                case '[':z++;break;
	                case ']':z--;break;
	              }
	              i++;
	            }
	            choice1=choice1.substring(i-1);
	            break;
	        }
	        choice1=choice1.substring(1);
	      }
	      if((int)list[cursor]==0){break;}
	    }
	    return list;
	  }
	public void run(){
		System.out.println("thread started");
		run.setSelected(false);
		run.transferFocus();
		output.setText("");
		String choice=code.getText();
	    String choice1="";
	    int lsize=0,msize=0;
	    choice1=choice+"a";
	    for(int i=0;i<choice1.length();i++){
	      if(choice1.charAt(i)=='>'){
	        lsize++;
	      }
	      if(choice1.charAt(i)=='<'){
	        lsize--;
	        if(lsize<0){output.setText("cursor out of bounds(too far backwards)");break;}
	      }
	      if(lsize>msize){msize=lsize;}
	    }
	    if(lsize<0){return;}
	    msize++;
	    char[] list=new char[msize];
	    choice1=choice+"a";lsize=0;i=0;
	    while(choice1.length()!=1){
	      if(choice1.charAt(0)=='['){
	        lsize++;
	        if(choice1.indexOf(']')==-1){break;}
	      }
	      if(choice1.charAt(0)==']'){
	        lsize--;
	      }
	      choice1=choice1.substring(1);
	    }
	    if(lsize!=0){output.setText(output.getText()+"incorrect loop syntax");return;}
	    while(choice.length()>0){
	      if(choice1.indexOf(']')<choice1.indexOf('[')||choice1.indexOf('[')==-1&&choice1.indexOf(']')!=-1){
	          choice1=choice1.substring(choice1.indexOf(']'));
	      }
	      switch(choice.charAt(0)){
	        case '>':cursor++;break;
	        case '<':cursor--;break;
	        case '+':list[cursor]++;break;
	        case '-':list[cursor]--;break;
	        case '.':output.setText(output.getText()+list[cursor]);break;
	        case ',':
	        	list[cursor]=new Main().getInput(new Main());
	        break;
	        case '[':
	          list=loop(list,choice);
	          choice=choice.substring(1);
	          z=0;i=0;
	          while(z!=-1){
	            switch(choice.charAt(i)){
	              case '[':z++;break;
	              case ']':z--;break;
	            }
	            i++;
	          }
	          choice=choice.substring(i-1);
	          break;
	      }
	      choice=choice.substring(1);
	    }
	    String out="\n=>";
	    for(int i=0;i<list.length;i++){
	    	out+=(i==cursor?"["+(int)list[i]+"]":(int)list[i])+(i!=list.length-1?" ":"");
	    }
	    output.setText(output.getText()+out);
	    run.setIcon(new ImageIcon(Main.class.getResource("/resources/play.png")));
	}
	protected static void convert(){
		ArrayList<Integer> list=new ArrayList<Integer>();
		Scanner input=new Scanner(code.getText());
		while(input.hasNextLine()){
	        char[] temp=input.nextLine().toCharArray();
	        for(int i=0;i<=temp.length;i++){
	          if(i==temp.length){list.add((int)10);break;}
	          list.add((int)temp[i]);
	        }
	      }
		Integer[] clist=new Integer[(list.size()+1)];
	    for(int i=1;i<clist.length;i++){
	      clist[i]=list.get(i-1);
	    }
	    clist[0]=0;
	    String output="";
	    int x;
	    for(int i=1;i<clist.length;i++){
	        Main.output.setText("");
	        x=Math.abs(clist[i]-clist[i-1])/10;
	        if(clist[i]==1){output+=">>>,<<<";}
	        else if(x>=4&&x%4==0){
	            for(int y=0;y<x;y+=4){output+="+";}
	            output+="[>";
	            if(clist[i]-clist[i-1]<0){output+="++++++++[>-----<-]<-]>>";}
	            else{output+="++++++++[>+++++<-]<-]>>";}
	            if(clist[i]-clist[i-1]<0){
	              for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="-";}}
	            else{
	              for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="+";}}
	          }
	        else if(x>=3&&x%3==0){
	          for(int y=0;y<x;y+=3){output+="+";}
	          output+="[>";
	          if(clist[i]-clist[i-1]<0){output+="++++++[>-----<-]<-]>>";}
	          else{output+="++++++[>+++++<-]<-]>>";}
	          if(clist[i]-clist[i-1]<0){
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="-";}}
	          else{
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="+";}}
	        }
	        else if(x>=2&&x%2==0){
	          for(int y=0;y<x;y+=2){output+="+";}
	          output+="[>";
	          if(clist[i]-clist[i-1]<0){output+="++++[>-----<-]<-]>>";}
	          else{output+="++++[>+++++<-]<-]>>";}
	          if(clist[i]-clist[i-1]<0){
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="-";}}
	          else{
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="+";}}
	        }
	        else if(x>1){
	          for(int y=0;y<x;y++){output+="+";}
	          output+="[>";
	          if(clist[i]-clist[i-1]<0){output+="+++++[>--<-]<-]>>";
	          }else{output+="+++++[>++<-]<-]>>";}
	          if(clist[i]-clist[i-1]<0){
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="-";}}
	          else{
	            for(int z=0;z<Math.abs(clist[i]-clist[i-1])%10;z++){output+="+";}}
	        }else{
	          output+=">>";
	          if(clist[i]-clist[i-1]>0){
	            for(int y=0;y<Math.abs(clist[i]-clist[i-1]);y++){output+="+";}}
	          else{
	            for(int y=0;y<Math.abs(clist[i]-clist[i-1]);y++){output+="-";}}
	          }
	        output+=".<<\n";
	        Main.output.setText("\u001B[31m"+(clist.length-i)+" characters left");
	      }
	    Main.output.setText(output);
		convert.setEnabled(true);
		gui.repaint();
	}
	public static void createGUI(){
		gui.getContentPane().setBackground(new Color(192, 192, 192));
		gui.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/icon.png")));
		gui.setBounds(new java.awt.Rectangle(410,600));
		gui.setLocationRelativeTo(null);
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.getContentPane().setLayout(null);
		
		code.setBounds(10, 40, 378, 231);
		gui.getContentPane().add(code);
		code.setForeground(new Color(51,51,51));
		code.setFont(new Font("Vernanda",Font.PLAIN,12));
		
		output.setBounds(10,328,378,237);
		gui.getContentPane().add(output);
		output.setForeground(new Color(51,51,51));
		output.setFont(new Font("Vernanda",Font.PLAIN,12));
		
		run.setIcon(new ImageIcon(Main.class.getResource("/resources/play.png")));
		run.setBounds(10,281,37,37);
		gui.getContentPane().add(run);
		run.setBorder(new LineBorder(Color.BLACK,1,true));
		run.setBackground(gui.getContentPane().getBackground());
		run.setFocusPainted(false);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				cursor=z=0;i=1;
				gui.repaint();
				if(running){
					run.setIcon(new ImageIcon(Main.class.getResource("/resources/play.png")));
					setOp();
					gui.repaint();
					return;
				}else{
					run.setIcon(new ImageIcon(Main.class.getResource("/resources/stop.png")));
					setOp();
					run.repaint();
					gui.repaint();
				}
				Main thread=new Main();
				thread.start();
				setOp();
				gui.repaint();
			}
		});
		
		JButton reset = new JButton("");
		reset.setIcon(new ImageIcon(Main.class.getResource("/resources/rubber.png")));
		reset.setBounds(104,281,37,37);
		gui.getContentPane().add(reset);
		reset.setBorder(new LineBorder(Color.BLACK,1,true));
		reset.setBackground(gui.getContentPane().getBackground());
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				output.setText("");
			}
		});
		reset.setFocusPainted(false);
		
		JButton openFile = new JButton("");
		openFile.setIcon(new ImageIcon(Main.class.getResource("/resources/folder.png")));
		openFile.setBounds(57,281,37,37);
		gui.getContentPane().add(openFile);
		openFile.setBorder(new LineBorder(Color.BLACK,1,true));
		openFile.setFocusPainted(false);
		openFile.setBackground(gui.getContentPane().getBackground());
		openFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser=new JFileChooser(System.getProperty("user.dir"));
				chooser.setSize(650,450);
				chooser.setFileFilter(new FileFilter(){
					public boolean accept(File f){
						return f.getName().endsWith(".bf");
					}
					public String getDescription(){
						return "brainf*ck files (.bf)";}
				});
				chooser.setAcceptAllFileFilterUsed(true);
				int jc=chooser.showOpenDialog(null);
				File chosen=(jc==JFileChooser.APPROVE_OPTION)?chooser.getSelectedFile():null;
				if(chosen.canRead()){
					try{
						Scanner scan=new Scanner(chosen);
						code.setText("");
						while(scan.hasNextLine()){
							code.setText((code.getText().isBlank()?"":code.getText()+"\n")+scan.nextLine());}
						scan.close();
					}catch(Exception ex){output.setText(ex.toString());}
					
				}else{output.setText("Cannot read file (permission issues)");}
			}
		});
		
		JButton save = new JButton("");
		save.setIcon(new ImageIcon(Main.class.getResource("/resources/save.png")));
		save.setBounds(151,281,37,37);
		gui.getContentPane().add(save);
		save.setBorder(new LineBorder(Color.BLACK,1,true));
		save.setFocusPainted(false);
		save.setBackground(gui.getContentPane().getBackground());
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser chooser=new JFileChooser(System.getProperty("user.dir"));
				chooser.setSize(650,450);
				chooser.setApproveButtonText("Save");
				chooser.setFileFilter(new FileFilter(){
					public boolean accept(File f){
						return f.getName().endsWith(".bf");
					}
					public String getDescription(){
						return "brainf*ck files (.bf)";}
				});
				chooser.setAcceptAllFileFilterUsed(true);
				int jc=chooser.showOpenDialog(null);
				File bf=(jc==JFileChooser.APPROVE_OPTION)?chooser.getSelectedFile():null;
				try{
					if(!bf.exists()){bf.createNewFile();}
					FileWriter fl=new FileWriter(bf);
					fl.write(code.getText());
					fl.close();
					output.setText("saved successfully");
				}catch(IOException e){output.setText("cannot create save (permission error)");}
			}
		});
		
		convert.setIcon(new ImageIcon(Main.class.getResource("/resources/secondary-icon.png")));
		convert.setBounds(306,281,37,37);
		gui.getContentPane().add(convert);
		convert.setBorder(new LineBorder(Color.BLACK,1,true));
		convert.setFocusPainted(false);
		convert.setBackground(gui.getContentPane().getBackground());
		convert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(code.getText().isEmpty()){return;}
				Thread thread=new Thread(){
					public void run(){
						convert();
					}
				};
				thread.start();
				convert.setEnabled(false);
				gui.repaint();
			}
		});
		
		JToggleButton darkMode = new JToggleButton("");
		darkMode.setIcon(new ImageIcon(Main.class.getResource("/resources/darkModeOff.png")));
		darkMode.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		darkMode.setBounds(10,3,37,31);
		darkMode.setBackground(new Color(192,192,192));
		darkMode.setFocusPainted(false);
		darkMode.setSelectedIcon(new ImageIcon(Main.class.getResource("/resources/darkModeOn.png")));
		darkMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(darkMode.isSelected()){
					code.setBackground(Color.BLACK);
					run.setBackground(new Color(59,58,58));
					reset.setBackground(new Color(59,58,58));
					gui.getContentPane().setBackground(new Color(46,46,46));
					openFile.setBackground(new Color(59,58,58));
					output.setBackground(Color.BLACK);
					save.setBackground(new Color(59,58,58));
					code.setForeground(new Color(216,216,216));
					output.setForeground(new Color(216,216,216));
					convert.setBackground(new Color(59,58,58));
					gui.repaint();
				}else{
					gui.getContentPane().setBackground(new Color(192,192,192));
					code.setBackground(Color.WHITE);
					output.setBackground(Color.WHITE);
					code.setForeground(new Color(51,51,51));
					output.setForeground(new Color(51,51,51));
					run.setBackground(new Color(192,192,192));
					reset.setBackground(new Color(192,192,192));
					openFile.setBackground(new Color(192,192,192));
					save.setBackground(new Color(192,192,192));
					convert.setBackground(new Color(192,192,192));
					gui.repaint();
				}
			}
		});
		gui.getContentPane().add(darkMode);
		gui.setVisible(true);
	}
}