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
package com.lud.service.open.message;

import java.text.MessageFormat;

import org.apache.dubbo.config.annotation.Service;

import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.message.ShortMesageFactory;
import com.lud.service.open.api.message.ShortMesageService;
import com.lud.service.open.api.message.ShortMessage;
import com.lud.util.spring.util.SpringContextUtil;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 05:43
 */
@Service
public class ShortMessageFactoryImp implements ShortMesageFactory {
    /**
     * 获取服务Bean
     * @author sunqinqiu 
     * @date   2019-10-26 00:22
     * @param driver
     * @return
     */
    private ShortMesageService getShortMesageService(String driver) {
        String name = MessageFormat.format("com.lud.service.open.message.{0}.shortmessage", driver);
        return (ShortMesageService) SpringContextUtil.getBean(name);
    }
    
    /**
     * 发送短信
     * @author sunqinqiu 
     * @date   2019-10-26 00:19
     * @param message
     * @return
     */
    public ApiResult send(ShortMessage message) {
        ShortMesageService service = getShortMesageService(message.getDriver());
        return service.send(message);
    }
}