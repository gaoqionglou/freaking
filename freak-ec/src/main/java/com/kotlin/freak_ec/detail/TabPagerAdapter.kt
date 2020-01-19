package com.kotlin.freak_ec.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.fastjson.JSONObject


class TabPagerAdapter : FragmentStatePagerAdapter {

    constructor(fm: FragmentManager?, data: JSONObject) : super(fm) {
        //获取tab信息
        val tabs = data.getJSONArray("tabs")
        val size = tabs.size
        for (i in 0 until size) {
            val eachTab = tabs.getJSONObject(i)
            val name = eachTab.getString("name")
            val pictureUrls = eachTab.getJSONArray("pictures")
            val eachTabPicturesArray = ArrayList<String>()
            //存储每个图片
            val pictureSize = pictureUrls.size
            for (j in 0 until pictureSize) {
                eachTabPicturesArray.add(pictureUrls.getString(j))
            }
            TAB_TITLES.add(name)
            PICTURES.add(eachTabPicturesArray)
        }

    }

    private val TAB_TITLES = ArrayList<String>()
    private val PICTURES = ArrayList<ArrayList<String>>()

    override fun getItem(position: Int): Fragment? {
        if (position == 0) {
            return ImageDelegate.create(PICTURES[0])
        } else if (position == 1) {
            return ImageDelegate.create(PICTURES[1])
        }
        return null
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

}