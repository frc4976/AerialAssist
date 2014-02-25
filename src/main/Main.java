package main;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import subsystems.*;

public class Main extends SimpleRobot {

    long currentTime;

    DriveTrain driveSystem = new DriveTrain();
    ControllerInput controller = new ControllerInput();
    public DigitalInputUsage diu = new DigitalInputUsage();
    AnalogInputUsage aiu = new AnalogInputUsage();
    Catapult catapult = new Catapult();
    LoadingArms loadingArms = new LoadingArms();
    SafetyWatchDog safe = new SafetyWatchDog();
    PWMOutput pwmOutput = new PWMOutput();
    NetworkTable networkTable;
    //Autonomous autonomous = new Autonomous();
    //Calibration calibration = new Calibration();

    public void robotInit() {
        networkTable = NetworkTable.getTable("Dashboard");
    }
    
    public void autonomous() {
        boolean CameraInput = false;
        boolean RobotTurned = false;
        boolean TimerAutonomousMode = false;
        double NinetyDegreeeTurningTime = 0.7;
        
        long Time = System.currentTimeMillis();

        networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
        
        if (aiu.getInputChannel(2) > 900 || aiu.getInputChannel(2) < 100) {
            TimerAutonomousMode = true;
        }
        
        networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
        /*System.out.println(cameraInput.getBoolean(""));
         System.out.println(cameraInput.containsKey(""));*/

        while (!TimerAutonomousMode) {
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            if (aiu.getInputChannel(2) > 250) {
                //System.out.println (aiu.getInputChannel(2));
                System.out.println("Moving Forward");
                pwmOutput.setOutputChannel(1, 177);
                pwmOutput.setOutputChannel(2, 177);
                pwmOutput.setOutputChannel(3, 77);
                pwmOutput.setOutputChannel(4, 77);
            } else {
                System.out.println("Robot Stopped");
                pwmOutput.setOutputChannel(1, 127);
                pwmOutput.setOutputChannel(2, 127);
                pwmOutput.setOutputChannel(3, 127);
                pwmOutput.setOutputChannel(4, 127);
                break;
            }
        }

        while (TimerAutonomousMode) {
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            //System.out.println (aiu.getInputChannel(2));
            System.out.println("Moving Forward");
            pwmOutput.setOutputChannel(1, 177);
            pwmOutput.setOutputChannel(2, 177);
            pwmOutput.setOutputChannel(3, 77);
            pwmOutput.setOutputChannel(4, 77);
            Timer.delay(2);
        
            System.out.println("Robot Stopped");
            pwmOutput.setOutputChannel(1, 127);
            pwmOutput.setOutputChannel(2, 127);
            pwmOutput.setOutputChannel(3, 127);
            pwmOutput.setOutputChannel(4, 127);
            break;

        }

        while (CameraInput) {
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            System.out.println("Turning");
            pwmOutput.setOutputChannel(1, 77);
            pwmOutput.setOutputChannel(2, 77);
            pwmOutput.setOutputChannel(3, 77);
            pwmOutput.setOutputChannel(4, 77);
            Timer.delay(NinetyDegreeeTurningTime);

            System.out.println("Stopped");
            pwmOutput.setOutputChannel(1, 127);
            pwmOutput.setOutputChannel(2, 127);
            pwmOutput.setOutputChannel(3, 127);
            pwmOutput.setOutputChannel(4, 127);
            Timer.delay(1);

            System.out.println("Fired");
            pwmOutput.setOutputChannel(7, 67);
            Timer.delay(0.3);
            while (true) {
                if (!diu.getInputChannel(1)) {
                    System.out.println("Nautilus Stopped");
                    pwmOutput.setOutputChannel(7, 127);
                    break;
                } 
            }
            break;
        }

        while (!CameraInput) {
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            if (!RobotTurned && ((System.currentTimeMillis() - Time) > 5000)) //Change this to wait more to blind fire
            {
                System.out.println("Turning");
                pwmOutput.setOutputChannel(1, 77);
                pwmOutput.setOutputChannel(2, 77);
                pwmOutput.setOutputChannel(3, 77);
                pwmOutput.setOutputChannel(4, 77);

                Timer.delay(NinetyDegreeeTurningTime);

                System.out.println("Turning Stopped");
                pwmOutput.setOutputChannel(1, 127);
                pwmOutput.setOutputChannel(2, 127);
                pwmOutput.setOutputChannel(3, 127);
                pwmOutput.setOutputChannel(4, 127);
                RobotTurned = true;
            }

            Timer.delay(1);
            pwmOutput.setOutputChannel(7, 67);
            System.out.println("Blind Nautilus Fired");
            Timer.delay(0.3);

            System.out.println("Hit Button Number 1 NOW!!!");
            while (true) {
                if (diu.getInputChannel(1)) {
                    System.out.println("Nautilus Stopped");
                    pwmOutput.setOutputChannel(7, 127);
                    break;
                }
            }
        }
    }

    public void operatorControl() {
        currentTime = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        while (this.isEnabled()) {

            networkTable.putBoolean("prox1", diu.getInputChannel(1));
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            
            if (aiu.getInputChannel(2) < 250)
            {
                System.out.println("Fire!!!");
            }
            else
            {
                System.out.println("The Distance is " + aiu.getInputChannel(2) + " cm");
            }

            controller.update(7, 10);

            driveSystem.checkTrim(controller.getSecondaryRawButton(5), controller.getSecondaryRawButton(6));
            driveSystem.checkGear(controller.getMainRawButton(5), controller.getMainRawButton(6));
            driveSystem.checkThrottle();
            driveSystem.arcadeDrive(controller.getMainLeftX(), controller.getMainTriggers());
            driveSystem.checkTurn(controller.getMainRawAxis(6));
            driveSystem.turn();

            // safe.BatteryCheck();
            //System.out.println(diu.getInputChannel(2));           
            loadingArms.loadFront(controller.getMainRightY(), diu.getInputChannel(5), diu.getInputChannel(4), controller.getMainRawButton(1), controller.getMainRawButton(4));
            catapult.shot(controller.getMainRawButton(3), !diu.getInputChannel(1), controller.getMainRawButton(2), !diu.getInputChannel(2));
            safe.CockCheck(controller.getMainRawButton(3), diu.getInputChannel(1));
            safe.ManualMode(controller.getSecondaryRawButton(3), controller.getSecondaryRawButton(2), controller.getSecondaryRawButton(4), controller.getSecondaryRawButton(1), controller.getSecondaryRawButton(5), controller.getSecondaryRawButton(6), controller.getSecondaryRightY());

            if (safe.isManualMode() == false) {
                pwmOutput.setOutputChannel(7, catapult.getNautilusMotor());
                pwmOutput.setOutputChannel(6, loadingArms.getIFrontLoadingMotor());
                pwmOutput.setOutputChannel(5, loadingArms.getIFrontArmMotor());
            } else {
                pwmOutput.setOutputChannel(6, safe.getManualLoadingOutput());
                pwmOutput.setOutputChannel(5, safe.getManualArchOutput());
                pwmOutput.setOutputChannel(7, safe.getManualOutput());
            }

            pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
            pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
            pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
            pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));

            //calibration.PreGameCalibration(true, true, true, true, true);
            Timer.delay(0.01);
        }
    }

    public void test() {
        while (this.isEnabled()) {
            networkTable.putNumber("aiuChannel1", aiu.getInputChannel(1));
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            networkTable.putNumber("aiuChannel3", aiu.getInputChannel(3));
            networkTable.putNumber("aiuChannel4", aiu.getInputChannel(4));
        }
    }
}
