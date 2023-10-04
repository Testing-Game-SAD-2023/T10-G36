package com.sad.t5t1.project.services;

import org.springframework.web.bind.annotation.RequestParam;

public interface DBInterface {
    Integer getID(@RequestParam("email") String email, @RequestParam("password") String password);
}
