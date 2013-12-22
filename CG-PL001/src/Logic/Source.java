/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.Random;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;

/**
 *
 * @author Emanuel
 */
public class Source {

    private int period;
    private int currentTick;
    private float originX;
    private float originY;
    private float originZ;
    private float[] color;
    private static Random rd = new Random();
    private boolean on;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public float[] getColor() {
        return color;
    }

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

    public Source(int period) {
        this.period = period;
        currentTick = 0;

        this.color = new float[3];
        this.color[0] = rd.nextFloat();
        this.color[1] = rd.nextFloat();
        this.color[2] = rd.nextFloat();
    }

    public int getPeriod() {
        return period;
    }

    public void incrementPeriod() {
        period++;
    }

    public void decrementPeriod() {
        period--;
    }
  
    public void incrementTick() {
        currentTick++;
    }

    public void resetTick() {
        currentTick = 0;
    }

    public boolean putCar() {
        return currentTick == period;
    }

    public void render(GL2 gl) {
        gl.glTranslatef(originX, originY, originZ); // translate to relative axe
        gl.glBegin(GL_QUADS); // of the color cube

        // Front-face
        if (on) {
            gl.glColor3f(color[0], color[1], color[2]); // grey
        } else {
            gl.glColor3f(0.8f, 0.8f, 0.8f); // grey
        }
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);

        // Back-face
        //gl.glColor3f(1.0f, 0.0f, 1.0f); // purple
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glVertex3f(0.0f, 0.0f, 6.0f);

        // Left-face
        //gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glVertex3f(0.0f, 0.0f, 6.0f);

        // Right-face
        //gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);

        // Top-face
        //gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);

        // Bottom-face
        //gl.glColor3f(0.0f, 1.0f, 1.0f); // cyan        
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 6.0f);
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);

        gl.glEnd();
        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe   
    }
}
