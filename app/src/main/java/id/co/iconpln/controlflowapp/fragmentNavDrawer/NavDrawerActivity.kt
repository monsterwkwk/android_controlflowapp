package id.co.iconpln.controlflowapp.fragmentNavDrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import id.co.iconpln.controlflowapp.R
import id.co.iconpln.controlflowapp.StyleActivity
import id.co.iconpln.controlflowapp.fragmentTab.FirstFragment
import id.co.iconpln.controlflowapp.fragmentTab.SecondFragment
import id.co.iconpln.controlflowapp.hero.ListHeroFragment
import kotlinx.android.synthetic.main.activity_nav_drawer.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class NavDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)

        setupActionBar()
        navViewDrawer.setNavigationItemSelectedListener(this)
        selectFirstNavigationMenu()
    }

    private fun selectFirstNavigationMenu(){
        navViewDrawer.menu.performIdentifierAction(R.id.nav_home_drawer,0)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        var toogle = ActionBarDrawerToggle(
            this, dlDrawerLayout, toolbar, R.string.app_name, 0
        )

        dlDrawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home_drawer -> {
                loadFragment(FirstFragment())
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_person_drawer -> {
                loadFragment(ListHeroFragment())
                Toast.makeText(this, "Person", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_gelery_drawer -> {
                val openActivityIntent = Intent(this, StyleActivity::class.java)
                startActivity(openActivityIntent)
                Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_edit -> {
                loadFragment(SecondFragment())
            }
            R.id.nav_exit -> {
                finish()
            }
        }
        // clicked item
        unCheckedItemMenu()
        item.isChecked = true

        // item title in actionbar
        title = item.title

        dlDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.flDrawerContent, fragment, fragment::class.java.simpleName)
            .commit()
    }

    private fun unCheckedItemMenu(){
        for (menuCount in 0 until navViewDrawer.menu.size()){
            navViewDrawer.menu.getItem(menuCount).isChecked = false

            if(navViewDrawer.menu.getItem(menuCount).hasSubMenu()){

                for(submenuCount in 0 until navViewDrawer.menu.getItem(menuCount).subMenu.size()){
                    navViewDrawer.menu.getItem(menuCount).subMenu
                        .getItem(submenuCount).isChecked = false
                }
            }
        }
    }
}
