package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class ObjetsStatique extends ObjetDuJeu{

    public ObjetsStatique(Point2D position, Point2D taille) {
        super(position, new Point2D(0,0), taille);
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);
    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation/*, boolean modeDebuge, boolean modeChamp1*/) {
        super.draw(contexte, simulation/*, modeDebuge, modeChamp1*/);
    }
}
