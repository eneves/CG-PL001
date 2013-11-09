package OpenGL;

import Logic.Simulator;
import java.awt.event.KeyEvent;
import javax.media.opengl.awt.GLCanvas;

/**
 * An example of a listener that reacts to some key events.
 *
 * @author Pedro Mariano
 */
public class ExampleListener
        extends StaticListener {

    public ExampleListener(GLCanvas canvas,Simulator simulator) {
        super(canvas,simulator);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        switch (ke.getKeyChar()) {
            case 'a':
                this.eye[0] -= 0.1f;
                break;
            case 's':
                this.eye[1] += 0.1f;
                this.center[1] += 0.1f;
                break;
            case 'd':
                this.eye[0] += 0.1f;
                break;
            case 'w':
                this.eye[1] -= 0.1f;
                this.center[1] -= 0.1f;
                break;
            case 'z':
                this.eye[1] -= 0.1f;
                break;
            case 'x':
                this.eye[1] += 0.1f;
                break;
            case ' ':
                this.perspectiveProjection = !this.perspectiveProjection;
                break;
            default:
                return;
        }
        this.canvas.setBounds(10, 10, 640, 480);
        this.canvas.display();
        this.status();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                this.center[0] -= 0.1f;
                break;
            default:
                return;
        }
        this.canvas.setBounds(10, 10, 640, 480);
        this.canvas.display();
        this.status();
    }
}
