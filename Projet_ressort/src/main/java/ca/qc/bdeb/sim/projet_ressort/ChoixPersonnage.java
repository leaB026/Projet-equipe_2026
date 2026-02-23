package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class ChoixPersonnage extends ObjetsStatique {
    Simulation simulation;


    public ChoixPersonnage(Point2D position, Point2D taille) {
        super(position, taille);
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);


    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);

        Image image = new Image("Personnage1.png");

        contexte.drawImage(image, position.getX(), position.getY(), taille.getX(), taille.getY());


    }

}
