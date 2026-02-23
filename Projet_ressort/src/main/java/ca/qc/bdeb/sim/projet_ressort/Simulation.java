package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class Simulation {

    ArrayList<Image> image = new ArrayList<>();
    ArrayList<ChoixPersonnage> personnages = new ArrayList<>();
    ChoixPersonnage personnageChoisie = new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 191, HEIGHT * 0.1), new Point2D(382, 459),new Image("hooke1.png") );
    boolean creationPersonnage = false;
//    ChoixPersonnage personnageChoisie;
//    Image imagePersonnage = new Image("hooke1.png");
    int indexPersonnage = 0;
    double chronometre;

    public void update(double deltaTemps) {
        if (!creationPersonnage) {
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 191, HEIGHT * 0.1), new Point2D(382, 459),new Image("hooke1.png") ));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 178, HEIGHT * 0.1), new Point2D(357, 525),new Image("hooke2.png") ));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 185, HEIGHT * 0.1 + 50), new Point2D(370, 396),new Image("hooke3.png") ));
            creationPersonnage = true;
        }
        boolean personnageNextDroite = Input.isKeyPressed(KeyCode.RIGHT);
        chronometre += deltaTemps;
        if (personnageNextDroite) {
            if(chronometre > 0.2) {
                if (indexPersonnage < personnages.size()) {
                    personnageChoisie = personnages.get(indexPersonnage++);
                } else {
                    indexPersonnage = 0;
                    personnageChoisie = personnages.get(indexPersonnage++);
                }
                chronometre = 0;
            }
        }
    }

    public void draw(GraphicsContext context, Simulation simulation) {
        personnageChoisie.draw(context, simulation, personnageChoisie);
    }

}
