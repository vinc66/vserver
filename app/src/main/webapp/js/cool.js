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
        index:function(data,callback){
            APP.request({
                url:cool.host+"/set",
                data:JSON.stringify(data),
                cb:function(json){
                    callback(json);
                }
            })
        }
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
        index:function(data,callback){
            APP.request({
                url:cool.host+"/set",
                data:JSON.stringify(data),
                cb:function(json){
                    callback(json);
                }
            })
        }
    },


}