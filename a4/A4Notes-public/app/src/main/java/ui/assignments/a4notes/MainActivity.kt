package ui.assignments.a4notes

import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import ui.assignments.a4notes.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    private fun refreshNoteView(viewModel: NotesViewModel) {
        viewModel.getNotes().observe(this) { list ->
            val linearLayout = findViewById<LinearLayout>(R.id.notesLinearLayout)
            linearLayout.removeAllViews()
            Log.println(Log.INFO, "", "$list")
            list.forEach { VMNote ->
                layoutInflater.inflate(R.layout.note, null, false).apply {
                    findViewById<TextView>(R.id.note_title).text = "${VMNote.value?.title}"
                    findViewById<TextView>(R.id.note_content).text = "${VMNote.value?.content}"

                    if (VMNote.value?.important == true) {
                        this.setBackgroundColor(Color.YELLOW)
                    } else if (VMNote.value?.archived == true) {
                        this.setBackgroundColor(Color.GRAY)
                    }

                    this.id = VMNote.value?.id!!
                    Log.println(Log.INFO, "", "creating button with id: ${this.id}")
                    linearLayout.addView(this)
                }
            }

            val dip = ((9 - list.size) * 70).toFloat()
            val r: Resources = resources
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.displayMetrics
            )
            Log.println(Log.INFO, "", "$px")
            findViewById<Button>(R.id.add_note_button).translationY = px
        }
    }

    fun deleteNote(view: View) {
        val noteId = (view.parent as ViewGroup).id
        viewModel.deleteNote(noteId)
    }

    fun archiveNote(view: View) {
        val noteId = (view.parent as ViewGroup).id

        val notes = viewModel.getNotes().value
        Log.println(Log.INFO, "", "$notes")

        notes?.find { it.value?.id == noteId }?.apply {
            if (this.value?.archived == true) {
                viewModel.archiveNote(noteId, false)
            } else {
                viewModel.archiveNote(noteId, true)
            }
        }
    }

    private val viewModel : NotesViewModel by viewModels { NotesViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getNotes().observe(this) {
            Log.i("MainActivity", it?.fold("Visible Note IDs:") { acc, cur -> "$acc ${cur.value?.id}" } ?: "[ERROR]")
        }

        findViewById<Switch>(R.id.archivedSwitch).setOnCheckedChangeListener { view, isChecked ->
            view as Switch
            if (isChecked) {
                viewModel.setViewArchived(true)
                Log.println(Log.INFO, "", "true")
                refreshNoteView(viewModel)
            } else {
                viewModel.setViewArchived(false)
                Log.println(Log.INFO, "", "false")
                refreshNoteView(viewModel)
            }
        }

        refreshNoteView(viewModel)
    }
}