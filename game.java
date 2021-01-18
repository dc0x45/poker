import java.util.*;

public class game {

    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" }; 
    private List<deck> deck; private final Scanner in;
    private hand player; private int chips; private int anteUp;
    private boolean trigger2; private int check; private boolean endOfGame; private int numCards;
    private int card1; private int card2; private int card3;

    public game() {
        this.player = new hand();    
        this.in = new Scanner(System.in);
        trigger2 = false; endOfGame = false;
        numCards = -1; chips = 0;
        card1 = 0; card2 = 0; card3 = 0;
    }

    public void play() {
        do {
            this.player = new hand();
            card1 = 0; card2 = 0; card3 = 0;
            numCards = -1; endOfGame = false;
            String input;
            if (chips == 0) {
                do {
                    int i = 0;
                    System.out.print("What is your buy in (up to 100 chips): ");
                    input = in.nextLine();

                    for (char c : input.toCharArray()) {
                        i++;
                        if (!Character.isDigit(c)) {
                            System.out.println("\nInvalid input please try again.");
                            break;
                        }
                    }
                    if (i == input.toCharArray().length) {
                        try {
                            chips = Integer.parseInt(input);
                            if(chips > 0) {
                                trigger2 = true;
                            } else {
                                System.out.println("\n0 chips aren't a thing.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nInvalid input please try again.");
                        }
                    }
                } while (trigger2 = false);
            }
            trigger2 = false;
            do {
                System.out.print("\nCurrent chips: " + chips);
                System.out.print("\nAnte: ");
                input = in.next() + in.nextLine();
                    try {
                        check = Integer.parseInt(input);
                        if (check < 1 || check > 100) {
                            System.out.println("Maximum ante is 100 chips.");
                        } else if (check > chips) {
                            System.out.println("Ante must be between 1 and " + chips);
                        } else {
                            anteUp = check;
                            trigger2 = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
            } while (trigger2 == false);
            trigger2 = false;
            shuffler(); showhand();
            do {
                System.out.println("\nHow many cards would like to trade out. You may trade up to 3 cards.");
                System.out.print("Number of cards: ");
                try {
                    numCards = Integer.parseInt(in.next());
                    if (numCards < 0 || numCards > 3) { System.out.print("\nYou may trade between 0 and 3 cards."); }
                } catch (NumberFormatException e) {
                    System.out.print("\nInvalid input.");
                }
            } while (numCards < 0 || numCards > 3);

            discardCards();
            player.sortHand();
            showhand();

            winCondition();
        } while (endOfGame == false);
        endOfGame = false;
        closeGame();
    }

    public void closeGame() {
        in.close();
    }

    public void winCondition() {
        if (player.rFlushC()) {
            System.out.println("\nA royal flush! You have recieved 100x the chips you've bet!");
            anteUp *= 100;
            chips += anteUp;
        } else if (player.straightFC()) {
            System.out.println("\nA straight flush! You have recieved 50x the chips you've bet!");
            anteUp *= 50;
            chips += anteUp;
        } else if (player.foakC()) {
            System.out.println("\nFour-of-a-kind! You have recieved 25x the chips you've bet!");
            anteUp *= 25;
            chips += anteUp;
        } else if (player.houseC()) {
            System.out.println("\nA full house! You have recieved 15x the chips you've bet!");
            anteUp *= 15;
            chips += anteUp;
        } else if (player.flushC()) {
            System.out.println("\nA flush! You have recieved 10x the chips you've bet!");
            anteUp *= 10;
            chips += anteUp;
        } else if (player.straightC()) {
            System.out.println("\nA straight! You have recieved 5x the chips you've bet!");
            anteUp *= 5;
            chips += anteUp;
        } else if (player.toakC()) {
            System.out.println("\nThree-of-a-kind! You have recieved 3x the chips you've bet!");
            anteUp *= 3;
            chips += anteUp;
        } else if (player.tPairC()) {
            System.out.println("\nTwo pairs! You have recieved 2x the chips you've bet!");
            anteUp *= 2;
            chips += anteUp;
        } else if (player.pairC()) {
            System.out.println("\nA pair! Your ante has been returned to you at no penalty.");
        } else {
            System.out.println("\nYou lost! Better luck next time");
            chips -= anteUp;
        }

        String answer = "";
        do {
            System.out.println("\nAnother round is starting. Would you like to play?");
            System.out.print("Please answer (y) for yes and (n) for no: ");
            answer = in.next();
            if (!answer.trim().toUpperCase().equals("Y") && !answer.trim().toUpperCase().equals("N")) {
                System.out.println("\nPlease answer (y) for yes and (n) for no");
            }
        } while (!answer.trim().toUpperCase().equals("Y") && !answer.trim().toUpperCase().equals("N"));
        if (answer.trim().toUpperCase().equals("N")) {
            endOfGame = true;
        }
    }

    public void showhand() {
        System.out.println("Your hand: " + player.getHand().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
    }

    private void shuffler() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));
        }
        player.sortHand();
    }

    private void discardCards() {
        boolean check = false;
        if (numCards > 0) {
            System.out.println("\nNow you will select the cards you would like to trade in. \nThe cards are listed in order so you can select them as such.");
            do {
                try {
                    System.out.print("Card 1: ");
                    card1 = Integer.parseInt(in.next());
                    if (card1 < 1 || card1 > 5) {
                        System.out.println("Please input a valid card!");
                    } else {
                        check = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input please try again!");
                }
            } while (check == false);
            check = false;
            if (numCards > 1) {
                do {
                    try {
                        System.out.print("Card 2: ");
                        card2 = Integer.parseInt(in.next());
                        if (card2 < 1 || card2 > 5) {
                            System.out.println("Please input a valid card!");
                        } else if (card2 == card1) {
                            System.out.println("You may not trade in the same card twice!.");
                        } else {
                            check = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input please try again!");
                    }
                } while (check == false);
                check = false;
                if (numCards > 2) {
                    do {
                        try {
                            System.out.print("The third card you would like to trade in: ");
                            card3 = Integer.parseInt(in.next());
                            if (card3 < 1 || card3 > 5) {
                                System.out.println("Please input a valid card!");
                            } else if (card3 == card1 || card3 == card2) {
                                System.out.println("You may not trade in the same card twice!");
                            } else {
                                check = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input please try again!");
                        }
                    } while (check == false);
                    check = false;
                    sortCards();
                    player.discardCard(player.getCards().get(card1-1));
                    player.discardCard(player.getCards().get(card2-2));
                    player.discardCard(player.getCards().get(card3-3));
                    player.takeCard(deck.remove(0)); player.takeCard(deck.remove(0)); player.takeCard(deck.remove(0));
                } else {
                    sortCards();
                    player.discardCard(player.getCards().get(card1-1));
                    player.discardCard(player.getCards().get(card2-2));
                    player.takeCard(deck.remove(0)); player.takeCard(deck.remove(0));
                }
            } else {
                player.discardCard(player.getCards().get(card1-1));
                player.takeCard(deck.remove(0));
            }
        }
    }

    private void sortCards() {
        for (int i = 0; i < 3; i++) {
            if (card1 > card2) {
                int input = card1;
                card1 = card2; card2 = input;
            }
            if (card2 > card3 && card3 != 0) {
                int input = card2;
                card2 = card3; card3 = input;
            }
        }
    }
    private void initializeDeck() {
        deck = new ArrayList<>(52);
        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new deck(rank, suit));
            }
        }
    }
    
    public static void main(String[] args) { new game().play(); }
}
