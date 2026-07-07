package sample.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.common.dao.entity.Task;

public interface TaskMapper {

    List<Task> findByUsername(@Param("username") String username,
                              @Param("offset") int offset,
                              @Param("limit") int limit);

    Task findByIdAndUsername(@Param("id") Long id,
                             @Param("username") String username);

    void insertTask(Task task);

    int updateTask(Task task);

    int deleteTask(@Param("id") Long id,
                   @Param("username") String username);

    int countByUsername(@Param("username") String username);
}