package ga.awsapps.dialogselect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    lateinit var test: BooleanArray;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test = BooleanArray(5)
        test[0] = false

        val array = arrayOf("One", "Two", "Three", "Four", "Five", "Six")
        setSpinner1(array)
        setSpinner2(array)
    }


    fun setSpinner1(list: Array<String>) {

        val multiSelectSpinner = findViewById<View>(R.id.spinner1) as SingleSelect
        multiSelectSpinner.setItems(list)
        multiSelectSpinner.setSelection("One")
        multiSelectSpinner.setListener(object : SingleSelect.OnMultipleItemsSelectedListener {
            override fun selectedString(string: String) {

            }

            override fun selectedIndex(indice: Int?) {


            }


        })
    }


    fun setSpinner2(list: Array<String>) {

        val multiSelectSpinner = findViewById<View>(R.id.spinner2) as MultiSelect
        multiSelectSpinner.setItems(list)
        multiSelectSpinner.setSelection(intArrayOf(0))
        multiSelectSpinner.setListener(object : MultiSelect.OnMultipleItemsSelectedListener {
            override fun selectedStrings(strings: List<String>) {


            }

            override fun selectedIndices(indices: List<Int>) {

                indices?.forEach {

                }

            }


        })
    }
}
