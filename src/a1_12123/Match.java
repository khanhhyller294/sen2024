package engine;

public class Match implements Comparable<Match> {
    private int freq;
    private Doc d;
    private Word w;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.freq = freq;
        this.d = d;
        this.w = w;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return freq;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public Word getWord() {
        return w;
    }

    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.firstIndex, o.getFirstIndex());
    }

}
