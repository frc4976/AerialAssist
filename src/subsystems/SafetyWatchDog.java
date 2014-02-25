/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subsystems;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author Mo
 */
public class SafetyWatchDog {

    // SafePWM NautilusMotor = new Jaguar (7);
    boolean bHardButton, bManualMode;
    double dBatteryLevel;
    DriverStation Station;
    boolean Forward, Backward;
    int send = 127;
    int Arch = 127;
    int Loading = 127;

    public void CockCheck(boolean mainXButton, boolean bTopLimitSensor) {
        //System.out.println ("CockCheck Started");
        //bHardButton = Stick.getRawButton(1);
        boolean waiting = false;
        long lastTime = 0;

        if (waiting) {
            long now = System.currentTimeMillis() - lastTime;

            if (now > 3000) {
                if (!bTopLimitSensor) {
                    System.out.println("Nautilus Cam is Cocked");
                }
                if (bTopLimitSensor) {
                    System.out.println("Nautilus Cam NOT is Cocked");
                    waiting = false;
                }
            }
        }

        if (mainXButton) {
            System.out.println("Fired");
            System.out.println("Waiting 3 Seconds");
            lastTime = System.currentTimeMillis();

            waiting = true;
            // Limit Sensor gives opposite values for some reason
            // ! = false
            //   = true  
        }
    }

    public int getManualOutput() {
        return send;
    }

    public int getManualArchOutput() {
        return Arch;
    }

    public int getManualLoadingOutput() {
        return Loading;
    }

    public boolean isManualMode() {
        return bManualMode;
    }

    public void BatteryCheck() {
        //System.out.println ("Battery Line Started");
        Station = DriverStation.getInstance();
        dBatteryLevel = Station.getBatteryVoltage();
        if (dBatteryLevel >= 12) {
            System.out.println("Battery is Good");
        }
        if (dBatteryLevel < 12 && dBatteryLevel >= 10) {
            System.out.println("Battery is Low");
        }
        if (dBatteryLevel < 10) {
            System.out.println("Battery is Critical");
        }
    }

    public void ManualMode(boolean secondaryXButton, boolean secondaryBButton,
            boolean secondaryYButton, boolean secondaryAButton, boolean LeftBumper, boolean RightBumper, double RightStick) {
        //System.out.println ("Manual Mode Started");
        if (secondaryXButton) {
            bManualMode = true;
        }

        if (secondaryBButton) {
            System.out.println("Manual Mode Disabled");
            bManualMode = false;
        }

        if (bManualMode == true) {
            System.out.println("Manual Mode Enabled");

            // Manual Nautilus Code Begins
            if (secondaryAButton) {
                System.out.println("Motor Running Backwards");
                send = 200;
            }

            if (secondaryYButton) {
                System.out.println("Motor Running Forwards");
                send = 40;
            }

            if (!secondaryAButton) {
                if (!secondaryYButton) {
                    send = 127;
                }
            }
            // Manual Nautilus Code Ends

            // Manual Arch/Arm Code
            if (RightBumper) {
                System.out.println("Motor Running Forwards");
                Arch = 170;
                Loading = 250;
            }

            if (LeftBumper) {
                System.out.println("Motor Running Backwards");
                Arch = 95;
                Loading = 250;
            }

            if (!RightBumper) {
                if (!LeftBumper) {
                    Arch = 127;
                    if (RightStick > -0.5) {
                        if (RightStick < 0.5) {
                            Loading = 127;           // Now it only stops the Loading Motor when all of them aren't pressed
                        }
                    }
                }
            }

            // Manual Arch/Arm Code End
            // Manual Loading Wheels Code Begins
            // Based off the Loading Arms Code 
            if (RightStick >= 0.5) {
                System.out.println("Motor Running Forwards");
                Loading = 10;
            }

            if (RightStick <= -0.5) {
                System.out.println("Motor Running Backwards");
                Loading = 250;
            }
            //Manual Loading Wheels Code End
        }
    }
}
