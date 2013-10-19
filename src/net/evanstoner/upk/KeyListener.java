package net.evanstoner.upk;

/**
 * Author: Evan Stoner <evanstoner.net>
 * Date: 10/19/13
 */

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyListener implements NativeKeyListener {

    private List<String> _keys = new ArrayList<String>();
    private List<String> _comboKeys = new ArrayList<String>();
    private boolean _wasComboPressed = false;
    private boolean _isTyping = false;
    private HashMap<String, String> _snippets;
    private Robot _robot;


    public KeyListener(HashMap<String, String> snippets) throws AWTException {
        this(snippets, null);
    }

    public KeyListener(HashMap<String, String> snippets, List<String> comboKeys) throws AWTException {
        _snippets = snippets;
        _robot = new Robot();

        if (comboKeys != null) {
            _comboKeys = comboKeys;
        } else {
            _comboKeys.add("Ctrl");
            _comboKeys.add("Shift");
            _comboKeys.add("K");
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (_isTyping) return;

        String key = NativeKeyEvent.getKeyText(e.getKeyCode());

        if (_wasComboPressed) {
            if (_snippets.containsKey(key)) {
                _isTyping = true;
                //_robot.keyPress(KeyEvent.VK_BACK_SPACE);
                //_robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                type(_snippets.get(key));
                _isTyping = false;
                _wasComboPressed = false;
                System.out.println("Sent snippet: " + key + " --> " + _snippets.get(key));
            } else {
                System.out.println("No snippet for " + key);
            }
        }

        _keys.add(key);

        _wasComboPressed = areComboKeysPressed();
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        _keys.remove(NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    private boolean areComboKeysPressed() {
        boolean comboKeysPressed = true;
        for (String comboKey : _comboKeys) {
            comboKeysPressed &= _keys.contains(comboKey);
        }
        return comboKeysPressed;
    }

    /*
    type() and doType() from http://stackoverflow.com/a/1248709
     */

    private void type(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            char character = characters.charAt(i);
            type(character);
        }
    }

    private void type(char character) {
        switch (character) {
            case 'a': doType(VK_A); break;
            case 'b': doType(VK_B); break;
            case 'c': doType(VK_C); break;
            case 'd': doType(VK_D); break;
            case 'e': doType(VK_E); break;
            case 'f': doType(VK_F); break;
            case 'g': doType(VK_G); break;
            case 'h': doType(VK_H); break;
            case 'i': doType(VK_I); break;
            case 'j': doType(VK_J); break;
            case 'k': doType(VK_K); break;
            case 'l': doType(VK_L); break;
            case 'm': doType(VK_M); break;
            case 'n': doType(VK_N); break;
            case 'o': doType(VK_O); break;
            case 'p': doType(VK_P); break;
            case 'q': doType(VK_Q); break;
            case 'r': doType(VK_R); break;
            case 's': doType(VK_S); break;
            case 't': doType(VK_T); break;
            case 'u': doType(VK_U); break;
            case 'v': doType(VK_V); break;
            case 'w': doType(VK_W); break;
            case 'x': doType(VK_X); break;
            case 'y': doType(VK_Y); break;
            case 'z': doType(VK_Z); break;
            case 'A': doType(VK_SHIFT, VK_A); break;
            case 'B': doType(VK_SHIFT, VK_B); break;
            case 'C': doType(VK_SHIFT, VK_C); break;
            case 'D': doType(VK_SHIFT, VK_D); break;
            case 'E': doType(VK_SHIFT, VK_E); break;
            case 'F': doType(VK_SHIFT, VK_F); break;
            case 'G': doType(VK_SHIFT, VK_G); break;
            case 'H': doType(VK_SHIFT, VK_H); break;
            case 'I': doType(VK_SHIFT, VK_I); break;
            case 'J': doType(VK_SHIFT, VK_J); break;
            case 'K': doType(VK_SHIFT, VK_K); break;
            case 'L': doType(VK_SHIFT, VK_L); break;
            case 'M': doType(VK_SHIFT, VK_M); break;
            case 'N': doType(VK_SHIFT, VK_N); break;
            case 'O': doType(VK_SHIFT, VK_O); break;
            case 'P': doType(VK_SHIFT, VK_P); break;
            case 'Q': doType(VK_SHIFT, VK_Q); break;
            case 'R': doType(VK_SHIFT, VK_R); break;
            case 'S': doType(VK_SHIFT, VK_S); break;
            case 'T': doType(VK_SHIFT, VK_T); break;
            case 'U': doType(VK_SHIFT, VK_U); break;
            case 'V': doType(VK_SHIFT, VK_V); break;
            case 'W': doType(VK_SHIFT, VK_W); break;
            case 'X': doType(VK_SHIFT, VK_X); break;
            case 'Y': doType(VK_SHIFT, VK_Y); break;
            case 'Z': doType(VK_SHIFT, VK_Z); break;
            case '`': doType(VK_BACK_QUOTE); break;
            case '0': doType(VK_0); break;
            case '1': doType(VK_1); break;
            case '2': doType(VK_2); break;
            case '3': doType(VK_3); break;
            case '4': doType(VK_4); break;
            case '5': doType(VK_5); break;
            case '6': doType(VK_6); break;
            case '7': doType(VK_7); break;
            case '8': doType(VK_8); break;
            case '9': doType(VK_9); break;
            case '-': doType(VK_MINUS); break;
            case '=': doType(VK_EQUALS); break;
            case '~': doType(VK_SHIFT, VK_BACK_QUOTE); break;
            case '!': doType(VK_SHIFT, VK_1); break;
            case '@': doType(VK_SHIFT, VK_2); break;
            case '#': doType(VK_SHIFT, VK_3); break;
            case '$': doType(VK_SHIFT, VK_4); break;
            case '%': doType(VK_SHIFT, VK_5); break;
            case '^': doType(VK_SHIFT, VK_6); break;
            case '&': doType(VK_SHIFT, VK_7); break;
            case '*': doType(VK_SHIFT, VK_8); break;
            case '(': doType(VK_SHIFT, VK_9); break;
            case ')': doType(VK_SHIFT, VK_0); break;
            case '_': doType(VK_SHIFT, VK_MINUS); break;
            case '+': doType(VK_SHIFT, VK_EQUALS); break;
            case '\t': doType(VK_TAB); break;
            case '\n': doType(VK_ENTER); break;
            case '[': doType(VK_OPEN_BRACKET); break;
            case ']': doType(VK_CLOSE_BRACKET); break;
            case '\\': doType(VK_BACK_SLASH); break;
            case '{': doType(VK_SHIFT, VK_OPEN_BRACKET); break;
            case '}': doType(VK_SHIFT, VK_CLOSE_BRACKET); break;
            case '|': doType(VK_SHIFT, VK_BACK_SLASH); break;
            case ';': doType(VK_SEMICOLON); break;
            case ':': doType(VK_COLON); break;
            case '\'': doType(VK_QUOTE); break;
            case '"': doType(VK_QUOTEDBL); break;
            case ',': doType(VK_COMMA); break;
            case '<': doType(VK_LESS); break;
            case '.': doType(VK_PERIOD); break;
            case '>': doType(VK_GREATER); break;
            case '/': doType(VK_SLASH); break;
            case '?': doType(VK_SHIFT, VK_SLASH); break;
            case ' ': doType(VK_SPACE); break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }

        _robot.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        _robot.keyRelease(keyCodes[offset]);
    }
}
