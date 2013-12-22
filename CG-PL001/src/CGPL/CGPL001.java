package CGPL;

import Logic.PersistenceManager;
import Logic.Simulator;
import OpenGL.AppListener;
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

    private static GLCanvas canvas;

    public static void main(String[] args) {

        String filename = null;
        boolean editorMode = false;
        if (args.length == 2) {
            filename = args[1];
        }
        if (args.length > 0) {
            editorMode = args[0].equals("e");
        }
        Simulator simulator = PersistenceManager.loadSimulator(filename, editorMode);

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
        canvas.addGLEventListener(listener);
        canvas.addKeyListener(listener);
    }
}
