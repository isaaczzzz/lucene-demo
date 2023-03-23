package lucenedemo.entity;

import lombok.Data;

@Data
public class Page {
    private String content;
    private String bookId;
    private String bookName;
    private int pageNum;
    private float score;
}
