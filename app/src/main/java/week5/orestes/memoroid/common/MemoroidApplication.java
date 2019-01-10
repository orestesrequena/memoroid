package week5.orestes.memoroid.common;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MemoroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
