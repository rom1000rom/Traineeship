package boot.controllers;


import boot.exceptions.EntityNotFoundException;
import boot.model.Banner;
import boot.services.BannersAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;



/**Класс представляет собой REST-контроллёр, содержащий методы для
 * обработки стандартных Http-запросов в отношении интернет баннеров.
 @author Артемьев Р.А.
 @version 22.09.2019 */
@RestController
@RequestMapping("/banners")
public class BannersController
{
    @Autowired
    private BannersAdminService bannersAdminService;

    @GetMapping
    public ResponseEntity<List<Banner>> getAllBanners(
            @RequestParam(required = false) Integer typeSort)
    {
        List<Banner> banners = bannersAdminService.getAllBanners();
       if(!(StringUtils.isEmpty(typeSort))) //если параметр запроса не пуст
       {
           switch (typeSort)//сортируем по одному из полей баннера
           {
               case 0:
                   banners.sort(Comparator.comparing(Banner::getBannerId));
                   break;
               case 1:
                   banners.sort(Comparator.comparing(Banner::getWidth));
                   break;
               case 2:
                   banners.sort(Comparator.comparing(Banner::getHeight));
                   break;
               case 3:
                   banners.sort(Comparator.comparing(Banner::getLangId));
                   break;
           }
       }

        /*Возвращаем ResponseEntity<List<Banner>>, это более гибкий вариант, чем вернуть
        просто Banner, поскольку для ResponseEntity можно установить Http-статус ответа –
        ResponseEntity.ok() – это 200 или ResponseEntity.status(201).
        В методе body() передается возвращаемая сущность –  это Banner (либо список Banner).
        Под капотом она конвертируется в JSON благодаря тому, что у нас стоит аннотация
        @RestController. Для конвертации под капотом Spring Boot использует библиотеку Jackson
         – она включена благодаря Maven-зависимости spring-boot-starter-web.*/
        return ResponseEntity.ok().body(banners);
    }

    @GetMapping("/{bannerId}")
    public ResponseEntity<Banner> getBanner(@PathVariable("bannerId") Integer bannerId)
    {
       Banner banner = bannersAdminService.getBanner(bannerId);
        if (banner == null)
            throw new EntityNotFoundException("Banners id-" + bannerId + " not found.");
        return ResponseEntity.ok().body(banner);
    }

    @PostMapping
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<Banner> addBanner(@RequestBody @Valid Banner banner)
    {
        Integer id = bannersAdminService.addBanner(banner);
        banner.setBannerId(id);
        return ResponseEntity.status(201).body(banner);
    }

    @PutMapping
    public ResponseEntity<Banner> updateBanner(@RequestBody @Valid Banner banner)
    {
        Integer id = bannersAdminService.updateBanner(banner);
        if (id == null)
            throw new EntityNotFoundException("Banners id-" + id + " not found.");
        return ResponseEntity.ok().body(banner);
    }

    @DeleteMapping(value = "/{bannerId}")
    public ResponseEntity<Banner> deleteBanner(@PathVariable("bannerId") Integer bannerId)
    {
        Banner banner = bannersAdminService.getBanner(bannerId);
        if (banner == null)
            throw new EntityNotFoundException("Banners id-" + bannerId + " not found.");
        bannersAdminService.deleteBanner(bannerId);

        return ResponseEntity.ok().body(banner);
    }

}
