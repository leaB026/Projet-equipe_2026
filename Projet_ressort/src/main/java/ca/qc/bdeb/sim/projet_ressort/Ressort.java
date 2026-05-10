package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ressort extends ObjetsStatique {

    protected double constanteDeRappel;
    protected double ConstanteCoefficientDAmortissement;
    protected double compression = 0;

    public void setCompression(double compression) {
        this.compression = Math.max(0, Math.min(compression, taille.getY()));
    }
    public void setConstanteDeRappel(double constanteDeRappel) {
        this.constanteDeRappel = constanteDeRappel;
    }

    public Ressort(Point2D position, Point2D taille, double constanteDeRappel, double constanteCoefficientDAmortissement) {
        super(position, taille);
        this.constanteDeRappel = constanteDeRappel;
        ConstanteCoefficientDAmortissement = constanteCoefficientDAmortissement;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);

    }


    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);

        double hauteurDeCompression = taille.getY() - compression;
        double ajusterY = position.getY() + compression;
        contexte.drawImage(new Image("ressort.png"), position.getX(), ajusterY, taille.getX(), hauteurDeCompression);
    }

    public boolean estEnCollision(PersonnageQuiSaute personnage) {
        double x1 = this.position.getX();
        double y1 = this.position.getY();
        double w1 = this.taille.getX();

        double x2 = personnage.position.getX();
        double w2 = personnage.getTaille().getX();

        // Collision horizontale
        boolean collisionX = x1 < x2 + w2 && x1 + w1 > x2;

        // Le bas du personnage a atteint le haut du ressort
        boolean collisionY = personnage.getBas() >= y1;

        return collisionX && collisionY;
    }

}
