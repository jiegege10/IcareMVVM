package com.icare.mvvm.util

import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.icare.mvvm.ext.setSingleClickListener
import com.icare.mvvm.ext.view.textString

object CustomBindAdapter {

    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.textString().length)
    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: Any) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .into(view)
    }


    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }


    @BindingAdapter(value = ["afterTextChanged"])
    @JvmStatic
    fun EditText.afterTextChanged(action: (String) -> Unit) {
        doAfterTextChanged {
            action(it.toString())
        }

    }

    @BindingAdapter(value = ["noRepeatClick"])
    @JvmStatic
    fun setOnClick(view: View, clickListener: () -> Unit) {
        view.setSingleClickListener {
            clickListener.invoke()
        }

    }


}