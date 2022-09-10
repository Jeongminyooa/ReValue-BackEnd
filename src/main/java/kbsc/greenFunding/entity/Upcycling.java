package kbsc.greenFunding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Upcycling {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upcycling_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Nullable
    private int price;

    private String title;

    private String description;

    // 총 제한 수량
    @Nullable
    private int totalCount;

    // 잔여 수량
    @Nullable
    private int remainingCount;

    //== 연관관계 메서드 ==//
    public void setProject(Project project) {
        this.project = project;
        project.getUpcyclingList().add(this);
    }
}
