Progetto PR2
======

Nome:Mauro
Cognome:Pisano
Matricola:48406
Questo progetto consiste nell'aggiungere un giocatore automatico al gioco 2048.
Una volta avviato, il programma chiede se si vuole giocare col giocatore 
automatico o se lasciare giocare l'utente.Se si sceglie "si", premi un tasto
a caso per lasciar eseguire una mossa al giocatore automatico.Se si sceglie "no",
sarà l'utente a giocare.Si può comunque lasciare una mossa al giocatore automatico 
premendo il tasto "a".Di seguito elencherò le aggiunte/modifiche che ho fatto nel 
programma ed eventualmente come funzionano.

MyGiocatoreAutomatico
====================

Questa classe implementa l'interfaccia GiocatoreAutomatico, dunque il metodo 
abstract prossimaMossa viene implementato qui.Il metodo prende in ingresso un 
oggetto di tipo Griglia, che rappresenta la griglia 4x4 contenente la 
situazione attuale del gioco.All'inizio di questo metodo, vengono prima simulate
tutte e quattro le direzioni in input su una copia della griglia grid2 (grid 
serve soltanto per ripristinare la griglia originale, una volta alterato 
grid2 per i movimenti).Gli interi x e y rappresentano le coordinate (x, y) della
griglia e vengono usate per inizializzare di volta in volta le varie Location.

```bash
//Case UP.
        for(y=1;y<yPositions;y++){
                for(x=0;x<xPositions;x++){
                    startingPosition=new Location(x, y);
                    temp=startingPosition;
                    val=grid.get(startingPosition);
                    
                    if(val!=-1){
                        do {
                            farthest = temp;
                            temp = farthest.offset(Direction.UP);
                        }while (temp.isValidFor(4) && grid.get(temp) == -1);
                        if(temp.isValidFor(4)){
                            if(val.equals(grid.get(temp))){
                                grid2.put(temp, val*2);
                                grid2.put(startingPosition, -1);
                                mergedIfUp++;
                            }else{
                                grid2.put(farthest, val);
                                grid2.put(startingPosition, -1);
                            }
                        }else{
                            grid2.put(farthest, val);
                            grid2.put(startingPosition, -1);
                        }
                    }
                 
                }
            }

//Case DOWN.
	etc...
```
Attraverso l'uso di una matrice, ogni piastrella della griglia viene selezionata
e spostata nella direzione indicata(prima viene simulato l'UP, poi il DOWN, poi
RIGHT ed infine LEFT).Per far ciò, la locazione iniziale e il valore di ogni 
piastrella vengono rappresentate rispettivamente da startingPosition e val.Se 
nella posizione attuale della griglia val!=-1(cioè se nella posizione attuale 
c'è una piastrella non vuota), allora tale piastrella viene spostata nel punto 
più lontano possibile(farthest è il punto più lontano, temp è una Location 
temporanea utile per tale scopo.).Una volta finito il while, si verifica la causa
che lo ha fermato.Se !temp.isValidFor(4)(cioè se temp ha "sforato" la griglia), 
allora la piastrella che stiamo facendo muovere ha raggiunto il fondo della griglia, 
se invece temp.isValidFor(4)(cioè se temp non ha "sforato" la griglia), allora 
questa ha trovato un'altra piastrella durante la strada.
Se il valore delle due piastrelle è diverso, si aggiornano gli spostamenti e basta, 
altrimenti le piastrelle si fondono e viene aggiornato un contatore mergedIf.Finito 
con una piastrella, si passa con la successiva fino a che le abbiamo fatte spostare
tutte.
I vari contatori mergedIf indicano quante fusioni ci sono state per ogni direzione
intrapresa, e sono i punti chiave per la scelta della direzione migliore da prendere.
Infatti nella parte finale del metodo, i vari mergedIf vengono confrontati fra loro, 
e il più grande indica la direzione da mandare in output(se ad esempio il più grande 
è mergedIfDown, prossimaMossa fa return 2, cioè giù.).Se ci dovessero essere due o più 
mergedIf uguali, la direzione "migliore" viene scelta a caso attraverso Random.
I vari isLast sono booleani statici che indicano qual'è stata l'ultima direzione
messa in input dal giocatore automatico.Prima di fare il return di una direzione, 
infatti, viene controllato se tale direzione è stata eseguita precedentemente.Ciò 
serve perché, se per qualche qualche strano motivo(tipo un eventuale bug) un mergedIf 
viene considerato migliore due volte di seguito, si genera un'ovvia situazione di 
deadlock.

Game2048
===================

Prima di tutto ho aggiunto una scelta per l'utente se vuole giocare con il giocatore 
automatico o no.Se si sceglie "si", l'identificatore GiocatoreAutomatico viene 
inizializzato con GetGiocatoreAutomatico e viene lanciato il metodo addAutoPlayHandler
(il tutto dentro un try catch, per lanciare un eccezione in caso di errore).Altrimenti 
viene lanciato il metodo del programma standard, addKeyHandler.In entrambi i casi, 
il VBox contenente la selezione del giocatore viene rimossa per lasciare libera la 
visione della griglia.
Il metodo addAutoPlayHandler viene attivato ogni volta che si preme un tasto, chiama 
il metodo automaticPlayerChoice il cui restituisce una direzione ed infine esegue il 
metodo move con la direzione data.
Il metodo automaticPlayerChoice ha come parametro il giocatore automatico giocAutom, 
e restituisce una Direction.

```bash
Location l;
        Tile t;
        Integer i;
        int dir;
        GrigliaGame g=new GrigliaGame();
        HashMap<Location, Tile> g2=(HashMap<Location, Tile>)gameManager.getGrid();
        Iterator it=g2.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry temp=(HashMap.Entry)it.next();
            l=(Location)temp.getKey();
            t=g2.get(l);
            if(t==null){
                i=-1;
            }else{
                i=t.getValue();
            }
                    
            g.put(l, i);
        }
        
        Direction direction;
        dir=giocAutom.prossimaMossa(g);
        if(dir==0){
            direction=Direction.UP;
        }else if(dir==1){
            direction=Direction.RIGHT;
        }else if(dir==2){
            direction=Direction.DOWN;
        }else{
            direction=Direction.LEFT;
        }
        
        return direction;
```
Il compito principale di questo metodo è chiamare prossimaMossa di giocAutom, mandando 
in input una griglia con key Location e value Integer.Dato che la griglia principale 
del gioco ha come value Tile, prendo quest'ultima e ne faccio una copia assegnandola 
ad una HashMap<Location, Tile> g2, e faccio un'iterazione su di essa usando Iterator 
ed HashMap.Entry.
Ad ogni ciclo,ottengo la Location di ogni posizione della griglia e il valore della 
relativa Tile(Se la Tile è vuota, il valore è -1.).Dopodichè inserisco i dati ottenuti 
in GrigliaGame(Che è la mia griglia personale).Una volta che GrigliaGame si sia 
"sincronizzata" con la griglia principale, la metto in input su prossimaMossa, che 
svolgerà i relativi algoritmi visti precedentemente, e restituisce un intero.In base 
all'intero ottenuto, automaticPlayerChoice restituisce una Direction.
In addKeyHandler ho aggiunto la possibilità di chiamare automaticPlayerChoice premendo 
il tasto "a".Ho dovuto separare i due metodi addAutoPlayHandler ed automaticPlayerChoice 
invece di farne uno solo per due motivi;Il primo è per  evitare di dover schiacciare 
due volte "a" durante la scelta del giocatore automatico in addKeyHandler.Il secondo 
è perché altrimenti, se durante il gioco manuale si sarebbe premuto "a", il metodo 
addAutoPlayHandler avrebbe prevalso su addKeyHandler(e quindi il giocatore automatico 
prevale sull'utente.).

GrigliaGame
===================

Questa classe rappresenta semplicemente una griglia 4x4 del gioco, e viene usata dal 
metodo prossimaMossa per ricavarci la direzione migliore simulando le 4 direzioni.
Questa classe implementa Griglia ed estende una HashMap<Location, Integer>.

GameManager
===================

Qui ho semplicemente aggiunto il metodo getGrid, che ritorna la griglia di gioco, 
in quanto quest'ultima era a private e non potevo ricavarla in nessun altro modo.

```bash
public Map<Location, Tile> getGrid(){
        return this.gameGrid;
    }
```
