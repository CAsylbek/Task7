package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import task7.dto.mapper.DtoMapper;
import task7.model.Role;
import task7.model.User;
import task7.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final DtoMapper dtoMapper;

    @GetMapping()
    public String getUsers(Model model, @RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        model.addAttribute("users", dtoMapper.listToDTO(userService.findAll()));
        model.addAttribute("roles", Role.getRoles());
        model.addAttribute("updateUserId", id);
        return "users";
    }

    @PostMapping
    public String saveUser(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                           @RequestParam("username") String  username,
                           @RequestParam("password") String password,
                           @RequestParam("role") List<Role> roles) {
        userService.save(new User(id, username, password, Set.copyOf(roles)));
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
