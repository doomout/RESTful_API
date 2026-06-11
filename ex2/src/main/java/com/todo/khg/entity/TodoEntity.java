package com.todo.khg.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity // 해당 클래스의 인스턴스는 엔티티 객체임을 명시
@Getter // Lombok 으로 getter 메서드 자동 생성
@ToString // Lombok 으로 toString 메서드 자동 생성
@AllArgsConstructor // 모든 멤버 변수에 해당하는 매개변수를 받는 생성자
@NoArgsConstructor // 매개변수가 없는 기본 생성자
@Table(name = "tbl_todos") // 엔티티 클래스와 매핑되는 테이블 이름을 지정
@Builder // Lombok 으로 빌더 패턴을 적용하여 객체 생성 시 가독성 향상
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String writer;

    private LocalDate dueDate;

    // 변경 메서드들 - 엔티티의 상태를 변경할 때 사용
    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeWriter(String writer) {
        this.writer = writer;
    }
    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
