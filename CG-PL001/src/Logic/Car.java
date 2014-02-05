/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import OpenGL.AppTexture;
import OpenGL.StaticListener;
import Utils.Face;
import Utils.Model;
import Utils.OBJLoader;
import Utils.Vector3f;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;

/**
 *
 * @author Emanuel
 */
public class Car {

    private float originX;
    private float originY;
    private float originZ;
    private float[] color;

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

    public void setColor(float[] color) {
        this.color = color;
    }

    private Model model;
    public void render(GL2 gl) {

        /*AppTexture textTop = StaticListener.textureDic.get("car_top");
        if (textTop != null && textTop.isSuccess()) {
            // Enables this texture's target in the current GL context's state.
            textTop.getTexture().enable(gl);  // same as gl.glEnable(texture.getTarget());
            // gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
            // Binds this texture to the current GL context.
            textTop.getTexture().bind(gl);  // same as gl.glBindTexture(texture.getTarget(), texture.getTextureObject());
        }
*/
        gl.glTranslatef(originX, originY, originZ); // translate to relative axe
        
        
        
        
        
        //Temp
        
        if (model ==null){
            try{
                model = OBJLoader.loadModel(new File("resources/test.obj"));
            }catch(FileNotFoundException e){
                e.printStackTrace();
                System.exit(1);
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(color[0], color[1], color[2]); // green
        for(Face face : model.faces){
            Vector3f n1 = model.normals.get((int)face.normal.getX() - 1);
            gl.glNormal3f(n1.getX(), n1.getY(), n1.getZ());
            Vector3f v1 = model.vertices.get((int)face.vertex.getX() - 1);
            gl.glVertex3f(v1.getX(), v1.getY(), v1.getZ());
            
            Vector3f n2 = model.normals.get((int)face.normal.getY() - 1);
            gl.glNormal3f(n2.getX(), n2.getY(), n2.getZ());
            Vector3f v2 = model.vertices.get((int)face.vertex.getY() - 1);
            gl.glVertex3f(v2.getX(), v2.getY(), v2.getZ());
            
            Vector3f n3 = model.normals.get((int)face.normal.getZ() - 1);
            gl.glNormal3f(n3.getX(), n3.getY(), n3.getZ());
            Vector3f v3 = model.vertices.get((int)face.vertex.getZ() - 1);
            gl.glVertex3f(v3.getX(), v3.getY(), v3.getZ());
        }
        gl.glEnd();
        
        
        
        
        
        
        
        /*gl.glBegin(GL_QUADS); // of the color cube

        // Front-face
        gl.glColor3f(color[0], color[1], color[2]); // green
        
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.5f, 0.0f);
        gl.glVertex3f(-1.0f, 1.5f, 0.0f);
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);

        // Back-face
        //gl.glColor3f(1.0f, 0.0f, 1.0f); // purple
        gl.glVertex3f(1.0f, 0.0f, 5.0f);
        gl.glVertex3f(1.0f, 1.5f, 5.0f);
        gl.glVertex3f(-1.0f, 1.5f, 5.0f);
        gl.glVertex3f(-1.0f, 0.0f, 5.0f);

        // Left-face
        //gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.5f, 0.0f);
        gl.glVertex3f(1.0f, 1.5f, 5.0f);
        gl.glVertex3f(1.0f, 0.0f, 5.0f);

        // Right-face
        //gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.5f, 0.0f);
        gl.glVertex3f(-1.0f, 1.5f, 5.0f);
        gl.glVertex3f(-1.0f, 0.0f, 5.0f);

        // Top-face
        //gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow        
        gl.glTexCoord2f(textTop.getTextureRight(), textTop.getTextureTop());
        gl.glVertex3f(-1.0f, 1.5f, 0.0f);
        gl.glTexCoord2f(textTop.getTextureLeft(), textTop.getTextureTop());
        gl.glVertex3f(-1.0f, 1.5f, 5.0f);
        gl.glTexCoord2f(textTop.getTextureLeft(), textTop.getTextureBottom());
        gl.glVertex3f(1.0f, 1.5f, 5.0f);
        gl.glTexCoord2f(textTop.getTextureRight(), textTop.getTextureBottom());
        gl.glVertex3f(1.0f, 1.5f, 0.0f);

        // Bottom-face
        //gl.glColor3f(0.0f, 1.0f, 1.0f); // cyan
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 0.0f, 5.0f);
        gl.glVertex3f(1.0f, 0.0f, 5.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);

        gl.glEnd();
                */
        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe 

        /*if (textTop != null && textTop.isSuccess()) {
            textTop.getTexture().disable(gl);
        }*/

    }

}
