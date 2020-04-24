package Swing;

import physics.PuttingSimulator;
import physics.SimulateMain;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

public class ShotMenu {

    private static final int FRAME_WIDTH = 200;
    private static final int FRAME_HEIGHT = 150;

    public static void create(){
        JFrame frame = new JFrame();
        frame.setTitle("SHOT");
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setLocation(400,200);
        JPanel panel = new JPanel();

        JLabel label1 = new JLabel();
        label1.setText("Speed: ");
        panel.add(label1);

        JTextField textfield1 = new JTextField(10);
        panel.add(textfield1);

        JLabel label2 = new JLabel();
        label2.setText(" Angle: ");
        panel.add(label2);

        JTextField textfield2 = new JTextField(10);
        panel.add(textfield2);

        JLabel label3 = new JLabel();
        label3.setText("(Maximum speed = 5)");
        panel.add(label3);

        JButton button = new JButton("APPLY");
        panel.add(button);

        frame.add(panel);


        ActionListener shotListener = new ShotListener(textfield1,textfield2, frame);
        button.addActionListener(shotListener);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}

