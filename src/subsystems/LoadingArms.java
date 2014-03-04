package subsystems;

import main.Main;

public class LoadingArms {

    //Processes
    public boolean LoweringFront = false, ClosingFront = false, LoadingFront;

    int iFrontLoadingMotor;
    int iFrontArmMotor;

    private Main main;
    
    public LoadingArms(Main add) {
        main = add;
    }
    
    /*////////////////////////////////////////////////////////////////////////////
        
     TO TEST
     -------
     START WITH MEDIUM SENSOR ENABLED
     NOTHING SHOULD MOVE
     FLICK THE STICK FORWARD
     BOTH MOTORS SHOULD SPIN
     DISABLE THE MEDIUM SENSOR AND ENABLE THE OPEN SENSOR
     THE ARM MOTOR SHOULD STOP
     LET GO OF THE STICK AND DISABLE THE OPEN SENSOR
     THE LOADING MOTOR SHOULD STOP AND THE ARM MOTOR SHOULD SPIN BACKWARDS
     ENABLE THE MEDIUM SENSOR
     THE ARM MOTOR SHOULD STOP
        
     /////////////////////////////////////////////////////////////////////////////*/
    
    public int getIFrontLoadingMotor() {
        return iFrontLoadingMotor;
    }

    public int getIFrontArmMotor() {
        return iFrontArmMotor;
    }

    public void loadFront(double mainRightY, boolean FrontArmOpenSensor, boolean FrontArmMediumSensor, boolean AButton, boolean YButton) 
    {

        if (mainRightY < -0.5) 
        {

            iFrontLoadingMotor = 250;
            if (FrontArmOpenSensor)
                iFrontArmMotor = 95;
            else {
                iFrontArmMotor = 127;
            }
        }

        if (mainRightY > 0.5) 
        {
                iFrontLoadingMotor = 250;
                if (FrontArmMediumSensor || !FrontArmMediumSensor && !FrontArmOpenSensor)
                    iFrontArmMotor = 170;
                else
                    iFrontArmMotor = 127;
        } 
        if (mainRightY >= -0.5 && mainRightY <= 0.5) {
            if (AButton){
                iFrontLoadingMotor = 250;
            }
            else if (YButton){
                iFrontLoadingMotor = 10;
            }
            else
            {
                iFrontLoadingMotor = 127;
            }
            iFrontArmMotor = 127;
        }
        /*////////////////////////////////////////////////////////////
        If we want to assign a button to control the loading motor
        //////////////////////////////////////////////////////////////
        
        /*if (AButton)
            iFrontLoadingMotor = 200;
        else
            iFrontLoadingMotor = 127;
        ////////////////////////////////////////////////////////////*/
    }
}
