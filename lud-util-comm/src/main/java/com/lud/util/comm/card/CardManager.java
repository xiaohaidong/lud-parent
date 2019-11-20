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
package com.lud.util.comm.card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.Expressions;

/**
 * 对证件的一些操作
 * 
 * @author sunqinqiu
 * @date 2018-05-06 23:03
 */
public final class CardManager {
    
    /**
     * 15位身份证号
     */
    private static final int    CHINA_ID_MIN_LENGTH = 15;
    
    /**
     * 18位身份证号
     */
    private static final int    CHINA_ID_MAX_LENGTH = 18;
    
    /**
     * 19**年的开始
     */
    private static final String YEAR_BEGIN_19       = "19";
    
    /**
     * 
     */
    private static final int[]  RAND                = new int[17];
    
    /**
     * 
     */
    static {
        for (int i = 0; i < RAND.length; i++) {
            RAND[i] = (int) Math.pow(2, (RAND.length - i)) % 11;
        }
    }
    
    /**
     * 根据ID号获取一个IDCARD模型
     * 
     * @author sunqinqiu
     * @date 2019-09-17 20:37
     * @param id
     * @return
     */
    public static CardEntity getCard(String id) {
        CardEntity model = new CardEntity(id);
        model.setIdentity(isIdentityID(model));
        if (!model.isIdentity()) { return model; }
        String sCardNum = (id.length() == CHINA_ID_MIN_LENGTH ? update(id) : id).substring(16, 17);
        model.setSex(Convert.toInt(sCardNum) % 2 == 0 ? CT.STRING_NUM_2 : CT.STRING_NUM_1);
        model.setName(CT.EMPTY);
        return model;
    }
    
    /**
     * 获取身份证的最后一位是否正确，只针对18位的身份证号
     * 
     * @author sunqinqiu
     * @date 2019-09-17 20:37
     * @param code
     * @return
     */
    private static String getCheckFlag(String code) {
        int numSum = 0;
        for (int i = 0; i < code.length(); i++) {
            numSum += Convert.toInt(code.charAt(i)) * RAND[i];
        }
        int checkDigit = 12 - numSum % 11;
        switch (checkDigit) {
            case 10:
                return CT.STRING_CARD_END;
            case 11:
                return CT.STRING_NUM_0;
            case 12:
                return CT.STRING_NUM_1;
            default:
                return String.valueOf(checkDigit);
        }
    }
    
    /**
     * 验证身份证号是否合法
     * 
     * @author sunqinqiu
     * @date 2019-09-17 20:37
     * @param model
     * @return
     */
    private static boolean isIdentityID(CardEntity model) {
        String id = model.getId();
        int idLength = id.length();
        if ((idLength != CHINA_ID_MIN_LENGTH) && (idLength != CHINA_ID_MAX_LENGTH)) { return false; }
        Matcher mt = Pattern.compile(Expressions.CARD_PAT).matcher(id);
        if (!mt.find()) { return false; }
        String birthday = (idLength == CHINA_ID_MIN_LENGTH) ? YEAR_BEGIN_19 + id.substring(6, 12) : id.substring(6, 14);
        if (!DateUtil.isDateTime(birthday, DateUtil.FORMAT_TYPE_DATE_SHORT)) { return false; }
        model.setBirth(birthday);
        if (idLength == CHINA_ID_MAX_LENGTH) { return (Convert.toString(id.charAt(CHINA_ID_MAX_LENGTH - 1))).equalsIgnoreCase(getCheckFlag(id.substring(0, CHINA_ID_MAX_LENGTH - 1))); }
        return true;
    }
    
    /**
     * 15位身份证号升级为18位身份证号
     * 
     * @author sunqinqiu
     * @date 2019-09-17 20:37
     * @param id
     * @return
     */
    private static String update(String id) {
        return id.substring(0, 6) + YEAR_BEGIN_19 + id.substring(6) + getCheckFlag(id);
    }
    
    /**
     * 构造方法
     */
    private CardManager() {}
}
