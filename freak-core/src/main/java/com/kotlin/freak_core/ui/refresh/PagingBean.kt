package com.kotlin.freak_core.ui.refresh

class PagingBean {
    //当前是第几页
    var mPageIndex = 0
    //总数据条数
    var mTotal = 0
    //一页显示几条
    var mPageSzie = 0
    //当前显示几条数据
    var mCurrentCount = 0
    //加载延迟
    var mDelayed = 0

    fun setPageIndex(pagingInt: Int): PagingBean {
        this.mPageIndex = pagingInt
        return this
    }

    fun setTotal(total: Int): PagingBean {
        this.mTotal = total
        return this
    }

    fun setPageSzie(pageSize: Int): PagingBean {
        this.mPageSzie = pageSize
        return this
    }

    fun setCurrentCount(currentCount: Int): PagingBean {
        this.mCurrentCount = currentCount
        return this
    }

    fun setDelayed(delayed: Int): PagingBean {
        this.mDelayed = delayed
        return this
    }

    fun addIndex(): PagingBean {
        mPageIndex++
        return this
    }

}