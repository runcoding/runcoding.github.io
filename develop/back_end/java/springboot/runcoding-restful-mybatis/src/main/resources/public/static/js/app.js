$(function() {

    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
        $.AMUI.fullscreen.toggle();
    });

    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
        $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
    });

    $('.tpl-switch').find('.tpl-switch-btn-view').on('click', function() {
        $(this).prev('.tpl-switch-btn').prop("checked", function() {
            if ($(this).is(':checked')) {
                return false
            } else {
                return true
            }
        })

    })
    String.prototype.replaceAll = function(s1,s2){
        return this.replace(new RegExp(s1,"gm"),s2);
    }

    /**添加全局正则校验*/
    var validatorInit = function (){
        if ($.AMUI && $.AMUI.validator) {
            /** 增加多个正则*/
            $.AMUI.validator.patterns = $.extend($.AMUI.validator.patterns, {
                mobile: /^1[0-9]{10}$/,
                idNumber:/(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|(\d{3}[x|X]))$)|(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)/
            });
        }
    }
    validatorInit();
})
// ==========================
// 侧边导航下拉列表
// ==========================

$('.tpl-left-nav-link-list').on('click', function() {
    $(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
        .end()
        .find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
})
// ==========================
// 头部导航隐藏菜单
// ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
    $('.tpl-left-nav').toggle();
    $('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
})