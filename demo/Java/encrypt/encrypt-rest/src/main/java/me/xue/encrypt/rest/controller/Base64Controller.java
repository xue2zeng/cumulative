package me.xue.encrypt.rest.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Api(value = "Base64Controller", description = "Base64加解密JDK8实现")
@RestController
@RequestMapping("/base64")
public class Base64Controller {

    @ApiOperation(value = "获取Base64加密字符", notes = "JDK自带实现Base64加密算法")
    @ApiImplicitParam(name = "text", value = "加密文本", dataType = "String", paramType = "query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "加密成功", response = String.class),
            @ApiResponse(code = 500, message = "加密失败", response = String.class)
    })
    @RequestMapping(value = "/jdk/encoder")
    public String base64EncoderOfJdk(@RequestParam(required = true) String text) {
        String base64 = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        log.info(" The {} JDK base64 encoder of {}", text, base64);
        return base64;
    }

    @RequestMapping(value = "/jdk/urlencoder")
    public String base64UrlEncoderOfJdk(@RequestParam(required = true) String text) {
        String base64UrlStr = Base64.getUrlEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        log.info(" The {} JDK base64 url encoder of {}", text, base64UrlStr);
        return base64UrlStr;
    }

    @RequestMapping(value = "/jdk/mimeencoder")
    public String base64MimeEncoderOfJdk(@RequestParam(required = true) String text) {
        String base64UrlStr = Base64.getMimeEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        log.info(" The {} JDK base64 url encoder of {}", text, base64UrlStr);
        return base64UrlStr;
    }

}
