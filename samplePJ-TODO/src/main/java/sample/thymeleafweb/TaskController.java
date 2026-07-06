package sample.thymeleafweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sample.common.dao.entity.Task;
import sample.common.service.TaskService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ログインユーザー名を取得する最もスマートな方法
    private String getUsername(UserDetails userDetails) {
        return userDetails.getUsername();
    }
    
    @GetMapping
    public String listTasks(@RequestParam(value = "page", defaultValue = "1") int page,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        
        String username = userDetails.getUsername();
        
        // 1ページあたりの表示件数を指定 (例: 5件)
        int limit = 10; 
        
        // ページ番号からoffsetを計算 (例: 1ページ目なら0, 2ページ目なら5)
        int offset = (page - 1) * limit;
        
        // 引数を2つ（usernameとoffset）渡す
        List<Task> tasks = taskService.getTasksByUsername(username, offset);
        
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/form-new";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {

        String username = getUsername(userDetails);

        Task task = taskService.getTaskById(id, username);

        model.addAttribute("task", task);

        return "tasks/form-edit";
    }    

    @PostMapping
    public String createTask(@ModelAttribute Task task, @AuthenticationPrincipal UserDetails userDetails) {
        task.setUsername(getUsername(userDetails));
        taskService.createTask(task);
        return "redirect:/tasks";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        taskService.deleteTask(id, getUsername(userDetails));
        return "redirect:/tasks";
    }
    
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute Task task,
                             @AuthenticationPrincipal UserDetails userDetails) {

        task.setId(id);
        task.setUsername(getUsername(userDetails));

        taskService.updateTask(task);

        return "redirect:/tasks";
    }
}