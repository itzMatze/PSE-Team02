package edu.kit.mensameet.client.util;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;
import java.util.Map;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.User;

/**
 * this class contains
 */
public class RequestUtil {

    private static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    /**
     * @param firebaseToken
     * @return
     */
    public static String createUser(final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firebaseToken", firebaseToken);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/server/user", null, params);
                    Log.i("create user success", str[0]);
                } catch (Exception e) {
                    Log.e("create user failed", e.getClass().toString() + e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param token
     * @return
     */
    public static String getUser(final String token) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", token);
                    str[0] = HttpUtil.get("http://193.196.38.98:8080/server/user", params);
                } catch (Exception e) {
                    Log.e("get user failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param user
     * @return
     */
    public static String updateUser(final User user) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ow.writeValueAsString(user);
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("firebaseToken", user.getToken());
                    str[0] = HttpUtil.put("http://193.196.38.98:8080/server/user", json, params);
                } catch (Exception e) {
                    Log.e("update user failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param firebaseToken
     * @return
     */
    public static String deleteUser(final String firebaseToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("firebaseToken", firebaseToken);
                    str[0] = HttpUtil.delete("http://193.196.38.98:8080/server/user", params);
                } catch (Exception e) {
                    Log.e("delete user failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param group
     * @param token
     * @return
     */
    public static String createGroup(final Group group, final String token) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ow.writeValueAsString(new GroupForRequest(group));
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    //todo: add attribute token to Model.Group or ...
                    params.put("token", token);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/create-group", json, params);
                } catch (Exception e) {
                    Log.e("create group failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param groupToken
     * @return
     */
    public static String getGroup(final String groupToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("groupToken", groupToken);
                    str[0] = HttpUtil.get("http://193.196.38.98:8080/create-group", params);
                } catch (Exception e) {
                    Log.e("get group failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    public static String getGroupByPrefferences(final MensaMeetTime time, final String[] lines) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ow.writeValueAsString(new GroupByPrefferences(time, lines));
                    Map<String, String> params = new HashMap<>();
                    params.put("token", "1");
                    params.put("Content-Type", "application/json");
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/group-prefferences", json, params);
                } catch (Exception e) {
                    Log.e("preferences failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param time
     * @param lines
     * @return
     */
    public static String addUserToGroup(final MensaMeetTime time, final String[] lines) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ow.writeValueAsString(new GroupByPrefferences(time, lines));
                    Map<String, String> params = new HashMap<>();
                    params.put("token", "1");
                    params.put("Content-Type", "application/json");
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/group-prefferences", json, params);
                } catch (Exception e) {
                    Log.e("preferences failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @param firebaseToken
     * @param groupToken
     * @return
     */
    public static String addUserToGroup(final String firebaseToken, final String groupToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firebaseToken", firebaseToken);
                    params.put("groupToken", groupToken);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/add-user-to-group", null, params);
                    Log.i("add user success", str[0]);
                } catch (Exception e) {
                    Log.e("create user failed", e.getClass().toString() + e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    //todo: why user token

    /**
     * @param firebaseToken
     * @param groupToken
     * @return
     */
    public static String removeUserFromGroup(final String firebaseToken, final String groupToken) {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firebaseToken", firebaseToken);
                    params.put("groupToken", groupToken);
                    str[0] = HttpUtil.post("http://193.196.38.98:8080/remove-user-from-group", null, params);
                    Log.i("create user success", str[0]);
                } catch (Exception e) {
                    Log.e("remove user failed", e.getClass().toString() + e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * @return
     */
    public static String getMensaData() {
        final String[] str = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str[0] = HttpUtil.get("http://193.196.38.98:8080/server/mensadata");
                } catch (Exception e) {
                    Log.e("get mensa data failed", e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("join error", e.getMessage());
        }
        return str[0];
    }

    /**
     * this class is used for sending json string as body
     */
    private static class GroupForRequest {
        String name;
        String motto;
        int maxMember;
        String line;
        int[] meetingTime = new int[4];

        public GroupForRequest(Group group) {
            name = group.getName();
            motto = group.getMotto();
            maxMember = group.getMaxMembers();
            line = group.getLine();
            meetingTime[0] = group.getMeetingDate().getHours();
            meetingTime[1] = group.getMeetingDate().getMinutes();
            meetingTime[2] = 0;
            meetingTime[3] = 0;
        }
    }

    private static class GroupByPrefferences {
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
