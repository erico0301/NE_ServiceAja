package com.example.serviceaja

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.User
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditProfileTest {


    @Rule @JvmField
    val activityTestRule = ActivityTestRule(EditProfilUser::class.java, false, false)

    @Test
    fun editProfil_fieldSalahSatuKosong() {
        val user = User("admin", "admin@gmail.com", "081234567890", "admin")
        val users = arrayListOf(
            User("admin", "admin@gmail.com", "081234567890", "admin"),
            User("testing", "testing@gmail.com", "082345678910", "testing")
        )
        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        intent.putExtra(EXTRA_USERS, users)
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.editProfil_nama)).perform(clearText())
        onView(withId(R.id.editProfil_nama)).check(matches(hasErrorText("Nama tidak boleh kosong")))
        onView(withId(R.id.editProfil_nama)).perform(typeText("admin testing"))
        onView(withId(R.id.editProfil_nama)).check(matches(not(hasErrorText("Nama tidak boleh kosong"))))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_alamatEmail)).perform(clearText())
        onView(withId(R.id.editProfil_alamatEmail)).check(matches(hasErrorText("Alamat E-mail tidak boleh kosong")))
        onView(withId(R.id.editProfil_alamatEmail)).perform(typeText("testing@gmail.com"))
        onView(withId(R.id.editProfil_alamatEmail)).check(matches(not(hasErrorText("Alamat E-mail tidak boleh kosong"))))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_noTelepon)).perform(clearText())
        onView(withId(R.id.editProfil_noTelepon)).check(matches(hasErrorText("No. Telepon tidak boleh kosong")))
        onView(withId(R.id.editProfil_noTelepon)).perform(typeText("082345678910"))
        onView(withId(R.id.editProfil_noTelepon)).check(matches(not(hasErrorText("No. Telepon tidak boleh kosong"))))
        closeSoftKeyboard()
    }

    @Test
    fun editProfil_validasiFieldEmailNoTelp() {
        val user = User("admin", "admin@gmail.com", "081234567890", "admin")
        val users = arrayListOf(
            User("admin", "admin@gmail.com", "081234567890", "admin"),
            User("testing", "testing@gmail.com", "082345678910", "testing")
        )
        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        intent.putExtra(EXTRA_USERS, users)
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.editProfil_alamatEmail)).perform(clearText())
        onView(withId(R.id.editProfil_alamatEmail)).perform(typeText("testing@gmail.com"))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_noTelepon)).perform(clearText())
        onView(withId(R.id.editProfil_noTelepon)).perform(typeText("82345678910"))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_alamatEmail)).perform(click())
        onView(withId(R.id.editProfil_alamatEmail)).check(matches(hasErrorText("Alamat E-mail telah terdaftar")))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_noTelepon)).perform(click())
        onView(withId(R.id.editProfil_noTelepon)).check(matches(hasErrorText("No. Telepon telah terdaftar")))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_alamatEmail)).perform(clearText())
        onView(withId(R.id.editProfil_alamatEmail)).perform(typeText("adminTesting@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_alamatEmail)).check(matches(not(hasErrorText("Alamat E-mail telah terdaftar"))))

        onView(withId(R.id.editProfil_noTelepon)).perform(clearText())
        onView(withId(R.id.editProfil_noTelepon)).perform(typeText("82345678190"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_noTelepon)).check(matches(not(hasErrorText("No. Telepon telah terdaftar"))))
    }

    @Test
    fun editProfil_updateProfile() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.halamanAwal_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.halamanLogin_inputEmail)).perform(ViewActions.typeText("admin@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_inputPassword)).perform(ViewActions.typeText("admin"))
        closeSoftKeyboard()
        onView(withId(R.id.halamanLogin_btnMasuk)).perform(ViewActions.click())
        onView(withId(R.id.bottomNavBarMenu)).check(matches(isDisplayed()))

        onView(withId(R.id.profileIcon)).perform(click())
        onView(withId(R.id.profilUser_btnEdit)).perform(click())

        onView(withId(R.id.editProfil_nama)).perform(typeText(" testing"))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_alamatEmail)).perform(clearText())
        onView(withId(R.id.editProfil_alamatEmail)).perform(typeText("adminTesting@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_alamatEmail)).check(matches(not(hasErrorText("Alamat E-mail telah terdaftar"))))

        onView(withId(R.id.editProfil_noTelepon)).perform(clearText())
        onView(withId(R.id.editProfil_noTelepon)).perform(typeText("82345678190"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_noTelepon)).check(matches(not(hasErrorText("No. Telepon telah terdaftar"))))

        onView(withId(R.id.editProfil_btnEditProfil)).perform(click())
        onView(withText("Ubah Informasi Akun")).check(matches(isDisplayed()))
        onView(withText("YA")).perform(click())

        onView(withId(R.id.profilUser_namaUser)).check(matches(withText("admin testing")))
        onView(withId(R.id.profilUser_emailUser)).check(matches(withText("adminTesting@gmail.com")))
        onView(withId(R.id.profilUser_noTelpUser)).check(matches(withText("(+62)-82345678190")))
    }

    @Test
    fun editProfil_ubahPassword() {
        val user = User("admin", "admin@gmail.com", "081234567890", "admin")
        val users = arrayListOf(
            User("admin", "admin@gmail.com", "081234567890", "admin"),
            User("testing", "testing@gmail.com", "082345678910", "testing")
        )
        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        intent.putExtra(EXTRA_USERS, users)
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.editProfil_btnShowEditPassword)).perform(click())
        onView(withId(R.id.editProfil_passwordLama)).perform(typeText("tester"))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_passwordBaru)).perform(typeText("testingaja"))
        closeSoftKeyboard()

        onView(withId(R.id.editProfil_btnEditPassword)).perform(click())
        onView(withText("Field Password tidak terisi lengkap. Silahkan isi setiap field dengan lengkap"))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))

        onView(withId(R.id.editProfil_konfirmasiPassword)).perform(typeText("testingajaa"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_btnEditPassword)).perform(click())

        onView(withId(R.id.editProfil_passwordLama)).check(matches(hasErrorText("Password yang anda masukkan salah")))
        onView(withId(R.id.editProfil_passwordLama)).perform(click())
        onView(withId(R.id.editProfil_passwordLama)).perform(clearText())
        onView(withId(R.id.editProfil_passwordLama)).perform(typeText("admin"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_btnEditPassword)).perform(click())
        onView(withId(R.id.editProfil_passwordLama)).check(matches(not(hasErrorText("Password yang anda masukkan salah"))))

        onView(withId(R.id.editProfil_passwordBaru)).check(matches(hasErrorText("Password harus mengandung minimal 8 karakter, terdiri dari huruf dan angka")))
        onView(withId(R.id.editProfil_passwordBaru)).perform(click())
        onView(withId(R.id.editProfil_passwordBaru)).perform(clearText())
        onView(withId(R.id.editProfil_passwordBaru)).perform(typeText("testing123"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_btnEditPassword)).perform(click())
        onView(withId(R.id.editProfil_passwordBaru)).check(matches(not(hasErrorText("Password harus mengandung minimal 8 karakter, terdiri dari huruf dan angka"))))

        onView(withId(R.id.editProfil_konfirmasiPassword)).check(matches(hasErrorText("Password yang Anda masukkan berbeda")))
        onView(withId(R.id.editProfil_konfirmasiPassword)).perform(click())
        onView(withId(R.id.editProfil_konfirmasiPassword)).perform(clearText())
        onView(withId(R.id.editProfil_konfirmasiPassword)).perform(typeText("testing123"))
        closeSoftKeyboard()
        onView(withId(R.id.editProfil_btnEditPassword)).perform(click())

        onView(withText("Ubah Password")).check(matches(isDisplayed()))
        onView(withText("YA")).perform(click())

        onView(withText("Berhasil Mengubah Password!"))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
}