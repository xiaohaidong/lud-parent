/*
 * Copyright 2018-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lud.service.open.api.wxpay;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 15:37
 */
public class WeChatPayConfigSystem implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID     = 1L;
    
    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    @Getter
    private static int        httpConnectTimeoutMs = 6000;
    
    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    @Getter
    private static int        httpReadTimeoutMs    = 8000;
    
    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    @Getter
    private static boolean    autoReport           = true;
    
    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    @Getter
    private static int        reportWorkerNum      = 6;
    
    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    @Getter
    private static int        reportQueueMaxSize   = 10000;
    
    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    @Getter
    private static int        reportBatchSize      = 10;
}
