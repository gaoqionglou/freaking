package com.kotlin.freak_ec.main.search

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.fastjson.JSON
import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration
import com.joanzapata.iconify.widget.IconTextView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.util.storage.FreakPreference
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2


class SearchDelegate : FreakDelegate() {

    @BindView(R2.id.rv_search)
    @JvmField
    var mRecyclerView: RecyclerView? = null


    @BindView(R2.id.et_search_view)
    @JvmField
    var etSearchView: AppCompatEditText? = null

    @BindView(R2.id.tv_top_search)
    @JvmField
    var tvSearch: AppCompatTextView? = null

    @BindView(R2.id.icon_top_search_back)
    @JvmField
    var iconBack: IconTextView? = null

    var mAdapter: SearchAdapter? = null

    @OnClick(R2.id.tv_top_search)
    fun onClickSearch() {
        val searchItemText = etSearchView?.text.toString().trim()
        saveItem(searchItemText)
        etSearchView?.setText("")
    }

    @OnClick(R2.id.icon_top_search_back)
    fun onClickBack() {
        pop()

    }

    fun saveItem(searchItemText: String) {
        if (searchItemText.isNotEmpty()) {
            val history = ArrayList<String>()
            val historyStr =
                FreakPreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY) ?: ""

            history.add(searchItemText)
            val json = JSON.toJSONString(history);
            FreakPreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json)
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_search
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val manager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = manager

        val data = SearchDataConverter().convert()
        mAdapter = SearchAdapter(data)
        mRecyclerView?.adapter = mAdapter

        val itemDecoration = DividerItemDecoration()
        itemDecoration.setDividerLookup(object : DividerItemDecoration.DividerLookup {
            override fun getVerticalDivider(position: Int): Divider? {
                return null
            }

            override fun getHorizontalDivider(position: Int): Divider {
                return Divider.Builder().size(2).margin(20, 20).color(Color.GRAY).build()
            }

        })
    }

}