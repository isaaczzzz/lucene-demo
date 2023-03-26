package lucenedemo;

import lucenedemo.entity.Page;
import lucenedemo.entity.Result;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SearchIndexTest {
    @Test
    void testSearchIndex() throws Exception{
        long start = System.currentTimeMillis();
        Analyzer analyzer = new IKAnalyzer();
        QueryParser parser = new QueryParser("bookContent", analyzer);

        Query query = parser.parse("epigenetic");

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
        //using hashmap to store the result of every individual book
        HashMap<String,Result> results = new HashMap<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;

            Document doc = searcher.doc(docId);
            String bookContent = doc.get("bookContent");
            String bookId = doc.get("bookId");
            String bookName = doc.get("bookName");
            String pageNum = doc.get("pageNum");
            Float score = scoreDoc.score;

            System.out.println("ID：" + bookId);
            System.out.println("名称：" + bookName);
            //System.out.println("作者：" + doc.get("bookAuthor"));
            System.out.println("页码："+pageNum);
            System.out.println(score);
//            System.out.println("内容：" + bookContent);
            System.out.println("==========================");
            if (bookContent != null) {
                TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(bookContent));
                String summary = highlighter.getBestFragment(tokenStream, bookContent);
                System.out.println("得分最高的的content: " + summary);

                if (results.containsKey(bookId)) {
                    Result result = results.get(bookId);
                    List<Page> list = result.getPages();
                    Page page = new Page();
                    page.setPageNum(Integer.parseInt(pageNum));
                    page.setContent(summary);
                    page.setBookId(bookId);
                    page.setScore(score);
                    list.add(page);
                    result.setPages(list);
                    Float score1 = result.getScore();
                    result.setScore(score1 + score);
                    results.put(bookId, result);
                } else {
                    Result result = new Result();
                    List<Page> list = new ArrayList<>();
                    Page page = new Page();
                    page.setPageNum(Integer.parseInt(pageNum));
                    page.setContent(summary);
                    page.setBookId(bookId);
                    page.setScore(score);
                    list.add(page);
                    result.setPages(list);
                    result.setScore(score);
                    results.put(bookId, result);
                }
            }
            System.out.println("==========================");
        }


        Set<String> ks = results.keySet();

        //sort the result by score
        List<Result> list = new ArrayList<>();
        for (String key : ks) {
            list.add(results.get(key));
        }
        list.sort((o1, o2) -> {
            return o2.getScore().compareTo(o1.getScore());
        });

        for (Result result : list) {
            System.out.println("bookId: "+result.getPages().get(0).getBookId());
            System.out.println("score: "+result.getScore());
            for (Page page : result.getPages()) {
                System.out.println("    pageNum: "+page.getPageNum());
                System.out.println("    score: "+page.getScore());
            }
            System.out.println("==========================");
        }
        reader.close();
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) / 1000.0 + "s");

    }
}
