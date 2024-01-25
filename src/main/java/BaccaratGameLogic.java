import java.util.ArrayList;
public class BaccaratGameLogic {


    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        // first hand (player) is greater WINNER
        if (handTotal(hand1) > handTotal(hand2)) {
            return "Player";
        }
        // second hand (banker) is greater WINNER
        else if (handTotal(hand1) < handTotal(hand2)) {
            return "Banker";
        }
        // if reach here, the hands are equal
        else {
            return "Draw";
        }
    }



    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        for (int i = 0; i < hand.size(); i++ ) {
            // card at i is faceCard
            if (hand.get(i).isFaceCard) {
                total += 0;
            }
            // card at i is an Ace
            else if (hand.get(i).isAce) {
                total += 1;
            }
            else {
                int cardValue = hand.get(i).value;
                total += cardValue;
            }
        }
        // 10 - 19
        if (total >= 10 && total <= 19) { // 10-10=0, 11-10=1, 12-10=2, etc.
            total -= 10;
        }
        // 20+
        if (total >= 20) {
            total -= 20;
        }
        return total;
    }




    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
        if (hand.size() == 3) { // already has 3 cards
            return false;
        }
        if (handTotal(hand) <= 2) {
            return true; // draw
        }
        // 3 4 5 or 6
        if (handTotal(hand) >= 3 && handTotal(hand) <= 6) {
            // banker 6, player third is 6 or 7
            if (handTotal(hand) == 6) {
                if (playerCard == null) {
                    return false;
                }
                else if (playerCard.value == 6 || playerCard.value == 7) {
                    return true; // draw
                }
                else {
                    return false; // dont draw
                }
            }
            // banker 5, player third is [4,7] or did NOT draw third
            if (handTotal(hand) == 5) {
                if (playerCard == null || (playerCard.value >= 4 && playerCard.value <= 7)) {
                    return true; // draw
                }
                else {
                    return false; // dont draw
                }
            }
            // banker 4, player third is [2,7] or did NOT draw third
            if (handTotal(hand) == 4) {
                if (playerCard == null || (playerCard.value >= 2 && playerCard.value <= 7)) {
                    return true; // draw
                }
                else {
                    return false; // dont draw
                }
            }
            if (handTotal(hand) == 3) {
                if (playerCard == null) {
                    return true;
                }
                else if (playerCard.value == 8) {
                    return false; // dont draw
                }
                else {
                    return true; // draw
                }
            }
        }
        return false; // dont draw, bankers hand >= 7
    }




    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        if (handTotal(hand) <= 5) {
            return true; // have to draw 1 more
        }
        else if (handTotal(hand) == 6 || handTotal(hand) == 7) {
            return false; // dont draw 1 more
        }
//        else if (handTotal(hand) == 8 || handTotal(hand) == 9) {
//            return false; // dont draw 1 more
//        }
        else {
            return false;
        }

    }

}
