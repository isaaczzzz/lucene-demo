package lucenedemo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.StringReader;

public class SearchIndexTest {
    @Test
    void testSearchIndex() throws Exception{
        Analyzer analyzer = new IKAnalyzer();
        QueryParser parser = new QueryParser("bookContent", analyzer);

        Query query = parser.parse("网络");

        File indexFile = new File("src/main/resources/index");
        Directory directory = FSDirectory.open(indexFile.toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs topDocs = searcher.search(query, 10);

        QueryScorer queryScorer = new QueryScorer(query);
//        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red style=\"background:yellow\">","</font></b>");
        //根据这个得分计算出一个片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer,1500);
        //将这个片段中的关键字用上面初始化好的高亮格式高亮
        Highlighter highlighter = new Highlighter(queryScorer);
        //设置一下要显示的片段
        highlighter.setTextFragmenter(fragmenter);

        TotalHits count = topDocs.totalHits;
        System.out.println("匹配出的记录总数:" + count.toString());

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;

            Document doc = searcher.doc(docId);
            String bookContent = doc.get("bookContent");
            System.out.println("ID：" + doc.get("bookId"));
            System.out.println("名称：" + doc.get("bookName"));
            System.out.println("作者：" + doc.get("bookAuthor"));
//            System.out.println("内容：" + bookContent);
            System.out.println("==========================");
            if (bookContent != null) {
                TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(bookContent));

                String summary = highlighter.getBestFragment(tokenStream, bookContent);
                System.out.println("得分最高的的content: "+ summary);
            }
        }


        reader.close();
    }
}
