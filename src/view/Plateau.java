package view;

import controller.Controller;
import model.Game;
import model.players.Player;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Plateau extends JPanel {

    JButton[] mCase = null;
    int idPlateau = 0;
    Controller controller = null;

    class CircleIcon implements Icon {

        Color couleurCercle;
        CircleIcon(Color couleurCercle)
        {
            super();

            this.couleurCercle = couleurCercle;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.drawOval(x, y, getIconWidth() - 1, getIconHeight() - 1);
            g2.setColor(couleurCercle);
            g2.fillOval(x, y, getIconWidth() -1, getIconHeight() -1);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }
    }

    public Plateau(Controller controller, int idPlateau) {

        this.controller = controller;
        this.idPlateau = idPlateau;
    }

    /**
     * Affiche la grille d'un joueur
     *
     */

    private int getIndiceCase(int x, int y)
    {
        return (y+1) * 10 + (x+1);
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
    }

    public void display(Player playerCurrent, Player playerAdverse, Game model) {

        //todo : ne pas détruire tout les élements à chaque update
        //Les créer une seule fois dans le constructeur, puis les modifie dans display()

        removeAll();
        //revalidate();
        //repaint();


        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.NONE;

        mCase = new JButton[11 * 11];

        for (int i = 1; i < 11; i++) {
            int indiceCase = (0) * 10 + i;

            mCase[indiceCase] = new JButton(getCharForNumber(i));
            mCase[indiceCase].setMargin(new Insets(0, 0, 0, 0));

            gridBagConstraints.gridx = i;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.WEST;

            mCase[indiceCase].setPreferredSize(new Dimension(40, 40));
            add(mCase[indiceCase], gridBagConstraints);
        }
        for (int i = 1; i < 11; i++) {
            int indiceCase = (i) * 10 + 0;

            mCase[indiceCase] = new JButton(String.valueOf(i));
            mCase[indiceCase].setMargin(new Insets(0, 0, 0, 0));

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.anchor = GridBagConstraints.WEST;

            mCase[indiceCase].setPreferredSize(new Dimension(40, 40));
            add(mCase[indiceCase], gridBagConstraints);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                int indiceCase = getIndiceCase(i, j);
                Position pos = new Position(j, i);

                gridBagConstraints.gridx = i+1;
                gridBagConstraints.gridy = j+1;
                gridBagConstraints.anchor = GridBagConstraints.WEST;

                if(playerCurrent != null && playerAdverse != null)
                {
                    boolean hasBoat = false;

                    if(playerCurrent.getPositionsList().contains(pos))
                        hasBoat = true;

                    int hasShoot = 0; // 0 non, 1 dans l'eau, 2 touché

                    if(playerAdverse.getPositionsWin().contains(pos) )
                        hasShoot = 2;
                    else if (playerAdverse.getPositionsPlay().contains(pos) )
                        hasShoot = 1;

                    if(hasShoot != 0)
                    {
                        Color couleurCercle = (hasShoot == 1)? Color.GREEN : Color.RED;
                        mCase[indiceCase] = new JButton(new CircleIcon(couleurCercle));
                    }
                    else
                    {
                        mCase[indiceCase] = new JButton();
                    }


                    if(hasBoat && (model.getCurrentPlayer() == playerCurrent || model.positionBateauCoulee(model.getCurrentPlayer(), pos) ) )
                        mCase[indiceCase].setBackground(Color.BLACK);
                    else if( !(hasBoat && model.getTriche()) || model.getTriche() == false )
                    {
                        mCase[indiceCase].setOpaque(false);
                        mCase[indiceCase].setContentAreaFilled(false);
                        mCase[indiceCase].setBorderPainted(false);
                    }


                }
                else
                    mCase[indiceCase] = new JButton();

                mCase[indiceCase].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        controller.joueurCoup(pos, idPlateau);
                    }
                });

                mCase[indiceCase].setPreferredSize(new Dimension(40, 40));
                add(mCase[indiceCase], gridBagConstraints);
            }
        }

    }


}
