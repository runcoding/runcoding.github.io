package com.runcoding.sso.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author runcoding
 * https://www.cnblogs.com/harrychinese/p/SpringBoot_security1.html
 */
@RestController
public class FooController {

    @PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('read')")
    @GetMapping("/foos/{id}")
    public Foo findById(@PathVariable final long id) {
        return new Foo(System.currentTimeMillis(),"西瓜");
    }


    @PreAuthorize("#oauth2.hasScope('openid')")
    @PostMapping("/foos")
    public Foo create(@RequestBody final Foo foo) {
        foo.setId(System.currentTimeMillis());
        return foo;
    }

    @PreAuthorize("hasRole('ROLE_USER') AND hasRole('ROLE_ADMIN')")
    @PostMapping("/addFoo")
    public Foo addFoo(Foo foo){
        return new Foo(System.currentTimeMillis(),"addFoo()");
    }


    @PreAuthorize ("#foo.name == authentication.name")
    @PutMapping("putFoo")
    public Foo deleteFoo(@RequestBody Foo foo){
        return new Foo(System.currentTimeMillis(),"putFoo()");
    }

    /**
     * 返回值
     * */
    @PostAuthorize("returnObject.name == authentication.name")
    @GetMapping("/get/foo")
    public Foo getFoo(){
        return new Foo(System.currentTimeMillis(),"runcoding");
    }

}
