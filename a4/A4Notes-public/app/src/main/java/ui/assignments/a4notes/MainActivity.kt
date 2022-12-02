package ui.assignments.a4notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                    linearLayout.addView(this)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel : NotesViewModel by viewModels { NotesViewModel.Factory }
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