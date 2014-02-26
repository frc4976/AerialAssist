package subsystems;

public class Catapult {

    //Buttons
    public int Increment = 0, A = 1, B = 2, X = 3, Y = 4, LeftBump = 5, RightBump = 6, Back = 7, Start = 8, LeftJoy = 9, RightJoy = 10;
    //Processes
    public boolean bReloadAfterSoft = false, bAfterHardShot = false, bAfterSoftShot = false, bHardShotProcess = false, bSoftShotProcess, bHardShooting = false, bSoftShooting = false, bWaiting;

    private long time, thistime;

    // SafePWM NautilusMotor = new SafePWM(7);
    int send = 127;

    public void shot(boolean XButton, boolean NautilusTopSen, boolean BButton, boolean NautilusBottomSen) {
        if (!bSoftShotProcess) {
            if (XButton && !bHardShotProcess) {
                time = System.currentTimeMillis();
                thistime = System.currentTimeMillis() - time;
                bHardShotProcess = true;
                send = 67;
                //bHardShooting = true;
                bWaiting = true;
            }
            if (bWaiting) {
                send = 67;
                if (NautilusTopSen) {
                    bHardShooting = true;
                    bWaiting = false;
                }
            }
            if (bHardShooting) {
                if (!NautilusTopSen && thistime >= 333) {
                    send = 127;
                    bHardShooting = false;
                    bHardShotProcess = false;
                } else {
                    thistime = System.currentTimeMillis() - time;
                    send = 67;
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

    public int getNautilusMotor() {
        return send;
    }
}
