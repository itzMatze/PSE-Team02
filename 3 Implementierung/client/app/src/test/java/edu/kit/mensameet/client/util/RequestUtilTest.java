package edu.kit.mensameet.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;


public class RequestUtilTest {

    ObjectMapper mapper = new ObjectMapper();
    User testUser = new User();
    Group testGroup = new Group();
    @Before
    public void setUp() throws Exception {
        testUser.setGender(Gender.MALE);
        testUser.setToken("testUser941");
        testUser.setMotto("");
        testUser.setName("hjko");
        testUser.setProfilePictureId(0);
        testUser.setStatus(Status.PROFESSOR);
        testUser.setSubject(Subject.INFORMATICS);
        testUser.setIsAdmin(true);
        testUser.setGroupToken(null);

        testGroup.setToken("testGroup");
        testGroup.setMotto("testMotto");
    }

    @Test
    public void userTest() {



        String str1 = RequestUtil.createUser(testUser.getToken());
//        Assert.assertNotNull(str1);

        String str2 = RequestUtil.updateUser(testUser);


        try {
            User actual = RequestUtil.getUser(testUser.getToken(),testUser.getToken());
            Assert.assertEquals(testUser.getMotto(), actual.getMotto());
            Assert.assertEquals(testUser.getIsAdmin(), actual.getIsAdmin());
            Assert.assertEquals(testUser.getGender(), actual.getGender());
        }catch (Exception e){

        }
    }
/*
delete user only with admin account
    @After
    public final void after() {
        RequestUtil.deleteUser(testUser.getToken());
    }
*/

/*todo
    @Test
    public void groupTest() {

        Group expect = RequestUtil.createGroup(testGroup, testGroup.getToken());

        // create group and get group
        try {
            Group actual = RequestUtil.getGroup(expect.getToken());
            Assert.assertNotNull(expect.getToken());
            Assert.assertEquals(expect.getToken(), actual.getToken());
            Assert.assertEquals("testMotto", actual.getMotto());
        }catch (Exception e){

        }

    }
*/
}
