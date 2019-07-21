package com.example.RecklessLabs.TaskManager;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class BackEndTest {

    DatabaseHelper myDB;

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = RuntimeEnvironment.application;
        myDB = new DatabaseHelper(appContext);
    }

    /*****************************************************/

    //tests for getAllData()
    @Test
    public void backEndTest1() {
        assertEquals(new ArrayList<Task>().getClass(), myDB.getAllData().getClass());
    }

    /*****************************************************/

    //tests for getTasks()
    @Test
    public void backEndTest2() {
        assertEquals(new ArrayList<String>().getClass(), myDB.getTasks().getClass());
    }

    /*****************************************************/

    //tests for insertData()
    @Test
    public void backEndTest3() {
        assertEquals(true, myDB.insertData("task3", "testDesc3"));
    }
    @Test
    public void backEndTest4() {
        //commented out due to intentional compile failure
        //assertEquals(false, myDB.insertData(4, ""));
    }
    @Test
    public void backEndTest5() {
        //commented out due to intentional compile failure
        //assertEquals(false, myDB.insertData("test5", 5));
    }
    @Test
    public void backEndTest6() {
        assertEquals(false, myDB.insertData("", ""));
    }
    @Test
    public void backEndTest7() {
        assertTrue(myDB.insertData("task7", ""));
    }
    @Test
    public void backEndTest8() {
        assertFalse(myDB.insertData("", "testDesc8"));
    }

    /*****************************************************/

    //tests for updateData()
    @Test
    public void backEndTest9() {
        assertFalse(myDB.updateData("task3", "task9", "testDesc9"));
    }
    @Test
    public void backEndTest10() {
        assertFalse(myDB.updateData("", "task10", "testDesc10"));
    }
    @Test
    public void backEndTest11() {
        assertFalse(myDB.updateData("task9", "", "testDesc11"));
    }
    @Test
    public void backEndTest12() {
        assertFalse(myDB.updateData("task12", "newTask12", "testDesc12"));
    }
    @Test
    public void backEndTest13() {
        assertTrue(myDB.updateData("task9", "task13", ""));
    }
    @Test
    public void backEndTest14() {
        //commented out due to intentional compile failure
        //assertEquals(false, myDB.updateData("task13", "task14", 14));
    }
    @Test
    public void backEndTest15() {
        //commented out due to intentional compile failure
       //assertEquals(false, myDB.updateData(1, "task16", "testDesc15"));
    }
    @Test
    public void backEndTest16() {
        //commented out due to intentional compile failure
        //assertEquals(false, myDB.updateData("task13", 16, "testDesc16"));
    }

    /*****************************************************/
    //tests for getDescription()
    @Test
    public void backEndTest17() {
        myDB.insertData("task7", "");
        assertEquals("No Description", myDB.getDescription("task7"));
    }
    @Test
    public void backEndTest18() {
        assertEquals("false", myDB.getDescription("task7"));
    }
    @Test
    public void backEndTest19() {
        //commented out due to intentional compile failure
        //assertEquals(false, myDB.getDescription(19));
    }


    /*****************************************************/
    //tests for deleteData()
    @Test
    public void backEndTest20() {
        assertTrue(myDB.deleteData("task13"));
    }
    @Test
    public void backEndTest21() {
        assertFalse(myDB.deleteData("task21"));
    }
    @Test
    public void backEndTest22() {
        assertFalse(myDB.deleteData(""));}
    @Test
    public void backEndTest23() {
        //commented out due to intentional compile failure
        //assertEquals(true, myDB.deleteData(20));
    }





    // Teardown our tests
    @After
    public void tearDown() throws Exception {
    }
}