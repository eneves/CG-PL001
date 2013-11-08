package CGPL;

import Logic.PersistenceManager;
import Logic.Simulator;
import OpenGL.ExampleListener;
import OpenGL.StaticListener;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

/**
 *
 *
 *
 *
 *
 */
public class CGPL001 {

    public static void main(String[] args) {

        Simulator simulator = PersistenceManager.loadSimulator("highway_data", true);

        System.out.print(simulator.toString());
        for (int i = 0; i < 20; i++) {
            simulator.incrementInstant();
            System.out.print(simulator.toString());
        }

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        Frame frame = new Frame("Controlo da câmara e dos parâmetros da projecção");
        frame.setSize(640, 480);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        StaticListener listener;
        listener = new ExampleListener(canvas,simulator);
//		listener = new ProjectionListener (canvas);
//		listener = new LineCameraListener (canvas, new float[] {3, -1, -1}, new float[] {3,1,1});
//		listener = new RotatingLineCameraListener (canvas, new float[] {3, -1, -1}, new float[] {3,1,1});
        canvas.addGLEventListener(listener);
        canvas.addKeyListener(listener);
    }
}
