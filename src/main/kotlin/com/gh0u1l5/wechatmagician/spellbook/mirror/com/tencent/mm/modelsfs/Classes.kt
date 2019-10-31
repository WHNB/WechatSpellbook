package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.modelsfs

import com.gh0u1l5.wechatmagician.spellbook.C
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxClasses
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxVersion
import com.gh0u1l5.wechatmagician.spellbook.base.Version
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassesFromPackage

object Classes {
    val EncEngine: Class<*> by wxLazy("EncEngine") {
        if (wxVersion!! < Version("7.0.7")) {
            findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.modelsfs")
                .filterByMethod(null, "seek", C.Long)
                .filterByMethod(null, "free")
                .firstOrNull()

        } else {
            findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.modelsfs")
                .filterByMethod(null, "init")
                .filterByMethod(null, "free")
                .firstOrNull()
        }
    }
}