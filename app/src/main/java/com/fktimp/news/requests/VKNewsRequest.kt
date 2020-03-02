package com.fktimp.news.requests

import com.fktimp.news.models.VKNewsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKNewsRequest(sources: String, count: Int, start_from: String) :
    VKRequest<VKNewsModel>("newsfeed.get") {
    init {
        addParam("filters", "post")
        addParam("max_photos", 0)
        addParam("source_ids", sources)
        addParam("count", count)
        addParam("start_from", start_from)
    }

    override fun parse(r: JSONObject): VKNewsModel = Gson().fromJson(
        r.getJSONObject("response").toString(),
        object : TypeToken<VKNewsModel?>() {}.type
    )
}