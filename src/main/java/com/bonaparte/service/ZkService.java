package com.bonaparte.service;

import com.bonaparte.bean.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by yangmingquan on 2018/9/25.
 */
public class ZkService {
    @Autowired
    ZkClient zkClient;

    public void ZookeeperCheck(){
        List<String> instanceList = zkClient.getInstances();
        Collections.sort(instanceList);
        //具体业务
    }
}
