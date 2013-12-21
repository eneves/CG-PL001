package OpenGL;

import Logic.PersistenceManager;
import Logic.Simulator;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
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
            case 'm': //passa para o segmento seguinte
                if (this.simulator.getActualSection() <= this.simulator.getRoad().size() - 1) {
                    this.simulator.increaseActualSection();
                }
                System.out.println("m pressionada!");
                break;
            case 'n'://passa para o segmento anterior
                if (this.simulator.getActualSection() > 0) {
                    this.simulator.decreaseActualSection();
                }
                System.out.println("n pressionada!");
                break;
            case 'k'://insere segmento depois do actual
                if (this.simulator.getActualSection() > 0) {
                    this.simulator.decreaseActualSection();
                }
                System.out.println("n pressionada!");
                break;
            case 'j'://insere segmento antes do actual
                if (this.simulator.getActualSection() > 0) {
                    this.simulator.decreaseActualSection();
                }
                System.out.println("n pressionada!");
                break;
            case 'i'://insere fonte no segmento actual
                if (this.simulator.getActualSection() > 0) {
                    this.simulator.decreaseActualSection();
                }
                System.out.println("n pressionada!");
                break;
            case '�'://grava um ficheiro com a configura��o actual
                PersistenceManager.saveSimulator();
                System.out.println("Edi��o actual gravada no ficheiro de texto");
                break;
            case ' ':
                this.simulator.setIsEditorMode(false);
                break;
        }
    }

    private void keyTypedInSimulation(char chars) {
        switch (chars) {
            case 'a':
                this.eye[0] -= 0.1f;
                break;
            case 'r':
                this.eye[1] += 0.1f;
                this.center[1] += 0.1f;
                break;
            case 'd':
                this.eye[0] += 0.1f;
                break;
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
            case 's':
                this.eye[2] -= 0.1f;
                this.center[2] -= 0.1f;
                break;
            case 'w':
                this.eye[2] += 0.1f;
                this.center[2] += 0.1f;
                break;
            case ' ':
                this.simulator.setIsEditorMode(true);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("mouse clicked!");
        //System.out.println(me.getX()); //To change body of generated methods, choose Tools | Templates.
        //System.out.println(me.getXOnScreen()); //To change body of generated methods, choose Tools | Templates.
    }
}
