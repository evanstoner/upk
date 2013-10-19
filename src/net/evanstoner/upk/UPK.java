package net.evanstoner.upk;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/19/13
 */

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class UPK {

    private static HashMap<String, String> _snippets = new HashMap<String, String>();

    public static void main(String[] args) throws AWTException {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        _snippets.put("A","816 Tavernier Cir NE");
        _snippets.put("E", "e.l.stoner@gmail.com");
        _snippets.put("N", "Evan Stoner");
        _snippets.put("S", "~!@#$%^&*()_+");

        GlobalScreen.getInstance().addNativeKeyListener(new KeyListener(_snippets));
        System.out.println("UPK is running!");

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "== UPK ==\n" +
                    "s - show snippets\n" +
                    "a - add snippet\n" +
                    "d - delete snippet\n" +
                    "c - clear snippets\n" +
                    "x - exit\n" +
                    "Choice: "
            );
            String choice = s.nextLine();
            if (choice.equals("s")) {
                showSnippets();
            } else if (choice.equals("a")) {
                addSnippet();
            } else if (choice.equals("d")) {
                deleteSnippet();
            } else if (choice.equals("c")) {
                clearSnippets();
            } else if (choice.equals("x")) {
                GlobalScreen.unregisterNativeHook();
                break;
            }
        }
    }

    public static void showSnippets() {

    }

    public static void addSnippet() {

    }

    public static void deleteSnippet() {

    }

    public static void clearSnippets() {

    }
}
