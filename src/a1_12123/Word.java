package a1_2201140044;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Word {
    public static Set<String> stopWords;
    private String text;
    private String suffix;
    private String prefix;

    private Word(String prefix, String text, String suffix) {
        this.text = text;
        this.suffix = suffix;
        this.prefix = prefix;
    }

    public boolean isKeyword() {
        String textLower = text.toLowerCase();
        if (textLower.contains(" ")) {
            return false;
        }
        if (stopWords.contains(textLower)) {
            return false;
        }
        if (textLower.trim().isEmpty() || textLower.startsWith(" ") || textLower.endsWith(" ") ||
                !textLower.matches("^[a-zA-Z\\-']+$") ||
                !textLower.matches(".*[a-zA-Z].*")) {
            return false;
        }
        return true;
    }

    public String getText() {
        return text;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word = (Word) o;

        return text.equalsIgnoreCase(word.text);
    }

    @Override
    public String toString() {
        return prefix + text + suffix;
    }

    public static Word createWord(String rawText) {
        if (rawText == null || rawText.isEmpty()) {
            return new Word("", "", "");
        }

        String text = rawText;
        String prefix = "";
        String suffix = "";

        int startIndex = 0;
        while (startIndex < text.length() && !Character.isLetterOrDigit(text.charAt(startIndex))) {
            startIndex++;
        }
        if (startIndex > 0) {
            prefix = text.substring(0, startIndex);
        }
        text = text.substring(startIndex);

        int endIndex = text.length();
        while (endIndex > 0 && !Character.isLetterOrDigit(text.charAt(endIndex - 1))) {
            endIndex--;
        }
        suffix = text.substring(endIndex);
        text = text.substring(0, endIndex);

        if (text.endsWith("'s") || text.endsWith("'d")) {
            suffix = text.substring(text.length() - 2) + suffix;
            text = text.substring(0, text.length() - 2);
        }

        if (text.isEmpty() || !text.matches("[a-zA-Z\\-']+")) {
            return new Word("", rawText, "");
        }

        return new Word(prefix, text, suffix);
    }

    public static boolean loadStopWords(String fileName) {
        Word.stopWords = new HashSet<>();
        File fileInstance = new File(fileName);
        try {
            Scanner fileReader = new Scanner(fileInstance);
            while (fileReader.hasNextLine()) {
                String detail = fileReader.nextLine().trim().toLowerCase();
                if (!detail.isEmpty()) {
                    Word.stopWords.add(detail);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException exception) {
            return false;
        }
        return true;
    }
}