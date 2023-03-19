package lucenedemo.entity;

import lombok.Data;

@Data
public class Book {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String bookContent;
}
