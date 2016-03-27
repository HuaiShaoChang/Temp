import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.geom.Line2D;
import javafx.stage.WindowEvent;
import javax.swing.JApplet;
import javax.swing.JFrame;

class aa extends JApplet {

    final boolean mode;

    public aa(boolean mode) {
        this.mode = mode;
    }

    @Override
    public void init() {
        setBackground(Color.white);
        setForeground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();
        int padding = 3;
        int fontSize = g2.getFont().getSize();
        int gridWidth = d.width - fontSize - padding*2;
        int gridHeight = d.height - fontSize - padding*2;

        g2.setPaint(Color.lightGray);
        g2.drawRect(padding + fontSize, padding, gridWidth, gridHeight);
        g2.setPaint(Color.black);

        int ox = padding + fontSize + 1;
        int oy = padding + 1;
        int stringY = padding + gridHeight + fontSize;

        // draw Label
        g2.drawString("O", padding + 3, stringY - 3);
        g2.drawString("X", padding + fontSize + gridWidth/2, stringY);
        g2.drawString("Y", padding, gridHeight/2);

        // draw graph
        for (int xi = 0, yi, xf = 1, yf; xi < 100; xi++, xf++) {
            yi = f(xi);
            yf = f(xf);
            if (this.mode) {
                g2.draw(new Line2D.Double(
                        ox + xi, padding + gridHeight - (oy + yi),
                        ox + xi, padding + gridHeight - (oy + yi)));
            } else {
                g2.draw(new Line2D.Double(
                        ox + xi, padding + gridHeight - (oy + yi),
                        ox + xf, padding + gridHeight - (oy + yf)));
            }
        }
    }

    public int f(int x) {
        int y = (x < 30) ? x*x + 2*x - 1 : 2*x - 1;
        return y / 2; // scale to 0.5
    }

    public static void main(String s[]) {
        newFrame(true);
        newFrameWithX(250, false);
    }

    public static void newFrame(boolean mode) {
        newFrameWithX(100, mode);
    }

    public static void newFrameWithX(int x, boolean mode) {
        JFrame f = new JFrame("SomeEquation");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new aa(mode);
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setLocation(x, 100);
        f.setSize(new Dimension(150, 600));
        f.setVisible(true);
    }
}
