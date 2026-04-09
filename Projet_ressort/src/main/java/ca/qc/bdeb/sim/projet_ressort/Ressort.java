package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ressort extends ObjetsStatique {

    double constanteDeRappel;
    double ConstanteCoefficientDAmortissement;

    double compression = 0;



    public Ressort(Point2D position, Point2D taille, double constanteDeRappel, double constanteCoefficientDAmortissement) {
        super(position, taille);
        this.constanteDeRappel = constanteDeRappel;
        ConstanteCoefficientDAmortissement = constanteCoefficientDAmortissement;
    }

    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);

    }
    public void setCompression(double compression) {
//        this.compression =Math.max(0, Math.min(compression, taille.getY()*0.8));
        this.compression =Math.max(0, Math.min(compression, taille.getY()));

    }
    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);

        double hauteurDeCompression = taille.getY() - compression;
        double ajusterY = position.getY() + compression;
        contexte.drawImage(new Image("ressort.png"), position.getX(), ajusterY, taille.getX(), hauteurDeCompression);
    }

    public boolean estEnCollision(PersonnageQuiSaute personnage) {
        // Rectangle de la boîte
        double x1 = this.position.getX();
        double y1 = this.position.getY();
        double w1 = this.taille.getX();
        double h1 = this.taille.getY();

        // Rectangle du journal
        double x2 = personnage.position.getX();
        double y2 = personnage.position.getY();
        double w2 = personnage.getTaille().getX();
        double h2 = personnage.getTaille().getY();

        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
