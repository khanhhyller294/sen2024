package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] lines = content.split("\\R");
        this.title = new ArrayList<>();
        this.body = new ArrayList<>();
        if (lines.length > 0) {
            String[] words = lines[0].split(" ");
            for (String word : words) {
                this.title.add(Word.createWord(word));
            }
        }
        if (lines.length > 1) {
            String[] words = lines[1].split(" ");
            for (String word : words) {
                this.body.add(Word.createWord(word));
            }
        }
    }

    public List<Word> getTitle() {
        return this.title;
    }

    public List<Word> getBody() {
        return this.body;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doc other = (Doc) o;
        return title.equals(other.title) && body.equals(other.body);
    }

}