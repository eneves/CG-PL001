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

    public Section[] processTraffic(Section[] inicialRoad) {
        Section[] finalRoad = new Section[inicialRoad.length];
        finalRoad[0] = new Section(true);
        finalRoad[inicialRoad.length - 1] = new Section(true);
        for (int i = 1; i < inicialRoad.length - 1; i++) {
            int lineIndex = (inicialRoad[i - 1].getLeftSide() == null ? 0 : 1) * 4
                    + (inicialRoad[i].getLeftSide() == null ? 0 : 1) * 2
                    + (inicialRoad[i + 1].getLeftSide() == null ? 0 : 1) * 1;
            int columnIndex = (inicialRoad[i - 1].getRightSide() == null ? 0 : 1) * 4
                    + (inicialRoad[i].getRightSide() == null ? 0 : 1) * 2
                    + (inicialRoad[i + 1].getRightSide() == null ? 0 : 1) * 1;
            Section section = new Section(false);
            section.setOriginX(inicialRoad[i].getOriginX());
            section.setOriginY(inicialRoad[i].getOriginY());
            section.setOriginZ(inicialRoad[i].getOriginZ());
            finalRoad[i] = section;
            Car car;
            switch (rigthSide[lineIndex][columnIndex]) {
                case emptyZone:
                    break;
                case carNotMove:
                    section.setRightSide(inicialRoad[i].getRightSide());
                    break;
                case carFromBehind:
                    car = inicialRoad[i - 1].getRightSide();
                    car.setOriginZ(car.getOriginZ()+10);
                    section.setRightSide(car);                    
                    break;
                case carFromSide:
                    car = inicialRoad[i].getLeftSide();
                    car.setOriginX(car.getOriginX()-3.5f);
                    section.setRightSide(car);
                    break;
            }
            switch (leftSide[lineIndex][columnIndex]) {
                case emptyZone:
                    break;
                case carNotMove:
                    section.setLeftSide(inicialRoad[i].getLeftSide());
                    break;
                case carFromBehind:
                    car = inicialRoad[i - 1].getLeftSide();
                    car.setOriginZ(car.getOriginZ()+10);
                    section.setLeftSide(car);
                    break;
                case carFromSide:
                    car = inicialRoad[i].getRightSide();
                    car.setOriginX(car.getOriginX()+3.5f);
                    section.setLeftSide(car);
                    break;
            }
        }
        return finalRoad;
    }

    public void processSources(ArrayList<Source> sources,Section[] road) {
        for (Source source : sources) {
            source.incrementTick();
            if (source.putCar()) {
                Section section = road[source.getPosition()];
                if (section.getRightSide() == null) {
                    Car car = new Car();
                    car.setOriginX(section.getOriginX()-1.75f);
                    car.setOriginY(0);
                    car.setOriginZ(section.getOriginZ()+2.5f);                                                          
                    section.setRightSide(car);
                    
                }
                source.resetTick();
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
