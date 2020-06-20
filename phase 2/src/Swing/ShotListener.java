package Swing;

import main.Main;
import physics.SimulateMain;
import physics.Tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class ShotListener implements ActionListener{

    private JTextField speed;
    private JTextField angle;
    private JFrame theFrame;

    /**
     *
     * @param textfield1
     * @param textfield2
     * @param frame
     */
    public ShotListener(JTextField textfield1, JTextField textfield2, JFrame frame){
        speed = textfield1;
        angle = textfield2;
        theFrame = frame;
    }

    // reads textfields from manual shot menu and send information of new shot to simulator

    /**
     *
     * @param event
     */
    public void actionPerformed(ActionEvent event){
        try {
            String speedfield = speed.getText();
            String anglefield = angle.getText();
            double speedShot = Double.parseDouble(speedfield);
            double angleShot = Double.parseDouble(anglefield);
            if(Math.abs(speedShot) > Math.abs(SimulateMain.getSpeed())){
                speedShot = SimulateMain.getSpeed();
            }
            SimulateMain.simulator.take_shot(Tools.velFromAngle(angleShot, speedShot), false);
            //SimulateMain.simulator.act_timestep_from_distance();
            Main.takingShot = true;
            theFrame.dispose();
            Main.openNewWindow = true;

        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(theFrame, "Fill all the fields");
        }
    }

}
