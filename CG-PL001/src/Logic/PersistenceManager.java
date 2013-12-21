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
            Section[] road = createRoad(Integer.parseInt(in.readLine()));
            String str;
            int sourcePos = 1;
            String[] vecStr;
            while ((str = in.readLine()) != null) {
                vecStr = str.split(" ");
                int isSource = Integer.parseInt(vecStr[1]);
                if (isSource != -1) {

                }

            }

        } catch (Exception e) {
            return loadDefaults(isEditorMode);
        }
        return loadDefaults(isEditorMode);
    }

    private static Section[] createRoad(int segmentsNumber) {
        Section[] road = new Section[segmentsNumber + 2];
        float x = 0;
        float y = 0;
        float z = 0;
        for (int i = 0; i < road.length; i++) {
            Section section = new Section(i == 0 || i == road.length - 1);
            if (!section.isAuxiliar()) {
                section.setOriginX(x);
                section.setOriginY(y);
                section.setOriginZ(z);
                z += 10;
            }
            road[i] = section;
        }
        return road;
    }

    private static Source createSource(int position, int period, Section section) {
        Source source = new Source(position, period, section);
        source.setOriginX(section.getOriginX() - 3.5f);
        source.setOriginY(section.getOriginY());
        source.setOriginZ(section.getOriginZ());
        return source;
    }

    //Caso o ficheiro não exista, deve ser criada uma rede de estradas com as características apresentadas na tabela 1.
    //    comprimento da estrada 10
    //    posições das fontes 1; 4; 5
    //    período de emissão de automóveis nas fontes 2; 4; 5
    //    estrada direita
    //Tabela 1: Características da rede de estradas quando é omitido o nome do ficheiro.
    private static Simulator loadDefaults(boolean isEditorMode) {
        Section[] road = new Section[12];
        float x = 0;
        float y = 0;
        float z = 0;
        for (int i = 0; i < 12; i++) {
            Section section = new Section(i == 0 || i == 11);
            if (i != 0 && i != 11) {
                section.setOriginX(x);
                section.setOriginY(y);
                section.setOriginZ(z);
                z += 10;
            }
            road[i] = section;
        }
        ArrayList<Source> sources = new ArrayList();
        Source source = new Source(1, 2, road[1]);
        source.setOriginX(road[1].getOriginX() - 3.5f);
        source.setOriginY(road[1].getOriginY());
        source.setOriginZ(road[1].getOriginZ());
        sources.add(source);
        source = new Source(4, 4, road[4]);
        source.setOriginX(road[4].getOriginX() - 3.5f);
        source.setOriginY(road[4].getOriginY());
        source.setOriginZ(road[4].getOriginZ());
        sources.add(source);
        source = new Source(5, 5, road[5]);
        source.setOriginX(road[5].getOriginX() - 3.5f);
        source.setOriginY(road[5].getOriginY());
        source.setOriginZ(road[5].getOriginZ());
        sources.add(source);

        Simulator simulator = new Simulator(isEditorMode);
        simulator.setRoad(road);
        simulator.setSources(sources);

        return simulator;
    }

    public static void saveSimulator() {
    }
}
