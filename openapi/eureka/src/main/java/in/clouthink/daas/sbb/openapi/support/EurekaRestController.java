package in.clouthink.daas.sbb.openapi.support;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaRestController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "welcome to openapi";
    }

    @RequestMapping("/info")
    @ResponseBody
    public String info() {
        return "openapi";
    }

    @RequestMapping("/health")
    @ResponseBody
    public String health() {
        return "ok";
    }

}
