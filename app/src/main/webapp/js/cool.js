cool = {
    host:"/api",
    user:{
        auth:function(data,callback){
            APP.request({
                url:cool.host+"/user/auth",
                contentType: "application/x-www-form-urlencoded",
                data:data,
                cb:function(json){
                    callback(json);
                }
            })
        },
    },
    room:{
        create:function(data,callback){
            APP.request({
                url:cool.host+"/room/create",
                contentType: "application/x-www-form-urlencoded",
                data:data,
                cb:function(json){
                    callback(json);
                }
            })
        },
        list:function(data,callback){
            APP.request({
                url:cool.host+"/room/list",
                data:JSON.stringify(data),
                cb:function(json){
                    callback(json);
                }
            })
        },
        get:function(data,callback){
            APP.request({
                url:cool.host+"/room/get",
                contentType: "application/x-www-form-urlencoded",
                data:data,
                cb:function(json){
                    callback(json);
                }
            })
        },
        olds:function(data,callback){
            APP.request({
                url:cool.host+"/room/olds",
                data:JSON.stringify(data),
                cb:function(json){
                    callback(json);
                }
            })
        },
    },


}