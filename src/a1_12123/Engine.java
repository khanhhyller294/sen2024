package engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
    private List<Doc> docs;

    public int loadDocs(String dirname) {
        docs = new ArrayList<>();
        File directory = new File(dirname);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try
                    {
                        docs.add(new Doc(file.getAbsolutePath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
        Collections.sort(results);
        return results;
    }

    public String htmlResult(List<Result> results)
    {
        StringBuilder html = new StringBuilder();
        for (Result result : results) {
            html.append(result.htmlHighlight()).append("<br><br>");

        }
        return html.toString();
    }
}