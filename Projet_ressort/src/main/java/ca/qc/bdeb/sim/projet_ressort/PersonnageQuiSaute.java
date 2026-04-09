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
    double compressionActuelle;
    boolean toucheLeSol;

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
//            double compressionActuelle = getBas() - ressort.getHaut();
//            compressionActuelle = (ressort.position.getY() + ressort.taille.getY()) + getBas();
//            compressionActuelle = getBas() - (ressort.position.getY() + ressort.taille.getY());
            double compressionActuelle = Math.max(0, getBas() - ressort.position.getY());

// Empêcher le personnage de descendre plus bas que le bas du ressort
            double posMaxY = ressort.position.getY() + ressort.taille.getY() - taille.getY();
            position = new Point2D(position.getX(), Math.min(position.getY(), posMaxY));

            ressort.setCompression(compressionActuelle);
            forceHooke = -ressort.constanteDeRappel * compressionActuelle;

            double coefficientAmortisement = ressort.ConstanteCoefficientDAmortissement * (2 * Math.pow(masse * ressort.constanteDeRappel, 0.5));
            forceAmortisement = -coefficientAmortisement * velocite.getY();

            forceTotal = forceHooke + forceAmortisement + (masse * planet.gravite);
            setAcceleration(new Point2D(acceleration.getX(), forceTotal / masse));

//            System.out.println(" | compressionActuelle: " + compressionActuelle); // ajoute ça
        } else {
            forceHooke = 0;
            forceAmortisement = 0;
            ressort.setCompression(0);
            compressionActuelle = 0;
            forceTotal = masse * planet.gravite;
            setAcceleration(new Point2D(acceleration.getX(), planet.gravite));

        }

//        forceTotal = forceHooke + forceAmortisement + (masse * planet.gravite);

//Accélération
//        setAcceleration(new Point2D(acceleration.getX(), forceTotal / masse));

//La vitesse et la position
        updatePhysique(deltaTemps);

        // Arrêter le rebond quand la vitesse est assez petite
//        if (Math.abs(velocite.getY()) < 50 && encollision) {
//            velocite = new Point2D(velocite.getX(), 0);
//            position = new Point2D(position.getX(), ressort.position.getY() - taille.getY());
//            ressort.setCompression(0);
//
//            velocite = new Point2D(velocite.getX(), 0);
//            position = new Point2D(position.getX(), ressort.position.getY() - taille.getY());
//            ressort.setCompression(0);
//            setAcceleration(new Point2D(acceleration.getX(), 0)); // ← ajoute ça
//            return; // ← et ça, pour stopper toute la mise à jour
//
//        }

//Collision et effet sur le ressort
        if (!encollision && getBas() < ressort.getHaut()) {
            toucheLeTrampoline = false;
        } else if (encollision) {
            toucheLeTrampoline = true;
        }

//Mouvement personnage, le bouger avec une souris
        boolean click = Input.isMousePressed(MouseButton.PRIMARY);
        double positionX = Input.getMouseX();
        double positionY = Input.getMouseY();

        boolean selectionner = positionY > position.getY() && positionY < position.getY() + taille.getY() && positionX > position.getX() && positionX < position.getX() + taille.getX();

        toucheLeSol = ((position.getY() + taille.getY()) == HEIGHT);

        if (click && selectionner /*&& (toucheLeTrampoline || toucheLeSol)*/) {
            estEnTrainDeTirerPersonnage = true;
        }

        if (click && estEnTrainDeTirerPersonnage) {
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(positionX - taille.getX() / 2, positionY - taille.getY() / 2);
            return;
        }

        if (!click && estEnTrainDeTirerPersonnage) {
            estEnTrainDeTirerPersonnage = false;
            velocite = new Point2D(velocite.getX(), 0);
            toucheLeTrampoline = false;
        }

//        position = new Point2D(position.getX(), Math.clamp(position.getY(), -3000, HEIGHT - taille.getY()));
        if (!encollision) {
            position = new Point2D(position.getX(), Math.clamp(position.getY(), -3000, HEIGHT - taille.getY()));
        }
//        System.out.println(
//                "encollision: " + encollision +
//                        " | velociteY: " + velocite.getY() +
//                        " | forceHooke: " + forceHooke +
//                        " | forceAmortisement: " + forceAmortisement +
//                        " | compression: " + ressort.compression
//        );
//        System.out.println("encollision: " + encollision + " | getBas: " + getBas() + " | ressort haut: " + ressort.position.getY());
//        System.out.println("loi de Hooke: " + forceHooke);
        System.out.println("taille ressort: " + ressort.taille.getY());
    }


    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(nom, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
