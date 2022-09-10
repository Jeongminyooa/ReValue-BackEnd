package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.project.ProjectInfoReq;
import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.entity.MaterialCategory;
import kbsc.greenFunding.entity.Project;
import kbsc.greenFunding.entity.ProjectType;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepo;

    // 프로젝트 type, category 저장
    @Transactional(rollbackFor=Exception.class)
    public Long postProjectType(Long userId, String projectType, String category) {
        try {
            // User user = userRepository.findById(userId).orElseThrow();

            Project project = Project.projectTypeBuilder()
                    .projectType(ProjectType.valueOf(projectType))
                    .category(MaterialCategory.valueOf(category))
                    .build();

            Project projectId = projectRepo.save(project);

            return projectId.getId();
        } catch(IllegalArgumentException e) {
            throw new NoEnumException("no enum", ErrorCode.NO_ENUM_CONSTANT);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public Long postProjectInfo(String title, String imageUrl, String content, Long projectId) {
       Project project = projectRepo.findById(projectId).orElseThrow();

       project.updateProjectInfo(title, imageUrl, content);

       return project.getId();
    }

}
