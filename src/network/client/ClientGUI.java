package network.client;

import main.GameScene;
import network.entitiesNet.PlayerMP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
/*
 * ClientGUI.java
 *
 * Created on 21 „«—”, 2008, 02:26 „
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * @author Mohamed Talaat Saad
 */
public class ClientGUI extends JFrame implements ActionListener, WindowListener {

    /**
     * Creates a new instance of ClientGUI
     */
    private JLabel ipaddressLabel;
    private JLabel portLabel;
    private static JLabel scoreLabel;

    private JTextField ipaddressText;
    private JTextField portText;

    private JButton registerButton;


    private JPanel registerPanel;
    public static JPanel gameStatusPanel;
    private Client client;
    private PlayerMP clientPlayer;

    private static int score;

    int width = 790, height = 580;
    boolean isRunning = true;
    private GameScene boardPanel;

    public ClientGUI() {
        score = 0;
        setTitle("Multiclients Players Game");
        setSize(width, height);
        setLocation(60, 100);
        getContentPane().setBackground(Color.BLACK);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addWindowListener(this);

        registerPanel = new JPanel();
        registerPanel.setBackground(Color.YELLOW);
        registerPanel.setSize(200, 140);
        registerPanel.setBounds(560, 50, 200, 140);
        registerPanel.setLayout(null);

        gameStatusPanel = new JPanel();
        gameStatusPanel.setBackground(Color.YELLOW);
        gameStatusPanel.setSize(200, 300);
        gameStatusPanel.setBounds(560, 210, 200, 311);
        gameStatusPanel.setLayout(null);

        ipaddressLabel = new JLabel("IP address: ");
        ipaddressLabel.setBounds(10, 25, 70, 25);

        portLabel = new JLabel("Port: ");
        portLabel.setBounds(10, 55, 50, 25);

        scoreLabel = new JLabel("Score : 0");
        scoreLabel.setBounds(10, 90, 100, 25);

        ipaddressText = new JTextField("localhost");
        ipaddressText.setBounds(90, 25, 100, 25);

        portText = new JTextField("11111");
        portText.setBounds(90, 55, 100, 25);

        registerButton = new JButton("Register");
        registerButton.setBounds(60, 100, 90, 25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(true);


        registerPanel.add(ipaddressLabel);
        registerPanel.add(portLabel);
        registerPanel.add(ipaddressText);
        registerPanel.add(portText);
        registerPanel.add(registerButton);

        gameStatusPanel.add(scoreLabel);


        client = Client.getGameClient();

        boardPanel = new GameScene(true);
        clientPlayer = boardPanel.getPlayerMP();

        actionRegister();

//        getContentPane().add(registerPanel);
//        getContentPane().add(gameStatusPanel);
        add(boardPanel);
        setVisible(true);

    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int scoreParametar) {
        score += scoreParametar;
        scoreLabel.setText("Score : " + score);
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == registerButton) {
            actionRegister();
        }

    }
    public void actionRegister() {
        registerButton.setEnabled(false);

        try {
            client.register(clientPlayer.getX(), clientPlayer.getY());
            boardPanel.setRunning(true);

            boardPanel.start();
            boardPanel.validate();
            boardPanel.repaint();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
            new ClientRecivingThread(client.getSocket(), clientPlayer, boardPanel).start();
            registerButton.setFocusable(false);
            boardPanel.setFocusable(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "The Server is not running, try again later!", "Players 2D Multiplayer Game", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("The Server is not running!");
            registerButton.setEnabled(true);
        }
    }

    public void setVisibility(boolean isVisible) {
        setVisible(isVisible);
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {

        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit ?", "2D Multiplayer Game!", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientPlayer.getID()));
            System.exit(0);
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }


    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

}
