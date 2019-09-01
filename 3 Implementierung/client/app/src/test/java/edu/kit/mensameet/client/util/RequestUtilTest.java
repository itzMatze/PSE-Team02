package edu.kit.mensameet.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MealLines;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;


public class RequestUtilTest {

    ObjectMapper mapper = new ObjectMapper();
    User testUser;
    Group testGroup = new Group();

    String[] lines;

    @Before
    public void setUp() throws Exception {

        testUser = new User();
        testUser.setGender(Gender.MALE);
        testUser.setToken("testUser941");
        testUser.setMotto("");
        testUser.setName("hjko");
        testUser.setProfilePictureId(0);
        testUser.setStatus(Status.PROFESSOR);
        testUser.setSubject(Subject.INFORMATICS);
        testUser.setIsAdmin(true);
        testUser.setGroupToken(null);

        testGroup.setToken("testToken");
        testGroup.setMotto("testMotto");
        testGroup.setName("testName");
        Date testTime = new Date();
        testTime.setHours(12);
        testTime.setMinutes(0);
        testGroup.setMeetingDate(testTime);
        testGroup.setLine(MealLines.CURRY_LINE.toString());

        lines = new String[4];
        lines[0] = MealLines.LINE_SIX.toString();
        lines[1] = MealLines.CAFETARIA_LATE.toString();
        lines[2] = MealLines.LINE_FOUR_FIVE.toString();
        lines[3] = MealLines.CURRY_LINE.toString();
    }

    /*
    delete user only with admin account
     */
    @After
    public final void tearDown() throws Exception {
            RequestUtil.deleteUser(testUser.getToken(),testUser.getToken());
    }

    @Test
    public void mensaDataTest() throws Exception{
        Assert.assertNotNull(RequestUtil.getMensaData());
    }

    @Test
    public void userTest() throws Exception{

        Assert.assertNotNull(testUser);
            RequestUtil.createUser(testUser.getToken());
            RequestUtil.updateUser(testUser);

            User actual = RequestUtil.getUser(testUser.getToken(),testUser.getToken());
            Assert.assertNotNull(actual);
            Assert.assertEquals(testUser.toString(), actual.toString());

    }


    @Test
    public void groupTest() throws Exception{

            Group expected = RequestUtil.createGroup(testGroup, testGroup.getToken());

        try {
            RequestUtil.createUser(testUser.getToken());
        } catch (RequestUtil.RequestException e) {
            e.printStackTrace();
        }

        try {
            RequestUtil.updateUser(testUser);
        } catch (RequestUtil.RequestException e) {
            e.printStackTrace();
        }

            Group actualGroup = RequestUtil.getGroup(expected.getToken(),expected.getToken());
            Assert.assertNotNull(actualGroup);
            Assert.assertEquals(expected.getName(), actualGroup.getName());

            List<Group> groupList = RequestUtil.getGroupByPrefferences(testUser.getToken(), new MensaMeetTime(new Time(11,30,0), new Time(13,0,0)),
                    lines);
            Assert.assertFalse(groupList.isEmpty());

            RequestUtil.addUserToGroup(expected.getToken(), testUser.getToken());
            RequestUtil.removeUserFromGroup(expected.getToken(), testUser.getToken());

            RequestUtil.deleteGroup(expected.getToken(),testUser.getToken());

    }

}
