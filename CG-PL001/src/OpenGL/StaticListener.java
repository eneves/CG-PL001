package OpenGL;

import Logic.Simulator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

/**
 * An OpenGL and key listener that has no action associated to key events.
 *
 * @author Pedro Mariano
 */
public class StaticListener
        implements
        GLEventListener,
        KeyListener {

    /**
     * Use a perspective or a parallel projection.
     */
    protected boolean perspectiveProjection = true;
    protected float left = -3;
    protected float right = 3;
    protected float top = 3;
    protected float bottom = -3;
    protected float far = 4;
    protected float near = 1;
    /**
     * Camera coordinates.
     */
    protected float[] eye = new float[]{0, 0, 3};
    /**
     * Coordinates of where the camera is pointing.
     */
    protected float[] center = new float[]{0, 0, 1};
    /**
     * Up vector used when setting the camera properties.
     */
    protected float[] up = new float[]{0, 1, 0};
    /**
     * The OpenGL AWT component that this listener is attached to.
     */
    protected final GLCanvas canvas;

    protected final Simulator simulator;

    StaticListener(GLCanvas canvas, Simulator simulator) {
        this.canvas = canvas;
        this.simulator = simulator;
        this.status();
    }

    @Override
    public void init(GLAutoDrawable glad) {
        System.out.println("GLEventListener.init(GLAutoDrawable)");
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        System.out.println("GLEventListener.dispose(GLAutoDrawable)");
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        GLU glu = GLU.createGLU(gl);
        glu.gluLookAt(
                this.eye[0], this.eye[1], this.eye[2],
                this.center[0], this.center[1], this.center[2],
                this.up[0], this.up[1], this.up[2]
        );

        simulator.render(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (this.perspectiveProjection) {
            gl.glFrustum(
                    left, right,
                    bottom, top,
                    near, far
            );
        } else {
             gl.glOrtho(
             left, right,
             bottom, top,
             near, far
             );
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
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

}
