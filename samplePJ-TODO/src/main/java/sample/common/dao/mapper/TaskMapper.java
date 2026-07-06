package sample.common.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // これをインポート
import sample.common.dao.entity.Task;

@Mapper
public interface TaskMapper {
    // @Param を付けることで、XML側の #{username}, #{offset} と紐付きます
    List<Task> findByUsername(@Param("username") String username, @Param("offset") int offset);
    
    // 他のメソッドも同様に必要に応じて @Param を付けます
    int countByUsername(@Param("username") String username);
    
    Task findByIdAndUsername(@Param("id") Long id, @Param("username") String username);
    
    void insertTask(Task task);
    int updateTask(Task task);
    int deleteTask(@Param("id") Long id, @Param("username") String username);
}