package subsystems;

public class CustomRobotDrive {
    
    private int iTrim = 0;
    private int iFinalLeft;
    private int iFinalRight;
    private final int iMiddleOfByte = 125;
    private final int iMaximumOfByte = 252;
    private final int iMinimumOfByte = 2;
    
    private final int iOffset = 2;
    
    public void setTrim(int args) {
        iTrim = args;
    }
    
    public int[] getFinalDrive() {
        int [] send = new int[2];
        send[0] = (iFinalLeft -iOffset) / (iMaximumOfByte - iOffset) * 100;
        send[1] = (iFinalRight -iOffset) / (iMaximumOfByte - iOffset) * 100;
        return send;
    }
    
    public int getOutput(int args) {
        int send = iFinalLeft;
        if (args == 1) {
            send = iFinalLeft;
        } else if (args == 2) {
            send = iFinalRight;
        }
        return send;
    }
    
    public boolean isSafetyEnabled() {
        boolean send;
        send = false;
        return send;
    }
    
    public void arcadeDrive(double drive, double steer) {
        
        int center = iMiddleOfByte + iTrim;

        iFinalRight = (int) (center + ((iMiddleOfByte * drive) + (steer * iMiddleOfByte)));
        iFinalLeft = (int) (center - ((iMiddleOfByte * drive) - (steer * iMiddleOfByte)));
        
        if (iFinalRight > iMaximumOfByte) {
            iFinalRight = iMaximumOfByte;
        } else if (iFinalRight < iMinimumOfByte) {
           iFinalRight = iMinimumOfByte;
        }
        
        if (iFinalLeft > iMaximumOfByte) {
            iFinalLeft = iMaximumOfByte;
        } else if (iFinalLeft < iMinimumOfByte) {
           iFinalLeft = iMinimumOfByte;
        }
    }
}
