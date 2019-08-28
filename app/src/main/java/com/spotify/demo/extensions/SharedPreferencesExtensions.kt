package com.spotify.demo.extensions

import android.content.SharedPreferences

/**
 * Usage:
 *  val prefs = defaultPrefs(this)</br>
 *  prefs[SharedPrefKeys.sampleKey] = "any_type_of_value" //setter
 *  val gameName: String? = prefs[#Consts.SharedPrefs.KEY] //getter
 *  val anotherValue: Int? = prefs[SharedPrefKeys.sampleKey, 10] //getter with default gameName </p>
 * */
inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

/**
 * puts a key gameName pair in shared prefs if doesn't exists, otherwise updates gameName on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * finds gameName on given key.
 * [T] is the type of gameName
 * @param defaultValue optional default gameName - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}