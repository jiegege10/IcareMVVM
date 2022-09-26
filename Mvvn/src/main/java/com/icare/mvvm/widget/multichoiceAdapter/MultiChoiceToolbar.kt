package com.icare.mvvm.widget.multichoiceAdapter

import android.content.res.TypedArray
import android.util.TypedValue
import android.view.View
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.icare.mvvm.R

class MultiChoiceToolbar private constructor(builder: Builder) {
    private val mAppCompatActivity: AppCompatActivity
    private val mToolbar: Toolbar
    private val mDefaultToolbarTitle: String

    @PluralsRes
    private val mSelectedQuantityTitle: Int
    private val mSelectedToolbarTitle: String
    private var mDefaultPrimaryColor = 0
    private var mDefaultPrimaryColorDark = 0
    private var mMultiPrimaryColor = 0
    private var mMultiPrimaryColorDark = 0
    private val mIcon: Int
    private val mIconAction: View.OnClickListener?
    private var mListener: Listener? = null
    fun setToolbarListener(listener: Listener?) {
        mListener = listener
    }

    /**
     * Builder class for MultiChoiceToolbar
     */
    class Builder(
        appCompatActivity: AppCompatActivity,
        toolbar: Toolbar
    ) {
        val mAppCompatActivity: AppCompatActivity
        val mToolbar: Toolbar
        var mDefaultToolbarTitle = ""

        @PluralsRes
        var mSelectedQuantityTitle = Constants.INVALID_RES
        var mSelectedToolbarTitle = ""

        // Colours
        var mDefaultPrimaryColor = 0
        var mDefaultPrimaryColorDark = 0
        var mMultiPrimaryColor = 0
        var mMultiPrimaryColorDark = 0
        var mIcon = 0
        var mIconAction: View.OnClickListener? = null

        /**
         * Set the colours when the toolbar is the default one
         *
         * @param defaultPrimaryColor     The color used for the background of the toolbar when is in default mode
         * @param defaultPrimaryColorDark The color used for the status bar background when is in default mode
         * @return Builder so you can chain together setters and build
         */
        fun setDefaultColours(defaultPrimaryColor: Int, defaultPrimaryColorDark: Int): Builder {
            mDefaultPrimaryColor = defaultPrimaryColor
            mDefaultPrimaryColorDark = defaultPrimaryColorDark
            return this
        }

        /**
         * Set the colours when the toolbar is the multi choice one
         *
         * @param multiPrimaryColor     The color used for the background of the toolbar when is in multi choice mode
         * @param multiPrimaryColorDark The color used for the status bar background when is in multi choice mode
         * @return Builder so you can chain together setters and build
         */
        fun setMultiChoiceColours(multiPrimaryColor: Int, multiPrimaryColorDark: Int): Builder {
            mMultiPrimaryColor = multiPrimaryColor
            mMultiPrimaryColorDark = multiPrimaryColorDark
            return this
        }

        /**
         * Set the titles for the differente state of the toolbar default/multiChoice
         *
         * @param defaultTitle  Title when the toolbar shown is the default one
         * @param selectedTitle Title shown when some item are selected. Will be of the format "itemCount title" where "itemCount" is the number of
         * selected item and "title" is this param
         * @return Builder so you can chain together setters and build
         */
        fun setTitles(defaultTitle: String, selectedTitle: String): Builder {
            mDefaultToolbarTitle = defaultTitle
            mSelectedToolbarTitle = selectedTitle
            return this
        }

        /**
         * Set the titles for the different states of the toolbar default/multiChoice
         *
         * @param defaultTitle          Title when the toolbar shown is the default one
         * @param selectedQuantityTitle Title shown when some item are selected. Will use the plural-feature to let you define the correct format.
         * @return Builder so you can chain together setters and build
         */
        fun setTitles(
            @StringRes defaultTitle: Int,
            @PluralsRes selectedQuantityTitle: Int
        ): Builder {
            mDefaultToolbarTitle = mAppCompatActivity.getString(defaultTitle)
            mSelectedQuantityTitle = selectedQuantityTitle
            return this
        }

        /**
         * Set the titles for the different states of the toolbar default/multiChoice
         *
         * @param defaultTitle          Title when the toolbar shown is the default one
         * @param selectedQuantityTitle Title shown when some item are selected. Will use the plural-feature to let you define the correct format.
         * @return Builder so you can chain together setters and build
         */
        @Deprecated("use {@link #setTitles(int, int)} instead")
        fun setTitles(defaultTitle: String, @PluralsRes selectedQuantityTitle: Int): Builder {
            mDefaultToolbarTitle = defaultTitle
            mSelectedQuantityTitle = selectedQuantityTitle
            return this
        }

        /**
         * Set the default icon that will be shown when is not in multi choice mode.
         * If not set there will be no icon.
         *
         * @param icon   The default icon
         * @param action The default icon action
         * @return Builder so you can chain together setters and build
         */
        fun setDefaultIcon(icon: Int, action: View.OnClickListener?): Builder {
            mIcon = icon
            mIconAction = action
            return this
        }

        fun build(): MultiChoiceToolbar {
            if (mDefaultPrimaryColor == 0 || mDefaultPrimaryColorDark == 0) {
                mDefaultPrimaryColor = getDefaultColorFromContext(intArrayOf(R.attr.colorPrimary))
                mDefaultPrimaryColorDark =
                    getDefaultColorFromContext(intArrayOf(R.attr.colorPrimaryDark))
            }
            return MultiChoiceToolbar(this)
        }

        private fun getDefaultColorFromContext(colorRes: IntArray): Int {
            val typedValue = TypedValue()
            val a: TypedArray = mAppCompatActivity.obtainStyledAttributes(typedValue.data, colorRes)
            val color: Int = a.getColor(0, 0)
            a.recycle()
            return color
        }

        init {
            mAppCompatActivity = appCompatActivity
            mToolbar = toolbar
        }
    }

    /* Getters */
    fun getAppCompatActivity(): AppCompatActivity {
        return mAppCompatActivity
    }

    fun getToolbar(): Toolbar {
        return mToolbar
    }

    fun getDefaultToolbarTitle(): String {
        return mDefaultToolbarTitle
    }

    @PluralsRes
    fun getSelectedToolbarQuantityTitle(): Int {
        return mSelectedQuantityTitle
    }

    fun getSelectedToolbarTitle(): String {
        return mSelectedToolbarTitle
    }

    fun getDefaultPrimaryColor(): Int {
        return mDefaultPrimaryColor
    }

    fun getDefaultPrimaryColorDark(): Int {
        return mDefaultPrimaryColorDark
    }

    fun getMultiPrimaryColor(): Int {
        return mMultiPrimaryColor
    }

    fun getMultiPrimaryColorDark(): Int {
        return mMultiPrimaryColorDark
    }

    fun getIcon(): Int {
        return mIcon
    }

    fun getIconAction(): View.OnClickListener? {
        return mIconAction
    }

    fun getToolbarListener(): Listener? {
        return mListener
    }

    /*
            * Listener
            * */
    interface Listener {
        fun onClearButtonPressed()
    }

    init {
        mAppCompatActivity = builder.mAppCompatActivity
        mToolbar = builder.mToolbar
        mDefaultToolbarTitle = builder.mDefaultToolbarTitle.trim { it <= ' ' }
        mSelectedQuantityTitle = builder.mSelectedQuantityTitle
        mSelectedToolbarTitle = builder.mSelectedToolbarTitle.trim { it <= ' ' }
        mDefaultPrimaryColor = builder.mDefaultPrimaryColor
        mDefaultPrimaryColorDark = builder.mDefaultPrimaryColorDark
        mMultiPrimaryColor = builder.mMultiPrimaryColor
        mMultiPrimaryColorDark = builder.mMultiPrimaryColorDark
        mIcon = builder.mIcon
        mIconAction = builder.mIconAction
    }
}