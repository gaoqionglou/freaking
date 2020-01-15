package com.kotlin.freak_ec.main.personal.list

import android.widget.CompoundButton
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.kotlin.freak_core.delegates.FreakDelegate

class ListBean(
    override val itemType: Int,
    var imageUrl: String? = null,
    var text: String? = null,
    var value: String? = null,
    var id: Int = 0,
    var delegate: FreakDelegate? = null,
    var onCheckedChangeListener: CompoundButton.OnCheckedChangeListener? = null

) : MultiItemEntity {


    class Builder {
        private var itemType: Int = 0
        private var imageUrl: String? = null
        private var text: String? = null
        private var value: String? = null
        private var id: Int = 0
        private var delegate: FreakDelegate? = null
        private var onCheckedChangeListener: CompoundButton.OnCheckedChangeListener? = null


        fun setItemType(itemType: Int): Builder {
            this@Builder.itemType = itemType
            return this@Builder
        }


        fun setImageUrl(imageUrl: String): Builder {
            this@Builder.imageUrl = imageUrl
            return this@Builder
        }

        fun setText(text: String): Builder {
            this@Builder.text = text
            return this@Builder
        }

        fun setValue(value: String): Builder {
            this@Builder.value = value
            return this@Builder
        }

        fun setId(id: Int): Builder {
            this@Builder.id = id
            return this@Builder
        }

        fun setDelegate(delegate: FreakDelegate): Builder {
            this@Builder.delegate = delegate
            return this@Builder
        }

        fun setOnCheckedChangeListener(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener): Builder {
            this@Builder.onCheckedChangeListener = onCheckedChangeListener
            return this@Builder
        }


        fun build(): ListBean {
            return ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener)
        }

    }


}