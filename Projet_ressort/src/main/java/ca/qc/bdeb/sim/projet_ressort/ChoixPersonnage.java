package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ChoixPersonnage extends ObjetsStatique {
    Simulation simulation;

    ArrayList<Simulation> personnage = new ArrayList<>();

    public ChoixPersonnage(Point2D position, Point2D taille) {
        super(position, taille);
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);


    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, Image image) {
        super.draw(contexte, simulation);

        contexte.drawImage(image, position.getX(), position.getY(), taille.getX(), taille.getY());



    }

}
