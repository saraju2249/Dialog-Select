package ga.awsapps.dialogselect

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView

import java.util.Arrays
import java.util.LinkedList

class MultiSelect : AppCompatTextView, DialogInterface.OnMultiChoiceClickListener {
    private var listener: OnMultipleItemsSelectedListener? = null

    internal var items: Array<String>? = null
    lateinit var mSelection: BooleanArray
    lateinit var mSelectionOld: BooleanArray
    internal var itemsOld: String? = null
    lateinit var c: Context

    val selectedStrings: List<String>
        get() {
            val selection = LinkedList<String>()
            for (i in items!!.indices) {
                if (mSelection[i]) {
                    selection.add(items!![i])
                }
            }
            return selection
        }

    val selectedIndices: List<Int>
        get() {
            val selection = LinkedList<Int>()
            for (i in items!!.indices) {
                if (mSelection[i]) {
                    selection.add(i)
                }
            }
            return selection
        }

    val selectedItemsAsString: String
        get() {
            val sb = StringBuilder()
            var foundOne = false

            for (i in items!!.indices) {
                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ")
                    }
                    foundOne = true
                    sb.append(items!![i])
                }
            }
            return sb.toString()
        }

    interface OnMultipleItemsSelectedListener {
        fun selectedIndices(indices: List<Int>)
        fun selectedStrings(strings: List<String>)
    }

    constructor(context: Context) : super(context) {
        c = context

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        //super.setAdapter(simple_adapter)
    }

    fun setListener(listener: OnMultipleItemsSelectedListener) {
        this.listener = listener
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if ( which < mSelection.size) {
            mSelection[which] = isChecked
            setText(buildSelectedItemString())
        } else {
            throw IllegalArgumentException(
                "Argument 'which' is out of bounds."
            )
        }
    }

    override fun performClick(): Boolean {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Please select!")
        builder.setMultiChoiceItems(items, mSelection, this)
        itemsOld = selectedItemsAsString
        //        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                setSelection(0);
        //            }
        //        });
        builder.setPositiveButton("Submit") { dialog, which ->
            System.arraycopy(mSelection!!, 0, mSelectionOld!!, 0, mSelection!!.size)
            listener!!.selectedIndices(selectedIndices)
            listener!!.selectedStrings(selectedStrings)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->

            System.arraycopy(mSelectionOld!!, 0, mSelection!!, 0, mSelectionOld!!.size)
        }
        builder.show()
        return true
    }


    fun setItems(items: Array<String>) {
        this.items = items
        mSelection = BooleanArray(this.items!!.size)
        mSelectionOld = BooleanArray(this.items!!.size)

        Arrays.fill(mSelection, false)
        mSelection[0] = true
        mSelectionOld[0] = true

    }

    fun setItems(items: List<String>) {
        this.items = items.toTypedArray()
        mSelection = BooleanArray(this.items!!.size)
        mSelectionOld = BooleanArray(this.items!!.size)

        Arrays.fill(mSelection, false)
        mSelection[0] = true
    }

    fun setSelection(selection: Array<String>) {
        for (i in mSelection.indices) {
            mSelection[i] = false
            mSelectionOld[i] = false
        }
        for (cell in selection) {
            for (j in items!!.indices) {
                if (items!![j] == cell) {
                    mSelection[j] = true
                    mSelectionOld[j] = true
                }
            }
        }
        setText(buildSelectedItemString())
    }

    fun setSelection(selection: List<String>) {
        for (i in mSelection.indices) {
            mSelection[i] = false
            mSelectionOld[i] = false
        }
        for (sel in selection) {
            for (j in items!!.indices) {
                if (items!![j] == sel) {
                    mSelection[j] = true
                    mSelectionOld[j] = true
                }
            }
        }
        setText(buildSelectedItemString())
    }


    fun setSelection(selectedIndices: IntArray) {
        for (i in mSelection.indices) {
            mSelection[i] = false
            mSelectionOld[i] = false
        }
        for (index in selectedIndices) {
            if (index >= 0 && index < mSelection!!.size) {
                mSelection[index] = true
                mSelectionOld[index] = true
            } else {
                throw IllegalArgumentException(
                    "Index " + index
                            + " is out of bounds."
                )
            }
        }

        setText(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): String {
        val sb = StringBuilder()
        var foundOne = false

        for (i in items!!.indices) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true

                sb.append(items!![i])
            }
        }
        return sb.toString()
    }

}