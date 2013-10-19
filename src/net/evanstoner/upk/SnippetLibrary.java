package net.evanstoner.upk;

import java.util.HashMap;
import java.util.Map;

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
    public void addSnippet(String key, String snippet) {
        _snippets.put(key.toUpperCase(), snippet);
    }

    /**
     * Returns the snippet that matches the key, or null if no snippet is specified for the key
     * @param key The key that triggers the snippet
     * @return The snippet, or null if no snippet is specified for the key
     */
    public String getSnippet(String key) {
        return _snippets.get(key);
    }
}
