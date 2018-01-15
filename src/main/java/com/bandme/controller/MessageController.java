package com.bandme.controller;

import com.bandme.model.Message;
import com.bandme.model.User;
import com.bandme.repository.UserRepository;
import com.bandme.service.MessageService;
import com.bandme.service.PostServiceImpl;
import com.bandme.service.UserService;
import com.bandme.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping("/{page}")
    public String getAllMessages(Model model, @PathVariable("page") int page) {
        long totalMessages = messageService.countAll();
        long lastPage = totalMessages/ PostServiceImpl.PAGE_SIZE;
        if(totalMessages<PostServiceImpl.PAGE_SIZE){
            lastPage=1;
        }
        if(page>lastPage && totalMessages>=PostServiceImpl.PAGE_SIZE){
            return "redirect:/message/"+(lastPage);
        }

        //page-1 because Spring uses 0 as first page index but we dont want the user to see that
        model.addAttribute("messages", messageService.findAllLimited(page-1));
        model.addAttribute("currentPage", page);
        model.addAttribute("lastPage", lastPage);
        return "messages";
    }

    @GetMapping("new/{toUserId}")
    public String newMessage(Model model, @PathVariable("toUserId") Long toUserId){
        Message message = new Message();
        User user = userService.findUserById(toUserId);
        if(user==null){
            return "redirect:/error?userNotFound";
        }
        message.setToUser(user);
        model.addAttribute("message", message);
        return "newMessage";
    }

    @PostMapping("new")
    public String newMessage(Model model, @Valid Message message){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        message.setFromUser(userService.findUserByEmail(auth.getName()));
        messageService.saveMessage(message);
        return "redirect:/message/new/"+message.getToUser().getId()+"?messageSent";
    }

}
