package run;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MazeClient extends JFrame implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2099569417948494846L;
	private Socket socket;
	private OutputStream ou;
	private Writer ouw; 
	private BufferedWriter bfw;
	
	private Player player;
	
	private Player[] opponets = {new Player(), new Player(), new Player()};
	private int opCount = 0;
	
	public int id;
	//private ArrayList<Player> players = new ArrayList<>();
	
	int[][] arrMaze = {	{9,9,9,9,9,9,9,9,9,9},
						{1,0,0,0,0,0,0,0,0,9},
						{9,0,9,9,9,9,9,9,0,9},
						{9,0,0,0,9,0,9,0,0,9},
						{9,0,0,9,9,0,0,0,0,9},
						{9,0,9,9,0,0,0,9,0,9},
						{9,0,0,9,0,9,0,9,0,9},
						{9,0,9,9,9,9,9,9,0,9},
						{9,0,0,0,9,0,9,0,0,9},
						{9,0,9,9,9,0,9,9,0,9},
						{9,0,0,0,0,0,0,0,0,0},
						{9,9,9,9,9,9,9,9,9,9}};
	
	final static int SIZE_RECT = 20;
	DrawTools dtools = new DrawTools(SIZE_RECT, arrMaze);

	public MazeClient() {
		
		player = new Player(0, 1);
		
		setSize(300, 400);
		setTitle("An Empty Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().add(dtools);
		
		addKeyListener(this);
		//DrawTools dt = new DrawTools();
	}
	
	public void setPosition(int x, int y) {
		if(checkMove(x, y)) {
			arrMaze[player.getY()][player.getX()] = 0;
			player.setX(x);
			player.setY(y);
			arrMaze[y][x] = player.getID();
			
			dtools.updateDrawMaze(arrMaze);
			try {
				enviarMensagem("MOVE:"+player.getID()+":"+x+":"+y);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int getOp(int opID) {
		for (int i = 0; i < opponets.length; i++) {
			if(opponets[i].getID()==opID)
				return i;
		}
		return -1;
	}
	
	public void setPositionOP(int opID, int x, int y) {
		if((opID>0 && opID<=4) && checkMove(x, y)) {
			int tempID = getOp(opID);
			arrMaze[opponets[tempID].getY()][opponets[tempID].getX()] = 0;
			opponets[tempID].setX(x);
			opponets[tempID].setY(y);
			arrMaze[y][x] = opponets[tempID].getID();
			
			System.out.println("OP ID = "+opponets[tempID].getID());
			dtools.updateDrawMaze(arrMaze);
			//try {
			//	enviarMensagem("MOVE:"+opponets[0].getID()+":"+x+":"+y);
			//} catch (IOException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		}
	}
	
	@SuppressWarnings("unused")
	private void putPlayers() {
		//dtools.addRectangle(new Rectangle(player, i*SIZE_RECT, Color.BLACK));
	}
	
	private boolean checkMove(int x, int y) {
		//System.out.println("x:"+x+" y:"+y);
		if(y<0 || y>=arrMaze.length) {
			return false;
		}
		if(x<0 || x>=arrMaze[0].length) {
			return false;
		}	
		if(arrMaze[y][x]!=0) {
			return false;
		}
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//dtools.swapCell(1, 1, 1);
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			setPosition(player.getX(), player.getY()-1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			setPosition(player.getX(), player.getY()+1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			setPosition(player.getX()-1, player.getY());
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setPosition(player.getX()+1, player.getY());
		}
	}
	
	private void putPlayer(int x, int y) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void enviarMensagem(String msg) throws IOException{
        
	    if(msg.equals("Sair")){
	      bfw.write("Desconectado \r\n");
	      //texto.append("Desconectado \r\n");
	    }else{
	      bfw.write(msg+"\r\n");
	      //texto.append( txtNome.getText() + " diz -> " +         txtMsg.getText()+"\r\n");
	    }
	     bfw.flush();
	     //txtMsg.setText("");        
	}
	
	public void conectar() throws IOException{
    
	  socket = new Socket("127.0.0.1",12345);
	  ou = socket.getOutputStream();
	  ouw = new OutputStreamWriter(ou);
	  bfw = new BufferedWriter(ouw);
	  bfw.write("Player"+"\r\n");
	  bfw.flush();
	}
	
	public void escutar() throws IOException{
     InputStream in = socket.getInputStream();
     InputStreamReader inr = new InputStreamReader(in);
     BufferedReader bfr = new BufferedReader(inr);
     String msg = "";
                             
      while(!"Sair".equalsIgnoreCase(msg))
                                        
         if(bfr.ready()){
           msg = bfr.readLine();
         if(msg.equals("Sair"))
        	 System.out.println("Servidor caiu!");
           //texto.append("Servidor caiu! \r\n");
         else if(msg.substring(0,2).equals("OP")) {
        	 String[] smsg = msg.split(":");
        	 int opID = Integer.parseInt(smsg[1]); 
        	 
        	 if(opCount<3) {
	        	// opponets[opCount] = new Player(8, 1);
	        	 opponets[opCount].setID(opID);
	        	 System.out.println("TESTYY "+opID);
	        	 opCount++;
        	 }
         }
         else if(msg.substring(0,4).equals("MOVE")) {
        	 String[] smsg = msg.split(":");
        	 int opID = Integer.parseInt(smsg[1]); 
        	 int opX = Integer.parseInt(smsg[2]);
        	 int opY = Integer.parseInt(smsg[3]);
        	 
        	 System.out.println("TEST "+opID);
        	 setPositionOP(opID,opX, opY);
         }
         else if(msg.substring(0,2).equals("ID")) {
        	 String[] smsg = msg.split(":");
        	 int plID = Integer.parseInt(smsg[1]); 
        	 player.setID(plID);
        	 
        	 System.out.println("OMFG ID = "+player.getID());
        	 
        	 switch (plID) {
			case 1:
				setPosition(0, 1);
				break;
			case 2:
				setPosition(8, 1);
				break;

			default:
				break;
			}
         }
         else
           System.out.println(msg);
           //texto.append(msg+"\r\n");  
         }
  }
	
	public void sair() throws IOException{
        
	    enviarMensagem("Sair");
	    bfw.close();
	    ouw.close();
	    ou.close();
	    socket.close();
	 }
	
	public static void main(String[] args) throws IOException {

		MazeClient mazeClient = new MazeClient();
		mazeClient.conectar();
		mazeClient.escutar();
	}

}
