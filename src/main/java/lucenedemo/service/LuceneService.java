package lucenedemo.service;

import lucenedemo.entity.Book;
import lucenedemo.entity.Book2;
import lucenedemo.entity.LuceneBook;
import lucenedemo.entity.Page;

import java.util.List;

public interface LuceneService {
    List<LuceneBook> getKeyword(String keyword) throws Exception;
    void createIndex(Book book) throws Exception;
    void createIndex2(Page page) throws Exception;
}
