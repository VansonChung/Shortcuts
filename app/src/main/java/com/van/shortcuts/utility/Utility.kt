package com.van.shortcuts.utility

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.util.Log
import com.van.shortcuts.ShortcutReceiver
import java.lang.Exception

object Utility {

    private const val TAG = "Utility"

    fun createShortcutInfo(
        context: Context,
        id: String,
        resIconId: Int,
        shortLabel: String,
        longLabel: String,
        disableMsg: String,
        intent: Intent,
        categories: Set<String>?
    ): ShortcutInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return ShortcutInfo.Builder(context, id)
                .setIcon(Icon.createWithResource(context, resIconId)).setShortLabel(shortLabel)
                .setLongLabel(longLabel).setDisabledMessage(disableMsg).setIntent(intent)
                .setCategories(categories)
                .build()
        }
        throw Exception("The Build.VERSION is too low")
    }

    fun createShortcutInfo(
        context: Context,
        id: String,
        resIconId: Int,
        shortLabel: String,
        longLabel: String,
        disableMsg: String,
        intent: Array<Intent>,
        categories: Set<String>?
    ): ShortcutInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return ShortcutInfo.Builder(context, id).setIcon(
                Icon
                    .createWithResource(context, resIconId)
            ).setShortLabel(shortLabel)
                .setLongLabel(longLabel).setDisabledMessage(disableMsg).setIntents(intent)
                .setCategories(categories)
                .build()
        }
        throw Exception("The Build.VERSION is too low")
    }

    fun setDynamicShortcuts(context: Context, shortcuts: List<ShortcutInfo>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            try {
                // 換為此組全新的 shortcuts.
                val success = shortcutManager.setDynamicShortcuts(shortcuts)
                Log.i(TAG, "setDynamicShortcuts : $success")
            } catch (e: Exception) {
                // Maybe the same id (static / dynamic)
                Log.e(TAG, "setDynamicShortcuts e : " + e.toString())
            }

        }
    }

    fun addDynamicShortcuts(context: Context, shortcuts: List<ShortcutInfo>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            try {
                // 先前的 shortcuts 加上此組, id 相同則取代.
                val success = shortcutManager.addDynamicShortcuts(shortcuts)
                Log.i(TAG, "addDynamicShortcuts : $success")
            } catch (e: Exception) {
                // Maybe the same id (static / dynamic)
                Log.e(TAG, "addDynamicShortcuts e : " + e.toString())
            }

        }
    }

    fun updateDynamicShortcuts(context: Context, shortcuts: List<ShortcutInfo>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            try {
                // 更新先前的 shortcuts, id 相同才更新, 不存在的 id 則無視.
                val success = shortcutManager.updateShortcuts(shortcuts)
                Log.i(TAG, "updateDynamicShortcuts : $success")
            } catch (e: Exception) {
                // Maybe the same id (static / dynamic)
                Log.e(TAG, "updateDynamicShortcuts e : " + e.toString())
            }

        }
    }

    fun delDynamicShortcuts(context: Context, ids: List<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            try {
                // 刪除該 id shortcuts, 不存在的 id 無視.
                shortcutManager.removeDynamicShortcuts(ids)
                Log.i(TAG, "delDynamicShortcuts")
            } catch (e: Exception) {
                // Maybe the same id (static / dynamic)
                Log.e(TAG, "delDynamicShortcuts e : " + e.toString())
            }

        }
    }

    fun delAllDynamicShortcuts(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            // 刪除全部
            shortcutManager.removeAllDynamicShortcuts()
            Log.i(TAG, "delAllDynamicShortcuts")

        }
    }

    fun disableShortcuts(context: Context, ids: List<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            try {
                // disable shortcuts, 不存在的 id 無視.
                shortcutManager.disableShortcuts(ids)
                Log.i(TAG, "disableShortcuts")
            } catch (e: Exception) {
                // Maybe the same id (static / dynamic)
                Log.e(TAG, "disableShortcuts e : " + e.toString())
            }

        }
    }

    fun addPinnedShortcuts(
        context: Context,
        id: String,
        resIconId: Int,
        shortLabel: String,
        disableMsg: String,
        intent: Intent
    ) {
        //  8.0 以上, id 與 Static/Dynamic 重複則取 Static/Dynamic 參數.
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val info = ShortcutInfoCompat.Builder(context, id)
                .setIcon(IconCompat.createWithResource(context, resIconId))
                .setShortLabel(shortLabel)
                .setDisabledMessage(disableMsg)
                .setIntent(intent)
                .build()

            // 8.0 以上會跳出確認添加捷徑 dialog 給 user, 跳出時 callback 此 broadcast.
            val shortcutCallbackIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, ShortcutReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            ShortcutManagerCompat.requestPinShortcut(
                context, info, shortcutCallbackIntent.intentSender
            )
        }
    }

    fun addPinnedShortcuts(
        context: Context,
        id: String,
        resIconId: Int,
        shortLabel: String,
        disableMsg: String,
        intents: Array<Intent>
    ) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val info = ShortcutInfoCompat.Builder(context, id)
                .setIcon(IconCompat.createWithResource(context, resIconId))
                .setShortLabel(shortLabel)
                .setDisabledMessage(disableMsg)
                .setIntents(intents)
                .build()

            val shortcutCallbackIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, ShortcutReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            ShortcutManagerCompat.requestPinShortcut(
                context, info, shortcutCallbackIntent.intentSender
            )
        }
    }
}