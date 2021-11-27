package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
* Gestionnaire des bateaux des joueurs
*/
public class Bateau {

    // orientation du bateau

    /**
    * orientation verticale
    */
    public static int VERTICAL = 0;

    /**
    * orientation horizontale
    */
    public static int HORIZONTAL = 1;

    /**
    * orientation du bateau
    */
    private int orientation;

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
    * Position du bateau
    * Indique la position du coin haut à gauche du bateau
    */
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int line, int column) {
        this.position = new Position(line, column);
    }

    /**
    * la taille du bateau
    * doit être compris au mieux entre 2 et 6
    */
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Liste des positions occupées par le bateau
     */
    private List<Position> allPositions;

    public List<Position> getAllPositions() {
        return allPositions;
    }

    /**
    * Bateau
    * @param position position du bateau
    * @param orientation orientation du bateau
    * @param size taille du bateau
    */
    public Bateau(Position position, int orientation, int size) {
        this.position = position;
        this.orientation = orientation;
        this.size = size;

        int ln = position.getLine();
        int col = position.getColumn();

        int dl = 0, dc = 0;
        if (orientation == HORIZONTAL) dc = 1;
        else dl = 1;

        for (int i = 0; i < size; i++)
            allPositions.add(new Position(ln + i * dl, col + i * dc));
    }

    /**
     * Bateau
     * @param orientation orientation du bateau
     * @param allPositions liste de positions occupées par le bateau
     */
    public Bateau(int orientation, List<Position> allPositions) {
        this.position = allPositions.get(0);
        this.orientation = orientation;
        this.size = allPositions.size();
        this.allPositions = allPositions;
    }

    /**
     * Génère une liste aléatoire de bateaux
     * @return
     */
    public static List<Bateau> generate() {
        List<Bateau> bateaux = new ArrayList<>();
        Random random = new Random();

        // on sauvegarde les positions afin d'éviter que les bateaux se superposent
        List<Position> allPositionsGenerate = new ArrayList<>();

        // on génère 2 bateaux de taille 5, 1 de 2, 1 de 3 et 1 et de 4
        for (int s = 0; s < 5; s++) {
            boolean added = false;
            addLoop: while (!added) {
                int ln = random.nextInt(10);
                int col = random.nextInt(10);

                int size = (s < 2) ? 5 : s;

                int orientation = VERTICAL;
                if (random.nextInt(2) == 1) orientation = HORIZONTAL;

                if (orientation == VERTICAL && ln + size >= 10) ln -= size + 1;
                if (orientation == HORIZONTAL && col + size >= 10) col -= size + 1;

                int dl = 0, dc = 0;
                if (orientation == HORIZONTAL) dc = 1;
                else dl = 1;

                List<Position> positions = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Position position = new Position(ln + i * dl, col + i * dc);
                    if (allPositionsGenerate.contains(position)) {
                        continue addLoop;
                    }
                    positions.add(position);
                }

                allPositionsGenerate.addAll(positions);

                bateaux.add(new Bateau(orientation, positions));
                added = true;
            }
        }

        return bateaux;
    }
}
