cool = {
    host:"",
    user:{
        get:function(data,callback){
            APP.request({
                url:cool.host+"/get",
                data:JSON.stringify(data),
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