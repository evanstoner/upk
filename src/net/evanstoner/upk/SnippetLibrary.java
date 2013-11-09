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

    /**
     * Adds a snippet to the library
     * @param key The key that triggers this snippet; one character only
     * @param snippet The snippet to paste
     */
    public void put(String key, String snippet) {
        _snippets.put(key.toUpperCase(), snippet);
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
        _snippets.remove(key.toUpperCase());
    }

    public void clear() {
        _snippets.clear();
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

    public void save(String path) throws IOException {
        FileWriter w = new FileWriter(path);
        for (String key : keySet()) {
            w.write(key + "||" + get(key) + "\n");
        }
        w.close();
    }
}
