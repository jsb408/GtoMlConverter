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
import com.goldouble.android.gtomlconverter.databinding.ActivityMainBinding
import com.goldouble.android.gtomlconverter.db.Recipe
import com.goldouble.android.gtomlconverter.db.RecipeDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val recipeDB by lazy { RecipeDB.getInstance(this) }
    private var recipeList = listOf<Recipe>()
    lateinit var mAdapter: RecipeAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            recipeList = recipeDB?.recipeDao()?.getAll()!!
            mAdapter = RecipeAdapter(recipeList)

            binding.recipeRecyclerView.adapter = mAdapter
            binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        }.start()

        MobileAds.initialize(this)
        //MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        val unitData: HashMap<String, Int> = hashMapOf()
        val itemList = resources.getStringArray(R.array.units)
        val unitList = resources.getIntArray(R.array.gpercup)
        for (i in resources.getStringArray(R.array.units).indices)
            unitData[itemList[i]] = unitList[i]

        itemList.sort()
        binding.itemSpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)

        binding.itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    if (binding.itemSpinner.getItemAtPosition(position).toString().substring(0, 2) == "계란" ||
                        binding.itemSpinner.getItemAtPosition(position).toString().substring(0, 3) == "egg")
                        binding.eggAlert.visibility = View.VISIBLE
                    else binding.eggAlert.visibility = View.GONE
                } catch(e: Exception) {
                    Log.d("MainActivity", e.toString())
                    binding.eggAlert.visibility = View.GONE
                }
                binding.convertedText.setText("0")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }

        binding.convertedText.addTextChangedListener {
            if (binding.convertedText.isFocused) {
                try {
                    val converted = it.toString().toDouble()
                    val gravity = unitData[binding.itemSpinner.selectedItem.toString()]!!.toDouble() / 200.0

                    Log.d("converted", converted.toString())
                    Log.d("gravity", gravity.toString())
                    Log.d("mL", (converted / gravity).toString())
                    binding.convertText.setText((converted / gravity).roundToInt().toString())
                } catch (e: Exception) {
                    Log.e("MainActivity", e.toString())
                    if (it!!.isEmpty()) binding.convertText.setText("")
                }
            }
        }

        binding.convertText.addTextChangedListener {
            if (binding.convertText.isFocused) {
                try {
                    val convert = it.toString().toDouble()
                    val gravity = unitData[binding.itemSpinner.selectedItem.toString()]!!.toDouble() / 200.0

                    Log.d("convert", convert.toString())
                    Log.d("gravity", gravity.toString())
                    Log.d("g", (convert * gravity).toString())
                    binding.convertedText.setText((convert * gravity).roundToInt().toString())
                } catch (e: Exception) {
                    Log.e("MainActivity", e.toString())
                    if (it!!.isEmpty()) binding.convertedText.setText("")
                }
            }
        }

        binding.addBtn.setOnClickListener {
            try {
                val converted = binding.convertedText.text.toString().toInt()
                val convert = binding.convertText.text.toString().toInt()

                val addRunnable = Runnable {
                    val newRecipe = Recipe(
                        null,
                        binding.itemSpinner.selectedItem.toString(),
                        converted,
                        convert)
                    recipeDB?.recipeDao()?.insert(newRecipe)
                    mAdapter.recipes = recipeDB?.recipeDao()?.getAll()!!
                }

                val addThread = Thread(addRunnable)
                addThread.start()

                Toast.makeText(this, binding.itemSpinner.selectedItem.toString() + getString(R.string.add_msg), Toast.LENGTH_SHORT).show()
                Log.d("LIST", recipeList.toString())
                mAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.err_alert), Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", e.toString())
            }
        }
    }
}