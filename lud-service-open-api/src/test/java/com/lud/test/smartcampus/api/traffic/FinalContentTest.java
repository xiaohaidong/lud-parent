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
package com.lud.test.smartcampus.api.traffic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lud.service.open.api.message.ShortMessageConfig;
import com.lud.service.open.api.wxpay.WeChatPayConfig;
import com.lud.service.open.api.wxpay.WeChatPayConfigSystem;
import com.lud.service.open.api.wxpay.WeChatPayDomainInfo;
import com.lud.service.open.api.wxpay.WeChatPayReportInfo;
import com.lud.service.open.api.wxpay.WeChatPayResult;
import com.lud.service.open.api.wxpay.WeChatPayRunConfig;
import com.lud.test.util.EntityClassTester;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 23:48
 */
public class FinalContentTest {
    
    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        List<Class> list = new ArrayList<>();
        list.add(ShortMessageConfig.class);
        list.add(WeChatPayConfig.class);
        list.add(WeChatPayDomainInfo.class);
        list.add(WeChatPayReportInfo.class);
        list.add(WeChatPayResult.class);
        list.add(WeChatPayRunConfig.class);
        list.add(WeChatPayConfigSystem.class);
        EntityClassTester.testEntities(list);
    }
}
