import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class JMiniMap extends JFrame implements KeyListener, MouseListener, ActionListener {

    /** Variable Declaration **/

    /** The Keyboard list **/
    private ArrayList<Integer> keys = new ArrayList<Integer>();

    /** The minimaps Housed Variable **/
    private static final JMiniMap minimap = new JMiniMap();

    /** The top color, and the bottom color **/
    private Color top;
    private Color bot;

    /** The robot **/
    private final RobotVector robot = new RobotVector();

    /** The Graphics and Image Component **/
    private Graphics offg;
    private Image offscreen;

    /** The Timer **/
    private Timer timer;

    public void init(double ratioToScreen, boolean flipped) {
        /**
         * 1. Get the Dimensions of the screen
         * 2. Create a ratio of the long wall to the short wall (24ft 8in. x 54ft)
         * 3. Create the ySize by using the user given ratioToScreen
         * 4. Create the xSize using the ySize and the calculated ratio
         * 5. Edit the screen size and set the screen to be visible
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double ratio = 54.0 / (24.0 + (8.0 / 12.0));
        double xSize = screenSize.getWidth() / ratioToScreen;
        double ySize = ratio * xSize;
        setSize((int) xSize, (int) ySize);
        setVisible(true);

        /** Initialize the colors **/
        if (flipped) {
            top = new Color(155, 0, 0);
            bot = new Color(0, 0, 155);
        } else {
            top = new Color(0, 0, 155);
            bot = new Color(155, 0, 0);
        }
        /** Initialize the Key and Mouse listeners **/
        addKeyListener(this);
        addMouseListener(this);

        /** Initialize the Timer for 20 ticks per second **/
        timer = new Timer(20, this);

        /** Initialize the Graphics and Image components **/
        offscreen = createImage(getWidth(), getHeight());
        offg = offscreen.getGraphics();

        /** Initialize the Robot **/
        robot.init();
    }

    public static JMiniMap getMiniMap() {
        return minimap;
    }

    /** MiniMap declaration **/
    private JMiniMap() {
        setTitle("JMiniMap");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** Main paint method **/
    public void paint(Graphics g) {
        /** Color the Background **/
        /** 1 **/ drawRectangle(Color.WHITE, 0, 0, getWidth(), getHeight());
        drawRectangle(top, 0, 0, getWidth(), getHeight() / 3);
        drawRectangle(bot, 0, (getHeight() / 3) * 2, getWidth(), getHeight() / 3);
        outlineRectangle(robot.areaColor, new BasicStroke(getHeight() / 150), 0, (int) (63 * robot.ratioHeight), getWidth(), (int) (27 * robot.ratioHeight));
                                                                                                                    /** |---2---| **/
        /** 2 **/ drawRectangle(Color.BLACK, 0, 0, getWidth(), getHeight() / 300);                                  /** |       | **/
        /** 3 **/ drawRectangle(Color.BLACK, 0, getHeight() - getHeight() / 300, getWidth(), getHeight() / 300);    /** 4   1   5 **/
        /** 4 **/ drawRectangle(Color.BLACK, 0, 0, getHeight() / 300, getHeight());                                 /** |       | **/
        /** 5 **/ drawRectangle(Color.BLACK, getWidth() - getHeight() / 300, 0, getHeight() / 300, getHeight());    /** |---3---| **/

        /** Draw the lines that separate each area **/
        drawRectangle(Color.BLACK, 0, getHeight() / 3 - ((getHeight() / 300) / 2), getWidth(), getHeight() / 300);          /** 1/3 Separator **/
        drawRectangle(Color.BLACK, 0, ((getHeight() / 3) * 2) - ((getHeight() / 300) / 2), getWidth(), getHeight() / 300);  /** 2/3 Separator **/

        /** Paint the robot **/
        offg.setColor(Color.BLACK);
        robot.paint(offg);

        /** Draw the truss **/
        int trussSize = getHeight() / 54;
        drawRectangle(Color.BLACK, 0, (getHeight() / 2) - (trussSize / 2), getWidth(), getHeight() / 300);
        drawRectangle(Color.BLACK, 0, (getHeight() / 2) + (trussSize / 2), getWidth(), getHeight() / 300);

        /** Draw all painted objects **/
        g.drawImage(offscreen, 0, 0, this);

        /** Call a paint update **/
        repaint();
    }

    public void drawRectangle(Color color, int x, int y, int width, int height) {
        offg.setColor(color);
        offg.fillRect(x, y, width, height);
    }

    public void outlineRectangle(Color color, Stroke stroke, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) offg;
        g2.setStroke(stroke);
        g2.setColor(color);
        g2.drawRect(x, y, width, height);
    }

    /** Start the Loop **/
    public void start() {
        timer.start();
    }

    /** Stop the Loop **/
    public void stop() {
        timer.stop();
    }

    /** Main Loop **/
    @Override
    public void actionPerformed(ActionEvent e) {
        /**Check for key presses **/
        checkKeys();

        /** Update the Robots position **/
        robot.updatePosition();

        /** Call a paint update **/
        repaint();
    }

    /** Called whenever a key is "Typed" (Pushed down, than let up), not very reliable **/
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /** Called whenever a key is "Pressed" (Pushed down), reliable **/
    @Override
    public void keyPressed(KeyEvent e) {
        if (keyWas(e, KeyEvent.VK_ESCAPE))
            System.exit(0);
    }

    /** Called whenever a key is "Released" (Let up), reliable **/
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /** Perform functions based on which keys are pressed **/
    public void checkKeys() {
    }

    /** Checks if the given keycode is equal to the keycode to check **/
    public boolean keyWas(KeyEvent e, int key) {
        return (e.getKeyCode() == key);
    }

    /** Called whenever the mouse is "Clicked" (Pushed down, than let up), not very reliable **/
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /** Called whenever the mouse is "Pressed" (Pushed down), reliable **/
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /** Called whenever the mouse is "Released" (Let up), reliable **/
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /** Called whenever the mouse enters the window **/
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /** Called whenever the mouse exits the window **/
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /** The Robot Class **/
    public class RobotVector {

        public double xPos, yPos, xSpeed, ySpeed, angle, thrust;
        public Polygon shape, drawShape, arrow, drawArrow;
        public double robotRatioWide = 54.0 / (32.0 / 12.0);
        public double robotRatioLong = 54.0 / (23.0 / 12.0);
        public double ratioHeight;
        public double ratioWidth;
        public int robotWideSize, robotLongSize;
        public Color arcColor, areaColor;

        public RobotVector() {
            thrust = 0.1;
            shape = new Polygon();
            drawShape = new Polygon();
            arrow = new Polygon();
            drawArrow = new Polygon();
        }

        public void init() {
            ratioHeight = getHeight() / 486.0;
            ratioWidth = getWidth() / 222.0;
            robotWideSize = (int) ((getHeight() / robotRatioWide) / 2);
            robotLongSize = (int) ((getHeight() / robotRatioLong) / 2);
            /** Various Vertices of the Robot **/
            /** 1 **/ shape.addPoint(-robotWideSize, robotLongSize);
            /** 2 **/ shape.addPoint(robotWideSize, robotLongSize);
            /** 3 **/ shape.addPoint(robotWideSize, -robotLongSize);                /** 1---------2 **/
            /** 4 **/ shape.addPoint(-robotWideSize, -robotLongSize);               /** |         | **/
            /** 1 **/ drawShape.addPoint(-robotWideSize, robotLongSize);            /** |         | **/
            /** 2 **/ drawShape.addPoint(robotWideSize, robotLongSize);             /** 4---------3 **/
            /** 3 **/ drawShape.addPoint(robotWideSize, -robotLongSize);
            /** 4 **/ drawShape.addPoint(-robotWideSize, -robotLongSize);
            arrow.addPoint(-robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            arrow.addPoint(0, -robotLongSize * 2);
            arrow.addPoint(robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            drawArrow.addPoint(-robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            drawArrow.addPoint(0, -robotLongSize * 2);
            drawArrow.addPoint(robotWideSize / 2, -robotLongSize - robotLongSize / 2);
        }

        /** Paint the Robot **/
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(getHeight() / 100));
            g2.setColor(arcColor);
            g2.drawArc((int) xPos - robotWideSize * 2, (int) yPos - robotWideSize * 2, robotWideSize * 4, robotWideSize * 4, -((int) robot.angle - 30), 300);
            g2.drawArc((int) xPos - robotWideSize * 2, (int) yPos - robotWideSize * 2, robotWideSize * 4, robotWideSize * 4, -((int) robot.angle + 10), 20);
            g2.drawArc((int) (xPos - robotWideSize * 1.5), (int) (yPos - robotWideSize * 1.5), robotWideSize * 3, robotWideSize * 3, -((int) robot.angle + 10), 20);
            g2.drawArc((int) (xPos - robotWideSize), (int) (yPos - robotWideSize), robotWideSize * 2, robotWideSize * 2, -((int) robot.angle + 10), 20);
            g2.setStroke(new BasicStroke(getHeight() / 300));
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillPolygon(drawShape);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(drawShape);
            g2.setColor(new Color(0, 155, 0));
            g2.fillPolygon(drawArrow);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(drawArrow);
        }

        public void updatePosition() {
            if (angle > 255 && angle < 285)
                arcColor = new Color(0, 255, 0, 255);
            else
                arcColor = new Color(255, 255, 0, 255);
            if (yPos - robotWideSize > 63 * ratioHeight && yPos + robotWideSize < 90 * ratioHeight)
                areaColor = new Color(0, 255, 0, 255);
            else
                areaColor = new Color(255, 255, 0, 255);
            double ratioHeight = getHeight() / 486.0;
            double ratioWidth = getWidth() / 222.0;
            setPos(TextHandler.getVal("x", xPos / ratioHeight), TextHandler.getVal("y", yPos / ratioWidth), TextHandler.getVal("r", angle));
            int x, y;
            for (int i = 0; i < shape.npoints; i++) {
                x = (int) Math.round(shape.xpoints[i] * Math.cos(Math.toRadians(angle)) - shape.ypoints[i] * Math.sin(Math.toRadians(angle)));
                y = (int) Math.round(shape.xpoints[i] * Math.sin(Math.toRadians(angle)) + shape.ypoints[i] * Math.cos(Math.toRadians(angle)));
                drawShape.xpoints[i] = x;
                drawShape.ypoints[i] = y;
            }
            for (int i = 0; i < arrow.npoints; i++) {
                x = (int) Math.round(arrow.xpoints[i] * Math.cos(Math.toRadians(angle)) - arrow.ypoints[i] * Math.sin(Math.toRadians(angle)));
                y = (int) Math.round(arrow.xpoints[i] * Math.sin(Math.toRadians(angle)) + arrow.ypoints[i] * Math.cos(Math.toRadians(angle)));
                drawArrow.xpoints[i] = x;
                drawArrow.ypoints[i] = y;
            }
            drawShape.invalidate();
            drawShape.translate((int) xPos, (int) yPos);
            drawArrow.invalidate();
            drawArrow.translate((int) xPos, (int) yPos);
        }

        public void setPos(double x, double y, double r) {
            /** Grid is equal to 486 x 222 **/
            xPos = (x * ratioWidth);
            yPos = (y * ratioHeight);
            angle = r;
        }
    }

    /** The Text Handler **/
    public static class TextHandler {

        public static ArrayList<String> stationOut = new ArrayList<String>();

        public static void ReadFromStation() throws IOException {
            File f = new File("stationOut.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String s;

            stationOut.clear();
            while ((s = br.readLine()) != null)
                stationOut.add(s);

            br.close();
        }

        public static void WriteToStation() throws IOException {
            File f = new File("minimapOut.txt");
            FileWriter fs = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fs);
            out.close();
        }

        public static double getVal(String prefix, double defaultVal) {
            double val = defaultVal;
            try {
                ReadFromStation();
                val = Double.parseDouble(getLine(stationOut, prefix));
            } catch (Exception e) {
                System.err.println("ERROR: Read an empty line! Using previous value, " + prefix + " = " + val);
            }
            return val;
        }

        public static String getLine(ArrayList<String> textDoc, String prefix) {
            for (int i = 0; i < textDoc.size(); i++) {
                if (textDoc.get(i).contains(prefix))
                    return textDoc.get(i).substring(textDoc.get(i).indexOf("=") + 1);
            }
            return "";
        }

    }

}