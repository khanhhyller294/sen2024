package engine;

import java.util.List;

public class Result implements Comparable<Result> {
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    private Doc d;
    private List<Match> matches;

    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
        this.matchCount = matches.size();
        int totalStIndex = 0;
        this.totalFrequency = 0;

        for (Match match : matches) {
            totalStIndex = totalStIndex + match.getFirstIndex();
            this.totalFrequency = this.totalFrequency + match.getFreq();
        }

        this.averageFirstIndex = matchCount > 0 ? (double) totalStIndex / matchCount : 0.0;
    }

    public Doc getDoc() {
        return this.d;
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
        StringBuilder md = new StringBuilder();
        List<Match> listMatch = this.getMatches();
        md.append("<h3>");
        List<Word> listTitle = this.d.getTitle();

        for (Word word : listTitle) {
            boolean isMatch = listMatch.stream().anyMatch(match -> word.equals(match.getWord()));
            if (isMatch) {
                md.append(word.getPrefix())
                        .append("<u>").append(word.getText()).append("</u>")
                        .append(word.getSuffix()).append(" ");
            } else {
                md.append(word.getPrefix()).append(word.getText()).append(word.getSuffix()).append(" ");
            }
        }

        List<Word> bodies = this.d.getBody();
        md.deleteCharAt(md.length() - 1);
        md.append("</h3>");
        md.append("<p>");
        for (Word word : bodies) {
            boolean isMatch = listMatch.stream().anyMatch(match -> word.equals(match.getWord()));
            if (isMatch) {
                md.append(word.getPrefix())
                        .append("<b>").append(word.getText()).append("</b>")
                        .append(word.getSuffix()).append(" ");
            } else {
                md.append(word.getPrefix()).append(word.getText()).append(word.getSuffix()).append(" ");
            }
        }
        md.deleteCharAt(md.length() - 1);
        md.append("</p>");

        return md.toString();
    }

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
