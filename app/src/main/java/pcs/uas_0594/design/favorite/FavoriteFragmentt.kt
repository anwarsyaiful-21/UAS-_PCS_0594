package pcs.uas_0594.design.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pcs.uas_0594.RunIn.Companion.db
import pcs.uas_0594.R
import pcs.uas_0594.design.detail.EventDetailActivity
import org.jetbrains.anko.support.v4.startActivity

class FavoriteFragmentt : Fragment() {

    private lateinit var recyclerView: RecyclerView
    val fav = db.getFavoriteDao().getAll()
    private lateinit var adapter: FavoriteAdapter
    lateinit var del: FloatingActionButton;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite_fragmentt, container, false)
        recyclerView = view.findViewById(R.id.listFavorite)
        del = view.findViewById(R.id.fbDel)

        adapter = FavoriteAdapter(requireContext(), fav) {
            startActivity<EventDetailActivity>(
                "id" to "${it.eventId}",
                "idhome" to "${it.idHome}",
                "idaway" to "${it.idAway}"
            )
            Log.d("test",it.eventId.toString()+ "||" +  it.idHome.toString() + "||" +  it.idAway.toString())
        }
        recyclerView.adapter = adapter

        del.setOnClickListener {
            db.getFavoriteDao().deleteAll()
        }

        return view
    }
}