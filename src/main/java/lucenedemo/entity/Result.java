package lucenedemo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    private List<Page> pages;
    private Float score;
}
