package GUI;

import javax.swing.*;
import java.awt.*;
import Jaume.*;
import java.io.File;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class StartView extends JPanel {
    private JButton playButton = new JButton("Play");
    private JPanel playOptions;

    private JRadioButton humanP1 = new JRadioButton("Human");
    private JRadioButton machineP1 = new JRadioButton("Machine");
    private JRadioButton machineP2 = new JRadioButton("Machine");
    private JTextField nameP1 = new JTextField("Introduce player's name");
    private JTextField nameP2 = new JTextField("Introduce player's name");
    private JRadioButton humanP2 = new JRadioButton("Human");
    private JComboBox problemsMatch;
    private JComboBox problemsRanking;
    private JButton problemsButton = new JButton("Problems");
    private JPanel problems = new JPanel();
    private JButton rankingsButton = new JButton("Rankings");
    private JPanel rankingPanel = new JPanel();
    private String[] players = new String[2];
    JPanel scoresRanking = new JPanel(new GridLayout(0,1));
    private JButton startButton;


    public StartView(){

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600,700));

        JLabel title = new JLabel(new ImageIcon("../PROP4/Chess/FONTS/assets/icons/logo.png"));
        title.setFont(new Font("Serif", Font.PLAIN, 65));
        Border border = title.getBorder();
        Border margin = new EmptyBorder(30,0,10,10);
        title.setBorder(new CompoundBorder(border, margin));

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

        ButtonGroup player1 = new ButtonGroup();
        player1.add(machineP1);
        player1.add(humanP1);
        JPanel radioPanel1 = new JPanel();
        radioPanel1.setLayout(new GridLayout(3, 1));
        radioPanel1.add(machineP1);
        radioPanel1.add(humanP1);
        radioPanel1.add(nameP1);
        nameP1.setVisible(false);
        //... Add a titled border to the button panel.
        radioPanel1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Player 1"));

        players.add(radioPanel1);

        ButtonGroup player2 = new ButtonGroup();
        player2.add(machineP2);
        player2.add(humanP2);
        JPanel radioPanel2 = new JPanel();
        radioPanel2.setLayout(new GridLayout(3, 1));
        radioPanel2.add(machineP2);
        radioPanel2.add(humanP2);
        radioPanel2.add(nameP2);
        nameP2.setVisible(false);
        //... Add a titled border to the button panel.
        radioPanel2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Player 2"));
        players.add(radioPanel2);

        playOptions.add(players, BorderLayout.CENTER);
        playOptions.setVisible(false);
        botonesInit.add(playOptions);

        botonesInit.add(problemsButton);
        botonesInit.add(problems);

        botonesInit.add(rankingsButton);
        rankingPanel.setLayout(new BorderLayout());
        problemsRanking = new JComboBox();

        rankingPanel.add(problemsRanking, BorderLayout.NORTH);
        rankingPanel.add(scoresRanking, BorderLayout.CENTER);

        botonesInit.add(rankingPanel);


        this.add(botonesInit, BorderLayout.CENTER);
    }

    public void setProblems(ArrayList<String> problems){
        /*
        Object[] array = problems.toArray();
        String[] probToSet = new String[problems.size()];

        for(int i = 0; i < probToSet.length; i++) {
            probToSet[i] = (String) array[i];
            this.problemsMatch.addItem(probToSet[i]);
            this.problemsRanking.addItem(probToSet[i]);

        }
        */
    }

    public void showScores(ArrayList<String> scores){
        //scoresRanking = new JPanel(new GridLayout(0,1));
        for(int i = 0; i < (scores.size() - 1); i++){
            scoresRanking.add(new JLabel(scores.get(i) + " " + scores.get(i+1)));
            i++;
        }
    }

    public String  getSelectedItem(){
        String idProblemRanking = (String) problemsRanking.getSelectedItem();
        String[] splitted = idProblemRanking.split("\\s");
        return splitted[0];
    }

    public void showPlayOptions(){
        this.playOptions.setVisible(!this.playOptions.isVisible());
        this.playButton.setVisible(!this.playOptions.isVisible());
    }


    public void addActionListenerChess(ActionListener mal) {

        playButton.setActionCommand(Actions.PLAY.name());
        playButton.addActionListener(mal);

        startButton.setActionCommand(Actions.START.name());
        startButton.addActionListener(mal);

        problemsRanking.setActionCommand(Actions.RANKING.name());
        problemsRanking.addActionListener(mal);

        humanP1.setActionCommand(Actions.NAME.name());
        humanP1.addActionListener(mal);

        humanP2.setActionCommand(Actions.NAME.name());
        humanP2.addActionListener(mal);


    }

    public String[] getPlayers(){
        return players;
    }

    public int getidProblem(){
        int idProblem = (int) problemsMatch.getSelectedItem();
        return idProblem;
    }


    public void showInputPlayer(String player){
        if(player.equals("Player1")){
            this.nameP1.setVisible(true);
        } else {
            this.nameP2.setVisible(true);
        }
    }

    public String getP1id(){
        return humanP1.getUIClassID();
    }

}
