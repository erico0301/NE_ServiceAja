package com.example.serviceaja

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.User
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginPageTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginTest_fieldEmailNoTelpatauPasswordKosong() {
        onView(withId(R.id.halamanAwal_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanLogin_inputEmail)).check(matches(hasErrorText("Isi dengan E-mail atau No. Telepon Terdaftar")))
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.halamanLogin_inputEmail)).check(matches(not(hasErrorText("Isi dengan E-mail atau No. Telepon Terdaftar"))))
        closeSoftKeyboard()

        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.typeText("admin"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanLogin_inputEmail)).check(matches(hasErrorText("Isi dengan E-mail atau No. Telepon Terdaftar")))
        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanLogin_inputPassword)).check(matches(hasErrorText("Harap isi Password Anda")))
        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.typeText("admin"))
        closeSoftKeyboard()

        onView(withId(R.id.halamanLogin_inputPassword)).check(matches(not(hasErrorText("Harap isi Password Anda"))))
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.halamanLogin_inputEmail)).check(matches(not(hasErrorText("Isi dengan E-mail atau No. Telepon Terdaftar"))))
    }

    @Test
    fun loginTest_loginGagal() {
        onView(withId(R.id.halamanAwal_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.typeText("tester"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_btnMasuk)).perform(ViewActions.click())
        onView(withText("Login Gagal")).check(matches(isDisplayed()))
    }

    @Test
    fun loginTest_loginSukses() {
        onView(withId(R.id.halamanAwal_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.typeText("admin"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.bottomNavBarMenu)).check(matches(isDisplayed()))
    }

    @Test
    fun registerTest_fieldSalahSatuKosong() {
        onView(withId(R.id.halamanAwal_btnDaftar)).perform(ViewActions.click())
        onView(withId(R.id.halamanDaftar_namaLengkap)).perform(ViewActions.typeText("Budi Doremifasol"))
        onView(withId(R.id.halamanDaftar_namaLengkap)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_namaLengkap)).check(matches(hasErrorText("Nama Lengkap tidak boleh kosong")))
        closeSoftKeyboard()

        onView(withId(R.id.halamanDaftar_alamatEmail)).perform(ViewActions.typeText("admin@gmail.co.id"))
        onView(withId(R.id.halamanDaftar_alamatEmail)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_alamatEmail)).check(matches(hasErrorText("Alamat E-mail tidak boleh kosong")))
        closeSoftKeyboard()

        onView(withId(R.id.halamanDaftar_noTelepon)).perform(ViewActions.typeText("812345678900"))
        onView(withId(R.id.halamanDaftar_noTelepon)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_noTelepon)).check(matches(hasErrorText("No. Telepon tidak boleh kosong")))
        closeSoftKeyboard()

        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdfghjkl"))
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_password)).check(matches(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka")))
        closeSoftKeyboard()

        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.typeText("asdfghjkl"))
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).check(matches(hasErrorText("Konfirmasi Password tidak boleh kosong")))
        closeSoftKeyboard()
    }

    @Test
    fun registerTest_fieldEmailTelahDigunakan() {
        onView(withId(R.id.halamanAwal_btnDaftar)).perform(ViewActions.click())
        onView(withId(R.id.halamanDaftar_alamatEmail)).perform(ViewActions.typeText("admin@gmail.co.id"))
        onView(withId(R.id.halamanDaftar_alamatEmail)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_alamatEmail)).check(matches(hasErrorText("Alamat E-mail tidak boleh kosong")))

        onView(withId(R.id.halamanDaftar_alamatEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.halamanDaftar_alamatEmail)).check(matches(hasErrorText("Alamat E-mail telah terdaftar")))
    }

    @Test
    fun registerTest_fieldNoTeleponTelahDigunakan() {
        onView(withId(R.id.halamanAwal_btnDaftar)).perform(ViewActions.click())
        onView(withId(R.id.halamanDaftar_noTelepon)).perform(ViewActions.typeText("8123456789"))
        onView(withId(R.id.halamanDaftar_noTelepon)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_noTelepon)).check(matches(hasErrorText("No. Telepon tidak boleh kosong")))

        onView(withId(R.id.halamanDaftar_noTelepon)).perform(ViewActions.typeText("81234567890"))
        onView(withId(R.id.halamanDaftar_noTelepon)).check(matches(hasErrorText("No. Telepon telah terdaftar")))
    }

    @Test
    fun registerTest_fieldPasswordTidakValid() {
        onView(withId(R.id.halamanAwal_btnDaftar)).perform(ViewActions.click())
        //Password kosong
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdfghjkl"))
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_password)).check(matches(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka")))
        //Password tidak mencapai 8 huruf
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdfghj"))
        onView(withId(R.id.halamanDaftar_password)).check(matches(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka")))
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.clearText())
        //Password tidak memiliki angka, hanya huruf
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdfghjk"))
        onView(withId(R.id.halamanDaftar_password)).check(matches(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka")))
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.clearText())
        //Password tidak memiliki huruf, hanya angka
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("12345678"))
        onView(withId(R.id.halamanDaftar_password)).check(matches(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka")))
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.clearText())
        //Password valid
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdf5678"))
        onView(withId(R.id.halamanDaftar_password)).check(matches(not(hasErrorText("Password wajib minimal 8 karakter campuran huruf dan angka"))))
    }

    @Test
    fun registerTest_fieldKonfirmasiPasswordValidasi() {
        onView(withId(R.id.halamanAwal_btnDaftar)).perform(ViewActions.click())
        onView(withId(R.id.halamanDaftar_password)).perform(ViewActions.typeText("asdfghjkl123"))
        closeSoftKeyboard()

        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.typeText("asdfghjkl"))
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).check(matches(hasErrorText("Password yang Anda masukkan berbeda")))
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.clearText())
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.typeText(""))
        closeSoftKeyboard()
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).check(matches(hasErrorText("Konfirmasi Password tidak boleh kosong")))

        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).perform(ViewActions.typeText("asdfghjkl123"))
        onView(withId(R.id.halamanDaftar_konfirmasiPassword)).check(matches(not(hasErrorText("Konfirmasi Password tidak boleh kosong"))))
    }
}