/**
 * 窗口操作
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.msgbox = {
    init: function () {
        MB.creater.render.event.initEvents();
    },
    cover: {
        coverdom: null,
        covers: new Array(),
        show: function () {
            if (MC.coverdom == null) MC.coverdom = $(".lud-msgbox-cover");
            if (MC.covers.length < 1) MC.coverdom.show();
            var zindex = lud.util.date.getFlag() - 1;
            MC.covers.push(zindex);
            MC.coverdom.css("z-index", zindex);
        },
        hide: function () {
            MC.covers.pop();
            if (MC.covers.length == 0) {
                MC.coverdom.hide();
            } else {
                var zindex = MC.covers[MC.covers.length - 1]
                MC.coverdom.css("z-index", zindex);
            }
        }
    },
    msgbox: {
        msgboxsdom: null,
        opt: {
            id: "", skin: "classic", icon: "fad fa-ellipsis-v", title: "lud system windows", render: "ajax", dataorg: "", data: null, template: "",
            width: 1200, height: 740, offset: { left: 0, top: 0 },
            ismax: false, toolbar: true, canHelp: true, canClose: true, canMax: true, canDrag: true, isAutoSize: false, onShow: false, onClose: false, target: null
        },
        curMsgboxOpt: null,
        content: "",
        isReady: true,
        show: function (target, cusopt) {
            if (!MB.isReady) { alert("窗口没准备好"); return; }
            MB.isReady = false;
            var iopt = { id: "", target: target == null ? $(document) : target };
            if (cusopt) {
                cusopt = $.extend(iopt, cusopt);
            }
            cusopt.id = "lud_msgbox_" + lud.util.date.getDate("hhmmssS") + "_" + lud.util.random.getRandomNum(1000, 9999);
            MB.curMsgboxOpt = $.extend(MB.opt, cusopt);
            MB.creater.creat();
        },
        creater: {
            creat: function () {
                MC.show();
                Function("MB.creater.render.loader." + MB.curMsgboxOpt.render + "();")();
            },
            render: {
                loader: {
                    ajax: function () {
                        var opt = MB.curMsgboxOpt;
                        R.post(opt.dataorg, opt.data, function (msg) {
                            MB.content = msg;
                            MB.creater.render.rend();
                        }, opt.target);
                    },
                    iframe: function () { },
                    dom: {},
                    content: {}
                },
                rend: function () {
                    var opt = MB.curMsgboxOpt;
                    var template = "";
                    if (opt.template != "") {
                        template = opt.template;
                    }
                    if (opt.template == "") {
                        template = '<div id="{msgboxid}" data-ismsgbox="1" class="lud-msgbox lud-boxskin-{skin}">';
                        if (opt.toolbar) {
                            template += '<div class="toolbar" nh="0" draghdl="1"><div class="title"><label><i class="{icon}"></i></label><label>{title}</label></div><div class="oprs">{toolbar}</div></div>{content}'
                        }
                        if (!opt.toolbar) {
                            template += '{content}';
                        }
                        template += '</div>';
                    }
                    var toolbar = "";
                    if (opt.toolbar) {
                        if (opt.canHelp) { toolbar += '<div class="tool help"><i class="fal fa-comment-alt-exclamation"></i></div>'; }
                        if (opt.canMax) { toolbar += '<div class="tool max"><i class="fal fa-bring-forward"></i></div>'; }
                        if (opt.canClose) { toolbar += '<div class="tool close" lude-mc=""><i class="fal fa-minus-octagon"></i></div>'; }
                    }
                    template = template.replace("{msgboxid}", opt.id).replace("{skin}", opt.skin).replace("{icon}", opt.icon).replace("{title}", opt.title).replace("{toolbar}", toolbar).replace("{content}", MB.content);
                    if (MB.msgboxsdom == null) MB.msgboxsdom = $(".lud-msgbox-boxs");
                    MB.msgboxsdom.append(template);
                    var $msgbox = MM.getBox(opt.id);
                    MB.creater.render.offsetAndSize.getAutoSize(opt);
                    MB.creater.render.offsetAndSize.setSizeAndOffset($msgbox, opt);
                    MM.setOpt($msgbox, opt);
                    MB.creater.render.offsetAndSize.setContentAutoSize($msgbox);
                    MB.creater.render.event.setEvents($msgbox, opt);
                },
                offsetAndSize: {
                    getAutoSize: function (opt) {
                        if (opt.isAutoSize) {
                            opt.width = $(window).width();
                            opt.height = $(window).height();
                        }
                    },
                    getAutoOffset: function (opt) {
                        var $left = ($(window).width() - opt.width) / 2;
                        var $top = ($(window).height() - opt.height) / 2;
                        $left = $left > 0 ? $left : 0;
                        $top = $top > 0 ? $top : 0;
                        return { left: $left, top: $top };
                    },
                    setSizeAndOffset: function ($msgbox, opt) {
                        $msgbox.width(opt.width);
                        $msgbox.height(opt.height);
                        var $offset = MB.creater.render.offsetAndSize.getAutoOffset(opt);
                        opt.offset = $offset;
                        $msgbox.offset($offset);
                        $msgbox.css("z-index", lud.util.date.getFlag());
                    },
                    setContentAutoSize: function (msgbox) {
                        var $msgbox = MM.getBox(msgbox);
                        if (!$msgbox) return;
                        var defHeight = 0;
                        $msgbox.find("[nh]:visible").each(function () {
                            defHeight += parseInt($(this).attr("nh")) + $(this).height();
                            $(this).css("overflow", "hidden");
                        });
                        var auheight = $msgbox.height() - defHeight;
                        var ahs = $msgbox.find("[ah]");
                        if (ahs.length == 0) {
                            ahs = $msgbox.find(">.content");
                        }
                        ahs.each(function () {
                            var ah = parseInt($(this).attr("ah"));
                            var ahHeight = auheight - (window.isNaN(ah) ? 0 : ah);
                            $(this).height(ahHeight);
                            $(this).css("overflow", "auto");
                            /**
                             子高度自适应
                             */
                            var cahHeight = 0;
                            $(this).find("[cnh]:visible").each(function () {
                                cahHeight += parseInt($(this).attr("cah")) + $(this).height();
                                $(this).css("overflow", "hidden");
                            });
                            $(this).find("[cah]").each(function () {
                                var achHeight = cahHeight - parseInt($(this).attr("cah"));
                                $(this).height(achHeight);
                                $(this).css("overflow", "auto");
                            });
                        });
                    }
                },
                event: {
                    setEvents: function ($msgbox, opt) {
                        if (opt.canDrag) {
                            $msgbox.candrag({
                                handle: "[draghdl]", scope: "body", cbStart: function () {
                                    $msgbox.find(">div:not(.toolbar)").hide();
                                    $msgbox.css("opacity",0.2);
                                }, cbEnd: function () {
                                    $msgbox.find(">div:not(.toolbar)").show();
                                    $msgbox.css("opacity", 1);
                                }
                            });
                        }
                        if (opt.onShow) {
                            Function("$msgbox", opt.onShow)($msgbox);
                        }
                        lud.event.render.init($msgbox);
                        lud.event.render.afterRend($msgbox);
                        MB.isReady = true;
                    },
                    close: function (msgbox, callback) {
                        var $msgbox = MM.getBox(msgbox);
                        var opt = MM.getOpt(msgbox);
                        if (opt.onClose) {
                            Function("$msgbox", opt.onClose)($msgbox);
                        }
                        if (callback) {
                            Function("$msgbox", callback)($msgbox);
                        }
                        lud.util.finder.cleartag($msgbox);
                        MC.hide();
                    },
                    initEvents: function () {
                        $(document).on("click", "[lude-mc]", function () {
                            var callback = $(this).attr("lude-mc");
                            MB.creater.render.event.close($(this), callback.length == 0 ? false : callback);
                        });
                    }
                }
            }
        }
    },
    msgboxs: {
        newmsgbox: function ($target) {
            var opts = {};
            opts.dataorg = $target.attr("lud-msgbox");
            var width = $target.data("width");
            if (width) opts.width = width;
            var height = $target.data("height");
            if (height) opts.height = height;
            var title = $target.data("title");
            if (title) opts.title = title;
            var icon = $target.find("i");
            if (icon) opts.icon = icon.attr("class");
            MB.show($target, opts);
        },
        init: function () {
            $(document).on("click", "[lud-msgbox]", function () {
                MM.newmsgbox($(this));
            });
        },
        getBox: function (msgbox) {
            if (typeof msgbox == "string") { return $("#" + msgbox) };
            if ($(msgbox).data("ismsgbox") == "1") { return msgbox };
            var $msgbox = $(msgbox).closest("div.lud-msgbox");
            if ($msgbox.length > 0) {
                return $msgbox;
            };
            return null;
        },
        getOpt: function (msgbox) {
            var $msgbox = MM.getBox(msgbox);
            if ($msgbox) {
                var opt = $msgbox.data("opt");
                if (opt) { return opt; }
            }
        },
        setOpt: function (msgbox, opt) {
            var $msgbox = MM.getBox(msgbox);
            if ($msgbox) {
                $msgbox.data("opt", opt);
            }
        }
    }
}
var MC = lud.msgbox.cover;
var MB = lud.msgbox.msgbox;
var MM = lud.msgbox.msgboxs;
$(function () {
    lud.msgbox.init();
    MM.init();
})