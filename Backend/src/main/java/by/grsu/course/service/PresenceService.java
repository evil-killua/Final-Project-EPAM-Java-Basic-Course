package by.grsu.course.service;

import by.grsu.course.model.dto.PresenceDTO;

import java.util.List;

public interface PresenceService {
    void createPresence(List<PresenceDTO> presenceDTOList);
    List<PresenceDTO> getPresenceByCourseNameAndUserName(String courseName,String userName);
    List<PresenceDTO> getPresenceByCourseName(String courseName);
    List<PresenceDTO> getPresenceByCourseNameAndTopicName(String courseName,String topicName);
}
