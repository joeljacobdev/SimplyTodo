package com.pcforgeek.simplytodo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.data.NotesRepository
import com.pcforgeek.simplytodo.data.entity.Label
import com.pcforgeek.simplytodo.viewmodel.LabelViewModel
import com.pcforgeek.simplytodo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.content_main.*

private const val NOTES_BY_LABEL_FRAG = "notesByLabelFragment"
private const val NOTES_LIST_FRAG = "notesListFragment"
private const val EDIT_FRAG = "editFragment"

class MainActivity : AppCompatActivity() {

    private lateinit var repository: NotesRepository
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LabelViewModel
    private var labelList = listOf<Label>()
    private lateinit var tagSubMenu: SubMenu
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            setupDrawerToggle()
        }

        repository = NotesRepository(application)
        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LabelViewModel::class.java)

        supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, NotesListFragment.newInstance(), NOTES_LIST_FRAG)
                .commit()

        setupNavDrawer(nav_drawer)
        viewModel.getAllLabel().observe(this, Observer {
            labelList = it!!
            for (label in labelList) {
                tagSubMenu.add(R.id.tagMenuGroup, label.labelId!!, Menu.NONE, label.label)
                        .setIcon(R.drawable.ic_hashtag)
            }
        })

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                animateFAB()
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frag_container)
                val tag = currentFragment!!.tag
                when (tag) {
                    EDIT_FRAG -> {
                        (currentFragment as EditNoteFragment).saveNote()
                        onBackPressed()
                    }
                    NOTES_LIST_FRAG -> {
                        openEditFragment()
                        changeFAB()
                    }
                }
            }
        })
    }

    private fun setupDrawerToggle() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun setupNavDrawer(navDrawer: NavigationView) {
        tagSubMenu = nav_drawer.menu.addSubMenu(getString(R.string.tag_menu_title))
        navDrawer.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                selectDrawerItem(item)
                return true
            }
        })
    }

    // currently implemented to work with label only6
    fun selectDrawerItem(item: MenuItem) {
        Log.i("MenuItem: Clicked", "${item.itemId}")

        //item.isChecked = !item.isChecked
        drawer_layout.closeDrawers()

        supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, NotesListByLabelFragment.newInstance(item.itemId), NOTES_BY_LABEL_FRAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        changeFAB()
    }

    fun animateFAB() {
        val drawable = fab.drawable
        (drawable as AnimatedVectorDrawable).start()
    }

    fun changeFAB() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frag_container)
        val tag = currentFragment!!.tag
        Log.i("FAB", "fab changed to - $tag")
        when (tag) {
            EDIT_FRAG -> fab.setImageDrawable(getDrawable(R.drawable.avd_anim_add_to_done))
            NOTES_LIST_FRAG -> fab.setImageDrawable(getDrawable(R.drawable.avd_anim_done_to_add))
        }
    }

    fun openEditFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, EditNoteFragment.newInstance("NEW", null), EDIT_FRAG)
                .addToBackStack(null)
                .commit()
    }
}
