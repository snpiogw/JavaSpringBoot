package sample.common.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.common.exception.TaskNotFoundException;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getTaskById_存在しない場合は例外を投げる() {
        Long id = 999L;
        String username = "testuser";

        when(taskMapper.findByIdAndUsername(id, username)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(id, username);
        });
    }

    @Test
    void deleteTask_削除件数0件なら例外を投げる() {
        Long id = 999L;
        String username = "testuser";

        when(taskMapper.deleteTask(id, username)).thenReturn(0);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(id, username);
        });
    }

    @Test
    void updateTask_更新件数0件なら例外を投げる() {
        Task task = new Task();
        task.setId(999L);
        task.setUsername("testuser");

        when(taskMapper.updateTask(task)).thenReturn(0);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(task);
        });
    }
}