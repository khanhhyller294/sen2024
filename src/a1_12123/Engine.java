package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {

    private List<Doc> docs;

    public Engine() {
        this.docs = new ArrayList<>();
    }

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                try (Scanner reader = new Scanner(file)) {
                    StringBuilder content = new StringBuilder();
                    while (reader.hasNextLine()) {
                        content.append(reader.nextLine()).append("\n");
                    }
                    Doc doc = new Doc(content.toString().trim());
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
                Result result = new Result(doc, matches);
                results.add(result);
            }
        }
        results.sort(Result::compareTo);
        return results;
    }

    public String htmlResult(List<Result> results) {
        if (results.isEmpty()) {
            return "";
        }
        StringBuilder resultHTML = new StringBuilder();
        for (Result result : results) {
            resultHTML.append(result.htmlHighlight()).append("\n");
        }
        return resultHTML.toString().trim();
    }
}
