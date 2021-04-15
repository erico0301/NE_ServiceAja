package com.example.serviceaja.search

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast


public class MyProgressTask(var ctx: Context) :

    AsyncTask<Void?, Int?, String>() {
    var pd: ProgressDialog? = null
    override fun onPreExecute() {
        pd = ProgressDialog(ctx)
        pd!!.setTitle("Searching")
        pd!!.max = 10
        pd!!.setButton(
            ProgressDialog.BUTTON_NEGATIVE, "Cancle"
        ) { dialogInterface, i ->
            dialogInterface.cancel()
            cancel(true)
        }
        pd!!.show()
    }

    protected override fun doInBackground(vararg params: Void?): String? {
        return try {
            for (i in 0..10) {
                Thread.sleep(500)
                Log.i("Thread", "Execute " + 1)
                publishProgress(i)
            }
            /*
            var ac2 =
                Intent(ctx,MainActivity2::class.java)
            ctx.startActivity(ac2)
            */

            var searchResultIntent = Intent(ctx, SearchResultActivity::class.java)
            ctx.startActivity(searchResultIntent)
            "Successful"

        } catch (e: Exception) {
            Log.i("Exeption", e.message!!)
            "Failure"
        }
    }

    protected override fun onProgressUpdate(vararg values: Int?) {
        val myValue = values[0]
        if (myValue != null) {
            pd!!.progress = myValue
        }
    }

    override fun onPostExecute(s: String) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
        pd!!.dismiss()
    }


}
