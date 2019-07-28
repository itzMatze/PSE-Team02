package edu.kit.mensameet.client.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MensaMeetSessionTest {

    /*
    tests the functionality of the singleton object MensaMeetSession
     */
    @Test
    public void singleton_test() {
        MensaMeetSession mensaMeetSession = MensaMeetSession.getInstance();
        User user = new User();
        user.setMotto("testMotto");
        mensaMeetSession.setUser(user);
        assertEquals("testMotto", MensaMeetSession.getInstance().getUser().getMotto());
    }
}