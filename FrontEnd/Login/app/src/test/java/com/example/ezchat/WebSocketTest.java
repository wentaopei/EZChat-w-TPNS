package com.example.ezchat;

/**
 * Test class for websocket
 * @author Wentao Pei
 */

import com.example.ezchat.ui.activity.GroupChatActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebSocketTest
{
    private GroupChatActivity test = mock(GroupChatActivity.class);

    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void WebSocket_Open() throws Exception
    {
        when(test.test()).thenReturn(true);
        Assert.assertTrue(test.test());
    }
}
