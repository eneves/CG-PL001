/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author Emanuel
 */
public class Source {

    private int period;
    private int position;
    private int currentTick;
    //private Section destination;
    //private Section origin;

    public Source(int position,int period, Section origin) {
        this.period = period;
        //this.origin = origin;
        this.position = position;
        currentTick = 0;
    }

    public int getPeriod() {
        return period;
    }

    public int getPosition() {
        return position;
    }

    /*public Section getOrigin() {
        return origin;
    }
    */
    public void incrementTick(){
        currentTick++;
    }
    public void resetTick(){
        currentTick = 0;
    }
    
    public boolean putCar(){
        return currentTick==period;
    }
        
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(" -> source at ");
        sb.append(position).append(" and period of ").append(period).append("\n");
        sb.append("   next car drop in ").append(period-currentTick);
        return sb.toString();        
    }
}
