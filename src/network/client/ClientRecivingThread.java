package network.client;

import main.GameScene;
import network.entitiesNet.PlayerMP;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRecivingThread extends Thread
{
    private Socket clientSocket;
    private DataInputStream reader;
    private PlayerMP clientPlayer;
    private GameScene boardPanel;
    boolean isRunning=true;
//    private ClientGUI clientGUI;
    public ClientRecivingThread(Socket clientSocket,PlayerMP clientPlayer,GameScene boardPanel)
    {
        this.clientSocket=clientSocket;
        this.clientPlayer=clientPlayer;
        this.boardPanel=boardPanel;
        try {
            reader=new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void run()
    {
        while(isRunning)
        {
            String sentence="";
            try {
                sentence=reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(sentence.startsWith("ID"))
            {
                int id=Integer.parseInt(sentence.substring(2));
                clientPlayer.setID(id);
                System.out.println("My ID= "+id);

            }
            else if(sentence.startsWith("NewClient"))
            {
                int pos1=sentence.indexOf(',');
                int pos2=sentence.indexOf('-');
                int pos3=sentence.indexOf('|');
                int x=Integer.parseInt(sentence.substring(9,pos1));
                int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                if(id!= clientPlayer.getID())
                    boardPanel.registerNewPlayer(new PlayerMP(x,y,dir,id));
                System.out.println("New Client ID= "+id);
            }
            else if(sentence.startsWith("Update"))
            {
                int pos1=sentence.indexOf(',');
                int pos2=sentence.indexOf('-');
                int pos3=sentence.indexOf('|');
                int x=Integer.parseInt(sentence.substring(6,pos1));
                int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));

                if(id!= clientPlayer.getID())
                {
                    boardPanel.getPlayer(id).setX(x);
                    boardPanel.getPlayer(id).setY(y);
                    boardPanel.getPlayer(id).setDirection(dir);
                    boardPanel.repaint();
                }

            }
            else if(sentence.startsWith("Shot"))
            {
                int id=Integer.parseInt(sentence.substring(4));

                if(id!= clientPlayer.getID())
                {
                    System.out.println("Shot from "+id);
                }

            }
            else if(sentence.startsWith("Remove"))
            {
                int id=Integer.parseInt(sentence.substring(6));

                if(id== clientPlayer.getID())
                {
                    int response= JOptionPane.showConfirmDialog(null,"Sorry, You are loss. Do you want to try again ?","Players 2D Multiplayer Game",JOptionPane.OK_CANCEL_OPTION);
                    if(response==JOptionPane.OK_OPTION)
                    {
                        //client.closeAll();
//                        clientGUI.setVisibility(false);
//                        clientGUI.dispose();

                        new ClientGUI();
                    }
                    else
                    {
                        System.exit(0);
                    }
                }
                else
                {
                    boardPanel.removePlayer(id);
                }
            }
            else if(sentence.startsWith("Exit"))
            {
                int id=Integer.parseInt(sentence.substring(4));

                if(id!= clientPlayer.getID())
                {
                    boardPanel.removePlayer(id);
                }
            }

        }

        try {
            reader.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
