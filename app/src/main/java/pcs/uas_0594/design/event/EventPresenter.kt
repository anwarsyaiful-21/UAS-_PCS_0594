package pcs.uas_0594.design.event

import com.google.gson.Gson
import pcs.uas_0594.data.api.ApiRepository
import pcs.uas_0594.data.api.TheSportDBApi
import pcs.uas_0594.data.model.EventResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pcs.uas_0594.design.event.EventView

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson){
    fun getEventList(league: String?, event: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEvent(league, event)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(data.events)
            }
        }
    }
}
