package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm

import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxClasses
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxVersion
import com.gh0u1l5.wechatmagician.spellbook.base.Version
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.sdk.platformtools.Classes.LruCache
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassesFromPackage

object Classes {
    val ImgInfoStorage: Class<*> by wxLazy("ImgInfoStorage") {
        findClassesFromPackage(wxLoader!!, wxClasses!!, wxPackageName, 1)
            .filterByMethod(C.String, C.String, C.String, C.String, C.Boolean)
            .firstOrNull()
    }

    val LruCacheWithListener: Class<*> by wxLazy("LruCacheWithListener") {
        if (wxVersion!! < Version("7.0.9")) {
            findClassesFromPackage(wxLoader!!, wxClasses!!, wxPackageName, 1)
                .filterBySuper(LruCache)
                .firstOrNull()

        } else {
            findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.memory", 1)
                .filterByMethod(C.Object, "put", C.String, C.Object)
                .filterByMethod(C.Object, "get", C.String)
                .firstOrNull()
        }
    }

    val LruCacheWithListenerType: Class<*> by wxLazy("LruCacheWithListenerType") {
        if (wxVersion!! < Version("7.0.9")) {
            findClassesFromPackage(wxLoader!!, wxClasses!!, wxPackageName, 1)
                .filterBySuper(LruCache)
                .firstOrNull()

        } else {
            findClassesFromPackage(wxLoader!!, wxClasses!!, wxPackageName, 1)
                .filterByMethod(C.Int, "missCount")
                .filterByMethod(C.Int, "createCount")
                .firstOrNull()
        }
    }
}