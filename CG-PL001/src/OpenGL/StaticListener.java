package OpenGL;

import Logic.Section;
import Logic.Simulator;
import Logic.Simulator.ViewportSize;
import Logic.Source;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_LINEAR_MIPMAP_LINEAR;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL.GL_REPEAT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_S;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.GL2GL3.GL_QUADS;
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
        KeyListener {

    /**
     * Use a perspective or a parallel projection.
     */
    protected boolean perspectiveProjection = true;
    protected float left = -4;
    protected float right = 14;
    protected float top = 12;
    protected float bottom = -1f;
    protected float far = 500;
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

    public static HashMap<String, AppTexture> textureDic;

    StaticListener(GLCanvas canvas, Simulator simulator) {
        this.canvas = canvas;
        this.simulator = simulator;
        if (textureDic == null) {
            textureDic = new HashMap<>();
        }
        this.status();
    }

    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();      // get the OpenGL graphics context
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting   
        gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        //gl.glEnable(GL_LIGHTING);
        //gl.glEnable(GL_LIGHT0);
        //float[] floatArray = {50,50,50};
        //gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, floatArray,0);
        //gl.glLightf(GL_LIGHT0, GL_DIFFUSE, 100);
        //gl.glEnable(GL_CULL_FACE);
        //gl.glCullFace(GL_BACK);
        //gl.glEnable(GL_COLOR_MATERIAL);
        //gl.glColorMaterial(GL.GL_FRONT, GL_DIFFUSE);

        // Load all textures
        loadTextures(gl);

        textDisplay = new TextDisplayer();

        //float[] floatArray = {0,50,50};
        //gl.glLightfv(GL_LIGHT0, GL_POSITION,floatArray ,0);
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
        
        //int currentInstant = simulator.getCurrentInstant();
        float hourDayFactor = (float)Math.abs( Math.cos(Math.toRadians((double) simulator.getCurrentInstant())));

        float red = 0.53f * hourDayFactor;
        float green = 0.81f * hourDayFactor;
        float blue = 0.93f * hourDayFactor;

        gl.glClearColor(red, green, blue, 1.0f);
        gl.glLoadIdentity();
        GLU glu = GLU.createGLU(gl);
        glu.gluLookAt(
                this.eye[0], this.eye[1], this.eye[2],
                this.center[0], this.center[1], this.center[2],
                this.up[0], this.up[1], this.up[2]
        );

        gl.glColor3f(1, 1, 1);

        // create grass plane
        AppTexture grass = StaticListener.textureDic.get("grass");
        if (grass != null && grass.isSuccess()) {
            grass.getTexture().enable(gl);
            grass.getTexture().bind(gl);
        }
        float maxSize = 10000;
        float repeatRacio = maxSize / 10.0f;
        gl.glBegin(GL_QUADS); // of the color cube

        gl.glTexCoord2f(repeatRacio, 0);
        gl.glVertex3f(-maxSize, -0.3f, maxSize);
        gl.glTexCoord2f(repeatRacio, repeatRacio);
        gl.glVertex3f(maxSize, -0.3f, maxSize);
        gl.glTexCoord2f(0, repeatRacio);
        gl.glVertex3f(maxSize, -0.3f, -maxSize);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-maxSize, -0.3f, -maxSize);

        gl.glEnd();

        if (grass != null && grass.isSuccess()) {
            grass.getTexture().disable(gl);
        }

        simulator.render(gl);
        updateText();
        textDisplay.render(drawable.getWidth(), drawable.getHeight());
    }

    private void updateText() {
        textDisplay.clearUlString();
        textDisplay.addLineUlString(simulator.isIsEditorMode() ? "Editor" : "Simulator");
        textDisplay.addLineUlString(simulator.isAnimationRunning() ? "Running" : "Stopped");
        textDisplay.addLineUlString("T = " + simulator.getCurrentInstant());
        textDisplay.addLineUlString("Road size = " + (simulator.getRoad().size() - 2));
        textDisplay.clearDlString();
        if (!simulator.isIsEditorMode()) {
            textDisplay.addLineDlString(this.perspectiveProjection ? "Perspective" : "Parallel");
            textDisplay.addLineDlString(String.format("Left Right: %5.1f .. %5.1f", this.left, this.right));
            textDisplay.addLineDlString(String.format("Top Bottom: %5.1f .. %5.1f", this.top, this.bottom));
            textDisplay.addLineDlString(String.format("  Near Far: %5.1f .. %5.1f", this.near, this.far));
            textDisplay.addLineDlString(String.format("   Eye:  ( %5.1f , %5.1f , %5.1f )", this.eye[0], this.eye[1], this.eye[2]));
            textDisplay.addLineDlString(String.format("Center:  ( %5.1f , %5.1f , %5.1f )", this.center[0], this.center[1], this.center[2]));
            textDisplay.addLineDlString(String.format("    Up:  ( %5.1f , %5.1f , %5.1f )", this.up[0], this.up[1], this.up[2]));
            textDisplay.addLineDlString(String.format("View angle:  ( %5.1f º)", Math.toDegrees(getViewAngle())));
        }
        textDisplay.clearUrString();
        if (simulator.hadSelection()) {
            Section section = simulator.getSelectedSection();
            textDisplay.addLineUrString(String.format("Angle:  ( %5.1f º)", section.getAngle()));
            Source source = section.getSource();
            textDisplay.addLineUrString(source != null ? "Period : " + source.getPeriod() : "No source");
        } else {
            textDisplay.addLineUrString("No selection!");
        }
    }

    public float getViewAngle() {
        float x0 = center[0] - eye[0];
        return (float) Math.acos(x0 / distanceEyeToCenter());
    }

    public float distanceEyeToCenter() {
        float x0 = center[0] - eye[0];
        float z0 = center[2] - eye[2];
        return (float) Math.sqrt(Math.pow(z0, 2) + Math.pow(x0, 2));
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (this.simulator.isIsEditorMode()) {
            ViewportSize vp = simulator.getViewport();
            up = new float[]{1, 0, 0};
            eye = new float[]{0, 200, 0};
            center = new float[]{0, 150, 0};
            //System.out.println("Center ->(" + vp.getxCenter() + "," + vp.getzCenter() + ")");
            //System.out.println("Min ->(" + vp.getxMin() + "," + vp.getzMin() + ")");
            //System.out.println("Max ->(" + vp.getxMax() + "," + vp.getzMax() + ")");
            gl.glOrtho(
                    vp.getzMin(), vp.getzMax(),
                    vp.getxMin(), vp.getxMax(),
                    /*vp.getMin(), vp.getMax(),
                     vp.getMin(), vp.getMax(),*/
                    1, 1000
            );
        } else {
            up = new float[]{0, 1, 0};
            eye = new float[]{0, 12, -14};
            center = new float[]{0, 11.5f, -12};
            GLU glu = GLU.createGLU(gl);
            //GLContext gLContext = drawable.getContext();
            //gLContext.makeCurrent();
            glu.gluPerspective(60, width / height, near, far);
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

    private void loadTextures(GL2 gl) {
        if (!textureDic.containsKey("test")) {
            textureDic.put("test", new AppTexture("resources/test.jpg", gl));
        }
        if (!textureDic.containsKey("car_top")) {
            textureDic.put("car_top", new AppTexture("resources/car_top.jpg", gl));
        }
        if (!textureDic.containsKey("house_back")) {
            textureDic.put("house_back", new AppTexture("resources/house_back_wall.jpg", gl));
        }
        if (!textureDic.containsKey("house_front")) {
            textureDic.put("house_front", new AppTexture("resources/house_front_wall.jpg", gl));
        }
        if (!textureDic.containsKey("house_side")) {
            textureDic.put("house_side", new AppTexture("resources/house_side_wall.jpg", gl));
        }
        if (!textureDic.containsKey("house_roof")) {
            textureDic.put("house_roof", new AppTexture("resources/house_roof.jpg", gl));
        }
        if (!textureDic.containsKey("grass")) {
            AppTexture newTexture = new AppTexture("resources/grass.jpg", gl);
            newTexture.getTexture().setTexParameterf(gl, GL.GL_TEXTURE_WRAP_T, GL_REPEAT);
            newTexture.getTexture().setTexParameterf(gl, GL.GL_TEXTURE_WRAP_S, GL_REPEAT);
            //newTexture.getTexture().setTexParameterf(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            newTexture.getTexture().setTexParameterf(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            textureDic.put("grass", newTexture);
        }
        if (!textureDic.containsKey("road")) {
            AppTexture newTexture = new AppTexture("resources/road.jpg", gl);
            textureDic.put("road", newTexture);
        }

    }

}
