package GUI;

import Controllers.CtrlDomain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class ViewController{
    private ChessView view;
    private CtrlDomain domainController;
    private boolean matchStarted = false;
    private boolean humanMoves = false;

    public ViewController(ChessView currentView) throws IOException {
        this.view = currentView;
        domainController = CtrlDomain.getInstance();

        this.view.addActionListenerTiles(new ActionListenerChess());
        this.view.setProblems(domainController.listProblems());
    }


    public void startMatch() throws IOException {
        String player1 = view.menuCard.getPlayer1Name();
        String player2 = view.menuCard.getPlayer2Name();


        Integer player1type = view.menuCard.getPlayer1Type();
        Integer player2type = view.menuCard.getPlayer2Type();


        if(player1type == null || player2type == null){
            //displayJOptionPane! error player not selected
        }

        String prob = view.menuCard.getidProblem();
        String[] splitted = prob.split("-");
        splitted = splitted[1].split("\\s");
        int idPr = Integer.parseInt(splitted[0]);

        String matchFEN = domainController.getFENFromId(idPr);
        splitted = matchFEN.split("\\s");
        domainController.newGameComplete(idPr, player1, player1type, 1, player2, player2type, 2);
        matchStarted = true;
        view.startMatch(splitted[0]);
        play();

    }


    public void play(){

        if((domainController.getTurn()%2) != 0){
            if(domainController.getPlayer1Type() != 0){
                humanMoves = false;
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                play();
            } else {
                humanMoves = true;
            }
        } else {
            if(domainController.getPlayer2Type() != 0){
                humanMoves = false;
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                play();
            } else {
                humanMoves = true;
            }
        }
    }


    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals(Actions.PLAY.name())) {
                view.menuCard.showPlayOptions();
            } else if (evt.getActionCommand().equals(Actions.START.name())) {
                try {
                    startMatch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.RANKING.name())) {
                String idP = view.menuCard.getSelectedItem();
                ArrayList<String> scores = new ArrayList<>();
                try {
                    scores = domainController.topScores(Integer.parseInt(idP));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.menuCard.showScores(scores);
            } else if(evt.getActionCommand().equals(Actions.PROBLEM_MANAGER.name())){
                view.menuCard.showProblemOptions();
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY1.name())) {
                view.menuCard.showDifficulty1();
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY2.name())) {
                view.menuCard.showDifficulty2();
            }
            if(matchStarted) {
                if (humanMoves) {
                    if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                        if (view.matchCard.tileAction((Tile) evt.getSource())) {
                            String currentFEN = domainController.makeMove(view.matchCard.getTilesInMove()[0], view.matchCard.getTilesInMove()[1]);
                            view.matchCard.updateBoard(currentFEN);
                            play();
                        }
                    }
                }
            }
        }
    }


}