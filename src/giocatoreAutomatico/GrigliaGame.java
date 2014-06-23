package giocatoreAutomatico;
import game2048.Location;
import game2048.Direction;
import java.util.HashMap;


public class GrigliaGame implements Griglia{

    private final HashMap<Location, Integer> map;
    private static final int xPositions=4;
    private static final int yPositions=4;
    
    public GrigliaGame(){
        this.map=new HashMap();
        
        for(int j=0;j<this.yPositions;j++){
            for(int i=0;i<this.xPositions;i++){
                this.map.put(new Location(i, j), -1);
            }
        }
    }
    
    
    
}
