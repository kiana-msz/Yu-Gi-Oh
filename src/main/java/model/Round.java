package model;

public class Round {
    private User winner;
    private User loser;
    private int winnerLifePoint;
    private int loserLifePoint;

    public Round(User winner, User loser, int winnerLifePoint, int loserLifePoint) {
        this.winner = winner;
        this.loser = loser;
        this.winnerLifePoint = winnerLifePoint;
        this.loserLifePoint = loserLifePoint;
    }

    public User getWinner() {
        return this.winner;
    }

    public int getLifePointByUser(User user) {
        if(this.winner.equals(user)) return this.winnerLifePoint;
        else return this.loserLifePoint;
    }
}
