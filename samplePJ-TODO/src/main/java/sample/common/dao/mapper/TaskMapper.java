package sample.common.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sample.common.dao.entity.Task;
import java.util.List;

@Mapper
public interface TaskMapper {
    int countByUsername(@Param("username") String username);

    List<Task> findByUsername(@Param("username") String username, @Param("offset") int offset);

    Task findByIdAndUsername(@Param("id") Long id, @Param("username") String username);
    void insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(@Param("id") Long id, @Param("username") String username);
}