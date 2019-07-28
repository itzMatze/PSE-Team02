package edu.kit.mensameet.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.User;

public class RequestUtilTest {


    @Test
    public void userTest() {

        ObjectMapper mapper = new ObjectMapper();

        User testUser = new User();
        testUser.setToken("testUser123");
        testUser.setMotto("testMotto");
        testUser.setName("foo");
        testUser.setIsAdmin(false);
        testUser.setGroupToken("0");

        String str1 = RequestUtil.createUser(testUser.getToken());
        Assert.assertNotNull(str1);

        String str2 = RequestUtil.updateUser(testUser);
        Assert.assertNotNull(str2);

        try {
            User actual = RequestUtil.getUser(testUser.getToken(),testUser.getToken());
            Assert.assertEquals(testUser.getMotto(), actual.getMotto());
            Assert.assertEquals(testUser.getIsAdmin(), actual.getIsAdmin());
        }catch (Exception e){

        }
    }


    @Test
    public void groupTest() {

        ObjectMapper mapper = new ObjectMapper();

        Group testGroup = new Group();
        testGroup.setToken("testGroup");
        testGroup.setMotto("testMotto");

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

}
