package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        String[] words = searchPhrase.split(" ");
        keywords = new ArrayList<>();
        for (String word : words) {
            Word wordSearch = Word.createWord(word);
            if (wordSearch.isKeyword()) {
                keywords.add(wordSearch);
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Word> docWords = new ArrayList<>();
        docWords.addAll(d.getTitle());
        docWords.addAll(d.getBody());

        return keywords.stream()
                .map(keyword -> {
                    int freq = (int) docWords.stream().filter(w -> w.equals(keyword)).count();
                    if (freq > 0) {
                        int firstIndex = docWords.indexOf(keyword);
                        return new Match(d, keyword, freq, firstIndex);
                    }
                    return null;
                })
                .filter(match -> match != null)
                .sorted()
                .collect(Collectors.toList());
    }
}
