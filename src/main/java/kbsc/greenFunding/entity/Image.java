package kbsc.greenFunding.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String fileUrl;

    @Builder
    public Image(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    //== 연관관계 메서드==//
    public void setProject(Project project) {
        this.project = project;

        if(!project.getImageList().contains(this)) {
            project.getImageList().add(this);
        }
    }
}
