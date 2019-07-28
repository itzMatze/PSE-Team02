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

        //see user in JSON String
        //TODO: change Model.User from Server und
       String str3 = RequestUtil.getUserString(testUser.getToken(), testUser.getToken());
        try {
            String expect = mapper.writeValueAsString(testUser);
            Assert.assertEquals(expect, str3);
        }catch (Exception e){

        }

        try {
            User actual = RequestUtil.getUser(testUser.getToken(),testUser.getToken());
            Assert.assertEquals(testUser, actual);
        }catch (Exception e){

        }
    }


    @Test
    public void grouTest() {

        ObjectMapper mapper = new ObjectMapper();

        Group testGroup = new Group();
        testGroup.setToken("testGroup");
        testGroup.setMotto("testMotto");

        String str1 = RequestUtil.createGroup(testGroup, testGroup.getToken());
        Assert.assertNotNull(str1);


        //see group in JSON String
        String str3 = RequestUtil.getGroupString(testGroup.getToken());
        try {
            String expect = mapper.writeValueAsString(testGroup);
            Assert.assertEquals(expect, str3);
        }catch (Exception e){

        }

    }

}
