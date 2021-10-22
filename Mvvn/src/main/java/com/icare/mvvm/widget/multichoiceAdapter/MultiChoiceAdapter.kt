package com.icare.mvvm.widget.multichoiceAdapter

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import java.util.*

//TODO 多选
abstract class MultiChoiceAdapter<VH : RecyclerView.ViewHolder?> : RecyclerView.Adapter<VH>(),
    MultiChoiceToolbar.Listener {
    var mIsInMultiChoiceMode = false
    var mIsInSingleClickMode = false
    private var mItemList: MutableMap<Int, State> = LinkedHashMap()
    private var mListener: Listener? = null
    private var mMultiChoiceToolbarHelper: MultiChoiceToolbarHelper? = null
    private var mRecyclerView: RecyclerView? = null
    /**
     * Override this method to customize the active item
     *
     * @param view  the view to customize
     * @param state true if the state is active/selected
     */
    open fun setActive(@NonNull view: View, state: Boolean) {
        if (state) {
            view.alpha = SELECTED_ALPHA
        } else {
            view.alpha = DESELECTED_ALPHA
        }
    }

    /**
     * Provide the default behaviour for the item click with multi choice mode disabled
     *
     * @return the onClick action to perform when multi choice selection is off
     */
    protected fun defaultItemViewClickListener(holder: VH, position: Int): View.OnClickListener? {
        return null
    }

    protected fun isSelectableInMultiChoiceMode(position: Int): Boolean {
        return true
    }

    /**
     * Deselect all the selected items in the adapter
     */
    fun deselectAll() {
        performAll(Action.DESELECT)
    }

    /**
     * Select all the view in the adapter
     */
    fun selectAll() {
        performAll(Action.SELECT)
    }

    /**
     * Select an item from the adapter position
     *
     * @param position adapter position of ther view which will be selected
     * @return True if the view has been selected, False if the view is already selected or is not part of the item list
     */
    fun select(position: Int): Boolean {
        if (mItemList.containsKey(position) && mItemList[position] == State.INACTIVE) {
            perform(Action.SELECT, position, true, true)
            return true
        }
        return false
    }

    /**
     * Deselect an item from the adapter position
     *
     * @param position adapter position of the view which will be deselected
     * @return True if the view has been deselected, False if the view is already deselected or is not part of the item list
     */
    fun deselect(position: Int): Boolean {
        if (mItemList.containsKey(position) && mItemList[position] == State.ACTIVE) {
            perform(Action.DESELECT, position, true, true)
            return true
        }
        return false
    }

    /**
     * Set the selection of the RecyclerView to always single click (instead of first long click and then single click)
     *
     * @param set true if single click sctivated
     */
    fun setSingleClickMode(set: Boolean) {
        mIsInSingleClickMode = set
        processNotifyDataSetChanged()
    }

    /**
     * Method to get the number of selected items
     *
     * @return number of selected items
     */
    fun getSelectedItemCount(): Int {
        return getSelectedItemListInternal().size
    }

    /**
     * Get the list of selected item
     *
     * @return Collection of all the selected position in the adapter
     */
    fun getSelectedItemList(): List<Int> {
        return getSelectedItemListInternal()
    }

    fun setMultiChoiceSelectionListener(listener: Listener?) {
        mListener = listener
    }

    fun setMultiChoiceToolbar(multiChoiceToolbar: MultiChoiceToolbar) {
        multiChoiceToolbar.setToolbarListener(this)
        mMultiChoiceToolbarHelper = MultiChoiceToolbarHelper(multiChoiceToolbar)
    }

    /**
     * @return true if the single click mode is active
     */
    fun isInSingleClickMode(): Boolean {
        return mIsInSingleClickMode
    }

    fun onSaveInstanceState(@Nullable savedInstanceState: Bundle?) {
        savedInstanceState?.putSerializable(EXTRA_ITEM_LIST, mItemList as Serializable)
    }


    //region Private methods
    fun getSelectedItemListInternal(): List<Int> {
        val selectedList: MutableList<Int> = ArrayList()
        for ((key, value) in mItemList) {
            if (value == State.ACTIVE) {
                selectedList.add(key)
            }
        }
        return selectedList
    }

    private fun processSingleClick(position: Int) {
        if (mIsInMultiChoiceMode || mIsInSingleClickMode) {
            processClick(position)
        }
    }

    private fun processLongClick(position: Int) {
        if (!mIsInMultiChoiceMode && !mIsInSingleClickMode) {
            processClick(position)
        }
    }

    private fun processUpdate(view: View, position: Int) {
        if (mItemList.containsKey(position)) {
            if (mItemList[position] == State.ACTIVE) {
                setActive(view, true)
            } else {
                setActive(view, false)
            }
        } else {
            mItemList[position] = State.INACTIVE
            processUpdate(view, position)
        }
    }

    private fun processClick(position: Int) {
        if (mItemList.containsKey(position)) {
            if (mItemList[position] == State.ACTIVE) {
                perform(Action.DESELECT, position, true, true)
            } else {
                perform(Action.SELECT, position, true, true)
            }
        }
    }

    /**
     * Remember to call this method before selecting or deselection something otherwise it won't vibrate
     */
    private fun performVibrate() {
        if (!mIsInMultiChoiceMode && !mIsInSingleClickMode && mRecyclerView != null) {
            if (ContextCompat.checkSelfPermission(
                    mRecyclerView!!.getContext(),
                    Manifest.permission.VIBRATE
                ) === PermissionChecker.PERMISSION_GRANTED
            ) {
                val v: Vibrator = mRecyclerView!!.getContext()
                    .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(10)
            }
        }
    }

    private fun perform(
        action: Action,
        position: Int,
        withCallback: Boolean,
        withVibration: Boolean
    ) {
        if (withVibration) {
            performVibrate()
        }
        if (action == Action.SELECT) {
            mItemList[position] =
                State.ACTIVE
        } else {
            mItemList[position] =
                State.INACTIVE
        }
        val selectedListSize = getSelectedItemListInternal().size
        updateToolbarIfNeeded(selectedListSize)
        updateMultiChoiceMode(selectedListSize)
        processNotifyDataSetChanged()
        if (mListener != null && withCallback) {
            if (action == Action.SELECT) {
                mListener!!.OnItemSelected(position, selectedListSize, mItemList.size)
            } else {
                mListener!!.OnItemDeselected(position, selectedListSize, mItemList.size)
            }
        }
    }

    private fun processNotifyDataSetChanged() {
        if (mRecyclerView != null) {
            notifyDataSetChanged()
        }
    }

    private fun updateToolbarIfNeeded(selectedListSize: Int) {
        if ((mIsInMultiChoiceMode || mIsInSingleClickMode || selectedListSize > 0) && mMultiChoiceToolbarHelper != null) {
            mMultiChoiceToolbarHelper!!.updateToolbar(selectedListSize)
        }
    }

    private fun updateMultiChoiceMode(selectedListSize: Int) {
        val somethingSelected = selectedListSize > 0
        if (mIsInMultiChoiceMode != somethingSelected) {
            mIsInMultiChoiceMode = somethingSelected
            processNotifyDataSetChanged()
        }
    }

    private fun performAll(action: Action) {
        performVibrate()
        val selectedItems: Int
        val state: State
        if (action == Action.SELECT) {
            selectedItems = mItemList.size
            state = State.ACTIVE
        } else {
            selectedItems = 0
            state = State.INACTIVE
        }
        for (i in mItemList.keys) {
            mItemList[i] = state
        }
        updateToolbarIfNeeded(selectedItems)
        updateMultiChoiceMode(selectedItems)
        processNotifyDataSetChanged()
        if (mListener != null) {
            if (action == Action.SELECT) {
                mListener!!.OnSelectAll(getSelectedItemListInternal().size, mItemList.size)
            } else {
                mListener!!.OnDeselectAll(getSelectedItemListInternal().size, mItemList.size)
            }
        }
    }

    override fun onClearButtonPressed() {
        performAll(Action.DESELECT)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        for (i in 0 until itemCount) {
            mItemList[i] =
                State.INACTIVE
        }
        super.onAttachedToRecyclerView(recyclerView)
    }


    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val mCurrentView: View = holder!!.itemView
        //
//        if ((mIsInMultiChoiceMode || mIsInSingleClickMode) && isSelectableInMultiChoiceMode(position)) {
//            mCurrentView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    processSingleClick(holder.getAdapterPosition()-1);
//                }
//            });
//        } else if (defaultItemViewClickListener(holder, position) != null) {
//            mCurrentView.setOnClickListener(defaultItemViewClickListener(holder, position-1));
//        }

//        mCurrentView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                processLongClick(holder.getAdapterPosition()-1);
//                return true;
//            }
//        });
        mCurrentView.setOnClickListener {
            //                    processLongClick(holder.getAdapterPosition()-1);
            processClick(holder.layoutPosition)
            //                    processSingleClick(holder.getAdapterPosition()-1);
        }
        processUpdate(mCurrentView, holder.layoutPosition)
    }

    //endregion
    //region Package-Protected methods
    @VisibleForTesting
    fun setItemList(itemList: LinkedHashMap<Int, State>) {
        mItemList = itemList
    }

    //endregion
    private enum class Action {
        SELECT, DESELECT
    }

    enum class State {
        ACTIVE, INACTIVE
    }

    interface Listener {
        fun OnItemSelected(selectedPosition: Int, itemSelectedCount: Int, allItemCount: Int)
        fun OnItemDeselected(deselectedPosition: Int, itemSelectedCount: Int, allItemCount: Int)
        fun OnSelectAll(itemSelectedCount: Int, allItemCount: Int)
        fun OnDeselectAll(itemSelectedCount: Int, allItemCount: Int)
    }

    companion object {
        private const val DESELECTED_ALPHA = 1f
        const val SELECTED_ALPHA = 0.25f
        private const val EXTRA_ITEM_LIST = "EXTRA_ITEM_LIST"
    }
}