/**
**Programmer: John Sainna
*Date: Sept 2012
**Poker Game:This Class Acts as a client Representing one player
*It receives the seven cards dealt to one player.
**/
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

 public class Player extends JFrame implements ActionListener {
	Card[] cards;
	private final int NUM_PLAYERS = 3;
	private final int FLOP_TURN_RIVER = 7;
	private Socket toServerSocket;
	private DataInputStream inClient;
	private DataOutputStream outClient;
	private String[] hand = new String[FLOP_TURN_RIVER];
	private int[] nums = new int[FLOP_TURN_RIVER];
	String message = "";
	ClassLoader cldr = this.getClass().getClassLoader();//for Gui
    String imagePath;
    URL imageURL;
    JLabel[] label = new JLabel[7];
    JPanel panel,panel2,panel3,panel4,panel5;
	JButton btn2, btn3,btn4;//For Frame
	JLabel lbl2,lbl3;
	int bankOne;
	private int gamePot;

public Player() {
	     try {

		System.out.println("CLIENT IS ATTEMPTING CONNECTION...");
	    toServerSocket = new Socket("localhost", 3456);
	    System.out.println("CONNECTION HAS BEEN MADE");

	    inClient  = new DataInputStream(toServerSocket.getInputStream());
		outClient = new DataOutputStream(toServerSocket.getOutputStream());
	     }catch (IOException e){
	     	System.out.println(e + "  At player constructor");
	     }
}//End Cosntructor

//#################################################################
//Play Game Method
//Receives the cards dealt
//#################################################################
public  void playGame() throws IOException {


	while(true)  {

		//RECEIVE each player their current bank and pot amount
		bankOne  = inClient.readInt();
	   	gamePot  = inClient.readInt();

		setLayout(new BorderLayout(150,150));
		this.getContentPane().setBackground(Color.GREEN);//Set Background

    panel = new JPanel();
    panel.setBackground(Color.GREEN);//Set Background
    panel2 = new JPanel();
    panel2.setBackground(Color.GREEN);//Set Background
    panel3 = new JPanel();
    panel3.setBackground(Color.GREEN);//Set Background
    panel4 = new JPanel();
    panel5 = new JPanel();

        lbl2 = new JLabel("TOTAL POT");
        lbl2.setText("TOTAL POT: $" + gamePot);
		lbl3 = new JLabel("USER BANK");
		lbl3.setText("USER BANK: $" + bankOne);
		panel4.add(lbl2);
		panel.add(panel4);
		add(panel);
		panel5.add(lbl3);

		btn2 = new JButton("CHECK");
		btn2.setMnemonic(KeyEvent.VK_C);
		btn2.setActionCommand("disable");
		btn2.setToolTipText("Click this button to CHECK bet");
		btn2.addActionListener(this);
		btn3 = new JButton("RAISE");
		btn3.setMnemonic(KeyEvent.VK_R);
		btn3.setActionCommand("disable");
		btn3.setToolTipText("Click this button to RAISE bet");
		btn3.addActionListener(this);
		btn4 = new JButton("FOLD");
		btn4.setMnemonic(KeyEvent.VK_F);
		btn4.setActionCommand("disable");
		btn4.setToolTipText("Click this button to FOLD");
		btn4.addActionListener(this);
   	    panel3.add(btn2);
        panel3.add(btn3);
        panel3.add(btn4);
        add(panel3, BorderLayout.SOUTH);
        panel3.revalidate();//update

	int index = 0;
	int twoCards = 0;
	while( twoCards < 2  )  {
		   hand[index] = inClient.readUTF();//Receive cards and put them in an array
		   nums[index]  = inClient.readInt(); //Receive int representing cards

			getImageFromDealer(twoCards, index);

			if(twoCards == 0) {
				setTextVerticalHorizantal(twoCards,"Player Card1 ");
				panel.add(label[twoCards]);
				add(panel, BorderLayout.NORTH);
				panel.revalidate();
			}
					if(twoCards == 1) {
		     		setTextVerticalHorizantal(twoCards,"Player Card2 ");
					panel.add(label[twoCards]);
					panel.add(panel5);//add USER BANK
					add(panel, BorderLayout.NORTH);
					panel.revalidate();
					}

		index++;//Increment the array(advance)
		twoCards++;
	}//End outer while loop

    getBetInfoFromDealer();

		int flop = 0;
			while( flop < 3  )  {
		   	hand[index] = inClient.readUTF();//Receive cards and put them in an array
		  	 nums[index]  = inClient.readInt(); //Receive int representing cards
			getImageFromDealer(flop, index);//Get flop Images
				if(flop == 0 || flop == 1 || flop == 2 ){
						setTextVerticalHorizantal(flop,"FLOP");
						panel2.add(label[flop]);
						add(panel2, BorderLayout.CENTER);
						panel2.revalidate();
				}
			index++;//Increment the array(advance)
			flop++;
			}//End outer while loop

		getBetInfoFromDealer();//Make a BET

	int turn = 0;
	while( turn < 1  )  {
		   hand[index] = inClient.readUTF();//Receive cards and put them in an array
		   nums[index]  = inClient.readInt(); //Receive int representing cards
		   getImageFromDealer(turn,index);//Get TURN images from Dealer
	if(turn == 0) {
		setTextVerticalHorizantal(turn,"TURN");
		panel2.add(label[turn]);
		add(panel2, BorderLayout.CENTER);
		panel2.revalidate();
	}

	index++;//Increment the array(advance)
	turn++;
	}//End outer while loop

	getBetInfoFromDealer();//Make a BET

		int river = 0;
		while( river < 1  )  {
		   hand[index] = inClient.readUTF();//Receive cards and put them in an array
		   nums[index]  = inClient.readInt(); //Receive int representing cards
		   getImageFromDealer(river,index);
			if(river == 0) {
						setTextVerticalHorizantal(river,"RIVER");
						panel2.add(label[river]);
						add(panel2, BorderLayout.CENTER);
						panel2.revalidate();
			}
		index++;//Increment the array(advance)
		river++;
		}//End outer while loop

		try {
	//Receive a random number representing the winner from dealer and display it.
	int num2 = inClient.readInt();
	if ( num2 == 1) {
		JOptionPane.showMessageDialog(null, "Congratulations-You Won this hand!!!!");
	}

	}catch (IOException e){
	}//end try

	this.getContentPane().removeAll(); // remove all the contents in the frame

	} //END FOREVER LOOP
	}//End PlayGame method


	public void getBetInfoFromDealer() {//Method to get bet information from dealer

		try {
    message = inClient.readUTF();
    JOptionPane.showMessageDialog(null, message);

    bankOne  = inClient.readInt();
	lbl3.setText("USER BANK: $" + bankOne);

	gamePot  = inClient.readInt();
	lbl2.setText("TOTAL POT: $" + gamePot);

	gamePot  = inClient.readInt();
	lbl2.setText("TOTAL POT: $" + gamePot);

		}
		catch (IOException e ) {
			System.err.println(e.getMessage());
		}
	}//End getInfoFromDealerMethod

	private void getImageFromDealer(int betLevel, int index89){
	    	imagePath = "images/cards_gif/" +  nums[index89] + ".gif";
			imageURL = cldr.getResource(imagePath);
			ImageIcon img = new ImageIcon(imageURL);
			label[betLevel] = new JLabel(img);
	}

	private void setTextVerticalHorizantal(int betLevel,String title){
		label[betLevel].setText(title);
		label[betLevel].setVerticalTextPosition(JLabel.BOTTOM);
		label[betLevel].setHorizontalTextPosition(JLabel.CENTER);
	}

	public void actionPerformed(ActionEvent e)  {//remove
  		try {

	if(e.getSource() == btn2) {//CHECK
		outClient.writeInt(0);
	}
	else if( e.getSource() == btn3){//RAISE
		outClient.writeInt(1);
	}
	else {//FOLD
		outClient.writeInt(2);
	}

  		}catch(IOException ioe) {
  			System.err.println(ioe);
  		}
	}//Listener Method

	public static void main(String[] args)throws IOException {
		Player d2 = new Player();
	    	d2.setTitle("Player");
			d2.setSize(550, 700);
			d2.setLocationRelativeTo(null);
			d2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			d2.setVisible(true);
			d2.playGame();
	}

 }	//Main Method

