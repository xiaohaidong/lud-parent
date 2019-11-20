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
package com.lud.service.smartcampus.service.teacher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.open.api.swjtu.teacher.TeacherAPI;
import com.lud.service.smartcampus.api.teacher.TeacherService;
import com.lud.util.comm.card.CardEntity;
import com.lud.util.comm.card.CardManager;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.task.Task;

/**
 * @author sunqinqiu 
 * @date   2019-09-06 17:45
 */
@Service
@Task(time = 60 * 24)
public class TeacherServiceImp extends CoreService implements TeacherService {
    
    /**
     * 读取教师信息的接口
     */
    @Reference
    private TeacherAPI teacherService;
    
    /**
     * 同步数据
     * @author sunqinqiu 
     * @date   2019-09-07 12:06
     * @return
     */
    @Override
    public void run() {
        teacherService.getTeacherList().forEach(this::asycTeacher);
    }
    
    /**
     * 同步单个教师数据
     * @author sunqinqiu 
     * @date   2019-09-07 12:06
     * @param item
     */
    private void asycTeacher(Map<String, String> item) {
        String code = item.get("code");
        String name = item.get("name");
        String college = item.get("college");
        String cardid = item.get("cardid");
        String category = getTeacherCategory(code);
        CardEntity card = CardManager.getCard(cardid);
        for (String p : springRuntimeService.getContext("teacher.project").split(",")) {
            if (!checkTeacherInfo(code, p)) {
                inserTeacher(code, name, college, category, p, card);
            }
        }
    }
    
    /**
     * 检查数据是否存在
     * @author sunqinqiu 
     * @date   2019-09-07 12:57
     * @param code
     * @param project
     * @return
     */
    private boolean checkTeacherInfo(String code, String project) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-smartcampus.lud_teacher_info_query", DataQueryContent.COUNT, "project,code");
        exe.addData("project", project);
        exe.addData("code", code);
        return this.dataQueryService.queryForInt(exe) > 0;
    }
    
    /**
     * 获取教师类型
     * @author sunqinqiu 
     * @date   2019-09-07 12:26
     * @param cardid
     * @return
     */
    private String getTeacherCategory(String code) {
        String codeFirstLat = code.toLowerCase().toLowerCase();
        String teacherCategory = "f".equals(codeFirstLat) ? "03" : "02";
        if ("1234567890cde.".contains(codeFirstLat)) {
            teacherCategory = "01";
        }
        return teacherCategory;
    }
    
    /**
     * 添加数据
     * @author sunqinqiu 
     * @date   2019-09-07 13:02
     * @param code
     * @param name
     * @param college
     * @param category
     * @param project
     * @param card
     * @return
     */
    private boolean inserTeacher(String code, String name, String college, String category, String project, CardEntity card) {
        Map<String, Serializable> args = new HashMap<>();
        String cardid = card.getId();
        args.put("project", project);
        args.put("code", code);
        args.put("name", name);
        args.put("cardtype", card.isIdentity() ? "1" : "9");
        args.put("cardid", cardid);
        args.put("cardisidentity", card.isIdentity());
        args.put("college", "");
        args.put("collegename", college);
        args.put("category", category);
        args.put("status", 1);
        args.put("birthday", card.getBirth());
        args.put("birthdaydate", DateUtil.toDateTime(card.getBirth(), DateUtil.FORMAT_TYPE_DATE_SHORT));
        args.put("country", card.isIdentity() ? "CN" : "");
        args.put("nativeplace", card.isIdentity() ? cardid.substring(0, 6) : "");
        String userpwd = "000000";
        if (card.isIdentity()) {
            userpwd = cardid.substring(cardid.length() == 18 ? 12 : 9);
        }
        args.put("userpwd", DEncrypt.getSHAEncode(userpwd));
        dataCommandService.exe("lud-smartcampus.lud_teacher_info_insert", args);
        return true;
    }
}
