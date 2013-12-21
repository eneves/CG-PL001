package OpenGL;

import Logic.Simulator;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import javax.media.opengl.glu.GLU;

/**
 * An OpenGL and key listener that has no action associated to key events.
 *
 * @author Pedro Mariano
 */
public class StaticListener
        implements
        GLEventListener,
        KeyListener,
        MouseListener {

    /**
     * Use a perspective or a parallel projection.
     */
    protected boolean perspectiveProjection = true;
    protected float left = -4;
    protected float right = 14;
    protected float top = 12;
    protected float bottom = -1f;
    protected float far = 100;
    protected float near = 1;
    /**
     * Camera coordinates.
     */
    protected float[] eye = new float[]{0, 12, -14};
    /**
     * Coordinates of where the camera is pointing.
     */
    protected float[] center = new float[]{0, 11.5f, -12};
    /**
     * Up vector used when setting the camera properties.
     */
    protected float[] up = new float[]{0, 1, 0};
    /**
     * The OpenGL AWT component that this listener is attached to.
     */
    protected final GLCanvas canvas;

    protected volatile Simulator simulator;
    private TextDisplayer textDisplay;

    StaticListener(GLCanvas canvas, Simulator simulator) {
        this.canvas = canvas;
        this.simulator = simulator;
        this.status();
    }

    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();      // get the OpenGL graphics context
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting

        textDisplay = new TextDisplayer();
        textDisplay.addLineDlString("this is a new line");
        textDisplay.addLineDrString("this is a new line");

        System.out.println("GLEventListener.init(GLAutoDrawable)");
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        System.out.println("GLEventListener.dispose(GLAutoDrawable)");
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();

        GLU glu = GLU.createGLU(gl);
        glu.gluLookAt(
                this.eye[0], this.eye[1], this.eye[2],
                this.center[0], this.center[1], this.center[2],
                this.up[0], this.up[1], this.up[2]
        );
        simulator.render(gl);
        updateText();
        textDisplay.render(drawable.getWidth(), drawable.getHeight());
    }

    private void updateText() {
        textDisplay.clearUlString();
        textDisplay.addLineUlString(simulator.isIsEditorMode() ? "Editor" : "Simulator");
        textDisplay.addLineUlString(simulator.isAnimationRunning() ? "Running" : "Stopped");
        textDisplay.addLineUlString("T = " + simulator.getCurrentInstant());
        textDisplay.clearDlString();
        textDisplay.addLineDlString(this.perspectiveProjection ? "Perspective" : "Parallel");
        textDisplay.addLineDlString(String.format("Left Right: %5.1f .. %5.1f", this.left, this.right));
        textDisplay.addLineDlString(String.format("Top Bottom: %5.1f .. %5.1f", this.top, this.bottom));
        textDisplay.addLineDlString(String.format("  Near Far: %5.1f .. %5.1f", this.near, this.far));
        textDisplay.addLineDlString(String.format("   Eye:  ( %5.1f , %5.1f , %5.1f )", this.eye[0], this.eye[1], this.eye[2]));
        textDisplay.addLineDlString(String.format("Center:  ( %5.1f , %5.1f , %5.1f )", this.center[0], this.center[1], this.center[2]));
        textDisplay.addLineDlString(String.format("    Up:  ( %5.1f , %5.1f , %5.1f )", this.up[0], this.up[1], this.up[2]));
        textDisplay.addLineDlString(String.format("View angle:  ( %5.1f º)", Math.toDegrees(getViewAngle())));
    }

    public float getViewAngle() {
        float x0 = center[0] - eye[0];
        float z0 = center[2] - eye[2];
        double dist = Math.sqrt(Math.pow(z0, 2) + Math.pow(x0, 2));
        return (float) Math.asin(z0 / dist);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (this.perspectiveProjection) {
            GLU glu = GLU.createGLU(gl);
            glu.gluPerspective(60, width / height, near, far);
        } else {
            gl.glOrtho(
                    left, right,
                    bottom, top,
                    near, far
            );
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    /**
     * Print the properties of the projection parameters and of the camera.
     */
    protected void status() {
        System.out.println("\n\n");
        System.out.println(this.perspectiveProjection ? "Perspective" : "Parallel");
        System.out.format("Left Right: %5.1f .. %5.1f\n", this.left, this.right);
        System.out.format("Top Bottom: %5.1f .. %5.1f\n", this.top, this.bottom);
        System.out.format("  Near Far: %5.1f .. %5.1f\n", this.near, this.far);
        System.out.format("   Eye:  ( %5.1f , %5.1f , %5.1f )\n", this.eye[0], this.eye[1], this.eye[2]);
        System.out.format("Center:  ( %5.1f , %5.1f , %5.1f )\n", this.center[0], this.center[1], this.center[2]);
        System.out.format("    Up:  ( %5.1f , %5.1f , %5.1f )\n", this.up[0], this.up[1], this.up[2]);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

}
