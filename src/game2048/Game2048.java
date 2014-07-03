package game2048;

import giocatoreAutomatico.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author bruno.borges@oracle.com
 */
public class Game2048 extends Application {

    private GameManager gameManager;
    private Bounds gameBounds;
    private GiocatoreAutomatico giocAutom;

    @Override
    public void start(Stage primaryStage) {
        gameManager = new GameManager();
        gameBounds = gameManager.getLayoutBounds();

        StackPane root = new StackPane(gameManager);
        root.setPrefSize(gameBounds.getWidth(), gameBounds.getHeight());
        ChangeListener<Number> resize = (ov, v, v1) -> {
            gameManager.setLayoutX((root.getWidth() - gameBounds.getWidth()) / 2d);
            gameManager.setLayoutY((root.getHeight() - gameBounds.getHeight()) / 2d);
        };
        root.widthProperty().addListener(resize);
        root.heightProperty().addListener(resize);

        Scene scene = new Scene(root, 600, 720);
        scene.getStylesheets().add("game2048/game.css");
        
        VBox vbox=new VBox();
        vbox.setPadding(new Insets(250, 250, 250, 250));
        vbox.setSpacing(5);
        
        HBox hbox1=new HBox(), hbox2=new HBox(), hbox3=new HBox(), hbox4=new HBox();
        hbox2.setPadding(new Insets(10, 250, 10, 250));
        hbox3.setPadding(new Insets(10, 250, 10, 250));
        
        Font font=Font.font("Serif", 25);
        Text text1=new Text("Vuoi giocare con il giocatore automatico?");
        text1.setFont(font);
        text1.setFill(Color.BLUE);
        Text text2=new Text("NB:Durante il gioco automatico premi un tasto a caso per fare la prossima mossa.\nDurante il gioco manuale, premi 'a' per lasciare una mossa al giocatore automatico.");
        text2.setFill(Color.BLUE);
        
        Button autoPlayYes = new Button();
        autoPlayYes.setText("Si");
        autoPlayYes.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try{
                giocAutom=(GiocatoreAutomatico)GiocatoreAutomatico.getGiocatoreAutomatico();
                    addAutoPlayHandler(scene, giocAutom);
                }catch(Exception e){
                    System.out.println(e);
                }
                root.getChildren().remove(vbox);
            }
        });
        
        Button autoPlayNo = new Button();
        autoPlayNo.setText("No");
        autoPlayNo.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                addKeyHandler(scene);
                root.getChildren().remove(vbox);
            }
        });
        addSwipeHandlers(scene);
        

        if (isARMDevice()) {
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
        }

        if (Platform.isSupported(ConditionalFeature.INPUT_TOUCH)) {
            scene.setCursor(Cursor.NONE);
        }
        
        hbox1.getChildren().add(text1);
        hbox2.getChildren().add(autoPlayYes);
        hbox3.getChildren().add(autoPlayNo);
        hbox4.getChildren().add(text2);
        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        root.getChildren().add(vbox);

        primaryStage.setTitle("2048FX");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(gameBounds.getWidth());
        primaryStage.setMinHeight(gameBounds.getHeight());
        primaryStage.show();
    }

    private boolean isARMDevice() {
        return System.getProperty("os.arch").toUpperCase().contains("ARM");
    }

    private void addKeyHandler(Scene scene) {
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.S)) {
                gameManager.saveSession();
                return;
            }
            if (keyCode.equals(KeyCode.R)) {
                gameManager.restoreSession();
                return;
            }
            if(keyCode.equals(KeyCode.A)){
                try{
                this.giocAutom=(GiocatoreAutomatico)GiocatoreAutomatico.getGiocatoreAutomatico();
                    Direction direction=automaticPlayerChoice(this.giocAutom);
                    gameManager.move(direction);
                }catch(Exception e){
                    System.out.println(e);
                }
                return;
            }
            if (keyCode.isArrowKey() == false) {
                return;
            }
            Direction direction = Direction.valueFor(keyCode);
            gameManager.move(direction);
        });
    }
    
    private void addAutoPlayHandler(Scene scene, GiocatoreAutomatico giocAutom){
        scene.setOnKeyPressed(ke -> {
           Direction direction=automaticPlayerChoice(giocAutom);
            gameManager.move(direction); 
        });
    }
    
    /**
     * Qui faccio "sincronizzare" la mia griglia con la griglia del programma 
     * principale, cosi da mandare la situazione di gioco attuale al metodo 
     * prossimaMossa.Per far ciò ho dovuto aggiungere il metodo getGrid in 
     * GameManager, in quanto la griglia era private.
     * @param giocAutom Il giocatore automatico.
     * @return La direzione ottenuta da prossimaMossa.
     */
    private Direction automaticPlayerChoice(GiocatoreAutomatico giocAutom){
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
    }
    
    private void addSwipeHandlers(Scene scene) {
        scene.setOnSwipeUp(e -> gameManager.move(Direction.UP));
        scene.setOnSwipeRight(e -> gameManager.move(Direction.RIGHT));
        scene.setOnSwipeLeft(e -> gameManager.move(Direction.LEFT));
        scene.setOnSwipeDown(e -> gameManager.move(Direction.DOWN));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
