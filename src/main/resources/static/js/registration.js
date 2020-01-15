$(document).ready(function()
{
    $("#regBut").click(function(){
        if(document.getElementById("login").value==="" ||
            document.getElementById("password").value==="")
        {
            valert("Введите данные пользователя!")
        }
        else {
            //сформируем JSON
            var JsonData = {
                "appUserId": 0,
                "name": document.getElementById("login").value,
                "encrytedPassword": document.getElementById("password").value
            };
            $.ajax({
                type: "POST",
                url: "/users",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(JsonData),//можно передать переменную с json в одном из параметре запроса
                success: function (res) {
                    if (res.appUserId == null) {
                        valert("Пользователь с именем " + res.name + " уже существует");
                    } else {
                        valert("Пользователь успешно добавлен");
                    }
                }
            });
        }
    });
});