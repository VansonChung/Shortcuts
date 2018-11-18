package com.van.shortcuts;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.util.Log;

import java.util.List;
import java.util.Set;

public class Utility {

    private static final String TAG = "Utility";

    public static ShortcutInfo getShortcutInfo(Context context, String id, int resIconId, String
            shortLabel, String longLabel, String disableMsg, Intent intent, Set<String>
                                                       categories) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            try {
                return new ShortcutInfo.Builder(context, id).setIcon(Icon
                        .createWithResource(context, resIconId)).setShortLabel(shortLabel)
                        .setLongLabel(longLabel).setDisabledMessage(disableMsg).setIntent(intent)
                        .setCategories(categories)
                        .build();
            } catch (Exception e) {
                Log.e(TAG, "getShortcutInfo e : " + e.toString());
            }
        }
        return null;
    }

    public static ShortcutInfo getShortcutInfo(Context context, String id, int resIconId, String
            shortLabel, String longLabel, String disableMsg, Intent[] intent, Set<String>
                                                       categories) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            try {
                return new ShortcutInfo.Builder(context, id).setIcon(Icon
                        .createWithResource(context, resIconId)).setShortLabel(shortLabel)
                        .setLongLabel(longLabel).setDisabledMessage(disableMsg).setIntents(intent)
                        .setCategories(categories)
                        .build();
            } catch (Exception e) {
                Log.e(TAG, "getShortcutInfo e : " + e.toString());
            }
        }
        return null;
    }

    public static void setDynamicShortcuts(Context context, List<ShortcutInfo> shortcuts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // 換為此組全新的 shortcuts.
                boolean success = shortcutManager.setDynamicShortcuts(shortcuts);
                Log.i(TAG, "setDynamicShortcuts : " + success);
            } catch (Exception e) {
                Log.e(TAG, "setDynamicShortcuts e : " + e.toString());
            }
        }
    }

    public static void addDynamicShortcuts(Context context, List<ShortcutInfo> shortcuts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // 先前的 shortcuts 加上此組, id 相同則取代.
                boolean success = shortcutManager.addDynamicShortcuts(shortcuts);
                Log.i(TAG, "addDynamicShortcuts : " + success);
            } catch (Exception e) {
                Log.e(TAG, "addDynamicShortcuts e : " + e.toString());
            }
        }
    }

    public static void updateDynamicShortcuts(Context context, List<ShortcutInfo> shortcuts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // 更新先前的 shortcuts, id 相同才更新, 不存在的 id 則無視.
                boolean success = shortcutManager.updateShortcuts(shortcuts);
                Log.i(TAG, "updateDynamicShortcuts : " + success);
            } catch (Exception e) {
                Log.e(TAG, "updateDynamicShortcuts e : " + e.toString());
            }
        }
    }

    public static void delDynamicShortcuts(Context context, List<String> ids) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // 刪除該 id shortcuts, 不存在的 id 無視.
                shortcutManager.removeDynamicShortcuts(ids);
                Log.i(TAG, "delDynamicShortcuts");
            } catch (Exception e) {
                Log.e(TAG, "delDynamicShortcuts e : " + e.toString());
            }
        }
    }

    public static void delAllDynamicShortcuts(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // 刪除全部
                shortcutManager.removeAllDynamicShortcuts();
                Log.i(TAG, "delAllDynamicShortcuts");
            } catch (Exception e) {
                Log.e(TAG, "delAllDynamicShortcuts e : " + e.toString());
            }
        }
    }

    public static void disableShortcuts(Context context, List<String> ids) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            try {
                // disable shortcuts, 不存在的 id 無視.
                shortcutManager.disableShortcuts(ids);
                Log.i(TAG, "disableShortcuts");
            } catch (Exception e) {
                Log.e(TAG, "disableShortcuts e : " + e.toString());
            }
        }
    }

    public static void addPinnedShortcuts(Context context, String id, int resIconId, String
            shortLabel, String disableMsg, Intent intent) {
        //  8.0 以上, id 與 Static/Dynamic 重複則取 Static/Dynamic 參數.
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, id)
                    .setIcon(IconCompat.createWithResource(context, resIconId))
                    .setShortLabel(shortLabel)
                    .setDisabledMessage(disableMsg)
                    .setIntent(intent)
                    .build();

            // 8.0 以上會跳出確認添加捷徑 dialog 給 user, 跳出時 callback 此 broadcast.
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new
                    Intent(context, ShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            ShortcutManagerCompat.requestPinShortcut(context, info, shortcutCallbackIntent
                    .getIntentSender());
        }
    }

    public static void addPinnedShortcuts(Context context, String id, int resIconId, String
            shortLabel, String disableMsg, Intent[] intents) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, id)
                    .setIcon(IconCompat.createWithResource(context, resIconId))
                    .setShortLabel(shortLabel)
                    .setDisabledMessage(disableMsg)
                    .setIntents(intents)
                    .build();

            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new
                    Intent(context, ShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            ShortcutManagerCompat.requestPinShortcut(context, info, shortcutCallbackIntent
                    .getIntentSender());
        }
    }
}
