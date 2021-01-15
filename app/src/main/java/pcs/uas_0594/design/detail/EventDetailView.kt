package pcs.uas_0594.design.detail


import pcs.uas_0594.data.model.EventDetail

interface EventDetailView{
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<EventDetail>)
}