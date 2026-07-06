package sample.common.service;

import java.util.List;
import sample.common.dao.entity.Task;

public interface TaskService {
    // 実装クラス(TaskServiceImpl)が持っているメソッドを「宣言」する場所です
	List<Task> getTasksByUsername(String username, int offset, int limit);
	int countByUsername(String username);
    Task getTaskById(Long id, String username);
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id, String username);
}