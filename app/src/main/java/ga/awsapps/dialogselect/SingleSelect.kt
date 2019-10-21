package ga.awsapps.dialogselect

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView
import java.lang.Exception

class SingleSelect : AppCompatTextView , DialogInterface.OnClickListener {


    private var listener: OnMultipleItemsSelectedListener? = null
    internal var items: Array<String> = arrayOf()
    internal var mSelection : String? = null
    internal var mSelectionIndex : Int = -1
    internal var mSelectionIndexOld :Int  =-1;


    interface OnMultipleItemsSelectedListener {
        fun selectedIndex(indice: Int?)
        fun selectedString(string: String)
    }

    fun setListener(listener: OnMultipleItemsSelectedListener) {
        this.listener = listener
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {


        if ( mSelectionIndex == p1) run {

            (p0 as AlertDialog).listView.setItemChecked(p1, false)
            mSelectionIndex = -1

        }
        else{

            mSelectionIndex = p1
        }



    }

    override fun performClick(): Boolean {
        super.performClick()


        val builder = AlertDialog.Builder(context)
        builder.setTitle("Please select!")
            //.setMessage("You can buy our products without registration too. Enjoy the shopping")

            .setSingleChoiceItems(items,mSelectionIndex,this@SingleSelect)
            .setPositiveButton("Go") { dialog, which ->

                mSelectionIndexOld = mSelectionIndex
                try {
                    listener?.selectedIndex(mSelectionIndex)
                    setText(items[mSelectionIndex])
                }catch (e:Exception) { setText(context.getString(R.string.None)) }

                dialog.dismiss()
            }
            .setNegativeButton("no",{dialog,which->
                listener?.selectedIndex(mSelectionIndexOld)
                dialog.dismiss()
            })
        builder.create().show()

        return true
    }

    constructor(context: Context) : super(
        context,
        null,
        android.R.style.Widget_Material_DropDownItem_Spinner
    )

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs,
        android.R.style.Widget_Material_Spinner
    )


    fun setItems(items: Array<String>) {
        this.items = items
        setText(this.items[0])
    }

    fun setItems(items: List<String>) {
        this.items = items.toTypedArray()

    }

    fun setSelection(selection: String) {
        mSelectionIndex = items.indexOf(selection)
    }

    fun getSelectedStrings():String? = mSelection;



}
