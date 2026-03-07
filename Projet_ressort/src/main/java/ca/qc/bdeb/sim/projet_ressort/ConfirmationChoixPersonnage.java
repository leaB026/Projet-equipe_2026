package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConfirmationChoixPersonnage extends ObjetsStatique{

    public ConfirmationChoixPersonnage(Point2D position, Point2D taille) {
        super(position, taille);
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);

        contexte.setFill(Color.GREEN);
        contexte.fillRect(position.getX(), position.getY(), taille.getX(), taille.getY());

    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation, ChoixPersonnage personnage) {

    }
}
