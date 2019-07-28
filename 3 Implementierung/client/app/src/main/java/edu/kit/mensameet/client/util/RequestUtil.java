package edu.kit.mensameet.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;

/*
in case return JSON File or JSON URL
    //JSON file to Java object
    Staff obj = mapper.readValue(new File("c:\\test\\staff.json"), Staff.class);

    //JSON URL to Java object
    Staff obj = mapper.readValue(new URL("http://some-domains/api/name.json"), Staff.class);
 */
/**
 * this class contains static methods responsible for send request to server
 */
public class RequestUtil {

    //for convert a java object to json string and vise versa
    private static ObjectMapper mapper = new ObjectMapper();

    //for convert a java object to json string
    private static ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    /**
     * create user
     * @param firebaseToken
     * @return
     */
    public static String createUser(final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firebaseToken", firebaseToken);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/server/user",null, params);
                    //Log.i("create user success", str[0]);
                }catch (Exception e){
                    //Log.e("create user failed", e.getClass().toString() + e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     *
     * @param firebaseToken
     * @param userToken
     * @return
     */
    public static User getUser(final String firebaseToken, final String userToken) {
        final User[] user = {new User()};
        final String[] str = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    params.put("userToken", userToken);
                    str[0] = HttpUtil.get("http://193.196.38.98:8080/server/user", params);
                    user[0] = mapper.readValue(str[0], User.class);
                }catch (Exception e){
                    //Log.e("get user failed", e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();

        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return user[0];
    }

    /**
     *
     * @param user
     * @return
     */
    public static String updateUser(final User user) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = ow.writeValueAsString(user);
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("firebaseToken", user.getToken());
                    str[0] = HttpUtil.put("http://193.196.38.98:8080/server/user", json, params);
                }catch (Exception e){
                    //Log.e("update user failed", e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     *
     * @param firebaseToken
     * @return
     */
    public static String deleteUser(final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    str[0] = HttpUtil.delete("http://193.196.38.98:8080/server/user?token" + firebaseToken);
                }catch (Exception e){
                    //Log.e("delete user failed", e.getMessage());
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     *
     * @param group
     * @param firebaseToken
     * @return group with new groupToken
     */
    public static Group createGroup(final Group group, final String firebaseToken) {
        final Group[] newGroup = new Group[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = ow.writeValueAsString(new GroupForRequest(group));
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("firebaseToken", firebaseToken);
                    String str = HttpUtil.post("http://193.196.38.98:8080/server/create-group", json, params);
                    GroupForRequest returnedGroup = mapper.readValue(str, GroupForRequest.class);
                    newGroup[0] = returnedGroup.parseToGroup();
                }catch (Exception e){
                    //Log.e("create group failed", e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return newGroup[0];
    }

    /**
     *
     * @param groupToken
     * @return
     */
    public static Group getGroup(final String groupToken) {
        final Group[] group = new Group[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("groupToken",groupToken);
                    String str = HttpUtil.get(
                            "http://193.196.38.98:8080/server/create-group?groupToken="+ groupToken, params);
                    GroupForRequest returnGroup = mapper.readValue(str, GroupForRequest.class);
                    group[0] = returnGroup.parseToGroup();
                }catch (Exception e){
                    //Log.e("get group failed", e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return group[0];
    }

    /*
     *
     *
     * @param groupToken
     * @return
     *
    public static String getGroupString(final String groupToken) {
        final String[] str = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("groupToken",groupToken);
                    str[0] = HttpUtil.get(
                            "http://193.196.38.98:8080/server/create-group?groupToken=" + groupToken, params);

                }catch (Exception e){
                    //Log.e("get group failed", e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }
    */

    /**
     *
     * @param time
     * @param lines
     * @return
     */
    public static List<Group> getGroupByPrefferences(final MensaMeetTime time, final String[] lines) {
        final String[] str = {""};
        final List<Group> groups = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = ow.writeValueAsString(new GroupByPrefferences(time, lines));
                    Map<String, String> params = new HashMap<>();
                    params.put("token","1");
                    params.put("Content-Type","application/json");
                    str[0] = HttpUtil.post
                            ("http://193.196.38.98:8080/server/group-prefferences", json, params);
                    List<GroupForRequest> groupForRequests = new ArrayList<>();
                    groupForRequests = mapper.readValue
                            (str[0], mapper.getTypeFactory().constructCollectionType(List.class, GroupForRequest.class));
                    for(GroupForRequest i: groupForRequests){
                        groups.add(i.parseToGroup());
                    }
                }catch (Exception e){
                    //Log.e("preferences failed", e.getMessage());
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("error", e.getMessage());
        }
        return groups;
    }

    /**
     *
     * @param firebaseToken
     * @return
     */
    public static String deleteGroup(final String groupToken, final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    str[0] = HttpUtil.delete(
                            "http://193.196.38.98:8080/server/group?groupToken=" + groupToken, params);
                }catch (Exception e){
                    //Log.e("delete user failed", e.getMessage());
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }


    public static String addUserToGroup(final String groupToken, final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/server/add-user-to-group?firebaseToken="
                            + firebaseToken + "&groupToken=" + groupToken,
                    null, null);
                }catch (Exception e){
                    //Log.e("preferences failed", e.getMessage());
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     *
     * @param firebaseToken
     * @param groupToken
     * @return
     */
    public static String removeUserFromGroup(final String firebaseToken, final String groupToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/server/remove-user-from-group?firebaseToken="
                                    + firebaseToken + "&groupToken=" + groupToken,
                            null, null);
                }catch (Exception e){
                    //Log.e("create user failed", e.getClass().toString() + e.getMessage());
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }



    /**
     *
     * @return
     */
    public static String getMensaData() {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    str[0] = HttpUtil.get("http://193.196.38.98:8080/server/mensadata");
                }catch (Exception e){
                    //Log.e("get mensa data failed", e.getMessage());
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            //Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * this class is used for convert group to a json String
     * and send json string as body
     */
    private static class GroupForRequest{
        String token;
        String name;
        String motto;
        int maxMember;
        String line;
        int[] meetingTime = new int[2];

        //constructor with group
        public GroupForRequest(Group group) {
            token = group.getToken();
            name = group.getName();
            motto = group.getMotto();
            maxMember = group.getMaxMembers();
            line = group.getLine();
            meetingTime[0] = group.getMeetingDate().getHours();
            meetingTime[1] = group.getMeetingDate().getMinutes();
        }

        public Group parseToGroup() {
            Group group = new Group();
            group.setToken(this.token);
            group.setName(this.name);
            group.setMotto(this.motto);
            group.setMaxMembers(this.maxMember);
            Date meetingDate = new Date();
            meetingDate.setHours(this.meetingTime[0]);
            meetingDate.setMinutes(this.meetingTime[1]);
            group.setMeetingDate(meetingDate);
            group.setLine(this.line);
            return group;
        }
    }

    /**
     *
     */
    private static class GroupByPrefferences{
        int[] startTime = new int[2];
        int[] endTime = new int[2];
        String[] mealLines;

        public GroupByPrefferences(MensaMeetTime intervalTime, String[] mealLines) {
            startTime[0] = intervalTime.getStartTime().getHours();
            startTime[1] = intervalTime.getStartTime().getMinutes();
            endTime[0] = intervalTime.getEndTime().getHours();
            endTime[1] = intervalTime.getEndTime().getMinutes();
            this.mealLines = mealLines;
        }
    }



}