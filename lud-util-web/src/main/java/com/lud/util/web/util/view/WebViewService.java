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
package com.lud.util.web.util.view;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.lud.service.core.api.data.query.DataPager;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.RandomUtil;
import com.lud.util.content.redis.node.WebViewNode;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.SpringServiceInvoker;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;
import com.lud.util.web.api.view.ViewCommandEntity;
import com.lud.util.web.api.view.ViewContentEntity;
import com.lud.util.web.api.view.ViewServiceEntity;
import com.lud.util.web.core.RequestService;

/**
 * @author sunqinqiu 
 * @date   2019-01-09 15:14
 */
@Service
public class WebViewService extends CoreService {
    private static final String ERROR_CODE_1004_1003 = "1004.1003";
    
    /**
     * 
     */
    @Autowired
    private WebViewManager      webViewManager;
    
    /**
     * 
     */
    @Autowired
    private ViewFactory         wiewFactory;
    
    /**
     * 
     */
    @Autowired
    private WebViewDataService  webViewDataService;
    
    /**
     * 
     */
    @Autowired
    private WebViewCompService  webViewCompService;
    
    /**
     * 
     */
    @Autowired
    private RequestService      requestService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-11-05 18:34
     * @param name
     * @return
     */
    private ViewServiceEntity getViewService(String name, String userGroup) {
        boolean isvali = false;
        ViewServiceEntity view = webViewManager.getViewServiceEntity(name);
        String acl = view.getAcl();
        if (acl.equals("public")) isvali = true;
        if (acl.equals("user") && !userGroup.equals("public")) isvali = true;
        if (acl.contains(userGroup)) isvali = true;
        return isvali ? view : null;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 20:16
     * @param name
     * @param flag
     * @param request
     * @return
     */
    public ContextResult exe(String name, String flag, ContextRequest request) {
        ContextResult result = new ContextResult();
        ViewServiceEntity service = getViewService(name, request.getUserGroup());
        if (service == null) {
            result.setResult(false);
            result.setCode(ERROR_CODE_1004_1003);
            result.setMessage(resourceService.getError(ERROR_CODE_1004_1003));
            return result;
        }
        try {
            exe(service, flag, request, result);
        } catch (Exception ex) {
            result.setResult(false);
            result.setCode("error:500");
            result.setMessage(ex.toString());
        }
        return result;
    }
    
    /**
     * 中文
     * @author sunqinqiu 
     * @date   2019-03-03 21:44
     * @param service
     * @param flag
     * @param request
     */
    @SuppressWarnings("unchecked")
    private void exe(ViewServiceEntity service, String flag, ContextRequest request, ContextResult result) {
        Map<String, Serializable> queryList = new HashMap<>();
        service.getDatas().forEach(data -> {
            if (data.getFlag().contains(flag)) {
                Serializable dataItem = webViewDataService.getData(data, request);
                if (dataItem.getClass().equals(DataPager.class)) {
                    queryList.put(data.getName() + "_list", (Serializable) ((DataPager) dataItem).getDataList());
                }
                queryList.put(data.getName(), dataItem);
            }
        });
        service.getCommands().forEach(cmd -> {
            if (cmd.getFlag().contains(flag)) {
                if (cmd.isList()) {
                    List<Map<String, Serializable>> list = (List<Map<String, Serializable>>) queryList.get(cmd.getDataName());
                    exeList(cmd, list, request, result);
                } else {
                    Map<String, Serializable> map = null;
                    if (cmd.getDataName() != null) {
                        map = (Map<String, Serializable>) queryList.get(cmd.getDataName());
                    }
                    exeMap(cmd, map, request, result);
                }
            }
        });
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 21:48
     * @param cmd
     * @param list
     * @param request
     */
    private void exeList(ViewCommandEntity cmd, List<Map<String, Serializable>> list, ContextRequest request, ContextResult result) {
        if (list != null) {
            list.forEach(item -> exeMap(cmd, item, request, result));
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 21:50
     * @param cmd
     * @param map
     * @param request
     */
    private void exeMap(ViewCommandEntity cmd, Map<String, Serializable> map, ContextRequest request, ContextResult result) {
        Map<String, Serializable> args = new HashMap<>();
        Map<String, Serializable> reqData = requestService.getDataByRequest(request);
        args.put("random", "z" + RandomUtil.getRandomChar(5).toLowerCase());
        if (reqData != null) {
            args.putAll(reqData);
        }
        if (cmd.getName() != null) {
            for (String arg : cmd.getArgs().split(CharacterContent.STRING_COMMA)) {
                String[] argl = arg.split(CharacterContent.CHS_M);
                String key = argl[0];
                String valueKey = argl.length == 1 ? argl[0] : argl[1];
                Serializable value = valueKey.startsWith(CharacterContent.CHS_D) ? map.get(valueKey.substring(1)) : request.getData().get(valueKey);
                args.put(key, value);
            }
            dataCommandService.exe(cmd.getName(), args);
        }
        if (cmd.getInvoke() != null) {
            if (map == null) {
                map = new HashMap<>();
            }
            SpringServiceInvoker.invoke(cmd.getInvoke(), map, request, result);
        }
        result.setAction(cmd.getAction());
    }
    
    /**
     * 获取页面信息
     * @author sunqinqiu 
     * @date   2019-01-09 15:16
     * @param service
     * @param action
     * @param flag
     * @param request
     * @return
     */
    public String getWebViewContent(String name, String flag, ContextRequest request) {
        ViewServiceEntity service = getViewService(name, request.getUserGroup());
        if (service == null) { return resourceService.getError(ERROR_CODE_1004_1003); }
        String key = name + CharacterContent.CHS_ZH + flag;
        String viewTemplement = null;
        if (service.isCache()) {
            viewTemplement = this.redisService.hget(WebViewNode.REDIS_NODE_FOR_WEBVIEW_PAGE, key);
        }
        if (viewTemplement == null) {
            viewTemplement = getWebViewTemplement(service, flag);
            if (service.isCache()) {
                this.redisService.hset(WebViewNode.REDIS_NODE_FOR_WEBVIEW_PAGE, key, Content.replaceBlank(viewTemplement));
            }
        }
        return getWebViewContentAddData(viewTemplement, service, flag, request);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-02-24 17:36
     * @param viewTemplement
     * @param service
     * @param flag
     * @param request
     * @return
     */
    private String getWebViewContentAddData(String viewTemplement, ViewServiceEntity service, String flag, ContextRequest request) {
        ModelMap map = new ModelMap();
        service.getDatas().forEach(data -> {
            if (data.getFlag().contains(flag)) {
                Serializable dataItem = webViewDataService.getData(data, request);
                if (dataItem.getClass().equals(DataPager.class)) {
                    map.put(data.getName() + "_list", ((DataPager) dataItem).getDataList());
                }
                map.put(data.getName(), dataItem);
            }
        });
        map.put(CT.STRING_PROJECTID, request.getProject());
        if (request.getMenuActions() != null) {
            map.put(CT.STRING_ROLE_ACTIONS, request.getMenuActions());
        }
        return wiewFactory.getContentView(viewTemplement, map);
    }
    
    /**
     * 
     * @author sunqinqiu
     * @date   2019-01-10 13:47
     * @param service
     * @param flag
     * @return
     */
    private String getWebViewTemplement(ViewServiceEntity service, String flag) {
        /**
         * 1. 生成布局
         */
        /**
         * 1.1 构造数据
         */
        ModelMap map = new ModelMap();
        map.put("serviceid", service.getName().replace("-", "_").replace(".", "_"));
        map.put("config", service.getConfig());
        map.put("contents", getLayoutsContent(service, flag));
        /**
         * 1.2 模板
         */
        String tempFile = service.getTheme();
        if (!tempFile.endsWith(".html")) {
            tempFile = MessageFormat.format("/view-service/layout/{0}.html", tempFile);
        }
        String layoutTemplement = wiewFactory.getFileView(tempFile, map);
        /**
         * 2 把组件加入到模板中
         */
        /**
         * 2.1 获取组件
         */
        map = new ModelMap();
        map.put("comp", getCompsContent(service, flag));
        return wiewFactory.getContentView(layoutTemplement, map);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-09 15:18
     * @return
     */
    public List<Map<String, Serializable>> getLayoutsContent(ViewServiceEntity service, String flag) {
        List<Map<String, Serializable>> contentLayout = new ArrayList<>();
        service.getContents().forEach(item -> {
            if (item.getFlag().contains(flag)) {
                Map<String, Serializable> map = new HashMap<>();
                map.putAll(item.getConfig());
                map.put(CT.STRING_CONTENT, getLayoutContent(service, item));
                contentLayout.add(map);
            }
        });
        return contentLayout;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-09 15:18
     * @return
     */
    private String getLayoutContent(ViewServiceEntity service, ViewContentEntity viewContent) {
        String tempFile = viewContent.getTheme();
        if (!tempFile.endsWith(".html")) {
            tempFile = MessageFormat.format("/view-service/content/{0}.html", tempFile);
        }
        ModelMap map = new ModelMap();
        map.put(CT.STRING_CONFIG, viewContent.getConfig());
        map.put("items", viewContent.getItems());
        map.put("contentid", (service.getName().replace("-", "_") + "_" + viewContent.getName()).replace(".", "_"));
        return wiewFactory.getFileView(tempFile, map);
    }
    
    /**
     * 获取组件的内容
     * @author sunqinqiu 
     * @date   2019-01-09 15:18
     * @return
     */
    private Map<String, String> getCompsContent(ViewServiceEntity service, String flag) {
        Map<String, String> list = new HashMap<>();
        service.getComps().forEach(item -> {
            if (item.getFlag().contains(flag)) {
                list.put(item.getName(), webViewCompService.getCompsContent(service.getName(), item, flag));
            }
        });
        return list;
    }
    
}
