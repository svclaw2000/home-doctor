package com.khnsoft.zipssa

import android.content.Context
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class ServerHandler {
    companion object {
        val alarmToServer = mapOf(
            "alarm_id" to "alarmId",
            "alarm_title" to "title",
            "alarm_user" to "user",
            "alarm_label" to "label",
            "alarm_start_date" to "startDate",
            "alarm_end_date" to "endDate",
            "alarm_times" to "times",
            "alarm_repeats" to "repeats",
            "alarm_enabled" to "alarmStatus"
        )

        val labelToServer = mapOf(
            "label_id" to "labelID",
            "label_user" to "user",
            "label_title" to "title",
            "label_color" to "color"
        )

        val alarmToLocal : Map<String, String>
        val labelToLocal : Map<String, String>

        init {
            val alarm = mutableMapOf<String, String>()
            for (entry in alarmToServer) {
                alarm[entry.value] = entry.key
            }
            alarmToLocal = alarm.toMap()

            val label = mutableMapOf<String, String>()
            for (entry in labelToServer) {
                label[entry.value] = entry.key
            }
            labelToLocal = label.toMap()
        }

        fun convertKeys(jOrigin: JsonObject, keyMap: Map<String, String>) : JsonObject {
            val jRet = JsonObject()

            for (key in jOrigin.keySet()) {
                if (keyMap.containsKey(key)) {
                    jRet.addProperty(keyMap[key], jOrigin[key].asString)
                } else {
                    jRet.addProperty(key, jOrigin[key].asString)
                }
            }

            return jRet
        }

        fun send(context: Context?, api: EndOfAPI, data: JsonObject? = null, id: Int? = null): JsonObject {
            val method = api.method
            val json: JsonObject?
            json = if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                if (data == null) return HttpAttr.ERROR_MSG
                when(api) {
                    EndOfAPI.ADD_ALARM, EndOfAPI.EDIT_ALARM -> convertKeys(data, alarmToServer)
                    EndOfAPI.ADD_LABEL, EndOfAPI.EDIT_LABEL -> convertKeys(data, labelToServer)
                    else -> data
                }
            } else if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                null
            } else {
                return HttpAttr.ERROR_MSG
            }

            val remote: String = if (api.isIdNeeded) {
                if (id == null)
                    return HttpAttr.ERROR_MSG
                else
                    "${api.remote}/${id}"
            } else {
                api.remote
            }

            // /*
            if (UserData.accountType == AccountType.OFFLINE) {
                if (context == null)
                    return HttpAttr.ERROR_MSG
                return DatabaseHandler.execOfflineAPI(context, api, data, id)
            }
            // */

            return JsonParser.parseString(HttpAsyncTask().execute(remote, method.method, json.toString()).get()).asJsonObject
        }

        fun isUserExists(type: AccountType, uid: String): Boolean {
            // TODO("Check whether the user is registered in server")
            val jAuth = JsonObject()
            jAuth.addProperty("type", type.type)
            jAuth.addProperty("uid", uid)

            return true
        }
    }
}