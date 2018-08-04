package com.pcforgeek.simplytodo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import com.pcforgeek.simplytodo.R


private const val NOTES_LIST_FRAG = "notesListFragment"
private const val EDIT_FRAG = "editFragment"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, NotesListFragment.newInstance(), NOTES_LIST_FRAG)
                .commit()

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
        Log.i("FAB","fab changed to - $tag")
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
