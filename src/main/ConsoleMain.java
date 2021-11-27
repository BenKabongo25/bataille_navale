package main;

import model.Game;
import model.players.HumanPlayer;
import model.players.Player;
import model.Bateau;
import model.Position;
import model.players.RandomPlayer;

import java.util.List;
import java.util.Scanner;

// jeu en console
public class ConsoleMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34mBATAILLE NAVALLE\u001B[0m");

        // noms des joueurs
        System.out.print("\nVeuillez choisir le nom du premier joueur : ");
        String name1 = scanner.nextLine();
        System.out.print("Veuillez choisir le nom du second joueur : ");
        String name2 = scanner.nextLine();

        // type de jeu
        System.out.println("\nModes de jeu :\n1 > Machine VS Machine\n2 > Humain vs Machine");
        System.out.print("Tapez 2 pour un jeu Huamin VS Machine : ");
        String choice = scanner.nextLine();

        Player player1, player2;
        if (choice.length() > 0 && choice.equals("2")) // Humain
            player1 = HumanPlayer.getHumanConsolePlayer(name1, Bateau.generate());
        else // Machine
            player1 = new RandomPlayer(name1, Bateau.generate());

        player2 = new RandomPlayer(name2, Bateau.generate());

        // initialisation du jeu
        Game game = new Game(player1, player2);
        Player currentPlayer;

        System.out.println("\n");
        do {
            currentPlayer = game.getCurrentPlayer();
            currentPlayer.playBot(game);
            System.out.println("Affichage : \u001B[33m" + currentPlayer.getName() + "\u001B[0m\n" + show(currentPlayer));
            System.out.println("Appuyez sur ENTER pour continuer ...");
            scanner.nextLine();
        } while (!game.isOver());

        System.out.println("Le gagnant est " + game.getWinner().getName());
        System.out.println("GOOD BYE !");
    }

    // affiche la flotte d'un joueur
    public static String show(Player player) {
        StringBuilder buffer = new StringBuilder();

        List<Position> positionsPlay = player.getPositionsPlay();
        List<Position> positionsWin = player.getPositionsWin();

        buffer.append("\t| 0\t| 1\t| 2\t| 3\t| 4\t| 5\t| 6\t| 7\t| 8\t| 9\t|\n");
        for (int ln = 0; ln < 10; ln++) {
            for (int col = -1; col < 10; col++) {
                if (col == -1) buffer.append(ln).append("\t|");
                else {
                    Position position = new Position(ln, col);
                    if (positionsWin.contains(position))
                        buffer.append(" \u001B[34mX\u001B[0m\t|");
                    else if (positionsPlay.contains(position))
                        buffer.append(" \u001B[31m!\u001B[0m\t|");
                    else
                        buffer.append("  \t|");
                }
            }
            buffer.append("\n");
        }

        return buffer.toString();
    }

}
