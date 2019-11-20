/**
 * WEB请求
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.req = {
    post: function (url, data, callback, target) {
        if (!url || url.length < 5) {
            callback("error-lud.req-001:no url!");
            return;
        }
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