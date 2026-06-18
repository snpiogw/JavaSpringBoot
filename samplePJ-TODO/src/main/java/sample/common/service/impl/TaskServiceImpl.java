package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.common.service.TaskService;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> getTasksByUsername(String username) { return taskMapper.findByUsername(username, 0); }
    @Override
    public Task getTaskById(Long id, String username) { return taskMapper.findByIdAndUsername(id, username); }
    @Override
    public void createTask(Task task) { taskMapper.insertTask(task); }
    @Override
    public void updateTask(Task task) { taskMapper.updateTask(task); }
    @Override
    public void deleteTask(Long id, String username) { taskMapper.deleteTask(id, username); }
    @Override
    public List<Task> getTasksByUsername(String username, int offset) {
        return taskMapper.findByUsername(username, offset);
    }

    @Override
    public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }
}