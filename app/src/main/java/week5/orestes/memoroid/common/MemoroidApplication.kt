package week5.orestes.memoroid.common

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

import com.orhanobut.hawk.Hawk

class MemoroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        Fresco.initialize(this)

    }
}
