package pcs.uas_0594.design.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import pcs.uas_0594.RunIn.Companion.db
import pcs.uas_0594.R
import pcs.uas_0594.data.api.ApiRepository
import pcs.uas_0594.data.db.entities.EventFavorite
import pcs.uas_0594.data.model.EventDetail
import pcs.uas_0594.util.invisible
import pcs.uas_0594.util.visible
import okhttp3.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.json.JSONObject
import pcs.uas_0594.design.detail.EventDetailView
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity(), EventDetailView {
    private lateinit var presenter: EventDetailPresenter
    private lateinit var idEvent: String
    private var idHome: String = ""
    private var idAway: String = ""
    private lateinit var progressBar: ProgressBar
    val client = OkHttpClient()

    lateinit var tv_skor_detail: TextView
    lateinit var tv_home_formation: TextView
    lateinit var tv_home_goals: TextView
    lateinit var tv_away_goals: TextView
    lateinit var tv_home_shots: TextView
    lateinit var tv_away_shots: TextView
    lateinit var tv_home_gk: TextView
    lateinit var tv_away_gk: TextView
    lateinit var tv_home_def: TextView
    lateinit var tv_away_def: TextView
    lateinit var tv_home_mid: TextView
    lateinit var tv_away_mid: TextView
    lateinit var tv_home_forward: TextView
    lateinit var tv_away_forward: TextView
    lateinit var tv_away_detail: TextView
    lateinit var tv_home_detail: TextView
    lateinit var tv_time_detail: TextView
    lateinit var tv_date_detail: TextView
    lateinit var tv_away_formation: TextView
    lateinit var img_home: ImageView
    lateinit var img_away: ImageView
    lateinit var fav: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
//        swipeRefresh = find(R.id.swipe_refresh_detail)

        img_away = find(R.id.img_away)
        img_home = find(R.id.img_home)
        tv_away_formation = find(R.id.tv_away_formation)
        tv_date_detail = find(R.id.tv_date_detail)
        tv_time_detail = find(R.id.tv_time_detail)
        tv_home_detail = find(R.id.tv_home_detail)
        tv_away_detail = find(R.id.tv_away_detail)
        tv_away_forward = find(R.id.tv_away_forward)
        tv_home_forward = find(R.id.tv_home_forward)
        tv_away_mid = find(R.id.tv_away_mid)
        tv_home_mid = find(R.id.tv_home_mid)
        tv_away_def = find(R.id.tv_away_def)
        tv_home_def = find(R.id.tv_home_def)
        tv_away_gk = find(R.id.tv_away_gk)
        tv_home_gk = find(R.id.tv_home_gk)
        tv_away_shots = find(R.id.tv_away_shots)
        tv_home_shots = find(R.id.tv_home_shots)
        tv_away_goals = find(R.id.tv_away_goals)
        tv_home_goals = find(R.id.tv_home_goals)
        tv_home_formation = find(R.id.tv_home_formation)
        tv_skor_detail = find(R.id.tv_skor_detail)
        fav = find(R.id.fbFav)

        val intent = intent
        idEvent = intent.getStringExtra("id").toString()
        idHome = intent.getStringExtra("idhome")!!
        idAway = intent.getStringExtra("idaway")!!

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(idEvent)
//        swipeRefresh.onRefresh {
//            presenter.getEventDetail(idEvent)
//        }
        val logo = arrayOf(idHome, idAway)
        getBadge(logo)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<EventDetail>) {

        fav.setOnClickListener {
            val data = data[0]
            db.getFavoriteDao().insert(
                EventFavorite(
                    data.eventId?.toInt(),
                    data.eventDate,
                    data.eventTime,
                    data.idHome,
                    data.teamHome,
                    data.scoreHome,
                    data.formationHome,
                    data.goalHome,
                    data.shotHome,
                    data.gkHome,
                    data.defHome,
                    data.midHome,
                    data.forwHome,

                    data.idAway,
                    data.teamAway,
                    data.scoreAway,
                    data.formationAway,
                    data.goalAway,
                    data.shotAway,
                    data.gkAway,
                    data.defAway,
                    data.midAway,
                    data.forwAway
                )
            )

        }

        Log.d("testDb", db.getFavoriteDao().getAll().toString())
//        swipeRefresh.isRefreshing = false
        val tanggal = SimpleDateFormat("EEE, d MMM yyyy")
                .format(toGMTFormat(data[0].eventDate, data[0].eventTime))
        val waktu = SimpleDateFormat("HH:mm")
                .format(toGMTFormat(data[0].eventDate, data[0].eventTime))
        tv_date_detail.text = tanggal
        tv_time_detail.text = waktu
        tv_home_detail.text = data[0].teamHome
        tv_away_detail.text = data[0].teamAway
        if(data[0].scoreHome.isNullOrEmpty() && data[0].scoreAway.isNullOrEmpty()){
            tv_skor_detail.text = "0 vs 0"
        }else{
            tv_skor_detail.text = data[0].scoreHome+"  vs  "+data[0].scoreAway
        }
        tv_home_formation.text = data[0].formationHome
        tv_away_formation.text = data[0].formationAway
        tv_home_goals.text = data[0].goalHome?.replace(";", "\n")
        tv_away_goals.text = data[0].goalAway?.replace(";", "\n")
        tv_home_shots.text = data[0].shotHome
        tv_away_shots.text = data[0].shotAway
        tv_home_gk.text = biarRapi(data[0].gkHome)
        tv_away_gk.text = biarRapi(data[0].gkAway)
        tv_home_def.text = biarRapi(data[0].defHome)
        tv_away_def.text = biarRapi(data[0].defAway)
        tv_home_mid.text = biarRapi(data[0].midHome)
        tv_away_mid.text = biarRapi(data[0].midAway)
        tv_home_forward.text = biarRapi(data[0].forwHome)
        tv_away_forward.text = biarRapi(data[0].forwAway)
    }

    private fun getBadge(logo: Array<String>) {
        for(i in 0..1){
            val request = Request.Builder()
                    .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                    .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var a = response.body()?.string()
                    runOnUiThread {
                        run(){
                            var json = JSONObject(a)
                            var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")
                            if(i == 0) {
                                Picasso.with(ctx).load(badge).into(img_home)
                            }else{
                                Picasso.with(ctx).load(badge).into(img_away)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun toGMTFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    //merapikan string (mengganti <semicolon> dengan <enter>)
    private fun biarRapi(pemain: String?): String? {
        return pemain?.replace("; ", "\n")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


