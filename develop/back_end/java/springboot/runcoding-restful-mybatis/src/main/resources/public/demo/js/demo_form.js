$(function() {
    var  param = COM_FUNC_API.getQueryParam();
    /***表单验证*/
    var $form = $('.am-form');
    var $tooltip = $('<div id="vld-tooltip">提示信息！</div>');
    $tooltip.appendTo(document.body);
    $form.validator({
        submit: function() {
            var formValidity = this.isFormValid();
            if (formValidity) {
                // 验证成功的逻辑
                var $subbtn = $(".submit-btn");
                $subbtn.button('loading');
                setTimeout(function(){
                    $subbtn.button('reset');
                }, 3000);
                doAddInfo();
            } else {
                /**验证失败的逻辑*/
                console.error('表单验证失败');
            }
            /**阻止原生form提交*/
            return false;
        }
    });
    var validator = $form.data('amui.validator');

    $form.on('focusin focusout', '.am-form-error input', function(e) {
        var status = true;
        if (e.type === 'focusin') {
            status = false;
            var $this = $(this);
            var offset = $this.offset();
            var msg = $this.data('foolishMsg') || validator.getValidationMessage($this.data('validity'));
            $tooltip.text(msg).show().css({
                left: offset.left + 10,
                top: offset.top + $(this).outerHeight() + 10
            });
            setTimeout(function(){
                $tooltip.hide();
            }, 3000);
        } else {
            $tooltip.hide();
        }
    });
    /**新增应用操作*/
    var doAddInfo = function(){
        var loading = COM_FUNC_API.modalLoading({});
        JQ_COM_API.requestByJson({
            url:"http://localhost:8888/user/put",
            method:"PUT",
            data:JSON.stringify({
               name:$('#name').val()
            })
        },{
            success:function(){
                loading.modal("close");
                COM_FUNC_API.modalAlert({body:"请求成功"});
            },
            fail: function(req,data){
                loading.modal("close");
                $(".submit-btn").button('reset');
                if(!data){
                    COM_FUNC_API.modalAlert({body:"请求出错请重试"});
                    return;
                }
            }
        })
    }

})


