package com.van.shortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            Log.i(TAG, "Max shortcuts : " + shortcutManager.getMaxShortcutCountPerActivity());
            // 7.1 1 只會顯示從列表拉出的 Pinned shortcuts, 8.0 Pinned 已整合
            Log.i(TAG, "Pinned shortcuts : " + shortcutManager.getPinnedShortcuts().size());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_set:
                Intent iSet1 = new Intent(this, OneActivity.class);
                Intent iSet2 = new Intent(this, TwoActivity.class);
                Intent iSet3 = new Intent(this, ThreeActivity.class);
                // 必設 action
                iSet1.setAction("com.van.from.dynamic_shortcut");
                iSet2.setAction("com.van.from.dynamic_shortcut");
                iSet3.setAction("com.van.from.dynamic_shortcut");
                Intent[] iSets = {iSet1, iSet2, iSet3};
                ShortcutInfo sSet = Utility.getShortcutInfo(this, "dynamic_shortcut1", R.drawable
                                .ic_looks_one_red_24dp, getString(R.string.dynamic_short_label_1),
                        getString(R.string.dynamic_long_label_1), getString(R.string
                                .dynamic_disable_msg_1), iSets, null);
                Utility.setDynamicShortcuts(this, Collections.singletonList(sSet));
                break;
            case R.id.bt_add:
                Intent iAdd = new Intent(this, TwoActivity.class);
                // 必設 action
                iAdd.setAction("com.van.from.dynamic_shortcut");
                ShortcutInfo sAdd1 = Utility.getShortcutInfo(this, "dynamic_shortcut1", R
                                .drawable.ic_looks_two_red_24dp, getString(R.string
                                .dynamic_short_label_2), getString(R.string.dynamic_long_label_2),
                        getString(R.string.dynamic_disable_msg_2), iAdd, null);
                ShortcutInfo sAdd2 = Utility.getShortcutInfo(this, "dynamic_shortcut2", R
                                .drawable.ic_looks_two_red_24dp, getString(R.string
                                .dynamic_short_label_2), getString(R.string.dynamic_long_label_2),
                        getString(R.string.dynamic_disable_msg_2), iAdd, null);
                List<ShortcutInfo> adds = new ArrayList<>();
                adds.add(sAdd1);
                adds.add(sAdd2);
                Utility.addDynamicShortcuts(this, adds);
                break;
            case R.id.bt_update:
                Intent iUpdate = new Intent(this, TwoActivity.class);
                // 必設 action
                iUpdate.setAction("com.van.from.dynamic_shortcut");
                ShortcutInfo sUpdate1 = Utility.getShortcutInfo(this, "dynamic_shortcut1", R
                                .drawable.ic_looks_one_red_24dp, getString(R.string
                                .dynamic_short_label_1), getString(R.string.dynamic_long_label_1),
                        getString(R.string.dynamic_disable_msg_1), iUpdate, null);
                ShortcutInfo sUpdate2 = Utility.getShortcutInfo(this, "dynamic_shortcut4", R
                                .drawable.ic_looks_3_red_24dp, getString(R.string
                                .dynamic_short_label_3),
                        getString(R.string.dynamic_long_label_3), getString(R.string
                                .dynamic_disable_msg_3), iUpdate, null);
                List<ShortcutInfo> updates = new ArrayList<>();
                updates.add(sUpdate1);
                updates.add(sUpdate2);
                Utility.updateDynamicShortcuts(this, updates);
                break;
            case R.id.bt_del:
                List<String> delIds = new ArrayList<>();
                delIds.add("dynamic_shortcut1");
                delIds.add("dynamic_shortcut4");
                Utility.delDynamicShortcuts(this, delIds);
                break;
            case R.id.bt_del_all:
                Utility.delAllDynamicShortcuts(this);
                break;
            case R.id.bt_disable:
                List<String> DisableIds = new ArrayList<>();
                DisableIds.add("dynamic_shortcut2");
                DisableIds.add("dynamic_shortcut8");
                Utility.disableShortcuts(this, DisableIds);
                break;
            case R.id.bt_pinned:
                Intent ipinned = new Intent(this, TwoActivity.class);
                ipinned.setAction("com.van.from.api_pinned");
                Utility.addPinnedShortcuts(this, "pinned_shortcut1", R.drawable
                                .ic_looks_one_blue_24dp, getString(R.string.pinned_short_label_1),
                        getString(R.string.pinned_disable_msg_1), ipinned);
                break;
        }
    }
}
