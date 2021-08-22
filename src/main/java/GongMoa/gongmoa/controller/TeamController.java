package GongMoa.gongmoa.controller;

import GongMoa.gongmoa.OAuth2.LoginUser;
import GongMoa.gongmoa.OAuth2.SessionUser;
import GongMoa.gongmoa.OAuth2.User;
import GongMoa.gongmoa.domain.*;
import GongMoa.gongmoa.domain.Contest.Contest;
import GongMoa.gongmoa.service.ContestService;
import GongMoa.gongmoa.service.NotificationService;
import GongMoa.gongmoa.service.TeamService;
import GongMoa.gongmoa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ContestService contestService;

    @PostMapping
    public String createTeam(@LoginUser SessionUser user, @RequestParam("notificationId") long notificationId) {

        User currentUser = userService.findUser(user.getId());
        Notification notification = notificationService.findNotification(notificationId);

        Contest contest = contestService.findContest(notification.getContest().getId());

        Long teamId = teamService.createTeam(currentUser, contest, notification);

        return "redirect:/teams/" + teamId;
    }

    @GetMapping("/{teamId}")
    public String team(@PathVariable long teamId, Model model) {
        Team team = teamService.findTeam(teamId);
        model.addAttribute("team", team);
        return "team";
    }
  
    @DeleteMapping("/{teamId}")
    public String deleteTeam(@PathVariable long teamId) {
        Team team = teamService.findTeam(teamId);

        teamService.deleteTeam(team);

        return "redirect:/contests";
    }

    @PostMapping("/{teamId}")
    public String join(@PathVariable long teamId,
                       @RequestParam long userId) {
        Team team = teamService.findTeam(teamId);
        User user = userService.findUser(userId);

        teamService.join(user, team);

        return "redirect:/teams/{teamId}";
    }

    @PostMapping("/{teamId}/leave")
    public String leaveTeam(
            @LoginUser SessionUser user,
            @PathVariable long teamId) {
        Team team = teamService.findTeam(teamId);

        teamService.leaveTeam(user.getId(), team);

        return "redirect:/teams/{teamId}";
    }
}