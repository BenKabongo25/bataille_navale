package model;

import model.players.Player;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire d'une partie de jeu
 */
public class Game extends Observable {

    private int typePartie = 0;
    // 0 == human vs human
    // 1 == human vs bot
    // 2 == bot vs bot
    public int getTypePartie() {
        return typePartie;
    }

    public void setTypePartie(int t) {
        typePartie = t;
    }


    boolean triche = false;
    public void setTriche(boolean var)
    {
        triche = var;
    }
    public boolean getTriche()
    {
        return triche;
    }


    /**
     * Les joueurs
     */
    private Player player1, player2;

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * Le joueur courant
     */
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Jeu
     * @param player1 joueur 1
     * @param player2 joueur 2
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void recommencer(Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    /**
     * @param player joueur
     * @return listes de positions où le joueur n'a pas encore joué
     */
    public List<Position> getMoves(Player player) {
        if (player == null) return new ArrayList<>();

        List<Position> positions = new ArrayList<>();
        List<Position> positionsPlay = player.getPositionsPlay();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Position position = new Position(i, j);
                if (!positionsPlay.contains(position))
                    positions.add(position);
            }
        }
        return positions;
    }

    /**
     * @return listes des positions où le joueur courant n'a pas encore joué
     */
    public List<Position> getMoves() {
        return getMoves(currentPlayer);
    }

    /**
     * Fait jouer un joueur à la position indiquée
     * @param position position à jouer
     */
    public void play(Position position) {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;

        currentPlayer.addPositionPlay(position); // on rajoute la position aux positions jouées

        // on vérifie si une position du joueur adverse a été touchée
        if (otherPlayer.getPositionsList().contains(position))
            currentPlayer.addPositionWin(position);

        // changement de joueur
        currentPlayer = otherPlayer;

        // on notifie les observateurs des changements
        notifyObservers();
    }

    /**
     * Liste de positions gagnées par le joueur courant
     * @return
     */
    public List<Position> getCurrentPlayerWin() {
        return currentPlayer.getPositionsWin();
    }

    /**
     * Liste de positions jouées par le joueur courant
     * @return
     */
    public List<Position> getCurrentPlayerPlay() {
        return currentPlayer.getPositionsPlay();
    }

    /**
     * @param player joueur
     * @return la liste de bateaux adverses que le joueur a coulés
     */
    public List<Bateau> getAdversesBateaux(Player player) {
        if (player == null) return new ArrayList<>();

        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;

        List<Bateau> bateaux = new ArrayList<>();
        List<Position> positionsWin = player.getPositionsWin();

        bateauLoop: for (Bateau bateau : otherPlayer.getBateaux()) {
            for (Position position : bateau.getAllPositions()) {
                if (!positionsWin.contains(position))
                    continue bateauLoop;
            }
            // on rajoute le bateau si toutes ses positions ont été trouvées
            bateaux.add(bateau);
        }

        return bateaux;
    }

    public boolean positionBateauCoulee(Player player, Position pos) {

        List<Bateau> bateauCoule = getAdversesBateaux(player);

        for(Bateau bateau : bateauCoule)
        {
            if(bateau.getAllPositions().contains(pos))
                return true;
        }

        return false;
    }

    /**
     * @param player joueur
     * @return true si le joueur a gagné la partie, false sinon
     */
    private boolean hasWon(Player player) {
        // un joueur gagne la partie si le nombre de pions qu'il a éliminés est égal
        // au nombre de pions au départ de l'adversaire

        return player.getPositionsWin().size() ==
                ((player == player1) ? player2 : player1).getPositionsList().size();
    }

    /**
     * @return gagnant de la partie si la partie est finie, null sinon
     */
    public Player getWinner() {
        if (hasWon(player1)) return player1;
        if (hasWon(player2)) return player2;
        return null;
    }

    /**
     * @return true si la partie est terminiée, false sinon
     */
    public boolean isOver() {
        return getWinner() != null;
    }
}
