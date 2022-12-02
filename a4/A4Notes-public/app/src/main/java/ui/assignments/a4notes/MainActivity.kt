package ui.assignments.a4notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import ui.assignments.a4notes.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model : NotesViewModel by viewModels { NotesViewModel.Factory }
        model.getNotes().observe(this) {
            Log.i("MainActivity", it?.fold("Visible Note IDs:") { acc, cur -> "$acc ${cur.value?.id}" } ?: "[ERROR]")
        }

        findViewById<Switch>(R.id.archivedSwitch).setOnCheckedChangeListener { view, isChecked ->
            view as Switch
            if (isChecked)
                model.viewArchived = MutableLiveData(true)
            else
                model.viewArchived = MutableLiveData(false)
        }
    }
}