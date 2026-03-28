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
    ChoixPersonnage personnageChoisie = new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1), new Point2D(300, 381), new Image("hooke1.png"));
    boolean creationPersonnage = false;
    //    ChoixPersonnage personnageChoisie;
//    Image imagePersonnage = new Image("hooke1.png");
    int indexPersonnage = 0;
    double chronometre;
    FlecheChoixPersonnage flecheG = new FlecheChoixPersonnage(new Point2D(200, 320), new Point2D(30, 80), new Image("flecheGauche.png"));
    FlecheChoixPersonnage flecheR = new FlecheChoixPersonnage(new Point2D(650, 320), new Point2D(30, 80), new Image("flecheDroite.png"));

    PersonnageQuiSaute personnageFinal;
    Ressort ressort;
    Planet planet;
    boolean creerPersonnage = false;
    ConfirmationChoixPersonnage confirmation = new ConfirmationChoixPersonnage(new Point2D(410, 520), new Point2D(80, 30));

    public void update(double deltaTemps, boolean pageIntro) {
        if (!creationPersonnage) {
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1), new Point2D(300, 381), new Image("hooke1.png")));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.05), new Point2D(300, 471), new Image("HOOKE2.png")));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1 + 50), new Point2D(300, 360), new Image("HOOKE3.png")));
            creationPersonnage = true;
        }
        if (!pageIntro) {
            if (!creerPersonnage) {
                personnageFinal = new PersonnageQuiSaute(new Point2D(WIDTH * 0.5 - personnageChoisie.taille.getX() * 0.25, HEIGHT *0.5), new Point2D(0, 0), new Point2D(personnageChoisie.getTaille().getX() * 0.5, personnageChoisie.getTaille().getY() * 0.5), personnageChoisie.image, 20);
                creerPersonnage = true;
                ressort = new Ressort(new Point2D(WIDTH * 0.5 - 201 / 2, HEIGHT - 64), new Point2D(201, 64), 100, 0.2);
                planet = new Planet(9.81, new Image("bgRose.jpeg"));
            }

            personnageFinal.updateCollisionRessort(deltaTemps, simulation, ressort.estEnCollision(personnageFinal), ressort,planet);

        }

    }

    public void draw(GraphicsContext context, Simulation simulation, boolean pageIntro) {
        if (pageIntro) {
            context.drawImage(new Image("bgRose.jpeg"), 0, 0, WIDTH, HEIGHT);
            personnageChoisie.draw(context, simulation, personnageChoisie);
            flecheG.draw(context, simulation);
            flecheR.draw(context, simulation);
            confirmation.draw(context, simulation);
        } else {
            personnageFinal.draw(context, simulation);
            ressort.draw(context, simulation);
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
