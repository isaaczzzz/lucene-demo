package lucenedemo;

import lucenedemo.entity.Book;
import lucenedemo.service.impl.LuceneServiceImpl;
import org.junit.jupiter.api.Test;

public class createIndexTest {
    @Test
    void testCreateIndex() throws Exception {
        Book book = new Book();
        book.setBookId("123");
        book.setBookName("数据库系统概论（第5版）");
        book.setBookAuthor("萨师煊");
        book.setBookContent("《数据库系统概论（第5版）》是王珊、萨师煊编著的“十二五”普通高等教育本科国家级规划教材，于2014年由高等教育出版社出版，可以作为高等学校计算机类专业、信息管理与信息系统等相关专业数据库课程的教材，也可供从事数据库系统研究、开发和应用的研究人员和工程技术人员参考。");
        LuceneServiceImpl luceneService = new LuceneServiceImpl();
        luceneService.createIndex(book);
    }
}
