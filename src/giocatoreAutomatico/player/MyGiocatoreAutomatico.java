package giocatoreAutomatico.player;
import giocatoreAutomatico.*;
import game2048.Location;
import game2048.Direction;
import java.util.HashMap;
import java.util.Random;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{
    private final int xPositions=4;
    private final int yPositions=4;
    
    @Override
    public int prossimaMossa(Griglia g){
        /*Qui simulo i 4 movimenti eseguiti nella griglia, spostando
         *le varie piastrelle e unendole quando hanno i valori uguali.
         *Ad ogni unione aumento le variabili mergedIf, che servono per
         *indicare qual'è la direzione più conveniente.I vari isMoved
         *servono per indicare se almeno una piastrella è stata mossa, 
         *e quindi se bisogna aggiungerne una nuova nella griglia.*/
        HashMap<Location, Integer> grid=(HashMap<Location, Integer>)g;
        Location startingPosition, farthest, temp;
        Integer val;
        int x, y, mergedIfUp=0, mergedIfDown=0, mergedIfRight=0, mergedIfLeft=0, moveTester;
        boolean isMovedUp=false, isMovedDown=false, isMovedRight=false, isMovedLeft=false;
        
        //Case UP.
        for(y=1;y<yPositions;y++){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=grid.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMovedUp=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(Direction.UP);
                            moveTester++;
                        }while (temp.isValidFor(4) && grid.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(grid.get(temp))){
                                grid.put(temp, val*2);
                                grid.put(startingPosition, -1);
                                isMovedUp=true;
                                mergedIfUp++;
                            }else{
                                grid.put(farthest, val);
                                grid.put(startingPosition, -1);
                            }
                        }else{
                            grid.put(farthest, val);
                            grid.put(startingPosition, -1);
                        }
                    }
                 
                }
            }
            
            //Case DOWN.
            grid=(HashMap<Location, Integer>)g;
            
            for(y=2;y>=0;y--){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=grid.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMovedDown=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(Direction.DOWN);
                            moveTester++;
                        }while (temp.isValidFor(4) && grid.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(grid.get(temp))){
                                grid.put(temp, val*2);
                                grid.put(startingPosition, -1);
                                isMovedDown=true;
                                mergedIfDown++;
                            }else{
                                grid.put(farthest, val);
                                grid.put(startingPosition, -1);
                            }
                        }else{
                            grid.put(farthest, val);
                            grid.put(startingPosition, -1);
                        }
                    }
                 
                }
            }
            
            //Case RIGHT.
            grid=(HashMap<Location, Integer>)g;
            
            for(x=2;x>=0;x--){
                for(y=0;y<yPositions;y++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=grid.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMovedRight=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(Direction.RIGHT);
                            moveTester++;
                        }while (temp.isValidFor(4) && grid.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(grid.get(temp))){
                                grid.put(temp, val*2);
                                grid.put(startingPosition, -1);
                                isMovedRight=true;
                                mergedIfRight++;
                            }else{
                                grid.put(farthest, val);
                                grid.put(startingPosition, -1);
                            }
                        }else{
                            grid.put(farthest, val);
                            grid.put(startingPosition, -1);
                        }
                    }
                 
                }
            }
            
            //Case LEFT.
            grid=(HashMap<Location, Integer>)g;
            
            for(x=1;x<xPositions;x++){
                for(y=0;y<yPositions;y++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=grid.get(startingPosition);
                    moveTester=0;
                    
                    if(val!=-1){
                        do {
                            if(moveTester>0){
                                isMovedLeft=true;
                            }
                            farthest = temp;
                            temp = farthest.offset(Direction.LEFT);
                            moveTester++;
                        }while (temp.isValidFor(4) && grid.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(grid.get(temp))){
                                grid.put(temp, val*2);
                                grid.put(startingPosition, -1);
                                isMovedLeft=true;
                                mergedIfLeft++;
                            }else{
                                grid.put(farthest, val);
                                grid.put(startingPosition, -1);
                            }
                        }else{
                            grid.put(farthest, val);
                            grid.put(startingPosition, -1);
                        }
                    }
                 
                }
            }
        
    
    
        int better, rand;
        Random r=new Random();
        boolean upBetter=false, downBetter=false, rightBetter=false, leftBetter=false;
        
        /*Qui controllo qual'è il più grande tra i mergedIf, e 
         *quindi qual'è la direzione più conveniente da eseguire.*/
        if(mergedIfUp>mergedIfDown){
            better=mergedIfUp;
            upBetter=true;
        }else if(mergedIfUp<mergedIfDown){
            better=mergedIfDown;
            downBetter=true;
        }else{
            rand=r.nextInt(2);
            if(rand==0){
                better=mergedIfUp;
                upBetter=true;
            }else{
                better=mergedIfDown;
                downBetter=true;
            }
        }
        
        if(upBetter){
            if(better>mergedIfRight){
                better=mergedIfUp;
                upBetter=true;
            }else if(better<mergedIfRight){
                better=mergedIfRight;
                rightBetter=true;
                upBetter=false;
            }else{
                rand=r.nextInt(2);
                if(rand==0){
                    better=mergedIfUp;
                    upBetter=true;
                }else{
                    better=mergedIfRight;
                    rightBetter=true;
                    upBetter=false;
                }
            }
        }else{
            if(better>mergedIfRight){
                better=mergedIfDown;
                downBetter=true;
            }else if(better<mergedIfRight){
                better=mergedIfRight;
                rightBetter=true;
                downBetter=false;
            }else{
                rand=r.nextInt(2);
                if(rand==0){
                    better=mergedIfDown;
                    downBetter=true;
                }else{
                    better=mergedIfRight;
                    rightBetter=true;
                    downBetter=false;
                }
            }
        }
        
        if(better>mergedIfLeft){
            if(upBetter){
                return 0;
            }else if(downBetter){
                return 2;
            }else{
                return 1;
            }
        }else if(better<mergedIfLeft){
            return 3;
        }else{
            rand=r.nextInt(2);
            if(rand==0){
                if(upBetter){
                    return 0;
                }else if(downBetter){
                    return 2;
                }else{
                    return 1;
                }
            }else{
                return 3;
            }
        }
    }
    
}
