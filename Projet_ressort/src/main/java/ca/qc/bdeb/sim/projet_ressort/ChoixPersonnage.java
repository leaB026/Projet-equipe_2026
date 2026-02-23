package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ChoixPersonnage extends ObjetsStatique {
    Simulation simulation;
    Image image;

    ArrayList<Simulation> personnage = new ArrayList<>();

    public ChoixPersonnage(Point2D position, Point2D taille,  Image image) {
        super(position, taille);
        this.image = image;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);


    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, ChoixPersonnage personnage) {
        super.draw(contexte, simulation);

        contexte.drawImage(personnage.image, position.getX(), position.getY(), taille.getX(), taille.getY());



    }

}
