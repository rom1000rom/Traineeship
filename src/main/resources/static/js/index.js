
    $(document).ready(function()//Код который будет выполнен после полной загрузки документа
    {
        $.getJSON(//Загружаем JSON - данные с сервера используя AJAX запрос(GET).
            "/banners",
            function(data)
            {
                for (var i in data) //Проходим по массиву JSON-объектов
                {
                    var row = data[i];
                    var y = i;
                    ++y;
                    var x = document.getElementById("tab").insertRow(y);//Добавляем строку в таблицу
                    x.insertCell(0);//Добавляем ячейки
                    x.insertCell(1);
                    x.insertCell(2);
                    x.insertCell(3);
                    x.insertCell(4);
                    x.insertCell(5);
                    x.insertCell(6);
                    //Помещаем в ячейки значения, полученные от сервера
                    document.getElementById("tab").rows[y].cells[0].innerHTML = row.bannerId;
                    document.getElementById("tab").rows[y].cells[1].innerHTML = row.imgSrc;
                    document.getElementById("tab").rows[y].cells[2].innerHTML = row.width;
                    document.getElementById("tab").rows[y].cells[3].innerHTML = row.height;
                    document.getElementById("tab").rows[y].cells[4].innerHTML = row.targetUrl;
                    document.getElementById("tab").rows[y].cells[5].innerHTML = row.langId;
                    document.getElementById("tab").rows[y].cells[6].innerHTML =
                        "<input type=\"radio\" name=\"radio\" id=" + row.bannerId + ">";
                }
            })

        $("#deleteBut").click(function()
        {
            var gr = document.getElementsByName('radio');//Получаем список всех элементов radio
            var count = gr.length;
            var index = -1;
            for(var i=0; i < count; i++)
            {
                if (gr[i].checked)//Ищем тот, который выбран
                {
                    index = i;
                }
            }
            if(index <  0)
            {
                valert("Баннер не выбран!");
            }
            else
            {
                    $.ajax({
                        type: "DELETE",
                        url: "/banners/" + gr[index].getAttribute("id"),
                        success: function () {//функция выполняется при удачном заверщение
                            location.reload();//Перезагружаем страницу
                        }
                    });
            }
        });

        $("#editBut").click(function()
        {
            var gr = document.getElementsByName('radio');//Получаем список всех элементов radio
            var count = gr.length;
            var index = -1;
            for(var i=0; i < count; i++)
            {
                if (gr[i].checked)//Ищем тот, который выбран
                {
                    index = i;
                }
            }
            if(index <  0)
            {
                valert("Баннер не выбран!");
            }
            else
            {
                //Сохраняем id выбранного баннера в локальное хранилище
                localStorage.setItem('id', gr[index].getAttribute("id"));
                document.location.href = "/edit";//Переходим на страницу редактирования
            }
        });

        $("#sort").change(function()
        {
            var typeSort = document.getElementById("sort").selectedIndex;
            $.getJSON(//Загружаем JSON - данные с сервера используя AJAX запрос(GET).
                "/banners?typeSort=" + typeSort,
                function(data)
                {
                    for (var i in data) //Проходим по массиву JSON-объектов
                    {
                        var row = data[i];
                        var y = i;
                        ++y;
                        var table = document.getElementById("tab");
                        table.deleteRow(y);
                        var x = table.insertRow(y);//Добавляем строку в таблицу
                        x.insertCell(0);//Добавляем ячейки
                        x.insertCell(1);
                        x.insertCell(2);
                        x.insertCell(3);
                        x.insertCell(4);
                        x.insertCell(5);
                        x.insertCell(6);
                        //Помещаем в ячейки значения, полученные от сервера
                        document.getElementById("tab").rows[y].cells[0].innerHTML = row.bannerId;
                        document.getElementById("tab").rows[y].cells[1].innerHTML = row.imgSrc;
                        document.getElementById("tab").rows[y].cells[2].innerHTML = row.width;
                        document.getElementById("tab").rows[y].cells[3].innerHTML = row.height;
                        document.getElementById("tab").rows[y].cells[4].innerHTML = row.targetUrl;
                        document.getElementById("tab").rows[y].cells[5].innerHTML = row.langId;
                        document.getElementById("tab").rows[y].cells[6].innerHTML =
                            "<input type=\"radio\" name=\"radio\" id=" + row.bannerId + ">";
                    }
                });


        });
    });


