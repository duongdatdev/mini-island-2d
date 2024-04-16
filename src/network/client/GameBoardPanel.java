package network.client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * This class is responsible for the game board panel
 *
 * @author DuongDat
 */
public class GameBoardPanel extends JPanel {
    
    /** Creates a new instance of GameBoardPanel */
    private Player player;
    private int width=609;
    private int height=523;
    private static ArrayList<Player> players;
    private boolean gameStatus;
    public GameBoardPanel(Player player, boolean gameStatus)
    {
        this.player = player;
        this.gameStatus=gameStatus;
        setSize(width,height);
        setBounds(-50,0,width,height);
        addKeyListener(new InputManager(player));
        setFocusable(true);
        
        players =new ArrayList<Player>(100);
        
        for(int i=0;i<100;i++)
        {
            players.add(null);
        }
   
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g=(Graphics2D)gr;
 
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(),getHeight());
        
        g.setColor(Color.GREEN);
        g.fillRect(70,50, getWidth()-100,getHeight());
        g.drawImage(new ImageIcon("Images/bg.jpg").getImage(),70,50,null);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        g.drawString("Tanks 2D Multiplayers Game",255,30);
        if(gameStatus)
        {
            g.drawImage(player.getBuffImage(), player.getXposition(), player.getYposition(),this);
            int i=1;
            while (i< players.size()) {
                if(players.get(i)!=null)
                    g.drawImage(players.get(i).getBuffImage(), players.get(i).getXposition(), players.get(i).getYposition(),this);
                i++;
            }

        }

        repaint();
    }

    public void registerNewTank(Player newPlayer)
    {
        players.set(newPlayer.getTankID(), newPlayer);
    }
    public void removeTank(int tankID)
    {
        players.set(tankID,null);
    }
    public Player getTank(int id)
    {
        return players.get(id);
    }
    public void setGameStatus(boolean status)
    {
        gameStatus=status;
    }
  
    public static ArrayList<Player> getClients()
    {
        return players;
    }
}
