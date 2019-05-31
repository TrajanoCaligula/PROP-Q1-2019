/**
 * @author Pau Charques
 */
package GUI;

import javax.swing.*;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class StartView extends JPanel {
    private JButton playButton = new JButton("Play");
    private JPanel playOptions;

    private ButtonGroup player1;
    private ButtonGroup machine1;
    private ButtonGroup player2;
    private ButtonGroup machine2;

    private JRadioButton humanP1 = new JRadioButton("Human");
    private JRadioButton machineP1 = new JRadioButton("Machine");
    private JRadioButton machineP1easy = new JRadioButton("Easy");
    private JRadioButton machineP1hard = new JRadioButton("Hard");
    private JRadioButton machineP2 = new JRadioButton("Machine");
    private JRadioButton machineP2easy = new JRadioButton("Easy");
    private JRadioButton machineP2hard = new JRadioButton("Hard");
    private JComboBox problemsToManage;
    private JTextField nameP1 = new JTextField("...");
    private JTextField nameP2 = new JTextField("...");
    private JRadioButton humanP2 = new JRadioButton("Human");
    private JComboBox problemsMatch;
    private JComboBox problemsRanking;
    private JButton problemsButton = new JButton("Problem Manager");
    private JPanel problemOptions = new JPanel(new GridLayout(0, 1));
    private JButton createNewProblem = new JButton("Create new Problem");
    private JButton modifyExistingProblem = new JButton("Modify");
    private JButton deleteProblem = new JButton("Delete Problem");
    private JButton cloneProblem = new JButton("Clone Problem");


    private JButton rankingsButton = new JButton("Rankings");
    private JPanel rankingPanel = new JPanel();
    private String[] players = new String[2];
    private JPanel scoresRanking = new JPanel(new GridLayout(0, 3));
    private JLabel[][] scores = new JLabel[6][3];
    private JButton startButton;


    public StartView(){

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(480,600));

        JLabel title = new JLabel(new ImageIcon("../PROP4/Chess/FONTS/assets/icons/logo.png"));
        title.setFont(new Font("Serif", Font.PLAIN, 65));
        Border border = title.getBorder();
        Border margin = new EmptyBorder(30,0,10,10);
        title.setBorder(new CompoundBorder(border, margin));

        Font font = new Font("Terminal", Font.BOLD,40);
        playButton.setMargin(new Insets(20, 20, 20, 20));
        playButton.setFont(font);

        this.add(title, BorderLayout.NORTH);

        JPanel botonesInit = new JPanel(new GridLayout(0,2));
        botonesInit.add(playButton);

        playOptions = new JPanel(new BorderLayout());
        startButton = new JButton("Start Match");

        problemsMatch = new JComboBox();
        problemsMatch.setBounds(50, 50,90,20);
        playOptions.add(problemsMatch, BorderLayout.NORTH);
        playOptions.add(startButton, BorderLayout.SOUTH);

        JPanel players = new JPanel(new GridLayout(0,2));

        player1 = new ButtonGroup();
        machine1 = new ButtonGroup();
        machine1.add(machineP1easy);
        machine1.add(machineP1hard);
        player1.add(humanP1);
        player1.add(machineP1);
        JPanel radioPanel1 = new JPanel();
        radioPanel1.setLayout(new GridLayout(3, 0));
        radioPanel1.add(nameP1);
        radioPanel1.add(new JPanel());
        radioPanel1.add(humanP1);
        radioPanel1.add(machineP1);
        radioPanel1.add(machineP1easy);
        radioPanel1.add(machineP1hard);
        machineP1easy.setVisible(false);
        machineP1hard.setVisible(false);

        //... Add a titled border to the button panel.
        radioPanel1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Player 1"));

        players.add(radioPanel1);

        player2 = new ButtonGroup();
        machine2 = new ButtonGroup();
        machine2.add(machineP2easy);
        machine2.add(machineP2hard);
        player2.add(machineP2);
        player2.add(humanP2);
        JPanel radioPanel2 = new JPanel();
        radioPanel2.setLayout(new GridLayout(3, 1));
        radioPanel2.add(nameP2);
        radioPanel2.add(humanP2);
        radioPanel2.add(machineP2);
        radioPanel2.add(machineP2easy);
        radioPanel2.add(machineP2hard);

        machineP2easy.setVisible(false);
        machineP2hard.setVisible(false);

        //... Add a titled border to the button panel.
        radioPanel2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Player 2"));
        players.add(radioPanel2);

        playOptions.add(players, BorderLayout.CENTER);
        playOptions.setVisible(false);
        botonesInit.add(playOptions);

        botonesInit.add(problemsButton);
        problemOptions.add(createNewProblem);
        problemsToManage = new JComboBox();
        problemOptions.add(problemsToManage);
        problemOptions.add(modifyExistingProblem);
        problemOptions.add(cloneProblem);
        problemOptions.add(deleteProblem);
        problemOptions.setVisible(false);

        botonesInit.add(problemOptions);

        botonesInit.add(rankingsButton);
        rankingPanel.setLayout(new BorderLayout());
        problemsRanking = new JComboBox();

        rankingPanel.add(problemsRanking, BorderLayout.NORTH);
        this.problemsRanking.setVisible(false);
        rankingPanel.add(scoresRanking, BorderLayout.CENTER);

        botonesInit.add(rankingPanel);


        this.add(botonesInit, BorderLayout.CENTER);
    }

    public void setProblems(ArrayList<String> problems){
        Object[] array = problems.toArray();
        String[] probToSet = new String[problems.size()];
        problemsMatch.removeAllItems();

        for(int i = 0; i < probToSet.length; i++) {
            probToSet[i] = (String) array[i];
            this.problemsMatch.addItem(probToSet[i]);
        }

    }

    public void setProblemsToManage(ArrayList<String> problems){
        Object[] array = problems.toArray();
        String[] probToSet = new String[problems.size()];
        problemsToManage.removeAllItems();

        for(int i = 0; i < probToSet.length; i++) {
            probToSet[i] = (String) array[i];
            this.problemsToManage.addItem(probToSet[i]);
        }

    }

    public void setRankings(ArrayList<String> problemsRanking){
        Object[] array = problemsRanking.toArray();
        String[] probToSet = new String[problemsRanking.size()];
        this.problemsRanking.removeAllItems();

        for(int i = 0; i < probToSet.length; i++) {
            probToSet[i] = (String) array[i];
            this.problemsRanking.addItem(probToSet[i]);
        }
    }

    public void showScores(ArrayList<String> scoresToAdd){
        resetScores();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 3; j++){
                JLabel currentLabel = new JLabel(" ");
                currentLabel.setBorder(BorderFactory.createLineBorder(new Color(31, 31, 31)));
                scores[i][j] = currentLabel;
                scoresRanking.add(scores[i][j]);
            }
        }

        for(int i = 1; i < 6; i++){
            scores[i][0].setText(String.valueOf(i));
        }
        scores[0][0].setText("          Position");
        scores[0][1].setText("          Play");
        scores[0][2].setText("          Score");


        for(int i = 0; i < scoresToAdd.size(); i++){
            String[] splitted = scoresToAdd.get(i).split("\\s");
            scores[i+1][1].setText(splitted[0]);
            scores[i+1][2].setText(splitted[1]);
        }

    }

    public void resetScores(){
        scoresRanking.removeAll();
    }

    public void showPlayOptions(ArrayList<String> problems){
        this.setProblems(problems);
        this.playOptions.setVisible(!this.playOptions.isVisible());
        this.playButton.setVisible(!this.playOptions.isVisible());
        this.problemOptions.setVisible(false);
        this.problemsButton.setVisible(true);
    }

    public void showRankingOptions(ArrayList<String> rankings){
        this.problemsRanking.setVisible(false);
        this.problemsRanking.setVisible(true);
        this.setRankings(rankings);
    }

    public void addActionListenerChess(ActionListener mal) {

        playButton.setActionCommand(Actions.PLAY.name());
        playButton.addActionListener(mal);

        startButton.setActionCommand(Actions.START.name());
        startButton.addActionListener(mal);

        problemsButton.setActionCommand(Actions.PROBLEM_MANAGER.name());
        problemsButton.addActionListener(mal);

        rankingsButton.setActionCommand(Actions.RANKING.name());
        rankingsButton.addActionListener(mal);

        machineP1.setActionCommand(Actions.DIFFICULTY1.name());
        machineP1.addActionListener(mal);

        machineP2.setActionCommand(Actions.DIFFICULTY2.name());
        machineP2.addActionListener(mal);

        createNewProblem.setActionCommand(Actions.NEW_PROBLEM.name());
        createNewProblem.addActionListener(mal);

        modifyExistingProblem.setActionCommand(Actions.MODIFY_PROBLEM.name());
        modifyExistingProblem.addActionListener(mal);

        problemsRanking.setActionCommand(Actions.SCORES.name());
        problemsRanking.addActionListener(mal);

        modifyExistingProblem.setActionCommand(Actions.MODIFY_PROBLEM.name());
        modifyExistingProblem.addActionListener(mal);

        deleteProblem.setActionCommand(Actions.DELETE_PROBLEM.name());
        deleteProblem.addActionListener(mal);

        cloneProblem.setActionCommand(Actions.CLONE_PROBLEM.name());
        cloneProblem.addActionListener(mal);


    }

    public Integer getPlayer1Type(){
        for (Enumeration<AbstractButton> buttons = player1.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                if(button.getText().equals("Machine")){
                    for (Enumeration<AbstractButton> machines = machine1.getElements(); machines.hasMoreElements();) {
                        AbstractButton machineButton = machines.nextElement();
                        if (machineButton.isSelected()) {
                            if(machineButton.getText().equals("Easy")){
                                return 1;
                            } else {
                                return 2;
                            }
                        }
                    }
                } else {
                    return 0;
                }
            }
        }
        return null;
    }

    public String getPlayer1Name(){
        return nameP1.getText();
    }


    public Integer getPlayer2Type(){

        for (Enumeration<AbstractButton> buttons = player2.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                if(button.getText().equals("Machine")){
                    for (Enumeration<AbstractButton> machines = machine2.getElements(); machines.hasMoreElements();) {
                        AbstractButton machineButton = machines.nextElement();
                        if (machineButton.isSelected()) {
                            if(machineButton.getText().equals("Easy")){
                                return 1;
                            } else {
                                return 2;
                            }
                        }
                    }
                } else {
                    return 0;
                }
            }
        }
        return null;
    }

    public String getPlayer2Name(){
        return nameP2.getText();
    }

    public String getidProblem(){
        return (String) problemsMatch.getSelectedItem();
    }

    public String getIdRanking(){ return (String) problemsRanking.getSelectedItem(); }

    public String getIdProblemToManage() {
            return (String) problemsToManage.getSelectedItem();
    }
    public void showDifficulty1(){
        this.machineP1easy.setVisible(true);
        this.machineP1hard.setVisible(true);
    }

    public void showDifficulty2(){
        this.machineP2easy.setVisible(true);
        this.machineP2hard.setVisible(true);
    }


    public void showProblemOptions(ArrayList<String> problems){
        this.setProblemsToManage(problems);
        this.problemOptions.setVisible(true);
        this.problemsButton.setVisible(false);
        this.playOptions.setVisible(false);
        this.playButton.setVisible(true);
    }

}
