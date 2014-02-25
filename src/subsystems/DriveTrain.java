package subsystems;

public class DriveTrain {
    
    public CustomRobotDrive drive = new CustomRobotDrive();
    
    private double dThrottle;
    private int iTrim = 0;
    public int iGear = 3;
    private boolean bGearButtonDown;
    private boolean bTrimButtonDown;
    private boolean bTurnButtonDown;
    public long lastTime = System.currentTimeMillis();
    
    int turning = 0;
    
    public int[] getFinalDrive() {
        return drive.getFinalDrive();
    }
    
    
    public boolean isSafetyEnabled() {
        return drive.isSafetyEnabled();
    }
    
    public void checkGear(boolean args1, boolean args2) {
        
        if (args1 && args2 && !bGearButtonDown) {
            iGear = 2;
            bGearButtonDown = true;
        } else if (args2 && !bGearButtonDown && iGear < 3) {
            iGear += 1;
            bGearButtonDown = true;
        } else if (args1 && !bGearButtonDown && iGear > 1) {
            iGear -= 1;
            bGearButtonDown = true;
        } else if (!args1 && !args2 && bGearButtonDown) {
            bGearButtonDown = false;
        }
    }
    
    public void checkTrim(boolean args1, boolean args2) {
        if (args1 && !bTrimButtonDown && iGear > 1) {
            iTrim += 1;
            bTrimButtonDown = true;
        } else if (args2 && !bTrimButtonDown && iGear < 3) {
            iTrim -= 1;
            bTrimButtonDown = true;
        } else if (!args1 && !args2 && bTrimButtonDown) {
            bTrimButtonDown = false;
        }
    }
    
    public void checkThrottle() {
        if (iGear == 1) {
            dThrottle = 0.3;
        } else if (iGear == 2) {
            dThrottle = 0.5;
        } else if (iGear == 3) {
            dThrottle = 1;
        }
    }
    
    public void arcadeDrive(double args1, double args2) {
        drive.setTrim(iTrim);
        double driveX;
        if (args1 > 0.15 || args1 < -0.15) {
            driveX = (args1 - 0.15) / 0.85;
        } else {
            driveX = 0;
        }
        
        drive.arcadeDrive(args2 * dThrottle, driveX * dThrottle);
    }
    
    public void checkTurn(double args1) {
        if (args1 == 1 && !bTurnButtonDown) {
            turning = 1;
            bTurnButtonDown = true;
            lastTime = System.currentTimeMillis();
        } else if (args1 == -1 && !bTurnButtonDown) {
            turning = 2;
            lastTime = System.currentTimeMillis();
            bTurnButtonDown = true;
        } else if (args1 == 0 && bTurnButtonDown) {
            bTurnButtonDown = false;
            turning = 0;
        }
    }
    
    public void turn() {
        long now = System.currentTimeMillis() - lastTime;
        if (turning == 1) {
            drive.arcadeDrive(0, 1 * dThrottle);
            if (now >= 1000 * 0.5 / dThrottle) {
                drive.arcadeDrive(0, 0);
                turning = 0;
            }
        } else if (turning == 2) {
            drive.arcadeDrive(0, -1 * dThrottle);
            if(now >= 1000 * 0.5 / dThrottle) {
                drive.arcadeDrive(0, 0);
                turning = 0;
            }
        }
    }
    
    
    public void autonomousDrive(double args, double args1) {
        drive.setTrim(iTrim);
        drive.arcadeDrive(args, args1);
    }
}
