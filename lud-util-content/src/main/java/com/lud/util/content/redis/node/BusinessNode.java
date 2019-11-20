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
package com.lud.util.content.redis.node;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 02:58
 */
public class BusinessNode {
    public static final String REDIS_NODE_FOR_BUSINESS_COMPANY                     = "com.lud.business.company";
    
    public static final String REDIS_NODE_FOR_BUSINESS_PROJECT                     = "com.lud.business.project";
    public static final String REDIS_NODE_FOR_BUSINESS_PROJECT_CONFIG              = "com.lud.business.project.config";
    public static final String REDIS_NODE_FOR_BUSINESS_USERGROUP                   = "com.lud.business.usergroup";
    public static final String REDIS_NODE_FOR_BUSINESS_USERGROUPS                  = "com.lud.business.usergroups";
    public static final String REDIS_NODE_FOR_BUSINESS_USERGROUP_APPLICATION_GROUP = "com.lud.business.usergroup.application.group";
    public static final String REDIS_NODE_FOR_BUSINESS_APPLICATION_GROUP           = "com.lud.business.application.group";
    public static final String REDIS_NODE_FOR_BUSINESS_APPLICATION                 = "com.lud.business.application";
    public static final String REDIS_NODE_FOR_BUSINESS_APPLICATION_MENU            = "com.lud.business.application.menu";
    public static final String REDIS_NODE_FOR_BUSINESS_APPLICATION_WEDGE           = "com.lud.business.application.wedge";
    public static final String REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATION        = "com.lud.business.userrole.application";
    public static final String REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENUS   = "com.lud.business.userrole.application.menu";
    public static final String REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENU    = "com.lud.business.userrole.menu.role";
    public static final String REDIS_NODE_FOR_BUSINESS_SHORT_MESSAGE_CONFIG        = "com.lud.business.showmessage.config";
    public static final String REDIS_NODE_FOR_BUSINESS_CODE_CONTENT                = "com.lud.business.code.content";
    
    private BusinessNode() {}
}
