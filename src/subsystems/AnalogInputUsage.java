package subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;

public class AnalogInputUsage {

    AnalogChannel AnalogInputChannel1 = new AnalogChannel(1,1);
    AnalogChannel AnalogInputChannel2 = new AnalogChannel(1,2);
    AnalogChannel AnalogInputChannel3 = new AnalogChannel(1,3);
    AnalogChannel AnalogInputChannel4 = new AnalogChannel(1,4);
    AnalogChannel AnalogInputChannel5 = new AnalogChannel(1,5);
    AnalogChannel AnalogInputChannel6 = new AnalogChannel(1,6);
    AnalogChannel AnalogInputChannel7 = new AnalogChannel(1,7);

    public double getInputChannel(int args) {
        double send = AnalogInputChannel1.getVoltage();

        if (args == 1) {
            send = Math.ceil((AnalogInputChannel1.getVoltage() * 1000) / 4.9 - 0.5);
        } else if (args == 2) {
            send = Math.ceil((AnalogInputChannel2.getVoltage() * 1000) / 4.9 - 0.5);
        } else if (args == 3) {
            send = Math.ceil((AnalogInputChannel3.getVoltage() * 1000) / 4.9 - 0.5);
        } else if (args == 4) {
            send = Math.ceil((AnalogInputChannel4.getVoltage() * 1000) / 4.9 - 0.5);
        } else if (args == 5) {
            send = AnalogInputChannel5.getVoltage();
        } else if (args == 6) {
            send = AnalogInputChannel6.getVoltage();
        } else if (args == 7) {
            send = AnalogInputChannel7.getVoltage();
        }
        return send;
    }


}
