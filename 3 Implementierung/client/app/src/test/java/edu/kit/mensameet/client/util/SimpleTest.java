package edu.kit.mensameet.client.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MealLines;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;

public class SimpleTest {
    public static void main(String args[]) {
        /*
        User user = MensaMeetSession.getInstance().getUser();
        user.setToken("999");
        user.setSubject(Subject.INFORMATICS);
        user.setStatus(Status.PROFESSOR);
        user.setProfilePictureId(0);
        user.setGender(Gender.MALE);
        user.setGroupToken(null);
        user.setIsAdmin(true);
        user.setMotto("");
        user.setName("hjko");
*/
        Group testGroup = new Group();
        testGroup.setMeetingDate(new Date());
        testGroup.setMaxMembers(8);
        testGroup.setMotto("testMotto");
        testGroup.setName("testGroup");
        testGroup.setLine(MealLines.CURRY_LINE.toString());
        testGroup.setToken("testToken");

        String[] mealLines = new String[4];
        mealLines[0] = MealLines.LINE_SIX.toString();
        mealLines[1] = MealLines.CAFETARIA_LATE.toString();
        mealLines[2] = MealLines.LINE_FOUR_FIVE.toString();
        mealLines[3] = MealLines.CAFETARIA.toString();



        RequestUtil.GroupByPrefferences testRefferences = new RequestUtil.GroupByPrefferences(
                new MensaMeetTime(new Time(11,30,0), new Time(13,0,0)),
                mealLines);

        try{


            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


            System.out.println("soll" +  mapper.writer().writeValueAsString(testRefferences));

            List<Group> resultFromServer = RequestUtil.getGroupByPrefferences("", new MensaMeetTime(new Time(11,30,0), new Time(13,0,0)),
                    mealLines);

            for(Group g : resultFromServer){
                String ist = mapper.writer().writeValueAsString(g);
                System.out.println("result " + ist);
            }


            //todo


            /*
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            System.out.println("soll" +  mapper.writer().writeValueAsString(testGroup));

            Group resultFromServer = RequestUtil.createGroup(testGroup,testGroup.getToken());
            String ist = mapper.writer().writeValueAsString(resultFromServer);
            System.out.println("ist " + ist);
            //todo

            */
        }catch (Exception e){
            System.out.println("error " + e.getMessage());
        }

        /*RequestUtil.updateUser(user);
        try{
            String str = new ObjectMapper().writer().writeValueAsString(user);
            System.out.println("soll\n" + str);
            User newUser = RequestUtil.getUser("999", "999");
            System.out.println("ist\n" + new ObjectMapper().writer().writeValueAsString(newUser));
        }catch (Exception e){

        }
        */

    }
}
