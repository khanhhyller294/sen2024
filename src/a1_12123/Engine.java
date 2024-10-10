package a1_2201140044;

import java.util.*;
import java.io.*;

public class Engine {
    private List<Doc> documents;

    public Engine() {
        documents = new ArrayList<>();
    }

    public int loadDocs(String dirname) {
        File dir = new File(dirname);
        if (!dir.exists() || !dir.isDirectory()) {
            return 0;
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        if (files == null) {
            return 0;
        }

        for (File file : files) {
            try {
                StringBuilder contentBuilder = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        contentBuilder.append(line).append("\n");
                    }
                }
                String content = contentBuilder.toString().trim();
                Doc doc = new Doc(content);
                documents.add(doc);
            } catch (IOException e) {
            }
        }

        return documents.size();
    }

    public Doc[] getDocs() {
        return documents.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        if (q == null) {
            return results;
        }

        for (Doc doc : documents) {
            List<Match> matches = q.matchAgainst(doc);
            if (!matches.isEmpty()) {
                Result result = new Result(doc, matches);
                results.add(result);
            }
        }

        Collections.sort(results);
        return results;
    }

    public String htmlResult(List<Result> results) {
        Scanner sc = new Scanner(System.in);
        StringBuilder htmlresult = new StringBuilder();
        int string;
        for(Result result:results){
            double res;
            htmlresult.append(result.htmlHighlight());
        }
        return htmlresult.toString();
    }

}
