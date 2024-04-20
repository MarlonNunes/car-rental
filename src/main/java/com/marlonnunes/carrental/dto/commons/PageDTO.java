package com.marlonnunes.carrental.dto.commons;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.function.Function;

@Data
public class PageDTO<T> {
    private Collection<T> content;

    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;


    public static <T, R> PageDTO<T> buildFromPage(Page<R> page, Function<R, T> transformer){
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.page = page.getNumber();
        pageDTO.pageSize = page.getSize();
        pageDTO.totalPages = page.getTotalPages();
        pageDTO.totalElements = page.getTotalElements();
        pageDTO.content = page.getContent().stream().map(transformer).toList();

        return pageDTO;
    }
}
