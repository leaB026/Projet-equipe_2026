package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ChoixPersonnage extends ObjetsStatique {
    Simulation simulation;
    Image image;
    double masse;

    String nom;

    ArrayList<Simulation> personnage = new ArrayList<>();

    public ChoixPersonnage(Point2D position, Point2D taille, Image image, double masse, String nom) {
        super(position, taille);
        this.image = image;
        this.masse = masse;
        this.nom = nom;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);

//        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
//        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
//
//
//        if (gauche) {
//            simulation.personnagePrecedent();
//        }
//        if (droite) {
//            simulation.personnageSuivant();
//        }
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, ChoixPersonnage personnage) {
        super.draw(contexte, simulation);

        contexte.drawImage(personnage.image, position.getX(), position.getY(), taille.getX(), taille.getY());

    }

}
