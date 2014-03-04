/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package subsystems;
import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.DigitalInput;
import main.Main;
/**
 *
 * @author Mo
 */
public class Calibration {
    DigitalInput bTopPositionSensor = new DigitalInput (2);
    
    private Main main;
    
    public Calibration(Main add) {
        main = add;
    }
    
    public void StartGameCaliration (boolean bUserInput) 
    {
        boolean bCocked = false;
        boolean bUserCockedInput = false;
        
        if (bTopPositionSensor.get() == true)
        {
            bCocked = true;
            System.out.println ("Cocked");
        }
        else
        {
           if (bUserCockedInput == true)
           {
               System.out.println ("Not Cocked");
           }
        }
    }
    
    public void PreGameCalibration (boolean bBallLoaded, boolean bSpotClose, boolean bSpotFar,
            boolean bShotClose, boolean bShotFar)
    {
        boolean bArray [] = new boolean[3];
        Arrays.fill (bArray, false);
        if (bBallLoaded == true)
        {
            if (bSpotClose == true)
            {
                if (bShotClose == true)
                {
                    bArray[1] = true;
                }
            }
            if (bSpotFar == true)
            {
                if (bShotFar == true)
                {
                    bArray[2] = true;
                }
            }
            for (int Loop = 1; Loop < bArray.length; Loop++)
            {
                System.out.println (bArray[Loop]);
            }
        }
    }
}
