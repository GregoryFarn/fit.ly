package com.example.a007fa.fitly;


import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.uiautomator.UiDevice;


import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.v4.content.ContextCompat.getSystemService;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class NotificationsTest extends Fragment {


  /*  @Rule
    public ActivityTestRule<addActivity> mActivityRule = new ActivityTestRule(addActivity.class);


    //Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(addActivity.class.getName(), null, false);


    UiDevice mDevice;

    private Notifications test = new Notifications();
    private Context context = InstrumentationRegistry.getTargetContext();

//    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
//        return new TypeSafeMatcher<View>() {
//            int currentIndex = 0;
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("with index: ");
//                description.appendValue(index);
//                matcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                return matcher.matches(view) && currentIndex++ == index;
//            }
//        };
//    }
    @Before
    public void init(){
        mActivityRule.getActivity()
                .getFragmentManager().beginTransaction();
    }

  public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
      return new TypeSafeMatcher<View>() {
          int currentIndex = 0;

          @Override
          public void describeTo(Description description) {
              description.appendText("with index: ");
              description.appendValue(index);
              matcher.describeTo(description);
          }

          @Override
          public boolean matchesSafely(View view) {
              return matcher.matches(view) && currentIndex++ == index;
          }
      };
  }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(addActivity.class, false, true);





    @Before
    public void init(){
        mActivityRule.getActivity()
                .getFragmentManager().beginTransaction();
    }


    @Test
    public void testNotification(){
        //mActivityRule.getActivity();

        mActivityRule.getActivity().findViewById(R.id.show).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                Notifications t1 = new Notifications();
//                Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), addActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("requestKey", 2);
//                intent.putExtras(bundle);
//                t1.onReceive(getInstrumentation().getTargetContext(), intent);
                NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(InstrumentationRegistry.getTargetContext())
                        .setContentText("notification")
                        .setContentTitle("fitly")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) InstrumentationRegistry.getTargetContext().getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.notify(0,mBuilder.build());
            }
        });

        onView(allOf(withId(R.id.show), hasFocus())).perform(click());
           // onView(withId(R.id.show)).perform(click());
//        AlarmManager am =  (AlarmManager)context.getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent( context, MainActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("requestKey", 3);
//        intent.putExtras(bundle);
//        PendingIntent sender = PendingIntent.getBroadcast(context,3, intent, 0);
//        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
//       // test.onReceive(getInstrumentation().getTargetContext(), this.intent);
//        Intent intent1 = new Intent( getInstrumentation().getTargetContext(), Notifications.class);
//         AlarmManager am =  (AlarmManager)getInstrumentation().getContext().getSystemService(ALARM_SERVICE);
//        // AlarmManager am =  (AlarmManager)mActivityRule.getActivity().getSystemService(ALARM_SERVICE);
//         PendingIntent sender = PendingIntent.getBroadcast(getInstrumentation().getTargetContext(),2, this.intent, 0);
//         am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
////        Bundle bundle = new Bundle();
//        bundle.putInt("requestKey", 8);
       // resultIntent.putExtras(bundle);
         // test.onReceive(InstrumentationRegistry.getInstrumentation().getTargetContext(), resultIntent);

//        Intent resultIntent = new Intent(InstrumentationRegistry.getInstrumentation().getContext() , MainActivity.class);
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Notifications t1 = new Notifications();
////        Bundle bundle = new Bundle();
////        bundle.putInt("requestKey", 8);
////        resultIntent.putExtras(bundle);
//
//        String channelId = "channel";
//
    }

*/

    @Test
    public void getBundleTest(){
        Notifications test = new Notifications();
        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", 2);
        intent.putExtras(bundle);

        assertEquals("Expected request int value 2", test.getRequestCode(intent),2 );


    }

}