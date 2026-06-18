package sample.thymeleafweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sample.common.dao.entity.Task;
import sample.common.service.TaskService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private String getUsername(HttpSession session) {
        return (String) session.getAttribute("username");
    }

    @GetMapping
    public String listTasks(@RequestParam(name = "page", defaultValue = "0") int page, HttpSession session, Model model) {
        String username = getUsername(session);
        if (username == null) return "redirect:/login";

        int limit = 10;
        int offset = page * limit;

        List<Task> tasks = taskService.getTasksByUsername(username, offset);
        
        int totalTasks = taskService.countByUsername(username);
        int totalPages = (int) Math.ceil((double) totalTasks / limit);

        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        
        return "tasks/list";
    }

    @GetMapping("/new")
    public String showNewForm(HttpSession session, Model model) {
        if (getUsername(session) == null) return "redirect:/login";
        model.addAttribute("task", new Task());
        return "tasks/form-new";
    }

    @PostMapping
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        String username = getUsername(session);
        if (username == null) return "redirect:/login";
        task.setUsername(username);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, HttpSession session, Model model) {
        String username = getUsername(session);
        if (username == null) return "redirect:/login";
        Task task = taskService.getTaskById(id, username);
        model.addAttribute("task", task);
        return "tasks/form-edit";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute Task task, HttpSession session) {
        String username = getUsername(session);
        if (username == null) return "redirect:/login";
        task.setId(id);
        task.setUsername(username);
        taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id, HttpSession session) {
        String username = getUsername(session);
        if (username == null) return "redirect:/login";
        taskService.deleteTask(id, username);
        return "redirect:/tasks";
    }
}