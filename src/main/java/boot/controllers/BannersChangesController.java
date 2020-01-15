package boot.controllers;



import boot.model.BannerChange;
import boot.services.BannersAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**Класс представляет собой REST-контроллёр, содержащий методы для
 * обработки Http-запросов в отношении действий над интернет баннерами.
 @author Артемьев Р.А.
 @version 27.09.2019 */
@RestController
@RequestMapping("/bannersChanges")
public class BannersChangesController
{
    @Autowired
    private BannersAdminService bannersAdminService;

    @GetMapping
    public ResponseEntity<List<BannerChange>> getAllBannersChanges()
    {
        List<BannerChange> bannersChange = bannersAdminService.getAllBannersChanges();
        return ResponseEntity.ok().body(bannersChange);
    }

}
