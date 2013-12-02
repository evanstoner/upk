package net.evanstoner.upk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/19/13
 */

public class SnippetLibrary {

    private Map<String, String> _snippets = new HashMap<String, String>();
    private ArrayList<SnippetLibraryObserver> _observers = new ArrayList<SnippetLibraryObserver>();

    /**
     * Adds a snippet to the library
     * @param key The key that triggers this snippet; one character only
     * @param snippet The snippet to paste
     */
    public void put(String key, String snippet) {
        key = key.toUpperCase();
        _snippets.put(key, snippet);

        for (SnippetLibraryObserver observer : _observers) {
            observer.didPutSnippet(key, snippet);
        }
    }

    /**
     * Returns the snippet that matches the key, or null if no snippet is specified for the key
     * @param key The key that triggers the snippet
     * @return The snippet, or null if no snippet is specified for the key
     */
    public String get(String key) {
        return _snippets.get(key.toUpperCase());
    }

    /**
     * Removes a snippet entry for the specified key
     * @param key The key to remove
     */
    public void remove(String key) {
        key = key.toUpperCase();
        String snippet = _snippets.get(key);
        _snippets.remove(key.toUpperCase());

        for (SnippetLibraryObserver observer : _observers) {
            observer.didRemoveSnippet(key, snippet);
        }
    }

    public void clear() {
        Map<String, String> snippets = _snippets;
        _snippets.clear();

        for (SnippetLibraryObserver observer : _observers) {
            observer.didClearSnippets(snippets);
        }
    }

    public Set<String> keySet() {
        return _snippets.keySet();
    }

    public int size() {
        return _snippets.size();
    }

    /**
     * Loads the snippets from the specified file
     * @param path The file to load from
     * @return int The number of snippets that were loaded
     */
    public int load(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        String[] line;
        int count = 0;

        while (s.hasNextLine()) {
            line = s.nextLine().split("\\|\\|");
            if (line.length == 2) {
                put(line[0], line[1]);
            }
        }

        return count;
    }

    /**
     * Saves the snippets to the specified file
     * @param path
     * @throws IOException
     */
    public void save(String path) throws IOException {
        FileWriter w = new FileWriter(path);
        for (String key : keySet()) {
            w.write(key + "||" + get(key) + "\n");
        }
        w.close();
    }

    public void registerObserver(SnippetLibraryObserver observer) {
        _observers.add(observer);
    }

    public void unregisterObserver(SnippetLibraryObserver observer) {
        _observers.remove(observer);
    }
}
