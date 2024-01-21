package SGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class board extends JPanel implements ActionListener { ///Jpanel dividing screen into components//jpanel is child of Jframe

    //capture actions while pressing the keys
    private int dots;
    private Image apple;
    private Image dot;
    private Image head;
    private final int ALLDOTS=900;
    private final int DOTSize=10;
    private final int RANDOM_POSITION=29;
    private int apple_x;
    private int apple_y;
    private final int x[]=new int[ALLDOTS];
    private final int y[]=new int[ALLDOTS];
    private Timer timer;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean rightDirection=true;
    private boolean leftDirection=false;
    private boolean inGame=true;

    board()
    {
        addKeyListener(new TAdapter());//actionListener interface ka class h
        setBackground(Color.BLACK); //black frame
        setFocusable(true);
        setPreferredSize(new Dimension(300,300));
        loadImages();//without images game can not initialise
        GameBegin();
    }
    public void loadImages()
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("SGame/icons/apple.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("SGame/icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("SGame/icons/head.png"));
        head=i3.getImage();

    }
    public void GameBegin()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]=50-i*DOTSize;
        }
        locateApple();

        timer=new Timer(140,this);
        timer.start();
    }
    public void locateApple()
    {
        int r=(int)(Math.random() * RANDOM_POSITION);
        apple_x=r *DOTSize;
         r=(int)(Math.random() * RANDOM_POSITION);
        apple_y=r *DOTSize;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)//draw only if snake is alive or over it
    {
        if(inGame)
        {
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<dots;i++)
            {
                if(i==0)
                {
                    g.drawImage(head,x[i],y[i],this);
                }
                else {
                    g.drawImage(dot,x[i],y[i],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameOver(g);
        }
    }
    public void gameOver(Graphics g)
    {
        String msg="Game Over!";
        Font font=new Font("SAN SERTF",Font.BOLD,14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,((300-metrices.stringWidth(msg))/2),300/2);
    }
    public void move()
    {
        for(int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]=x[0]-DOTSize;
        }

        if(rightDirection)
        {
            x[0]=x[0]+DOTSize;
        }
        if(upDirection)
        {
            y[0]=y[0]-DOTSize;
        }
        if(downDirection)
        {
            y[0]=y[0]+DOTSize;
        }

    }
    public void checkApple()
    {
        if((x[0] == apple_x) && (y[0] == apple_y))
        {
            dots++;
            locateApple();
        }
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--)
        {
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
            {
                inGame=false;
            }
        }
        if(y[0]>=300)
        {
            inGame=false;
        }
        if(x[0]>=300)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(x[0]<0)
        {
            inGame=false;
        }
        if(!inGame)
        {
            timer.stop();
        }
    }
    public void actionPerformed(ActionEvent ae)//after 140milliseconds this function is always calles
    {
        if(inGame)
        {
            checkApple();
            checkCollision();
            move();
        }

        repaint();//same function as pack
    }
    public class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection))
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection))
            {
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection))
            {
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
}
