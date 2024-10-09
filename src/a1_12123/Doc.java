package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] lines = content.split("\n", 2); // Assume title and body are separated by a newline
        title = extractWords(lines[0]);
        body = extractWords(lines[1]);
    }

    private List<Word> extractWords(String line) {
        String[] words = line.split(" ");
        List<Word> wordList = new ArrayList<>();
        for (String word : words) {
            wordList.add(Word.createWord(word));
        }
        return wordList;
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doc doc = (Doc) o;
        return title.equals(doc.title) && body.equals(doc.body);
    }
}
