package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private List<Doc> docs = new ArrayList<>();

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles();
        if (files == null) return 0;

        for (File file : files) {
            try (Scanner sc = new Scanner(file)) {
                String content = sc.nextLine() + "\n" + sc.nextLine();
                docs.add(new Doc(content));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return docs.size();
    }

    public Doc[] getDocs() {
        return docs.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        for (Doc doc : docs) {
            List<Match> matches = q.matchAgainst(doc);
            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }
        results.sort(null); // Sort by the compareTo() method in Result
        return results;
    }

    public String htmlResult(List<Result> results) {
        StringBuilder html = new StringBuilder();
        for (Result result : results) {
            html.append(result.htmlHighlight()).append("\n");
        }
        return html.toString();
    }
}
