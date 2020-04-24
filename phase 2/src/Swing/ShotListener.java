package Swing;

import main.Main;
import physics.SimulateMain;
import physics.Tools;
import physics.Vector2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShotListener implements ActionListener{

    private JTextField speed;
    private JTextField angle;
    private JFrame theFrame;

    public ShotListener(JTextField textfield1, JTextField textfield2, JFrame frame){
        speed = textfield1;
        angle = textfield2;
        theFrame = frame;
    }


    public void actionPerformed(ActionEvent event){
        try {
            String speedfield = speed.getText();
            String anglefield = angle.getText();
            double speedShot = Double.parseDouble(speedfield);
            double angleShot = Double.parseDouble(anglefield);
            SimulateMain.simulator.take_shot(Tools.velFromAngle(angleShot, speedShot));
            Main.takingShot = true;
            theFrame.dispose();



            //System.out.println("speed: " + speedShot + " and angle " + angleShot);
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(theFrame, "Fill all the fields");
        }





    }




}
