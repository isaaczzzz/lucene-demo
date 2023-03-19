package lucenedemo.entity;

import lombok.Data;

import java.util.List;

@Data
public class LuceneBook {
    private List keywords;
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String bookContent;
}
