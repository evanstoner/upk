package net.evanstoner.upk;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/19/13
 */

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class UPK {

    private static SnippetLibrary _snippets = new SnippetLibrary();
    private static KeyboardForm _keyboardForm;
    private static Scanner _scanner;

    private static final String SNIPPETS_FILE = "snippets";
    private static final String EXIT_FLAG = "xx";

    public static void main(String[] args) throws AWTException {
        System.out.println("Starting UPK...");

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the key hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        try {
            _snippets.load(SNIPPETS_FILE);
        } catch (FileNotFoundException e) {
            System.err.println("Couln't load snippets file");
        }
        System.out.println("Snippets found: " + _snippets.size());

        _keyboardForm = new KeyboardForm(_snippets);
        _keyboardForm.show();
        _keyboardForm.hide();
        GlobalScreen.getInstance().addNativeKeyListener(new KeyListener(_snippets, _keyboardForm));
        System.out.println("UPK is running!");

        _scanner = new Scanner(System.in);

        while (true) {
            System.out.print(
                    "\n== UPK ==\n" +
                    "s - show snippets (" + _snippets.size() + ")\n" +
                    "a - add snippet\n" +
                    "d - delete snippet\n" +
                    "c - clear snippets\n" +
                    "x - exit\n" +
                    "Choice: "
            );
            String choice = _scanner.nextLine();
            System.out.println();

            if (choice.equals("s")) {
                showSnippets();
            } else if (choice.equals("a")) {
                addSnippet();
            } else if (choice.equals("d")) {
                deleteSnippet();
            } else if (choice.equals("c")) {
                clearSnippets();
            } else if (choice.equals("x") || choice.equals(EXIT_FLAG)) {
                System.out.println("Shutting down...");
                GlobalScreen.unregisterNativeHook();
                _keyboardForm.dispose();
                break;
            }

            try {
                _snippets.save(SNIPPETS_FILE);
            } catch (IOException ex) {
                System.err.println("Your snippets couldn't be saved: " + ex.getMessage());
            }
        }
    }

    private static void printExitFlag() {
        System.out.println("(Enter " + EXIT_FLAG + " at any time to cancel.)");
    }

    private static String promptForKey(String prompt) {
        String key = null;

        while (key == null) {
            System.out.print(prompt + ": ");
            key = _scanner.nextLine();

            if (key.equals(EXIT_FLAG)) {
                return EXIT_FLAG;
            }

            if (key.length() != 1) {
                System.out.println("<!> The key must be exactly one character.");
                key = null;
            }
        }

        return key;
    }

    public static void showSnippets() {
        if (_snippets.size() > 0) {
            System.out.println("Key | Snippet");
            for (String key : _snippets.keySet()) {
                System.out.println(key + "   | " + _snippets.get(key));
            }
        } else {
            System.out.println("No snippets have been added yet.");
        }
    }

    public static void addSnippet() {
        String key = null;
        String snippet = null;

        printExitFlag();

        while (key == null) {
            key = promptForKey("Key");

            if (key.equals(EXIT_FLAG)) {
                return;
            }

            if (_snippets.get(key) != null) {
                System.out.println("<!> This key is already in use.");
                key = null;
            }
        }

        while (snippet == null) {
            System.out.print("Snippet: ");
            snippet = _scanner.nextLine();

            if (snippet.equals(EXIT_FLAG)) {
                return;
            }

            if (snippet.length() == 0) {
                System.out.println("<!> Enter a snippet.");
                snippet = null;
            }
        }

        _snippets.put(key, snippet);
        System.out.println("Snippet added.");
    }

    public static void deleteSnippet() {
        showSnippets();

        printExitFlag();

        String key = promptForKey("Key to delete");
        if (key.equals(EXIT_FLAG)) {
            return;
        }

        _snippets.remove(key);
        System.out.println("Snippet removed.");
    }

    public static void clearSnippets() {
        String yn;
        System.out.print("Are you sure you want to clear all snippets? This is irreversible (y/n): ");
        yn = _scanner.nextLine();
        if (yn.equals("y")) {
            _snippets.clear();
            System.out.println("Snippets cleared.");
        } else {
            System.out.println("Canceled.");
        }
    }
}
