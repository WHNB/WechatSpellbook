package com.gh0u1l5.wechatmagician.spellbook.base

import android.util.Log
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findFieldIfExists
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findFieldsWithType
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findMethodExactIfExists
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findMethodsByExactParameters

/**
 * 一组 Class 对象的集合, 可以通过调用不同的 filter 函数筛选得到想要的结果
 */
class Classes(private val classes: List<Class<*>>) {
    /**
     * @suppress
     */
    private companion object {
        private const val TAG = "Reflection"
    }
    
    fun filterBySuper(superClass: Class<*>?): Classes {
        return Classes(classes.filter { it.superclass == superClass }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterBySuper found nothing, super class = ${superClass?.simpleName}")
            }
        })
    }

    fun filterByEnclosingClass(enclosingClass: Class<*>?): Classes {
        return Classes(classes.filter { it.enclosingClass == enclosingClass }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByEnclosingClass found nothing, enclosing class = ${enclosingClass?.simpleName} ")
            }
        })
    }

    fun filterByMethod(returnType: Class<*>?, methodName: String, vararg parameterTypes: Class<*>): Classes {
        return Classes(classes.filter { clazz ->
            val method = findMethodExactIfExists(clazz, methodName, *parameterTypes)
            method != null && method.returnType == returnType ?: method.returnType
        }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByMethod found nothing, returnType = ${returnType?.simpleName}, methodName = $methodName, parameterTypes = ${parameterTypes.joinToString("|") { it.simpleName }}")
            }
        })
    }

    fun filterByMethod(returnType: Class<*>?, vararg parameterTypes: Class<*>): Classes {
        return Classes(classes.filter { clazz ->
            findMethodsByExactParameters(clazz, returnType, *parameterTypes).isNotEmpty()
        }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByMethod found nothing, returnType = ${returnType?.simpleName}, parameterTypes = ${parameterTypes.joinToString("|") { it.simpleName }}")
            }
        })
    }

    fun filterByField(fieldName: String, fieldType: String): Classes {
        return Classes(classes.filter { clazz ->
            val field = findFieldIfExists(clazz, fieldName)
            field != null && field.type.canonicalName == fieldType
        }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByField found nothing, fieldName = $fieldName, fieldType = $fieldType")
            }
        })
    }

    fun filterByField(fieldType: String): Classes {
        return Classes(classes.filter { clazz ->
            findFieldsWithType(clazz, fieldType).isNotEmpty()
        }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByField found nothing, fieldType = $fieldType")
            }
        })
    }

    fun firstOrNull(): Class<*>? {
        if (classes.size > 1) {
            val names = classes.map { it.canonicalName }
            Log.w(TAG, "found a signature that matches more than one class: $names")
        }
        return classes.firstOrNull()
    }

    fun filterByConstructor(vararg parameterTypes: Class<*>): Classes {
        return Classes(classes.filter { clazz ->
            for (constructor in clazz.constructors) {
                if (parameterTypes.size == constructor.parameterTypes.size) {
                    for (i in parameterTypes.indices) {
                        if (parameterTypes[i] == constructor.parameterTypes[i]) {
                            return@filter true
                        }
                    }
                }
            }

            return@filter false
        })
    }

    fun lastOrNull(): Class<*>? {
        if (classes.size > 1) {
            val names = classes.map { it.canonicalName }
            Log.w(TAG, "[lastOrNull] found a signature that matches more than one class: $names")
        }

        return classes.lastOrNull()
    }

    fun all(): List<Class<*>> {
        return classes
    }

    fun dumpAll() {
        if (classes.isNotEmpty()) {
            val names = classes.map { it.name }
            Log.w(TAG, "[dumpAll] class: $names")

        } else {
            Log.w(TAG, "[dumpAll] do not found a signature that matches any class")
        }
    }

    fun filterByFieldNotInclude(fieldName: String, fieldType: String): Classes {
        return Classes(classes.filterNot { clazz ->
            val field = findFieldIfExists(clazz, fieldName)
            Log.w(TAG, "filterByFieldNotInclude clazz=$clazz field=$field")
            field != null && field.type.canonicalName == fieldType
        }.also {
            if (it.isEmpty()) {
                Log.w(TAG, "filterByFieldNotInclude found nothing, $fieldName $fieldType")
            }
        })
    }

    fun filterByGenericInterfaces(fieldType: String): Classes {
        return Classes(classes.filter { it ->
            it.interfaces.any {
                it.name == fieldType
            }
        })
    }
}