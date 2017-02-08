/*
 * Bailey Thompson
 * Adventure (1.4.0)
 * 7 February 2017
 * Dependencies: jaco mp3 player
 * Info: RPG Game! Made during a hackathon.
 */

import jaco.mp3.player.MP3Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Adventure {

    private static final ImageIcon ICON_ADVENTURE = new ImageIcon("Adventure.png");
    private static final String SOUND_FILE = "Adventure.mp3";
    private final JFrame frame = new JFrame("Adventure");
    private final JLabel text1 = new JLabel("Welcome To Adventure! This Game Was Created By Bailey Thompson");
    private final JLabel text2 = new JLabel("To Start The Game, Please Pick A Direction To Move In!");
    private final JLabel infoA1 = new JLabel("  Health: 20");
    private final JLabel infoA2 = new JLabel("  Level: 1");
    private final JButton north = new JButton("North");
    private final JLabel infoB1 = new JLabel("  Weapon: Fists");
    private final JLabel infoB2 = new JLabel("  Item: Nothing");
    private final JButton west = new JButton("West"), south = new JButton("South"), east = new JButton("East");
    private final JButton btnWeapon = new JButton("Weapon"), btnItem = new JButton("Item");
    private final JButton run = new JButton("Run/Leave");
    private final JPanel textPanel = new JPanel(), infoAPanel = new JPanel(), infoBPanel = new JPanel();
    private final JPanel panelMid = new JPanel(), panelBot = new JPanel(), panelNeg = new JPanel();
    private int xp, level = 1, currentHP = 20, weapon, item, randomNum, encounter;
    private boolean newRound = true;

    public static void main(String[] args) {
        Adventure Adventure = new Adventure();
        Adventure.prepareGUI();
    }

    private void prepareGUI() {
        final File SOUND_HANDLE = new File(SOUND_FILE);
        if (SOUND_HANDLE.exists()) {
            MP3Player player = new MP3Player();
            player.addToPlayList(SOUND_HANDLE);
            player.setRepeat(true);
            player.play();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(ICON_ADVENTURE.getImage());
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.gray);

        textPanel.add(text1);
        textPanel.add(text2);
        textPanel.setLayout(new GridLayout(2, 1));

        infoAPanel.add(infoA1);
        infoAPanel.add(infoA2);
        infoAPanel.setLayout(new GridLayout(2, 1));

        infoBPanel.add(infoB1);
        infoBPanel.add(infoB2);
        infoBPanel.setLayout(new GridLayout(2, 1));

        panelMid.add(infoAPanel);
        panelMid.add(north);
        panelMid.add(infoBPanel);
        panelMid.setLayout(new GridLayout(1, 3));

        panelBot.add(west);
        panelBot.add(south);
        panelBot.add(east);
        panelBot.setLayout(new GridLayout(1, 3));

        panelNeg.add(btnWeapon);
        panelNeg.add(run);
        panelNeg.add(btnItem);
        panelNeg.setLayout(new GridLayout(1, 3));

        frame.add(textPanel);
        frame.add(panelMid);
        frame.add(panelBot);
        frame.add(panelNeg);
        frame.setLayout(new GridLayout(4, 1, 0, 1));

        btnWeapon.setEnabled(false);
        run.setEnabled(false);
        btnItem.setEnabled(false);

        buttonPress();
    }

    private void buttonPress() {
        north.addActionListener((ActionEvent e) -> {
            if (newRound) {
                randomGenerate();
                updateGUI();
            }
        });

        south.addActionListener((ActionEvent e) -> {
            if (newRound) {
                randomGenerate();
                updateGUI();
            }
        });

        west.addActionListener((ActionEvent e) -> {
            if (newRound) {
                randomGenerate();
                updateGUI();
            }
        });

        east.addActionListener((ActionEvent e) -> {
            if (newRound) {
                randomGenerate();
                updateGUI();
            }
        });

        btnWeapon.addActionListener((ActionEvent e) -> {
            if (!newRound) {
                randomNum = generateNumber(100);
                if (randomNum > (100 - (2 * weapon + 30))) {
                    //if user wins random test
                    newRound = true;
                    btnWeapon.setEnabled(false);
                    run.setEnabled(false);
                    btnItem.setEnabled(false);
                    north.setEnabled(true);
                    south.setEnabled(true);
                    west.setEnabled(true);
                    east.setEnabled(true);
                    xp++;
                    if (encounter == 5 || encounter == 6) {
                        text2.setText("You cross with your weapon! What direction do you choose now?");
                        weapon = 0;
                    } else {
                        text2.setText("You killed it! What direction do you choose now?");
                    }
                } else {
                    //if user loses random test
                    text2.setText("You failed, due to exhaustion you lost 2HP. Choose another action.");
                    currentHP -= 2;
                }
                updateGUI();
            }
        });

        run.addActionListener((ActionEvent e) -> {
            if (!newRound) {
                randomNum = generateNumber(100);
                if (randomNum > 50) {
                    //if user wins random test
                    newRound = true;
                    btnWeapon.setEnabled(false);
                    run.setEnabled(false);
                    btnItem.setEnabled(false);
                    north.setEnabled(true);
                    south.setEnabled(true);
                    west.setEnabled(true);
                    east.setEnabled(true);
                    xp++;
                    if (encounter == 5 || encounter == 6) {
                        text2.setText("You ran, but you lost 3HP! What direction do you choose now?");
                        currentHP -= 3;
                    } else {
                        text2.setText("You were able to run away. What direction do you choose now?");
                    }
                } else {
                    //if user loses random test
                    text2.setText("You are too tired to run, and lost 2HP. Choose another action.");
                    currentHP -= 2;
                }
                updateGUI();
            }
        });

        btnItem.addActionListener((ActionEvent e) -> {
            if (!newRound) {
                //determines outcome based on what item the user has
                if (item > 0 && item < 6) {
                    if (encounter == 7) {
                        text2.setText("She stole your weapon! What direction do you choose now?");
                        weapon = 0;
                        newRound = true;
                        btnWeapon.setEnabled(false);
                        run.setEnabled(false);
                        btnItem.setEnabled(false);
                        north.setEnabled(true);
                        south.setEnabled(true);
                        west.setEnabled(true);
                        east.setEnabled(true);
                        xp++;
                    } else {
                        text2.setText("You healed yourself. Choose another action.");
                        currentHP += 4 * item;
                        if (currentHP > 20) {
                            currentHP = 20;
                        }
                    }
                } else if (item == 0) {
                    text2.setText("You have no item. Choose another action.");
                } else if (encounter == 5 || encounter == 6) {
                    text2.setText("It had no effect. Choose another action.");
                } else {
                    int bombDamage = generateNumber(item);
                    if (bombDamage > 2) {
                        text2.setText("You killed it! What direction do you choose now?");
                        newRound = true;
                        btnWeapon.setEnabled(false);
                        run.setEnabled(false);
                        btnItem.setEnabled(false);
                        north.setEnabled(true);
                        south.setEnabled(true);
                        west.setEnabled(true);
                        east.setEnabled(true);
                        xp++;
                    } else {
                        text2.setText("It had no effect. Choose another action.");
                    }
                }
                item = 0;
                updateGUI();
            }
        });
    }

    private void randomGenerate() {
        text2.setText("");
        newRound = false;

        btnWeapon.setEnabled(true);
        run.setEnabled(true);
        btnItem.setEnabled(true);

        north.setEnabled(false);
        south.setEnabled(false);
        west.setEnabled(false);
        east.setEnabled(false);
        encounter = generateNumber(11);
        while ((item != 0 && (encounter == 8 || encounter == 10))
                || (weapon != 0 && (encounter == 9 || encounter == 11))) {
            encounter = generateNumber(11);
        }
        if (encounter == 10 || encounter == 11) {
            encounter -= 2;
        }
        switch (encounter) {
            case 0:
                text1.setText("You encounter a dragon. What do you do?");
                break;
            case 1:
                text1.setText("You encounter a furious goose. What do you do?");
                break;
            case 2:
                text1.setText("You encounter EdCom. What do you do?");
                break;
            case 3:
                text1.setText("A wild lizard blocks your path. What do you do?");
                break;
            case 4:
                text1.setText("You encounter Harambe. What do you do?");
                break;
            case 5:
                text1.setText("A forest blocks your path. What do you do?");
                break;
            case 6:
                text1.setText("Quicksand blocks your path. What do you do?");
                break;
            case 7:
                text1.setText("A dying girl blocks your path. What do you do?");
                break;
            case 8:
                int half = generateNumber(2);
                if (half == 0) {
                    text1.setText("An elf gave you an item. What do you do?");
                } else {
                    text1.setText("A sand person gave you an item. What do you do?");
                }
                item = generateNumber(9) + 1;
                break;
            case 9:
                half = generateNumber(2);
                if (half == 0) {
                    text1.setText("An old man gave you a weapon. What do you do?");
                } else {
                    text1.setText("A sentient robot gave you a weapon. What do you do?");
                }
                weapon = generateNumber(9) + 1;
                break;
        }
    }

    private void updateGUI() {
        if (currentHP > 0) {
            final String[] WEAPONS = {"Fists", "Stick", "Rock", "Dagger", "S. Sword", "L. Sword",
                    "L. Bow", "R. Bow", "C. Bow", "Crossbow"};
            infoB1.setText("  Weapon: " + WEAPONS[weapon]);

            final String[] ITEMS = {"Nothing", "Petty Potion", "Small Potion", "Average Potion", "Effective Potion",
                    "Campfire", "Petty Bomb", "Small Bomb", "Average Bomb", "Effective Bomb"};
            infoB2.setText("  Item: " + ITEMS[item]);

            int previousLevel = level;
            level = (int) (Math.floor((xp / 5)) + 1);

            if (previousLevel != level) {
                currentHP = 20;
            }

            infoA1.setText("  Health: " + currentHP);
            infoA2.setText("  Level: " + level);
        } else {
            text1.setText("You died. Restart the application to play again! :(");
            text2.setText("On your adventure, you made it to Level " + level);
            infoA1.setText("  Health: 0");

            btnWeapon.setEnabled(false);
            run.setEnabled(false);
            btnItem.setEnabled(false);
            north.setEnabled(false);
            south.setEnabled(false);
            west.setEnabled(false);
            east.setEnabled(false);
        }
    }

    private int generateNumber(int max) {
        return (int) (Math.random() * (max + 1));
    }
}
