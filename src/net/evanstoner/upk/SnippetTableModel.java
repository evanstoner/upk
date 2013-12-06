package net.evanstoner.upk;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/26/13
 */

public class SnippetTableModel extends AbstractTableModel implements SnippetLibraryObserver {

    private SnippetLibrary _snippets;
    private ArrayList<String[]> _snippetsList = new ArrayList<String[]>();

    public SnippetTableModel(SnippetLibrary snippets) {
        _snippets = snippets;
        makeSnippetList();
    }

    private void makeSnippetList() {
        _snippetsList.clear();
        for (String key : _snippets.keySet()) {
            String[] array = { key, _snippets.get(key) };
            _snippetsList.add(array);
        }
        Collections.sort(_snippetsList, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        });
    }

    @Override
    public int getRowCount() {
        return _snippets.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Key";
            case 1: return "Snippet";
            default: return "???";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return _snippetsList.get(rowIndex)[columnIndex];
    }

    @Override
    public void didPutSnippet(String key, String snippet) {
        makeSnippetList();
    }

    @Override
    public void didRemoveSnippet(String key, String snippet) {
        makeSnippetList();
    }

    @Override
    public void didClearSnippets(Map<String, String> snippets) {
        makeSnippetList();
    }
}
