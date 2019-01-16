package com.simorgh.redcalendar.Model;

import android.util.Log;

import com.facebook.stetho.Stetho;
import com.simorgh.redcalendar.BuildConfig;
import com.simorgh.redcalendar.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.multidex.MultiDexApplication;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class AppManager extends MultiDexApplication {
    public static final String TAG = "debug13";
    public static Calendar minDate;
    public static Calendar maxDate;
    public static Locale mLocale;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            LeakCanary.install(this);
//            // Normal app init code...
//
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectAll()
//                    .penaltyLog()
//                    .build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
//                    .penaltyLog()
//                    .penaltyDeath()
//                    .build());
//
            Stetho.initializeWithDefaults(this);
        } else {
            getUncaughtExceptions();
        }


        mLocale = getResources().getConfiguration().locale;

        minDate = getCalendarInstance();
        minDate.set(Calendar.YEAR, 2018);

        maxDate = getCalendarInstance();
        maxDate.set(Calendar.YEAR, 2020);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/iransans_medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    private void getUncaughtExceptions() {
        // Setup handler for uncaught exceptions.
        try {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                Log.e(TAG, "Uncaught Exception thread: " + t.getName() + "" + Arrays.toString(e.getStackTrace()));

            });
        } catch (Exception e) {
            Log.e(TAG, "Could not set the Default Uncaught Exception Handler:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public static Calendar getCalendarInstance() {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"), mLocale);
    }
}
