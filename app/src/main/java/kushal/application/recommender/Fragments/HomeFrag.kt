package kushal.application.recommender.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.view.*
import kushal.application.recommender.Adapters.CirclePagerIndicatorDecoration
import kushal.application.recommender.Adapters.Home_adapter
import kushal.application.recommender.R


class HomeFrag : Fragment() {

    var IS_DOWN = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val list = arrayOf(
            R.drawable.bg_blue,
            R.drawable.bg_green,
            R.drawable.bg_yellow,
            R.drawable.bg_blue
        )

        view.home_recView_2.adapter = Home_adapter(list, true)
        val llm2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.home_recView_2.layoutManager = llm2

        view.home_recView.adapter = Home_adapter(list, false)
        view.home_recView.smoothScrollToPosition(3)
        val llm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.home_recView.layoutManager = llm
        view.home_recView.addItemDecoration(CirclePagerIndicatorDecoration())

        return view
    }

}
