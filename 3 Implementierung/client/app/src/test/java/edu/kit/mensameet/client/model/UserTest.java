package edu.kit.mensameet.client.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void singleton_test() {
        User staticUser = User.getInstance();
        User user = new User();
        user.setMotto("testMotto");
        assertEquals("testMotto", user.getMotto());
        assertEquals("", User.getInstance().getMotto());
        staticUser.setMotto("staticTestMotto");
        assertEquals("staticTestMotto", User.getInstance().getMotto());
        assertEquals("testMotto", user.getMotto());
    }

}