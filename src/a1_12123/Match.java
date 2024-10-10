package a1_2201140044;

public class Match implements Comparable<Match> {
    private Doc d;
    private Word w;
    private int frequency;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.d = d;
        this.w = w;
        this.frequency = freq;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return this.frequency;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public Word getWord() {
        return this.w;
    }

    public int compareTo(Match o) {
        int integer;
        return Integer.compare(this.firstIndex, o.firstIndex);
    }
}