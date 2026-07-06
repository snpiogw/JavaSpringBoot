package sample.thymeleafweb;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sample.common.dao.entity.Task;
import sample.common.service.TaskService;
import sample.thymeleafweb.form.TaskForm;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private String getUsername(UserDetails userDetails) {
        return userDetails.getUsername();
    }
    
    private static final int PAGE_SIZE = 10;
    
    @GetMapping
    public String listTasks(@RequestParam(value = "page", defaultValue = "1") int page,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        String username = userDetails.getUsername();

        int totalCount = taskService.countByUsername(username);
        int totalPages = Math.max((int) Math.ceil((double) totalCount / PAGE_SIZE), 1);

        int safePage = Math.max(page, 1);
        safePage = Math.min(safePage, totalPages);

        int offset = (safePage - 1) * PAGE_SIZE;

        List<Task> tasks = taskService.getTasksByUsername(username, offset, PAGE_SIZE);

        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", totalPages);

        return "tasks/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("task", new TaskForm());
        return "tasks/form-new";
    }
    
    @PostMapping
    public String createTask(@Valid @ModelAttribute("task") TaskForm form,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            return "tasks/form-new";
        }

        Task task = toEntity(form, getUsername(userDetails));
        taskService.createTask(task);

        return "redirect:/tasks";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {

        String username = getUsername(userDetails);

        Task task = taskService.getTaskById(id, username);

        model.addAttribute("task", toForm(task));

        return "tasks/form-edit";
    }
    
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("task") TaskForm form,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            form.setId(id);
            return "tasks/form-edit";
        }

        Task task = toEntity(form, getUsername(userDetails));
        task.setId(id);

        taskService.updateTask(task);

        return "redirect:/tasks";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {

        taskService.deleteTask(id, getUsername(userDetails));
        return "redirect:/tasks";
    }

    private Task toEntity(TaskForm form, String username) {
        Task task = new Task();

        task.setId(form.getId());
        task.setUsername(username);
        task.setTitle(form.getTitle());
        task.setContent(form.getContent());
        task.setName(form.getName());
        task.setStartDate(form.getStartDate());
        task.setEndDate(form.getEndDate());

        return task;
    }

    private TaskForm toForm(Task task) {
        TaskForm form = new TaskForm();

        form.setId(task.getId());
        form.setTitle(task.getTitle());
        form.setContent(task.getContent());
        form.setName(task.getName());
        form.setStartDate(task.getStartDate());
        form.setEndDate(task.getEndDate());

        return form;
    }
}