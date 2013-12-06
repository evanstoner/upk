package net.evanstoner.upk;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/19/13
 */

public class SnippetsForm implements SnippetLibraryObserver {
    private JTable snippetsTable;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JPanel buttonPanel;

    private KeyboardForm _keyboardForm;
    private SnippetLibrary _snippets = new SnippetLibrary();

    private final String SNIPPETS_FILE = "snippets";

    public SnippetsForm() {
        // set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           // oh well, do nothing
        }

        // register the hook
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            JOptionPane.showMessageDialog(mainPanel, "There was a problem registering the key hook.");
            System.exit(1);
        }

        // load the snippets file
        try {
            _snippets.load(SNIPPETS_FILE);
        } catch (FileNotFoundException e) {
            System.err.println("Couln't load snippets file");
        }

        // prepare the keyboard form
        _keyboardForm = new KeyboardForm(_snippets);
        _keyboardForm.show();
        _keyboardForm.hide();
        try {
            GlobalScreen.getInstance().addNativeKeyListener(new KeyListener(_snippets, _keyboardForm));
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(mainPanel, "There was a problem starting the key listener.");
            System.exit(1);
        }

        // update on changes
        _snippets.registerObserver(this);

        // bind the table model
        SnippetTableModel model = new SnippetTableModel(_snippets);
        snippetsTable.setModel(model);
        _snippets.registerObserver(model);

        // add listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("New Snippet");
                frame.setContentPane(new EditSnippetForm(_snippets, EditSnippetForm.EditMode.ADD).getMainPanel());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditSnippetForm editSnippetForm = new EditSnippetForm(_snippets);
                TableModel model = snippetsTable.getModel();
                int row = snippetsTable.getSelectedRow();
                editSnippetForm.setValues(model.getValueAt(row, 0).toString(), model.getValueAt(row, 1).toString());

                JFrame frame = new JFrame("Edit Snippet");
                frame.setContentPane(editSnippetForm.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditSnippetForm editSnippetForm = new EditSnippetForm(_snippets, EditSnippetForm.EditMode.DELETE);
                TableModel model = snippetsTable.getModel();
                int row = snippetsTable.getSelectedRow();
                editSnippetForm.setValues(model.getValueAt(row, 0).toString(), model.getValueAt(row, 1).toString());

                JFrame frame = new JFrame("Delete Snippet");
                frame.setContentPane(editSnippetForm.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snippets");
        frame.setContentPane(new SnippetsForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void saveSnippets() {
        try {
            _snippets.save(SNIPPETS_FILE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Your snippets couldn't be saved: " + ex.getMessage());
        }
    }

    private void updateTable() {
        AbstractTableModel abstractModel = (AbstractTableModel) snippetsTable.getModel();
        abstractModel.fireTableDataChanged();
    }

    @Override
    public void didPutSnippet(String key, String snippet) {
        saveSnippets();
        updateTable();
    }

    @Override
    public void didRemoveSnippet(String key, String snippet) {
        saveSnippets();
        updateTable();
    }

    @Override
    public void didClearSnippets(Map<String, String> snippets) {
        updateTable();
    }
}
