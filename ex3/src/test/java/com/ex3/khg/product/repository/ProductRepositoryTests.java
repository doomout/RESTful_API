package com.ex3.khg.product.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test // 상품 1개 + 이미지 2개 등록 테스트
    @Transactional
    @Commit
    public void testInsert() {
        for(int i = 1; i <= 50; i++) {
            ProductEntity productEntity = ProductEntity.builder()
                    .pname(i + "_새로운 상품")
                    .price(5000)
                    .content(i + "_상품 설명")
                    .writer("user00")
                    .build();

            productEntity.addImage(i + "_test1.jpg");
            productEntity.addImage(i + "_test2.jpg");

            productRepository.save(productEntity);

            System.out.println("New Product no: " + productEntity.getPno());
        }
    }

    @Test // 지연 로딩 테스트
    @Transactional(readOnly = true)
    public void testRead() {
        Long pno = 1L;
        Optional<ProductEntity> result = productRepository.findById(pno);
        ProductEntity productEntity = result.get();

        System.out.println(productEntity);
        System.out.println("-----------------------------");
        System.out.println(productEntity.getImages());
    }

    @Test // 즉시 로딩 테스트
    public void TestReadQuery() {
        Long pno = 1L;
        Optional<ProductEntity> result = productRepository.getProduct(pno); //인터페이스에 정의한 getProduct() 메서드 호출
        ProductEntity productEntity = result.get();

        System.out.println(productEntity);
        System.out.println("-----------------------------");
        System.out.println(productEntity.getImages());
    }
}
