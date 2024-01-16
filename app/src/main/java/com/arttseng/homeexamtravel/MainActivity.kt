package com.arttseng.homeexamtravel

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.arttseng.homeexamtravel.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        //initPopup(binding.toolbar);
        var deviceLang = Locale.getDefault().language
        if(deviceLang=="zh") {
            deviceLang = deviceLang + "-" + Locale.getDefault().country
        }
        toast(deviceLang.lowercase(Locale.ROOT))
    }

//    lateinit var popupMenu:PopupMenu;
//    fun initPopup(attachView: View) {
//        popupMenu = PopupMenu(
//            this,   //context
//            attachView     //UI View, where clicking the view to show the popup menu
//        )
//
//        //add menu items to popup menu
//        popupMenu.menu.add(Menu.NONE, 0, 0, "Copy") //2nd param is id of the menu item, used to handle click, 3rd param is at which position the item should be displayed, 4th param is the title of the menu item
//        popupMenu.menu.add(Menu.NONE, 1, 1, "Share")
//        popupMenu.menu.add(Menu.NONE, 2, 2, "Save")
//        popupMenu.menu.add(Menu.NONE, 3, 3, "Delete")
//
//        //handle menu item clicks
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//
//            //get id of the item clicked
//            val id = menuItem.itemId
//            val text:String;
//            if (id==0){
//                //Copy clicked
//                text = "Copy Clicked"
//            }
//            else if (id==1){
//                //Share clicked
//                text = "Share Clicked"
//            }
//            else if (id==2){
//                //Save
//                text = "Save Clicked"
//            }
//            else if (id==3){
//                //Delete clicked
//                text = "Delete Clicked"
//            } else {
//                text = "out of range"
//            }
//            Toast.makeText(this@MainActivity, "You Clicked : " + text, Toast.LENGTH_SHORT).show()
//
//            false
//
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun popupDialog() {
        val arrayAdapter: ArrayAdapter<*>
        val langs = this.resources.getStringArray(R.array.pickLanguage)

        // access the listView from xml file
        var mListView =  ListView(this)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, langs)
        mListView.adapter = arrayAdapter
        val dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
            .setTitle(getString(R.string.language))
            .setView(mListView)
            .show()

        mListView.setOnItemClickListener { adapterView, view, i, l ->
            val text = (view as TextView).text
            val langs = this.resources.getStringArray(R.array.lang)
            toast("You Clicked : " + langs[i])
            dialog.dismiss()
        }
    }

    fun Activity.toast(str:String) {
        Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                Log.e("" , "menu setting")
                //popupMenu.show()
                popupDialog()
            }
            else -> {
                Log.e("" , "menu other")
            }
        }
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}