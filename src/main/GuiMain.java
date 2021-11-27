package main;

import controller.Controller;
import model.Bateau;
import model.Game;
import model.Position;
import model.players.Player;
import model.players.RandomPlayer;
import view.MainFrame;

// jeu en interface graphique
public class GuiMain {

    public static void main(String[] args) {

        /* TODO: Définir un mécanisme graphique permettant à l'utilisateur de choisir :
            - Le nom des joueurs
            - Le type de jeu :
                Ordi vs Ordi
                Humain vs Ordi
             S'il sagit
         */
        Player player1 = new RandomPlayer("Joueur 1", Bateau.generate());
        Player player2 = new RandomPlayer("Joueur 2", Bateau.generate());

        Game game = new Game(player1, player2);
        Controller controller = new Controller(game);
        MainFrame mainFrame = new MainFrame(controller);
        game.addObserver(mainFrame);

        game.notifyObservers();//créer la vue de base

        while(true)
        {
            // 0 == human vs human
            // 1 == human vs bot
            // 2 == bot vs bot
            if(game.isOver() == false)
            {
                if(game.getTypePartie() == 0) //
                {
                    game.getCurrentPlayer().playCoup(game);
                }
                if(game.getTypePartie() == 1) //
                {
                    if(game.getCurrentPlayer() == game.getPlayer1())
                        game.getCurrentPlayer().playCoup(game);
                    else
                        game.getCurrentPlayer().playBot(game);
                }

                if(game.getTypePartie() == 2) //
                {
                    game.getCurrentPlayer().playBot(game);
                }
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
