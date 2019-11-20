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
lud.userCenterWinx = {
    run: function () {
        
    },
    init: function () {
        $(document).on("click", ".apps li", function () {
            $("#mudl_user_center_winx_comp_head_workspace").show();
        });
        $(document).on("click", "#mudl_user_center_winx_comp_head_workspace_menu .menu ul li.item-2", function () {
            var $this = $(this);
            var code = $this.data("code");
            var $icon = $this.find("span i");
            var $iconAll = $("#mudl_user_center_winx_comp_head_workspace_menu .item-2 span i");
            $iconAll.removeClass("fa-angle-double-down");
            $iconAll.addClass("fa-angle-double-right");
            $icon.removeClass("fa-angle-double-right");
            $icon.addClass("fa-angle-double-down");
            var $menus = $this.siblings("[data-code^='" + code + "']");
            $this.siblings(".item-4").hide();
            $this.siblings(".selected").removeClass("selected");
            $this.addClass("selected");
            $menus.addClass("selected");
            $menus.slideDown("fast");
            $menus.first().click();
        });
    },
    workspace: {
        showDesk: function () {
            $("#mudl_user_center_winx_comp_head_workspace").hide();
        }
    }
};
$(function () {
    lud.userCenterWinx.init();
})
