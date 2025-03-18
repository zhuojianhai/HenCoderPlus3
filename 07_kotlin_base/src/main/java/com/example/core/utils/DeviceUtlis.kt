package com.example.core.utils

import android.content.Context
import android.provider.Settings
import java.util.UUID

object DeviceUtlis {

    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
    }

    fun getUUIDFromAndroidId(androidId: String): String {
        return if (androidId == "9774d56d682e549c" || androidId.isEmpty()) {
            UUID.randomUUID().toString() // 如果 ANDROID_ID 无效，生成新的 UUID
        } else {
            UUID.nameUUIDFromBytes(androidId.toByteArray()).toString()
        }
    }

    fun getAppGUID(context: Context): String {
        val prefs = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        var guid = prefs.getString("app_guid", null)
        if (guid == null) {
            guid = UUID.randomUUID().toString() // 生成新的 GUID
            prefs.edit().putString("app_guid", guid).apply()
        }
        return guid
    }

    fun getUniqueDeviceId(context: Context): String {
        val androidId = getAndroidId(context) // 获取 ANDROID_ID
        val uuid = getUUIDFromAndroidId(androidId) // 生成基于 ANDROID_ID 的 UUID
        val guid = getAppGUID(context) // 获取应用 GUID

        // 组合三者，确保最大唯一性
        return UUID.nameUUIDFromBytes((androidId + uuid + guid).toByteArray()).toString()
    }
}