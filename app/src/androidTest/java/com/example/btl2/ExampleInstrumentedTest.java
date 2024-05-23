package com.example.btl2;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    public void buttonClickChangesTextViewText(){
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.withText("Bam vao nut Login thanh cong")));

    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.btl2", appContext.getPackageName());
    }
    @Test

    public void testSuccessfulLogin() {
        // Nhập email
        Espresso.onView(ViewMatchers.withId(R.id.editText_Email)).perform(ViewActions.typeText("accept email ne"), ViewActions.closeSoftKeyboard());

        // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.editText_Password)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        // Nhấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click());

        // Kiểm tra chuyển đổi màn hình (MainActivity)
        Espresso.onView(ViewMatchers.withId(R.layout.activity_main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testFailedLogin() {
        // Nhập email
        Espresso.onView(ViewMatchers.withId(R.id.editText_Email)).perform(ViewActions.typeText("accept email ne"), ViewActions.closeSoftKeyboard());

        // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.editText_Password)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        // Nhấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click());

        // Kiểm tra Toast thông báo thất bại
        Espresso.onView(ViewMatchers.withText("Login Failed"))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    //Tạo sản phẩm
    @Test

    public void testSuccessfulCreatProduct() {
        // Nhập email
        Espresso.onView(ViewMatchers.withId(R.id.productNameEditText)).perform(ViewActions.typeText("accept email ne"), ViewActions.closeSoftKeyboard());

        // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.productDescriptionEditText)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextStartTime)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextEndTime)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.startPriceEditText)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.stepPriceEditText)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        // Nhấn nút đăng nhập
//        Espresso.onView(ViewMatchers.withId(R.id.addProduct)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addProductPicture)).perform(ViewActions.click());


        // Kiểm tra chuyển đổi màn hình (MainActivity)
        Espresso.onView(ViewMatchers.withId(R.layout.activity_create_auction))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }


    @Test
    public void testFailedCreatProduct() {
        // Nhập email
        // Nhập email
        Espresso.onView(ViewMatchers.withId(R.id.productNameEditText)).perform(ViewActions.typeText("fail ten san pham ne"), ViewActions.closeSoftKeyboard());

        // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.productDescriptionEditText)).perform(ViewActions.typeText("fail password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextStartTime)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextEndTime)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.startPriceEditText)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.stepPriceEditText)).perform(ViewActions.typeText("accept password"), ViewActions.closeSoftKeyboard());

        // Nhấn nút đăng nhập
//        Espresso.onView(ViewMatchers.withId(R.id.addProduct)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addProductPicture)).perform(ViewActions.click());


        // Kiểm tra Toast thông báo thất bại
        Espresso.onView(ViewMatchers.withText("Creat Product Failed"))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

