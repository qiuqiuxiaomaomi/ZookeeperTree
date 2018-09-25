package com.bonaparte.config;

import com.bonaparte.bean.ZkClient;
import com.bonaparte.constant.ZkProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangmingquan on 2018/9/25.
 */
@Configuration
public class ZkConfiguration {
    @Autowired
    private ZkProps zkProps;

    @Bean(initMethod = "init", destroyMethod = "stop")
    public ZkClient zkClient(){
        ZkClient zkClient = new ZkClient();
        zkClient.setServer(zkProps.getServer());
        zkClient.setSessionTimeoutMs(zkProps.getSessionTimeoutMs());
        zkClient.setConnectionTimeoutMs(zkProps.getConnectionTimeoutMs());
        zkClient.setMaxRetries(zkProps.getMaxRetries());
        zkClient.setBaseSleepTimeMs(zkProps.getBaseSleepTimeMs());
        zkClient.register();
        return zkClient;
    }
}
