package cn.hc.downloadmanger

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * @ClassName: SPUtils
 * @Description: 存储器
 * @Author: zengqiang
 * @github: https://github.com/zqMyself
 * @Date: 2020-12-31 13:47
 */
class SPUtils {
    private val SP_UTILS_MAP: MutableMap<String, SPUtils> =HashMap()
    private var sp: SharedPreferences? = null

    /**
     * Return the single [SPUtils] instance
     *
     * @return the single [SPUtils] instance
     */
    companion object{

        var spUtils = SPUtils()
        fun getInstance():SPUtils{
            if (spUtils == null)
                spUtils = SPUtils()

            return spUtils
        }
    }
    constructor(){
        getInstance()
    }
    fun getInstance(): SPUtils? {
        return getInstance("", Context.MODE_PRIVATE)
    }

    /**
     * Return the single [SPUtils] instance
     *
     * @param mode Operating mode.
     * @return the single [SPUtils] instance
     */
    fun getInstance(mode: Int): SPUtils? {
        return getInstance("", mode)
    }

    /**
     * Return the single [SPUtils] instance
     *
     * @param spName The name of sp.
     * @return the single [SPUtils] instance
     */
    fun getInstance(spName: String): SPUtils? {
        return getInstance(spName, Context.MODE_PRIVATE)
    }

    /**
     * Return the single [SPUtils] instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single [SPUtils] instance
     */
    fun getInstance(spName: String, mode: Int): SPUtils? {
        var spName = spName
        if (isSpace(spName)) spName = "spUtils"
        var spUtils = SP_UTILS_MAP[spName]
        if (spUtils == null) {
            synchronized(SPUtils::class.java) {
                spUtils = SP_UTILS_MAP[spName]
                if (spUtils == null) {
                    spUtils = SPUtils(spName, mode)
                    SP_UTILS_MAP[spName] = spUtils!!
                }
            }
        }
        return spUtils
    }

    private fun SPUtils(spName: String) {
        sp = MyApplication.context!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    private fun SPUtils(spName: String, mode: Int): SPUtils {
        sp = MyApplication.context!!.getSharedPreferences(spName, mode)
        return this
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: String?) {
        put(key, value, false)
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: String?, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().putString(key, value).commit()
        } else {
            sp!!.edit().putString(key, value).apply()
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or `""` otherwise
     */
    fun getString(key: String): String? {
        return getString(key, "")
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    fun getString(key: String, defaultValue: String?): String? {
        return sp!!.getString(key, defaultValue)
    }

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Int) {
        put(key, value, false)
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Int, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().putInt(key, value).commit()
        } else {
            sp!!.edit().putInt(key, value).apply()
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or `-1` otherwise
     */
    fun getInt(key: String): Int {
        return getInt(key, -1)
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return sp!!.getInt(key, defaultValue)
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Long) {
        put(key, value, false)
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Long, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().putLong(key, value).commit()
        } else {
            sp!!.edit().putLong(key, value).apply()
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or `-1` otherwise
     */
    fun getLong(key: String): Long {
        return getLong(key, -1L)
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    fun getLong(key: String, defaultValue: Long): Long {
        return sp!!.getLong(key, defaultValue)
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Float) {
        put(key, value, false)
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Float, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().putFloat(key, value).commit()
        } else {
            sp!!.edit().putFloat(key, value).apply()
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or `-1f` otherwise
     */
    fun getFloat(key: String): Float {
        return getFloat(key, -1f)
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        return sp!!.getFloat(key, defaultValue)
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Boolean) {
        put(key, value, false)
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Boolean, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().putBoolean(key, value).commit()
        } else {
            sp!!.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or `false` otherwise
     */
    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sp!!.getBoolean(key, defaultValue)
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Set<String?>?) {
        put(key, value, false)
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(
        key: String,
        value: Set<String?>?,
        isCommit: Boolean
    ) {
        if (isCommit) {
            sp!!.edit().putStringSet(key, value).commit()
        } else {
            sp!!.edit().putStringSet(key, value).apply()
        }
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or `Collections.<String>emptySet()` otherwise
     */
    fun getStringSet(key: String): Set<String?>? {
        return getStringSet(key, emptySet<String>())
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or `defaultValue` otherwise
     */
    fun getStringSet(
        key: String,
        defaultValue: Set<String?>?
    ): Set<String?>? {
        return sp!!.getStringSet(key, defaultValue)
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    fun getAll(): Map<String?, *>? {
        return sp!!.all
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return `true`: yes<br></br>`false`: no
     */
    operator fun contains(key: String): Boolean {
        return sp!!.contains(key)
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    fun remove(key: String) {
        remove(key, false)
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun remove(key: String, isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().remove(key).commit()
        } else {
            sp!!.edit().remove(key).apply()
        }
    }

    /**
     * Remove all preferences in sp.
     */
    fun clear() {
        clear(false)
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun clear(isCommit: Boolean) {
        if (isCommit) {
            sp!!.edit().clear().commit()
        } else {
            sp!!.edit().clear().apply()
        }
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}