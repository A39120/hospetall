package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import mobile.hospetall.ps.isel.hospetallmobile.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu_main, menu)
        baseContext
        return true
    }

    //override fun onPrepareOptionsMenu(menu: Menu?): Boolean {}

    open fun goToPetListActivity(item: MenuItem): Boolean{
        val int = Intent(this, PetsListActivity::class.java)
        startActivity(int)
        return true
    }

    open fun goToHomeActivity(item: MenuItem): Boolean {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
        return true
    }
}