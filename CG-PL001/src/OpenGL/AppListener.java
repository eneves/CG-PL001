package OpenGL;

import Logic.PersistenceManager;
import Logic.Section;
import Logic.Simulator;
import Logic.Source;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
            case 'm': //adiciona segmento antes
                if (this.simulator.hadSelection()) {
                    ArrayList<Section> road = this.simulator.getRoad();
                    ArrayList<Section> newRoad = new ArrayList();
                    int index = road.indexOf(this.simulator.getSelectedSection());
                    int i = 0;
                    float z = 10;
                    for (Section s : road) {
                        if (i == index) {
                            Section newSection = new Section(false, i == 1);
                            newSection.setOriginX(0);
                            newSection.setOriginY(0);
                            newSection.setOriginZ(z * i);
                            newSection.setAngle(s.getAngle());
                            newRoad.add(i, newSection);
                            i++;
                        }
                        if (s.isAuxiliar()) {
                            s.setOriginZ(0);
                        } else {
                            s.setOriginZ(z * i);
                            if (s.hasSource()) {
                                s.getSource().setOriginZ(s.getOriginZ());
                            }
                        }
                        newRoad.add(i, s);
                        i++;
                    }
                    this.simulator.setRoad(newRoad);
                }
                break;
            case 'n': //adiciona segmento depois
                if (this.simulator.hadSelection()) {
                    ArrayList<Section> road = this.simulator.getRoad();
                    ArrayList<Section> newRoad = new ArrayList();
                    int index = road.indexOf(this.simulator.getSelectedSection());
                    int i = 0;
                    float z = 10;
                    for (Section s : road) {
                        if (!s.isAuxiliar()) {
                            s.setOriginZ(z * i);
                            if (s.hasSource()) {
                                s.getSource().setOriginZ(s.getOriginZ());
                            }
                        }
                        newRoad.add(s);
                        if (i == index) {
                            Section newSection = new Section(false, i+1 == 1);
                            newSection.setOriginX(0);
                            newSection.setOriginY(0);
                            newSection.setOriginZ(z * (i+1));
                            newSection.setAngle(s.getAngle());
                            newRoad.add(newSection);
                            i++;
                        }
                        i++;
                    }
                    this.simulator.setRoad(newRoad);
                }
                break;
            case 'b': //remove segmento
                if (this.simulator.hadSelection() && this.simulator.getRoad().size() > 3 ) {
                    ArrayList<Section> road = this.simulator.getRoad();
                    int index = road.indexOf(this.simulator.getSelectedSection());
                    road.remove(index);
                    this.simulator.removeSelection();
                    for(int i=index; i<road.size()-1; i++){
                        road.get(i).setOriginZ(10*(i-1));
                        if(road.get(i).hasSource()){
                            road.get(i).getSource().setOriginZ(road.get(i).getOriginZ());
                        }
                    }
                }
                break;
            case 'k': //insere fonte com periodo por default = 1
                if (this.simulator.hadSelection()) {
                    ArrayList<Section> road = this.simulator.getRoad();
                    int index = road.indexOf(this.simulator.getSelectedSection());
                    Section s = this.simulator.getSelectedSection();
                    if (!s.hasSource()) {
                        Source source = PersistenceManager.createSource(index, 1, s);
                        s.setSource(source);
                    }
                }
                break;
            case 'j': //remove fonte
                if (this.simulator.hadSelection()) {
                    Section s = this.simulator.getSelectedSection();
                    if (s.hasSource()) {
                        s.setSource(null);
                    }
                }
                break;
            case 'l': //aumenta periodo da fonte
                if (this.simulator.hadSelection()) {
                    Section s = this.simulator.getSelectedSection();
                    if (s.hasSource()) {
                        s.getSource().incrementPeriod();
                    }
                }
                break;
            case 'o': //diminui periodo da fonte
                if (this.simulator.hadSelection()) {
                    Section s = this.simulator.getSelectedSection();
                    if (s.hasSource() && s.getSource().getPeriod() > 1) {
                        s.getSource().decrementPeriod();
                    }
                }
                break;
            case 'i': //aumentar ângulo
                if (this.simulator.hadSelection()) {
                    Section s = this.simulator.getSelectedSection();
                    this.simulator.getSelectedSection().setAngle((float) (s.getAngle() + 0.1));
                }
                break;
            case 'u': //diminui ângulo
                if (this.simulator.hadSelection()) {
                    Section s = this.simulator.getSelectedSection();
                    this.simulator.getSelectedSection().setAngle((float) (s.getAngle() - 0.1));
                }
                break;
            case 'ç'://grava um ficheiro com a configuração actual
                PersistenceManager.saveSimulator(this.simulator.getRoad());
                System.out.println("Edição actual gravada no ficheiro de texto");
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
            case 'e':
                float sinal = center[2] - eye[2] < 0 ? -1.0f : 1.0f;
                float newAngle = viewAngle + 0.17f;
                if (sinal < 0) {
                    newAngle = (float) (2 * Math.PI - (viewAngle - 0.17f));
                }
                this.center[0] = eye[0] + (float) (2.0f * Math.cos(newAngle));
                this.center[2] = eye[2] + (float) (2.0f * Math.sin(newAngle));
                break;
            case 'q':
                sinal = center[2] - eye[2] < 0 ? -1.0f : 1.0f;
                newAngle = viewAngle - 0.17f;
                if (sinal < 0) {
                    newAngle = (float) (2 * Math.PI - (viewAngle + 0.17f));
                }
                this.center[0] = eye[0] + (float) (2.0f * Math.cos(newAngle));
                this.center[2] = eye[2] + (float) (2.0f * Math.sin(newAngle));
                break;
            case ' ':
                this.simulator.setIsEditorMode(true);
                if (this.simulator.isAnimationRunning()) {
                    this.simulator.toogleAnimation();
                }
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
            case 'r':
                this.eye[1] += 0.1f;
                this.center[1] += 0.1f;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        /*if (simulator.isIsEditorMode()) {

         } else {

         }*/
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
