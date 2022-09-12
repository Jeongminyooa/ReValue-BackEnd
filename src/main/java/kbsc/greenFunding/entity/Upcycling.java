package kbsc.greenFunding.entity;

<<<<<<< HEAD
import lombok.*;
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
>>>>>>> main
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Builder
<<<<<<< HEAD
@NoArgsConstructor
=======
@RequiredArgsConstructor
>>>>>>> main
@AllArgsConstructor
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

<<<<<<< HEAD
    /*
    @Builder(builderClassName = "upcyclingBuilder", builderMethodName="upcyclingBuilder")
    public Upcycling(String title, int price, String description, int totalCount) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.totalCount = totalCount;
    }
     */

    public void updateRemainingCount(int count) {
        this.remainingCount -= count;
    }
=======
>>>>>>> main
}
