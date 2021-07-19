package com.example.ezchat;

import com.example.ezchat.ui.login.LoginViewModel;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for login function
 * @author Wentao Pei
 */

public class LoginTest
{
    private LoginViewModel test = mock(LoginViewModel.class);
    private WebSocketClient cc;

    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void loginViewModel_NameValid() throws JSONException
    {
        String name = "ngo";
        when(test.isUserNameValid(name)).thenReturn(true);
        Assert.assertTrue(test.isUserNameValid(name));
    }

    @Test
    public void loginViewModel_NameNotValid() throws JSONException
    {
        String name = "nnon";
        when(test.isUserNameValid(name)).thenReturn(false);
        Assert.assertFalse(test.isUserNameValid(name));
    }


    @Test
    public void loginViewModel_PassValid() throws JSONException
    {
        String pass = "000000";
        when(test.isPasswordValid(pass)).thenReturn(true);
        Assert.assertTrue(test.isPasswordValid(pass));
    }

    @Test
    public void loginViewModel_PassNotValid() throws JSONException
    {
        String pass = "111111";
        when(test.isPasswordValid(pass)).thenReturn(false);
        Assert.assertFalse(test.isPasswordValid(pass));
    }

}
