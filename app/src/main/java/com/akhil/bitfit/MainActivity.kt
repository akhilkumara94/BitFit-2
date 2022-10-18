package com.akhil.bitfit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

const val AVERAGE: String = "AVERAGE"
const val MINIMUM: String = "MINIMUM"
const val MAXIMUM: String = "MAXIMUM"

class MainActivity : AppCompatActivity(), DashboardFragment.OnClearListener {

    lateinit var logsFragment: LogsFragment
    lateinit var dashboardFragment: DashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logsFragment = LogsFragment()
        dashboardFragment = DashboardFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, logsFragment, "log_fragment").commit()

        val addFood = findViewById<Button>(R.id.addNewFoodButton)
        addFood.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ActivityBitfitInput::class.java)
            startActivityForResult(intent, 1)
        })

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logs_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, logsFragment, "log_fragment").commit()
                    true
                }
                R.id.dashboard_menu -> {
                    var (average, min, max) = calculateDashboardValues()
                    val bundle = Bundle()
                    bundle.putString(AVERAGE, average.toString())
                    bundle.putString(MINIMUM, min.toString())
                    bundle.putString(MAXIMUM, max.toString())
                    dashboardFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, dashboardFragment, "dashboard_fragment").commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun calculateDashboardValues(): Triple<Int, Int, Int> {
        var bitfitsCalories: ArrayList<Int> = ArrayList()
        for (bitfit in logsFragment.bitfits) {
            bitfit.calorieCount?.toInt()?.let { bitfitsCalories.add(it) }
        }

        if (bitfitsCalories.size == 0) {
            return Triple(0, 0, 0)
        }

        return Triple(
            bitfitsCalories.average().toInt(),
            bitfitsCalories.min(),
            bitfitsCalories.max()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val bitFitResult = data?.getSerializableExtra("result") as BitFit
                logsFragment.bitfits.add(bitFitResult)
                logsFragment.bitFitAdapter.notifyDataSetChanged()

                if (supportFragmentManager.findFragmentByTag("dashboard_fragment")?.isVisible == true) {
                    var (average, min, max) = calculateDashboardValues()

                    dashboardFragment.updateDashboard(
                        average.toString(),
                        min.toString(),
                        max.toString()
                    )
                }
                //data added to the DB
                lifecycleScope.launch(IO) {
                    (application as BitFitApplication).db.bitFitDao().insert(
                        BitFitEntity(
                            foodName = bitFitResult.foodName,
                            calorieCount = bitFitResult.calorieCount
                        )
                    )
                }
            }
        }
    }

    /**
     * When the clear data event is invoked it'll clear the bitfits and notify the adapter of data change
     */
    override fun onClearData() {
        logsFragment.bitfits.clear()
        logsFragment.bitFitAdapter.notifyDataSetChanged()
    }
}