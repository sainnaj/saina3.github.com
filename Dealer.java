/*Programer:JOHN SAINNA
 *Date: Sept 2012
 *This Dealer.java class acts as a dealer in poker game;
 *It has a card.java and deck.java classes.
 *It waits till three players join the game, then creates the cards,
 *shuffles and deals the cards to three players.
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

  public class Dealer extends JFrame  {

	final static int NUM_PLAYERS = 3;//The number of players
	Deck deck;
    Socket[] socketArray = new Socket[NUM_PLAYERS];
	DataInputStream[] inputArray = new DataInputStream[NUM_PLAYERS];
	DataOutputStream[] outputArray = new DataOutputStream[NUM_PLAYERS];
	DataInputStream in;
	DataOutputStream out;
    Socket socket;
    ServerSocket serverSocket;
    ClassLoader cldr = this.getClass().getClassLoader();//for Gui
    String imagePath;
    URL imageURL;
    boolean[] folded = {false,false,false};
    JLabel[] labelArray = new JLabel[NUM_PLAYERS];
    JLabel[] labelArray2 = new JLabel[NUM_PLAYERS];
    JLabel lbl4;
   ImageIcon img;
   int[] bank = new int[NUM_PLAYERS]; //players 1,2,3 bank amounts

   	    static int gamePot = 0;
	    static int gameCounter = 1;
	    static int bet = 0;

	public Dealer()  {
		 deck = new Deck();
		try {
		 serverSocket = new ServerSocket(3456);
		System.out.println("Waiting for TREE players to join the game...");

		int playerCounter  = 0;
		int index = 0;
	    //Connect three players
		while( playerCounter < NUM_PLAYERS ) {

			socket = serverSocket.accept();
			System.out.println("Player number: " +(index +1)+" has join the game" );
			socketArray[index] = socket;
			out = new DataOutputStream(socket.getOutputStream());
			outputArray[index] = out;
			in = new DataInputStream(socket.getInputStream());
			inputArray[index] = in;
			index++;
			playerCounter++;

		}
		//at this point connection made
		System.out.println("Tree Players have join the game...GOOD LUCK!!");

		}catch(IOException e) {
			System.out.println(e);
		}catch (NullPointerException e ){
			System.out.println(e + " At While loop for Dealer ");
		}
	}//End constructor


	/************************************************
	Play Game Method
	PlayGame Method deals Two,flop,turn and River cards to players
	************************************************/
	public  void playGame() throws IOException {
		while(true) {

	Border border = LineBorder.createGrayLineBorder();
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();

			 for(int i = 0; i<NUM_PLAYERS; i++) {//Assign $2000 to each player
			 	bank[i] = 2000;

			 }
			// int Player = ((gameCounter-1) % 3);

			 for(int i = 0,Player = ((gameCounter-1) % 3) ; i < NUM_PLAYERS; i++) {

			 	if(i == 0){
			 		bet = 0;
			 		bank[i] -= bet;
			 		outputArray[i].writeInt(bank[i]);//pass player 1 bank
			 	}
			 	else if(i == 1)	{
			 		bet = 50;
			 		bank[i] -= bet;
			 		gamePot += bet;
			 		outputArray[i].writeInt(bank[i]);//pass player 2 bank
			 	}
			 	else{
			 		bet = 100;
			 		bank[i] -= bet;
			 		gamePot += bet;
			 		outputArray[i].writeInt(bank[i]);//pass player 3 bank
			 	}
			 }

			 for(int pot = 0; pot<NUM_PLAYERS; pot++) {
			 outputArray[pot].writeInt(gamePot);//pass all Players POT bank

			 }

		setLayout(new BorderLayout(150,150));
		this.getContentPane().setBackground(Color.GREEN);//Set Background
		JPanel panel,panel2,panel3,panel4,panel5,panel6,panel7;
        JLabel lbl2,lbl3;
		lbl4 =  new JLabel ("Current POT");

    panel2 = new JPanel();
    panel3 = new JPanel();
    panel4 = new JPanel();
    panel5 = new JPanel();
    panel6 = new JPanel();
    panel7 = new JPanel();

    panel2.setBackground(Color.GREEN);
    panel3.setBackground(Color.GREEN);
    panel4.setBackground(Color.GREEN);//Set Color green
    panel5.setBackground(Color.GREEN);
    panel6.setBackground(Color.GREEN);
    panel7.setBackground(Color.GREEN);

    lbl4.setText("CURRENT POT: $" + gamePot );
    lbl4.setBorder(loweredbevel);
    lbl4.setBackground(Color.RED);
    panel4.add(lbl4);
    add(panel4, BorderLayout.CENTER );

     	System.out.println("Deal TWO cards...");
     	int deal = 51;	//Cards count down from 51 to 0.
		int dealTwoCards = 0;//A while loop tp deal the two unique cards to players.

		while (dealTwoCards < 2) {
		try {
		deck.shuffle();//Shuffle the cards
		int player = 0; // ((gameCounter-1) % 3);
		for(int i = 0; i< NUM_PLAYERS; i++){
		outputArray[player].writeUTF(deck.cards[deal].toString());
		outputArray[player].writeInt(deck.cards[deal].getNum());


	        getImageFromDealer(deal);//Call this method to get images
		      labelArray[i] = new JLabel(img);
		     if( i == 0) {//indexes 0,1,2 represents three players

		     	labelArray[i].setBorder(loweredbevel);
		     	setTextVerticalHorizantal(labelArray[i],"Player1");
		     	panel3.add(labelArray[i]);
		     	panel7.add(panel3);
		     	add(panel7, BorderLayout.NORTH);
		     	panel7.revalidate();

		     }
		     	else if(i == 1) {//Second players Card
		     		setTextVerticalHorizantal(labelArray[i],"Player2");
		     		panel5.add(labelArray[i]);
		     		panel7.add(panel5);
		     		add(panel7, BorderLayout.NORTH);
		     		panel7.revalidate();
		     	}
		     		else {//Third Players Card																					// Deal Two

		     		    setTextVerticalHorizantal(labelArray[i],"Player3");
		     			panel6.add(labelArray[i]);
		     			panel7.add(panel6);
		     			add(panel7, BorderLayout.NORTH);
		     			panel7.revalidate();
		     		}

		deck.shuffle();//Shuffle the cards
		player++;//track the indexes in an array
		}//End "FOR" loop
		}catch (IOException e) {
			System.out.println(e);
		}catch (NullPointerException e) {
			System.out.println(e + " At playGame for Dealer Class");
		}

		deal--;
		dealTwoCards++;
		}//End deal two cards while loop


	makeBet();	//Make a bet before FLOP


	//Deal Flop to each player
	System.out.println("Deal FLOP");

	int dealFlop = 0;// A loop to deal the FLOP(Three cards to three players;
	while (dealFlop < 3){
	try {
	int index2 = 0;
		for(int i = 0; i<NUM_PLAYERS; i++){
		outputArray[index2].writeUTF(deck.cards[deal].toString());
		outputArray[index2].writeInt(deck.cards[deal].getNum());
		index2++;
		}
	}catch(IOException e ) {
	System.out.println(e + " At playGame loop for FLOP");
	} catch(NullPointerException  e ){
	System.err.println(e + " At playGame loop for FLOP");
	}
		    getImageFromDealer(deal);
			labelArray2[dealFlop] = new JLabel(img);
			setTextVerticalHorizantal(labelArray2[dealFlop],"Flop");  //FLOP
			panel2.add(labelArray2[dealFlop]);
			add(panel2,BorderLayout.SOUTH);//remove
			panel2.revalidate();//remove

	    deal--;
		dealFlop++;
	}//End deal Flop

	    makeBet(); //Make a bet before TURN

	//Deal Turn(one card each);
	System.out.println("Deal TURN");

		int dealTurn = 0;
		while(dealTurn < 1) {
		try {
	int index3 = 0;
		for(int i = 0; i<NUM_PLAYERS; i++){
		outputArray[index3].writeUTF(deck.cards[deal].toString());
		outputArray[index3].writeInt(deck.cards[deal].getNum());
		index3++;
		}
	}catch(IOException e ) {
	System.out.println(e + " At playGame loop for TURN");
	} catch(NullPointerException  e ){
	System.err.println(e + " At playGame loop for TURN");
	}

			getImageFromDealer(deal);
			lbl2 = new JLabel(img);
			setTextVerticalHorizantal(lbl2,"Turn");
			panel2.add(lbl2);
			add(panel2,BorderLayout.SOUTH); //remove   //Deal Turn Cards
			panel2.revalidate();//remove

	  	deal--;
	  	dealTurn++;
		}//End Deal Turn


	    //String b = JOptionPane.showInputDialog("Make a bet before RIVER");
		makeBet();//Make a bet before RIVER


		//Deal River
		System.out.println("Deal RIVER");

		int dealRiver = 0;
		while( dealRiver < 1 ) {
		try {
	   int index4 = 0;
		for(int i = 0; i<NUM_PLAYERS; i++){
		outputArray[index4].writeUTF(deck.cards[deal].toString());
		outputArray[index4].writeInt(deck.cards[deal].getNum());

		index4++;
		}
	}catch(IOException e ) {
	System.out.println(e + " At playGame loop for RIVER");
	} catch(NullPointerException  e ){
	System.err.println(e + " At playGame loop for RIVER");
	}

			getImageFromDealer(deal);
			lbl3 = new JLabel(img);
			setTextVerticalHorizantal(lbl3,"River");         //Deal River Cards
			panel2.add(lbl3);
			add(panel2,BorderLayout.SOUTH);
			panel2.revalidate();

	    deal--;
	    dealRiver++;
		}//End dealRiver

		//Generate a random Number and passes it to dealers;
				try {
	int player1 = calculateWinner();

	if(player1 == 1 ) {

		outputArray[0].writeInt(1);
		outputArray[1].writeInt(0);
		outputArray[2].writeInt(0);
		bank[0] += gamePot;
			gamePot -= gamePot;

	}
	if(player1 == 0 ) {
		outputArray[0].writeInt(0);
		outputArray[1].writeInt(1);
		outputArray[2].writeInt(0);
		bank[1] += gamePot;
		gamePot -= gamePot;

	}



		}catch (IOException e) {
		}
		gameCounter++;
	  	this.getContentPane().removeAll(); // remove all the GUI contents(clean/clear)
		} //End for Ever Loop... FOREVER LOOP
		}//End PlayGame Method


		public  void makeBet(){//Options for player 1,2 and 3 to make a bet

	try {
		for(int i = 0; i<NUM_PLAYERS; i++) {

			if( i == 0 ) {//Player number 1
				outputArray[i].writeUTF("Player 1-Make a BET");//Notify player
				int num1 = inputArray[0].readInt();//Options 0,1,2
				if(num1 == 0 ){//CHECK
						 bet = 0;
				        gamePot += bet;
		    			outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
						outputArray[i].writeInt(gamePot);//to be added to gamePot
				}
				else if(num1 == 1){//RAISE

						bet = 100;
						gamePot += bet;
						bank[i] -= bet;
							outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
							outputArray[i].writeInt(gamePot);//to be added to gamePot
							lbl4.setText("CURRENT POT: $" + gamePot );
				}
				else {//FOLD
						bet = 0;
				    	gamePot += bet;
				    	folded[0] = true;
						outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
						outputArray[i].writeInt(gamePot);//to be added to gamePot
						System.out.println("3 You pressed PLAYER #1 " + num1 + " game Pot is " + gamePot);

				}

			}
		}


	for(int i = 0; i<NUM_PLAYERS; i++) {//Player number 2
		  if( i == 1 ) {
		  	outputArray[i].writeUTF("Player 2-Make a BET");//Notify player

				int num2 = inputArray[1].readInt();//Options 0,1,2
				if(num2 == 0 ){//CHECK
					bet = 0;
				    gamePot += bet;
					outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
					outputArray[i].writeInt(gamePot);//to be added to gamePot
				}
				else if(num2 == 1){//RAISE
						bet = 100;
						gamePot += bet;
						bank[i] -= bet;
							outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
							outputArray[i].writeInt(gamePot);//to be added to gamePot
							lbl4.setText("CURRENT POT: $" + gamePot );
				}
				else {//FOLD
				    bet = 0;
				    gamePot += bet;
				    folded[1] = true;

					outputArray[i].writeInt(bank[i]);	//to be subtracted from userBank
					outputArray[i].writeInt(gamePot);//to be added to gamePot
				}

			}
	}

		for(int i = 0; i<NUM_PLAYERS; i++) {//Player number 3

			if (i == 2) {
				outputArray[i].writeUTF("Player 3-Make a BET");//Notify player

				int num3 = inputArray[2].readInt();//Options 0,1,2
				if(num3 == 0 ){//CHECK
				    bet = 0;
				    gamePot += bet;
					outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
					outputArray[i].writeInt(gamePot);//to be added to gamePot
				}
				else if(num3 == 1){//RAISE

						bet = 100;
						gamePot += bet;
						bank[i] -= bet;
							outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
							outputArray[i].writeInt(gamePot);//to be added to gamePot
							lbl4.setText("CURRENT POT: $" + gamePot );

				}
				else {//FOLD
						bet = 0;
						gamePot += bet;
						folded[2] = true;
						outputArray[i].writeInt(bank[i]);//to be subtracted from userBank
						outputArray[i].writeInt(gamePot);//to be added to gamePot
				}

			}
		}//End for Loop
			//Update all players
			 for(int pot = 0; pot<NUM_PLAYERS; pot++) {
			 outputArray[pot].writeInt(gamePot);//pass all Players POT bank
			 }

		}catch (IOException e) {
			System.err.println(e);
		}
		}
	//Get images from file
	private void getImageFromDealer(int index89){
	    	imagePath = "images/cards_gif/" + deck.cards[index89].getNum() + ".gif";
			imageURL = cldr.getResource(imagePath);
			img = new ImageIcon(imageURL);

	}
	//Position JLabels title
	private void setTextVerticalHorizantal(JLabel betLevel,String title){
		betLevel.setText(title);
		betLevel.setVerticalTextPosition(JLabel.BOTTOM);
		betLevel.setHorizontalTextPosition(JLabel.CENTER);

	}
	public void actionPerformed(ActionEvent e) {//remove


	}//calculate winner using a random number 0 and 2
	public static int calculateWinner() {
		int m = (int)(Math.random() * 2);
		return m;
	}

		public static void main(String[] args)throws IOException {
	    Dealer d2 = new Dealer();
	    d2.setTitle("Porker Game-Dealer");
		d2.setSize(1000, 700);
		d2.setLocationRelativeTo(null);
		d2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d2.setVisible(true);
		d2.playGame();
	}
	}//End Dealer Class


