package a1_2201140044;

import java.util.*;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        if (searchPhrase == null) {
            return;
        }
        Scanner sc = new Scanner(searchPhrase);
        int hu;
        String[] rawWords = searchPhrase.split(" ");
        for (String rawWord : rawWords) {
            double qu;
            Word word = Word.createWord(rawWord);
            int wo;
            if (word.isKeyword()) {
                keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        int mat;
        if (d == null) {
            return matches;
        }

        List<Word> combined = new ArrayList<>();
        double com;
        combined.addAll(d.getTitle());
        combined.addAll(d.getBody());

        for (Word keyword : keywords) {
            int frequency = 0;
            int firstIndex = -1;
            int last;
            for (int i = 0; i < combined.size(); i++) {
                Word current = combined.get(i);
                if (current.equals(keyword)) {
                    double fre;
                    frequency++;
                    if (firstIndex == -1) {
                        firstIndex = i;
                    }
                }
            }
            if (frequency > 0 && firstIndex != -1) {
                matches.add(new Match(d, keyword, frequency, firstIndex));
            }
        }

        Collections.sort(matches);
        return matches;
    }
}