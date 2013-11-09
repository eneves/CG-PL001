/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;

/**
 *
 * @author Emanuel
 */
public class Car {

    private Section destination;
    private float originX;
    private float originY;
    private float originZ;

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public float getOriginZ() {
        return originZ;
    }

    public void setOriginZ(float originZ) {
        this.originZ = originZ;
    }

    public void render(GL2 gl) {
        //gl.glLoadIdentity();                        // reset the model-view matrix
        gl.glTranslatef(originX, originY, originZ); // translate to relative axe
        //gl.glRotatef(90, 0, 1, 1);

        gl.glBegin(GL_QUADS); // of the color cube

        gl.glColor3f(0.0f, 1.0f, 0.0f); // green
        // Back-face
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.5f, 0.0f);
        gl.glVertex3f(2.0f, 1.5f, 0.0f);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);

        gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        // Left-face
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glVertex3f(2.0f, 1.5f, 0.0f);
        gl.glVertex3f(2.0f, 1.5f, -5.0f);
        gl.glVertex3f(2.0f, 0.0f, -5.0f);

        gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        // Right-face
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.5f, 0.0f);
        gl.glVertex3f(0.0f, 1.5f, -5.0f);
        gl.glVertex3f(0.0f, 0.0f, -5.0f);

        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        // Top-face
        gl.glVertex3f(0.0f, 1.5f, 0.0f);
        gl.glVertex3f(0.0f, 1.5f, -5.0f);
        gl.glVertex3f(2.0f, 1.5f, -5.0f);
        gl.glVertex3f(2.0f, 1.5f, 0.0f);

        gl.glColor3f(0.0f, 1.0f, 1.0f); // cyan
        // Bottom-face
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, -5.0f);
        gl.glVertex3f(2.0f, 0.0f, -5.0f);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);

        gl.glColor3f(1.0f, 0.0f, 1.0f); // purple
        // Front-face
        gl.glVertex3f(0.0f, 0.0f, -5.0f);
        gl.glVertex3f(0.0f, 1.5f, -5.0f);
        gl.glVertex3f(2.0f, 1.5f, -5.0f);
        gl.glVertex3f(2.0f, 0.0f, -5.0f);

        gl.glEnd();

        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe
    }

}
