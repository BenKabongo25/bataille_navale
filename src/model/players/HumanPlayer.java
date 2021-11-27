package model.players;

import model.Bateau;
import model.Game;
import model.Position;

import java.util.List;
import java.util.Scanner;

/**
 * Joueur humain
 */
public abstract class HumanPlayer extends Player {

    public HumanPlayer(String name, List<Bateau> bateaux) {
        super(name, bateaux);
    }

    /**
     * Retourne un joueur humain adapté pour le jeu en console
     * @param name nom du joueur
     * @param bateaux bateaux du joueur
     * @return
     */
    public static HumanPlayer getHumanConsolePlayer(String name, List<Bateau> bateaux) {
        return new HumanPlayer(name, bateaux) {
            // on définit la méthode console pour le joueur Humain
            @Override
            protected Position getChoice(Game game) {
                Scanner scanner = new Scanner(System.in);
                List<Position> positions = game.getMoves(this);
                int line, column;
                Position position;
                do {
                    System.out.println("Veuillez choisir la position où jouer.");
                    String choice;

                    // ligne
                    do {
                        System.out.print("Numéro de ligne (0-9) : ");
                        choice = scanner.next();
                        try {
                            line = Integer.parseInt(choice);
                        } catch (Exception e) {
                            System.out.println("Veuillez saisir un nombre");
                            line = -1;
                        }
                    } while (line < 0 || line > 9);

                    // colonne
                    do {
                        System.out.print("Numéro de colonne (0-9) : ");
                        choice = scanner.next();
                        try {
                            column = Integer.parseInt(choice);
                        } catch (Exception e) {
                            System.out.println("Veuillez saisir un nombre");
                            column = -1;
                        }
                    } while (column < 0 || column > 9);

                    position = new Position(line, column);
                } while (!positions.contains(position));
                return position;
            }
        };
    }

}
