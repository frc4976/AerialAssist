package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import main.Main;

public class ControllerInput {

    Joystick mainController = new Joystick(1);
    Joystick secondaryController = new Joystick(2);
    
    private double[] mainRawAxis;
    private double[] secondaryRawAxis;
    private boolean[] mainRawButton;
    private boolean[] secondaryRawButton;
    
    private Main main;
    
    public ControllerInput(Main add) {
        main = add;
    }
    
    public double getMainRawAxis(int args) {
        return mainRawAxis[args];
    }
    
    public double getSecondaryRawAxis(int args) {
        return secondaryRawAxis[args];
    }
    
    public boolean getMainRawButton(int args) {
        return mainRawButton[args];
    }
    
    public boolean getSecondaryRawButton(int args) {
        return secondaryRawButton[args];
    }
    
    public double getMainLeftX() {
        return mainRawAxis[1];
    }
    
    public double getMainLeftY() {
        return mainRawAxis[2];
    }
    
    public double getMainTriggers() {
        return mainRawAxis[3];
    }
    
    public double getMainRightX() {
        return mainRawAxis[4];
    }
    
    public double getMainRightY() {
        return mainRawAxis[5];
    }
    
    public double getSecondaryLeftX() {
        return secondaryRawAxis[1];
    }
    
    public double getSecondaryLeftY() {
        return secondaryRawAxis[2];
    }
    
    public double getSecondaryTriggers() {
        return secondaryRawAxis[3];
    }
    
    public double getSecondaryRightX() {
        return secondaryRawAxis[4];
    }
    
    public double getSecondaryRightY() {
        return secondaryRawAxis[5];
    }
    
    public void update(int axisCount, int buttonCount) {
        this.mainRawAxis = new double[axisCount + 1];
        for (int i = 1; i < axisCount; i++) {
            mainRawAxis[i] = mainController.getRawAxis(i);
        }
        
        this.secondaryRawAxis = new double[axisCount + 1];
        for (int i = 1; i < axisCount; i++) {
            secondaryRawAxis[i] = secondaryController.getRawAxis(i);
        }
        
        this.mainRawButton = new boolean[buttonCount + 1];
        for (int i = 1; i < buttonCount; i++) {
            mainRawButton[i] = mainController.getRawButton(i);
        }
        
        this.secondaryRawButton = new boolean[buttonCount + 1];
        for (int i = 1; i < buttonCount; i++) {
            secondaryRawButton[i] = secondaryController.getRawButton(i);
        }
    }
}
