package pcs.uas_0594

import android.app.Application
import pcs.uas_0594.data.db.AppDatabase
import pcs.uas_0594.data.pref.PrefManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class RunIn : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@RunIn))

    }

    companion object {
        @get:Synchronized
        lateinit var instance: RunIn
        lateinit var prefManager: PrefManager
        lateinit var db: AppDatabase

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = PrefManager(this)
        db = AppDatabase(this)
    }
}