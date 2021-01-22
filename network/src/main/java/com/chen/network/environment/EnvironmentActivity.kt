package com.chen.network.environment

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.chen.network.R
import com.chen.network.databinding.ActivityEnvironmentBinding

const val NETWORK_ENVIRONMENT_PREF_KEY = "network_environment_type"

class EnvironmentActivity : AppCompatActivity(){

    companion object{
        fun isOfficialEnvironment(application: Application) : Boolean{
            val prefs = PreferenceManager.getDefaultSharedPreferences(application)
            val environment: String = prefs?.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1") ?: "1"
            return "1".equals(environment, ignoreCase = true)
        }
    }


    private var sCurrentNetworkEnvironment = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEnvironmentBinding>(this , R.layout.activity_environment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MyPreferenceFragment())
            .commit()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        sCurrentNetworkEnvironment = prefs?.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1") ?: "1"
    }


    inner class MyPreferenceFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.environment_preference)
            findPreference<ListPreference>(NETWORK_ENVIRONMENT_PREF_KEY)?.setOnPreferenceChangeListener(this)
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            if (!sCurrentNetworkEnvironment.equals(newValue.toString(), ignoreCase = true)) {
                Toast.makeText(context, "您已经更改了网络环境，再您退出当前页面的时候APP将会重启切换环境！", Toast.LENGTH_SHORT).show();
            }
            return true
        }
    }

    override fun onBackPressed() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val newValue = prefs?.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1")
        if (!sCurrentNetworkEnvironment.equals(newValue, ignoreCase = true)) {
            android.os.Process.killProcess(android.os.Process.myPid())
        } else {
            finish()
        }
    }
}