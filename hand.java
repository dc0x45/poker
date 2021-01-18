import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hand {
    private ArrayList<deck> cards;

    public hand() { cards = new ArrayList<>(); }

    public List<String> getHand() {
        List<deck> temp = cards;
        List<String> returns = new ArrayList<>();
        for(deck i : temp) {
            returns.add(i.toString());
        }
        return returns;
    }

    public ArrayList<deck> getCards() {
        return cards;
    }

    public void takeCard(deck card) {
        cards.add(card);
    }

    public void discardCard(deck card) {
        cards.remove(card);
    }

    public boolean rFlushC() {
        String suits = "TJQKA";
        ArrayList<deck> temp = cards;
        String cSuit = temp.get(0).getSuit();
        for(deck i : temp) {
            if (suits.contains(i.getRank()) && i.getSuit().equals(cSuit)) {
                suits.replace(i.getRank(), "");
            }
        }
        if(suits.equals("")) {
            return true;
        }
        return false;
    }

    public boolean straightFC() {
        int[] temp = new int[5];
        int count = 0;
        String suit = cards.get(0).getSuit();
        boolean suitC = true;

        for(deck i : cards) {
            try {
                int number = Integer.parseInt(i.getRank());

                temp[count] = number;
            } catch (NumberFormatException e) {
                int number = i.getOrderedRank(i.getRank());

                temp[count] = number;
            }
            count++;
        }

        for(deck i : cards) {
            if (!i.getSuit().equals(suit)) {
                suitC = false;
                break;
            }
        }

        Arrays.sort(temp);

        if(temp[0]+1 == temp[1] &&
                temp[1]+1 == temp[2] &&
                temp[2]+1 == temp[3] &&
                temp[3]+1 == temp[4] &&
                suitC == true) {
            return true;
        }
        return false;
    }

    public boolean foakC() {
        String[] temp = new String[5];
        int count = 0;

        for(deck i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) && temp[0].equals(temp[2]) && temp[0].equals(temp[3]) || temp[1].equals(temp[2]) && temp[1].equals(temp[3]) && temp[1].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean houseC() {
        String[] temp = new String[5];
        int count = 0;

        for(deck i : cards) {
            temp[count] = i.getRank();
            count++;
        }
        Arrays.sort(temp);
        if (temp[0].equals(temp[1]) && temp[2].equals(temp[3]) && temp[2].equals(temp[4]) || temp[0].equals(temp[1]) && temp[0].equals(temp[2]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean flushC() {
        ArrayList<deck> temp = cards;
        String suit = temp.get(0).getSuit();
        for(deck i : temp) {
            if (!suit.equals(i.getSuit())) {
                return false;
            }
        }
        return true;
    }

    public boolean straightC() {
        int[] temp = new int[5];
        int count = 0;
        for(deck i : cards) {
            try {
                int number = Integer.parseInt(i.getRank());
                temp[count] = number;
            } catch (NumberFormatException e) {
                int number = i.getOrderedRank(i.getRank());
                temp[count] = number;
            }
            count++;
        }
        Arrays.sort(temp);
        if(temp[0]+1 == temp[1] && temp[1]+1 == temp[2] && temp[2]+1 == temp[3] && temp[3]+1 == temp[4]) {
            return true;
        }
        return false;
    }

    public boolean toakC() {
        String[] temp = new String[5];
        int count = 0;

        for(deck i : cards) {
            temp[count] = i.getRank(); count++;
        }
        Arrays.sort(temp);
        if (temp[0].equals(temp[1]) && temp[0].equals(temp[2]) || temp[1].equals(temp[2]) && temp[1].equals(temp[3]) || temp[2].equals(temp[3]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean tPairC() {
        String[] temp = new String[5]; int count = 0;
        for(deck i : cards) {
            temp[count] = i.getRank(); count++;
        }
        Arrays.sort(temp);
        if (temp[0].equals(temp[1]) && temp[2].equals(temp[3]) || temp[0].equals(temp[1]) && temp[3].equals(temp[4]) || temp[1].equals(temp[2]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean pairC() {
        String[] temp = new String[5];
        int count = 0;
        for(deck i : cards) {
            temp[count] = i.getRank();
            count++;
        }
        Arrays.sort(temp);
        if (temp[0].equals(temp[1]) || temp[1].equals(temp[2]) ||temp[2].equals(temp[3]) || temp[3].equals(temp[4])) {
            return true;
        } 
        return false;
    }

    public void sortHand() {
        List<deck> temp = cards;
        temp.sort((a, b) -> {
            if (deck.getOrderedRank(a.getRank()) == deck.getOrderedRank(b.getRank())) {
                return deck.getOrderedSuit(a.getSuit()) - deck.getOrderedSuit(b.getSuit());     
            }                                                                                   
            return deck.getOrderedRank(a.getRank()) - deck.getOrderedRank(b.getRank());
        });
    }
}
