package model.players;

import model.Bateau;
import model.Game;
import model.Position;

import java.util.List;
import java.util.Random;

/**
 * Joueur aléatoire
 */
public class RandomPlayer extends Player {

    private final Random random;

    public RandomPlayer(String name, List<Bateau> bateaux) {
        super(name, bateaux);
        random = new Random();
    }

    @Override
    protected Position getChoice(Game game) {
        List<Position> moves = game.getMoves(this);
        if(moves.size() == 0)
            return null;

        return moves.get(random.nextInt(moves.size()));
    }
}
