
INSERT INTO banners_changes( banner_id, admin_name, type_change, description_change, date_change)
VALUES ( 1, '1', 'CREATE',
         null, '2016-09-21 22:25:35'::timestamp),
       ( 1, '1', 'UPDATE',
         'New = Banner{bannerId=1, imgSrc=''TEST'', width=1, height=2, targetUrl=''TEST2'', langId=''TEST3''}',
        '2017-09-21 22:25:35'::timestamp),
       ( 1, '2', 'DELETE',
         null, '2016-09-21 22:25:35'::timestamp),
       ( 5, '1', 'CREATE',
         null, '2016-09-21 22:25:35'::timestamp);
