package com.icare.tianxiangyuan.weight.multichoiceAdapter

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.PluralsRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.icare.mvvm.R
import com.icare.mvvm.widget.multichoiceAdapter.Constants
import com.icare.mvvm.widget.multichoiceAdapter.MultiChoiceToolbar
import java.util.*

internal class MultiChoiceToolbarHelper(private val mMultiChoiceToolbar: MultiChoiceToolbar) {
    private var mSupportActionBar: ActionBar?=null
    private var mAppCompatActivity: AppCompatActivity?=null
    @Throws(IllegalStateException::class)
    fun updateToolbar(itemNumber: Int) {
        if (itemNumber == ZERO) {
            showDefaultToolbar()
        } else if (itemNumber > ZERO) {
            showMultiChoiceToolbar()
        }
        updateToolbarTitle(itemNumber)
    }

    private fun showMultiChoiceToolbar() {
        mSupportActionBar!!.setHomeAsUpIndicator(R.drawable.fragmentation_ic_stack)
        mSupportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val multi_primaryColor = mMultiChoiceToolbar.getMultiPrimaryColor()
        if (multi_primaryColor != 0) {
            mSupportActionBar!!.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        mAppCompatActivity!!,
                        multi_primaryColor
                    )
                )
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = mAppCompatActivity!!.getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val multiPrimaryColorDark = mMultiChoiceToolbar.getMultiPrimaryColorDark()
            if (multiPrimaryColorDark != 0) {
                window.statusBarColor =
                    ContextCompat.getColor(mAppCompatActivity!!, multiPrimaryColorDark)
            }
        }
        mMultiChoiceToolbar.getToolbar().setNavigationOnClickListener(View.OnClickListener {
            val listener = mMultiChoiceToolbar.getToolbarListener()
            listener?.onClearButtonPressed()
        })
    }

    private fun showDefaultToolbar() {
        showDefaultIcon()
        mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(mMultiChoiceToolbar.getDefaultPrimaryColor()))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = mAppCompatActivity!!.getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = mMultiChoiceToolbar.getDefaultPrimaryColorDark()
        }
    }

    private fun showDefaultIcon() {
        val icon = mMultiChoiceToolbar.getIcon()
        if (icon != 0) {
            mSupportActionBar!!.setHomeAsUpIndicator(icon)
            mSupportActionBar!!.setDisplayHomeAsUpEnabled(true)
            mMultiChoiceToolbar.getToolbar().setNavigationOnClickListener(mMultiChoiceToolbar.getIconAction())
        } else {
            mSupportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun updateToolbarTitle(itemNumber: Int) {
        if (itemNumber > 0) {
            @PluralsRes val selectedToolbarQuantityTitle =
                mMultiChoiceToolbar.getSelectedToolbarQuantityTitle()
            val selectedToolbarTitle = mMultiChoiceToolbar.getSelectedToolbarTitle()

            // if set, prefer the quantity-res over the given string or just use the number of selected items
            if (selectedToolbarQuantityTitle != 0 && selectedToolbarQuantityTitle != Constants.INVALID_RES) {
                mSupportActionBar!!.title = mAppCompatActivity!!.resources
                    .getQuantityString(selectedToolbarQuantityTitle, itemNumber, itemNumber)
            } else if (selectedToolbarTitle != null) {
                mSupportActionBar!!.title = String.format(
                    Locale.UK,
                    SELECTED_TOOLBAR_TITLE_FORMAT,
                    itemNumber,
                    selectedToolbarTitle
                )
            } else {
                mSupportActionBar!!.title = itemNumber.toString()
            }
        } else {
            val defaultToolbarTitle = mMultiChoiceToolbar.getDefaultToolbarTitle()
            if (defaultToolbarTitle != null) {
                mSupportActionBar!!.title = defaultToolbarTitle
            } else {
                mSupportActionBar!!.title = mAppCompatActivity!!.getTitle()
            }
        }
    }

    companion object {
        private const val TOOLBAR_ERROR_MESSAGE =
            "Toolbar not implemented via getSupportActionBar method"
        private const val SELECTED_TOOLBAR_TITLE_FORMAT = "%d %s"
        private const val ZERO = 0
    }

    init {
        mAppCompatActivity = mMultiChoiceToolbar.getAppCompatActivity()
        mSupportActionBar = mAppCompatActivity!!.getSupportActionBar()
        checkNotNull(mSupportActionBar) { TOOLBAR_ERROR_MESSAGE }
    }
}