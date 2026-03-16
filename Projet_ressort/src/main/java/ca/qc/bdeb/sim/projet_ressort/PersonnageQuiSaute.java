package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.awt.event.MouseEvent;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;

public class PersonnageQuiSaute extends ObjetDuJeu {

    Image nom;
    boolean toucheLeTrampoline;
    boolean estEnTrainDeTirerPersonnage;

    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom) {
        super(position, velocite, taille);
        this.nom = nom;
        this.toucheLeTrampoline = true;
    }


    @Override
    protected void update(double deltaTemps, Simulation simulation) {

//        boolean selectionner = Input.isKeyPressed(KeyCode.UP) || Input.isKeyPressed(KeyCode.SPACE);

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

        if(click && estEnTrainDeTirerPersonnage){
            velocite = new Point2D(velocite.getX() , 0);
            position = new Point2D(position.getX(), positionY- taille.getY()/2);
            return;
        }

        if(!click && estEnTrainDeTirerPersonnage){
            estEnTrainDeTirerPersonnage = false;
            velocite = new Point2D(velocite.getX(), -500);
            toucheLeTrampoline = false;
        }

        super.update(deltaTemps, simulation);

        position = new Point2D(position.getX(),
                Math.clamp(position.getY(), -3000, HEIGHT - taille.getY())
        );

        if ((HEIGHT <= getBas()) && !toucheLeTrampoline) {
//            position = new Point2D(position.getX(), HEIGHT - taille.getY());
//            velocite = new Point2D(velocite.getX(),0);
            toucheLeTrampoline = true;
        }


    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(nom, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
