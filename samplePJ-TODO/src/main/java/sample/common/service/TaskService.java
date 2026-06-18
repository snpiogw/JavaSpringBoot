package sample.common.service;
import sample.common.dao.entity.Task;
import java.util.List;

public interface TaskService {
    List<Task> getTasksByUsername(String username);
    
    Task getTaskById(Long id, String username);
    List<Task> getTasksByUsername(String username, int offset);
    int countByUsername(String username);
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id, String username);
}