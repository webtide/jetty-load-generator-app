package com.webtide.jetty.load.generator.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController
{

    @GetMapping( "/helloworld" )
    @ResponseBody
    public String helloWorld()
    {
        return "Hello World";
    }

    @GetMapping( "/hello/{who}" )
    @ResponseBody
    public String helloWhoFromPath( @PathVariable String who)
    {
        return "Hello " + who;
    }

    @GetMapping( "/hello" )
    @ResponseBody
    public String helloWhoFromRequestPath( @RequestParam(name = "who") String who)
    {
        return "Hello " + who;
    }

}
