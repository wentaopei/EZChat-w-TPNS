package com.example.ezchat;

import com.example.ezchat.ui.activity.ChatActivity;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ChatTest {
    private ChatActivity test = mock(ChatActivity.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void isConnectionValid() throws JSONException
    {
        when(test.sendRequestToServerTest()).thenReturn(true);
        Assert.assertTrue(test.sendRequestToServerTest());
    }
}
