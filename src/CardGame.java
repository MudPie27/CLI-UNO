import java.util.ArrayList; 
import java.util.Random;
import java.util.Scanner;


public class CardGame {
	
    static ArrayList<Card> deck = new ArrayList<Card>(52);		
    static ArrayList<Card> discard = new ArrayList<Card>();		
    static ArrayList<Card> hand1 = new ArrayList<Card>(7);		
    static ArrayList<Card> hand2 = new ArrayList<Card>(7);		

    static int startAmount = 7, d, p1, p2, choice, select, c;				
    static String player1, player2, choiceStr, selectStr, cSTR, contMenu;	
    static boolean turnp1 = true, win = false, drawed;						


    static Value v;						
    static Card currentCard;			
    
    static Scanner in = new Scanner(System.in);			
    static Random  rand = new Random();					


    public static enum Colour {
        RED, YELLOW, GREEN, BLUE, WILD
    }
    
    public static enum Value {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        SKIP, REVERSE, DRAW_TWO, CARD, DRAW_FOUR
    }
    
    // stores all the colour and value enums in an array
    static Colour cols[] = Colour.values();			
    static Value vals[] = Value.values();			

    /**************** Variables End *****************/
   
    // Card object
    public static class Card {
        public Colour colour;
        public Value value;

        public Card(Colour c, Value v) {		
            colour = c;
            value = v;
        }

        @Override public String toString() {			
            return this.colour + " " + this.value;
        }
    }
    
    // create the deck (half of a real UNO deck, so only 52 cards)
    public static void createDeck() {
        for (int i = 0; i < cols.length-1; i++) {

            for (int x = 0; x < vals.length-2; x++) {

                deck.add(new Card(cols[i], vals[x]));
            }
        }
        deck.add(new Card(Colour.WILD, Value.CARD));
        deck.add(new Card(Colour.WILD, Value.CARD));
        deck.add(new Card(Colour.WILD, Value.DRAW_FOUR));
        deck.add(new Card(Colour.WILD, Value.DRAW_FOUR));
    }

    // give players 7 cards from the deck and adds a starting card to the discard pile
    public static void dealCards() {

        d = rand.nextInt(deck.size());			
        
        // ensures special cards can't be a starting card
        while (((v = deck.get(d).value) == Value.SKIP) || (v == Value.REVERSE) ||
        (v == Value.DRAW_TWO) ||(v == Value.CARD || (v == Value.DRAW_FOUR))) {

            d = rand.nextInt(deck.size());

        }
        
        discard.add(deck.remove(d));
        currentCard = discard.get(discard.size()-1);
        
        for (int i = 0; i < startAmount; i++) {

            p1 = rand.nextInt(deck.size());
            hand1.add(deck.remove(p1));

            p2 = rand.nextInt(deck.size());
            hand2.add(deck.remove(p2));           
        }
        
    }

    // get player names
    public static void getPlayerNames() {

        System.out.println("\nEnter the name of Player 1: ");
        System.out.println("=============================");
        player1 = in.nextLine();
        System.out.println("\nEnter the name of Player 2: ");
        System.out.println("=============================");
        player2 = in.nextLine();
        System.out.println("\r\n\r\n[     Starting game...     ]\r\n\r\n");
    }


    // printing player cards with choice numbers
    static void showCardsHand() {
        if (turnp1 == true) {

            for (int i = 0; i < hand1.size(); i++) {
                System.out.println("["+ i + "] " + hand1.get(i));
            }

            System.out.println("[100] DRAW CARD");
        } else if (turnp1 == false) {

            for (int i = 0; i < hand2.size(); i++) {
                System.out.println("["+ i + "] " + hand2.get(i));
            }

            System.out.println("[100] DRAW CARD");
        }
    }
   
    // checks if the card chosen by the player can be used or not
    static void checkCardValidity() {
        drawed = false;					
        
        if (turnp1) {

            while (true) {
                System.out.print("\r\nChoose card: ");
                choiceStr = in.nextLine();
                if (choiceStr.toLowerCase().equals("stop")) {
                    System.out.println("\r\n[     Returning to menu...     ]\r\n");
                    menu();
                }

                try {
                    choice = Integer.parseInt(choiceStr);

                    if ((choice == 100) || (hand1.get(choice).colour == Colour.WILD) || (currentCard.colour == Colour.WILD) ||
                    (hand1.get(choice).colour == currentCard.colour) || (hand1.get(choice).value == currentCard.value)) {

                        if (choice == 100) {		
                        	if (deck.size() >= 1) {
                                drawed = true;		
                        		p1 = rand.nextInt(deck.size());
                        		hand1.add(deck.remove(p1));
                        		System.out.println("\n-> " + player1 + " drew a card. Turn skipped!\r\n");
                        		break; 
                        	}
                        }
                    
                        System.out.println("\r\n-> Card is compatible\r\n");	
                        discard.add(hand1.remove(choice));
                        break;

                    } else if ((hand1.get(choice).colour != currentCard.colour) && (hand1.get(choice).value != currentCard.value)) {

                        System.out.println("\r\n-> Card cannot be placed: \r\n");		
                    }

                } catch (java.lang.IndexOutOfBoundsException e) {		

                    System.out.println("\nInvalid choice: ");		

                } catch (java.lang.NumberFormatException e) {		

                    System.out.println("\nEnter a number from the choices: ");
                }
            }
		
        } else if (!turnp1) {

            while (true) {
                System.out.print("\r\nChoose card: ");
                choiceStr = in.nextLine();

                if (choiceStr.toLowerCase().equals("stop")) {
                    System.out.println("\r\n[     Returning to menu...     ]\r\n");
                    menu();
                }

                try {
                    choice = Integer.parseInt(choiceStr);

                    if ((choice == 100) || (hand2.get(choice).colour == Colour.WILD) || (currentCard.colour == Colour.WILD) ||
                    (hand2.get(choice).colour == currentCard.colour) || (hand2.get(choice).value == currentCard.value)) {

                    	if (choice == 100) {

                          	if (deck.size() >= 1) {
                                drawed = true;
                          		p2 = rand.nextInt(deck.size());
                          		hand2.add(deck.remove(p2));
                          		System.out.println("\n-> " + player2 + " drew a card. Turn skipped!\r\n");
                          		break; 
                          	}
                        }

                        System.out.println("\r\n-> Card is compatible\r\n");
                        discard.add(hand2.remove(choice));
                        break;

                    } else if ((hand2.get(choice).colour != currentCard.colour) && (hand2.get(choice).value != currentCard.value)) {

                        System.out.println("\r\n-> Card cannot be placed: \r\n");
                    }

                } catch (java.lang.IndexOutOfBoundsException e) {

                    System.out.println("\nInvalid choice: ");

                } catch (java.lang.NumberFormatException e) {

                    System.out.println("\nEnter a number from the choices: ");
                }
            }
        }
    }

    // checks if current card is a special card - if yes, does the effect.
    public static void specialCards() {
        if (currentCard.value == Value.SKIP) {
            System.out.println("~~~~~   Turn skiped!   ~~~~~\r\n");
        } else if (currentCard.value == Value.REVERSE) {
            System.out.println("~~~~~   Turns reversed!   ~~~~~\r\n");
        } else if (currentCard.value == Value.DRAW_TWO) {
            p1 = rand.nextInt(deck.size());

            if ((turnp1) && (deck.size() >= 2)) {
                System.out.println("~~~~~     Added 2 cards to " + player2 + "'s deck!     ~~~~~\r\n");
                hand2.add(deck.remove(p1));
                p2 = rand.nextInt(deck.size());
                hand2.add(deck.remove(p2));

            } else if ((!turnp1) && (deck.size() >= 2)) {
                System.out.println("~~~~~     Added 2 cards to " + player1 + "'s deck!     ~~~~~\r\n");
                hand1.add(deck.remove(p1));
                p2 = rand.nextInt(deck.size());
                hand1.add(deck.remove(p2));
                    
            } else {
                System.out.println("~~~~~   Not enough card's in deck to draw 2... card was used without it's effect.   ~~~~~\r\n");

            }
            turnp1 = !turnp1; 
            
        } else if (currentCard.value == Value.DRAW_FOUR) {

            if ((turnp1) && (deck.size() >= 4)) {
                System.out.println("~~~~~     Wild draw four used! added 4 cards to " + player2 + "'s deck!     ~~~~~"
                + "\r\n->   Play any card and the colour will be changed accordingly!\r\n"); 
                for (int i = 0; i < 4; i++) {
                    p1 = rand.nextInt(deck.size());
                    hand2.add(deck.remove(p1));
                }
                    
            } else if ((!turnp1) && (deck.size() >= 4)) {
                System.out.println("~~~~~     Wild draw four used! added 4 cards to " + player1 + "'s deck!     ~~~~~" 
                + "\r\n->   Play any card and the colour will be changed accordingly!\r\n"); 
                for (int i = 0; i < 4; i++) {
                    p2 = rand.nextInt(deck.size());
                    hand1.add(deck.remove(p2));
                }
            } else {
                 System.out.println("~~~~~   Not enough card's in deck to draw 4... card will only change the colour   ~~~~~\r\n");
            }
        } else if (currentCard.value == Value.CARD) {
            System.out.println("~~~~~     Wild card used! Play any card and the colour will be changed accordingly!     ~~~~~\r\n"); 

        } else {
            turnp1 = !turnp1;	
        }
    }
    
    // checks win condition to see if any players won
    public static void winnner() {

        if (hand1.size() == 0) {
            System.out.println("=====================================================\r\n"
            + "              " + player1 + " won the game!\r\n" 
            + "              " + player2 + " had " + hand2.size() + " card(s) left.\r\n"
            + "=====================================================\r\n");

            System.out.println("\r\nEnter anything to return to menu...\r\n");
            contMenu = in.nextLine();
            menu();

        } else if (hand2.size() == 0) {
            System.out.println("=====================================================\r\n"
            + "              " + player2 + " won the game!\r\n" 
            + "              " + player1 + " had " + hand1.size() + " card(s) left.\r\n"
            + "=====================================================\r\n");

            System.out.println("\r\nEnter anything to return to menu...\r\n");
            contMenu = in.nextLine();
            menu();
         
        } else if (deck.size() == 0) {
            System.out.println("[     The deck is out of cards. The game will end.     ]\r\n");

            if (hand1.size() < hand2.size()) {
                System.out.println("=============================================================================================\r\n"
                + "            " + player1 + " won because they had " + (hand2.size() - hand1.size()) + " card(s) less left than " + player2 + "!\r\n"
                + "=============================================================================================\r\n");
            } else if (hand1.size() > hand2.size()) {
                System.out.println("=============================================================================================\r\n"
                    + "            " + player2 + " won because they had " + (hand1.size() - hand2.size()) + " card(s) less left than " + player1 + "!\r\n"
                    + "=============================================================================================\r\n");
            } else if (hand1.size() == hand2.size()) {
                System.out.println("=============================================================================================\r\n"
                    + "            " + player2 + " and " + player1 + " had the same amount of card(s) left... it's a tie!\r\n"
                    + "=============================================================================================\r\n");
            }
            
            System.out.println("\r\nPress enter to return to menu...\r\n");
            contMenu = in.nextLine();
            menu();
        }
    }
    
    // main game loop
    public static void unoGame() {
    	
    	// resets the array lists 
        deck.clear();
        hand1.clear();
        hand2.clear();
        discard.clear();
        turnp1 = true;
        
        // runs preliminary methods
        createDeck();
        dealCards();
        getPlayerNames();

        while (!win) {
        	
            if (turnp1) {
                System.out.println("[     " + player1 + "'s turn     ]\n");
            } else if (!turnp1) {
                System.out.println("[     " + player2 + "'s turn     ]\n");
            }
            
            System.out.println("Current card: " + currentCard);
            System.out.println("=============================");

            showCardsHand();	
            checkCardValidity();	
            currentCard = discard.get(discard.size()-1); // updates current card with the new played card

            if ((hand1.size() == 0) || (hand2.size() == 0) || (deck.size() == 0)) {
                winnner();
                win = true;
            }
            
            // if player drawed a card last turn, special cards aren't checked
            // this is done to avoid the bug where drawing cards multiple times made special card effects play out multiple times
            if (drawed == false) {
                specialCards();
            } else {
                turnp1 = !turnp1; // switches turn
            }
        }
    }
    
    // main menu of the game
    public static void menu() {
    	
    	System.out.print("____     ___                    \r\n"
    			+ "`MM'     `M'                    \r\n"
    			+ " MM       M                     \r\n"
    			+ " MM       M ___  __     _____   \r\n"
    			+ " MM       M `MM 6MMb   6MMMMMb  \r\n"
    			+ " MM       M  MMM9 `Mb 6M'   `Mb \r\n"
    			+ " MM       M  MM'   MM MM     MM \r\n"
    			+ " MM       M  MM    MM MM     MM \r\n"
    			+ " YM       M  MM    MM MM     MM \r\n"
    			+ "  8b     d8  MM    MM YM.   ,M9 \r\n"
    			+ "   YMMMMM9  _MM_  _MM_ YMMMMM9  \r\n"
    			+ "================================\r\n"
    			+ "                                \r\n"
                + "   You can return to the menu\r\n" 
                + "  at any time by typing \"stop\"!"
    			+ "\r\n"
    			+ "\r\n"
    			+ "	  [1] Play Game\r\n"
    			+ "	  [2] Rules\r\n"
    			+ "	  [3] Quit\r\n"
    			+ "\r\n"
    			+ "\r\nSelect: ");
    	
    	while (true) {
    		
    		selectStr = in.nextLine();		
        	
        	try {
                select = Integer.parseInt(selectStr);

        		if ((select != 1) && (select != 2 ) && (select != 3)) {  
        			System.out.print("\r\nPlease select one of the given options: ");
        			
        		} else if (((select == 1) || (select == 2 ) || (select == 3))) {
        			
        			if (select == 1) {
        	    		unoGame();
        	    		
        	    	} else if (select == 2) {
        	    		System.out.print("\r\nWelcome to UNO! This version of UNO is modified and only has half the original deck (52 cards!)\r\n"
        	    				+ "Here are the basics. You can only play: \r\n"
        	    				+ "\r\n    [1] Cards with the same colour\r\n"
        	    				+ "    [2] Cards with the same number\r\n"
        	    				+ "    [3] Special cards with the same colour\r\n"
                                + "    [4] Special cards on top of different coloured, but otherwise same special cards\r\n"
                                + "    [5] WILD CARDs and WILD DRAW FOURs can be played on top of any card\r\n"
                                + "\r\n"
                                + "\r\nYour goal is to get rid of all your cards. Whoever reaches zero cards first wins!\r\n"
                                + "\r\nIf the deck runs out of cards, whoever has less cards win. The same amount of cards result in a tie!\r\n"
                                + "And to make it easier, you don't even need to say UNO! Good luck!\r\n");

                                System.out.println("\r\nPress enter to return to menu...\r\n");
                                contMenu = in.nextLine();

                                System.out.println("===================================\r\n"
                                + "	  [1] Play Game\r\n"
                                + "	  [2] Rules\r\n"
                                + "	  [3] Quit\r\n"
                                + "\r\n"
                                + "\r\nSelect: ");

        	    	} else if (select == 3) {
                        System.out.println("\r\n[      Quiting game...      ]\r\n");
        	    		System.exit(0);
        	    	}	
        		}
        		
        	} catch (java.lang.NumberFormatException e) {
    			System.out.print("\r\nPlease select a proper option: "); 
        	}	
    	}
    }
    
    public static void main(String[] args) {
        menu();				
        in.close();			
    }
}
