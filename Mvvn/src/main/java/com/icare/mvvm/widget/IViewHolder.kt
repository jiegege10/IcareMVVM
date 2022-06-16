/**
 *
 * Copyright 2017 Harish Sridharan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.icare.mvvm.widget

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icare.mvvm.R


class IViewHolder(inflater: LayoutInflater, parent: ViewGroup, innerViewResId: Int) : RecyclerView.ViewHolder(inflater.inflate(
    R.layout.viewholder_shimmer, parent, false)) {

    private val mILayout: ILayout = itemView as ILayout

    init {
        inflater.inflate(innerViewResId, mILayout, true)
    }

    fun setShimmerAngle(angle: Int) {
        mILayout.setShimmerAngle(angle)
    }

    fun setShimmerColor(color: Int) {
        mILayout.setShimmerColor(color)
    }

    fun setShimmerMaskWidth(maskWidth: Float) {
        mILayout.setMaskWidth(maskWidth)
    }

    fun setShimmerViewHolderBackground(viewHolderBackground: Drawable?) {
        if (viewHolderBackground != null) {
            setBackground(viewHolderBackground)
        }
    }

    fun setShimmerAnimationDuration(duration: Int) {
        mILayout.setShimmerAnimationDuration(duration)
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        mILayout.setAnimationReversed(animationReversed)
    }

    fun bind() {
        mILayout.startShimmerAnimation()
    }

    private fun setBackground(background: Drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            mILayout.background = background
        } else {
            mILayout.setBackgroundDrawable(background)
        }
    }
}
