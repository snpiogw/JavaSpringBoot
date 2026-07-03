package sample.common.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("タスクが見つかりません: id=" + id);
    }
}