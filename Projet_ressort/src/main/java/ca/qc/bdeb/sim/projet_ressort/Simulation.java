package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class Simulation {
    Simulation simulation;

    ArrayList<Image> image = new ArrayList<>();
    ArrayList<ChoixPersonnage> personnages = new ArrayList<>();
    ChoixPersonnage personnageChoisie = new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 191, HEIGHT * 0.1), new Point2D(382, 459), new Image("hooke1.png"));
    boolean creationPersonnage = false;
    //    ChoixPersonnage personnageChoisie;
//    Image imagePersonnage = new Image("hooke1.png");
    int indexPersonnage = 0;
    double chronometre;
    FlecheChoixPersonnage flecheG = new FlecheChoixPersonnage(new Point2D(200, 320), new Point2D(30, 80));
    FlecheChoixPersonnage flecheR = new FlecheChoixPersonnage(new Point2D(650, 320), new Point2D(30, 80));

    PersonnageQuiSaute personnageFinal = new PersonnageQuiSaute(new Point2D(200, 320), new Point2D(0,0), new Point2D(personnageChoisie.getTaille().getX(), personnageChoisie.getTaille().getY()), personnageChoisie.image);

    ConfirmationChoixPersonnage confirmation = new ConfirmationChoixPersonnage(new Point2D(410,520), new Point2D(80,30));

    public void update(double deltaTemps) {
        if (!creationPersonnage) {
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 191, HEIGHT * 0.1), new Point2D(382, 459), new Image("hooke1.png")));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 178, HEIGHT * 0.05), new Point2D(357, 525), new Image("hooke2.png")));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 185, HEIGHT * 0.1 + 50), new Point2D(370, 396), new Image("hooke3.png")));
            creationPersonnage = true;
        }
        personnageFinal.update(deltaTemps, simulation);


    }

    public void draw(GraphicsContext context, Simulation simulation, boolean pageIntro) {
        if (pageIntro) {
            personnageChoisie.draw(context, simulation, personnageChoisie);
            flecheG.draw(context, simulation);
            flecheR.draw(context, simulation);
            confirmation.draw(context,simulation);
        }else{
            personnageFinal.draw(context, simulation);
        }
    }
    public void personnageSuivant() {
        indexPersonnage++;

        if (indexPersonnage > personnages.size() - 1) {
            indexPersonnage = 0;
        }

        if (!personnages.isEmpty()) {
            personnageChoisie = personnages.get(indexPersonnage);
        }
    }

    public void personnagePrecedent() {
        indexPersonnage--;

        if (indexPersonnage < 0) {
            indexPersonnage = personnages.size() - 1;
        }

        if (!personnages.isEmpty()) {
            personnageChoisie = personnages.get(indexPersonnage);
        }
    }
}
