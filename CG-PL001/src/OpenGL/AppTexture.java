/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpenGL;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

/**
 *
 * @author Emanuel
 */
public class AppTexture {

    private Texture texture;
    private String textureFileName;
    private String textureFileType;
    private float textureTop;
    private float textureBottom;
    private float textureLeft;
    private float textureRight;
    private boolean success;

    public AppTexture(String textureFileName, GL2 gl) {

        // Load texture from image
        try {
            this.textureFileName = textureFileName;
            int index = textureFileName.lastIndexOf('.');
            this.textureFileType = textureFileName.substring(index);
            File file = new File(textureFileName);
            FileInputStream fis = new FileInputStream(file);

            InputStream stream = getClass().getResourceAsStream(textureFileName);
            TextureData data = TextureIO.newTextureData(gl.getGLProfile(), fis, false, this.textureFileType);
            texture = TextureIO.newTexture(data);

            // Use linear filter for texture if image is larger than the original texture
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            // Use linear filter for texture if image is smaller than the original texture
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            // Texture image flips vertically. Shall use TextureCoords class to retrieve
            // the top, bottom, left and right coordinates, instead of using 0.0f and 1.0f.
            TextureCoords textureCoords = texture.getImageTexCoords();
            textureTop = textureCoords.top();
            textureBottom = textureCoords.bottom();
            textureLeft = textureCoords.left();
            textureRight = textureCoords.right();
            success = true;
        } catch (GLException | IOException e) {
            e.printStackTrace();
            success = false;
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public float getTextureTop() {
        return textureTop;
    }

    public float getTextureBottom() {
        return textureBottom;
    }

    public float getTextureLeft() {
        return textureLeft;
    }

    public float getTextureRight() {
        return textureRight;
    }

    public boolean isSuccess() {
        return success;
    }
}
