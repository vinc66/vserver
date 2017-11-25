<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<h1>THIS IS HOME</h1>
<h2></h2>
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/common.js"></script>
<script src="js/cool.js"></script>
<script>
    $(function () {
        var data = {};
        cool.user.get(data, function (json) {
            $("h2").html(json);
        })
    })
</script>

</body>
</html>