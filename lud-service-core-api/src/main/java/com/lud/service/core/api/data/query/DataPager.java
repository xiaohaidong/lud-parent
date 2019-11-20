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
package com.lud.service.core.api.data.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lud.util.content.core.data.PLSQLContent;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页程序
 * @author sunqinqiu 
 * @date   2019-01-02 04:07
 */
public class DataPager implements Serializable {
    
    /**
     * 序号
     */
    private static final long               serialVersionUID = 1L;
    
    /**
    * 当前第几页
    */
    @Getter
    private int                             pageIndex;
    
    /**
     * 每页的数据
     */
    @Getter
    private int                             pageSize;
    
    /**
     * 总数据量
     */
    @Getter
    @Setter
    private int                             dataCount;
    
    /**
     * 开始数
     */
    @Getter
    private int                             beginNumber;
    
    /**
     * 开始页数
     */
    @Getter
    private int                             pagerBegin;
    
    /**
     * 结束页数
     */
    @Getter
    private int                             pagerEnd;
    
    /**
     * 总页数
     */
    @Getter
    private int                             pagerCount;
    
    /**
     * 数据
     */
    @Getter
    @Setter
    private List<Map<String, Serializable>> dataList;
    
    /**
     * 构造一个分页模型
     */
    public DataPager(int pageindex, int pagesize) {
        pageIndex = pageindex;
        pageSize = pagesize;
        pageIndex = pageIndex < 1 ? 1 : pageIndex;
        pageSize = pageSize < 1 ? PLSQLContent.PAGER_DEF_PAGEINDEX : pageSize;
        beginNumber = (pageIndex - 1) * pageSize;
        if (pageSize > PLSQLContent.PAGER_MAX_NUM) {
            pageSize = PLSQLContent.PAGER_MAX_NUM;
        }
    }
    
    /**
     * 获取总页数
     * @author sunqinqiu 
     * @date   2018-05-17 11:13
     */
    public void reckonPagerNum() {
        pagerCount = (dataCount + pageSize - 1) / pageSize;
        pagerCount = (pagerCount > 0 ? pagerCount : 1);
        pagerBegin = pageIndex - PLSQLContent.PAGER_BT_PAGE > 0 ? pageIndex - PLSQLContent.PAGER_BT_PAGE : 1;
        pagerEnd = pageIndex + PLSQLContent.PAGER_BT_PAGE < pagerCount ? pageIndex + PLSQLContent.PAGER_BT_PAGE : pagerCount;
    }
}
