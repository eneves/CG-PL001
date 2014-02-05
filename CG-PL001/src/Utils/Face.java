/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

/**
 *
 * @author Emanuel
 */
public class Face {
    public Vector3f vertex = new Vector3f();  // Three indices, not vertices or normals
    public Vector3f normal = new Vector3f();
    
    public Face (Vector3f vertex, Vector3f normal){
        this.normal = normal;
        this.vertex = vertex;
    }
}
