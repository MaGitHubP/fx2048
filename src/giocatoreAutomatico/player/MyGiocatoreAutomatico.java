package giocatoreAutomatico.player;
import game2048.Location;
import game2048.Direction;
import java.util.HashMap;
import java.util.Iterator;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{
    
    public int prossimaMossa(Griglia g){
        /*Qui faccio un iterazione sugli elementi della HashMap,
         *sia le Key sia le Value.
        */
        Iterator it=g.entrySet().iterator();
        Location loc, farthest, temp;
        Integer val;
        while(it.hasNext()){
            HashMap.Entry tile=(HashMap.Entry)it.next();
            loc=(Location)tile.getKey();
            temp=(Location)tile.getKey();
            val=(Integer)tile.getValue();
            
            do {
            farthest = temp;
            temp = farthest.offset(Direction.UP);
            }while (temp.isValidFor(4) && g.get(temp) == -1);
            
        }
        
    }
   
    
}
