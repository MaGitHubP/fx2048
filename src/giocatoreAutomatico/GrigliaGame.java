package giocatoreAutomatico;
import game2048.Location;
import game2048.Direction;
import java.util.HashMap;
import java.util.Random;


public class GrigliaGame implements Griglia{

    private final HashMap<Location, Integer> map;
    private final int xPositions=4;
    private final int yPositions=4;
    
    public GrigliaGame(){
        this.map=new HashMap();
        
        for(int j=0;j<this.yPositions;j++){
            for(int i=0;i<this.xPositions;i++){
                this.map.put(new Location(i, j), -1);
            }
        }
        this.newRandomTile();
    }

    /**
     * Questo metodo aggiunge nella griglia una nuova casella, all'
     * inizio del gioco e dopo ogni mossa.
     */
    public void newRandomTile(){
        int x, y, val, newValue;
        Random r=new Random();
        boolean found=false;
        Location loc;
        
        do{
            x=r.nextInt(4);
            y=r.nextInt(4);
            loc=new Location(x, y);
            val=this.map.get(loc);
            
            if(val==-1){
                newValue = new Random().nextDouble() < 0.9 ? 2 : 4;
                this.modifyTile(loc, newValue);
                found=true;
            }
        }while(!found);
    }
    
    /**
     * Questo metodo viene usato per modificare le caselle, e viene
     * usato sia durante l'aggiunta casuale di una casella, sia durante
     * gli spostamenti.
     * @param loc
     * @param i 
     */
    public void modifyTile(Location loc, Integer i){
        this.map.put(loc, i);
    }
    
    /**
     * Questo metodo viene usato per mettere le caselle vuote(quindi 
     * con valore -1) nella griglia durante le modifiche di spostamento.
     * @param loc 
     */
    public void removeTile(Location loc){
        this.map.put(loc, -1);
    }
    
    /**
     * Questo metodo rappresenta mossa del giocatore(automatico), 
     * aggiornando, di conseguenza, la griglia.
     * @param d 
     */
    public void move(Direction d){
        boolean isMoved=false;
        int x, y, val, moveTester;
        Location temp, farthest, startingPosition;
        
        if(d==Direction.UP){
            for(y=1;y<yPositions;y++){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=this.map.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMoved=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(d);
                            moveTester++;
                        }while (temp.isValidFor(4) && this.map.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val==this.map.get(temp)){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                isMoved=true;
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
                    val=this.map.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMoved=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(d);
                            moveTester++;
                        }while (temp.isValidFor(4) && this.map.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val==this.map.get(temp)){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                isMoved=true;
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
                    val=this.map.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMoved=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(d);
                            moveTester++;
                        }while (temp.isValidFor(4) && this.map.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val==this.map.get(temp)){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                isMoved=true;
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
                    val=this.map.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMoved=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(d);
                            moveTester++;
                        }while (temp.isValidFor(4) && this.map.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val==this.map.get(temp)){
                                this.modifyTile(temp, val*2);
                                this.removeTile(startingPosition);
                                isMoved=true;
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
        
        if(isMoved){
            this.newRandomTile();
        }
    }
    
}
