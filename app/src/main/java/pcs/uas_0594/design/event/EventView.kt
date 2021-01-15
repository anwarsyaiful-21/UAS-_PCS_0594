package pcs.uas_0594.design.event

import pcs.uas_0594.data.model.Event

interface EventView{
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}