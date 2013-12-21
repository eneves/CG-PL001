package OpenGL;

import Logic.Simulator;
import Logic.Source;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.awt.GLCanvas;

/**
 * An example of a listener that reacts to some key events.
 *
 * @author Pedro Mariano
 */
public class AppListener
        extends StaticListener {

    public AppListener(GLCanvas canvas, Simulator simulator) {
        super(canvas, simulator);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (this.simulator.isIsEditorMode()) {
            keyTypedInEdition(ke.getKeyChar());
        } else {
            keyTypedInSimulation(ke.getKeyChar());
        }
        this.canvas.display();
    }

    private void keyTypedInEdition(char chars) {
        switch (chars) {
            case 'l':
                System.out.println("l pressionada!");
                break;
            case ' ':
                this.simulator.setIsEditorMode(false);
                break;
        }
    }

    private void keyTypedInSimulation(char chars) {

        float viewAngle = getViewAngle();
        switch (chars) {
            case 'a':
                this.eye[0] += 0.1f * Math.sin(viewAngle);
                this.center[0] += 0.1f * Math.sin(viewAngle);
                this.eye[2] -= 0.1f * Math.cos(viewAngle);
                this.center[2] -= 0.1f * Math.cos(viewAngle);
                break;
            case 'd':
                this.eye[0] -= 0.1f * Math.sin(viewAngle);
                this.center[0] -= 0.1f * Math.sin(viewAngle);
                this.eye[2] += 0.1f * Math.cos(viewAngle);
                this.center[2] += 0.1f * Math.cos(viewAngle);
                break;
            case 's':
                this.eye[0] -= 0.1f * Math.cos(viewAngle);
                this.center[0] -= 0.1f * Math.cos(viewAngle);
                this.eye[2] -= 0.1f * Math.sin(viewAngle);
                this.center[2] -= 0.1f * Math.sin(viewAngle);
                break;
            case 'w':
                this.eye[0] += 0.1f * Math.cos(viewAngle);
                this.center[0] += 0.1f * Math.cos(viewAngle);
                this.eye[2] += 0.1f * Math.sin(viewAngle);
                this.center[2] += 0.1f * Math.sin(viewAngle);
                break;
            case ' ':
                this.simulator.setIsEditorMode(true);
                break;
            case 'p':
                toogleAnimation();
                break;
            case 'o':
                if (this.simulator.hadSelection()) {
                    Source source = this.simulator.getSelectedSection().getSource();
                    if (source != null) {
                        source.setOn(!source.isOn());
                    }
                }
                break;
            // TODO: Check this keys
            case 'f':
                this.eye[1] -= 0.1f;
                this.center[1] -= 0.1f;
                break;
            case 'z':
                this.eye[1] -= 0.1f;
                break;
            case 'x':
                this.eye[1] += 0.1f;
                break;
            case 'q':
                double h = Math.sqrt(Math.pow(this.center[0], 2) + Math.pow(this.center[2], 2));
                double angle = Math.asin(this.center[0] / h);
                this.center[0] = (float) (h * Math.sin(angle + 0.17));
                this.center[2] = (float) (h * Math.cos(angle + 0.17));
                break;
            case 'e':
                h = Math.sqrt(Math.pow(this.center[0], 2) + Math.pow(this.center[2], 2));
                angle = Math.asin(center[0] / h);
                this.center[0] = (float) (h * Math.sin(angle - 0.17));
                this.center[2] = (float) (h * Math.cos(angle - 0.17));
                break;
            case 'r':
                this.eye[1] += 0.1f;
                this.center[1] += 0.1f;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        /*if (simulator.isIsEditorMode()) {

         } else {*/
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                simulator.selectLeft();
                break;
            case KeyEvent.VK_RIGHT:
                simulator.selectRight();
                break;
            case KeyEvent.VK_DOWN:
                simulator.removeSelection();
                break;
        }
        //}
        this.canvas.display();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("mouse clicked!");
        //System.out.println(me.getX()); //To change body of generated methods, choose Tools | Templates.
        //System.out.println(me.getXOnScreen()); //To change body of generated methods, choose Tools | Templates.
    }

    public void toogleAnimation() {
        if (simulator.isAnimationRunning()) {
            simulator.toogleAnimation();
        } else {
            simulator.toogleAnimation();
            AnimationThread animation = new AnimationThread();
            new Thread(animation).start();
        }
    }

    class AnimationThread implements Runnable {

        @Override
        public void run() {
            while (simulator.isAnimationRunning()) {
                try {
                    simulator.incrementInstant();
                    canvas.display();
                    TimeUnit.MILLISECONDS.sleep(150);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Thread finished!");
        }
    }
}
