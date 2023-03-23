package lucenedemo.service.impl;

import lucenedemo.entity.Book;
import lucenedemo.entity.Book2;
import lucenedemo.entity.LuceneBook;
import lucenedemo.entity.Page;
import lucenedemo.service.LuceneService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.List;

public class LuceneServiceImpl implements LuceneService {

    private static final Logger logger = LoggerFactory.getLogger(LuceneServiceImpl.class);
    @Override
    public List<LuceneBook> getKeyword(String keyword) throws Exception {
        return null;
    }

    @Override
    public void createIndex(Book book) throws Exception {
        Document document = new Document();
        Field bookId = new StringField("bookId", book.getBookId(), Field.Store.YES);
        Field bookName = new StringField("bookName", book.getBookName(), Field.Store.YES);
        Field bookAuthor = new StringField("bookAuthor", book.getBookAuthor(), Field.Store.YES);
        Field bookContent = new TextField("bookContent", book.getBookContent(), Field.Store.YES);


        document.add(bookId);
        document.add(bookName);
        document.add(bookAuthor);
        document.add(bookContent);

        Analyzer analyzer = new IKAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        File indexFile = new File("src/main/resources/index");
        Directory directory = FSDirectory.open(indexFile.toPath());
        IndexWriter writer = new IndexWriter(directory, config);

        writer.addDocument(document);

        writer.close();
    }

    @Override
    public void createIndex2(Page page) throws Exception {
        Document document = new Document();
        Field bookId = new StringField("bookId", page.getBookId(), Field.Store.YES);
        Field bookName = new StringField("bookName", page.getBookName(), Field.Store.YES);
        Field bookContent = new TextField("bookContent", page.getContent(), Field.Store.YES);

        Field pageNum = new StringField("pageNum", String.valueOf(page.getPageNum()), Field.Store.YES);


        document.add(bookId);
        document.add(bookName);
        document.add(bookContent);
        document.add(pageNum);

        Analyzer analyzer = new IKAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        File indexFile = new File("src/main/resources/index");
        Directory directory = FSDirectory.open(indexFile.toPath());
        IndexWriter writer = new IndexWriter(directory, config);

        writer.addDocument(document);

        writer.close();

    }


}
