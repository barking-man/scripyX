package com.mark.service.impl;

import com.mark.config.NettyClientConfig;
import com.mark.service.SenderService;
import com.mark.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public class SenderServiceImpl implements SenderService {

    @Override
    public ResultVo<String> send(String data) {
        NettyClientConfig.sendMsg(data);
        return ResultVo.setSuccess(data);
    }
}
