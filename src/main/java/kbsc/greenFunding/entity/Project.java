package kbsc.greenFunding.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @OneToMany(mappedBy = "project")
    private List<Upcycling> upcyclingList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int amount; //목표 금액

    @Enumerated(EnumType.STRING)
    private MaterialCategory category;

    //== 연관관계 메서드==//
    public void addUpcyclingList(Upcycling upcycling) {
        upcyclingList.add(upcycling);
        upcycling.setProject(this);
    }

    @Builder(builderClassName = "projectTypeBuilder", builderMethodName="projectTypeBuilder")
    public Project(ProjectType projectType, MaterialCategory category) {
        this.projectType = projectType;
        this.category = category;
    }
}
