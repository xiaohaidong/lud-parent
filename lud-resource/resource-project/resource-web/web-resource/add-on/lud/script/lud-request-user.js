/**
 * WEB请求
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.req = {
    roleCode: "lud-menu",
    getRole: function (target) {
        return lud.util.finder.attr(target, lud.req.roleCode);
    },
    post: function (url, data, callback, target) {
        var $tag = $(target);
        var application = $tag.attr("lud-app");
        if (application) {
            var style = $tag.attr("lud-app-css");
            if (style) {
                lud.util.loader.style("/web-resource/lud-application/" + application + "/style/main.css");
            }
            var script = $tag.attr("lud-app-script");
            if (script) {
                lud.util.loader.script("/web-resource/lud-application/" + application + "/script/main.js", function () {
                    lud.req.postx(url, data, callback, target);
                });
            } else {
                lud.req.postx(url, data, callback, target);
            }
        } else {
            lud.req.postx(url, data, callback, target);
        }
    },
    postx: function (url, data, callback, target) {
        if (!url || url.length < 5) {
            callback("error-lud.req-001:no url!");
            return;
        }
        var role = lud.req.getRole(target);
        if (!data) {
            data = new Array();
        }
        data.push({ "name": lud.req.roleCode, "value": role });
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            data: data,
            statusCode: {
                500: function () {
                    callback("error-lud.req-002:500!");
                },
                404: function () {
                    callback("error-lud.req-003:400!");
                },
                600: function () {
                    alert("error-lud.req-005:no role!\n对不起，错误的权限！\n您可能已经超时或被其他用户踢出系统！");
                    window.location.href = "/";
                }
            }
        }).done(function (msg) {
            if (msg == "error") {
                alert("error-lud.req-004:done-error!");
            }
            if (callback) {
                callback(msg);
            }
        });
    }
};
var R = lud.req;