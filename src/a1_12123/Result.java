package engine;

import java.util.List;

public class Result implements Comparable<Result> {
    private Doc doc;
    private List<Match> matches;

    public Result(Doc doc, List<Match> matches) {
        this.doc = doc;
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public Doc getDoc() {
        return this.doc;
    }

    public int getTotalFrequency() {
        return matches.stream().mapToInt(Match::getFreq).sum();
    }

    public double getAverageFirstIndex() {
        return matches.stream().mapToInt(Match::getFirstIndex).average().orElse(0.0);
    }

    public String htmlHighlight() {
        // For simplicity, only working with title words in this implementation
        StringBuilder html = new StringBuilder();
        for (Word word : doc.getTitle()) {
            if (matches.stream().anyMatch(m -> m.getWord().equals(word))) {
                html.append("<u>").append(word.getText()).append("</u> ");
            } else {
                html.append(word.getText()).append(" ");
            }
        }
        return html.toString().trim();
    }

    @Override
    public int compareTo(Result other) {
        int matchCountComparison = Integer.compare(other.getMatches().size(), this.getMatches().size());
        if (matchCountComparison != 0) return matchCountComparison;

        int totalFreqComparison = Integer.compare(other.getTotalFrequency(), this.getTotalFrequency());
        if (totalFreqComparison != 0) return totalFreqComparison;

        return Double.compare(this.getAverageFirstIndex(), other.getAverageFirstIndex());
    }
}
