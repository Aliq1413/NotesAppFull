package com.example.notesappfull
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {

    lateinit var rvNotes: RecyclerView
    lateinit var edtNote: EditText
    lateinit var dbh : DBhlpr
    var note = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbh = DBhlpr(applicationContext)

        edtNote=findViewById(R.id.edtNote)

        rvNotes=findViewById(R.id.rvNotes)
        updtRC()
        rvNotes.adapter?.notifyDataSetChanged()

        var subBtn=findViewById(R.id.submBtn) as Button
        subBtn.setOnClickListener {
            addNote()
        }
    }

    fun updtRC(){
        rvNotes.adapter = RVAdapter(this,dbh.retriveAll())
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    fun addNote(){
        try{
            note=edtNote.text.toString()
            var status = dbh.saveData(note)
            Toast.makeText(applicationContext, "Note submitted successfully! " + status, Toast.LENGTH_LONG).show()
            updtRC()
            edtNote.text.clear()
            edtNote.clearFocus()


        }catch (e:Exception){
            Toast.makeText(applicationContext, "There is no notes, in DB", Toast.LENGTH_SHORT).show()
        }
    }

    fun updtNote(note: String, upnote: String){
        dbh.updatNote(note,upnote)
        updtRC()
    }

    fun DialogEdit(oldNote : String ){
        val dialogBuilder = AlertDialog.Builder(this)
        val newNote = EditText(this)
        newNote.hint = "Enter new note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {

                    dialog, id-> updtNote( oldNote,newNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(newNote)
        alert.show()
    }

    fun delNote(Note:String){
        dbh.delNote(Note)
        updtRC()
        Toast.makeText(this, "deleted note successfully!", Toast.LENGTH_SHORT).show()

    }

    fun DialogDel(oldNote : String ){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure?")
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id-> delNote(oldNote)

            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Delete Note")
        alert.show()
    }
}