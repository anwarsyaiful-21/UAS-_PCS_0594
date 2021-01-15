package pcs.uas_0594.design.favorite

import pcs.uas_0594.data.db.entities.EventFavorite

interface ClickListener {
    fun onClickListener(eventFavorite: EventFavorite)
}