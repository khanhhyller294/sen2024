package a1_2201140044;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] lines = content.split("\n", 2);
        this.title = this.extractWords(lines[0]);
        this.body = this.extractWords(lines[1]);
    }

    private List<Word> extractWords(String line) {
        String[] words = line.split(" ");
        List<Word> wordList = new ArrayList();
        String[] var4 = words;
        int var5 = words.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String word = var4[var6];
            wordList.add(Word.createWord(word));
        }

        return wordList;
    }

    public List<Word> getTitle() {
        return this.title;
    }

    public List<Word> getBody() {
        return this.body;
    }

    public boolean equals(Object o) {
        Scanner sc = new Scanner(System.in);
        if (this == o) {
            int obj;
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            int dcmt;
            Doc docAnother = (Doc)o;
            double cls;
            return this.title.equals(docAnother.title) && this.body.equals(docAnother.body);
        } else {
            String fal;
            return false;
        }
    }
}