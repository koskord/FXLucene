
package lucenejavafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

/**
 *
 * @author kostas
 */
public class SearchIndex {

    public static ScoreDoc[] hits = new ScoreDoc[20];

    public String[] Searcher(String query) throws CorruptIndexException,
            LockObtainFailedException, IOException, ParseException, FileNotFoundException {

        System.out.println("Start Searching...");

        File stop = new File("..\\LuceneJavaFX\\src\\common\\common_words");
        Reader aReader = new FileReader(stop);

        //Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        Analyzer analyzer = new CustomAnalyzer(Version.LUCENE_47, aReader);
        Directory directory = FSDirectory.open(new File(
                "..\\LuceneJavaFX\\src\\index"));

        IndexReader inReader = DirectoryReader.open(directory);

        IndexSearcher isearcher = new IndexSearcher(inReader);

        //  Parse a simple query that searches ...
        // "Content": the default field for query terms
        // analyzer : used to find terms in the query text.
        QueryParser parser = new QueryParser(Version.LUCENE_47, "Title", analyzer);
        Query q = parser.parse(query);

        // Weight weight = searcher.createNormalizedWeight(q);
        //HERE IS TAKING PLACE THE SEARCHING
        TopDocs topDocs = isearcher.search(q, 10);

        // searcher.createNormalizedWeight(q);
        //TopScoreDocCollector collector = TopScoreDocCollector.create(40, true);
        //public static ScoreDoc[] hits = new ScoreDoc[10];
        hits = topDocs.scoreDocs;

        long end = System.currentTimeMillis();

        //String Array with length same with the number of hits
        String[] resultsCon = new String[hits.length];
        //CHECKING
        System.out.println("Results of search: " + hits.length);

        Document hitDoc = new Document();

        int a = 0;
        for (int i = 0; i < hits.length; i++) {

            //hits[i].doc : a hit document's number
            //isearcher.doc() : Returns the stored fields of the n'th Document in this index. 
            hitDoc = isearcher.doc(hits[i].doc);

            resultsCon[i] = hitDoc.get("Title");

            a++;

        }
        //CHECKING
        //System.out.println(resultsCon[a]);
        System.out.println("Searcher Loop: " + a + " times");

        directory.close();
        inReader.close();

        return resultsCon;
    }

    public static List<IndexableField> retrieve(int index) throws FileNotFoundException, IOException {

        Directory directory = FSDirectory.open(new File(
                "..\\LuceneJavaFX\\src\\index"));

        IndexReader inReader = DirectoryReader.open(directory);

        IndexSearcher isearcher = new IndexSearcher(inReader);

        Document uniqDoc = new Document();
        uniqDoc = isearcher.doc(hits[index].doc);

        return uniqDoc.getFields();

    }

}
