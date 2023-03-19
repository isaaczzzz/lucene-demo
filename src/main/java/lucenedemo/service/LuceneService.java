package lucenedemo.service;

import lucenedemo.entity.Book;
import lucenedemo.entity.LuceneBook;

import java.util.List;

public interface LuceneService {
    List<LuceneBook> getKeyword(String keyword) throws Exception;
    void createIndex(Book book) throws Exception;
}
