package com.example.a007fa.fitly;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;


import static org.junit.Assert.*;

public class TestBadge {
    @Test
    public void testCreateBadge()
    {
        Badge testBadge= new Badge("small", true);
        assertEquals(testBadge.getTypeOfBadge(), "small");
        assertEquals(testBadge.completed, true);
    }
    public void testSetCompleted()
    {
        Badge testBadge= new Badge("small", false);
        testBadge.setCompleted(true);
        


    }


}
