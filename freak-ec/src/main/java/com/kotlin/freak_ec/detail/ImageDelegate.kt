package com.kotlin.freak_ec.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.kotlin.freak_core.delegates.FreakDelegate
import com.kotlin.freak_core.ui.recycler.ItemType
import com.kotlin.freak_core.ui.recycler.MultipleItemEntity
import com.kotlin.freak_core.ui.recycler.MutilpleFields
import com.kotlin.freak_ec.R
import com.kotlin.freak_ec.R2

class ImageDelegate : FreakDelegate() {

    @BindView(R2.id.rv_image_container)
    @JvmField
    var rvImage: RecyclerView? = null


    companion object {
        val ARG_PICTURES = "ARG_PICTURES"
        fun create(images: ArrayList<String>): ImageDelegate {
            val args = Bundle()
            args.putStringArrayList(ARG_PICTURES, images)
            val imageDelegate = ImageDelegate()
            imageDelegate.arguments = args
            return imageDelegate
        }
    }


    fun initImage() {
        val pics = arguments?.getStringArrayList(ARG_PICTURES)
        val entities = ArrayList<MultipleItemEntity>()
        if (!pics.isNullOrEmpty()) {
            val size = pics.size
            for (i in 0 until size) {
                val imageUrl = pics[i]
                val entity = MultipleItemEntity.builder()
                    .setItemType(ItemType.SINGLE_BIG_IMAGE)
                    .setField(MutilpleFields.IMAGE_URL, imageUrl)
                    .build()
                entities.add(entity)
            }
        }

        val imageAdapter = RecyclerImageAdapter(entities)
        rvImage?.adapter = imageAdapter
    }


    override fun setLayout(): Any {
        return R.layout.delegate_image
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        val manager = LinearLayoutManager(context)
        rvImage?.layoutManager = manager
        initImage()
    }

}