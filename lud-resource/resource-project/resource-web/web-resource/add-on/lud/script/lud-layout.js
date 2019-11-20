/**
 * 布局事件
 * author:sunqinqiu
 * date:2019-01-11
 */
var lud = lud || {};
lud.layout = {
    winlay: {
        width: 0,
        height: 0
    },
    initer: {
        init: function () {
            lud.layout.size.init();
            lud.layout.initer.initContainer(document);
        },
        initContainer: function (container) {
            lud.layout.position.init(container);
        }
    },
    size: {
        init: function () {
            lud.layout.size.initWinSize();
        },
        initWinSize: function () {
            lud.layout.winlay.width = $(window).width();
            lud.layout.winlay.height = $(window).height();
            $(window).resize(function () {
                lud.layout.winlay.width = $(window).width();
                lud.layout.winlay.height = $(window).height();
                lud.layout.initer.initContainer(document);
            });
        }
    },
    position: {
        init: function (container) {
            $(container).find("[lud-layout='center']").each(function () {
                var $target = $(this);
                var hdift = $target.attr("osx-layout-hdif");
                if (!hdift) hdift = 0;
                var hdif = parseInt(hdift);
                var top = (lud.layout.winlay.height - $target.height() - hdif) / 2 - 100;
                if (top < 100) top = 100;
                var wdift = $target.attr("osx-layout-wdif");
                if (!wdift) wdift = 0;
                var wdif = parseInt(wdift);
                var left = (lud.layout.winlay.width - $target.width() - wdif) / 2;
                $target.css({ left: left + "px", top: top + 'px', opacity: 1 });
            });
        }
    }
}
$(function () {
    lud.layout.initer.init(); 
})