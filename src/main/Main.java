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

    public DriveTrain driveSystem = new DriveTrain(this);
    public ControllerInput controller = new ControllerInput(this);
    public DigitalInputUsage diu = new DigitalInputUsage(this);
    public AnalogInputUsage aiu = new AnalogInputUsage(this);
    public Catapult catapult = new Catapult(this);
    public LoadingArms loadingArms = new LoadingArms(this);
    public SafetyWatchDog safe = new SafetyWatchDog(this);
    public PWMOutput pwmOutput = new PWMOutput();
    public NetworkTable networkTable;
    //Autonomous autonomous = new Autonomous();
    //Calibration calibration = new Calibration();

    public void robotInit() {
        networkTable = NetworkTable.getTable("SmartDashboard");
    }
    
    public void disabled() {
        while (this.isDisabled()) {
            networkTable.putNumber("gyro", aiu.getInputChannel(2));
            networkTable.putNumber("Ultrasonic1", aiu.getInputChannel(3));
            networkTable.putNumber("Ultrasonic2", aiu.getInputChannel(4));
            networkTable.putNumber("Ultrasonic3", aiu.getInputChannel(5));
            networkTable.putNumber("Ultrasonic4", aiu.getInputChannel(6));
        }
    }

    public void autonomous() {

        int state = 1;
        boolean sensorFail = true;

        while (this.isEnabled() && !sensorFail) {
            
            networkTable.putNumber("gyro", aiu.getInputChannel(2));
            networkTable.putNumber("Ultrasonic1", aiu.getInputChannel(3));
            networkTable.putNumber("Ultrasonic2", aiu.getInputChannel(4));
            networkTable.putNumber("Ultrasonic3", aiu.getInputChannel(5));
            networkTable.putNumber("Ultrasonic4", aiu.getInputChannel(6));
            
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
                catapult.shot();
                if (pwmOutput.getOutputChannel(7) == 127) {
                    state = 0;
                }
            } else {
                System.out.println("disabling");
                break;
            }
        }

        while (this.isEnabled() && sensorFail) {
            
            networkTable.putNumber("gyro", aiu.getInputChannel(2));
            networkTable.putNumber("Ultrasonic1", aiu.getInputChannel(3));
            networkTable.putNumber("Ultrasonic2", aiu.getInputChannel(4));
            networkTable.putNumber("Ultrasonic3", aiu.getInputChannel(5));
            networkTable.putNumber("Ultrasonic4", aiu.getInputChannel(6));
            
            if (state == 1) {
                System.out.println("driving forward - sensor failed");
                driveSystem.autonomousDrive(-0.3, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));

                Timer.delay(5);

                System.out.println("stopping - sensor failed");
                driveSystem.autonomousDrive(0, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                Timer.delay(0.5);
                state = 2;
            } else if (state == 2) {
                
                pwmOutput.setOutputChannel(5, 95);
                Timer.delay(0.2);
                pwmOutput.setOutputChannel(5, 125);
                Timer.delay(0.5);
                
                System.out.println("turning - sensor failed");
                driveSystem.autonomousDrive(0, -0.4);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                Timer.delay(1.4);
                System.out.print("stopping - sensor failed");
                driveSystem.autonomousDrive(0, 0);
                pwmOutput.setOutputChannel(1, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(2, driveSystem.drive.getOutput(1));
                pwmOutput.setOutputChannel(3, driveSystem.drive.getOutput(2));
                pwmOutput.setOutputChannel(4, driveSystem.drive.getOutput(2));
                state = 3;
            } else if (state == 3) {
                Timer.delay(0.5);
                System.out.println("Firing - sensor failed");
                catapult.shot();
                if (pwmOutput.getOutputChannel(7) == 127) {
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

            networkTable.putNumber("gyro", aiu.getInputChannel(2));
            networkTable.putNumber("Ultrasonic1", aiu.getInputChannel(3));
            networkTable.putNumber("Ultrasonic2", aiu.getInputChannel(4));
            networkTable.putNumber("Ultrasonic3", aiu.getInputChannel(5));
            networkTable.putNumber("Ultrasonic4", aiu.getInputChannel(6));

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
            safe.CockCheck(controller.getMainRawButton(3), diu.getInputChannel(1));
            safe.ManualMode(controller.getSecondaryRawButton(3), controller.getSecondaryRawButton(2), controller.getSecondaryRawButton(4), controller.getSecondaryRawButton(1), controller.getSecondaryRawButton(5), controller.getSecondaryRawButton(6), controller.getSecondaryRightY());

            if (!safe.isManualMode()) {
                catapult.shot();
                loadingArms.loadFront(controller.getMainRightY(), diu.getInputChannel(5), diu.getInputChannel(4), controller.getMainRawButton(1), controller.getMainRawButton(4));
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

            Timer.delay(0.01);
        }
    }

    public void test() {
        
    }
}
