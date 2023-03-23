package lucenedemo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Book2 {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private List<Page> bookContent;
}
