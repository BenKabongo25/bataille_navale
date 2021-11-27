package view;

import controller.Controller;
import model.Game;
import utils.Observable;
import utils.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame implements Observer {

    private Plateau []mPlateaux;
    JButton buttonTour = null;

    public MainFrame(Controller controller) {
        super("Bataille Navale");

        GridBagLayout basLayout = new GridBagLayout();

        JPanel jpanelTop= new JPanel(new BorderLayout());
        JPanel jpanelBot= new JPanel(new BorderLayout());

        jpanelTop.setLayout(new GridBagLayout());
        jpanelBot.setLayout(new GridBagLayout());

        add(jpanelTop, BorderLayout.NORTH);
        add(jpanelBot, BorderLayout.SOUTH);



        mPlateaux = new Plateau[2];

        mPlateaux[0] = new Plateau(controller, 0);
        mPlateaux[1] = new Plateau(controller, 1);

        {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            ButtonGroup buttonGroup = new ButtonGroup();
            JButton buttonRecommencer = new JButton("Recommencer");
            JToggleButton buttonTriche = new JToggleButton("Triche");
            JToggleButton  buttonHvH = new JToggleButton ("Human vs Human");
            JToggleButton  buttonHvB = new JToggleButton ("Human vs Bot");
            JToggleButton  buttonBvB = new JToggleButton ("Bot vs Bot");

            buttonHvH.setSelected(true); // par défaut, le mode Human vs Human est selectioné
            buttonGroup.add(buttonHvH);
            buttonGroup.add(buttonHvB);
            buttonGroup.add(buttonBvB);

            buttonRecommencer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.recommencer();
                }
            });
            buttonTriche.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.tricher();
                }
            });

            buttonHvH.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.changerMode(0);
                }
            });
            buttonHvB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.changerMode(1);
                }
            });
            buttonBvB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.changerMode(2);
                }
            });

            c.gridx = 0;
            jpanelTop.add(buttonRecommencer, c);
            c.gridx = 1;
            jpanelTop.add(buttonTriche, c);

            c.gridx = 2;
            jpanelTop.add(buttonHvH, c);

            c.gridx = 3;
            jpanelTop.add(buttonHvB, c);

            c.gridx = 4;
            jpanelTop.add(buttonBvB, c);


        }

        {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            jpanelBot.add(mPlateaux[0], c);

            c.gridx = 1;
            c.gridy = 0;
            buttonTour = new JButton("Joueur 1");
            buttonTour.setPreferredSize(new Dimension(140, 40));
            jpanelBot.add(buttonTour, c);

            c.gridx = 2;
            c.gridy = 0;
            jpanelBot.add(mPlateaux[1], c);
        }




        setSize(1080, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    // méthode de mise à jour de la vue en fonction du modèle
    @Override
    public void updateObserver(Observable observable) {
        Game game = (Game) observable;

        // System.out.println("update Plateaux");
        if(game.isOver())
        {
            buttonTour.setText( game.getWinner().getName() + " gagne !");
        }
        else
        {
            if(game.getCurrentPlayer() == game.getPlayer1())
                buttonTour.setText( "<html> " + game.getCurrentPlayer().getName() + "<br> Cliquez à droite</html>");
            else
                buttonTour.setText( "<html> " + game.getCurrentPlayer().getName() + "<br> Cliquez à gauche</html>");
        }

        mPlateaux[0].display(game.getPlayer1(), game.getPlayer2(), game);
        mPlateaux[1].display(game.getPlayer2(), game.getPlayer1(), game);

        //rafraichie la vue principale
        SwingUtilities.updateComponentTreeUI(this);

        // autres affichages
    }
}
