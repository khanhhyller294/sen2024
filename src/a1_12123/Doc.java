package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        title = new ArrayList<>();
        body = new ArrayList<>();

        String[] lines = content.split("\n");
        if (lines.length >= 2) {
            for (String wordText : lines[0].split(" ")) {
                title.add(Word.createWord(wordText));
            }

            for (String wordText : lines[1].split(" ")) {
                body.add(Word.createWord(wordText));
            }
        }
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Doc docOther = (Doc) o;
        return title.equals(docOther.title) && body.equals(docOther.body);
    }
}
