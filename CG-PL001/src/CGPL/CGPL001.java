package CGPL;

import Logic.PersistenceManager;
import Logic.Simulator;
import OpenGL.AppListener;
import OpenGL.StaticListener;
import java.awt.Frame;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
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

    private static GLCanvas canvas;

    public static void main(String[] args) {

        Simulator simulator = PersistenceManager.loadSimulator("highway_data.txt", true);

        startOpenGLWindow(simulator);
    }

    public static void startOpenGLWindow(Simulator simulator) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        canvas = new GLCanvas(caps);

        Frame frame = new Frame("Auto-Estrada");
        frame.setSize(1024, 768);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setResizable(false);

        StaticListener listener;
        listener = new AppListener(canvas, simulator);
//		listener = new ProjectionListener (canvas);
//		listener = new LineCameraListener (canvas, new float[] {3, -1, -1}, new float[] {3,1,1});
//		listener = new RotatingLineCameraListener (canvas, new float[] {3, -1, -1}, new float[] {3,1,1});
        canvas.addGLEventListener(listener);
        canvas.addKeyListener(listener); 
        canvas.addMouseListener( listener);
    }
}
