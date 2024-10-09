package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        for (String wordText : searchPhrase.split(" ")) {
            Word word = Word.createWord(wordText);
            if (word.isKeyword()) {
                keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();

        for (Word keyword : keywords) {
            int keyFreq = 0;
            int indexFirst = -1;

            List<Word> docWords = new ArrayList<>();
            docWords.addAll(d.getTitle());
            docWords.addAll(d.getBody());

            for (int i = 0; i < docWords.size(); i++) {
                if (keyword.equals(docWords.get(i))) {
                    keyFreq++;
                    if (indexFirst == -1) {
                        indexFirst = i;
                    }
                }
            }

            if (keyFreq > 0) {
                matches.add(new Match(d, keyword, keyFreq, indexFirst));
            }
        }

        matches.sort(Comparator.comparingInt(Match::getFirstIndex));

        return matches;
    }
}
