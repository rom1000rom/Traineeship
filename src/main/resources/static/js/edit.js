$(document).ready(function()
{
    //Получаем характеристики выбранного баннера из локальное хранилище
    $.getJSON(//Загружаем JSON - данные с сервера используя AJAX запрос(GET).
        "/banners/" + localStorage.getItem("id"),
        function(data)
        {
            document.getElementById("imgSrc").setAttribute("value", data.imgSrc);
            document.getElementById("width").setAttribute("value", data.width);
            document.getElementById("height").setAttribute("value", data.height);
            document.getElementById("targetUrl").setAttribute("value", data.targetUrl);
            document.getElementById("langId").setAttribute("value", data.langId);
        })

    $("#editBut").click(function(){
            //сформируем JSON
            var JsonData = {
                "bannerId":localStorage.getItem("id"),
                "imgSrc":document.getElementById("imgSrc").value,
                "width":document.getElementById("width").value,
                "height":document.getElementById("height").value,
                "targetUrl":document.getElementById("targetUrl").value,
                "langId":document.getElementById("langId").value
            };

            localStorage.clear();//Очищаем локальное хранилище

            $.ajax({
                type: "PUT",
                url: "/banners",
                dataType: "json",
                contentType : "application/json",
                data: JSON.stringify(JsonData),//можно передать переменную с json в одном из параметров запроса
                success: function(res) {//функция выполняется при удачном заверщение
                    window.location.replace("/");//Возвращаемся на главную страницу
                }
            });
    });
});