package Pau;

import Calin.Human;

public class Score implements Comparable<Score> {
    private Human player;
    private int score;
    //Constructors

    public Score(Human player, int puntuacioPartida){
        this.player = player;
        this.score = puntuacioPartida;
    }

    public int getScore(){
        return this.score;
    }

    public Human getPlayer(){ return player; }

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