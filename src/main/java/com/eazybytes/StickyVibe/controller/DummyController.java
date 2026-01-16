package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.UserDto;
import jakarta.validation.constraints.Size;
import org.springframework.http.RequestEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

import java.util.Map;

@RestController
@RequestMapping("api/v1/dummy")
@Validated
public class DummyController
{
    @PostMapping("/create-user")
    public String createUser(@RequestBody UserDto userDto)
    {
        System.out.println(userDto);
        return "User Created SuccessFully";
    }

    @PostMapping("/request-entity")
    public String createUserWithEntity(RequestEntity<UserDto> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();  // This will now use Spring's HttpHeaders
        UserDto userDto = requestEntity.getBody();
        String queryStirng =  requestEntity.getUrl().getPath();
        return "User Created Successfully with headers: " + headers;
    }


    @GetMapping("/search")
    public String searchUser(@Size(min = 3, max = 30) @RequestParam(required = false, defaultValue = "Guest", name = "name") String userName)
    {
        return "Searching for User : " + userName;
    }

    @GetMapping("/multiple-search")
    public String multipleSearch(@RequestParam Map<String, String> params)
    {
        return "Searching for User : " + params.get("firstName") + params.get("lastName");
    }

    @GetMapping({"/user/{userId}/posts/{postId}", "/user/{userId}"})
    public String getUser(@PathVariable(name = "userId") String id, @PathVariable(required = false) String postId)
    {
        return "Searching for User : " + id + " Post Id = " + postId;
    }

    @GetMapping({"/user/map/{userId}/posts/{postId}", "/user/map/{userId}"})
    public String getUserUsingMap(@PathVariable Map<String, String> pathVar)
    {
        return "Searching for User : " + pathVar.get("userId") + " Post Id = " + pathVar.get("postId");
    }

    @GetMapping("/headers")
    public String readHeaders(@RequestHeader HttpHeaders headers)
    {
        return "Received Headers with Value : " + headers.get("User-Agent") + " : " + headers.get("UserLocation");
    }

}
