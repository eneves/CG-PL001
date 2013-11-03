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
        for(Section section : inicialRoad){
            
        }
        return finalRoad;
    }

    public void processSources(ArrayList<Source> sources) {
        for (Source source : sources) {
            source.incrementTick();
            if (source.putCar()) {
                Section section = source.getOrigin();
                if (section.getRightSide() == null) {
                    section.setRightSide(new Car());
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
        for (int i = 4; i < 6; i ++) {
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
