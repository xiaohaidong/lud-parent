/**
 * 基础类
 * author:sunqinqiu
 * date:2019-01-11
 */
var MC = false;
var lud = lud || {};
lud = {
    
},
lud.util = {
    reurl: function (url) {
        window.document.location.href = url;
    },
    /**
     * 查找对象
     */
    loader: {
        script: function (url, callback) {
            $LAB.script(url).wait(function () {
                if (callback) {
                    callback();
                }
            });
        },
        style: function (url) {
            if ($("link[href='" + url + "']").length === 0) {
                var ref = document.createElement("link");
                ref.setAttribute("rel", "stylesheet");
                ref.setAttribute("type", "text/css");
                ref.setAttribute("href", url);
                document.getElementsByTagName("head")[0].appendChild(ref);
            }
        }
    },
    finder: {
    	/**
    	 * 查找对象属性
    	 */
        attr: function (target, attr) {
            var $tag = $(target);
            if ($tag.attr(attr)) return $tag.attr(attr);
            $tag = $tag.closest("[" + attr + "]");
            if ($tag) return $tag.attr(attr);
            return "";
        },
        data: function (target, data) {
            var $tag = $(target);
            if ($tag.data(data)) return $tag.data(data);
            $tag = $tag.closest("[data-" + data + "]");
            if ($tag) return $tag.data(data);
            return "";
        },
        /**
         * 清除对象的子元素以及数据项
         */
        clear: function (target) {
            lud.util.finder.clearother(target);
            var $tag = $(target);
            $tag.children().removeData();
            $.event.remove($tag.children());
            $tag.children().remove();
        },
        cleartag: function (target) {
            lud.util.finder.clearother(target);
            var $tag = $(target);
            $tag.children().removeData();
            $.event.remove($tag.children());
            $tag.children().remove();
            $tag.removeData();
            $.event.remove($tag);
            $tag.remove();
        },
        clearother: function (target) {
            if (lud.comp) {
                lud.comp.distroy(target);
            }
        }
    },
    /**
     * 随机数
     */
    random: {
        getRandomNum: function (min, max) {
            var range = max - min;
            var rand = Math.random();
            return (min + Math.round(rand * range));
        }
    },
    /**
     * 时间操作
     */
    date: {
    	/**
    	 * rundate的毫秒数
    	 */
        rundate: new Date().getTime(),
        /**
         * 格式化时间
         */
        getDate: function (format) {
            var digital = new Date();
            return digital.format(format);
        },
        /**
         * 得到时间戳
         */
        getFlag: function () {
            return 50 + parseInt((new Date().getTime() - this.rundate) / 10);
        }
    },
    /**
     * 数字金额大写替换
     */
    convert: {
        toRMB: function (n) {
            var fraction = ['角', '分'];
            var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
            var unit = [['元', '万', '亿'], ['', '拾', '佰', '仟']];
            var head = n < 0 ? '欠' : '';
            n = Math.abs(n);
            var s = '';
            for (var i = 0; i < fraction.length; i++) {
                s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
            }
            s = s || '整';
            n = Math.floor(n);

            for (var i = 0; i < unit[0].length && n > 0; i++) {
                var p = '';
                for (var j = 0; j < unit[1].length && n > 0; j++) {
                    p = digit[n % 10] + unit[1][j] + p;
                    n = Math.floor(n / 10);
                }
                s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
            }
            return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
        }
    }
};
/**
 * Date 转化为指定格式的String 
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
/**
 * 复制功能
 */
function copyToClipboard(s) {
    if (window.clipboardData) {
        window.clipboardData.setData('text', s);
    } else {
        (function (s) {
            document.oncopy = function (e) {
                e.clipboardData.setData('text', s);
                e.preventDefault();
                document.oncopy = null;
            }
        })(s);
        document.execCommand('Copy');
    }
}