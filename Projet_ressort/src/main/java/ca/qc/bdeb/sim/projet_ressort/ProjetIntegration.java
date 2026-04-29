package ca.qc.bdeb.sim.projet_ressort;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.controlsfx.control.ToggleSwitch;

public class ProjetIntegration extends Application {


    public static final double WIDTH = 900, HEIGHT = 580;

    private Simulation simulation;
    protected boolean pageIntro = true;
    private ChoiceBox<String> menuPlanetes;
    ToggleSwitch forceAmortissement = new ToggleSwitch("Force d'amortissement");


    @Override
    public void start(Stage stage) throws IOException {


        music();
        Simulation simulation = new Simulation();

        var root = new Pane();
        root.setBackground(Background.fill(Color.WHITE));
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

//        Pane rootGraphique = new Pane();
        VBox partieGraphique = new VBox(45);
//        Stage graphique = new Stage();
//        Scene sceneGraphique = new Scene(rootGraphique, 500, 500);
        Popup popup1 = new Popup();

        Button pause = new Button("▶\uFE0E‖");
        pause.setLayoutX(850);
        pause.setLayoutY(200);

        root.getChildren().add(pause);
//        pause.setVisible(!pageIntro);
          /*
  JE NE SAIS PAS C'EST QUOI LA DIFFENRECE ENTRE ATOMIC
  BOOLEAN ET BOOLEAN SIMPLE MAIS QUAND C'ÉTAIT ROUGE ÇA M'A PROPOSÉ CELA
   */
        AtomicBoolean isPlaying = new AtomicBoolean(true);

        pause.setOnAction(e -> {
            if (mediaPlayer != null) {
                if (isPlaying.get()) {
                    mediaPlayer.pause();
                    isPlaying.set(false);
                } else {
                    mediaPlayer.play();
                    isPlaying.set(true);
                }
            }
        });



        Image iconeGraphique = new Image("/iconBARCHART.png");
        ImageView imgViewGrah = new ImageView(iconeGraphique);
        imgViewGrah.setFitWidth(30);
        imgViewGrah.setFitHeight(30);

        partieGraphique.setPadding(new Insets(15, 12, 15, 12));
        HBox fermer = new HBox();
//https://www.geeksforgeeks.org/java/javafx-font-class/
        Font font = Font.font("Verdana", FontWeight.BOLD, 12);

        Button show = new Button();
//
//        k.setTextAlignment(TextAlignment.CENTER);
//        k.setStyle("-fx-font-size: 12px");

        Text jouerK = new Text("Jouez avec la constante de rappel! ");
        jouerK.setTextAlignment(TextAlignment.CENTER);
        jouerK.setStyle("-fx-font-size: 12px");
        jouerK.setFont(font);

        Label labelK = new Label("k = 250 N/m");
        labelK.setFont(font);


        show.setGraphic(imgViewGrah);
        show.setStyle("-fx-background-color: transparent;");

        Button hide = new Button("Fermer");

        hide.setFont(font);
        hide.setStyle("-fx-background-color: #EF9F27;");
        hide.setOnMouseEntered((e) -> {


            hide.setStyle("-fx-background-color: #BA7517;");
        });


        hide.setOnMouseExited((e) -> {


            hide.setStyle("-fx-background-color: #EF9F27;");
        });
        canvas.requestFocus();


        var popUp = new Popup();
        var vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20));
        vbox.setBackground(Background.fill(Color.BISQUE));

        popUp.getContent().add(vbox);
        Text titre = new Text(simulation.personnageChoisie.nom);
        titre.setFont(Font.font(20));
        titre.isUnderline();
        Text information = new Text("Poid: " + simulation.personnageChoisie.masse + " kg");
        information.setFont(Font.font(10));
        vbox.getChildren().addAll(titre, information);

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
                forceAmortissement.setVisible(!pageIntro);

                pause.setVisible(!pageIntro);

            }

        };
        timer.start();

        simulation.getBc().draw();

        CornerRadii radii = new CornerRadii(10);
        Insets insets = new Insets(10);
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGRAY, radii, Insets.EMPTY);


        partieGraphique.setPrefWidth(500);
        partieGraphique.setPrefHeight(700);

        Background background = new Background(background_fill);


        fermer.getChildren().add(hide);
        fermer.setAlignment(Pos.CENTER_LEFT);


        partieGraphique.getChildren().add(fermer);


        partieGraphique.setBackground(background);

        partieGraphique.getChildren().add(simulation.bc.getBc());


        partieGraphique.getChildren().add(jouerK);

        partieGraphique.getChildren().add(labelK);

        partieGraphique.getChildren().add(simulation.slider);
        //https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/slider.htm#CCHFBJCH
        simulation.getSlider().valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            labelK.setText("K = " + new_val.doubleValue() / 10 + "N/m");
        });

        partieGraphique.setAlignment(Pos.CENTER);

        popup1.getContent().add(partieGraphique);
        popup1.setAutoHide(false);


        // MENU DEROULANT PLANET

        menuPlanetes = new ChoiceBox<>();
        menuPlanetes.getItems().addAll("Terre", "Lune", "Mars");
        menuPlanetes.setValue("Planètes");
        menuPlanetes.setLayoutX(WIDTH - 160);
        menuPlanetes.setLayoutY(20);
        menuPlanetes.setPrefWidth(140);
        root.getChildren().add(menuPlanetes);

        menuPlanetes.setVisible(false);

        menuPlanetes.setOnAction(e -> {
            String choix = menuPlanetes.getValue();
            simulation.changerPlanete(choix);
        });

        this.simulation = simulation;

        scene.setOnKeyPressed((e) -> conditionInput(e.getCode()));
        scene.setOnMousePressed((e) -> conditionInput2(e));
        scene.setOnMouseReleased(e -> Input.setMousePressed(e.getButton(), false));
        scene.setOnMouseDragged((e) -> Input.setMousePosition(e.getX(), e.getY()));

        root.getChildren().add(show);

        forceAmortissement.setLayoutX(WIDTH - 230);
        forceAmortissement.setLayoutY(55);
        forceAmortissement.setSelected(false);
        forceAmortissement.setVisible(false); // pas visible si il est sur la page d'introduction

        forceAmortissement.selectedProperty().addListener((obs, old, val) -> {
            this.simulation.utiliserAmortissement = val;

        });
        root.getChildren().add(forceAmortissement);

        canvas.setOnMouseMoved((e) -> {
            if (pageIntro) {  // marche seulement quand on est sur la page d'intro
                double positionPersonnageX = simulation.personnageChoisie.position.getX();
                double positionPersonnageY = simulation.personnageChoisie.position.getY();
                double taillePersonnageX = simulation.personnageChoisie.taille.getX();
                double taillePersonnageY = simulation.personnageChoisie.taille.getY();

                double screenX = canvas.localToScreen(
                        simulation.personnageChoisie.getPosition().getX() + (simulation.personnageChoisie.getTaille().getX()) + 70,
                        simulation.personnageChoisie.getPosition().getY()
                ).getX();

                double screenY = canvas.localToScreen(
                        simulation.personnageChoisie.getPosition().getX(),
                        simulation.personnageChoisie.getPosition().getY() + (simulation.personnageChoisie.getTaille().getY() / 2) - 50 // center vertically on character
                ).getY();

                if (e.getX() >= positionPersonnageX && e.getX() <= positionPersonnageX + taillePersonnageX && e.getY() >= positionPersonnageY && e.getY() <= positionPersonnageY + taillePersonnageY) {
                    titre.setText(simulation.personnageChoisie.nom);
                    information.setText("Poid: " + simulation.personnageChoisie.masse + " kg");

                    popUp.show(stage, screenX, screenY);
                    Platform.runLater(() -> canvas.requestFocus());
                } else {
                    popUp.hide();
                }
            } else {
                popUp.hide();
            }
        });

        stage.setScene(scene);
        stage.setTitle("Boing Boing 3000");

        stage.show();

        show.setOnAction((e) -> {


            if (popup1.isShowing()) {
                popup1.hide();
            } else {
                popup1.show(stage, stage.getX() - 600, stage.getY());
            }
            canvas.requestFocus();
        });


        hide.setOnAction((e) -> {


            if (popup1.isShowing()) {
                popup1.hide();
            }
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
        if(e== KeyCode.P){
            simulation.personnageFinal.setPause(!simulation.personnageFinal.isPause());
        }
        if(e == KeyCode.H){
            simulation.personnageFinal.setVecteurLoiHooke(!simulation.personnageFinal.isVecteurLoiHooke());
        }
        if(e== KeyCode.G){
            simulation.personnageFinal.setVecteurForceGravit(!simulation.personnageFinal.isVecteurForceGravit());

        }
        Input.setKeyPressed(e, true);
    }

    public void conditionInput2(MouseEvent e) {

        Input.setMousePressed(e.getButton(), true);
        Input.setMousePosition(e.getX(), e.getY());

        if (e.getButton() == MouseButton.PRIMARY) {
            double positionX = e.getX();
            double positionY = e.getY();

            if (pageIntro) {
                if (positionY > 320 && positionY < 400) {
                    if (positionX > 200 && positionX < 230) {
                        simulation.personnagePrecedent();
                    } else if (positionX > 650 && positionX < 680) {
                        simulation.personnageSuivant();
                    }
                }
                if (positionY > 520 && positionY < 550) {
                    if (positionX > 410 && positionX < 490) {
                        pageIntro = false;
                    }
                }
            } else if (!pageIntro) {
                if (positionY > HEIGHT - 50 && positionY < HEIGHT) {
                    if (positionX > 10 && positionX < 60) {
                        pageIntro = true;
                        simulation.creerPersonnageFinal = false;
                    }
                }
            }
        }
    }


    MediaPlayer mediaPlayer;


    public void music() {
        //CODE POUR MUSIC

        Media media = new Media(getClass().getResource("/music2.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }
}




