package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import mobile.hospetall.ps.isel.hospetallmobile.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }*/

    open fun goToPetListActivity(item: MenuItem): Boolean{
        PetsListActivity.start(baseContext)
        return true
    }

    open fun goToHomeActivity(item: MenuItem): Boolean {
        MainActivity.start(baseContext)
        return true
    }

    open fun goToProcedures(item: MenuItem) : Boolean {
        return true
    }

    open fun goToTreatment(item: MenuItem) : Boolean {
        TreatmentListActivity.start(baseContext)
        return true
    }

    open fun goToConsultations(item: MenuItem): Boolean {
        ConsultationListActivity.start(baseContext)
        return true
    }

    open fun goToProfile(item: MenuItem) : Boolean {
        return true
    }

    open fun goToSettings(item: MenuItem): Boolean {
        return true
    }

    open fun goToSchedule(item: MenuItem): Boolean {
        return true
    }

}