package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class Simulation {

    ArrayList<Image> personnages = new ArrayList<>();
    boolean creationPersonnage = false;
    Image imagePersonnage = new Image("Personnage1.png");
    int indexPersonnage = 0;
    double chronometre;

    ChoixPersonnage personnage = new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 100, HEIGHT * 0.25), new Point2D(170, 325));

    public void update(double deltaTemps) {
        if (!creationPersonnage) {
            personnages.add(new Image("Personnage1.png"));
            personnages.add(new Image("Personnage2.png"));
            personnages.add(new Image("Personnage3.png"));
            personnages.add(new Image("Personnage4.png"));
            creationPersonnage = true;
        }
        boolean personnageNextDroite = Input.isKeyPressed(KeyCode.RIGHT);
        chronometre += deltaTemps;
        if (personnageNextDroite) {
            if(chronometre > 0.25) {
                if (indexPersonnage < personnages.size()) {
                    imagePersonnage = personnages.get(indexPersonnage++);
                } else {
                    indexPersonnage = 0;
                    imagePersonnage = personnages.get(indexPersonnage++);
                }
                chronometre = 0;
            }
        }
    }

    public void draw(GraphicsContext context, Simulation simulation) {
        personnage.draw(context, simulation, imagePersonnage);
    }

}
