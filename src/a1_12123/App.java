package a1_2201140044;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class App {
    public App() {
    }

    public static void main(String[] args) throws FileNotFoundException {
        boolean canLoadStopWords = Word.loadStopWords("stopwords2.txt");
        if (canLoadStopWords) {
            System.out.println("Word.loadStopWords(): incorrect return value (expected: false)");
        }

        canLoadStopWords = Word.loadStopWords("stopwords.txt");
        if (!canLoadStopWords) {
            System.out.println("Word.loadStopWords(): incorrect return value (expected: true)");
        }

        if (Word.stopWords.size() != 174) {
            System.out.println("Word.loadStopWords(): incorrect number of stopWords loaded (expected: 174)");
        }

        Engine e = new Engine();
        int loadedDocs = e.loadDocs("docs");
        if (loadedDocs != 10) {
            System.out.println("Word.loadDocs(): incorrect return value (expected: 10)");
        }

        if (Word.createWord("").isKeyword()) {
            System.out.println("Word.createWord(): empty string ('') should be an invalid word (not a keyword)");
        }

        if (Word.createWord("123456").isKeyword()) {
            System.out.println("Word.createWord(): '123456' should be an invalid word (not a keyword)");
        }

        if (Word.createWord("!@#$%^").isKeyword()) {
            System.out.println("Word.createWord(): '!@#$%^' should be an invalid word (not a keyword)");
        }

        if (Word.createWord("se2021").isKeyword()) {
            System.out.println("Word.createWord(): 'se2021' should be an invalid word (not a keyword)");
        }

        if (Word.createWord(" and").isKeyword()) {
            System.out.println("Word.createWord(): ' and' should be treated as an invalid word (not a keyword because it contains a space)");
        }

        if (Word.createWord(",se2021.").isKeyword()) {
            System.out.println("Word.createWord(): ',se2021.' should be an invalid word");
        }

        if (Word.createWord("the").isKeyword()) {
            System.out.println("Word.isKeyword(): 'the' is a stop word (not a keyword)");
        }

        if (Word.createWord("of").isKeyword()) {
            System.out.println("Word.isKeyword(): 'of' is a stop word (not a keyword)");
        }

        if (!Word.createWord("context").isKeyword()) {
            System.out.println("Word.isKeyword(): 'context' should be a keyword");
        }

        if (!Word.createWord("design").isKeyword()) {
            System.out.println("Word.isKeyword(): 'design' should be a keyword");
        }

        if (!Word.createWord(",se2021.").getText().equals(",se2021.")) {
            System.out.println("Word.createWord(): the text part of ',se2021.' should be ',se2021.'");
        }

        if (!Word.createWord(",se2021.").getPrefix().equals("")) {
            System.out.println("Word.createWord(): the prefix of ',se2021.' should be empty");
        }

        if (!Word.createWord(",se2021.").getSuffix().equals("")) {
            System.out.println("Word.createWord(): the suffix of ',se2021.' should be empty");
        }

        if (!Word.createWord("word,").getText().equals("word")) {
            System.out.println("Word.createWord(): the text part of 'word,' should be 'word'");
        }

        if (!Word.createWord("word,").getPrefix().equals("")) {
            System.out.println("Word.createWord(): the prefix of 'word,' should be empty");
        }

        if (!Word.createWord("word,").getSuffix().equals(",")) {
            System.out.println("Word.createWord(): the suffix of 'word,' should be ','");
        }

        if (!Word.createWord("«word»").getText().equals("word")) {
            System.out.println("Word.createWord(): the text part of '«word»' should be 'word'");
        }

        if (!Word.createWord("«WORD»").getPrefix().equals("«")) {
            System.out.println("Word.createWord(): the prefix of '«WORD»' should be '«'");
        }

        if (!Word.createWord("«Word»").getSuffix().equals("»")) {
            System.out.println("Word.createWord(): the prefix of '«Word»' should be '»'");
        }

        if (!Word.createWord("apple").equals(Word.createWord("apple"))) {
            System.out.println("Word.equals() failed with case 'apple'");
        }

        if (!Word.createWord("apple").equals(Word.createWord("Apple"))) {
            System.out.println("Word.equals() should be case-insensitive, so 'apple' should be equal to 'Apple'");
        }

        if (!Word.createWord("content").equals(Word.createWord("\"content\"."))) {
            System.out.println("Word.equals() should compare the text part only, so 'content' should be equal to '\"content\".'");
        }

        Doc d = new Doc("Object-oriented \"design\": with UML's diagrams\nDefinition: An object-oriented system's context made up of (interacting) objects.");
        Query q = new Query("the <context> of observer: design");
        Object[] tests = new Object[]{d.getTitle(), d.getBody(), q.getKeywords()};
        int[] listSizes = new int[]{5, 10, 3};
        String[] methods = new String[]{"Doc.getTitle()", "Doc.getBody()", "Query.getKeywords()"};
        Object[] wordTexts = new Object[]{new String[]{"Object-oriented", "design", "with", "UML", "diagrams"}, new String[]{"Definition", "An", "object-oriented", "system", "context", "made", "up", "of", "interacting", "objects"}, new String[]{"context", "observer", "design"}};
        Object[] wordPrefixes = new Object[]{new String[]{"", "\"", "", "", ""}, new String[]{"", "", "", "", "", "", "", "", "(", ""}, new String[]{"<", "", ""}};
        Object[] wordSuffixes = new Object[]{new String[]{"", "\":", "", "'s", ""}, new String[]{":", "", "", "'s", "", "", "", "", ")", "."}, new String[]{">", ":", ""}};
        Object[] wordTypes = new Object[]{new boolean[]{true, true, false, true, true}, new boolean[]{true, false, true, true, true, true, false, false, true, true}, new boolean[]{true, true, true}};

        for(int x = 0; x < tests.length; ++x) {
            List<Word> tmp = (List)tests[x];
            String[] wtxt = (String[])((String[])((String[])wordTexts[x]));
            String[] wpf = (String[])((String[])((String[])wordPrefixes[x]));
            String[] wsf = (String[])((String[])((String[])wordSuffixes[x]));
            boolean[] wtp = (boolean[])((boolean[])((boolean[])wordTypes[x]));
            if (tmp.size() != listSizes[x]) {
                System.out.println(methods[x] + ": unexpected list length");
            } else {
                for(int i = 0; i < tmp.size(); ++i) {
                    if (!wtxt[i].equals(((Word)tmp.get(i)).getText())) {
                        System.out.println(methods[x] + ": incorrect word text '" + ((Word)tmp.get(i)).getText() + "' (expected '" + wtxt[i] + "')");
                    }

                    if (!wpf[i].equals(((Word)tmp.get(i)).getPrefix())) {
                        System.out.println(methods[x] + ": incorrect word prefix '" + ((Word)tmp.get(i)).getPrefix() + "' (expected '" + wpf[i] + "')");
                    }

                    if (!wsf[i].equals(((Word)tmp.get(i)).getSuffix())) {
                        System.out.println(methods[x] + ": incorrect word suffix '" + ((Word)tmp.get(i)).getSuffix() + "' (expected '" + wsf[i] + "')");
                    }

                    if (wtp[i] != ((Word)tmp.get(i)).isKeyword()) {
                        System.out.println(methods[x] + ": incorrect isKeyword for '" + ((Word)tmp.get(i)).toString() + "' (expected: " + wtp[i] + ")");
                    }
                }
            }
        }

        List<Match> matches = q.matchAgainst(d);
        String[] matchedWords = new String[]{"design", "context"};
        if (matches.size() != 2) {
            System.out.println("Query.matchAgainst(): incorrect matches count (expected: 2)");
        }

        for(int i = 0; i < matches.size(); ++i) {
            if (!((Match)matches.get(i)).getWord().getText().equals(matchedWords[i])) {
                System.out.println("Query.matchAgainst(): incorrect word '" + ((Match)matches.get(i)).getWord().getText() + " (expected: '" + matchedWords[i] + "')");
            }
        }

        List<Result> results = e.search(q);
        if (results.size() != 8) {
            System.out.println("Engine.search(): incorrect results count");
        }

        int[] matchCounts = new int[]{2, 1, 1, 1, 1, 1, 1, 1};

        for(int i = 0; i < results.size(); ++i) {
            int x = ((Result)results.get(i)).getMatches().size();
            if (x != matchCounts[i]) {
                System.out.println("Engine.search(): incorrect match count (actual: " + x + ", expected: " + matchCounts[i]);
            }
        }

        String tmpTitle = ((Result)results.get(0)).getDoc().getTitle().toString();
        if (!tmpTitle.equals("[System, context, and, interactions]")) {
            System.out.println("Engine.search(): incorrect first result '" + tmpTitle + "' (expected '[System, context, and, interactions]'");
        }

        tmpTitle = ((Result)results.get(1)).getDoc().getTitle().toString();
        if (!tmpTitle.equals("[Design, patterns]")) {
            System.out.println("Engine.search(): incorrect second result '" + tmpTitle + "' (expected '[Design, patterns]'");
        }

        Scanner sc = new Scanner(new File("testCases.html"));
        String firstResultHTML = sc.nextLine();
        if (!firstResultHTML.equals(((Result)results.get(0)).htmlHighlight().trim())) {
            System.out.println("Result.htmlHighlight(): incorrect output for first result");
        }

        String secondResultHTML = sc.nextLine();
        if (!secondResultHTML.equals(((Result)results.get(1)).htmlHighlight().trim())) {
            System.out.println("Result.htmlHighlight(): incorrect output for second result");
        }

        String html = e.htmlResult(results).trim();
        String expectedHTML = sc.nextLine();
        if (!html.equals(expectedHTML)) {
            System.out.println("Engine.htmlResult(): incorrect output");
        }

    }
}
