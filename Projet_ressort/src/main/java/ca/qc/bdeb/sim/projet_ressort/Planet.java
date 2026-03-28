package ca.qc.bdeb.sim.projet_ressort;

import javafx.scene.image.Image;

public class Planet {

    double gravite;
    Image image;

    public Planet(double gravite, Image image) {
        this.gravite = gravite * 100;
        this.image = image;
    }
}
