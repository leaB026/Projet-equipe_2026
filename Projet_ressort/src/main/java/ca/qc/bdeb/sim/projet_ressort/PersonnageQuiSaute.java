package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;

public class PersonnageQuiSaute extends ObjetDuJeu {
    Image image;
    boolean toucheLeTrampoline;
    boolean estEnTrainDeTirerPersonnage;
    double masse;
    double compressionActuelle;
    boolean toucheLeSol;
    boolean utiliserAmortissement = true;
    double forceTotal;
    double forceHooke;
    double forceAmortisement;
    double forceGravitationnelle;
    boolean pause;
    ArrayList<Point2D> valeurEnregister = new ArrayList<>();
    Point2D ancienneAcceleration;
    Point2D ancienneVelocite;
    boolean vecteurLoiHooke;
    boolean vecteurForceGravit;

    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom, double masse) {
        super(position, velocite, taille);
        this.image = nom;
        this.toucheLeTrampoline = true;
        this.masse = masse;
    }
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    public boolean isPause() {
        return pause;
    }
    public boolean isVecteurLoiHooke() {
        return vecteurLoiHooke;
    }
    public void setVecteurLoiHooke(boolean vecteurLoiHooke) {
        this.vecteurLoiHooke = vecteurLoiHooke;
    }
    public boolean isVecteurForceGravit() {
        return vecteurForceGravit;
    }
    public void setVecteurForceGravit(boolean vecteurForceGravit) {
        this.vecteurForceGravit = vecteurForceGravit;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
    }

    protected void updateCollisionRessort(double deltaTemps, Simulation simulation, boolean encollision, Ressort ressort, Planet planet) {
        valeurEnregister.add(getAcceleration());
        valeurEnregister.add(getVelocite());
        if (!pause) {
            tempsTotal += deltaTemps;
            setAcceleration(valeurEnregister.getFirst());
            setVelocite(valeurEnregister.getLast());
            valeurEnregister.clear();
/*
Calcules des forces et du mouvement physique du personnage et du ressort
         Au lieu de faire la différence entre le personnage et le ressort,
         on donne un maximum à la compression que le personnage peut effectuer sur le ressort
 */
            if (encollision) {
                compressionActuelle = Math.max(0, getBas() - ressort.position.getY());

// Empêcher le personnage de descendre plus bas que le bas du ressort
                double posMaxY = ressort.position.getY() + ressort.taille.getY() - taille.getY();
                if (position.getY() > posMaxY) {
                    position = new Point2D(position.getX(), posMaxY);
                    //Pour éviter des buildup de vélocité quand il dépasse la fenêtre, on le reste à 0.
                    if (velocite.getY() > 0) {
                        velocite = new Point2D(velocite.getX(), 0);
                    }
                }
                ressort.setCompression(compressionActuelle);
                forceHooke = -ressort.constanteDeRappel * compressionActuelle;
                if (utiliserAmortissement) {
                    double coefficientAmortisement = ressort.ConstanteCoefficientDAmortissement * (2 * Math.pow(masse * ressort.constanteDeRappel, 0.5));
                    forceAmortisement = -coefficientAmortisement * velocite.getY();
                } else {
                    forceAmortisement = 0;
                }
                forceGravitationnelle = masse * planet.gravite;
                forceTotal = forceHooke + forceAmortisement + forceGravitationnelle;
            } else {
                forceHooke = 0;
                forceAmortisement = 0;
                ressort.setCompression(0);
                compressionActuelle = 0;
                forceGravitationnelle = masse * planet.gravite;
                forceTotal = forceGravitationnelle;
            }
//Adapter l'accélération
            setAcceleration(new Point2D(acceleration.getX(), forceTotal / masse));

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
                velocite = new Point2D(velocite.getX(), -100);
                //Donner une petite vélocité pour donner de la vie au personnage
                toucheLeTrampoline = false;
            }

            if (!encollision) {
                position = new Point2D(position.getX(), Math.clamp(position.getY(), -3000, HEIGHT - taille.getY()));
            }
        }
        if (pause) {
            valeurEnregister.add(getAcceleration());
            valeurEnregister.add(getVelocite());
            setAcceleration(new Point2D(0, 0));
            setVelocite(new Point2D(0, 0));
        }
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        // On multiplie par une échelle pour que le vecteur soit visible
        double scaleLoiHooke = 0.001;
        double scaleForceGravitationnelle = 0.005;
        double centrePersonnageX = position.getX() + (taille.getX() / 2);
        double centrePersonnageTopY = position.getY() + (taille.getY() / 4);
        double centrePersonnageBottomY = position.getY() + (taille.getY());
        contexte.setLineWidth(5);

        if (vecteurLoiHooke) {
            //Dessiner le vecteur de la loi Hooke
            contexte.setStroke(Color.RED);
            contexte.strokeLine(centrePersonnageX, centrePersonnageTopY, centrePersonnageX, centrePersonnageTopY + forceHooke * scaleLoiHooke);
            contexte.strokeLine(centrePersonnageX, centrePersonnageTopY + forceHooke * scaleLoiHooke, centrePersonnageX - 5, centrePersonnageTopY + forceHooke * scaleLoiHooke + 5);
            contexte.strokeLine(centrePersonnageX, centrePersonnageTopY + forceHooke * scaleLoiHooke, centrePersonnageX + 5, centrePersonnageTopY + forceHooke * scaleLoiHooke + 5);
        }
        if(vecteurForceGravit){
            //Dessiner le vecteur de la force graviationelle
            contexte.setStroke(Color.GREEN);
            contexte.strokeLine(centrePersonnageX, centrePersonnageBottomY, centrePersonnageX, centrePersonnageBottomY + forceGravitationnelle * scaleForceGravitationnelle);
            contexte.strokeLine(centrePersonnageX, centrePersonnageBottomY + forceGravitationnelle * scaleForceGravitationnelle, centrePersonnageX - 5, centrePersonnageBottomY + forceGravitationnelle * scaleForceGravitationnelle - 5);
            contexte.strokeLine(centrePersonnageX, centrePersonnageBottomY + forceGravitationnelle * scaleForceGravitationnelle, centrePersonnageX + 5, centrePersonnageBottomY + forceGravitationnelle * scaleForceGravitationnelle - 5);
            }

        super.draw(contexte, simulation);
        contexte.drawImage(image, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
