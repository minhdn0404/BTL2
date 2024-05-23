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

@RunWith(AndroidJUnit4.class)
public class LoginTest extends TestCase {

    Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("email ne"), ViewActions.closeSoftKeyboard());

    // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());

    // Nhấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

    // Kiểm tra chuyển đổi màn hình (MainActivity)
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
}

    @Test
    public void testFailedLogin() {
        // Nhập email không hợp lệ
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("wrong@example.com"), ViewActions.closeSoftKeyboard());

        // Nhập mật khẩu
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("wrongpassword"), ViewActions.closeSoftKeyboard());

        // Nhấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

        // Kiểm tra Toast thông báo thất bại
        Espresso.onView(ViewMatchers.withText("Login Failed"))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}