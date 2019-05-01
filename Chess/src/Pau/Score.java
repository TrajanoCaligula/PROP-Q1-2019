package model;

public class Score implements Comparable<Score> {
    private String player;
    private int score;
    //Constructors

    public Score(String playerScore, int puntuacioPartida){
        this.player = playerScore;
        this.score = puntuacioPartida;
    }

    public int getScore(){
        return this.score;
    }

    public String getPlayer(){ return player; }

    @Override
    public int compareTo(Score score2){

        /* For Ascending order*/
        return score2.getScore() - this.score;
    }

    @Override
    public String toString(){
        return "[" + this.getPlayer() + ", " + this.getScore() + "]";
    }

}