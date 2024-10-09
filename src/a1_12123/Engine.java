package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private List<Doc> docs;

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;
        for (File file : files) {
            String[] lines = new String[2];
            String content = "";
            if (file.isFile()) {
                try {
                    Scanner reader = new Scanner(file);
                    // add two lines into content of the doc
                    while (reader.hasNext()) {
                        for (int i = 0; i < lines.length; i++) {
                            lines[i] = reader.nextLine();
                        }
                    }
                    content += lines[0] + "\n" + lines[1];
                    Doc doc = new Doc(content);
                    docs.add(doc);
                    count++;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
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