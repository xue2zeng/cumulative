package me.xue.encrypt.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Slf4j
@Api(value = "/Base64Controller", description = "Base64加解密JDK8实现")
@RestController
public class Base64Controller {

    @ApiOperation(value = "获取Base64加密字符", notes = "JDK自带实现Base64加密算法")
    @ApiImplicitParam(name = "text", value = "加密文本", dataType = "String", paramType = "query")
    @RequestMapping("/base64OfJdk")
    public String base64OfJdk(@RequestParam(required = true) String text) {
        String base64 = Base64.getEncoder().encodeToString(text.getBytes());
        log.info(" The {} JDK base64 of {}", text, base64);
        return base64;
    }
}
