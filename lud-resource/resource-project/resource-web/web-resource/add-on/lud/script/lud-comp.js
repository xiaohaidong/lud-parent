/**
 * 事件绑定
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.comp = {
    init: function () {
        lud.comp.tab.init();
        lud.comp.form.init();
        lud.comp.table.init();
        lud.comp.select.init();
    },
    distroy: function (target) {
        lud.comp.echart.dispose(target);
    },
    initContainer: function (container) {
        lud.comp.canver.init(container);
        lud.comp.drager.init(container);
        lud.comp.table.merge(container);
        lud.comp.map.init(container);
        lud.comp.select.initvalue(container);
    },
    tab: {
        init: function () {
            $(document).on("click", "ul[lud-tab] li", function () {
                var $ul = $(this).closest("ul");
                $ul.find("li").focusout();
                var tab = $ul.attr("lud-tab");
                if (tab.indexOf("c") >= 0) {
                    $ul.find("li").removeClass("actived");
                }
                $(this).addClass("actived");
                if (tab.indexOf("f") >= 0) {
                    var $forform = $($(this).closest("[data-forform]").data("forform"));
                    var $forpara = lud.util.finder.data($(this), "forinput");
                    if ($forpara) {
                        var $forinput = $forform.find("[name='" + $forpara + "']");
                        $forinput.val($(this).data("value"));
                    }
                    if (tab.indexOf("r") >= 0) {
                        $forform.find("[name='pager-index']").val(1);
                    }
                    lud.comp.form.getdata($forform);
                }
                if (tab.indexOf("e") >= 0) {
                    var index = $(this).index();
                    var econtent = $(this).closest("[lud-ep]");
                    var ep = econtent.attr("lud-ep");
                    if (ep == "wrap") {
                        econtent.find(".lud-tab-pannel >div").hide();
                        econtent.find(".lud-tab-pannel >div:eq(" + index + ")").show();
                    }
                    if (ep == "next") {
                        econtent = econtent.siblings(".lud-tab-pannel");
                        econtent.find(">div").hide();
                        econtent.find(">div:eq(" + index + ")").show();
                    }
                }
            });
        }
    },
    form: {
        init: function () {
            $(document).on("click", "[lud-submit-form]", function () {
                var $form = $(this).closest("form");
                lud.comp.form.getdata($form);
            });
        },
        fresh: function (target) {
            var $form = $($(target).closest("[data-forform]").data("forform"));
            lud.comp.form.getdata($form);
        },
        getdata: function ($form) {
            var url = $form.data("action");
            var data = $form.serializeArray();
            var target = $(lud.util.finder.attr($form, "lud-render-target"));
            lud.event.render.rendContent(url, data, target, target);
        }
    },
    canver: {
        init: function (container) {
            container.find("div[canvas]").each(function () {
                lud.comp.canver.draw($(this));
            });
        },
        getRender: function (target) {
            var char = echarts.init(document.getElementById(target));
            return char.getZr();
        },
        draw: function (canversContainer) {
            var canvasid = canversContainer.attr("canvas");
            var zrender = lud.comp.canver.getRender(canvasid);
            zrender.clear();
            lud.comp.canver.line.draw(zrender, canversContainer);
        },
        node: {
            getCenterPostion: function (node) {
                var x = node.position().left + node.width() / 2;
                var y = node.position().top + node.height() / 2;
                return { x: x, y: y };
            }
        },
        line: {
            draw: function (zrender, canversContainer) {
                var nodes = canversContainer.find(".nodes");
                $(canversContainer).find("line").each(function () {
                    var from = nodes.find("[node-id='" + $(this).data("from") + "']");
                    var to = nodes.find("[node-id='" + $(this).data("to") + "']");
                    var color = $(this).data("color");
                    var linw = $(this).data("linw");
                    zrender.add(lud.comp.canver.line.getBezierCurve(lud.comp.canver.line.getLinePostionByNodes(from, to), color, linw));
                });
            },
            getBezierCurve: function (node, color, width) {
                return new echarts.graphic.BezierCurve({
                    shape: { x1: node.x1, y1: node.y1, cpx1: (node.x1 + node.x2) / 2, cpy1: node.y1, cpx2: (node.x1 + node.x2) / 2, cpy2: node.y2, x2: node.x2, y2: node.y2 },
                    style: { stroke: color, lineWidth: width }
                });
            },
            getLinePostionByNodes: function (from, to) {
                var p1 = lud.comp.canver.node.getCenterPostion(from);
                var p2 = lud.comp.canver.node.getCenterPostion(to);
                return { x1: p1.x, y1: p1.y, x2: p2.x, y2: p2.y };
            }
        }
    },
    drager: {
        getBinder: function (target) {
            var binder = $(target).closest("[candraglist]");
            if (!binder) {
                binder = $(target);
            }
            return binder;
        },
        init: function (container) {
            container.find("[can] [candrag]").each(function () {
                var binder = lud.comp.drager.getBinder(this);
                var opt = {};
                if (binder.attr("handle")) { opt.handle = binder.attr("handle"); }
                var callbackstring = binder.attr("callback");
                if (callbackstring) {
                    opt.cbEnd = new Function("obj", callbackstring + "(obj);");
                }
                $(this).candrag(opt);
            });
        },
        updatePostion: function (obj) {
            var $obj = $(obj);
            var parent = $obj.closest("[canvas]");
            lud.comp.canver.draw(parent);
            var binder = lud.comp.drager.getBinder(obj);
            var updateurl = binder.attr("update");
            var data = new Array();
            data.push({ "name": "code", "value": $obj.attr("node-id") });
            data.push({ "name": "cdnx", "value": $obj.position().left / 10 });
            data.push({ "name": "cdny", "value": $obj.position().top / 10 });
            R.post(updateurl, data, function (msg) {

            }, $obj);
        }
    },
    echart: {
        show: function (con, skin, option) {
            var char = echarts.init(document.getElementById(con), skin);
            char.setOption(option);
        },
        dispose: function (target) {
            $(target).find("[lud-echart]").each(function () {
                var id = $(this).attr("id");
                var echar = echarts.getInstanceByDom(document.getElementById(id));
                echar.dispose();
            });
        },
        setFingerValue: function (target) {
            var id = $(target).attr("id");
            var echar = echarts.getInstanceByDom(document.getElementById(id));
            var option = echar.getOption();
            option.series[0].data[0].value = $(target).data("value");
            echar.setOption(option, true);
        },
        setRuntimeValue: function (target) {
            var id = $(target).attr("id");
            var echar = echarts.getInstanceByDom(document.getElementById(id));
            var option = echar.getOption();
            var ndata = {
                name: osxc.util.date.getDate("yyyy/MM/dd hh:mm:ss"),
                value: [lud.util.date.getDate("yyyy/MM/dd hh:mm:ss"), $(target).data("value")]
            }
            option.series[0].data.push(ndata);
            echar.setOption(option, true);
        },
        setRuntimeWarning: function (target) {
            var value = $(target).data("value");
            if (value) {
                $(target).addClass("actived");
            } else {
                $(target).removeClass("actived");
            }
        },
        setRuntimeStatus: function (target) {
            var value = $(target).data("value");
            if (value) {
                $(target).addClass("run");
            } else {
                $(target).removeClass("run");
            }
        }
    },
    table: {
        merge: function (container) {
            var tables = $(container).find("table[merge]");
            tables.each(function () {
                var merges = $(this).attr("merge").split(";");
                var mergecolss = $(this).attr("mergeclos").split(";");
                for (var m = 0; m < merges.length; m++) {
                    var merge = merges[m];
                    var mergecols = mergecolss[m].split(",");
                    var tr = $(this).find("tbody tr");
                    for (var i = 1; i < tr.length; i++) {
                        var t = i - 1;
                        var prerowtext = tr.eq(t).find("td:eq(" + merge + ")").text();
                        var curowtext = tr.eq(i).find("td:eq(" + merge + ")").text();
                        if (prerowtext.length == 0 || curowtext.length == 0) break;
                        if (prerowtext == curowtext) {
                            while (prerowtext == tr.eq(i).find("td:eq(" + merge + ")").text()) {
                                for (var j = mergecols.length; j >= 0; j--) {
                                    var col = tr.eq(t).find("td:eq(" + mergecols[j] + ")");
                                    var hcol = tr.eq(i).find("td:eq(" + mergecols[j] + ")");
                                    hcol.hide();
                                    var rowspan = col.attr("rowspan");
                                    if (rowspan) {
                                        rowspan = parseInt(rowspan);
                                    } else {
                                        rowspan = 1;
                                    }
                                    col.attr("rowspan", rowspan + 1);
                                }
                                i++;
                            }
                        }
                    }
                }
            });
        },
        init: function () {
            $(document).on("click", "[lud-select]", function () {
                var keys = $(this).closest("table").find("input[name='_keys']");
                keys.each(function () {
                    var checked = $(this).prop("checked");
                    $(this).prop("checked", !checked);
                });
            });
            $(document).on("click", "[lud-exet]", function () {
                var target = $(this);
                if (lud.event.conmsg.con(target)) {
                    var value = new Array();
                    var i = 0;
                    var keys = target.closest("[data-forform]").find("table input[name='_keys']:checked");
                    keys.each(function () {
                        value.push($(this).val());
                    });
                    if (keys.length == 0) {
                        alert("没有选择任何数据！");
                        return;
                    }
                    var url = target.attr("lud-exet");
                    var data = new Array();
                    data.push({ "name": "keys", "value": value });
                    lud.event.binder.exe(url, data, target);
                }
            });
        }
    },
    map: {
        init: function (container) {
            $(container).find("div[lud-map]").each(function () {
                var $maptag = $(this);
                var map = new BMap.Map($maptag.attr("id"));
                var point = new BMap.Point(parseFloat($maptag.data("lng")), parseFloat($maptag.data("lat")));
                map.centerAndZoom(point, parseInt($maptag.data("zoom")));
                map.enableScrollWheelZoom();
                var traffic = new BMap.TrafficLayer();
                map.addTileLayer(traffic);
            });
        },
        destroyMap: function () {
        }
    },
    select: {
        initvalue: function (container) {
            $(container).find(".lud-select-wrap").each(function () {
                var value = $(this).find("input:hidden").val();
                if (value == "") {
                    $(this).find(".value").text("请选择......");
                } else {
                    var $text = $(this).find("li[data-value='" + value + "']");
                    if ($text.length > 0) {
                        $(this).find(".value").text($text.text());
                    }
                }
            });
        },
        init: function () {
            $(document).on("click", function (e) {
                var _con = $('.lud-select-wrap');
                if (!_con.is(e.target) && _con.has(e.target).length === 0) {
                    $(".lud-select-wrap .select:visible").hide();
                }
            });
            $(document).on("click", ".lud-select-wrap .value", function () {
                $(".lud-select-wrap .select:visible").hide();
                var $parent = $(this).closest(".lud-select-wrap");
                var $select = $parent.find(".select");
                $select.find("input").val("");
                $select.find("li").show();
                $select.css("z-index", lud.util.date.getFlag());
                $select.show();
                var value = $parent.find("input:hidden").val();
                $select.find("li").removeClass("actived");
                $select.find("li[data-value='" + value + "']").addClass("actived");
            });
            $(document).on("click", ".lud-select-wrap .select li ", function () {
                var $parent = $(this).closest(".lud-select-wrap");
                $parent.find(".value").text($(this).text());
                $parent.find("input:hidden").val($(this).data("value"));
                $parent.find(".select").hide();
            });
            $(document).on("keyup", ".lud-select-wrap .select input", function () {
                var value = $(this).val();
                var ovalue = $(this).data("value");
                if (value != ovalue) {
                    $(this).data("value", value);
                    var $select = $(this).closest(".select");
                    var $options = $select.find("li");
                    if (value == "") {
                        $options.show();
                    }
                    else {
                        $options.hide();
                        $select.find("li[text *='" + value + "']").show();
                    }
                }
            });
        }
    }
}
$(function () {
    lud.comp.init();
})