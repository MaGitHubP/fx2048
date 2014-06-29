package giocatoreAutomatico.player;
import giocatoreAutomatico.*;
import game2048.Direction;
import java.util.Random;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{
    /**
     * Questo metodo serve al giocatore automatico per decidere la 
     * direzione più conveniente da eseguire, basandosi sulla griglia 
     * di gioco passata per parametro.
     * @param g La griglia di gioco attuale.
     * @return 0=Su, 1=Destra, 2=Giù, 3=Sinistra.
     */
    @Override
    public int prossimaMossa(Griglia g){
        /*Qui simulo i 4 movimenti eseguiti nella griglia, spostando
         *le varie piastrelle e unendole quando hanno i valori uguali.
         *Ad ogni unione aumento le variabili mergedIf, che servono per
         *indicare qual'è la direzione più conveniente.*/
        GrigliaGame grid=(GrigliaGame)g;
        int mergedIfUp, mergedIfDown, mergedIfRight, mergedIfLeft;
        
        //Case UP.
        mergedIfUp=grid.move(Direction.UP);
            
        //Case DOWN.
        grid=(GrigliaGame)g;
        mergedIfDown=grid.move(Direction.DOWN);
            
            
        //Case RIGHT.
        grid=(GrigliaGame)g;
        mergedIfRight=grid.move(Direction.RIGHT);
            
        //Case LEFT.
        grid=(GrigliaGame)g;
        mergedIfLeft=grid.move(Direction.LEFT);
        
    
    
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
