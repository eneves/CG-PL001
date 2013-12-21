/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.media.opengl.GL2;

/**
 *
 * @author Emanuel
 */
public class Simulator {

    private TrafficManager trafficManager;
    private Section[] road;
    private ArrayList<Source> sources;
    private boolean isEditorMode;
    private int currentInstant;

    public Simulator(boolean isEditorMode) {
        this.isEditorMode = isEditorMode;
        trafficManager = new TrafficManager();
        currentInstant = 0;
    }

    public void setIsEditorMode(boolean isEditorMode) {
        this.isEditorMode = isEditorMode;
    }

    public Section[] getRoad() {
        return road;
    }

    public void setRoad(Section[] road) {
        this.road = road;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }

    public boolean isIsEditorMode() {
        return isEditorMode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Mode : ");
        sb.append(isEditorMode ? "Editor" : "Simulator").append("\n");
        sb.append("Sources:\n");
        for (Source s : sources) {
            sb.append(s.toString()).append("\n");
        }
        sb.append("Current road status (t=").append(currentInstant).append(") :\n");
        //int index = 0;
        for (int i = road.length - 1; i >= 0; i--) {
            Section s = road[i];
            sb.append(s.toString()).append(" ").append(haveSource(i) ? "I" : "O");
            sb.append(" -> (").append(i).append(")\n");
        }
        return sb.toString();
    }

    private boolean haveSource(int index) {
        for (Source source : sources) {
            if (source.getPosition() == index) {
                return true;
            }
        }
        return false;
    }

    public void incrementInstant() {
        road = trafficManager.processTraffic(road);
        trafficManager.processSources(sources, road);
        currentInstant++;
    }

    public void render(GL2 gl) {
        for (Section section : road) {
            if (!section.isAuxiliar())
                section.render(gl);
        }
        for (Source source : sources) {
            source.render(gl);
        }
    }

}
