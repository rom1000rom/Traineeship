$(document).ready(function()//Код который будет выполнен после полной загрузки документа
{
    $.getJSON(//Загружаем JSON - данные с сервера используя AJAX запрос(GET).
        "/bannersChanges",
        function(data)
        {
            for (var i in data) //Проходим по массиву JSON-объектов
            {
                var row = data[i];
                var y = i;
                ++y;
                var x = document.getElementById("tabHistory").insertRow(y);//Добавляем строку в таблицу
                x.insertCell(0);//Добавляем ячейки
                x.insertCell(1);
                x.insertCell(2);
                x.insertCell(3);
                x.insertCell(4);
                x.insertCell(5);
                //Помещаем в ячейки значения, полученные от сервера
                document.getElementById("tabHistory").rows[y].cells[0].innerHTML = row.bannerChangeId;
                document.getElementById("tabHistory").rows[y].cells[1].innerHTML = row.bannerId;
                document.getElementById("tabHistory").rows[y].cells[2].innerHTML = row.adminName;
                document.getElementById("tabHistory").rows[y].cells[3].innerHTML = row.typeChange;
                document.getElementById("tabHistory").rows[y].cells[4].innerHTML = row.descriptionChange;
                document.getElementById("tabHistory").rows[y].cells[5].innerHTML = row.dateChange;
            }
        })

    $("#applyBut").click(function(){
        var bannerId = document.getElementById("bannerId").value;
        var adminName = document.getElementById("adminName").value;

        //Загружаем JSON - данные с сервера используя AJAX запрос(GET).
        $.getJSON(
            "/bannersChanges",
            function(data)
            {
                for (var i in data) //Проходим по массиву JSON-объектов
                {
                    var row = data[i];
                    var y = i;
                    ++y;
                    var table = document.getElementById("tabHistory");
                    table.deleteRow(y);
                    var x = table.insertRow(y);//Добавляем строку в таблицу
                    table.rows[y].setAttribute("id", i);
                    x.insertCell(0);//Добавляем ячейки
                    x.insertCell(1);
                    x.insertCell(2);
                    x.insertCell(3);
                    x.insertCell(4);
                    x.insertCell(5);
                    //Помещаем в ячейки значения, полученные от сервера
                    table.rows[y].cells[0].innerHTML = row.bannerChangeId;
                    table.rows[y].cells[1].innerHTML = row.bannerId;
                    table.rows[y].cells[2].innerHTML = row.adminName;
                    table.rows[y].cells[3].innerHTML = row.typeChange;
                    table.rows[y].cells[4].innerHTML = row.descriptionChange;
                    table.rows[y].cells[5].innerHTML = row.dateChange;
                    if(row.bannerId!=bannerId&&bannerId!="")
                    {
                        $("#"+ i).hide().attr("class", "row");
                    }
                    if(row.adminName!=adminName&&adminName!="")
                    {
                        $("#"+ i).hide().attr("class", "row");
                    }
                }
            });
    });

    $("#resetBut").click(function()
    {
        $(".row").show();
    });
});