package de.conciso.codingdojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RotCipherImpl implements RotCipher {

    Map<Character, Character> encryptionMap = new HashMap<>();

    @Override
    public String encrypt(String text) {
        if(text == null) {
            throw new IllegalArgumentException();
        }
        char[] characters = text.toLowerCase().toCharArray();
        String encrypted = "";
        for (char toencrypt: characters) {
            encrypted += encryptionMap.computeIfAbsent(toencrypt, this::encrypt);
        }

        return encrypted;
    }

    private char encrypt(char toEncrypt){
        char[] alphabet = getAlphabet();
        for (int i = 0; i < alphabet.length; i++) {
            if(alphabet[i] == toEncrypt) {
                return alphabet[(i + getRotationFactor()) % alphabet.length];
            }
        }
        return toEncrypt;
    }

    @Override
    public String decrypt(String encryptedText) {
        return null;
    }
}
