/*Programmer JOHN SAINNA
 *Date: Sept 2012
 *Program Name: Card.java
 *This program, Card.java, creates a card type object to be used by deck class
 *
 */

import java.net.URL;
import javax.swing.*;

class Card{


private int num;

public Card(int number) {
	num = number;


}
//Returns the values mapped to suit.
public static int getSuit(int number) {


	switch(number/13) {

		case 0: return 0;

			case 1: return 1;

				case 2: return 2;

					case 3: return 3;

					default: System.out.println("Errors: invalid status");
						     System.exit(0);


	}
return 0;
}
//Returns the face value of the card
public static int getValue(int number) {
	switch(number%13) {

		case 0: return 2;

			case 1: return 3;

				case 2: return 4;

					case 3: return 5;

					   case 4: return 6;

						  case 5: return 7;

							case 6: return 8;

								case 7: return 9;

									case 8: return 10;

										case 9: return 11;

											case 10: return 12;

					          					case 11: return 13;

					          						case 12: return 14;

					default: System.out.println("Errors: invalid status");
						     System.exit(0);

	}
return 0;

}
public  int getNum(){
	return num;
}

//Prints the card face value and suit.
public static void printCard(Card c){


	String[] suits = {"Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs",
	                  "Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds",
	                  "Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts",
	                  "Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades"};



	String[] ranks = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
	System.out.println(ranks[c.num] + " of " + suits[c.num]);


}
	//Overiddes the object toString method.
	public String toString(){


	String[] suits = {"Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs","Clubs",
	                  "Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds","Diamonds",
	                  "Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts","Hearts",
	                  "Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades","Spades"};



	String[] ranks = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace",
	                  "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
	return (ranks[num] + " of " + suits[num]);


}

}//End of class





