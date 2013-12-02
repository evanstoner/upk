package net.evanstoner.upk;

import java.util.Map;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 12/1/13
 */

public interface SnippetLibraryObserver {
    public void didPutSnippet(String key, String snippet);
    public void didRemoveSnippet(String key, String snippet);
    public void didClearSnippets(Map<String, String> snippets);
}
