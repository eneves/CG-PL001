/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;

/**
 *
 * @author Emanuel
 */
public class Section {

    private Car rightSide;
    private Car leftSide;
    private boolean isAuxiliar;
    private float originX;
    private float originY;
    private float originZ;
    private float[] color;
    private float angle;
    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
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

    public Section(boolean isAuxiliar) {
        this.isAuxiliar = isAuxiliar;
        color = new float[3];
        color[0] = 1.0f;
        color[1] = 1.0f;
        color[2] = 1.0f;
    }

    public boolean isAuxiliar() {
        return isAuxiliar;
    }

    public Car getRightSide() {
        return rightSide;
    }

    public void setRightSide(Car rightSide) {
        this.rightSide = rightSide;
    }

    public Car getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Car leftSide) {
        this.leftSide = leftSide;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isAuxiliar) {
            sb.append("| A : A |");
        } else {
            sb.append("| ").append(leftSide == null ? " " : "*").append(" : ");

            sb.append(rightSide == null ? " " : "*").append(" |");
        }
        return sb.toString();
    }

    public void render(GL2 gl) {
        gl.glTranslatef(originX, originY, originZ); // translate to relative axe
        gl.glRotatef(angle, 0, 1, 0);
        gl.glBegin(GL_QUADS); // of the color cube

        // Front-face
        gl.glColor3f(color[0], color[1], color[2]); // grey
        gl.glVertex3f(-3.5f, 0.0f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, 0.0f, 0.0f);

        // Back-face
        //gl.glColor3f(1.0f, 0.0f, 1.0f); // purple
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, 0.0f, 10.0f);

        // Left-face
        //gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glVertex3f(3.5f, 0.0f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, 0.0f, 10.0f);

        // Right-face
        //gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        gl.glVertex3f(-3.5f, 0.0f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);

        // Top-face
        //gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(3.5f, 0.0f, 0.0f);
        gl.glVertex3f(3.5f, 0.0f, 10.0f);
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);
        gl.glVertex3f(-3.5f, 0.0f, 0.0f);

        // Bottom-face
        //gl.glColor3f(0.0f, 1.0f, 1.0f); // cyan
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);

        gl.glEnd();
        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe 

        if (rightSide != null) {
            rightSide.render(gl);
        }
        if (leftSide != null) {
            leftSide.render(gl);
        }
    }

}
