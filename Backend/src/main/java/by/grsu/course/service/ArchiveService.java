package by.grsu.course.service;

import by.grsu.course.model.Course;
import by.grsu.course.model.dto.ArchiveDTO;
import by.grsu.course.model.dto.CourseDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ArchiveService {
    List<ArchiveDTO> getAllArchive();
    ArchiveDTO getArchiveById(long id);
    void deleteArchive(long id);
    void updateArchive(List<ArchiveDTO> archiveDTO);
    void addNewArchive(ArchiveDTO archiveDTO);
    void deleteArchiveByCourseAndUserName(String courseName, String username);
    List<ArchiveDTO> getArchiveByCourseName(String courseName);
    ArchiveDTO getArchiveByCourseNameAndUserName(String courseName, String username);
}
