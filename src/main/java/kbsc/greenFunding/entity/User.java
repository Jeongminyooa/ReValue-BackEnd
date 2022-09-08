package kbsc.greenFunding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "id") // 변경
    private String userId;

    private String pw;

    private String tel;

    private boolean agreement;

    private LocalDateTime signDate;

}
