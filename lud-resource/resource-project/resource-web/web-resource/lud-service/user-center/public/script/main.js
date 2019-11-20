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

var lud = lud || {};
lud.userCenter = {
    init: function () {
        lud.userCenter.application.init();
        lud.userCenter.timer.init();
    },
    application: {
        init: function () { }
    },
    //执行
    run: function () {
        lud.userCenter.resource.loadStyle();
    },
    timer: {
        times: 0,
        init: function () {
            lud.userCenter.timer.exetimer();
        },
        exetimer: function () {
            lud.userCenter.timer.exe();
            if (lud.userCenter.timer.times == 60 * 60 * 12) {
                lud.userCenter.timer.times = 0
            };
            lud.userCenter.timer.times++;
            setTimeout("lud.userCenter.timer.exetimer();", 1000);
        },
        exe: function () {
            $("[lud-timer='click']").each(function () {
                var $tag = $(this);
                var time = parseInt($(this).data("time"));
                if (lud.userCenter.timer.times % time == 0) {
                    $tag.click();
                }
            });
            $("[timer]").each(function () {
                var fmt = $(this).attr("timer");
                $(this).text(lud.util.date.getDate(fmt));
            });
        }
    },
    //资源预加载
    resource: {
        loadIndex: 0,
        scripts: [
            "/web-resource/add-on/assets/datepicker/wdatepicker.js",
            "/web-resource/add-on/assets/echart/echarts.min.js",
            "/web-resource/add-on/assets/echart/skin-l.js",
            "/web-resource/add-on/assets/echart/skin-d.js",
            "/web-resource/add-on/assets/echart/skin-s.js",
            "/web-resource/add-on/assets/drag/drag.js",
            "/web-resource/add-on/assets/ztree/js/jquery.ztree.all.min.js",
            "/web-resource/add-on/lud/script/lud-request-user.js",
            "/web-resource/add-on/lud/script/lud-comp.js",
            "/web-resource/add-on/lud/script/lud-layout.js",
            "/web-resource/add-on/lud/script/lud-event.js",
            "/web-resource/add-on/lud/script/lud-msgbox.js",
            "/web-resource/lud-service/user-center/" + GLBDATA.theme + "/script/main.js"
        ],
        styles: [
            "/web-resource/add-on/assets/icon/css/fontawesome-all.min.css",
            "/web-resource/add-on/assets/ztree/css/def/main.css",
            "/web-resource/add-on/lud/style/lud-comm/reboot.css",
            "/web-resource/add-on/lud/style/lud-comm/color.css",
            "/web-resource/add-on/lud/style/lud-comm/font.css",
            "/web-resource/add-on/lud/style/lud-comm/size.css",
            "/web-resource/add-on/lud/style/lud-comm/box.css",
            "/web-resource/add-on/lud/style/lud-comp/navigation.css",
            "/web-resource/add-on/lud/style/lud-comp/logo.css",
            "/web-resource/add-on/lud/style/lud-comp/title.css",
            "/web-resource/add-on/lud/style/lud-comp/pictext.css",
            "/web-resource/add-on/lud/style/lud-comp/reader.css",
            "/web-resource/add-on/lud/style/lud-comp/date.css",
            "/web-resource/add-on/lud/style/lud-comp/editor.css",
            "/web-resource/add-on/lud/style/lud-comp/canvas.css",
            "/web-resource/add-on/lud/style/lud-comp/chart.css",
            "/web-resource/add-on/lud/style/lud-comp/bar.css",
            "/web-resource/add-on/lud/style/lud-comp/msgbox.css",
            "/web-resource/add-on/lud/style/lud-element/images.css",
            "/web-resource/add-on/lud/style/lud-element/label.css",
            "/web-resource/add-on/lud/style/lud-element/form.css",
            "/web-resource/add-on/lud/style/lud-element/button.css",
            "/web-resource/add-on/lud/style/lud-element/table-grid.css",
            "/web-resource/add-on/lud/style/lud-layout/comm.css",
            "/web-resource/add-on/lud/style/lud-layout/spliter.css",
            "/web-resource/lud-service/user-center/public/style/main.css",
            "/web-resource/lud-service/user-center/" + GLBDATA.theme + "/style/main.css"
        ],
        loadScript: function () {
            var res = lud.userCenter.resource.scripts[lud.userCenter.resource.loadIndex];
            lud.userCenter.resource.loadIndex = lud.userCenter.resource.loadIndex + 1;
            lud.util.loader.script(res, lud.userCenter.resource.loadIndex < lud.userCenter.resource.scripts.length ? lud.userCenter.resource.loadScript : lud.userCenter.init);
        },
        loadStyle: function () {
            for (var css in lud.userCenter.resource.styles) {
                lud.util.loader.style(lud.userCenter.resource.styles[css]);
            }
            lud.userCenter.resource.loadScript();
        }
    }
};
$(function () {
    lud.userCenter.run();
});
