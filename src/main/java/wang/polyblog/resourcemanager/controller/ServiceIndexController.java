package wang.polyblog.resourcemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
public class ServiceIndexController {
    @RequestMapping("/index")
    public String serviceIndex() {
        return "/resourceManage";
    }
}
