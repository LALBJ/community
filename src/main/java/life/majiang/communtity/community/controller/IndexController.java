package life.majiang.communtity.community.controller;

import life.majiang.communtity.community.dto.QuestionDTO;
import life.majiang.communtity.community.mapper.QuestionMapper;
import life.majiang.communtity.community.mapper.UserMapper;
import life.majiang.communtity.community.model.Question;
import life.majiang.communtity.community.model.User;
import life.majiang.communtity.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1")Integer page,
                        @RequestParam(name = "size", defaultValue = "5")Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0)
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findBytoken(token);
                if (user != null ){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        //questionService = new QuestionService();

        List<QuestionDTO> questionList = questionService.list(page,size);
        model.addAttribute("questions",questionList);

        return "index";
    }
}
