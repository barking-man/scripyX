package com.mark.controller;

import com.mark.service.SenderService;
import com.mark.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/scripyX/client/sender")
public class SenderController {

    @Resource
    private SenderService senderService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResultVo sender(@RequestBody String data) {
        return senderService.send(data);
    }
}
