package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;
import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.WIDTH;

public class Simulation {

    ChoixPersonnage personnage = new ChoixPersonnage( new Point2D(WIDTH * 0.5 - 100, HEIGHT*0.75), new Point2D(200,50));
    public void update(double deltaTemps) {

    }

    public void draw(GraphicsContext context, Simulation simulation) {
personnage.draw(context, simulation);
    }
}
