package lucenedemo;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class SearchIndexTest {
    @Test
    void testSearchIndex() throws Exception{
        QueryParser parser = new QueryParser("bookContent", new IKAnalyzer());

        Query query = parser.parse("bookContent:数据库");


        File indexFile = new File("src/main/resources/index");
        Directory directory = FSDirectory.open(indexFile.toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs topDocs = searcher.search(query, 10);

        TotalHits count = topDocs.totalHits;
        System.out.println("匹配出的记录总数:" + count.toString());

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;

            Document doc = searcher.doc(docId);
            System.out.println("ID：" + doc.get("bookId"));
            System.out.println("名称：" + doc.get("bookName"));
            System.out.println("作者：" + doc.get("bookAuthor"));
            System.out.println("==========================");
        }

        reader.close();
    }
}
