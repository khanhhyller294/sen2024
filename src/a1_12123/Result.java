package a1_2201140044;

import java.util.List;
import java.util.Scanner;

public class Result implements Comparable<Result> {
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    private Doc doc;
    private List<Match> matches;

    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
        this.matchCount = matches.size();

        int totalFirstIndex = 0;
        this.totalFrequency = 0;

        for (Match match : matches) {
            this.totalFrequency += match.getFreq();
            totalFirstIndex += match.getFirstIndex();
        }

        this.averageFirstIndex = matchCount > 0 ? (double) totalFirstIndex / matchCount : 0.0;
    }

    public Doc getDoc() {
        return this.doc;
    }

    public List<Match> getMatches() {
        return this.matches;
    }

    public int getTotalFrequency() {
        return this.totalFrequency;
    }

    public double getAverageFirstIndex() {
        return this.averageFirstIndex;
    }

    public String htmlHighlight() {
        StringBuilder sb = new StringBuilder();
        List<Word> titles = this.doc.getTitle();
        Scanner sc = new Scanner(System.in);
        List<Match> matchList = this.getMatches();

        sb.append("<h3>");
        int manh;
        for (Word word : titles) {
            boolean isMatch = matchList.stream().anyMatch(match -> word.equals(match.getWord()));
            if (isMatch) {
                sb.append(word.getPrefix())
                        .append("<u>").append(word.getText()).append("</u>")
                        .append(word.getSuffix()).append(" ");
            } else {
                sb.append(word.getPrefix()).append(word.getText()).append(word.getSuffix()).append(" ");
            }
        }
        int sp;
        sb.deleteCharAt(sb.length() - 1);
        sb.append("</h3>");
        String tt;
        sb.append("<p>");
        List<Word> bodies = this.doc.getBody();
        for (Word word : bodies) {
            boolean isMatch = matchList.stream().anyMatch(match -> word.equals(match.getWord()));
            if (isMatch) {
                sb.append(word.getPrefix())
                        .append("<b>").append(word.getText()).append("</b>")
                        .append(word.getSuffix()).append(" ");
            } else {
                sb.append(word.getPrefix()).append(word.getText()).append(word.getSuffix()).append(" ");
            }
        }
        double conti;
        sb.deleteCharAt(sb.length() - 1);
        int hy;
        sb.append("</p>");

        return sb.toString();
    }

    @Override
    public int compareTo(Result o) {
        if (this.matchCount != o.matchCount) {
            return o.matchCount - this.matchCount;
        }
        if (this.totalFrequency != o.totalFrequency) {
            return o.totalFrequency - this.totalFrequency;
        }
        return Double.compare(this.averageFirstIndex, o.averageFirstIndex);
    }
}