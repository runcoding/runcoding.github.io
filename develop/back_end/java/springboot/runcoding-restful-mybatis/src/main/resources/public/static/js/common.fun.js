/**
 * @author  runcoding
 * @description 方法封装
 * */
(function () {
    var COM_FUNC_API = window.COM_FUNC_API = {};
    /**分页*/
    COM_FUNC_API.page = function (pageTable,cOption) {
        pageTable = pageTable || $("#pageTable");

        /**分页加载前触发，如果没有执行next()则中断加载*/
        var pageBefore = cOption.before || function(context, next) {
                console.log('开始加载...'+context.option.curr);
                next();
        };
        /**渲染[context：对this的引用，$element：当前元素，index：当前索引]*/
        var pageRender = cOption.render ||  function(context, $element, index) {
                /**虽然上面设置了last的文字为尾页，但是经过render处理，结果变为最后一页*/
                if (index == 'last') {
                    $element.find('a').html('最后一页');
                    /**如果有返回值则使用返回值渲染*/
                    return $element;
                }
                /**没有返回值则按默认处理*/
                return false;
        };
        /**加载完成后触发*/
        var pageAfter = cOption.after || function(context, next) {
                next();
        };
        /**触发分页后的回调，如果首次加载时后端已处理好分页数据则需要在after中判断终止或在jump中判断first是否为假*/
        var pageJump = cOption.jump   || function(context, first) {
                console.log('当前第：' + context.option.curr + "页");
            }
        var option = {
            pages:  cOption.pages   || 1,         /**页数*/
            curr:   cOption.curr    || 1,         /**当前页*/
            theme:  cOption.theme   || 'default', /**主题*/
            groups: cOption.groups  || 5,         /**连续显示分页数*/
            prev:   cOption.prev    || '上一页',  /**若不显示，设置false即可*/
            next:   cOption.next    || '下一页',  /**若不显示，设置false即可*/
            first:  cOption.first   || "首页",    /**若不显示，设置false即可*/
            last:   cOption.last    || "尾页",    /**若不显示，设置false即可*/
            before: pageBefore,                   /**分页加载前触发(ajax异步加载数据)*/
            render: pageRender,                   /**分页组件*/
            after:  pageAfter,                    /**分页加载后触发*/
            jump:   pageJump                      /**触发分页后的回调*/
        };
        /**加载分页*/
        pageTable.page(option);
    }
    /**加载中模态框*/
    COM_FUNC_API.modalLoading =  function(target){
        var $loading = AMUI.dialog.loading();
        setTimeout(function() {
            $loading.modal('close');
        }, target.timeout||3000);
        return $loading;
    };
    /**删除模态框*/
    COM_FUNC_API.modalDelete =  function(target){
        return AMUI.dialog.confirm({
            title:   target.head || "删除提示",
            content: target.body || '你，确定要删除这条记录吗？',
            onConfirm: target.confirm || function() {
                console.log("同意删除");
            },
            onCancel: function() {
                console.log("删除取消")
            }
        });
    };
    /**alert模态框*/
    COM_FUNC_API.modalAlert = function(target){
        return AMUI.dialog.alert({
            title: target.head || "消息提示",
            content: target.body || '',
            onConfirm: function() {
                console.log('close');
            }
        });
    }
    /**显示富文本内容模态框*/
    COM_FUNC_API.modalDialog = function(target){
        var modal = target.tplModal || $('#tpl_modal').text();
        modal = modal.replace("{{class}}","am-modal-show")
            .replace("{{head}}",target.head || '')
            .replace("{{body}}",target.body || '')
            .replace("{{footer}}",target.footer || '');
        $('#mk-modal').remove();
        $('body').append(modal);
        $('#mk-modal').modal();
        $('#mk-modal').on('closed.modal.amui', function(){
            $('.am-dimmer').hide();
        });
    }
    /**获取请求参数*/
    COM_FUNC_API.getQueryParam = function () {
        /**获取url中"?"符后的字串*/
        var url = location.search;
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            var arr = str.split("&");
            for(var i = 0; i < arr.length; i ++) {
                var obj = arr[i].split("=");
                theRequest[obj[0]] = decodeURIComponent(obj[1]);
            }
        }
        return theRequest;
    }
})();
