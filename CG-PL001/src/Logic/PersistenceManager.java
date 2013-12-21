/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Emanuel
 */
public class PersistenceManager {

    private static String filename = "highway_data";

    public static Simulator loadSimulator(String filename, boolean isEditorMode) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            ArrayList<Section> road = new ArrayList(Integer.parseInt(in.readLine()) + 2);
            float x = 0;
            float y = 0;
            float z = 0;
            String str;
            String[] vecStr;
            int index = 0;
            while ((str = in.readLine()) != null) {
                vecStr = str.split(" ");
                Section section = new Section(index == 0 || index == road.size() - 1, index == 0);
                if (!section.isAuxiliar()) {
                    section.setOriginX(x);
                    section.setOriginY(y);
                    section.setOriginZ(z);
                    z += 10;
                    section.setAngle(Float.parseFloat(vecStr[0]));
                    if (Integer.parseInt(vecStr[1]) != -1) {
                        section.setSource(createSource(index, Integer.parseInt(vecStr[1]), section));
                    }
                }
                road.add(index, section);
                index ++;
            }
            Simulator simulator = new Simulator(isEditorMode);
            simulator.setRoad(road);
            return simulator;

        } catch (Exception e) {
            System.out.println("Foi lido o ficheiro de default!!");
            return loadDefaults(isEditorMode);
        }
    }

    private static ArrayList<Section> createRoad(int segmentsNumber) {
        ArrayList<Section> road = new ArrayList();
        float x = 0;
        float y = 0;
        float z = 0;
        float angle = 0;
        for (int i = 0; i < segmentsNumber + 2; i++) {
            Section section = new Section(i == 0 || i == segmentsNumber + 1, i == 1);
            if (!section.isAuxiliar()) {
                if (angle < 90) {
                    angle += 20;
                    section.setAngle(20);
                } else {
                    angle -= 20;
                    section.setAngle(-20);
                }
                section.setOriginX(x);
                section.setOriginY(y);
                section.setOriginZ(z);
                z += 10;
            }
            road.add(i, section);
        }
        return road;
    }

    private static Source createSource(int position, int period, Section section) {
        Source source = new Source(position, period);
        source.setOriginX(section.getOriginX() - 3.5f);
        source.setOriginY(section.getOriginY());
        source.setOriginZ(section.getOriginZ());
        source.setOn(true);
        return source;
    }

    //Caso o ficheiro não exista, deve ser criada uma rede de estradas com as características apresentadas na tabela 1.
    //    comprimento da estrada 10
    //    posições das fontes 1; 4; 5
    //    período de emissão de automóveis nas fontes 2; 4; 5
    //    estrada direita
    //Tabela 1: Características da rede de estradas quando é omitido o nome do ficheiro.
    private static Simulator loadDefaults(boolean isEditorMode) {
        ArrayList<Section> road = createRoad(10);
        System.out.println(road.size());
        Source s1 = createSource(1, 2, road.get(1));
        road.get(1).setSource(s1);
        Source s2 = createSource(4, 4, road.get(4));
        road.get(4).setSource(s2);
        Source s3 = createSource(5, 5, road.get(5));
        road.get(5).setSource(s3);
        Simulator simulator = new Simulator(isEditorMode);
        simulator.setRoad(road);
        return simulator;
    }

    public static void saveSimulator() {
    }
}
