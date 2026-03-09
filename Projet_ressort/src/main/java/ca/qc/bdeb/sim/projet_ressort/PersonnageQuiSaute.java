package ca.qc.bdeb.sim.projet_ressort;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import static ca.qc.bdeb.sim.projet_ressort.ProjetIntegration.HEIGHT;

public class PersonnageQuiSaute extends ObjetDuJeu{

    Image nom;
    boolean toucheLeTrampoline;
    public PersonnageQuiSaute(Point2D position, Point2D velocite, Point2D taille, Image nom) {
        super(position, velocite, taille);
        this.nom = nom;
        this. toucheLeTrampoline = true;
    }


    @Override
    protected void update(double deltaTemps, Simulation simulation) {
        super.update(deltaTemps, simulation);

        boolean haut = Input.isKeyPressed(KeyCode.UP) || Input.isKeyPressed(KeyCode.SPACE);

        if(haut && toucheLeTrampoline){
            velocite = new Point2D(velocite.getX(), -500);
            toucheLeTrampoline = false;
        }
        position = new Point2D(position.getX(),
                Math.clamp(position.getY(),-3000, HEIGHT - taille.getY())
        );

        if((HEIGHT <= getBas()) && !toucheLeTrampoline){
//            position = new Point2D(position.getX(), HEIGHT - taille.getY());
//            velocite = new Point2D(velocite.getX(),0);
            toucheLeTrampoline = true;
        }






    }

    @Override
    protected void draw(GraphicsContext contexte, Simulation simulation) {
        super.draw(contexte, simulation);
        contexte.drawImage(nom, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
