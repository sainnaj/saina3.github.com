/*Programmer JOHN SAINNA
 *Date: Sept 2012
 *This program Deck.java, uses the Card class to create a deck.
 *It adds a shuffle method to shuffle the deck and a print method to
 *print the deck object
 */

import java.io.*;

class Deck {
	Card[] cards;

	//Creates a new card
	public Deck() {
	 cards = new Card[52];
		int index = 0;
		for(int nums = 0; nums < cards.length; nums++ ) {
		cards[index] = new Card(nums);
		index++;
		}
	}
	//Shuffle methos, shuffles the cards and returns
	//an array of shuffled cards
	public Card[] shuffle() {
		Card temp;
		int a;
		for(int i = 0; i < cards.length; i++){
			a = (int)(Math.random() * cards.length);
			temp = cards[i];
			cards[i] = cards[a];
			cards[a] = temp;

		}
		return cards;
	}

	public static void printDeck (Deck deck) {
		for(int i = 0; i<deck.cards.length; i++) {
			Card.printCard(deck.cards[i]);
		}

	}



}