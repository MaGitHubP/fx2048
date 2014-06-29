package giocatoreAutomatico;
import game2048.Location;
import game2048.Direction;
import java.util.HashMap;


public class GrigliaGame extends HashMap<Location, Integer> implements Griglia{
    private final int xPositions=4;
    private final int yPositions=4;
    
    /**
     * Questo metodo viene usato per modificare le caselle, in particolare
     * durante gli spostamenti delle piastrelle quando viene eseguito il
     * metodo move.
     * @param loc La locazione della griglia da modificare.
     * @param i Il valore della griglia da modificare.
     */
    public void modifyTile(Location loc, Integer i){
        this.put(loc, i);
    }
    
    /**
     * Questo metodo viene usato per mettere le caselle vuote(quindi 
     * con valore -1) nella griglia durante le modifiche di spostamento.
     * @param loc La locazione della griglia.
     */
    public void removeTile(Location loc){
        this.put(loc, -1);
    }
    
    /**
     * Questo metodo rappresenta mossa del giocatore(automatico), 
     * aggiornando, di conseguenza, la griglia.
     * @param d La direzione messa in input.
     * @return Il numero di valori uniti durante il movimento.
     */
    public int move(Direction d){
        int x, y, merged=0;
        Integer val;
        Location temp, farthest, startingPosition;
        
        if(d==Direction.UP){
            for(y=1;y<yPositions;y++){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=this.get(startingPosition);
                    
                    if(val!=-1){
                        do {
                            farthest = temp;
                            temp = farthest.offset(d);
                        }while (temp.isValidFor(4) && this.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(this.get(temp))){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                merged++;
                            }else{
                                this.modifyTile(farthest, val);
                                this.removeTile(startingPosition);
                            }
                        }else{
                            this.modifyTile(farthest, val);
                            this.removeTile(startingPosition);
                        }
                    }
                 
                }
            }
        }else if(d==Direction.DOWN){
            for(y=2;y>=0;y--){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=this.get(startingPosition);
                    
                    if(val!=-1){
                        do {
                            farthest = temp;
                            temp = farthest.offset(d);
                        }while (temp.isValidFor(4) && this.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(this.get(temp))){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                merged++;
                            }else{
                                this.modifyTile(farthest, val);
                                this.removeTile(startingPosition);
                            }
                        }else{
                            this.modifyTile(farthest, val);
                            this.removeTile(startingPosition);
                        }
                    }
                 
                }
            }
        }else if(d==Direction.RIGHT){
            for(x=2;x>=0;x--){
                for(y=0;y<yPositions;y++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=this.get(startingPosition);
                    
                    if(val!=-1){
                        do {
                            farthest = temp;
                            temp = farthest.offset(d);
                        }while (temp.isValidFor(4) && this.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(this.get(temp))){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                merged++;
                            }else{
                                this.modifyTile(farthest, val);
                                this.removeTile(startingPosition);
                            }
                        }else{
                            this.modifyTile(farthest, val);
                            this.removeTile(startingPosition);
                        }
                    }
                 
                }
            }
        }else{
            for(x=1;x<xPositions;x++){
                for(y=0;y<yPositions;y++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=this.get(startingPosition);
                    
                    if(val!=-1){
                        do {
                            farthest = temp;
                            temp = farthest.offset(d);
                        }while (temp.isValidFor(4) && this.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(this.get(temp))){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                merged++;
                            }else{
                                this.modifyTile(farthest, val);
                                this.removeTile(startingPosition);
                            }
                        }else{
                            this.modifyTile(farthest, val);
                            this.removeTile(startingPosition);
                        }
                    }
                 
                }
            }
        }
        return merged;
    }
    
}
