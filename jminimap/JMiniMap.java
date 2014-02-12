import org.omg.CORBA.COMM_FAILURE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
     * The Locator *
     */
    private final CoordLocator locator = new CoordLocator();

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
        locator.triptest();
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
     * The Coordinate Handler *
     */
    public class CoordLocator {
        /**
         * This Class solves the following problem;
         * A robot is within an Arena.
         * The robot is at the location (x0,y0), and oriented at angle theta
         * Because of symmetry, theta is assumed to be between 0 and 90 degrees
         * Notations;
         * 0 Degrees         = North
         * 90 Degrees        = East
         * 180 Degrees       = South
         * 270 Degrees       = West
         * yAxis             = North-South
         * xAxis             = East-West
         * North Border      = T (TOP)
         * East Border       = R (RIGHT)
         * South Border      = B (BOTTOM)
         * West Border       = L (LEFT)
         * Robot Alignment is denoted by 4 letters
         * Thus, designation TRLL means;
         * T is to the front,
         * R is to the right,
         * L is to the back,
         * L is to the left.
         * The formula for each alignment is different
         * Thus, by calculating sin ^ 2 + cos ^ 2, one can quickly see what alignment(s) are possible
         * Variable a is used to denote the alternate (wall not seen)
         */

        public double[] distances, states, answers;
        public double tolerance = 1e-5;

        public void locateRobot() {
            double F, R, B, L;
            double Fa, Ra, Ba, La;

            double x0, y0, theta, c, s;

            F = distances[0];
            R = distances[1];
            B = distances[2];
            L = distances[3];

            /** TRBL & RBLT Cases, AKA the simplest of cases **/
            if ((F + B >= 2) && Math.abs(F + B - R - L) < tolerance) {
                c = 2 / (F + B);
                theta = Math.acos(c);
                s = Math.sin(theta);
                y0 = -(F - B) / (F + B);
                x0 = -(R - L) / (R + L);
                Fa = (1 - x0) / s;
                Ra = (1 + y0) / s;
                Ba = (1 + x0) / s;
                La = (1 - y0) / s;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }

                x0 = -(F - B) / (F + B);
                y0 = (R - L) / (F + B);
                s = 2 / (F + B);
                theta = Math.asin(s);
                c = Math.cos(theta);
                Fa = (1 - y0) / c;
                Ra = (1 - x0) / c;
                Ba = (y0 + 1) / c;
                La = (x0 + 1) / c;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** RRBL Case **/
            if (Math.abs((4 * (R * R + F * F)) / (F * F * (R + L)) - 1) < tolerance) {
                c = 2 / (R + L);
                theta = Math.acos(c);
                x0 = -(R - L) / (R + L);
                y0 = -(R + L - 2 * B) / (R + L);
                s = (2 * R) / (F * R + F * L);
                Fa = (1 - y0) / c;
                Ra = (1 + y0) / s;
                Ba = (1 + x0) / s;
                La = (1 - y0) / s;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** RRLT Case **/
            if (Math.abs((4 * (R * R + F * F)) / ((F + B) * (F + B) * R * R) - 1) < tolerance) {
                x0 = -(F - B) / (F + B);
                s = 2 / (F + B);
                c = (2 * F) / ((F + B) * R);
                theta = Math.acos(c);
                y0 = -(2 * L - F - B) / (F + B);
                Fa = (1 - y0) / c;
                Ra = (y0 + 1) / s;
                Ba = (y0 + 1) / c;
                La = (x0 + 1) / c;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** RBBL Case **/
            if (Math.abs((4 * (R * R + B * B)) / ((L * R + B * F) * (L * R + B * F)) - 1) < tolerance) {
                x0 = (L * R - B * F) / (L * R + B * F);
                s = (2 * B) / (L * R + B * F);
                c = (2 * R) / (L * R + B * F);
                theta = Math.acos(c);
                y0 = -(L * R - 2 * B * R + B * F) / (L * R + B * F);
                Fa = (1 - y0) / c;
                Ra = (1 - x0) / c;
                Ba = (x0 + 1) / s;
                La = (1 - y0) / s;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TRLL Case **/
            if (Math.abs((4 * (L * L + B * B)) / (B * B * (R + L) * (R + L)) - 1) < tolerance) {
                y0 = (R + L - 2 * F) / (R + L);
                c = 2 / (R + L);
                theta = Math.acos(c);
                s = (2 * L) / (B * (R + L));
                x0 = -(R - L) / (R + L);
                Fa = (1 - x0) / s;
                Ra = (y0 + 1) / s;
                Ba = (y0 + 1) / c;
                La = (1 - y0) / s;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TBBL Case **/
            if (Math.abs((4 * (R * R + B * B)) / ((F + B) * (F + B) * R * R) - 1) < tolerance) {
                c = 2 / (F + B);
                s = (2 * B) / ((F + B) * R);
                y0 = -(F - B) / (F + B);
                x0 = (2 * L - F - B) / (F + B);
                Fa = (1 - x0) / s;
                Ra = (1 - x0) / c;
                Ba = (x0 + 1) / s;
                La = (1 - y0) / s;
                theta = Math.acos(c);
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TBLT Case **/
            if (Math.abs((4 * (L * L + F * F)) / (F * F * (R + L) * (R + L)) - 1) < tolerance) {
                c = (2 * L) / (F * (R + L));
                s = 2 / (R + L);
                y0 = (R - L) / (R + L);
                x0 = -(R + L - 2 * B) / (R + L);
                theta = Math.acos(c);
                Fa = (1 - x0) / s;
                Ra = (1 - x0) / c;
                Ba = (y0 + 1) / c;
                La = (x0 + 1) / c;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                }
            }

            /** RBLL Case **/
            if (Math.abs((4 * (L * L + B * B)) / ((F + B) * (F + B) * L * L) - 1) < tolerance) {
                s = 2 / (F + B);
                y0 = (2 * R - F - B) / (F + B);
                x0 = -(F - B) / (F + B);
                c = (2 * B) / ((F + B) * L);
                theta = Math.acos(c);
                Fa = (1 - y0) / c;
                Ra = (1 - x0) / c;
                Ba = (y0 + 1) / c;
                La = (1 - y0) / s;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** RRBT Case **/
            if (Math.abs((4 * (R * R + F * F)) / ((L * R + B * F) * (L * R + B * F)) - 1) < tolerance) {
                s = (2 * R) / (L * R + B * F);
                c = (2 * F) / (L * R + B * F);
                x0 = (L * R - 2 * F * R + B * F) / (L * R + B * F);
                y0 = -(L * R - B * F) / (L * R + B * F);
                theta = Math.acos(c);
                Fa = (1 - y0) / c;
                Ra = (y0 + 1) / s;
                Ba = (x0 + 1) / s;
                La = (x0 + 1) / c;
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TRBT Case **/
            if (Math.abs((4 * (L * L + F * F)) / ((F + B) * (F + B) * L * L) - 1) < tolerance) {
                c = 2 / (F + B);
                x0 = -(2 * R - F - B) / (F + B);
                y0 = -(F - B) / (F + B);
                s = (2 * F) / ((F + B) * L);
                Fa = (1 - x0) / s;
                Ra = (y0 + 1) / s;
                Ba = (x0 + 1) / s;
                La = (x0 + 1) / c;
                theta = Math.acos(c);
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** RBBT Case **/
            if (Math.abs((4 * (R * R + B * B)) / (B * B * (R + L) * (R + L)) - 1) < tolerance) {
                s = 2 / (R + L);
                y0 = (R - L) / (R + L);
                x0 = (R + L - 2 * F) / (R + L);
                c = (2 * R) / (B * (R + L));
                Fa = (1 - y0) / c;
                Ra = (1 - x0) / c;
                Ba = (x0 + 1) / s;
                La = (x0 + 1) / c;
                theta = Math.acos(c);
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TBLL Case **/
            if (Math.abs((4 * (L * L + B * B)) / ((L * R + B * F) * (L * R + B * F)) - 1) < tolerance) {
                c = (2 * B) / (L * R + B * F);
                y0 = (L * R - B * F) / (L * R + B * F);
                x0 = -(L * R - 2 * B * L + B * F) / (L * R + B * F);
                s = (2 * L) / (L * R + B * F);
                Fa = (1 - x0) / s;
                Ra = (1 - x0) / c;
                Ba = (y0 + 1) / c;
                La = (1 - y0) / s;
                theta = Math.acos(c);
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }

            /** TRLT Case **/
            if (Math.abs((4 * (L * L + F * F)) / ((L * R + B * F) * (L * R + B * F)) - 1) < tolerance) {
                c = (2 * L) / (L * R + B * F);
                y0 = (L * R - 2 * F * L + B * F) / (L * R + B * F);
                x0 = -(L * R - B * F) / (L * R + B * F);
                s = (2 * F) / (L * R + B * F);
                Fa = (1 - x0) / s;
                Ra = (y0 + 1) / s;
                Ba = (y0 + 1) / c;
                La = (x0 + 1) / c;
                theta = Math.acos(c);
                if (F <= Fa && R <= Ra && B <= Ba && L <= La) {
                    states[0] = x0;
                    states[1] = y0;
                    states[2] = theta * 180.0 / Math.PI;
                    return;
                }
            }
        }

        public void triptest() {
            distances = new double[4];
            states = new double[3];
            answers = new double[3];
            try {
                File f = new File("triptest.txt");
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String s;
                while ((s = br.readLine()) != null) {
                    distances[0] = Double.parseDouble(s.split(" ")[0]);
                    distances[1] = Double.parseDouble(s.split(" ")[1]);
                    distances[2] = Double.parseDouble(s.split(" ")[2]);
                    distances[3] = Double.parseDouble(s.split(" ")[3]);
                    answers[0] = Double.parseDouble(s.split(" ")[4]);
                    answers[1] = Double.parseDouble(s.split(" ")[5]);
                    answers[2] = Double.parseDouble(s.split(" ")[6]);
                    locateRobot();
                    for (int i = 0; i < 3; ++i) {
                        String prefix = "SUCCESS";
                        if (i < 2)
                            if (Math.abs(states[i] - answers[i]) > 0.01)
                                prefix = "FAIL";
                            else if (Math.abs(states[i] - answers[i]) > 1)
                                prefix = "FAIL";
                        System.out.println(prefix + ": calc: " + states[i] + " | answer: " + answers[i]);
                    }
                }

                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}