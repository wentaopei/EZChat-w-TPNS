package com.example.ezchat;


import com.example.ezchat.ui.activity.MomentsActivity;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MomentTest {

    private MomentsActivity test = mock(MomentsActivity.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void isConnectionValid() throws JSONException
    {
        when(test.sendRequestToServerTest()).thenReturn(true);
        Assert.assertTrue(test.sendRequestToServerTest());
    }

}
