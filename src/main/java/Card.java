public class Card {
    public String suite;

    public int value; // = 100;

    public String name;


    public boolean isFaceCard = false;

    public boolean isAce = false;

    Card(String theSuite, int theValue) {
        this.suite = theSuite;
        this.value = theValue;
        switch(theValue) {
            case 2:
                this.name = "2";
                break;
            case 3:
                this.name = "3";
                break;
            case 4:
                this.name = "4";
                break;
            case 5:
                this.name = "5";
                break;
            case 6:
                this.name = "6";
                break;
            case 7:
                this.name = "7";
                break;
            case 8:
                this.name = "8";
                break;
            case 9:
                this.name = "9";
                break;
            case 10:
                this.name = "10";
                break;
            case 11:
                this.name = "Jack";
                break;
            case 12:
                this.name = "Queen";
                break;
            case 13:
                this.name = "King";
                break;
            case 14:
                this.name = "Ace";
                break;
        }
        // theValue is 10, 11, 12, or 13 (10, Jack, Queen, or King)
        if (theValue > 9 && theValue < 14) {
            isFaceCard = true;
        }
        // the Value is 14 (Ace)
        if (theValue == 14) {
            isAce = true;
        }
    }

}
