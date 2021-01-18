public class deck {

    private String rank;
    private String suit;

    public deck(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + suit;     
    }

    public static int getOrderedRank(String rank) {
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            switch (rank) {
                case "Q": 
                    return 13;    
                case "K": 
                    return 14;    
                case "A": 
                    return 15;
                case "T": 
                    return 11;    
                case "J":   
                    return 12;   
                default:
                    return -1; 
            }
        }
    }

    public static int getOrderedSuit(String suit) {
        switch (suit) {
            case "C": 
                return 1;   
            case "D": 
                return 2;
            case "H": 
                return 3;
            case "S": 
                return 4;
            default: 
                return -1;
        }
    }

    public deck getCardByRank(String rank) {
        if (rank == null) return null;
        if (rank.length() != 1) return null;
        if (rank.equals("T") || rank.equals("J") ||rank.equals("Q") || rank.equals("K") || rank.equals("A")) {
            return new deck(rank, null);
        } else {
            try {
                int number = Integer.parseInt(rank);

                if (number > 1 && number < 10) {
                    return new deck(rank, null);
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }
}