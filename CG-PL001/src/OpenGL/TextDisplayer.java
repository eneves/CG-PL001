/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpenGL;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 *
 * @author Emanuel
 */
public class TextDisplayer {

    private final int FONT_SIZE = 18;
    private TextRenderer upperLeft;
    private ArrayList<String> ulString;
    private TextRenderer upperRight;
    private ArrayList<String> urString;
    private TextRenderer DownerLeft;
    private ArrayList<String> dlString;
    private TextRenderer DownerRight;
    private ArrayList<String> drString;

    public TextDisplayer() {
        upperLeft = new TextRenderer(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        upperLeft.setColor(Color.yellow);
        ulString = new ArrayList<>();
        upperRight = new TextRenderer(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        upperRight.setColor(Color.red);
        urString = new ArrayList<>();
        DownerLeft = new TextRenderer(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        DownerLeft.setColor(Color.green);
        dlString = new ArrayList<>();
        dlString.add("DownerLeft");
        DownerRight = new TextRenderer(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        DownerRight.setColor(Color.orange);
        drString = new ArrayList<>();
        drString.add("DownerRight");
    }

    public void render(int width, int height) {
        upperLeft.beginRendering(width, height);
        // optionally set the color
        //upperLeft.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        for (int i = 0; i < ulString.size(); i++) {
            upperLeft.draw(ulString.get(i), 10, height - ((FONT_SIZE + 5) * (1 + i)));
        }
        // ... more draw commands, color changes, etc.
        upperLeft.endRendering();

        upperRight.beginRendering(width, height);
        // optionally set the color
        //upperLeft.setColor(1.0f, 0.2f, 0.2f, 0.8f);

        for (int i = 0; i < urString.size(); i++) {
            upperRight.draw(urString.get(i), width - 200, height - ((FONT_SIZE + 5) * (1 + i)));
        }
        // ... more draw commands, color changes, etc.
        upperRight.endRendering();
        DownerLeft.beginRendering(width, height);
        // optionally set the color
        //upperLeft.setColor(1.0f, 0.2f, 0.2f, 0.8f);

        for (int i = 0; i < dlString.size(); i++) {
            DownerLeft.draw(dlString.get(i), 10, (FONT_SIZE + 5) * (dlString.size() - i));
        }
        // ... more draw commands, color changes, etc.
        DownerLeft.endRendering();
        DownerRight.beginRendering(width, height);
        // optionally set the color
        //upperLeft.setColor(1.0f, 0.2f, 0.2f, 0.8f);

        for (int i = 0; i < drString.size(); i++) {
            DownerRight.draw(drString.get(i), width - 200,(FONT_SIZE + 5) * (drString.size() - i));
        }
        // ... more draw commands, color changes, etc.
        DownerRight.endRendering();
    }

    public void addLineUlString(String string) {
        ulString.add(string);
    }
    public void addLineUrString(String string) {
        urString.add(string);
    }
    public void addLineDlString(String string) {
        dlString.add(string);
    }
    public void addLineDrString(String string) {
        drString.add(string);
    }
    public void clearUlString(){
        ulString.clear();
    }
    public void clearUrString(){
        urString.clear();
    }
    public void clearDlString(){
        dlString.clear();
    }
    public void clearDrString(){
        drString.clear();
    }
}
