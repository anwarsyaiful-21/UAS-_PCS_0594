package pcs.uas_0594.design.detail

import com.google.gson.Gson
import pcs.uas_0594.data.api.ApiRepository
import pcs.uas_0594.data.api.TheSportDBApi
import pcs.uas_0594.data.model.EventDetailResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pcs.uas_0594.design.detail.EventDetailView

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){

    fun getEventDetail(eventId: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventDetail(eventId)),
                    EventDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventDetail(data.events)
            }
        }
    }
}