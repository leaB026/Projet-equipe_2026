package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;

public class PersonnageQuiSaute extends ObjetDuJeu {

    Image image;
    boolean toucheLeTrampoline;
    boolean estEnTrainDeTirerPersonnage;
    double masse;
    double compressionActuelle;
    boolean toucheLeSol;

    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom, double masse) {
        super(position, velocite, taille);
        this.image = nom;
        this.toucheLeTrampoline = true;
        this.masse = masse;
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
/*
       Au lieu de faire la différence entre le personnage et le ressort,
         on donne un maximum à la compression que le personnage peut effectuer sur le ressort
 */
        if (encollision) {
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

        } else {
            forceHooke = 0;
            forceAmortisement = 0;
            ressort.setCompression(0);
            compressionActuelle = 0;
            forceTotal = masse * planet.gravite;
            setAcceleration(new Point2D(acceleration.getX(), planet.gravite));

        }


//La vitesse et la position
        updatePhysique(deltaTemps);


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

        if (click && selectionner) {
            estEnTrainDeTirerPersonnage = true;
        }

        if (click && estEnTrainDeTirerPersonnage) {
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(positionX - taille.getX() / 2, positionY - taille.getY() / 2);
            return;
        }

        if (!click && estEnTrainDeTirerPersonnage) {
            estEnTrainDeTirerPersonnage = false;
            velocite = new Point2D(velocite.getX(), -20);
            //Donner une petite vélocité pour donner de la vie au personnage
            toucheLeTrampoline = false;
        }

        if (!encollision) {
            position = new Point2D(position.getX(), Math.clamp(position.getY(), -3000, HEIGHT - taille.getY()));
        }
    }


    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(image, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
