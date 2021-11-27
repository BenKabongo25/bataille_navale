package controller;

import model.Bateau;
import model.Game;
import model.Position;
import model.players.Player;
import model.players.RandomPlayer;

public class Controller {
    Game model;

    public Controller(Game model)
    {
        this.model = model;
    }

    public void changerMode(int typePartie)
    {
        model.setTypePartie(typePartie);
    }

    public void joueurCoup(Position position, int idPlateau)
    {
        if(idPlateau == 1 && model.getCurrentPlayer() == model.getPlayer1())// on vérifie que la grille associé correspond bien au joueur actuel
            model.getCurrentPlayer().setCoupChoisie(position);

        if(idPlateau == 0 && model.getCurrentPlayer() == model.getPlayer2())
            model.getCurrentPlayer().setCoupChoisie(position);
    }

    public void recommencer()
    {
        Player player1 = new RandomPlayer(model.getPlayer1().getName(), Bateau.generate());
        Player player2 = new RandomPlayer(model.getPlayer2().getName(), Bateau.generate());
        model.recommencer(player1, player2);
        model.notifyObservers();
    }
    public void tricher()
    {
        model.setTriche( !model.getTriche() );
        model.notifyObservers();
    }
}
