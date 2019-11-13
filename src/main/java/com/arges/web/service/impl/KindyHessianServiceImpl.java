package com.arges.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arges.web.service.KindyHessianService;

/**
 * @author zhangjie
 */
@Service
public class KindyHessianServiceImpl implements KindyHessianService {
    @Override
    public boolean pushTransmission(int systemType, int endpointType, List<String> clientIdList, String subject, String content) throws Exception {
        return false;
    }
}
