package com.RESTfulAPI.khg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO (Data Transfer Object) 클래스는 주로 데이터 전송을 위해 사용되는 객체입니다.
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {
    private Long ssn;
    private String name;
}
