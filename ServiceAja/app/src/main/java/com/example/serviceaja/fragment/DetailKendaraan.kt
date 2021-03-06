package com.example.serviceaja.fragment

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.serviceaja.*
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.Kendaraan
import kotlinx.android.synthetic.main.activity_edit_profil_user.*
import kotlinx.android.synthetic.main.fragment_detail_kendaraan.*
import kotlinx.android.synthetic.main.fragment_detail_kendaraan.view.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailKendaraan : Fragment() {
    private lateinit var photoPath: String
    private var photoFileName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail_kendaraan, container, false)

        val toolbar = rootView.findViewById(R.id.detailKendaraan_toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.apply {
            setNavigationOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.kendaraan_fragmentContainer, DaftarKendaraan())
                    commit()
                }
            }
            setTitle("Tambah Kendaraan")
        }

        val adapterMerk = ArrayAdapter.createFromResource(requireActivity(), R.array.merkMobil, android.R.layout.simple_dropdown_item_1line)
        val adapterNama = ArrayAdapter.createFromResource(requireActivity(), R.array.namaMobil, android.R.layout.simple_dropdown_item_1line)
        val adapterBahanBakar = ArrayAdapter.createFromResource(requireActivity(), R.array.bahanBakar, android.R.layout.simple_dropdown_item_1line)

        var merk = ""
        var nama = ""
        var bahanBakar = ""

        rootView.detailKendaraan_merk.setAdapter(adapterMerk)
        rootView.detailKendaraan_merk.setOnItemClickListener { parent, view, position, id ->
            merk = parent.getItemAtPosition(position).toString()
        }

        rootView.detailKendaraan_nama.setAdapter(adapterNama)
        rootView.detailKendaraan_nama.setOnItemClickListener { parent, view, position, id ->
            nama = parent.getItemAtPosition(position).toString()
        }

        rootView.detailKendaraan_bahanBakar.setAdapter(adapterBahanBakar)
        rootView.detailKendaraan_bahanBakar.setOnItemClickListener { parent, view, position, id ->
            bahanBakar = parent.getItemAtPosition(position).toString()
        }

        rootView.detailKendaraan_tambahFoto.setOnClickListener {
            val menu = PopupMenu(context!!, editProfil_btnEditFoto)
            menu.menuInflater.inflate(R.menu.menu_choose_image, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menuChooseItem_bukaKamera -> {
                        // Sebelum membuka kamera, akan dicek terlebih dahulu permission, apakah sudah diberikan
                        // atau belum
                        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.CAMERA), 101)
                        openCamera()
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
                        openGallery()
                        true
                    }
                    else -> false
                }
            }
            menu.show()
        }

        if (arguments != null) {
            rootView.apply {
                detailKendaraan_btnExecute.setText("Edit")
                if (arguments?.getString(KENDARAAN_TIPE) == "Mobil") detailKendaraan_rbMobil.isChecked = true
                else detailKendaraan_rbMotor.isChecked = true
                detailKendaraan_noPlat.setText(arguments?.getString(KENDARAAN_NO_PLAT))
                detailKendaraan_merk.setText(arguments?.getString(KENDARAAN_MERK))
                detailKendaraan_nama.setText(arguments?.getString(KENDARAAN_NAMA))
                detailKendaraan_tahun.setText(arguments?.getString(KENDARAAN_TAHUN))
                detailKendaraan_warna.setText(arguments?.getString(KENDARAAN_WARNA))
                detailKendaraan_bahanBakar.setText(arguments?.getString(KENDARAAN_BAHAN_BAKAR))
                detailKendaraan_noRangka.setText(arguments?.getString(KENDARAAN_NO_RANGKA))
                detailKendaraan_noMesin.setText(arguments?.getString(KENDARAAN_NO_MESIN))
                detailKendaraan_noBPKB.setText(arguments?.getString(KENDARAAN_NO_BPKB))
            }
        }

        rootView.findViewById<Button>(R.id.detailKendaraan_btnExecute).setOnClickListener {
            val jenis = if (rootView.detailKendaraan_rbMobil.isChecked) "Mobil" else "Motor"
            val plat = rootView.detailKendaraan_noPlat.text.toString()
            val tahun = rootView.detailKendaraan_tahun.text.toString().toInt()
            val warna = rootView.detailKendaraan_warna.text.toString()
            val noRangka = rootView.detailKendaraan_noRangka.text.toString()
            val noMesin = rootView.detailKendaraan_noMesin.text.toString()
            val noBPKB = rootView.detailKendaraan_noBPKB.text.toString()
            val serviceTerakhir = Calendar.getInstance().time

            merk = rootView.detailKendaraan_merk.text.toString()
            nama = rootView.detailKendaraan_nama.text.toString()
            bahanBakar = rootView.detailKendaraan_bahanBakar.text.toString()
            val db = DBHelper(context!!)
            val kendaraan = Kendaraan(jenis, plat, merk, nama, tahun, bahanBakar, warna, noRangka, noMesin, noBPKB, SimpleDateFormat("dd MMMM yyyy").format(serviceTerakhir))
            if (rootView.detailKendaraan_btnExecute.text == "Tambah")
                db.addKendaraan((activity as KendaraanActivity).user.noTelp, kendaraan, arrayListOf(photoFileName))
            else {
                when(jenis) {
                    "Mobil" -> (activity as KendaraanActivity).daftarMobil[arguments?.getInt(KENDARAAN_POSITION)!!] = kendaraan
                    "Motor" -> (activity as KendaraanActivity).daftarMotor[arguments?.getInt(KENDARAAN_POSITION)!!] = kendaraan
                }
            }
            (activity as KendaraanActivity).updateListKendaraan()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.kendaraan_fragmentContainer, DaftarKendaraan())
                commit()
            }
        }

        rootView.detailKendaraan_tambahFoto.setOnClickListener {
            val menu = PopupMenu(context, detailKendaraan_tambahFoto)
            menu.menuInflater.inflate(R.menu.menu_choose_image, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menuChooseItem_bukaKamera -> {
                        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(context as KendaraanActivity, arrayOf(android.Manifest.permission.CAMERA), 101)
                        openCamera()
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(context as KendaraanActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
                        openGallery()
                        true
                    }
                    else -> false
                }
            }
            menu.show()
        }
        return rootView
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
            else
                Toast.makeText(context!!, "Tidak dapat membuka kamera karena tidak diberi izin akses", Toast.LENGTH_SHORT).show()
        }
        else if (requestCode == 102) {
            if (grantResults[0].equals(PackageManager.PERMISSION_GRANTED))
                openGallery()
            else
                Toast.makeText(context!!, "Tidak dapat membuka Gallery karena tidak diberi izin akses", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val bitmap = data.extras?.get("data") as Bitmap
            detailKendaraan_foto1.setImageBitmap(bitmap)
            val sdf = SimpleDateFormat("yyyyMMdd_hhmmss")
            photoFileName = "JPEG_" + sdf.format(Calendar.getInstance().time)
            val externalStorage = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File(externalStorage, photoFileName).createNewFile()

            context!!.openFileOutput("$photoFileName.jpg", Context.MODE_PRIVATE).use {
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val bitmap = BitmapFactory.decodeStream(context!!.contentResolver.openInputStream(data.data!!))
            detailKendaraan_foto1.setImageBitmap(bitmap)
            val sdf = SimpleDateFormat("yyyyMMdd_hhmmss")
            photoFileName = "JPEG_" + sdf.format(Calendar.getInstance().time)
            context!!.openFileOutput("$photoFileName.jpg", Context.MODE_PRIVATE).use {
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            return
        val openCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(openCam, 1)
        /*
        if (openCam.resolveActivity(context!!.packageManager) != null) {
            // Saat membuka intent implisit kamera, proses akan sekaligus membuat suatu file baru yang merupakan file foto yang akan disimpan dalam
            // folder khusus untuk aplikasi, ditandai dengan pemanggilan fungsi createImageFile()
            photoFile = try {
                createImageFile()
            } catch (ex: IOException) {
                Log.e("Failed to save image", ex.toString())
                null
            }
            // Jika file terbentuk, maka akan disimpan dalam direktori untuk app, lalu akan membuka kamera
            if (photoFile != null) {
                val photoUri = FileProvider.getUriForFile(context!!, "com.example.android.fileprovider", photoFile!!)
                openCam.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(openCam, 1)
            }
        }*/
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)
    }

    private fun createImageFile(): File {
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val fileName = "JPEG_" + LocalDateTime.now().format(dateFormat)
        val dir = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // Setelah di-generasi nama dan direktori, maka file akan dibentuk, yang kemudian akan diisi dengan foto hasil jepretan.
        return File.createTempFile(fileName, ".jpg", dir).apply {
            photoPath = absolutePath
        }
    }
}