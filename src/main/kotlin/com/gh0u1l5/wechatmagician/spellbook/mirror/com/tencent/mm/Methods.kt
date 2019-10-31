package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm

import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxVersion
import com.gh0u1l5.wechatmagician.spellbook.base.Version
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.Classes.ImgInfoStorage
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.Classes.LruCacheWithListener
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findMethodsByExactParameters
import java.lang.reflect.Method

object Methods {
    val ImgInfoStorage_load: Method by wxLazy("ImgInfoStorage_load") {
        findMethodsByExactParameters(ImgInfoStorage, C.String, C.String, C.String, C.String, C.Boolean)
                .firstOrNull()?.apply { isAccessible = true }
    }

    val LruCacheWithListener_put: Method by wxLazy("LruCacheWithListener_put") {
        if (wxVersion!! < Version("7.0.7")) {
            findMethodsByExactParameters(LruCacheWithListener, null, C.Object, C.Object)
                .firstOrNull()?.apply { isAccessible = true }

        } else {
            findMethodsByExactParameters(LruCacheWithListener, C.Object, C.String, C.Object)
                .firstOrNull()?.apply { isAccessible = true }
        }
    }
}