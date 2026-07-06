package sample.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.common.exception.TaskNotFoundException;
import sample.common.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByUsername(String username, int offset) {
        return taskMapper.findByUsername(username, offset);
    }

    @Override
    @Transactional(readOnly = true)
    public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id, String username) {
        Task task = taskMapper.findByIdAndUsername(id, username);

        if (task == null) {
            throw new TaskNotFoundException(id);
        }

        return task;
    }

    @Override
    public void createTask(Task task) {
        taskMapper.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        int count = taskMapper.updateTask(task);

        if (count == 0) {
            throw new TaskNotFoundException(task.getId());
        }
    }

    @Override
    public void deleteTask(Long id, String username) {
        int count = taskMapper.deleteTask(id, username);

        if (count == 0) {
            throw new TaskNotFoundException(id);
        }
    }
}