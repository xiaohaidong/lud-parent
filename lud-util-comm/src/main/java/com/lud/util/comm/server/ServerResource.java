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
package com.lud.util.comm.server;

import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.content.core.runtime.RuntimeContent;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务器信息
 * @author sunqinqiu 
 * @date   2018-12-30 04:28
 */
public class ServerResource {
    private static String resourceRoot = CT.EMPTY;
    
    /**
     * 获取目录
     * @author sunqinqiu 
     * @date   2018-12-30 11:13
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        if (resourceRoot.isEmpty()) {
            getResourceRoot();
        }
        return resourceRoot + path;
    }
    
    /**
     * 获取该项目的根目录
     * @author sunqinqiu 
     * @date   2018-12-30 04:30
     * @return
     */
    private static void getResourceRoot() {
        String projectRootPath = ServerResource.class.getClassLoader().getResource(CT.EMPTY).getPath();
        projectRootPath = FileUtil.getParentDirByNames(projectRootPath, RuntimeContent.getSPRING_RUN_DIR());
        projectRootPath = projectRootPath.replace(CharacterContent.PATH_SPLITER, CharacterContent.PATH_SPLITER_S);
        resourceRoot = projectRootPath + (projectRootPath.endsWith(CharacterContent.PATH_SPLITER_S) ? CT.EMPTY : CharacterContent.PATH_SPLITER_S) + RuntimeContent.RESOURCE_DIR;
        System.out.println("resourceRoot:"+resourceRoot);
    }
    
    private ServerResource() {}
}
