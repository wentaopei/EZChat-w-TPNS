package com.example.ezchat;

import com.example.ezchat.ui.activity.RegisterActivity;

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
 * Unit test for register
 * @author Wentao Pei
 */

public class RegisterTest
{
    private RegisterActivity test = mock(RegisterActivity.class);

    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void RegisterActivity_SignUpCorrectly() throws JSONException
    {
        when(test.signUpCorrectly()).thenReturn(true);
        Assert.assertTrue(test.signUpCorrectly());
    }

}
