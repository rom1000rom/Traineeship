package services;

import boot.dao.AppUserDAO;
import boot.dao.BannerChangeDAO;
import boot.dao.BannerDAO;
import boot.model.AppUser;
import boot.model.Banner;
import boot.model.BannerChange;
import boot.services.BannersAdminService;
import boot.services.BannersAdminServiceImpl;
import boot.services.CurrentPrincipalService;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.easymock.Mock;
import static junit.framework.Assert.assertNull;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BannersAdminServiceImplTest extends EasyMockSupport
{
    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @TestSubject
    private BannersAdminServiceImpl testedObject = new BannersAdminServiceImpl();

    @Mock
    private BannerDAO bannerDAO;

    @Mock
    private BannerChangeDAO bannerChangeDAO;

    @Mock
    private AppUserDAO appUserDAO;

    @Mock
    private CurrentPrincipalService currentPrincipalService;

    @Test
    public void testGetAllBanners()
    {
        List<Banner> listBanner= new ArrayList<>();
        listBanner.add(new Banner(2, "TEST", 2, 3,
                "TEST2", "TEST3"));
        expect(bannerDAO.getAllBanners()).andReturn(listBanner);
        replayAll();

        assertThat(testedObject.getAllBanners(), is(listBanner));
    }

    @Test
    public void testGetBannerIsNull()
    {
        expect(bannerDAO.getBanner(null)).andReturn(null);
        replayAll();
        assertNull(testedObject.getBanner(null));
    }

    @Test
    public void testGetBanner()
    {
        Banner expected = new Banner(1, "TEST", 0, 0,
                "TEST", "TEST");
        expect(bannerDAO.getBanner(1)).andReturn(expected);
        replayAll();

        assertThat(testedObject.getBanner(1), is(expected));
    }

    @Test
    public void testGetBannerNotExist()
    {
        expect(bannerDAO.getBanner(0)).andReturn(null);
        replayAll();
        assertNull(testedObject.getBanner(0));
    }

    @Test
    public void testGetAllBannersChanges()
    {
        BannerChange expected = new BannerChange(1, 1, "1",
                "CREATE", null, LocalDate.parse("2016-09-21"));

        List<BannerChange> actualList = new ArrayList<>();
        actualList.add(expected);
        expect(bannerChangeDAO.getAllBannersChanges()).andReturn(actualList);
        replayAll();

        assertThat(testedObject.getAllBannersChanges(), is(actualList));
    }

    @Test
    public void testGetBannersChanges()
    {
        BannerChange expected = new BannerChange(3, 1, "2",
                "DELETE", null, LocalDate.parse("2016-09-21"));

        List<BannerChange> actualList = new ArrayList<>();
        actualList.add(expected);
        expect(bannerChangeDAO.getBannersChanges(3, 1)).andReturn(actualList);
        replayAll();

        assertThat(testedObject.getBannersChanges(3, 1), is(actualList));
    }

    @Test
    public void testGetBannersChangesByBannerId()
    {
        BannerChange expected = new BannerChange(4, 5, "1",
                "CREATE", null, LocalDate.parse("2016-09-21"));

        List<BannerChange> actualList = new ArrayList<>();
        actualList.add(expected);
        expect(bannerChangeDAO.getBannersChanges(5, 2)).andReturn(actualList);
        replayAll();

        assertThat(testedObject.getBannersChanges(5, 2), is(actualList));
    }

    @Test
    public void testGetBannersChangesIsNull()
    {
        expect(bannerChangeDAO.getBannersChanges(null, 1)).andReturn(null);
        expect(bannerChangeDAO.getBannersChanges(null, null)).andReturn(null);
        expect(bannerChangeDAO.getBannersChanges(1, null)).andReturn(null);
        expect(bannerChangeDAO.getBannersChanges(1, 0)).andReturn(null);
        expect(bannerChangeDAO.getBannersChanges(1, 4)).andReturn(null);
        replayAll();
        assertNull(testedObject.getBannersChanges(null, 1));
        assertNull(testedObject.getBannersChanges(null, null));
        assertNull(testedObject.getBannersChanges(1, null));
        assertNull(testedObject.getBannersChanges(1, 0));
        assertNull(testedObject.getBannersChanges(1, 4));
    }

    @Test
    public void testAddBannerIsNull()
    {
        expect(bannerDAO.addBanner(null)).andReturn(null);
        replayAll();
        assertNull(testedObject.addBanner(null));
    }

    @Test
    public void testAddBanner()
    {
        Integer id = 1;
        Banner expected = new Banner(id, "TEST", 0, 0,
                "TEST", "TEST");
        expect(bannerDAO.addBanner(expected)).andReturn(id);
        expect(currentPrincipalService.getCurrentPrincipalName()).andReturn("TEST");
        expect(bannerChangeDAO.addBannerChange(new BannerChange(0, id,
                "TEST",
                BannersAdminService.CREATE, expected.toString(),
                LocalDate.now()))).andReturn(id);
        replayAll();
        assertThat(testedObject.addBanner(expected), is(id));
    }

    @Test
    public void testDeleteBannerIsNull()
    {
        expect(bannerDAO.deleteBanner(null)).andReturn(null);
        replayAll();
        assertNull(testedObject.deleteBanner(null));
    }

    @Test
    public void testDeleteBanner()
    {
        Integer id = 1;

        expect(bannerDAO.deleteBanner(id)).andReturn(id);
        expect(currentPrincipalService.getCurrentPrincipalName()).andReturn("TEST");
        expect(bannerChangeDAO.addBannerChange(new BannerChange(0, id,
                "TEST", BannersAdminService.DELETE, "",
                LocalDate.now()))).andReturn(1);
        replayAll();
        assertThat(testedObject.deleteBanner(id), is(id));
    }

    @Test
    public void testUpdateBannerIsNull()
    {
        expect(bannerDAO.updateBanner(null)).andReturn(null);
        replayAll();

        assertNull(testedObject.updateBanner(null));
    }

    @Test
    public void testUpdateBannerNotExist()
    {
        Banner expected = new Banner(0, "TEST", 3, 3,
                "TEST2", "TEST3");
        expect(bannerDAO.updateBanner(expected)).andReturn(null);
        replayAll();

        assertNull(testedObject.updateBanner(expected));
    }


    @Test
    public void testUpdateBanner()
    {
        Integer id = 2;
        Banner expectedBanner = new Banner(id, "TEST", 3, 3,
                "TEST", "TEST");
        BannerChange expectedBannerChange = new BannerChange(0, id,
                "TEST", BannersAdminService.UPDATE,
                "New value: " + expectedBanner.toString(), LocalDate.now());
        expect(bannerDAO.updateBanner(expectedBanner)).andReturn(id);
        expect(currentPrincipalService.getCurrentPrincipalName()).andReturn("TEST");
        expect(bannerChangeDAO.addBannerChange(expectedBannerChange)).andReturn(1);
        replayAll();

        assertThat(testedObject.updateBanner(expectedBanner), is(id));
    }

    @Test
    public void testAddUserIsNull()
    {
        expect(appUserDAO.addAppUser(null)).andReturn(null);
        replayAll();
        assertNull(testedObject.addUser(null));
    }

    @Test
    public void testAddUser()
    {
        Integer id = 1;
        AppUser expected = new AppUser(id, "TEST_NAME",
                "TEST_PASSWORD");
        expect(appUserDAO.addAppUser(expected)).andReturn(id);

        replayAll();
        assertThat(testedObject.addUser(expected), is(id));
    }
}
