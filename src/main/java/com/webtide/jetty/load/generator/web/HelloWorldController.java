package com.webtide.jetty.load.generator.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HelloWorldController
{

    private static final Logger LOGGER = LoggerFactory.getLogger( HelloWorldController.class );

    @GetMapping( "/helloworld" )
    @ResponseBody
    public String helloWorld()
    {
        return "Hello World";
    }

    @GetMapping( "/hello/{who}" )
    @ResponseBody
    public String helloWhoFromPath( @PathVariable String who )
    {
        return "Hello " + who;
    }

    @GetMapping( "/hello" )
    @ResponseBody
    public String helloWhoFromRequestPath( @RequestParam( name = "who" ) String who )
    {
        return "Hello " + who;
    }


    @PutMapping( "/upload" )
    public String uploadFile( @RequestParam( "file" ) MultipartFile file, RedirectAttributes redirectAttributes )
        throws Exception
    {
        redirectAttributes.addAttribute( "message", //
                                         "uploaded:" + file.getOriginalFilename() + ",size:" + file.getBytes().length );

        return "redirect:/{message}";
    }

}
