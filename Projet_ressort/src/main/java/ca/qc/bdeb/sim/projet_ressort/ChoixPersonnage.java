package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ChoixPersonnage extends ObjetsStatique {
   protected Image image;
   protected double masse;
   protected String nom;

    public ChoixPersonnage(Point2D position, Point2D taille, Image image, double masse, String nom) {
        super(position, taille);
        this.image = image;
        this.masse = masse;
        this.nom = nom;
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
