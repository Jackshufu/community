package com.community.controller;

import com.community.dto.NotificationDTO;
import com.community.enums.NotificationTypeEnum;
import com.community.model.User;
import com.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 舒先亮 on 2019/9/23.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        User userFindByToken = (User) request.getSession().getAttribute("userFindByToken");
        if (userFindByToken == null) {
            return "index";
        }
        NotificationDTO notificationDTO = notificationService.readNotification(id, userFindByToken);
        if (NotificationTypeEnum.REPLAY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else {
            return "redirect:/index";
        }


    }
}
