/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;

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
        int index = 0;
        for (Section s : road) {
            sb.append(s.toString()).append(" ").append(haveSource(s)?"I":"O");
            sb.append(" -> (").append(index++).append(")\n");
        }
        return sb.toString();
    }

    private boolean haveSource(Section s) {
        for (Source source : sources) {
            if (s.equals(source.getOrigin())) {
                return true;
            }
        }
        return false;
    }

    public void incrementInstant() {
        road = trafficManager.processTraffic(road);
        trafficManager.processSources(sources);
        currentInstant++;
    }

}
