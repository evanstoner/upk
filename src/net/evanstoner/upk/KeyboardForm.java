package net.evanstoner.upk;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 11/17/13
 */

public class KeyboardForm implements SnippetLibraryObserver {
    private JFrame _frame;
    private JPanel mainPanel;
    private JPanel infoRow;
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    private SnippetLibrary _snippets;
    private HashMap<String, JLabel> _keyLabels = new HashMap<String, JLabel>();

    public KeyboardForm(SnippetLibrary snippets) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // oh well, do nothing
        }

        _snippets = snippets;
        _snippets.registerObserver(this);

        _frame = new JFrame("Keyboard");
        _frame.setContentPane(mainPanel);
        _frame.setPreferredSize(new Dimension(1000, 370));
        _frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        _frame.pack();
        _frame.setLocationRelativeTo(null);

        configurePanel(row1);
        configurePanel(row2);
        configurePanel(row3);

        String[] keys1 = {"q","w","e","r","t","y","u","i","o","p"};
        String[] keys2 = {"a","s","d","f","g","h","j","k","l"};
        String[] keys3 = {"z","x","c","v","b","n","m"};

        addKeys(keys1, row1);
        addKeys(keys2, row2);
        addKeys(keys3, row3);
    }

    private void configurePanel(JPanel panel) {
        panel.setLayout(new FlowLayout());
    }

    private void addKeys(String[] keys, JPanel panel) {
        for (String key : keys) {
            key = key.toUpperCase();
            JLabel label = new JLabel();
            label.setText(getKeyText(key));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setPreferredSize(new Dimension(90, 90));
            label.setBorder(new LineBorder(new Color(121, 121, 121), 1));
            panel.add(label);
            _keyLabels.put(key, label);
        }
    }

    private String getKeyText(String key) {
        key = key.toUpperCase();
        if (_snippets.get(key) != null) {
            return "<html><div style=\"text-align:center\"><b>" + key + "</b><br>" + _snippets.get(key) + "</div></html>";
        } else {
            return key;
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

    @Override
    public void didPutSnippet(String key, String snippet) {
        _keyLabels.get(key).setText(getKeyText(key));
    }

    @Override
    public void didRemoveSnippet(String key, String snippet) {
        _keyLabels.get(key).setText(getKeyText(key));
    }

    @Override
    public void didClearSnippets(Map<String, String> snippets) {
        //TODO
    }
}
