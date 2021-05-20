package com.example.serviceaja.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.text.Editable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVTransactionServiceProductPreview
import com.example.serviceaja.recyclerview.RecyclerViewBengkelDetailsPreview
import com.example.serviceaja.recyclerview.RecyclerViewServiceDetailsSearchResult
import kotlinx.android.synthetic.main.activity_search.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class SearchActivity : AppCompatActivity() {
    
    //Variable untuk menyimpan nama file di internal storage
    val addrs : String = "SearchHistory"
    
    var p1: MyProgressTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        readFileInternal()

        backBtn.setOnClickListener {
            onBackPressed()
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchResultIntent = Intent(this, SearchResultActivity::class.java)
                startActivity(searchResultIntent)
            }
            return@setOnEditorActionListener true
        }

        searchIcon.setOnClickListener {

            p1 = MyProgressTask(this)
            p1!!.execute()
            
            //file akan di tulis pada saat menekan tombol pencarian
            writeFileInternal()
        }
        
        //button hapus file history pencarian
        btn_historyDelete.setOnClickListener{
            delFile()
            readFileInternal()
        }
    }
    
    //Function Membaca file history pencarian
    private fun readFileInternal(){
        historyKey.text = ""
        var i = 0
        try {
            var input = openFileInput("${addrs}.txt").apply {
                bufferedReader().useLines {
                    for(text in it.toList()){
                        if (i==0){
                            historyKey.text=text
                        }
                        else{
                            historyKey.setText("${historyKey.text}\n$text")
                        }
                        i++
                    }
                }
            }
        }catch (e : FileNotFoundException){
        }catch (e : IOException){
        }
    }

    //Function Menulis history pencarian pada internal storage
    private fun writeFileInternal(){
        var output = openFileOutput("${addrs}.txt", Context.MODE_PRIVATE).apply {
            historyKey.setText("${historyKey.text}\n${searchEditText.text}")
            write(historyKey.text.toString().toByteArray())
            close()
            readFileInternal()

        }
    }

    //Function menghapus History Pencarian
    private fun delFile() {
        if(fileList().size!=0) {
            for (i in fileList())
                deleteFile(i)
        }
    }
}