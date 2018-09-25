package com.bonaparte.bean;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangmingquan on 2018/9/25.
 */
public class ZkClient {
    private CuratorFramework client;
    public String server;
    public Integer sessionTimeoutMs;
    public Integer connectionTimeoutMs;
    public Integer maxRetries;
    public Integer baseSleepTimeMs;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public Integer getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Integer getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    public void init(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        client = CuratorFrameworkFactory.builder()
                .connectString(server)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .build();
        client.start();
    }

    public void stop(){
        client.close();
    }

    public void register(){
        try{
            String rootPath="/" + "services";
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String serviceInstance = "prometheus" + "-" + hostAddress + "-";
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getChildren(String path){
        List<String> childrenList = new ArrayList<>();
        try{
            childrenList = client.getChildren().forPath(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return childrenList;
    }

    public int getChildrenCount(String path){
        return getChildren(path).size();
    }

    public List<String> getInstances(){
        return getChildren("/services");
    }

    public int getInstancesCount(){
        return getInstances().size();
    }
}
