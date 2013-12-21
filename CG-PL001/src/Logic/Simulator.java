/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import javax.media.opengl.awt.GLCanvas;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL2;

/**
 *
 * @author Emanuel
 */
public class Simulator {

    private TrafficManager trafficManager;
    private ArrayList<Section> road;
    private ArrayList<Source> sources;
    private boolean editorMode;
    private int currentInstant;
    private boolean animationRunning;
    private Section selectedSection;

    public Simulator(boolean isEditorMode) {
        this.editorMode = isEditorMode;
        trafficManager = new TrafficManager();
        animationRunning = false;
        currentInstant = 0;
    }

    public void setIsEditorMode(boolean editorMode) {
        this.editorMode = editorMode;
    }

    public ArrayList<Section> getRoad() {
        return road;
    }

    public void setRoad(ArrayList<Section> road) {
        this.road = road;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }

    public boolean isIsEditorMode() {
        return editorMode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Mode : ");
        sb.append(editorMode ? "Editor" : "Simulator").append("\n");
        sb.append("Sources:\n");
        for (Source s : sources) {
            sb.append(s.toString()).append("\n");
        }
        sb.append("Current road status (t=").append(currentInstant).append(") :\n");
        //int index = 0;
        for (int i = road.size() - 1; i >= 0; i--) {
            Section s = road.get(i);
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
            if (!section.isAuxiliar()) {
                section.render(gl);
                Source currSource = section.getSource();
                if (currSource != null) {
                    currSource.render(gl);
                }
            }
        }
    }

    public void toogleAnimation() {
        animationRunning = !animationRunning;
        if (animationRunning) {
            removeSelection();
        }
    }

    public boolean isAnimationRunning() {
        return animationRunning;
    }

    public int getCurrentInstant() {
        return currentInstant;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section selectedSection) {
        this.selectedSection = selectedSection;
    }

    public void selectLeft() {
        if (selectedSection == null) {
            selectedSection = road.get(1);
        } else {
            selectedSection.setSelected(false);
            int index = road.indexOf(selectedSection);
            if (index < road.size() - 2) {
                selectedSection = road.get(index + 1);
            } else {
                selectedSection = road.get(1);
            }
        }
        selectedSection.setSelected(true);
    }

    public void selectRight() {
        if (selectedSection == null) {
            selectedSection = road.get(road.size() - 2);
        } else {
            selectedSection.setSelected(false);
            int index = road.indexOf(selectedSection);
            if (index > 1) {
                selectedSection = road.get(index - 1);
            } else {
                selectedSection = road.get(road.size() - 2);
            }
        }
        selectedSection.setSelected(true);
    }

    public void removeSelection() {
        if (selectedSection != null) {
            selectedSection.setSelected(false);
            selectedSection = null;
        }
    }

    public boolean hadSelection() {
        return selectedSection != null;
    }
}
