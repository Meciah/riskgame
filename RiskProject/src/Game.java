import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Game implements Runnable {

    public void run() {

        final JFrame frame = new JFrame("Final Project: Risk");
        final Start startScreen = new Start();
        frame.add(startScreen);
        startScreen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startScreen.setVisible(false);
                startScreen.setEnabled(false);
                InitializeGame(frame);
            }
        });

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private static void InitializeGame(JFrame frame) {
        final JPanel tPanel = new JPanel();
        final JLabel tInfo = new JLabel();
        tInfo.setFont(new Font("Serif", Font.PLAIN, 20));
        tPanel.add(tInfo);

        final JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(0, 120));


        final Board board = new Board(tInfo);
        frame.add(board, BorderLayout.CENTER);


        final JButton cont = new JButton("Continue");
        cont.setPreferredSize(new Dimension(200, 70));
        cont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.cont();
            }
        });

        statusPanel.add(tPanel);
        statusPanel.add(cont, BorderLayout.EAST);


        frame.add(statusPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }






























    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}

