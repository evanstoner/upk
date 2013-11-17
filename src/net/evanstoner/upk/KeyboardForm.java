package net.evanstoner.upk;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 11/17/13
 */

public class KeyboardForm {
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    private JPanel mainPanel;
    private JFrame _frame;
    private SnippetLibrary _snippets;

    public KeyboardForm(SnippetLibrary snippets) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // do nothing
        }

        _snippets = snippets;

        _frame = new JFrame("Keyboard");
        _frame.setContentPane(mainPanel);
        _frame.setPreferredSize(new Dimension(1000, 350));
        _frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        _frame.pack();
        _frame.setLocationRelativeTo(null);

        String[] keys1 = {"q","w","e","r","t","y","u","i","o","p"};
        String[] keys2 = {"a","s","d","f","g","h","j","k","l"};
        String[] keys3 = {"z","x","c","v","b","n","m"};

        configurePanel(row1);
        configurePanel(row2);
        configurePanel(row3);

        addKeys(keys1, row1);
        addKeys(keys2, row2);
        addKeys(keys3, row3);
    }

    private void configurePanel(JPanel panel) {
        panel.setLayout(new FlowLayout());
//        panel.setPreferredSize(new Dimension(900, 100));
    }

    private void addKeys(String[] keys, JPanel panel) {
        for (String key : keys) {
            JLabel label = new JLabel();
            if (_snippets.get(key) != null) {
                label.setText("<html><div style=\"text-align:center\">" + key.toUpperCase() + "<br>" + _snippets.get(key) + "</div></html>");
            } else {
                label.setText(key.toUpperCase());
            }
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setPreferredSize(new Dimension(90, 90));
            label.setBorder(new LineBorder(new Color(121, 121, 121), 1));
            panel.add(label);
        }
    }

    public void show() {
        _frame.setVisible(true);
    }

    public void hide() {
        _frame.setVisible(false);
    }

    public boolean isVisible() {
        return _frame.isVisible();
    }

    public void dispose() {
        _frame.dispose();
    }
}
