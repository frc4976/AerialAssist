package subsystems;

import edu.wpi.first.wpilibj.SafePWM;

public class PWMOutput {

    SafePWM pwmChannel1 = new SafePWM(1);
    SafePWM pwmChannel2 = new SafePWM(2);
    SafePWM pwmChannel3 = new SafePWM(3);
    SafePWM pwmChannel4 = new SafePWM(4);
    SafePWM pwmChannel5 = new SafePWM(5);
    SafePWM pwmChannel6 = new SafePWM(6);
    SafePWM pwmChannel7 = new SafePWM(7);
    SafePWM pwmChannel8 = new SafePWM(8);
    
    public void setOutputChannel(int args, int output) {
        if (args == 1) {
            pwmChannel1.setRaw(output);
        } else if (args == 2) {
            pwmChannel2.setRaw(output);
        } else if (args == 3) {
            pwmChannel3.setRaw(output);
        } else if (args == 4) {
            pwmChannel4.setRaw(output);
        } else if (args == 5) {
            pwmChannel5.setRaw(output);
        } else if (args == 6) {
            pwmChannel6.setRaw(output);
        } else if (args == 7) {
            pwmChannel7.setRaw(output);
        } else if (args == 8) {
            pwmChannel8.setRaw(output);
        }
    }
}
