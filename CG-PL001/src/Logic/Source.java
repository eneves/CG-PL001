/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import OpenGL.AppTexture;
import OpenGL.StaticListener;
import java.util.Random;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_TRIANGLES;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

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

        AppTexture sideWall = StaticListener.textureDic.get("house_side");
        if (sideWall != null && sideWall.isSuccess()) {
            sideWall.getTexture().enable(gl);
            sideWall.getTexture().bind(gl);
        }

        gl.glBegin(GL_QUADS); // of the color cube

        // Front-face
        if (on) {         
            gl.glMaterialfv(GL.GL_FRONT, GL_AMBIENT, color, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, color, 0);
            gl.glMaterialf(GL.GL_FRONT, GL_SHININESS, 0.5f);
        } else {
            float[] rgba = {0.8f, 0.8f, 0.8f};
            gl.glMaterialfv(GL.GL_FRONT, GL_AMBIENT, rgba, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, rgba, 0);
            gl.glMaterialf(GL.GL_FRONT, GL_SHININESS, 0.5f);
        }

        gl.glTexCoord2f(sideWall.getTextureRight(), sideWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(sideWall.getTextureRight(), sideWall.getTextureTop());
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(sideWall.getTextureLeft(), sideWall.getTextureTop());
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(sideWall.getTextureLeft(), sideWall.getTextureBottom());
        gl.glVertex3f(0.0f, 0.0f, 0.0f);


        // Back-face
        //gl.glColor3f(1.0f, 0.0f, 1.0f); // purple
        gl.glTexCoord2f(sideWall.getTextureRight(), sideWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);
        gl.glTexCoord2f(sideWall.getTextureRight(), sideWall.getTextureTop());
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(sideWall.getTextureLeft(), sideWall.getTextureTop());
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(sideWall.getTextureLeft(), sideWall.getTextureBottom());
        gl.glVertex3f(0.0f, 0.0f, 6.0f);

        gl.glEnd();
        if (sideWall != null && sideWall.isSuccess()) {
            sideWall.getTexture().disable(gl);
        }
        AppTexture frontWall = StaticListener.textureDic.get("house_front");
        if (frontWall != null && frontWall.isSuccess()) {
            frontWall.getTexture().enable(gl);
            frontWall.getTexture().bind(gl);
        }
        gl.glBegin(GL_QUADS); // of the color cube
        // Left-face
        //gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glTexCoord2f(frontWall.getTextureRight(), frontWall.getTextureBottom());
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(frontWall.getTextureRight(), frontWall.getTextureTop());
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(frontWall.getTextureLeft(), frontWall.getTextureTop());
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(frontWall.getTextureLeft(), frontWall.getTextureBottom());
        gl.glVertex3f(0.0f, 0.0f, 6.0f);
        gl.glEnd();
        if (frontWall != null && frontWall.isSuccess()) {
            frontWall.getTexture().disable(gl);
        }
        AppTexture backWall = StaticListener.textureDic.get("house_back");
        if (backWall != null && backWall.isSuccess()) {
            backWall.getTexture().enable(gl);
            backWall.getTexture().bind(gl);
        }
        gl.glBegin(GL_QUADS); // of the color cube

        // Right-face
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureTop());
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureTop());
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);
        gl.glEnd();
        if (backWall != null && backWall.isSuccess()) {
            backWall.getTexture().disable(gl);
        }
        AppTexture roof = StaticListener.textureDic.get("house_roof");
        if (roof != null && roof.isSuccess()) {
            roof.getTexture().enable(gl);
            roof.getTexture().bind(gl);
        }
        
        
        
        gl.glBegin(GL_QUADS); // of the color cube
        // Top-face
        //front-roof
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureBottom());
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureBottom());
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 5.4f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 0.6f);
        //back-roof
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 5.4f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 0.6f);        
        gl.glEnd();
        
        
        gl.glBegin(GL_TRIANGLES); // of the color cube
        // right side roof
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureBottom());
        gl.glVertex3f(0.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 6.0f, 0.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 0.6f);
        // left side roof
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureBottom());
        gl.glVertex3f(0.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(backWall.getTextureRight(), backWall.getTextureBottom());
        gl.glVertex3f(-3.0f, 6.0f, 6.0f);
        gl.glTexCoord2f(backWall.getTextureLeft(), backWall.getTextureTop());
        gl.glVertex3f(-1.5f, 7.0f, 5.4f);
        gl.glEnd();
        
        if (roof != null && roof.isSuccess()) {
            roof.getTexture().disable(gl);
        }
        gl.glBegin(GL_QUADS); // of the color cube
        // Bottom-face     
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 6.0f);
        gl.glVertex3f(-3.0f, 0.0f, 6.0f);
        gl.glVertex3f(-3.0f, 0.0f, 0.0f);

        gl.glEnd();

        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe          
        StaticListener.resetMaterial(gl);

    }
}
