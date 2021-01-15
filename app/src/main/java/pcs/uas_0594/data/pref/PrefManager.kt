package pcs.uas_0594.data.pref

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "pareId"
private const val KEY_USER_ID = "key_user_id"

class PrefManager(context: Context) {

    private val sp: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clearId() {
        sp.edit().remove(KEY_USER_ID).apply()
    }


//    var spUsername: String?
//        get() = sp.getString("KEY_USERNAME", "")
//        set(value) {
//            spe.putString("KEY_USERNAME", value)
//            spe.commit()
//        }
//
//    var spPassword: String?
//        get() = sp.getString("KEY_PASSWORD", "")
//        set(value) {
//            spe.putString("KEY_PASSWORD", value)
//            spe.commit()
//        }
}
