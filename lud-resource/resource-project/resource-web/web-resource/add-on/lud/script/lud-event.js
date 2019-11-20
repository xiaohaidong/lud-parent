/**
 * 事件绑定
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.event = {
    init: function () {
        lud.event.binder.bind();
        lud.event.render.init(document);
        lud.event.task.init();
        lud.event.light.init();
    },
    render: {
        autoRender: {
            $doms: null,
            curDomIndex: -1,
            init: function (container) {
                lud.event.render.autoRender.curDomIndex = -1;
                lud.event.render.autoRender.$doms = $(container).find("[lud-render]");
                if (lud.event.render.autoRender.$doms.length > 0) {
                    lud.event.render.autoRender.rendDoms();
                }
            },
            rendDoms: function () {
                lud.event.render.autoRender.curDomIndex++;
                if (lud.event.render.autoRender.$doms.length > lud.event.render.autoRender.curDomIndex) {
                    var $dom = $(lud.event.render.autoRender.$doms[lud.event.render.autoRender.curDomIndex]);
                    var url = $dom.attr("lud-render");
                    lud.event.render.rend(url, null, $dom, $dom, lud.event.render.autoRender.rendDoms);
                } else {
                    lud.event.render.autoRender.$doms.each(function () {
                        lud.event.render.afterRend($(this));
                    });
                }
            }
        },
        init: function (container) {
            $(container).find("[lud-oclick]").each(function () {
                var eventClick = $(this).attr("lud-oclick");
                if (eventClick == "this") {
                    $(this).click();
                } else {
                    $(this).find(eventClick).click();
                }
            });
            lud.event.render.autoRender.init(container);
        },
        rendContent: function (url, data, dom, target) {
            this.rend(url, data, dom, target, function ($con) {
                lud.event.render.afterRend($con);
            });
        },
        rend: function (url, data, dom, target, callback) {
            var $con = $(dom);
            R.post(url, data, function (msg) {
                lud.util.finder.clear($con);
                $con.html(msg);
                if (callback) {
                    callback($con);
                }
            }, $(target));
        },
        afterRend: function (target) {
            lud.event.render.init(target);
            lud.layout.initer.initContainer(target);
            lud.comp.initContainer(target);
        }
    },
    conmsg: {
        con: function (target) {
            var isConfirm = false;
            var confgMsg = target.attr("confirm");
            if (confgMsg) {
                if (confirm(confgMsg)) {
                    isConfirm = true;
                }
            } else {
                isConfirm = true;
            }
            return isConfirm;
        }
    },
    binder: {
        exe: function (url, data, target) {
            if (MC) {
                MC.show();
            }
            R.post(url, data, function (msg) {
                var result = JSON.parse(msg);
                if (result.result) {
                    if (result.action) {
                        eval(result.action);
                    } else {
                        if (result.code || result.message) {
                            alert(result.code + "\n" + result.message);
                        }
                    }
                } else {
                    alert(result.code + "\n" + result.message);
                }
                if (MC) {
                    MC.hide();
                }
            }, target);
        },
        bind: function () {
            /**
             * 点击按钮后把数据显示在指定位置
             */
            $(document).on("click", "[lud-render-click]", function () {
                var url = $(this).attr("lud-render-click");
                var dom = $($(this).closest("[lud-render-target]").attr("lud-render-target"));
                var menu = $(this).attr("lud-menu");
                if (menu != undefined) {
                    $(dom).attr("lud-menu", menu);
                }
                lud.event.render.rendContent(url, null, dom, $(this));
            });
            /**
             * 通用执行程序
             */
            $(document).on("click", "[lud-exe]", function () {
                var target = $(this);
                if (lud.event.conmsg.con(target)) {
                    var $form = $(this).closest("form");
                    var url = $form.data("action");
                    var data = $form.serializeArray();
                    lud.event.binder.exe(url, data, target);
                }
            });
            /**
             * url
             */
            $(document).on("click", "[lud-exeu]", function () {
                var target = $(this);
                if (lud.event.conmsg.con(target)) {
                    var url = target.attr("lud-exeu");
                    lud.event.binder.exe(url, null, $(this));
                }
            });
            /**
             * 
             */
            $(document).on("change", "[lud-update]", function () {
                var target = $(this);
                if (lud.event.conmsg.con(target)) {
                    if (target.val().trim() == "") {
                        alert("不能为空");
                        return;
                    }
                    var url = target.attr("lud-update");
                    var data = new Array();
                    data.push({ "name": "key", "value": target.attr("key") });
                    data.push({ "name": "ovalue", "value": target.attr("ovalue") });
                    data.push({ "name": "value", "value": target.val() });
                    lud.event.binder.exe(url, data, $(this));
                }
            });
        }
    },
    task: {
        init: function () {

        },
        exe: function () {

        }
    },
    light: {
        times: 0,
        init: function () {
            lud.event.light.exe();
        },
        exe: function () {
            $(document).find("[light]").each(function () {
                var random = lud.util.random.getRandomNum(0, 20);
                if (random % 20 > 0 && !$(this).is(":hidden")) return;
                $(this).toggle();
            });
            if (lud.event.light.times > 1000000) {
                lud.event.light.times = 0;
            }
            lud.event.light.times++;
            setTimeout("lud.event.light.exe()", 500);
        }
    }
}
$(function () {
    lud.event.init();
});