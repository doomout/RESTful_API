package com.ex3.khg.product.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.dto.ProductListDTO;
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

    // @Test // 즉시 로딩 테스트(인터페이스 변경으로 인해 주석처리)
    // public void TestReadQuery() {
    //     Long pno = 1L;
    //     Optional<ProductEntity> result = productRepository.getProduct(pno); //인터페이스에 정의한 getProduct() 메서드 호출
    //     ProductEntity productEntity = result.get();

    //     System.out.println(productEntity);
    //     System.out.println("-----------------------------");
    //     System.out.println(productEntity.getImages());
    // }

    // @Test // 상품 수정 테스트(인터페이스 변경으로 인해 주석처리)
    // @Transactional
    // @Commit
    // public void testUpdate() {
    //     Long pno = 1L;
    //     Optional<ProductEntity> result = productRepository.getProduct(pno);
    //     ProductEntity productEntity = result.get();

    //     productEntity.changeTitle("변경된 상품");
    //     productEntity.changePrice(10000);
    //     productEntity.addImage("new1.jpg");
    //     productEntity.addImage("new2.jpg");

    //     // 변경 감지시에는 필요없음 
    //     //productRepository.save(productEntity);
    // }

    @Test // 상품 삭제 테스트
    @Transactional
    @Commit
    public void testDelete() {
        Long pno = 1L;
        productRepository.deleteById(pno);
    }

    @Test // ProductDTO 반환 테스트
    public void testReadDTO() {
        Long pno = 1L;
        Optional<ProductDTO> result = productRepository.getProductDTO(pno);
        ProductDTO productDTO = result.get();

        System.out.println(productDTO);
    }

    @Test // join fetch 테스트
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<ProductListDTO> result = productRepository.list(pageable);
        
        result.getContent().forEach(productListDTO -> { 
            System.out.println(productListDTO); 
        });
    }

    @Test // All Images 조회 테스트
    @Transactional
    public void testListWithAllImages() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<ProductDTO> result = productRepository.listWithAllImages(pageable);
        
        result.getContent().forEach(productDTO -> {
            System.out.println(productDTO);
        });
    }

    @Test // Fetch Join 테스트 코드
    public void testListFetchAllImages() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<ProductDTO> result = productRepository.listFetchAllImages(pageable);
    }
}
