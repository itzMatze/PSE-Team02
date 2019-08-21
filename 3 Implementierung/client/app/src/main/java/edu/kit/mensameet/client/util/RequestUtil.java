package edu.kit.mensameet.client.util;

import android.app.DownloadManager;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.R;

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

    private static final String EXCEPTION_MAPPER_IO = "Mapper IO Exception";

    //for convert a java object to json string and vise versa
    private static ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    private static void handleException(Exception e, String response) throws RequestException {
        if (e != null) {

            try {

                JsonNode jsonNode = mapper.readTree(response);
                throw new RequestException(jsonNode);

            } catch (IOException ioException) {

                throw new RequestException(0, EXCEPTION_MAPPER_IO);

            }
        }
    }

    /**
     * create user
     * @param firebaseToken token
     * @return not null if success
     */
    public static void createUser(final String firebaseToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    response[0] = HttpUtil.post("http://193.196.38.98:8080/server/user",null, params);

                } catch (Exception e) {

                    exception[0] = e;

                }

            }
        });

        thread.start();

        try{

            thread.join();

        } catch (Exception e) {

            exception[0] = e;

        }

        handleException(exception[0], response[0]);
    }

    /**
     * @param firebaseToken firebaseToken
     * @param userToken userToken
     * @return user
     */
    public static User getUser(final String firebaseToken, final String userToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final User[] user = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    params.put("userToken", userToken);
                    response[0] = HttpUtil.get("http://193.196.38.98:8080/server/user", params);
                    user[0] = mapper.readValue(response[0], User.class);
                } catch (Exception e) {

                    exception[0] = e;

                }

            }
        });

        thread.start();

        try{

            thread.join();

        } catch (Exception e) {

            exception[0] = e;

        }

        handleException(exception[0], response[0]);

        return user[0];
    }

    /**
     *
     * @param user
     * @return
     */
    public static void updateUser(final User user) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = ow.writeValueAsString(user);
                    Log.e("json string", json); // TODO: delete after testing
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("firebaseToken", user.getToken());
                    response[0] = HttpUtil.put("http://193.196.38.98:8080/server/user", json, params);
                }catch (Exception e){
                    exception[0] = e;
                }

            }
        });
        thread.start();

        try{

            thread.join();

        } catch (Exception e) {

            exception[0] = e;

        }

        handleException(exception[0], response[0]);
    }

    /**
     *
     * @param firebaseToken
     * @return
     */
    public static void deleteUser(final String firebaseToken, final String userToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    response[0] = HttpUtil.delete("http://193.196.38.98:8080/server/user?firebaseToken="
                            + firebaseToken + "&userToken=" + userToken);
                }catch (Exception e){
                    exception[0] = e;
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);
    }

    /**
     *
     * @param group
     * @param firebaseToken
     * @return group with new groupToken
     */
    public static Group createGroup(final Group group, final String firebaseToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final Group[] newGroup = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);//to avoid not serializable
                    String json = mapper.writer().writeValueAsString(new GroupForRequest(group));
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("firebaseToken", firebaseToken);
                    response[0] = HttpUtil.post("http://193.196.38.98:8080/server/create-group", json, params);
                    GroupForRequestWithToken returnedGroup = mapper.readValue(response[0], GroupForRequestWithToken.class);
                    newGroup[0] = returnedGroup.parseToGroup();
                }catch (Exception e){
                    exception[0] = e;
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);

        return newGroup[0];
    }

/*
    public static String createGroupString(final Group group, final String firebaseToken) {
        final String[] str = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                    String json = mapper.writer().writeValueAsString(new GroupForRequest(group));
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("firebaseToken", firebaseToken);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/server/create-group", json, params);
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
        return str[0];
    }
    */

    /**
     *
     * @param groupToken
     * @return
     */
    public static Group getGroup(final String firebaseToken, final String groupToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final Group[] group = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    params.put("groupToken",groupToken);
                    response[0] = HttpUtil.get(
                            "http://193.196.38.98:8080/server/group?groupToken="+ groupToken, params);
                    GroupForRequestWithToken returnGroup = mapper.readValue(response[0], GroupForRequestWithToken.class);
                    group[0] = returnGroup.parseToGroup();
                } catch (Exception e){
                    exception[0] = e;
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);

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
    public static List<Group> getGroupByPrefferences(final MensaMeetTime time, final String[] lines) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final List<Group> resultGroups = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);//to avoid not serializable
                    String json = mapper.writer().writeValueAsString(new GroupByPrefferences(time, lines));
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken","1");
                    params.put("Content-Type","application/json");
                    response[0] = HttpUtil.post
                            ("http://193.196.38.98:8080/server/group-prefferences", json, params);
                    //we split json String from Server, each of them should be converting in a GroupForRequest object
                    List<GroupForRequestWithToken> groupList = Arrays.asList(mapper.readValue(response[0], GroupForRequestWithToken[].class));

                    for(GroupForRequestWithToken g : groupList){
                        resultGroups.add(g.parseToGroup());
                    }
                } catch (Exception e) {
                    exception[0] = e;
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);

        return resultGroups;
    }

    /**
     *
     * @param firebaseToken
     * @return
     */
    public static void deleteGroup(final String groupToken, final String firebaseToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    response[0] = HttpUtil.delete(
                            "http://193.196.38.98:8080/server/group?groupToken=" + groupToken, params);
                } catch (Exception e) {
                    exception[0] = e;
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);
    }


    public static void addUserToGroup(final String groupToken, final String firebaseToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    response[0] = HttpUtil.post("http://193.196.38.98:8080/server/add-user-to-group?firebaseToken="
                            + firebaseToken + "&groupToken=" + groupToken,
                    null, null);
                }catch (Exception e){
                    exception[0] = e;
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);
    }

    /**
     *
     * @param firebaseToken
     * @param groupToken
     * @return
     */
    public static void removeUserFromGroup(final String firebaseToken, final String groupToken) throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    response[0] = HttpUtil.post("http://193.196.38.98:8080/server/remove-user-from-group?firebaseToken="
                                    + firebaseToken + "&groupToken=" + groupToken,
                            null, null);
                }catch (Exception e){
                    exception[0] = e;
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);
    }



    /**
     *
     * @return
     */
    public static MensaData getMensaData() throws RequestException {

        final String[] response = {null};
        final Exception[] exception = {null};

        final MensaData[] mensaData = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    response[0] = HttpUtil.get("http://193.196.38.98:8080/server/mensadata");
                    mensaData[0] = mapper.readValue(response[0], MensaData.class);
                }catch (Exception e){
                    exception[0] = e;
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            exception[0] = e;
        }

        handleException(exception[0], response[0]);

        return mensaData[0];
    }

    /**
     * this class is used for convert group to a json String
     * and send json string as body
     */


    public static class GroupForRequest {

        String name;
        String motto;
        int maxMembers;
        int[] meetingTime = new int[2];
        String line;
        User[] members;
        // if token in body then... in server remains same values, so here is no token

        public GroupForRequest() {
        }

        public GroupForRequest(String name, String motto, int maxMembers, int[] meetingTime, String line, User[] members) {

            this.name = name;
            this.motto = motto;
            this.maxMembers = maxMembers;
            this.meetingTime = meetingTime;
            this.line = line;
            this.members = members;
        }

        //constructor with group todo change after test

        public GroupForRequest(Group group) {
            name = group.getName();
            motto = group.getMotto();
            maxMembers = group.getMaxMembers();
            line = group.getLine();
            meetingTime[0] = group.getMeetingDate().getHours();
            meetingTime[1] = group.getMeetingDate().getMinutes();
        }

        public void setLine(String line) {
            this.line = line;
        }

        public void setMeetingTime(int[] meetingTime) {
            this.meetingTime = meetingTime;
        }

        public void setMaxMembers(int maxMembers) {
            this.maxMembers = maxMembers;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User[] getMembers() {
            return members;
        }

        public String getLine() {
            return line;
        }

        public int[] getMeetingTime() {
            return meetingTime;
        }

        public int getMaxMembers() {
            return maxMembers;
        }

        public String getMotto() {
            return motto;
        }

        public String getName() {
            return name;
        }

        public Group parseToGroup() {
            Group group = new Group();
            group.setName(this.name);
            group.setMotto(this.motto);
            group.setMaxMembers(this.maxMembers);
            Date meetingDate = new Date();
            meetingDate.setHours(this.meetingTime[0]);
            meetingDate.setMinutes(this.meetingTime[1]);
            group.setMeetingDate(meetingDate);
            group.setLine(this.line);
            List<User> userList = new ArrayList<>();
            if(members != null) {
                for (int n = 0; n < members.length; n++){
                    userList.add(members[n]);
                }
            }
            group.setUsers(userList);
            return group;
        }
    }

    public static class GroupForRequestWithToken extends GroupForRequest {

        String token;

        public GroupForRequestWithToken() {}

        public GroupForRequestWithToken(Group group) {
            this.token = group.getToken();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public Group parseToGroup() {

            Group group = super.parseToGroup();
            group.setToken(this.token);

            return group;
        }


    }

    /**
     *
     */
    public static class GroupByPrefferences{
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

    /*
    public static List<String> extractMessageByRegular(String msg){

        List<String> list=new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?<=\\{)[^}]*(?=\\})");
        Matcher m = pattern.matcher(msg);
        while(m.find()){
            list.add("{" + m.group() + "}");
        }
        return list;
    }
*/

    public static class RequestException extends Exception {

        private int errorCode = 0;
        private String errorMessage;

        public RequestException(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public RequestException(JsonNode errorJsonNode) {

            if (errorJsonNode.has("status")) {
                this.errorCode = errorJsonNode.get("status").asInt();
            }

            if (errorJsonNode.has("message")) {
                this.errorMessage = errorJsonNode.get("message").asText();
            }
        }

        @Override
        public String getLocalizedMessage() {
            return errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }


}