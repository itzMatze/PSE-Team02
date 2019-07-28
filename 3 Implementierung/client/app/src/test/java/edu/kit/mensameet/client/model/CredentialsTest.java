package edu.kit.mensameet.client.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CredentialsTest {

    /*
    tests the functionality of the singleton object Credentials
     */
    @Test
    public void singleton_test() {
        Credentials credentials = Credentials.getInstance();
        credentials.setEmail("test@testmail.com");
        assertEquals(Credentials.getInstance().getEmail(), "test@testmail.com");
        credentials.setPassword("testPasswort12");
        assertEquals(Credentials.getInstance().getPassword(), "testPasswort12");
    }
}