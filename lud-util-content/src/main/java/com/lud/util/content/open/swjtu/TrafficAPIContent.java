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
package com.lud.util.content.open.swjtu;

import java.io.Serializable;

/**
 * @author sunqinqiu 
 * @date   2019-09-12 10:33
 */
public class TrafficAPIContent implements Serializable {
    
    /**
     * 
     */
    private static final long  serialVersionUID  = 1L;
    /**
     * 车辆信息
     */
    public static final String PARK_NAME         = "车场名称";
    public static final String OWNER_REGEID      = "人员编号";
    public static final String OWNER_NAME        = "车主姓名";
    public static final String OWNER_SEX         = "性别";
    public static final String OWNER_DEPARTEMNT  = "部门名称";
    public static final String OWNER_ADDRESS     = "地址";
    public static final String OWNER_MOBITEL     = "联系电话";
    public static final String CAR_REGID         = "注册卡号";
    public static final String CAR_NUMUBER       = "车牌号码";
    public static final String CAR_NCOLOR        = "车牌颜色";
    public static final String CAR_TYPE          = "车型";
    public static final String ISSURE_ID         = "ID";
    public static final String ISSURE_TYPE       = "授权类型";
    public static final String ISSURE_BEGIN_DATE = "授权开始时间";
    public static final String ISSURE_END_DATE   = "授权结束时间";
    public static final String ISSURE_DATE_FAT   = "yyyy-MM-dd HH:mm:ss";
    public static final String ISSURE_BALANCE    = "储值余额";
    public static final String ISSURE_DEPOSIT    = "授权押金";
    public static final String ISSURE_POSTION    = "车位编号";
    public static final String ISSURE_NOTE       = "备注";
    public static final String OPERATOR_USER     = "操作员";
    public static final String OPERATOR_DATE     = "操作时间";
    public static final String OPERATOR_ORG      = "操作来源";
    public static final String MSTR_ISSUREID     = "issureid";
}
