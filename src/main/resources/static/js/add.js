$(document).ready(function()
{
    $("#addBut").click(function(){
        if(document.getElementById("imgSrc").value==="" ||
            document.getElementById("width").value===""||
            document.getElementById("height").value===""||
            document.getElementById("targetUrl").value===""||
            document.getElementById("langId").value==="")
        {
            valert("Введите данные!");
        }
        else {
        //сформируем JSON
        var JsonData = {
            "bannerId":0,
            "imgSrc":document.getElementById("imgSrc").value,
            "width":document.getElementById("width").value,
            "height":document.getElementById("height").value,
            "targetUrl":document.getElementById("targetUrl").value,
            "langId":document.getElementById("langId").value
        };
        $.ajax({
            type: "POST",
            url: "/banners",
            dataType: "json",
            contentType : "application/json",
            data: JSON.stringify(JsonData),//можно передать переменную с json в одном из параметре запроса
            success: function(res) {//функция выполняется при удачном заверщение
                window.location.replace("/");//Возвращаемся на главную страницу
            }
        });
        }
    });
});