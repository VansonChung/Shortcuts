# Shortcuts
#### https://developer.android.com/guide/topics/ui/shortcuts/

![image](https://github.com/VansonChung/Shortcuts/blob/master/shortcuts-1.jpg)    ![image](https://github.com/VansonChung/Shortcuts/blob/master/shortcuts-2.jpg)
         
         Shortcuts 可分為 Static/Dynamic/Pinned, 點擊後可導到該客製化的 Intent.
         
         Static/Dynamic : 常按 APP 後會出現的列表.
         Pinned : 桌面捷徑 (icon), 在 8.0 前需特別區分為從 Static/Dynamic 拉出的與直接由 API 建立的.
                  8.0 前 API 建立的 Pinned 與 Static/Dynamic 拉出的 Pinned 彼此獨立 (id 可重複).
                  8.0 以上由 Static/Dynamic 拉出與 API 建立的 Pinned 則一樣, 相互影響.
                  (API 建立的相當於由 Dynamic 拉出)
                  
         7.1.1 API 25 : 此版本以下只有直接建立桌面捷徑功能 (8.0 開始稱為 Pinned shortcuts).
                        需透過 broadcast 新增, 但透過 broadcast 刪除皆無效...
                        此版本開始有 Static/Dynamic shortcuts.
         8.0   API 26 : 開始有 Pinned shortcuts, broadcast 建立 Pinned 在此版本無效, 新 api 取代並可兼容所有版本                    
                        (ShortcutManagerCompat), 但無法設置是否允許重複 ("duplicate") 建立與刪除.
                  
         8.0 Note : 1. Static/Dynamic 加起來最多 5 個, 但顯示只顯示 4 個 ? Pinned 無限制.
                    2. Static/Dynamic id 不可重複, 會造成 exception.
                    3. Static/Dynamic 不能修改對方的 shortcuts.
                    4. Pinned 不受 Static/Dynamic disable/刪除影響, 永遠存在桌面(id 佔住)(只有使用者可以移除).
                       Static : Disable/刪除則 Pinned 反灰, 點擊 toast disable msg.
                                刪除目標 class 則不反灰, 點擊 toast "APP isn't installed",
                                有 back intent 且存在則在 toast 後接續跳.
                       Dynamic : Disable 則 Pinned 反灰, 點擊 toast disable msg.
                                 刪除與刪除目標 class 不反灰, 點擊跳入該 intent (目標 class 刪除同 Static).
                    5. 修改 Static/Dynamic, Pinned (id 相同) 會直接更新.

         Static shortcuts 實作 :
         
         1. AndroidManifest 中啟動 app 的 activity

            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            添加

            <meta-data
                android:name="android.app.shortcuts"
                android:resource="@xml/shortcuts" />

         2. res/xml/shortcuts.xml 加入詳細項

         Dynamic shortcuts 實作見 MainActivity.java.
