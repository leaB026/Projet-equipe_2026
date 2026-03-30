package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.awt.event.MouseEvent;
import java.util.AbstractMap;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;

public class PersonnageQuiSaute extends ObjetDuJeu {

    Image nom;
    boolean toucheLeTrampoline;
    boolean estEnTrainDeTirerPersonnage;
    double masse;
    double coefficientAmortisement;

    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom, double masse) {
        super(position, velocite, taille);
        this.nom = nom;
        this.toucheLeTrampoline = true;
        this.masse = masse;
//        this.coefficientAmortisement = ressort.ConstanteCoefficientDAmortissement * (2 * Math.pow(masse * ressort.constanteDeRappel, 0.5));

    }


    @Override
    protected void update(double deltaTemps, Simulation simulation) {
    }

    protected void updateCollisionRessort(double deltaTemps, Simulation simulation, boolean encollision, Ressort ressort, Planet planet) {
        tempsTotal += deltaTemps;

//Calcules des forces et du mouvement physique du personnage et ressort

        double forceTotal;
        double forceHooke;
        double forceAmortisement;

        if (encollision) {
            double bas = position.getY() + taille.getY();
            double compressionActuelle = (ressort.position.getY() + ressort.taille.getY()) + bas;
            ressort.setCompression(compressionActuelle);
            forceHooke = - ressort.constanteDeRappel * compressionActuelle;

            double coefficientAmortisement = ressort.ConstanteCoefficientDAmortissement * (2 * Math.pow(masse * ressort.constanteDeRappel, 0.5));
            forceAmortisement = -coefficientAmortisement * velocite.getY();

        } else {
            forceHooke = 0;
            forceAmortisement = 0;
            ressort.setCompression(0);

        }

        forceTotal = forceHooke + forceAmortisement + (masse * planet.gravite);

//Accélération
        setAcceleration(new Point2D(acceleration.getX(), forceTotal / masse));

//La vitesse et la position
        updatePhysique(deltaTemps);

//Collision et effet sur le ressort
        if(!encollision && getBas() < ressort.getHaut()){
            toucheLeTrampoline = false;
        }else if (encollision){
            toucheLeTrampoline = true;
        }

//Mouvement personnage
        boolean click = Input.isMousePressed(MouseButton.PRIMARY);
        double positionX = Input.getMouseX();
        double positionY = Input.getMouseY();

        boolean selectionner = positionY > position.getY() &&
                positionY < position.getY() + taille.getY() &&
                positionX > position.getX() &&
                positionX < position.getX() + taille.getX();

        if (click && selectionner && toucheLeTrampoline) {
            estEnTrainDeTirerPersonnage = true;
        }

        if (click && estEnTrainDeTirerPersonnage) {
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(positionX - taille.getX() / 2, positionY - taille.getY() / 2);
            return;
        }

        if (!click && estEnTrainDeTirerPersonnage) {
            estEnTrainDeTirerPersonnage = false;
            velocite = new Point2D(velocite.getX(), -500);
            toucheLeTrampoline = false;
        }

        position = new Point2D(position.getX(),
                Math.clamp(position.getY(), -3000, HEIGHT - taille.getY())
        );

        System.out.println("encollision: " + encollision +
                " | compression: " + ressort.compression +
                " | forceHooke: " + forceHooke +
                " | velociteY: " + velocite.getY() +
                " | positionY: " + position.getY());
    }


    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(nom, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
