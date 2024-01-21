package SGame;
import javax.swing.*;
public class SnakeGame extends JFrame{
    SnakeGame()
    {
        super("Snake Game");//first step of constructor used to call parent class constructor
        add(new board());
        pack();//used to refresh the frames in order to inculcate new changes

        setResizable(false);
        setLocationRelativeTo(null);//frame in center

    }
    public static void main(String []args)
    {
        new SnakeGame().setVisible(true);//one time
    }
}
