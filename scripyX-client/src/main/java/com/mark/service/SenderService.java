package com.mark.service;

import com.mark.vo.ResultVo;

public interface SenderService {
    ResultVo<String> send(String data);
}
