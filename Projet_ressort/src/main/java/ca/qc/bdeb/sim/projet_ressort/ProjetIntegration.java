package ca.qc.bdeb.sim.projet_ressort;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProjetIntegration extends Application {

    public static final double WIDTH = 900, HEIGHT = 580;

    private Simulation simulation;
    protected boolean pageIntro = true;


    @Override
    public void start(Stage stage) throws IOException {

        var root = new Pane();
        root.setBackground(Background.fill(Color.WHITE));
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        Text ressort = new Text("Les ressorts: ");

//        Button bouton = new Button();

        Simulation simulation = new Simulation();

        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long temps) {

                double deltaTemps = (temps - dernierTemps) * 1e-9;
                dernierTemps = temps;

                context.clearRect(0, 0, WIDTH, HEIGHT);

                simulation.update(deltaTemps, pageIntro);
                simulation.draw(context, simulation, pageIntro);


            }
        };
        timer.start();

        this.simulation = simulation;
        scene.setOnKeyPressed((e) -> conditionInput(e.getCode()));
//        scene.setOnKeyReleased((e) -> Input.setKeyPressed(e.getCode(), false));
//        scene.setOnKeyTyped((e) -> conditionInput(e.getCode()));
        scene.setOnMousePressed((e) -> conditionInput2(e));

        stage.setScene(scene);
        stage.setTitle("Boing Boing 3000");
        stage.show();
    }

    public void conditionInput(KeyCode e) {
        if (e == KeyCode.ESCAPE) {
            //FermeJavaFX
            Platform.exit();
        }
        if (e == KeyCode.LEFT) {
            simulation.personnagePrecedent();
        } else if (e == KeyCode.RIGHT) {
            simulation.personnageSuivant();
        }

        Input.setKeyPressed(e, true);

    }

    public void conditionInput2(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            double positionX = e.getX();
            double positionY = e.getY();

            if (positionY > 320 && positionY < 400) {

                if (positionX > 200 && positionX < 230) {
                    simulation.personnagePrecedent();
                }else if (positionX > 650 && positionX < 680){
                    simulation.personnageSuivant();
                }
            }

            if (positionY > 520 && positionY < 550){
                if(positionX >410 && positionX < 490){
                    pageIntro = false;
                }
            }

        }


    }
}

