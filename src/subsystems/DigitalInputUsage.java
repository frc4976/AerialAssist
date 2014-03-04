package subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import main.Main;

public class DigitalInputUsage {

    DigitalInput inputChannel1 = new DigitalInput(1);
    DigitalInput inputChannel2 = new DigitalInput(2);
    DigitalInput inputChannel3 = new DigitalInput(3);
    DigitalInput inputChannel4 = new DigitalInput(4);
    DigitalInput inputChannel5 = new DigitalInput(5);
    DigitalInput inputChannel6 = new DigitalInput(6);
    DigitalInput inputChannel7 = new DigitalInput(7);
    DigitalInput inputChannel8 = new DigitalInput(8);

    private Main main;
    
    public DigitalInputUsage(Main add) {
        main = add;
    }
    
    public boolean getInputChannel(int args) {
        boolean send = inputChannel1.get();

        if (args == 1) {
            send = inputChannel1.get();
        } else if (args == 2) {
            send = inputChannel2.get();
        } else if (args == 3) {
            send = inputChannel3.get();
        } else if (args == 4) {
            send = inputChannel4.get();
        } else if (args == 5) {
            send = inputChannel5.get();
        } else if (args == 6) {
            send = inputChannel6.get();
        } else if (args == 7) {
            send = inputChannel7.get();
        } else if (args == 8) {
            send = inputChannel8.get();
        }

        return send;
    }
}
