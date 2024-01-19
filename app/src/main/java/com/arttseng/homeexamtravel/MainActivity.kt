package com.arttseng.homeexamtravel

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.arttseng.homeexamtravel.tools.toast
import com.arttseng.homeexamtravel.databinding.ActivityMainBinding
import com.arttseng.homeexamtravel.viewmodel.MyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //dealVM = ViewModelProviders(this).get(MyViewModel::class.java)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        //initPopup(binding.toolbar);
        //get()


    }

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
            viewModel.userLang.value = langs[i]
            //toast("You Clicked : " + langs[i])
            dialog.dismiss()
        }
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