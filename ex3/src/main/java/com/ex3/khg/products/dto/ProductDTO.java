package com.ex3.khg.products.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.entity.ProductImage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long pno;
    private String pname;
    private int price;
    private String content;
    private String writer;

    private List<String> imageList; // 이미지 파일 이름 배열
    public ProductDTO(ProductEntity productEntity) {
        this.pno = productEntity.getPno();
        this.pname = productEntity.getPname();
        this.price = productEntity.getPrice();
        this.content = productEntity.getContent();
        this.writer = productEntity.getWriter();
        this.imageList = productEntity.getImages()
                        .stream()
                        .map(ProductImage::getFileName)
                        .collect(Collectors.toList());
    }
}
