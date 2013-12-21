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
public class TrafficManager {

    private ruleActions[][] rigthSide;
    private ruleActions[][] leftSide;

    public TrafficManager() {
        createTables();
    }

    public ArrayList<Section> processTraffic(ArrayList<Section> inicialRoad) {
        ArrayList<Section> finalRoad = new ArrayList(inicialRoad.size());
        finalRoad.add(new Section(true, false));
        for (int i = 1; i < inicialRoad.size() - 1; i++) {
            int lineIndex = (inicialRoad.get(i - 1).getLeftSide() == null ? 0 : 1) * 4
                    + (inicialRoad.get(i).getLeftSide() == null ? 0 : 1) * 2
                    + (inicialRoad.get(i + 1).getLeftSide() == null ? 0 : 1) * 1;
            int columnIndex = (inicialRoad.get(i - 1).getRightSide() == null ? 0 : 1) * 4
                    + (inicialRoad.get(i).getRightSide() == null ? 0 : 1) * 2
                    + (inicialRoad.get(i + 1).getRightSide() == null ? 0 : 1) * 1;
            Section section = new Section(false, i == 1);
            section.setAngle(inicialRoad.get(i).getAngle());
            section.setOriginX(inicialRoad.get(i).getOriginX());
            section.setOriginY(inicialRoad.get(i).getOriginY());
            section.setOriginZ(inicialRoad.get(i).getOriginZ());
            section.setSource(inicialRoad.get(i).getSource());
            finalRoad.add(i, section);
            Car car;
            switch (rigthSide[lineIndex][columnIndex]) {
                case emptyZone:
                    break;
                case carNotMove:
                    section.setRightSide(inicialRoad.get(i).getRightSide());
                    break;
                case carFromBehind:
                    car = inicialRoad.get(i - 1).getRightSide();
                    car.setOriginZ(car.getOriginZ() + 10);
                    section.setRightSide(car);
                    break;
                case carFromSide:
                    car = inicialRoad.get(i).getLeftSide();
                    car.setOriginX(car.getOriginX() - 3.5f);
                    section.setRightSide(car);
                    break;
            }
            switch (leftSide[lineIndex][columnIndex]) {
                case emptyZone:
                    break;
                case carNotMove:
                    section.setLeftSide(inicialRoad.get(i).getLeftSide());
                    break;
                case carFromBehind:
                    car = inicialRoad.get(i - 1).getLeftSide();
                    car.setOriginZ(car.getOriginZ() + 10);
                    section.setLeftSide(car);
                    break;
                case carFromSide:
                    car = inicialRoad.get(i).getRightSide();
                    car.setOriginX(car.getOriginX() + 3.5f);
                    section.setLeftSide(car);
                    break;
            }
        }
        finalRoad.add(new Section(true, false));
        return finalRoad;
    }

    public void processSources(ArrayList<Source> sources, ArrayList<Section> road) {
        for (Section section : road) {
            Source currSource = section.getSource();
            if (currSource != null && currSource.isOn()) {
                currSource.incrementTick();
                if (currSource.putCar()) {
                    if (section.getRightSide() == null) {
                        Car car = new Car();
                        car.setOriginX(section.getOriginX() - 1.75f);
                        car.setOriginY(0);
                        car.setOriginZ(section.getOriginZ() + 2.5f);
                        section.setRightSide(car);
                        car.setColor(currSource.getColor());
                    }
                    currSource.resetTick();
                }
            }
        }
    }

    private void createTables() {
        rigthSide = new ruleActions[8][8];
        leftSide = new ruleActions[8][8];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                leftSide[i][j] = ruleActions.emptyZone;
                rigthSide[j][i] = ruleActions.emptyZone;
            }
        }
        for (int j = 0; j < 8; j++) {
            leftSide[6][j] = ruleActions.emptyZone;
            rigthSide[j][6] = ruleActions.emptyZone;
        }
        //car dont move
        for (int i = 3; i < 8; i += 4) {
            for (int j = 0; j < 8; j++) {
                leftSide[i][j] = ruleActions.carNotMove;
                rigthSide[j][i] = ruleActions.carNotMove;
            }
        }
        //car move from behind
        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                leftSide[i][j] = ruleActions.carFromBehind;
                rigthSide[j][i] = ruleActions.carFromBehind;
            }
        }
        // move side
        leftSide[3][0] = ruleActions.emptyZone;
        rigthSide[0][3] = ruleActions.emptyZone;
        leftSide[0][3] = ruleActions.carFromSide;
        rigthSide[3][0] = ruleActions.carFromSide;
    }

    public enum ruleActions {

        emptyZone,
        carNotMove,
        carFromSide,
        carFromBehind
    }
}
