package main;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import subsystems.*;

public class Main extends SimpleRobot {

    int rotation;
    long currentTime;
    private long time, thistime;
    private int degree;

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
        networkTable = NetworkTable.getTable("SmartDashboard");
    }
    
    public void disabled() {
        while (this.isDisabled()) {
            networkTable.putNumber("UltraSonic1", aiu.getInputChannel(2));
            networkTable.putNumber("UltraSonic2", aiu.getInputChannel(3));
            networkTable.putNumber("UltraSonic3", aiu.getInputChannel(4));
            networkTable.putNumber("UltraSonic4", aiu.getInputChannel(5));
        }
    }

    public void autonomous() {

        int state = 1;
        boolean sensorFail = aiu.getInputChannel(2) < 200;

        while (this.isEnabled() && !sensorFail) {
            
            networkTable.putNumber("UltraSonic1", aiu.getInputChannel(2));
            networkTable.putNumber("UltraSonic2", aiu.getInputChannel(3));
            networkTable.putNumber("UltraSonic3", aiu.getInputChannel(4));
            networkTable.putNumber("UltraSonic4", aiu.getInputChannel(5));
            
            if (state == 1) {
                if (aiu.getInputChannel(2) >= 300) {
                    System.out.println("driving forward");
                    driveSystem.autonomousDrive(-0.3, 0);
                    pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                    pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                    pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                    pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                } else {
                    System.out.println("stopping");
                    driveSystem.autonomousDrive(0, 0);
                    pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                    pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                    pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                    pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                    state = 2;
                }
            } else if (state == 2) {
                    
                pwmOutput.setOutputChannel(5, 95);
                Timer.delay(0.2);
                pwmOutput.setOutputChannel(5, 125);
                
                System.out.println("turning");
                driveSystem.autonomousDrive(0, -0.5);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                Timer.delay(1.2);
                System.out.println("stopping");
                driveSystem.autonomousDrive(0, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                state = 3;
            } else if (state == 3) {
                System.out.println("Firing");
                catapult.shot(true, !diu.getInputChannel(1), false, true);
                pwmOutput.setOutputChannel(7, catapult.getNautilusMotor());
                if (catapult.getNautilusMotor() == 127) {
                    state = 0;
                }
            } else {
                System.out.println("disabling");
                break;
            }
        }

        while (this.isEnabled() && sensorFail) {
            
            networkTable.putNumber("UltraSonic1", aiu.getInputChannel(2));
            networkTable.putNumber("UltraSonic2", aiu.getInputChannel(3));
            networkTable.putNumber("UltraSonic3", aiu.getInputChannel(4));
            networkTable.putNumber("UltraSonic4", aiu.getInputChannel(5));
            
            
            if (state == 1) {
                System.out.println("driving forward - sensor failed");
                driveSystem.autonomousDrive(-0.3, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));

                Timer.delay(2.9);

                System.out.println("stopping - sensor failed");
                driveSystem.autonomousDrive(0, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                state = 2;
            } else if (state == 2) {
                
                pwmOutput.setOutputChannel(5, 95);
                Timer.delay(0.2);
                pwmOutput.setOutputChannel(5, 125);
                
                System.out.println("turning - sensor failed");
                driveSystem.autonomousDrive(0, -0.5);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                Timer.delay(1.2);
                System.out.print("stopping - sensor failed");
                driveSystem.autonomousDrive(0, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                state = 3;
            } else if (state == 3) {
                System.out.println("Firing - sensor failed");
                catapult.shot(true, !diu.getInputChannel(1), false, true);
                pwmOutput.setOutputChannel(7, catapult.getNautilusMotor());
                if (catapult.getNautilusMotor() == 127) {
                    state = 0;
                }
            } else {
                System.out.println("disabling - sensor failed");
                break;
            }
        }
    }

    public void operatorControl() {
        currentTime = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        while (this.isEnabled()) {

            networkTable.putBoolean("prox1", diu.getInputChannel(1));
            networkTable.putNumber("aiuChannel1", aiu.getInputChannel(1));
            networkTable.putNumber("aiuChannel2", aiu.getInputChannel(2));
            networkTable.putNumber("aiuChannel3", aiu.getInputChannel(3));
            networkTable.putNumber("aiuChannel4", aiu.getInputChannel(4));

            System.out.print(aiu.getInputChannel(1));

            if (aiu.getInputChannel(2) < 250) {
                System.out.println("Fire!!!");
            } else {
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

            /*if (controller.getSecondaryRawButton(9)) {
             rotation = 0;
             }

             if (aiu.getInputChannel(5) < 2.528 || aiu.getInputChannel(5) > 2.568) {
             rotation = (int) (rotation + (aiu.getInputChannel(5) * 1000 - 2548) / 100);
             }

             int degree = (int) (rotation / 3.7);*/
            /*
             double gyro = aiu.getInputChannel(5);
             int gyroZeroVoltage = (int) (gyro * 1000 - 2500); 
             int currentAngle = (int) (gyroZeroVoltage / 3.7);
             degree += currentAngle;*/
        }
    }

    public void test() {

        //aiu.gyro.getAngle();
    }
}
