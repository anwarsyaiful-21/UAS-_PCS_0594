package pcs.uas_0594.data.api

import java.net.URL


class ApiRepository{

    fun doRequest(url: String): String{
        return URL(url).readText()
    }
}