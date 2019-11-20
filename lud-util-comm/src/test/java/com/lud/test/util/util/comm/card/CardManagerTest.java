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
package com.lud.test.util.util.comm.card;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.test.util.EntityClassTester;
import com.lud.util.comm.card.CardEntity;
import com.lud.util.comm.card.CardManager;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 12:13
 */
public class CardManagerTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetCard() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        loger.trace("=============TEST THE card.CardManager==============");
        String[] cards = new String[] { "510104199505036933", "51010419950503409x", "140303940301250", "14030319940301080x", "140303199403016882" };
        for (String card2 : cards) {
            CardEntity card = CardManager.getCard(card2);
            loger.trace("{}", JSON.toJSONString(card));
        }
        List<Class> list = new ArrayList<>();
        list.add(CardEntity.class);
        EntityClassTester.testEntities(list);
    }
}
