package ca.qc.bdeb.sim.projet_ressort;


import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.HashSet;
import java.util.Set;

public class Input {

    //Ensemble des touches sur lesquelles on appuie en ce moment
    private static final Set<KeyCode> touches = new HashSet<>();
    private static final Set<MouseButton> click = new HashSet<>();

    //personnage.update() va demander si certaines touches sont appuyées ou non
    public static boolean isKeyPressed(KeyCode code) {
        return touches.contains(code);
    }

    //Dans le Main, on va écouter les événements sur la scène et modifier l'état quand les touches changent
    public static void setKeyPressed(KeyCode code, boolean appuie) {
        if (appuie) touches.add(code);

        else touches.remove(code);
    }

    public static boolean isMousePressed(MouseButton mouse) {
        return click.contains(mouse);
    }

    public static void setMousePressed(MouseButton mouse, boolean appuie ){
        if (appuie) click.add(mouse);

        else  click.remove(mouse);
    }
}
