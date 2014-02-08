import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class JMiniMap extends JFrame implements KeyListener, MouseListener, ActionListener {

    /** Variable Declaration **/

    /**
     * The minimaps Housed Variable *
     */
    private static final JMiniMap minimap = new JMiniMap();

    /**
     * The top color, and the bottom color *
     */
    private Color top;
    private Color bot;

    /**
     * The robot *
     */
    private final RobotVector robot = new RobotVector();

    /**
     * The Graphics and Image Component *
     */
    private Graphics offg;
    private Image offscreen;

    /**
     * The Timer *
     */
    private Timer timer;

    public void init(double ratioToScreen, boolean flipped) {
        /** Arena:  54ft x 24ft 8in
         *          16.46m x 7.5m
         *          1646cm x 752cm
         * 1. Get the Dimensions of the screen
         * 2. Create a ratio of the long wall to the short wall
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

    /**
     * MiniMap declaration *
     */
    private JMiniMap() {
        setTitle("JMiniMap");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
    }

    /**
     * Main paint method *
     */
    public void paint(Graphics g) {
        /** Color the Background **/
        drawRectangle(Color.WHITE, 0, 0, getWidth(), getHeight());

        /** Draw the top side color and the bottom side color, decided in the program parameters **/
        drawRectangle(top, 0, 0, getWidth(), getHeight() / 3);
        drawRectangle(bot, 0, (getHeight() / 3) * 2, getWidth(), getHeight() / 3);

        /** Draw the arena walls **/
        drawRectangle(Color.BLACK, 0, 0, getWidth(), getHeight() / 300);
        drawRectangle(Color.BLACK, 0, getHeight() - getHeight() / 300, getWidth(), getHeight() / 300);
        drawRectangle(Color.BLACK, 0, 0, getHeight() / 300, getHeight());
        drawRectangle(Color.BLACK, getWidth() - getHeight() / 300, 0, getHeight() / 300, getHeight());

        /** Draw the lines that separate each area **/
        drawRectangle(Color.BLACK, 0, getHeight() / 3 - ((getHeight() / 300) / 2), getWidth(), getHeight() / 300);
        drawRectangle(Color.BLACK, 0, ((getHeight() / 3) * 2) - ((getHeight() / 300) / 2), getWidth(), getHeight() / 300);

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

    /**
     * Draw a rectangle using the graphics components *
     */
    public void drawRectangle(Color color, int x, int y, int width, int height) {
        offg.setColor(color);
        offg.fillRect(x, y, width, height);
    }

    /**
     * Start the Loop *
     */
    public void start() {
        timer.start();
    }

    /**
     * Stop the Loop *
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Main Loop *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**Check for key presses **/
        checkKeys();

        /** Update the Robots position **/
        robot.updatePosition();

        /** Call a paint update **/
        repaint();
    }

    /**
     * Called whenever a key is "Typed" (Pushed down, than let up), not very reliable *
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Called whenever a key is "Pressed" (Pushed down), reliable *
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Called whenever a key is "Released" (Let up), reliable *
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (keyWas(e, KeyEvent.VK_ESCAPE))
            System.exit(0);
    }

    /**
     * Perform functions based on which keys are pressed *
     */
    public void checkKeys() {
    }

    /**
     * Checks if the given keycode is equal to the keycode to check *
     */
    public boolean keyWas(KeyEvent e, int key) {
        return (e.getKeyCode() == key);
    }

    /**
     * Called whenever the mouse is "Clicked" (Pushed down, than let up), not very reliable *
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Called whenever the mouse is "Pressed" (Pushed down), reliable *
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Called whenever the mouse is "Released" (Let up), reliable *
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Called whenever the mouse enters the window *
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Called whenever the mouse exits the window *
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * The Robot Class *
     */
    public class RobotVector {

        /**
         * Variables that hold the robots position *
         */
        public double xPos, yPos, angle;

        /**
         * Variables that hold the robots graphical shapes *
         */
        public Polygon shape, drawShape, arrow, drawArrow;

        /**
         * Various ratios that make sure the robot is the right size on the field *
         */
        public double robotRatioWide = 54.0 / (32.0 / 12.0), robotRatioLong = 54.0 / (23.0 / 12.0);
        public double ratioWidth, ratioHeight;
        public int robotWideSize, robotLongSize;

        /**
         * Initialize the Robot *
         */
        public RobotVector() {
            shape = new Polygon();
            drawShape = new Polygon();
            arrow = new Polygon();
            drawArrow = new Polygon();
        }

        public void init() {
            /** Provide values to the variables **/
            ratioHeight = getHeight() / 1646.0;
            ratioWidth = getWidth() / 752.0;
            robotWideSize = (int) ((getHeight() / robotRatioWide) / 2);
            robotLongSize = (int) ((getHeight() / robotRatioLong) / 2);

            /** Create the various shapes using verticies **/
            shape.addPoint(-robotWideSize, robotLongSize);
            shape.addPoint(robotWideSize, robotLongSize);
            shape.addPoint(robotWideSize, -robotLongSize);
            shape.addPoint(-robotWideSize, -robotLongSize);
            drawShape.addPoint(-robotWideSize, robotLongSize);
            drawShape.addPoint(robotWideSize, robotLongSize);
            drawShape.addPoint(robotWideSize, -robotLongSize);
            drawShape.addPoint(-robotWideSize, -robotLongSize);
            arrow.addPoint(-robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            arrow.addPoint(0, -robotLongSize * 2);
            arrow.addPoint(robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            drawArrow.addPoint(-robotWideSize / 2, -robotLongSize - robotLongSize / 2);
            drawArrow.addPoint(0, -robotLongSize * 2);
            drawArrow.addPoint(robotWideSize / 2, -robotLongSize - robotLongSize / 2);
        }

        /**
         * Paint the Robot *
         */
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(getHeight() / 300));

            /** Draw the robot **/
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillPolygon(drawShape);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(drawShape);

            /** Draw the Arrow **/
            g2.setColor(new Color(0, 155, 0));
            g2.fillPolygon(drawArrow);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(drawArrow);
        }

        /**
         * Update the robots position on the minimap every tick *
         */
        public void updatePosition() {
            /** Grab information from the text file and use it to update the robots position **/
            setPos(TextHandler.getVal("x1"), TextHandler.getVal("x2"), TextHandler.getVal("y1"), TextHandler.getVal("y2"), TextHandler.getVal("y3"), TextHandler.getVal("r"));

            /** Perform various mathematical calculations to draw the robot correctly each tick **/
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

            /** Apply these calculations **/
            drawShape.invalidate();
            drawShape.translate((int) xPos, (int) yPos);
            drawArrow.invalidate();
            drawArrow.translate((int) xPos, (int) yPos);
        }

        /**
         * Set the position of the robot using information from the text file *
         */
        public void setPos(double x1, double x2, double y1, double y2, double y3, double r) {
            angle = r;
            boolean readyForUSCalibration = false;

            /** Check if the robots angle is 10 degrees off a perpendicular angle **/
            for (int i = -10; i < 11; i++)
                if (((r + i) / 90) % 1 == 0)
                    readyForUSCalibration = true;

            /** If so, calibrate the robots position using the Ultrasonic sensors **/
            if (readyForUSCalibration) {
            }
        }
    }

    /**
     * The Text Handler *
     */
    public static class TextHandler {

        /**
         * Holds the contents of the text file *
         */
        public static ArrayList<String> stationOut = new ArrayList<String>();

        /**
         * Read information from the text file and update the array contents *
         */
        public static void ReadFromStation() throws IOException {
            /** Initialize the reader **/
            File f = new File("stationOut.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String s;

            /**Clear the array contents **/
            stationOut.clear();

            /** Read the contents of the text file **/
            while ((s = br.readLine()) != null)
                stationOut.add(s);

            /** Stop the connection **/
            br.close();
        }

        /**
         * Write information to the text file and update its contents *
         */
        public static void WriteToStation() throws IOException {
            /** Initialize the writer **/
            File f = new File("minimapOut.txt");
            FileWriter fs = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fs);

            /** Stop the connection **/
            out.close();
        }

        /**
         * Get a value from the array using a prefix *
         */
        public static double getVal(String prefix) {
            double val = 0;
            try {
                /** Read from the Station and update the array contents **/
                ReadFromStation();
                val = Double.parseDouble(getLine(stationOut, prefix));
            } catch (Exception e) {
                /** If the value is empty, error **/
                System.err.println("ERROR: Read an empty line!");
            }
            return val;
        }

        /**
         * Get a line from the specified array *
         */
        public static String getLine(ArrayList<String> textDoc, String prefix) {
            for (int i = 0; i < textDoc.size(); i++) {
                if (textDoc.get(i).contains(prefix))
                    return textDoc.get(i).substring(textDoc.get(i).indexOf("=") + 1);
            }
            return "";
        }

    }

}