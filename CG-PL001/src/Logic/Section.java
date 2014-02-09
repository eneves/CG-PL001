/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import OpenGL.AppTexture;
import OpenGL.StaticListener;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

/**
 *
 * @author Emanuel
 */
public class Section {

    private Car rightSide;
    private Car leftSide;
    private boolean auxiliar;
    private float originX;
    private float originY;
    private float originZ;
    private float[] color;
    private float angle;
    private Source source;
    private boolean first;
    private boolean selected;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean hasSource() {
        return this.source != null;
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
        this.auxiliar = isAuxiliar;
        this.first = isFirst;
        color = new float[3];
        color[0] = 1.0f;
        color[1] = 1.0f;
        color[2] = 1.0f;
        this.selected = false;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isAuxiliar() {
        return auxiliar;
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
        if (auxiliar) {
            sb.append("| A : A |");
        } else {
            sb.append("| ").append(leftSide == null ? " " : "*").append(" : ");

            sb.append(rightSide == null ? " " : "*").append(" |");
        }
        return sb.toString();
    }

    public void repositionCars() {
        if (leftSide != null) {
            leftSide.setOriginX(originX + 1.75f);
            leftSide.setOriginY(0);
            leftSide.setOriginZ(originZ + 2.5f);
        }
        if (rightSide != null) {
            rightSide.setOriginX(originX - 1.75f);
            rightSide.setOriginY(0);
            rightSide.setOriginZ(originZ + 2.5f);
        }
    }

    public void render(GL2 gl) {
        gl.glTranslatef(originX, originY, originZ); // translate to relative axe
        gl.glRotatef(angle, 0, 1, 0);

        AppTexture roadTexture = StaticListener.textureDic.get("road");
        if (roadTexture != null && roadTexture.isSuccess()) {
            roadTexture.getTexture().enable(gl);
            roadTexture.getTexture().bind(gl);
        }

        gl.glBegin(GL_QUADS); // of the color cube

        // Front-face
        if (selected) {
            float[] rgba = {0.5f, 0.5f, 0.4f};
            gl.glMaterialfv(GL.GL_FRONT, GL_AMBIENT, rgba, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, rgba, 0);
            gl.glMaterialf(GL.GL_FRONT, GL_SHININESS, 0.5f);

        } else {
            gl.glMaterialfv(GL.GL_FRONT, GL_AMBIENT, color, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, color, 0);
            gl.glMaterialf(GL.GL_FRONT, GL_SHININESS, 0.5f);
        }

        gl.glVertex3f(-3.5f, 0.0f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, 0.0f, 0.0f);

        // Back-face
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, 0.0f, 10.0f);

        // Left-face
        gl.glVertex3f(3.5f, 0.0f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(3.5f, 0.0f, 10.0f);

        // Right-face
        gl.glVertex3f(-3.5f, 0.0f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);

        // Top-face
        gl.glTexCoord2f(roadTexture.getTextureLeft(), roadTexture.getTextureTop());
        gl.glVertex3f(3.5f, 0.0f, 0.0f);
        gl.glTexCoord2f(roadTexture.getTextureRight(), roadTexture.getTextureTop());
        gl.glVertex3f(3.5f, 0.0f, 10.0f);
        gl.glTexCoord2f(roadTexture.getTextureRight(), roadTexture.getTextureBottom());
        gl.glVertex3f(-3.5f, 0.0f, 10.0f);
        gl.glTexCoord2f(roadTexture.getTextureLeft(), roadTexture.getTextureBottom());
        gl.glVertex3f(-3.5f, 0.0f, 0.0f);

        // Bottom-face
        gl.glVertex3f(3.5f, -0.3f, 0.0f);
        gl.glVertex3f(3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 10.0f);
        gl.glVertex3f(-3.5f, -0.3f, 0.0f);
        float b;
        float beta;
        float deltaZ = 0;
        float deltaX = 0;
        if (!first && angle < 0) {
            b = (float) (7 * Math.sin((angle * Math.PI) / (2 * 180)));
            beta = (float) (((90 - ((180 - angle) / 2)) * Math.PI) / 180);
            deltaZ = (float) (b * Math.cos(beta));
            deltaX = (float) (b * Math.sin(beta));
            gl.glVertex3f(3.5f, 0.0f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, 0.0f, deltaZ);
            gl.glVertex3f(3.5f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, -0.3f, deltaZ);

        } else if (!first && angle > 0) {
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
        if (!first && angle < 0) {
            gl.glTexCoord2f(roadTexture.getTextureLeft(), roadTexture.getTextureBottom());
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glTexCoord2f(roadTexture.getTextureLeft(), roadTexture.getTextureTop());
            gl.glVertex3f(3.5f, 0.0f, 0.0f);
            gl.glTexCoord2f(roadTexture.getTextureRight(), roadTexture.getTextureTop());
            gl.glVertex3f(3.5f - deltaX, 0.0f, deltaZ);

            gl.glVertex3f(0.0f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f, -0.3f, 0.0f);
            gl.glVertex3f(3.5f - deltaX, -0.3f, deltaZ);
        } else if (!first && angle > 0) {
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(-3.5f, 0.0f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, 0.0f, -deltaZ);

            gl.glVertex3f(0.0f, -0.3f, 0.0f);
            gl.glVertex3f(-3.5f, -0.3f, 0.0f);
            gl.glVertex3f(-3.5f + deltaX, -0.3f, -deltaZ);
        }
        gl.glEnd();

        if (roadTexture != null && roadTexture.isSuccess()) {
            roadTexture.getTexture().disable(gl);
        }

        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe 
        StaticListener.resetMaterial(gl);
        
        if (rightSide != null) {
            rightSide.render(gl);
        }
        if (leftSide != null) {
            leftSide.render(gl);
        }
    }

}
