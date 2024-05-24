package com.example.btl2;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    protected boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            return windowToken == appToken;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }


//    @Override
//    public boolean matches(Root root) {
//        int type = root.getWindowLayoutParams().get().type;
//        if((type == WindowManager.LayoutParams.TYPE_TOAST)){
//            IBinder windowToken = root.getDecorView().getWindowToken();
//            IBinder appToken = root.getDecorView().getApplicationWindowToken();
//            return  windowToken == appToken;
//        }
//        return false;
//    }
//
////    @Override
////    public boolean matches(Object item) {
////        return false;
////    }
//
//    @Override
//    public void describeMismatch(Object item, Description mismatchDescription) {
//
//    }
//
//    @Override
//    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
//
//    }
//
//    @Override
//    public void describeTo(Description description) {
//        description.appendText("is toast");
//    }

}
