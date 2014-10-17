package lucenejavafx;

/**
 *
 * @author kostas
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import static lucenejavafx.Parser.parse;

import org.apache.lucene.analysis.Analyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.util.Version;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class LuceneIndex {

    public static boolean LuceneIndex() throws CorruptIndexException,
            LockObtainFailedException, IOException, Exception, FileNotFoundException {

        //Stop Words from common_words file
        File stop = new File("..\\LuceneJavaFX\\src\\common\\common_words");
        Reader aReader = new FileReader(stop);

        //Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        Analyzer analyzer = new CustomAnalyzer(Version.LUCENE_47, aReader);
        File file_index = new File(
                "..\\LuceneJavaFX\\src\\index");

        Directory directory = FSDirectory.open(file_index);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
                analyzer);
        IndexWriter writer = new IndexWriter(directory, config);

        List<Document> finalDocList;

        Iterator<Document> iterator;

        finalDocList = parse();
        iterator = finalDocList.iterator();

        while (iterator.hasNext()) {
            writer.addDocument(iterator.next());
        }

        writer.close();

        return true;

    }

}
