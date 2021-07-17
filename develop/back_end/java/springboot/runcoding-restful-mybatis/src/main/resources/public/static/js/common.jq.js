/**
 * @author  runcoding
 * @description jquery 方法封装
 * */
(function () {
    var JQ_COM_API = window.JQ_COM_API = {};
    $.ajaxSetup({
        headers: { 'CSRF-TOKEN':"running-ghost" }
    });
    /**
     * ajax json 请求
     * */
    JQ_COM_API.requestByJson = function(req,fun){
    	fun.success = fun.success ||  function(req){console.log('获取成功')};
        fun.cancel  = fun.cancel  ||  function(req){console.log('操作取消')};
        fun.fail    = fun.fail    ||  function(req){console.log('操作失败')};
         $.ajax({
            url:         req.url ,
            type:        req.method      || "POST",
            contentType: req.contentType || 'application/json',
            dataType:    req.dataType    || "json",
            async:       req.async == null ? true : req.async,
            timeout:     req.timeout     || 60000,
            data:        req.data        || {},
            /*beforeSend: function(xhr) {
                 xhr.setRequestHeader("X-CSRF-TOKEN",$('meta[name="csrf-token"]').attr('content'));
            },*/
            success: function (data) {
                var code  = data.status ;
                console.log("请求返回code："+code);
                if( code == 200){
                   fun.success(req,data);
                }else if(code == 401){
                    window.location.href =  "/api/login/login.html?redirect_uri="+window.location.href;
                } else{
                   fun.fail(req,data);
                }
            },
            error: function(xhr, type){
                fun.fail(req,null);
            }
        })
    }
    String.prototype.replaceAll = function(s1,s2){
        return this.replace(new RegExp(s1,"gm"),s2);
    }

})()
