package com.van.shortcuts

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.van.shortcuts.utility.Utility
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            Log.i(TAG, "Max shortcuts : ${shortcutManager.maxShortcutCountPerActivity}")
            // 7.1 1 只會顯示從列表拉出的 Pinned shortcuts, 8.0 Pinned 已整合
            Log.i(TAG, "Pinned shortcuts : ${shortcutManager.pinnedShortcuts.size}")
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.bt_set -> {
                val iSet1 = Intent(this, OneActivity::class.java)
                val iSet2 = Intent(this, TwoActivity::class.java)
                val iSet3 = Intent(this, ThreeActivity::class.java)
                // 必設 action
                iSet1.action = "com.van.from.dynamic_shortcut"
                iSet2.action = "com.van.from.dynamic_shortcut"
                iSet3.action = "com.van.from.dynamic_shortcut"
                val iSets = arrayOf(iSet1, iSet2, iSet3)
                val sSet = Utility.createShortcutInfo(
                    this,
                    "dynamic_shortcut1",
                    R.drawable.ic_looks_one_red_24dp,
                    getString(R.string.dynamic_short_label_1),
                    getString(R.string.dynamic_long_label_1),
                    getString(R.string.dynamic_disable_msg_1),
                    iSets,
                    null
                )
                Utility.setDynamicShortcuts(this, listOf(sSet))
            }
            R.id.bt_add -> {
                val iAdd = Intent(this, TwoActivity::class.java)
                // 必設 action
                iAdd.action = "com.van.from.dynamic_shortcut"
                val sAdd1 = Utility.createShortcutInfo(
                    this,
                    "dynamic_shortcut1",
                    R.drawable.ic_looks_two_red_24dp,
                    getString(R.string.dynamic_short_label_2),
                    getString(R.string.dynamic_long_label_2),
                    getString(R.string.dynamic_disable_msg_2),
                    iAdd,
                    null
                )
                val sAdd2 = Utility.createShortcutInfo(
                    this,
                    "dynamic_shortcut2",
                    R.drawable.ic_looks_two_red_24dp,
                    getString(R.string.dynamic_short_label_2),
                    getString(R.string.dynamic_long_label_2),
                    getString(R.string.dynamic_disable_msg_2),
                    iAdd,
                    null
                )
                val adds = ArrayList<ShortcutInfo>()
                adds.add(sAdd1)
                adds.add(sAdd2)
                Utility.addDynamicShortcuts(this, adds)
            }
            R.id.bt_update -> {
                val iUpdate = Intent(this, TwoActivity::class.java)
                // 必設 action
                iUpdate.action = "com.van.from.dynamic_shortcut"
                val sUpdate1 = Utility.createShortcutInfo(
                    this,
                    "dynamic_shortcut1",
                    R.drawable.ic_looks_one_red_24dp,
                    getString(R.string.dynamic_short_label_1),
                    getString(R.string.dynamic_long_label_1),
                    getString(R.string.dynamic_disable_msg_1),
                    iUpdate,
                    null
                )
                val sUpdate2 = Utility.createShortcutInfo(
                    this,
                    "dynamic_shortcut4",
                    R.drawable.ic_looks_3_red_24dp,
                    getString(R.string.dynamic_short_label_3),
                    getString(R.string.dynamic_long_label_3),
                    getString(R.string.dynamic_disable_msg_3),
                    iUpdate,
                    null
                )
                val updates = ArrayList<ShortcutInfo>()
                updates.add(sUpdate1)
                updates.add(sUpdate2)
                Utility.updateDynamicShortcuts(this, updates)
            }
            R.id.bt_del -> {
                val delIds = ArrayList<String>()
                delIds.add("dynamic_shortcut1")
                delIds.add("dynamic_shortcut4")
                Utility.delDynamicShortcuts(this, delIds)
            }
            R.id.bt_del_all -> Utility.delAllDynamicShortcuts(this)
            R.id.bt_disable -> {
                val disableIds = ArrayList<String>()
                disableIds.add("dynamic_shortcut2")
                disableIds.add("dynamic_shortcut8")
                Utility.disableShortcuts(this, disableIds)
            }
            R.id.bt_pinned -> {
                val ipinned = Intent(this, TwoActivity::class.java)
                ipinned.action = "com.van.from.api_pinned"
                Utility.addPinnedShortcuts(
                    this,
                    "dynamic_shortcut1",
                    R.drawable.ic_looks_one_blue_24dp,
                    getString(R.string.pinned_short_label_1),
                    getString(R.string.pinned_disable_msg_1),
                    ipinned
                )
            }
        }
    }
}