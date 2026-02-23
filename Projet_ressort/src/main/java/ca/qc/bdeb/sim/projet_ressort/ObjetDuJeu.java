package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ObjetDuJeu {

    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration = new Point2D(0, 1500);
    protected Point2D taille;
    protected double tempsTotal = 0;

    public ObjetDuJeu(Point2D position, Point2D velocite, Point2D taille) {
        this.position = position;
        this.velocite = velocite;
        this.taille = taille;
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getVelocite() {
        return velocite;
    }

    public Point2D getTaille() {
        return taille;
    }

    protected void update(double deltaTemps, Simulation simulation) {
        tempsTotal += deltaTemps;
        updatePhysique(deltaTemps);
    }

    protected void updatePhysique(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }

    protected void draw(GraphicsContext contexte,  Simulation simulation /*,boolean modeDebuge, boolean modeChamp1*/) {
//        Point2D posEcran = this.position.subtract(camera.getPositionCamera());
//
//        if (modeDebuge) {
//            contexte.setStroke(Color.YELLOW);
//            contexte.strokeRect(posEcran.getX(), posEcran.getY(), this.taille.getX(), this.taille.getY());
//            contexte.setLineWidth(3);
//
//        }
    }

    public double getBas() {
        return position.getY() + taille.getY();
    }

    public Point2D getCentre() {
        return position.add(taille.multiply(1 / 2.0));
    }


}


