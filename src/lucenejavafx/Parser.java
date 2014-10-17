
package lucenejavafx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

/**
 *
 * @author kostas
 */
public class Parser {

    private static class Fields {

        private static final char PREFIX = '.';
        private static final char AUTHORS = 'A';
        private static final char DATE = 'B';
        private static final char CONTENT = 'C';
        private static final char ID = 'I';
        private static final char KEYWORDS = 'K';
        private static final char ENTRYDATE = 'N';
        private static final char TITLE = 'T';
        private static final char ABSTRACT = 'W';
        private static final char REFERENCE = 'X';
    }

    public static List<Document> parse() throws CorruptIndexException,
            LockObtainFailedException, IOException, Exception, FileNotFoundException {

        System.out.println("Start Parsing...");

        FileReader text = new FileReader("..\\LuceneJavaFX\\src\\lucenejavafx\\cacm.all");
        BufferedReader br = new BufferedReader(text);

        List<Document> results_list = new ArrayList<Document>();

        String line = "a";
        String fname = "";
        char state = 0;

        int x = 0;

        line = br.readLine();

        if (line.charAt(0) == Fields.PREFIX) {
            state = line.charAt(1);
        }

        while (state == Fields.ID) {
            fname = " ";

            System.out.println("Loop");
            // opote vro .I new doc
            //grafo sto doc
            //add sti List
            //epistrefo tin list
            if ((line = line.trim()).isEmpty()) {
                System.out.println("Continue if...");
                continue;
            }

            x++;
            System.out.println("NEW " + x);
            Document doc = new Document();

            while ((line = br.readLine()) != null) {
                
                
                
                if (line.charAt(0) == Fields.PREFIX) {
                    state = line.charAt(1);
                }
                if (state == Fields.ID) {
                    System.out.println("BREAK!");
                    break;
                }

                if (state == Fields.ABSTRACT) {
                    fname = "Abstract";
                }
                if (state == Fields.TITLE) {
                    line= br.readLine();
                    fname = "Title";
                }
                if (state == Fields.AUTHORS) {

                    fname = "Authors";

                }

                if (state == Fields.DATE) {
                    continue;
                }

                if (state == Fields.CONTENT) {
                    continue;
                }

                if (state == Fields.KEYWORDS) {
                    continue;
                }

                if (state == Fields.ENTRYDATE) {
                    continue;
                }

                if (state == Fields.REFERENCE) {
                    continue;
                }

                doc.add(new Field(fname, line, TextField.TYPE_STORED));
                System.out.println("Field name " + fname + "  Line: " + line);

            }

            results_list.add(doc);
            System.out.println("Lucene doc just appear!");

        }
        return results_list;

    }

}
