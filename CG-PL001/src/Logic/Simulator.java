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

    public boolean isIsEditorMode() {
        return editorMode;
    }

    public void incrementInstant() {
        road = trafficManager.processTraffic(road);
        trafficManager.processSources(road);
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

    public ViewportSize getViewport() {
        ViewportSize result = new ViewportSize();
        result.xMax += 10 * road.size() - 2;
        result.xMin -= 10 * road.size() - 2;
        result.zMax += 10 * road.size() - 2;
        result.zMin -= 10 * road.size() - 2;
        return result;
    }

    public class ViewportSize {

        private float xMax;
        private float xMin;
        private float zMax;
        private float zMin;

        public ViewportSize() {
            xMax = 0;
            xMin = 0;
            zMax = 0;
            zMin = 0;
        }

        public float getxMax() {
            return xMax;
        }

        public float getxMin() {
            return xMin;
        }

        public float getzMax() {
            return zMax;
        }

        public float getzMin() {
            return zMin;
        }
    }
}
