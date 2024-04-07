package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.book;

import lombok.Getter;
import lombok.Setter;

public class AddBookResponseDto {
    @Setter
    @Getter
    private Long id;
    private int copies;

    public AddBookResponseDto(Long id, int copies) {
        this.id = id;
        this.copies = copies;
    }

}
