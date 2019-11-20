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
package com.lud.service.open.pay;

import com.lud.service.open.api.pay.PaymetService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.wxpay.WeChatPayConfig;
import com.lud.service.open.api.wxpay.WeChatPayRunConfig;
import com.lud.service.open.api.wxpay.WeChatPayService;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 03:15
 */
@Service
@Preload
public class PaymetServiceImp extends CoreService implements PaymetService {
    /**
     * 支付
     */
    @Reference
    private WeChatPayService             weChatPayService;
    
    /**
     * 用户配置
     */
    private Map<String, WeChatPayConfig> configs = new HashMap<>();
    
    /**
     * 初始化数据
     * @author sunqinqiu 
     * @date   2019-10-26 03:17
     */
    public void run() {
        initWeChatPayConfigs();
    }
    
    /**
     * 初始化
     * @author sunqinqiu 
     * @date   2019-09-14 23:40
     */
    private void initWeChatPayConfigs() {
        configs.clear();
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_wechatpay_config_query", DataQueryContent.DEF, DataQueryContent.DEF);
        dataQueryService.queryForList(exe).forEach(item -> {
            WeChatPayConfig config = new WeChatPayConfig();
            config.setProject(Convert.toString(item.get(CT.STRING_PROJECT)));
            config.setAppID(Convert.toString(item.get("appid")));
            config.setMchID(Convert.toString(item.get("mchid")));
            config.setKey(Convert.toString(item.get("apikey")));
            configs.put(config.getProject(), config);
        });
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 03:29
     * @param project
     * @return
     */
    private WeChatPayRunConfig getWeChatPayDefaultRunConfig(String project) {
        return new WeChatPayRunConfig(getWeChatPayConfig(project), DEncrypt.HMAC_SHA256, true, false);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 03:33
     * @param runconfig
     * @param reqData
     * @return
     */
    @Override
    public ApiResult unifiedOrder(String project, Map<String, String> data) {
        return weChatPayService.unifiedOrder(getWeChatPayDefaultRunConfig(project), data);
    }
    
    /**
     * 获取项目的配置文件
     * @author sunqinqiu 
     * @date   2019-09-14 16:13
     * @param project
     * @return
     */
    private WeChatPayConfig getWeChatPayConfig(String project) {
        return configs.get(project);
    }
}
