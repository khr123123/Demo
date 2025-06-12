package org.khr.proxydemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KK
 * @create 2025-06-12-15:01
 */
@RestController
public class MyController {

    @Inject
    private MyService myService;


    @RequestMapping("/hello")
    public String hello(String name) {
        return myService.hello(name);
    }


}
