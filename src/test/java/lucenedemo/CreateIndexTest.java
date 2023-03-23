package lucenedemo;

import lucenedemo.entity.Book;
import lucenedemo.entity.Book2;
import lucenedemo.entity.Page;
import lucenedemo.service.impl.LuceneServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateIndexTest {
    @Test
    void testCreateIndex() throws Exception {
        Book book = new Book();
        Page pages = new Page();
        book.setBookId("232");
        book.setBookName("数据通信与网络（英文版·第5版）");
        book.setBookAuthor("Behrouz A.Forouzan");
        //create pages
        pages.setBookId("232");
        pages.setBookName("数据通信与网络（英文版·第5版）");
        pages.setContent("本书延续了Forouzan一贯的风格，以通俗易懂的方式全面阐述了网络技术及其应用。本书自第1版引进国内以来，对网络课程教学产生了较大影响，被多所名校采用为教材。 本书以因特网五层模型为框架，以形象直观的描述手法，详细地介绍了数据通信和网络领域的基础知识、基本概念、基本原理和实践方法，堪称数据通信和网络方面的经典著作。虽然本版仍由七个部分组成，但是作者对书中的内容做了全面的更新和修订，调整了章节顺序，合并、挪动和新增了一些章节，删减了一些过时的主题。");

        //book.setBookContent("本书延续了Forouzan一贯的风格，以通俗易懂的方式全面阐述了网络技术及其应用。本书自第1版引进国内以来，对网络课程教学产生了较大影响，被多所名校采用为教材。 本书以因特网五层模型为框架，以形象直观的描述手法，详细地介绍了数据通信和网络领域的基础知识、基本概念、基本原理和实践方法，堪称数据通信和网络方面的经典著作。虽然本版仍由七个部分组成，但是作者对书中的内容做了全面的更新和修订，调整了章节顺序，合并、挪动和新增了一些章节，删减了一些过时的主题。");
        LuceneServiceImpl luceneService = new LuceneServiceImpl();
        luceneService.createIndex(book);
    }
    @Test
    void createPagesIndex() throws Exception{
        //print execution time in seconds
        long startTime = System.currentTimeMillis();
        List<Page> pages = new ArrayList<>();
        //create pages
        for (int i = 1; i <= 3; i++) {
            Page page = new Page();
            page.setBookId("231");
            page.setBookName("three body");
            page.setPageNum(i);
            pages.add(page);
        }
        //add contents to pages
        for (int i = 0; i < pages.size(); i++) {
            File file = new File("src/main/resources/files/test" + (i + 1) + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            pages.get(i).setContent(sb.toString());
        }
        //book.setBookContent("本书延续了Forouzan一贯的风格，以通俗易懂的方式全面阐述了网络技术及其应用。本书自第1版引进国内以来，对网络课程教学产生了较大影响，被多所名校采用为教材。 本书以因特网五层模型为框架，以形象直观的描述手法，详细地介绍了数据通信和网络领域的基础知识、基本概念、基本原理和实践方法，堪称数据通信和网络方面的经典著作。虽然本版仍由七个部分组成，但是作者对书中的内容做了全面的更新和修订，调整了章节顺序，合并、挪动和新增了一些章节，删减了一些过时的主题。");
        LuceneServiceImpl luceneService = new LuceneServiceImpl();
        for (int i = 0; i < pages.size(); i++) {
            luceneService.createIndex2(pages.get(i));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) / 1000.0 + "s");
    }
    @Test
    void createIndexByFile() throws Exception {
        Book book = new Book();
        String fileName = "src/main/resources/files/test.txt";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String id;
        String name;
        String author;
        StringBuilder content = new StringBuilder();
        String line;
        id = br.readLine();
        name = br.readLine();
        author = br.readLine();
        while((line = br.readLine()) != null) {
            content.append(line);
        }
        book.setBookId(id);
        book.setBookAuthor(author);
        book.setBookName(name);
//        book.setBookContent(content.toString());
        LuceneServiceImpl luceneService = new LuceneServiceImpl();
        luceneService.createIndex(book);
    }
}
