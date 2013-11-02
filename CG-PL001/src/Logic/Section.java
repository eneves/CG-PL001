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
public class Section {
    private Car rightSide;
    private Car leftSide;
    private boolean isAuxiliar;
    
    public Section(boolean isAuxiliar){
        this.isAuxiliar = isAuxiliar;
    }    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (isAuxiliar){
            sb.append("| A : A |");
        }else{
            sb.append("| ").append(leftSide==null?" ":"*").append(" : ");
            
            sb.append(rightSide==null?" ":"*").append(" |");
        }
        return sb.toString();
    }
    
}
