package edu.hitsz.application.gui;

import edu.hitsz.application.Main;
import edu.hitsz.application.music.MusicThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame {


    private JPanel mainPanel;
    private JButton buttonEasy;
    private JButton buttonNormal;
    private JButton buttonHard;
    private JLabel soundLable;
    private JComboBox soundComboBox;


    public StartFrame() {

        String selectedOption = (String) soundComboBox.getSelectedItem();
        assert selectedOption != null;
        if (selectedOption.equalsIgnoreCase("off")) {
            MusicThread.setMusicSwitch(false);
        } else {
            MusicThread.setMusicSwitch(true);
        }
        buttonEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Main.LOCK) {
                    Main.setDifficulty("Easy");
                    Main.LOCK.notify();
                }
            }
        });


        buttonNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Main.LOCK) {
                    Main.setDifficulty("Normal");
                    Main.LOCK.notify();
                }
            }
        });
        buttonHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Main.LOCK) {
                    Main.setDifficulty("Hard");
                    Main.LOCK.notify();
                }
            }
        });
        soundComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) soundComboBox.getSelectedItem();
                assert selectedOption != null;
                if (selectedOption.equalsIgnoreCase("off")) {
                    MusicThread.setMusicSwitch(false);
                } else {
                    MusicThread.setMusicSwitch(true);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
