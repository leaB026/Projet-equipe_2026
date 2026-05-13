package ca.qc.bdeb.sim.projet_ressort;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;

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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;

public class ProjetIntegration extends Application {


    public static final double WIDTH = 900, HEIGHT = 580;
    private Simulation simulation;
    protected boolean pageIntro = true;
    private ChoiceBox<String> menuPlanetes;
    protected ToggleSwitch forceAmortissement = new ToggleSwitch();
    protected boolean estApparu = false;

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

        VBox partieGraphique = new VBox(45);
//https://www.geeksforgeeks.org/java/javafx-font-class/
        Font font = Font.font("Verdana", FontWeight.BOLD, 12);

        Popup popup1 = new Popup();

        Button showSlider = new Button("⛭");
//"♫"
        Button parametres = new Button();
        Button planetes = new Button();
        Button pause = new Button();
        Image iconeMusique = new Image("onn-removebg-preview.png");
        Image iconeMusiqueOff = new Image("offf-removebg-preview.png");
        ImageView imgOffMusic = new ImageView(iconeMusiqueOff);
        Image iconePl = new Image("iconpl.png");
        Image iconeParam = new Image("settings-removebg-preview.png");
        ImageView imgMusique = new ImageView(iconeMusique);
        ImageView imgpl = new ImageView(iconePl);
        ImageView imgParam = new ImageView(iconeParam);
        imgMusique.setFitWidth(40);
        imgMusique.setFitHeight(50);
        imgOffMusic.setFitWidth(40);
        imgOffMusic.setFitHeight(50);
        imgpl.setFitWidth(40);
        imgpl.setFitHeight(50);
        imgParam.setFitWidth(40);
        imgParam.setFitHeight(50);
        pause.setGraphic(imgMusique);
        parametres.setGraphic(imgParam);
        planetes.setGraphic(imgpl);
        parametres.setStyle("-fx-background-color: transparent;");
        planetes.setStyle("-fx-background-color: transparent;");
        pause.setStyle("-fx-background-color: transparent;");
        HBox banniere = new HBox(15);
        Label nomPlanete = new Label("Terre");
        VBox jeuOptions = new VBox(20);


        Button hideSlider = new Button("Fermer");
        hideSlider.setFont(font);
        hideSlider.setStyle("-fx-background-color: #5D6D7E;");
        hideSlider.setOnMouseEntered((e) -> {
                hideSlider.setStyle("-fx-background-color: #34495E;");
        });
        hideSlider.setOnMouseExited((e) -> {
                hideSlider.setStyle("-fx-background-color: #5D6D7E;");
    });


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
                    pause.setGraphic(imgOffMusic);

                } else {
                    mediaPlayer.play();
                    isPlaying.set(true);
                    pause.setGraphic(imgMusique);
                }
            }
        });



        Image iconeGraphique = new Image("iconBARCHART.png");
        ImageView imgViewGrah = new ImageView(iconeGraphique);
        imgViewGrah.setFitWidth(50);
        imgViewGrah.setFitHeight(40);

        partieGraphique.setPadding(new Insets(15, 12, 15, 12));
        HBox fermer = new HBox();

        Button show = new Button();
show.setGraphic(imgViewGrah);
//        k.setTextAlignment(TextAlignment.CENTER);
//        k.setStyle("-fx-font-size: 12px");

        Text jouerK = new Text("Jouez avec la constante de rappel! ");
        jouerK.setTextAlignment(TextAlignment.CENTER);
        jouerK.setStyle("-fx-font-size: 12px");
        jouerK.setFont(font);

        Label labelK = new Label("k = 250 N/m");
        labelK.setFont(font);
        VBox sliderSection = new VBox(8);
        sliderSection.setAlignment(Pos.CENTER);
        sliderSection.getChildren().addAll(jouerK, labelK, simulation.slider);

//        show.setGraphic(imgViewGrah);

        show.setStyle("-fx-background-color: transparent;");

        Button hide = new Button("Fermer");

        hide.setFont(font);
        hide.setStyle("-fx-background-color: #5D6D7E;");
        hide.setOnMouseEntered((e) -> {


            hide.setStyle("-fx-background-color: #34495E;");
        });


        hide.setOnMouseExited((e) -> {


            hide.setStyle("-fx-background-color: #5D6D7E;");
        });
        canvas.requestFocus();


        var popUp = new Popup();
        var vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20));
        vbox.setStyle(
                "-fx-background-color: #A8C5A0; "+
                        "-fx-border-color: #5D6D7E; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px;"
        );
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
                forceAmortissement.setVisible(!pageIntro);
                parametres.setVisible(!pageIntro);
                planetes.setVisible(!pageIntro);
                banniere.setVisible(!pageIntro);
                pause.setVisible(!pageIntro);

            }

        };
        timer.start();
        simulation.getBc().draw();

        CornerRadii radii = new CornerRadii(10);
        Insets insets = new Insets(10);


        partieGraphique.setPrefWidth(300);
        partieGraphique.setPrefHeight(500);



        fermer.getChildren().add(hide);
        fermer.setAlignment(Pos.CENTER_LEFT);



        partieGraphique.getChildren().add(simulation.bc.getBc());
        partieGraphique.getChildren().add(hide);

        jeuOptions.setBackground(Background.fill(Color.web("#A8C5A0")));
        partieGraphique.setBackground(Background.fill(Color.web("#A8C5A0")));

        //https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/slider.htm#CCHFBJCH
        simulation.getSlider().valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            labelK.setText("K = " + String.format("%.1f",new_val.doubleValue() / 10 ) + "N/m");
        });

        partieGraphique.setAlignment(Pos.CENTER);

        popup1.getContent().add(partieGraphique);
        popup1.setAutoHide(false);


//        // MENU DEROULANT PLANET
//
//        menuPlanetes = new ChoiceBox<>();
//        menuPlanetes.getItems().addAll("Terre", "Lune", "Mars");
//        menuPlanetes.setValue("Planètes");
//        menuPlanetes.setLayoutX(WIDTH - 160);
//        menuPlanetes.setLayoutY(20);
//        menuPlanetes.setPrefWidth(140);
//
//        menuPlanetes.setVisible(false);
//
//        menuPlanetes.setOnAction(e -> {
//            String choix = menuPlanetes.getValue();
//            simulation.changerPlanete(choix);
//        });

       //scroll POUR LE CHANGEMENT DE BACKGROUNDS

        StackPane bgs = new StackPane();
        bgs.setPrefWidth(200);
        bgs.setPrefHeight(200);
        bgs.setLayoutX(650);
        bgs.setLayoutY(350);


        Button choixBgs = new Button();
        Button choixBgsR = new Button();

        Image iconeChoixBgs = new Image("flecheDroite.png");
        Image iconeChoixBgsR = new Image("flecheGauche.png");

        ImageView imgChoixBgs = new ImageView(iconeChoixBgs);
        ImageView imgChoixBgsR = new ImageView(iconeChoixBgsR);
        imgChoixBgs.setFitWidth(30);
        imgChoixBgs.setFitHeight(50);
        imgChoixBgsR.setFitWidth(30);
        imgChoixBgsR.setFitHeight(50);
        choixBgs.setGraphic(imgChoixBgs);
        choixBgsR.setGraphic(imgChoixBgsR);
        choixBgs.setStyle("-fx-background-color: transparent;");
        choixBgsR.setStyle("-fx-background-color: transparent;");

        ArrayList<String> listePlanete = new ArrayList<>();
        listePlanete.add("Terre");
        listePlanete.add("Lune");
        listePlanete.add("Mars");

        Image bgLune = new Image("bgLune.jpeg");
        Image bgMars = new Image("bgMars.jpeg");
        Image bgRose = new Image("bgRose.jpeg");
        Image bgTerre = new Image("terre.jpg");

        ImageView imgLune = new ImageView(bgLune);
        ImageView imgMars = new ImageView(bgMars);
        ImageView imgRosee = new ImageView(bgRose);
        ImageView imgterre = new ImageView(bgTerre);



        Image preview = new Image("terre.jpg");
        ImageView imgPreview = new ImageView(preview);
        imgPreview.setFitWidth(200);  // la taille de ton StackPane
        imgPreview.setFitHeight(200);
        imgPreview.setPreserveRatio(false);
        // bgs.setBackground(Background.fill(Color.web("#FFE5D0")));
        bgs.getChildren().add(imgterre);
        imgterre.setFitWidth(200);
        imgterre.setFitHeight(200);
        imgterre.setPreserveRatio(false);
        int index[] = {0};

            Text titreP = new Text("Terre");
            titreP.setFont(font);

        choixBgs.setOnAction(e -> {
            index[0]++;

            if(index[0] >= 3){
                index[0] = 0;
            }

                simulation.changerPlanete(listePlanete.get(index[0]));

            if(index[0] == 0){
                imgPreview.setImage(bgTerre);
                titreP.setText("Terre");
                nomPlanete.setText("Terre");
                banniere.setStyle("-fx-background-color: #A8C5A0;");
                jeuOptions.setBackground(Background.fill(Color.web("#A8C5A0")));
                partieGraphique.setBackground(Background.fill(Color.web("#A8C5A0")));
            } else if(index[0] == 1){
                imgPreview.setImage(bgLune);
                nomPlanete.setText("Lune");
                banniere.setStyle("-fx-background-color: #8FA8B8;");
                jeuOptions.setBackground(Background.fill(Color.web("#8FA8B8")));
                partieGraphique.setBackground(Background.fill(Color.web("#8FA8B8")));
            } else if(index[0] == 2){
                imgPreview.setImage(bgMars);
                titreP.setText("Mars");
                nomPlanete.setText("Mars");
                banniere.setStyle("-fx-background-color: #D4845A;");
                jeuOptions.setBackground(Background.fill(Color.web("#D4845A")));
                partieGraphique.setBackground(Background.fill(Color.web("#D4845A")));

            }

            canvas.requestFocus();
        });
        choixBgsR.setOnAction(e -> {
            index[0]--;

            if(index[0] < 0){
                index[0] = 2;
            }

            simulation.changerPlanete(listePlanete.get(index[0]));

            if(index[0] == 0){
                imgPreview.setImage(bgTerre);
                titreP.setText("Terre");
                nomPlanete.setText("Terre");
                banniere.setStyle("-fx-background-color: #A8C5A0;");
                jeuOptions.setBackground(Background.fill(Color.web("#A8C5A0")));
                partieGraphique.setBackground(Background.fill(Color.web("#A8C5A0")));
            } else if(index[0] == 1){
                imgPreview.setImage(bgLune);
                titreP.setText("Lune");
                nomPlanete.setText("Lune");
                banniere.setStyle("-fx-background-color: #8FA8B8;");
                jeuOptions.setBackground(Background.fill(Color.web("#8FA8B8")));
                partieGraphique.setBackground(Background.fill(Color.web("#8FA8B8")));
            } else if(index[0] == 2){
                imgPreview.setImage(bgMars);
                titreP.setText("Mars");
                nomPlanete.setText("Mars");
                banniere.setStyle("-fx-background-color: #D4845A;");
                jeuOptions.setBackground(Background.fill(Color.web("#D4845A")));
                partieGraphique.setBackground(Background.fill(Color.web("#D4845A")));

            }

            canvas.requestFocus();
        });
        boolean []estVisible = {false};

        bgs.setVisible(false);

        planetes.setOnAction(e -> {
            if(!estVisible[0]){
                bgs.setVisible(true);
                estVisible[0] = true;
            } else {
                bgs.setVisible(false);
                estVisible[0]= false;
            }
            canvas.requestFocus();
        });
        bgs.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");
        bgs.getChildren().add(imgPreview);
        bgs.getChildren().add(choixBgs);
        bgs.getChildren().add(choixBgsR);
        bgs.getChildren().add(titreP);
        root.getChildren().add(bgs);
        StackPane.setAlignment(choixBgs, Pos.CENTER_RIGHT);
        StackPane.setAlignment(choixBgsR, Pos.CENTER_LEFT);



        this.simulation = simulation;

        scene.setOnKeyPressed((e) -> conditionInput(e.getCode()));
        scene.setOnMousePressed((e) -> conditionInput2(e));
        scene.setOnMouseReleased(e -> Input.setMousePressed(e.getButton(), false));
        scene.setOnMouseDragged((e) -> Input.setMousePosition(e.getX(), e.getY()));

        //GAME OPTIONS MENU

        //https://www.tutorialspoint.com/javafx/javafx_translate_transition.htm
        jeuOptions.setPadding(new Insets(5, 12, 15, 12));
        jeuOptions.setAlignment(Pos.CENTER);
        Label titrePanel = new Label("⛭ Paramètres!");

        titrePanel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));

        jeuOptions.getChildren().add(titrePanel);
        Separator sep = new Separator(Orientation.HORIZONTAL);
        HBox bottomJeuOptions = new HBox(50);
        bottomJeuOptions.setAlignment(Pos.CENTER);

        //https://docs.oracle.com/javafx/2/api/javafx/scene/effect/DropShadow.html
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        jeuOptions.setEffect(dropShadow);

        Label labelAmortissement = new Label("Force d'amortissement");
        labelAmortissement.setFont(font);
        VBox toggleAvecLabel = new VBox(5);
        toggleAvecLabel.setAlignment(Pos.CENTER);
        toggleAvecLabel.getChildren().addAll(labelAmortissement, forceAmortissement);
        forceAmortissement.setStyle("-fx-base: #5D6D7E;");


        //not visible au début
        jeuOptions.setVisible(false);

        //Position du VBox au début
        jeuOptions.setLayoutX((WIDTH - 400) / 2);
        jeuOptions.setLayoutY(100);

        //Background et taille du Vbox
        jeuOptions.setPrefWidth(400);
        jeuOptions.setPrefHeight(400);

        //1er translateTransition Bas haut pour le faire apparaître
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(800));
        translateTransition.setByY(-550);
        translateTransition.setCycleCount(1);
        translateTransition.setNode(jeuOptions);


        //2eme translateTransition Bas haut pour le faire apparaître
        TranslateTransition translateTransition2 = new TranslateTransition();
        translateTransition2.setDuration(Duration.millis(800));
        translateTransition2.setByY(550);
        translateTransition2.setNode(jeuOptions);




        parametres.setOnAction((e) -> {
            if(!estApparu) {
                jeuOptions.setTranslateY(600);
                jeuOptions.setVisible(true);
                translateTransition.play();
                estApparu = true;
            }
            else if(estApparu){
                translateTransition2.play();
                estApparu = false;
            }
            canvas.requestFocus();

        });
        //Claude
        translateTransition2.setOnFinished(e1 -> jeuOptions.setVisible(false));
        forceAmortissement.setFont(font);

        partieGraphique.setEffect(dropShadow);


        Button fermerM = new Button("Fermer");

        fermerM.setFont(font);
        fermerM.setStyle("-fx-background-color: #5D6D7E; -fx-background-radius: 10;");
        fermerM.setOnMouseEntered((e) -> {


            fermerM.setStyle("-fx-background-color: #34495E;");
        });


        fermerM.setOnMouseExited((e) -> {


            fermerM.setStyle("-fx-background-color: #5D6D7E;");
        });

        fermerM.setOnAction((e) -> {
            if(!estApparu) {
                jeuOptions.setTranslateY(600);
                jeuOptions.setVisible(true);
                translateTransition.play();
                estApparu = true;
            }
            else if(estApparu){
                translateTransition2.play();
                estApparu = false;
            }
            canvas.requestFocus();
        });



        //FIN: GAME OPTIONS MENU

        CheckBox vecteurs = new CheckBox("Vecteurs");
        vecteurs.selectedProperty().addListener((obs, oldVal, newVal) -> {
            simulation.personnageFinal.setVecteurLoiHooke(newVal);
            simulation.personnageFinal.setVecteurForceGravit(newVal);
        });
        jeuOptions.getChildren().addAll(toggleAvecLabel, sep, sliderSection, vecteurs, fermerM);
        root.getChildren().add(jeuOptions);



        forceAmortissement.setLayoutX(WIDTH - 230);
        forceAmortissement.setLayoutY(55);
        forceAmortissement.setSelected(false);
        forceAmortissement.setVisible(false); // pas visible si il est sur la page d'introduction

        forceAmortissement.selectedProperty().addListener((obs, old, val) -> {
            this.simulation.utiliserAmortissement = val;

        });



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
                popup1.show(stage, stage.getX() + 50, stage.getY() + 100);
            }
            canvas.requestFocus();
        });


        hide.setOnAction((e) -> {


            if (popup1.isShowing()) {
                popup1.hide();
            }
            canvas.requestFocus();
        });

        parametres.setOnMouseEntered((e) -> {
            parametres.setScaleX(1.2);
        });
        parametres.setOnMouseExited((e) -> {
            parametres.setScaleX(1.0);
        });

        show.setOnMouseEntered((e) -> {
            show.setScaleX(1.2);
        });
        show.setOnMouseExited((e) -> {
            show.setScaleX(1.0);
        });
        pause.setOnMouseEntered((e) -> {
            pause.setScaleX(1.2);
        });
        pause.setOnMouseExited((e) -> {
            pause.setScaleX(1.0);
        });

        planetes.setOnMouseEntered((e) -> {
            planetes.setScaleX(1.2);
        });
        planetes.setOnMouseExited((e) -> {
            planetes.setScaleX(1.0);
        });




        banniere.setPrefWidth(WIDTH);
        banniere.setPrefHeight(50);
        banniere.setLayoutX(0);
        banniere.setLayoutY(0);
        banniere.setAlignment(Pos.CENTER);
        banniere.setPadding(new Insets(8, 20, 8, 20));
        banniere.setStyle("-fx-background-color: rgba(168, 197, 160, 0.7); ");
        banniere.setVisible(!pageIntro);

        nomPlanete.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        nomPlanete.setTextFill(Color.WHITE);
        Region spacer1 = new Region();

        HBox.setHgrow(spacer1, Priority.ALWAYS);
       nomPlanete.setAlignment(Pos.CENTER_LEFT);

        banniere.getChildren().addAll(nomPlanete, spacer1, show, parametres, pause, planetes);
        root.getChildren().add(banniere);

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
            double debutX = simulation.confirmation.getPosition().getX();
            double debutY =  simulation.confirmation.getPosition().getY();
            double finX =  simulation.confirmation.getPosition().getX() + simulation.confirmation.getTaille().getX();
            double finY = simulation.confirmation.getPosition().getY() + simulation.confirmation.getTaille().getY();


            if (pageIntro) {
                if (positionY > 320 && positionY < 400) {
                    if (positionX > 200 && positionX < 230) {
                        simulation.personnagePrecedent();
                    } else if (positionX > 650 && positionX < 680) {
                        simulation.personnageSuivant();
                    }
                }
                if (positionY > debutY && positionY < finY) {
                    if (positionX > debutX && positionX < finX) {
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




