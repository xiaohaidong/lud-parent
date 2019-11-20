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
package com.lud.test.service.open.wechat;

import com.lud.service.open.api.pay.PaymetService;
import com.lud.test.util.SpringServiceTester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 14:22
 */
public class WeChatPayServiceTest extends SpringServiceTester {
    @Autowired
    private PaymetService paymetService;
    
    @Test
    public void test() throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("body", "西南交通大学2019年年费");
        data.put("out_trade_no", "2019091510595900000001");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");
        //loger.info(JSON.toJSONString(paymetService.unifiedOrder("100202", data)));
    }
}
