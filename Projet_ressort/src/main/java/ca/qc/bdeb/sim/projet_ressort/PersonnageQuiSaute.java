package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PersonnageQuiSaute extends ObjetDuJeu{

    Image nom;
    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom) {
        super(position, velocite, taille);
        this.nom = nom;
    }


    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.fillRect( 300, 400, 20, 30);
        contexte.drawImage(nom, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
