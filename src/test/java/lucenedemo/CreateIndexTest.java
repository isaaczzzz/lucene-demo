package lucenedemo;

import lucenedemo.entity.Book;
import lucenedemo.service.impl.LuceneServiceImpl;
import org.junit.jupiter.api.Test;

public class CreateIndexTest {
    @Test
    void testCreateIndex() throws Exception {
        Book book = new Book();
        book.setBookId("142");
        book.setBookName("Python网络编程");
        book.setBookAuthor("John Goerzen");
        book.setBookContent("本书针对想要深入理解使用Python来解决网络相关问题或是构建网络应用程序的技术人员，结合实例讲解了网络协议、网络数据及错误、电子邮件、服务器架构和HTTP及Web应用程序等经典话题。具体内容包括：全面介绍Python3中最新提供的SSL支持，异步I/O循环的编写，用Flask框架在Python代码中配置URL，跨站脚本以及跨站请求伪造攻击网站的原理及保护方法，等等。");
        LuceneServiceImpl luceneService = new LuceneServiceImpl();
        luceneService.createIndex(book);
    }
}
