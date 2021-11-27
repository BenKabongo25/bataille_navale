package model.players;

import model.Bateau;
import model.Game;
import model.Position;

import java.util.List;
import java.util.ArrayList;

/**
* classe du joueur
*/
public abstract class Player {

    /**
    * Nom du joueur
    */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    * liste des bateaux du joueur
    */
    private List<Bateau> bateaux;

    public List<Bateau> getBateaux() {
        return bateaux;
    }

    public void setBateaux(List<Bateau> bateaux) {
        this.bateaux = bateaux;
    }

    /**
     * Liste de positions jouées
     */
    private final List<Position> positionsPlay;

    public void addPositionPlay(Position position) {
        if (!positionsPlay.contains(position))
            positionsPlay.add(position);
    }

    public List<Position> getPositionsPlay() {
        return positionsPlay;
    }

    /**
     * Liste de positions du joueur adverse touchées
     */
    private final List<Position> positionsWin;

    public void addPositionWin(Position position) {
        if (!positionsWin.contains(position))
            positionsWin.add(position);
    }

    public List<Position> getPositionsWin() {
        return positionsWin;
    }

    /**
     * Liste de positions occupées par le joueur
     */
    private final List<Position> positionsList;

    public List<Position> getPositionsList() {
        return positionsList;
    }

    /**
    * Joueur
    * @param name nom du joueur
    * @param bateaux liste des bateaux
    */
    public Player(String name, List<Bateau> bateaux) {
        this.name = name;
        this.bateaux = bateaux;
        this.positionsPlay = new ArrayList<>();
        this.positionsWin = new ArrayList<>();
        this.positionsList = new ArrayList<>();

        for (Bateau bateau : bateaux)
            for (Position position : bateau.getAllPositions()) {
                if (!positionsList.contains(position))
                    positionsList.add(position);
            }
    }

    /**
     * choix du joueur
     * @param game jeu courant
     * @return position à jouer
     */
    protected abstract Position getChoice(Game game);

    public void playBot(Game game) {
        Position position = getChoice(game);
        if (position != null)
            game.play(position);
    }


    private Position mCoupChoisie = null;

    public Position getCoupChoisie()
    {
        return mCoupChoisie;
    }

    public void setCoupChoisie(Position position)
    {
        mCoupChoisie = position;
    }

    public void playCoup(Game game) {

        if (mCoupChoisie != null)
        {
            if(game.getMoves().contains(mCoupChoisie))
            {
                game.play(mCoupChoisie);
                mCoupChoisie = null;
            }
        }

    }
}
