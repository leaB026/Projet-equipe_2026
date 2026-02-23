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
    ArrayList<String> personnages = new ArrayList<>();


    @Override
    public void start(Stage stage) throws IOException {
        personnages.add("Personnage1.png");
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

                simulation.update(deltaTemps);
                simulation.draw(context, simulation);


            }
        };
        timer.start();
        scene.setOnKeyPressed((e) -> conditionInput(e.getCode()));
        scene.setOnKeyReleased((e) -> Input.setKeyPressed(e.getCode(), false));
        scene.setOnMousePressed((e) -> conditionInput2(e));


        stage.setScene(scene);
        stage.setTitle("Boing Boing 3000");
        stage.show();
    }

    public void conditionInput(KeyCode e) {
        if (e == KeyCode.ESCAPE) {
            //FermeJavaFX
            Platform.exit();
        } else if (e == KeyCode.D) {
        } else if (e == KeyCode.F) {
        } else if (e == KeyCode.I) {
        } else {
            Input.setKeyPressed(e, true);
        }
    }

    public void conditionInput2(MouseEvent e) {



    }
}

