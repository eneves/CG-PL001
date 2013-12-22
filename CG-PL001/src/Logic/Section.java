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
    private boolean isFirst;
    private boolean selected;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
    
    public boolean hasSource(){
        return this.source!= null;
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

    public Section(boolean isAuxiliar, boolean isFirst) {
        this.isAuxiliar = isAuxiliar;
        this.isFirst = isFirst;
        color = new float[3];
        color[0] = 1.0f;
        color[1] = 1.0f;
        color[2] = 1.0f;
        this.selected = false;
    }
    
    public void setIsFirst(boolean isFirst){
        this.isFirst = isFirst;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
        if (selected) {
            gl.glColor3f(0.5f, 0.5f, 0.4f); // grey
        } else {
            gl.glColor3f(color[0], color[1], color[2]); // grey
        }

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
        float b;
        float beta;
        float deltaZ = 0;
        float deltaX = 0;
        if (!isFirst && angle < 0) {
            b = (float) (7 * Math.sin((angle * Math.PI) / (2 * 180)));
            beta = (float) (((90 - ((180 - angle) / 2)) * Math.PI) / 180);
            deltaZ = (float) (b * Math.cos(beta));
            deltaX = (float) (b * Math.sin(beta));
            gl.glVertex3f(3.5f, 0.0f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, 0.0f, deltaZ);
            gl.glVertex3f(3.5f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, -0.3f, deltaZ);

        } else if (!isFirst && angle > 0) {
            b = (float) (7 * Math.sin((angle * Math.PI) / (2 * 180)));
            beta = (float) (((90 - ((180 - angle) / 2)) * Math.PI) / 180);
            deltaZ = (float) (b * Math.cos(beta));
            deltaX = (float) (b * Math.sin(beta));
            gl.glVertex3f(-3.5f, 0.0f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, 0.0f, -deltaZ);
            gl.glVertex3f(-3.5f, -0.3f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, -0.3f, -deltaZ);

        }

        gl.glEnd();

        gl.glBegin(GL.GL_TRIANGLES); // of the color cube
        if (!isFirst && angle < 0) {
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(3.5f, 0.0f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, 0.0f, deltaZ);

            gl.glVertex3f(0.0f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, -0.3f, deltaZ);
        } else if (!isFirst && angle > 0) {
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(-3.5f, 0.0f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, 0.0f, -deltaZ);

            gl.glVertex3f(0.0f, -0.3f, 0.0f);
            gl.glVertex3f(-3.5f, -0.3f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, -0.3f, -deltaZ);
        }
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
