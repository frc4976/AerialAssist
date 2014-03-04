package subsystems;

import main.Main;

public class Catapult {

    //Buttons
    public int Increment = 0, A = 1, B = 2, X = 3, Y = 4, LeftBump = 5, RightBump = 6, Back = 7, Start = 8, LeftJoy = 9, RightJoy = 10;
    //Processes
    public boolean bReloadAfterSoft = false, bAfterHardShot = false, bAfterSoftShot = false, bHardShotProcess = false, bSoftShotProcess, bHardShooting = false, bSoftShooting = false, bWaiting;

    private long time, thistime;

    // SafePWM NautilusMotor = new SafePWM(7);
    int send = 127;

    private Main main;
    
    public Catapult(Main add) {
        main = add;
    }
    
    public void shot() {
        
        boolean XButton = main.controller.getMainRawButton(3);
        boolean NautilusTopSen = !main.diu.getInputChannel(1);
        //boolean BButton = main.controller.getMainRawButton(2);
        //boolean NautilusBottomSen = !main.diu.getInputChannel(6);
        
        if (main.isAutonomous()) {
            XButton = true;
        }
        
        if (!bSoftShotProcess) {
            if (XButton && !bHardShotProcess) {
                time = System.currentTimeMillis();
                thistime = System.currentTimeMillis() - time;
                bHardShotProcess = true;
                main.pwmOutput.setOutputChannel(7, 40); //send = 40;
                //bHardShooting = true;
                bWaiting = true;
            }
            if (bWaiting) {
                main.pwmOutput.setOutputChannel(7, 40); //send = 40;
                if (NautilusTopSen) {
                    bHardShooting = true;
                    bWaiting = false;
                }
            }
            if (bHardShooting) {
                if (!NautilusTopSen && thistime >= 700) {
                    main.pwmOutput.setOutputChannel(7, 127); //send = 127;
                    bHardShooting = false;
                    bHardShotProcess = false;
                } else {
                    thistime = System.currentTimeMillis() - time;
                    main.pwmOutput.setOutputChannel(7, 40); //send = 40;
                }
            }

        }
        /*if (!bHardShotProcess) {
            if (BButton && !NautilusTopSen && !bSoftShotProcess) {
                bSoftShotProcess = true;
                send = 187;
                bSoftShooting = true;
            }
            if (bSoftShooting && !NautilusBottomSen) {
                send = 67;
                bReloadAfterSoft = true;
                bSoftShooting = false;
                bSoftShotProcess = false;
            }
            if (bReloadAfterSoft) {
                if (!NautilusTopSen) {
                    send = 127;
                    bReloadAfterSoft = false;
                    bAfterSoftShot = true;
                    bSoftShotProcess = false;
                } else {
                    send = 67;
                }
            }

        }*/
    }
}
