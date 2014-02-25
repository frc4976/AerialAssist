package subsystems;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

    //DriveTrain driveSystem = new DriveTrain();
    public void runAutonomously(boolean bTopLimitSensor) {
        //NetworkTable cameraInput;
        boolean Seen = false;
        boolean Turn = false;
        long Time = System.currentTimeMillis();
        
        /*System.out.println(cameraInput.getBoolean(""));
        System.out.println(cameraInput.containsKey(""));*/
       
        /*while (true)
        {
            System.out.println ("Moving");
            pwmOutput.setOutputChannel(1, 50);
            pwmOutput.setOutputChannel(2, 50);
            pwmOutput.setOutputChannel(3, 160);
            pwmOutput.setOutputChannel(4, 160);
            Timer.delay(2);
            
            System.out.println ("Robot Stopped");
            pwmOutput.setOutputChannel(1, 127);
            pwmOutput.setOutputChannel(2, 127);
            pwmOutput.setOutputChannel(3, 127);
            pwmOutput.setOutputChannel(4, 127);
            break;
        }
        
        while (Seen) 
        {
            System.out.println ("Turning");
            pwmOutput.setOutputChannel(1, 50);
            pwmOutput.setOutputChannel(2, 160);
            pwmOutput.setOutputChannel(3, 50);
            pwmOutput.setOutputChannel(4, 160);
            Timer.delay(0.5);
            
            System.out.println ("Stopped");
            pwmOutput.setOutputChannel(1, 127);
            pwmOutput.setOutputChannel(2, 127);
            pwmOutput.setOutputChannel(3, 127);
            pwmOutput.setOutputChannel(4, 127);
            
            System.out.println ("Fired");
            pwmOutput.setOutputChannel(7, 67);
            Timer.delay(0.1);
            if (!bTopLimitSensor) 
            {
                System.out.println ("Nautilus Stopped");
                pwmOutput.setOutputChannel(7, 127);
                break;
            }
        }
        
        while(!Seen)
        {   
            if(!Turn && ((System.currentTimeMillis() - Time) > 1000))
            {
                System.out.println ("Turning");
                pwmOutput.setOutputChannel(1, 50);
                pwmOutput.setOutputChannel(2, 50);
                pwmOutput.setOutputChannel(3, 160);
                pwmOutput.setOutputChannel(4, 160);
                
                Timer.delay(0.5);
                
                System.out.println("Turning Stopped");
                pwmOutput.setOutputChannel(1, 127);
                pwmOutput.setOutputChannel(2, 127);
                pwmOutput.setOutputChannel(3, 127);
                pwmOutput.setOutputChannel(4, 127);
                Turn = true;
            }
            
            pwmOutput.setOutputChannel(7, 67);
            System.out.println ("Blind Nautilus Fired");
            Timer.delay(0.3);
            
            System.out.println ("Hit Button Number 1 NOW!!!");
            if (!bTopLimitSensor) 
            {
               System.out.println ("Nautilus Stopped");
               pwmOutput.setOutputChannel(7, 127);
               break;
            }
        }*/
    }
 }