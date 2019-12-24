package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm

import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxVersion
import com.gh0u1l5.wechatmagician.spellbook.base.Version
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.Classes.ImgInfoStorage
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.Classes.LruCacheWithListener
import com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.Classes.LruCacheWithListenerType
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findFieldsWithGenericType
import java.lang.reflect.Field

object Fields {
    val ImgInfoStorage_mBitmapCache: Field by wxLazy("ImgInfoStorage_mBitmapCache") {
        var genericTypeName =
            "${LruCacheWithListener.canonicalName}<java.lang.String, android.graphics.Bitmap>"
        if (wxVersion!! >= Version("7.0.9")) {
            genericTypeName =
                "${LruCacheWithListenerType.canonicalName}<java.lang.String, android.graphics.Bitmap>"
        }

        findFieldsWithGenericType(ImgInfoStorage, genericTypeName).firstOrNull()
            ?.apply { isAccessible = true }
    }
}