package com.goldouble.android.gtomlconverter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldouble.android.gtomlconverter.db.Recipe
import com.goldouble.android.gtomlconverter.db.RecipeDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val recipeDB by lazy { RecipeDB.getInstance(this) }
    private var recipeList = listOf<Recipe>()
    lateinit var mAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            recipeList = recipeDB?.recipeDao()?.getAll()!!
            mAdapter = RecipeAdapter(recipeList)

            recipeRecyclerView.adapter = mAdapter
            recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        }.start()

        MobileAds.initialize(this)
        //MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val unitData: HashMap<String, Int> = hashMapOf()
        val itemList = resources.getStringArray(R.array.units)
        val unitList = resources.getIntArray(R.array.gpercup)
        for (i in resources.getStringArray(R.array.units).indices)
            unitData[itemList[i]] = unitList[i]

        itemList.sort()
        itemSpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)

        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    if (itemSpinner.getItemAtPosition(position).toString().substring(0, 2) == "계란" ||
                        itemSpinner.getItemAtPosition(position).toString().substring(0, 3) == "egg")
                        eggAlert.visibility = View.VISIBLE
                    else eggAlert.visibility = View.GONE
                } catch(e: Exception) {
                    Log.d("MainActivity", e.toString())
                    eggAlert.visibility = View.GONE
                }
                convertedText.setText("0")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }

        convertedText.addTextChangedListener {
            if (convertedText.isFocused) {
                try {
                    val converted = it.toString().toDouble()
                    val gravity = unitData[itemSpinner.selectedItem.toString()]!!.toDouble() / 200.0

                    Log.d("converted", converted.toString())
                    Log.d("gravity", gravity.toString())
                    Log.d("mL", (converted / gravity).toString())
                    convertText.setText((converted / gravity).roundToInt().toString())
                } catch (e: Exception) {
                    Log.e("MainActivity", e.toString())
                    if (it!!.isEmpty()) convertText.setText("")
                }
            }
        }

        convertText.addTextChangedListener {
            if (convertText.isFocused) {
                try {
                    val convert = it.toString().toDouble()
                    val gravity = unitData[itemSpinner.selectedItem.toString()]!!.toDouble() / 200.0

                    Log.d("convert", convert.toString())
                    Log.d("gravity", gravity.toString())
                    Log.d("g", (convert * gravity).toString())
                    convertedText.setText((convert * gravity).roundToInt().toString())
                } catch (e: Exception) {
                    Log.e("MainActivity", e.toString())
                    if (it!!.isEmpty()) convertedText.setText("")
                }
            }
        }

        addBtn.setOnClickListener {
            try {
                val converted = convertedText.text.toString().toInt()
                val convert = convertText.text.toString().toInt()

                val addRunnable = Runnable {
                    val newRecipe = Recipe(
                        null,
                        itemSpinner.selectedItem.toString(),
                        converted,
                        convert)
                    recipeDB?.recipeDao()?.insert(newRecipe)
                    mAdapter.recipes = recipeDB?.recipeDao()?.getAll()!!
                }

                val addThread = Thread(addRunnable)
                addThread.start()

                Toast.makeText(this, itemSpinner.selectedItem.toString() + getString(R.string.add_msg), Toast.LENGTH_SHORT).show()
                Log.d("LIST", recipeList.toString())
                mAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.err_alert), Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", e.toString())
            }
        }
    }
}