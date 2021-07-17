package com.runcoding.controller.account;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account/admin/api")
@Api(value = "account_admin", description = "账号管理")
public class AccountAdminController {

    private static Logger logger = LoggerFactory.getLogger(AccountAdminController.class);



}
