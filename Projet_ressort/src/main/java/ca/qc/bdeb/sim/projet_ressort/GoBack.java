package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GoBack extends ObjetsStatique {

    Image nom;

    public GoBack(Point2D position, Point2D taille, Image nom) {
        super(position, taille);
        this.nom = nom;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(nom,position.getX(), position.getY(), taille.getX(), taille.getY());
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, ChoixPersonnage personnage) {
    }
}
