/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Emanuel
 */
public class OBJLoader {

    public static Model loadModel(File f) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                String[] temp = line.split(" ");
                float x = Float.valueOf(temp[1]);
                float y = Float.valueOf(temp[2]);
                float z = Float.valueOf(temp[3]);
                m.vertices.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                String[] temp = line.split(" ");
                float x = Float.valueOf(temp[1]);
                float y = Float.valueOf(temp[2]);
                float z = Float.valueOf(temp[3]);
                m.normals.add(new Vector3f(x, y, z));
            } else if (line.startsWith("f ")) {
                String[] temp = line.split(" ");
                Vector3f vertexIndices = new Vector3f(Float.valueOf(temp[1].split("/")[0]),
                        Float.valueOf(temp[2].split("/")[0]),
                        Float.valueOf(temp[3].split("/")[0]));
                Vector3f normalIndices = new Vector3f(Float.valueOf(temp[1].split("/")[2]),
                        Float.valueOf(temp[2].split("/")[2]),
                        Float.valueOf(temp[3].split("/")[2]));
                m.faces.add(new Face(vertexIndices, normalIndices));
            }
        }
        reader.close();
        return m;
    }

}
