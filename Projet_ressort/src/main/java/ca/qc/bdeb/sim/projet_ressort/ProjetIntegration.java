package ca.qc.bdeb.sim.projet_ressort;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProjetIntegration extends Application {


    public static final double WIDTH = 900, HEIGHT = 580;

    private Simulation simulation;
    protected boolean pageIntro = true;
    private ChoiceBox<String> menuPlanetes;


    @Override
    public void start(Stage stage) throws IOException {

        var root = new Pane();
        root.setBackground(Background.fill(Color.WHITE));
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        Pane rootGraphique = new Pane();
        VBox partieGraphique = new VBox(45);
        Stage graphique = new Stage();
        Scene sceneGraphique = new Scene(rootGraphique, 500, 500);



        Image iconeGraphique = new Image("/iconBARCHART.png");
        ImageView imgViewGrah = new ImageView(iconeGraphique);
        imgViewGrah.setFitWidth(30);
        imgViewGrah.setFitHeight(30);

        Button show = new Button();
        Text k = new Text("Constante de rappel");
        k.setTextAlignment(TextAlignment.CENTER);
        k.setStyle("-fx-font-size: 12px");

        Text jouerK = new Text("Jouez avec la constante de rappel! ");
        jouerK.setTextAlignment(TextAlignment.CENTER);
        jouerK.setStyle("-fx-font-size: 12px");

        show.setGraphic(imgViewGrah);
        show.setStyle("-fx-background-color: transparent;");

        //CLAUDE AI
        graphique.addEventFilter(KeyEvent.ANY, e -> {
            if(e.getEventType() == KeyEvent.KEY_PRESSED) {
                scene.getOnKeyPressed().handle(e);
            }else if(e.getEventType() == KeyEvent.KEY_RELEASED){
                scene.getOnKeyReleased().handle(e);
            }
            e.consume();
        });

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
                menuPlanetes.setVisible(!pageIntro);

            }

        };
        timer.start();

        simulation.getBc().draw();

        CornerRadii radii = new CornerRadii(10);
        Insets insets = new Insets(10);
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGRAY,  radii, Insets.EMPTY);


        partieGraphique.setPrefWidth(500);
        partieGraphique.setPrefHeight(500);

        Background background = new Background(background_fill);


        partieGraphique.setBackground(background);

        partieGraphique.getChildren().add(simulation.bc.getBc());

        partieGraphique.getChildren().add(k);

        partieGraphique.getChildren().add(jouerK);

        partieGraphique.getChildren().add(simulation.slider);

        // MENU DEROULANT PLANEET

        menuPlanetes = new ChoiceBox<>();

        menuPlanetes.getItems().addAll("Terre", "Lune", "Mars");
        menuPlanetes.setValue("Planètes");
        menuPlanetes.setLayoutX(WIDTH - 160);
        menuPlanetes.setLayoutY(20);
        menuPlanetes.setPrefWidth(140);
        root.getChildren().add(menuPlanetes);

            menuPlanetes.setVisible(false);
        rootGraphique.getChildren().addAll(partieGraphique);

        menuPlanetes.setOnAction(e -> {
            String choix = menuPlanetes.getValue();
            simulation.changerPlanete(choix);
        });
        this.simulation = simulation;
        scene.setOnKeyPressed((e) -> conditionInput(e.getCode()));
        scene.setOnMousePressed((e) -> conditionInput2(e));
        scene.setOnMouseReleased(e ->Input.setMousePressed(e.getButton(), false));
        scene.setOnMouseDragged((e) ->Input.setMousePosition(e.getX(), e.getY()));

        root.getChildren().add(show);
        stage.setScene(scene);
        stage.setTitle("Boing Boing 3000");
        graphique.setScene(sceneGraphique);
        stage.show();
        show.setOnAction((e) -> {

            graphique.setX(-5);

            graphique.show();


        });
        graphique.setOnCloseRequest((e) -> {

            graphique.hide();


        });
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

        Input.setMousePressed(e.getButton(), true);
        Input.setMousePosition(e.getX(), e.getY());

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

