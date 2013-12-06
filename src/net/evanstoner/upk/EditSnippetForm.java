package net.evanstoner.upk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 11/9/13
 */

public class EditSnippetForm {
    private JTextField txtKey;
    private JTextField txtSnippet;
    private JButton saveButton;
    private JPanel mainPanel;
    private JButton deleteButton;
    private JButton cancelButton;

    public enum EditMode {
        ADD, EDIT, DELETE
    }

    public EditSnippetForm(SnippetLibrary snippets) {
        this(snippets, EditMode.EDIT);
    }

    public EditSnippetForm(final SnippetLibrary snippets, final EditMode editMode) {
        switch (editMode) {
            case ADD:
                deleteButton.setVisible(false);
                break;
            case EDIT:
                txtKey.setEditable(false);
                deleteButton.setVisible(false);
                break;
            case DELETE:
                saveButton.setVisible(false);
                txtKey.setEditable(false);
                txtSnippet.setEditable(false);
                break;
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtKey.getText().toUpperCase();
                String snippet = txtSnippet.getText();

                if (key.length() != 1) {
                    JOptionPane.showMessageDialog(mainPanel, "The key has to be exactly one character.");
                    return;
                }

                if (snippet.length() == 0) {
                    JOptionPane.showMessageDialog(mainPanel, "Enter a snippet.");
                    return;
                }

                if (snippets.get(key) != null) {
                    if (editMode == EditMode.ADD) {
                        JOptionPane.showMessageDialog(mainPanel, "The key " + key + " is already in use.");
                        return;
                    } else {
                        snippets.remove(key);
                    }
                }

                snippets.put(key, snippet);
                close();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtKey.getText().toUpperCase();
                snippets.remove(key);
                close();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    private void close() {
        Container c = mainPanel.getParent();
        while (c.getParent() != null) {
            c = c.getParent();
        }
        c.setVisible(false);
    }

    public void setValues(String key, String snippet) {
        txtKey.setText(key);
        txtSnippet.setText(snippet);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
