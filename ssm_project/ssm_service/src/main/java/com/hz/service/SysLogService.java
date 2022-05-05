package com.hz.service;

import com.hz.domain.SysLog;

import java.util.List;

public interface SysLogService {

    void save(SysLog log);

    List<SysLog> findAll();
}
