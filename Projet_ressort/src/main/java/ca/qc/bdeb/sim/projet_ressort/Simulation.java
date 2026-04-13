package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

import java.util.ArrayList;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class Simulation {
    Simulation simulation;

    ArrayList<Image> image = new ArrayList<>();
    ArrayList<ChoixPersonnage> personnages = new ArrayList<>();
    ChoixPersonnage personnageChoisie = new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1), new Point2D(300, 381), new Image("hooke1.png"), 20, "Capitaine Rebond");
    boolean creationPersonnage = false;
    //    ChoixPersonnage personnageChoisie;
//    Image imagePersonnage = new Image("hooke1.png");
    int indexPersonnage = 0;
    double chronometre;
    FlecheChoixPersonnage flecheG = new FlecheChoixPersonnage(new Point2D(200, 320), new Point2D(30, 80), new Image("flecheGauche.png"));
    FlecheChoixPersonnage flecheR = new FlecheChoixPersonnage(new Point2D(650, 320), new Point2D(30, 80), new Image("flecheDroite.png"));
    GoBack back = new GoBack(new Point2D(10, HEIGHT-50), new Point2D(50, 50), new Image("Fleche.Back.png"));
    PersonnageQuiSaute personnageFinal;
    Ressort ressort;
    Planet planet;
    boolean creerPersonnageFinal = false;
    ConfirmationChoixPersonnage confirmation = new ConfirmationChoixPersonnage(new Point2D(410, 520), new Point2D(80, 30));
    EnergyChart bc = new EnergyChart("Énergie potentielle gravitationnelle", "Énergie potentielle élastique");
    Slider slider = new Slider(0, 150, 5);

    public void update(double deltaTemps, boolean pageIntro) {
        if (!creationPersonnage) {
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1), new Point2D(300, 381), new Image("hooke1.png"), 20, "Capitaine Rebond"));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.05), new Point2D(300, 471), new Image("HOOKE2.png"), 30, "Ella Sticke"));
            personnages.add(new ChoixPersonnage(new Point2D(WIDTH * 0.5 - 150, HEIGHT * 0.1 + 50), new Point2D(300, 360), new Image("HOOKE3.png"),10, "Hale Spire"));
            creationPersonnage = true;
        }
        if (!pageIntro) {
            if (!creerPersonnageFinal) {
                ressort = new Ressort(new Point2D(WIDTH * 0.5 - 201 / 2, HEIGHT - 64), new Point2D(201, 64), 3000, 0.15);
                planet = new Planet(9.81, new Image("terre.jpg"));
                personnageFinal = new PersonnageQuiSaute(new Point2D(WIDTH * 0.25 - personnageChoisie.taille.getX() * 0.25, HEIGHT*0.5), new Point2D(0, 0), new Point2D(personnageChoisie.getTaille().getX() * 0.5, personnageChoisie.getTaille().getY() * 0.5), personnageChoisie.image, personnageChoisie.masse);
                creerPersonnageFinal = true;
            }

            personnageFinal.updateCollisionRessort(deltaTemps, simulation, ressort.estEnCollision(personnageFinal), ressort,planet);
            bc.update(deltaTemps, personnageFinal, slider, planet);


        }

    }

    public void draw(GraphicsContext context, Simulation simulation, boolean pageIntro) {
        if (pageIntro) {
            context.drawImage(new Image("sc.jpg"), 0, 0, WIDTH, HEIGHT);
            personnageChoisie.draw(context, simulation, personnageChoisie);
            flecheG.draw(context, simulation);
            flecheR.draw(context, simulation);
            confirmation.draw(context, simulation);
        } else {
            if (planet != null) {
                context.drawImage(planet.image, 0, 0, WIDTH, HEIGHT);
            }
            personnageFinal.draw(context, simulation);
            ressort.draw(context, simulation);
            back.draw(context, simulation);
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
    public void changerPlanete(String nomPlanete) {
        if (planet == null) return;
        switch (nomPlanete) {
            case "Terre" -> planet = new Planet(9.81, new Image("terre.jpg"));
            case "Lune"  -> planet = new Planet(1.62, new Image("bgLune.jpeg"));
            case "Mars"  -> planet = new Planet(3.72, new Image("bgMars.jpeg"));
        } }

    public EnergyChart getBc() {
        return bc;
    }
}
