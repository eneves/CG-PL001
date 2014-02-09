/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

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
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

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

        gl.glTranslatef(originX, originY, originZ); // translate to relative axe

        if (model == null) {
            try {
                model = OBJLoader.loadModel(new File("resources/car.obj"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        gl.glBegin(GL.GL_TRIANGLES);
        // Set material properties.
        gl.glMaterialfv(GL.GL_FRONT, GL_AMBIENT, color, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, color, 0);
        gl.glMaterialf(GL.GL_FRONT, GL_SHININESS, 0.5f);

        for (Face face : model.faces) {
            Vector3f n1 = model.normals.get((int) face.normal.getX() - 1);
            gl.glNormal3f(n1.getX(), n1.getY(), n1.getZ());
            Vector3f v1 = model.vertices.get((int) face.vertex.getX() - 1);
            gl.glVertex3f(v1.getX(), v1.getY(), v1.getZ());

            Vector3f n2 = model.normals.get((int) face.normal.getY() - 1);
            gl.glNormal3f(n2.getX(), n2.getY(), n2.getZ());
            Vector3f v2 = model.vertices.get((int) face.vertex.getY() - 1);
            gl.glVertex3f(v2.getX(), v2.getY(), v2.getZ());

            Vector3f n3 = model.normals.get((int) face.normal.getZ() - 1);
            gl.glNormal3f(n3.getX(), n3.getY(), n3.getZ());
            Vector3f v3 = model.vertices.get((int) face.vertex.getZ() - 1);
            gl.glVertex3f(v3.getX(), v3.getY(), v3.getZ());
        }
        gl.glEnd();

        gl.glTranslatef(-originX, -originY, -originZ); // translate back to absolute axe 

        StaticListener.resetMaterial(gl);
    }

}
