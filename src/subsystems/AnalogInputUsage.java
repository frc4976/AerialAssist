package subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import main.Main;

public class AnalogInputUsage {

    AnalogChannel AnalogInputChannel1 = new AnalogChannel(1,1);
    Gyro AnalogInputChannel2 = new Gyro(1,2);
    AnalogChannel AnalogInputChannel3 = new AnalogChannel(1,3);
    AnalogChannel AnalogInputChannel4 = new AnalogChannel(1,4);
    AnalogChannel AnalogInputChannel5 = new AnalogChannel(1,5);
    AnalogChannel AnalogInputChannel6 = new AnalogChannel(1,6);
    AnalogChannel AnalogInputChannel7 = new AnalogChannel(1,7);

    private Main main;
    
    public AnalogInputUsage(Main add) {
        main = add;
    }
    
    public double getInputChannel(int args) {
        double send = (int) AnalogInputChannel1.getVoltage();

        if (args == 1) {
            send = (int) AnalogInputChannel1.getVoltage();
        } else if (args == 2) {
            send = (int) AnalogInputChannel2.getAngle();
        } else if (args == 3) {
            send = (int) ((AnalogInputChannel3.getVoltage() * 1000) / 4.9);
        } else if (args == 4) {
            send = (int) ((AnalogInputChannel4.getVoltage() * 1000) / 4.9);
        } else if (args == 5) {
            send = (double) ((AnalogInputChannel5.getVoltage() * 1000) / 4.9);
        } else if (args == 6) {
            send = (double) ((AnalogInputChannel6.getVoltage() * 1000) / 4.9);
        } else if (args == 7) {
            send = (int) AnalogInputChannel7.getVoltage();
        }
        return send;
    }
}
