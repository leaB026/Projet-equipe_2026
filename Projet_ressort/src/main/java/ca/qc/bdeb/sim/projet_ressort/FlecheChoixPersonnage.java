package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FlecheChoixPersonnage extends ObjetsStatique{

    Image nom;
    public FlecheChoixPersonnage(Point2D position, Point2D taille, Image nom) {
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
        contexte.setFill(Color.RED);
        contexte.drawImage(nom,position.getX(), position.getY(), taille.getX(), taille.getY());

//        contexte.fillRect(position.getX(), position.getY(), taille.getX(), taille.getY());
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, ChoixPersonnage personnage) {
    }
}
