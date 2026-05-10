package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Planet extends ObjetsStatique {

    protected double gravite;
    protected Image image;
    public Planet(Point2D position, Point2D taille, double gravite, Image image) {
        super(position, taille);
        this.gravite = gravite * 100;
        this.image = image;
    }
}
