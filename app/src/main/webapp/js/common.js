APP = {
    version: "1.0.0",
    isDoing: false,
    request: function (options) {
        $.ajax({
            type: options.type == undefined ? "POST" : options.type,
            url: options.url,
            contentType: options.contentType == undefined ? "application/json;charset=UTF-8" : options.contentType,
            dataType: options.dataType ? "json" : options.dataType,
            cache: options.cache ? "false" : options.cache,
            data: options.data,
            async: options.async ? true : options.async,
            timeout: 60 * 1000,
            beforeSend: function (XMLHttpRequest) {
                if (APP.isDoing) {
                    return;
                }
                APP.isDoing = true;
            },
            success: function (json) {
                if (!json) {
                    return;
                }
                if (typeof options.cb == "function") {
                    if(json.error_code == -1)
                        location.href = "login.html";
                    options.cb(json);
                    return;
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                APP.isDoing = false;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("error");
            }
        });

    }
}
