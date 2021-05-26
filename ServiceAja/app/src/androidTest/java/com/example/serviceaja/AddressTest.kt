package com.example.serviceaja

import android.content.Intent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.classes.User
import com.example.serviceaja.fragment.DetailAlamat
import com.example.serviceaja.recyclerview.RVDetailAlamat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddressTest {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(AlamatActivity::class.java, false, false)
    val intent = Intent()

    @Test
    fun addressTest_tambahAlamat() {
        intent.putExtra(EXTRA_USER, User("Tester", "tester12345@gmail.com", "081236448954", "tester"))
        activityTestRule.launchActivity(intent)
        onView(withId(R.id.daftarAlamat_fab)).perform(ViewActions.click())
        onView(withId(R.id.detailAlamat_toolbar)).check(matches(isDisplayed()))

        onView(withId(R.id.detailAlamat_namaAlamat)).perform(ViewActions.typeText("Rumah"))
        closeSoftKeyboard()

        onView(withId(R.id.detailAlamat_namaPenerima)).perform(ViewActions.typeText("Budi"))
        closeSoftKeyboard()

        onView(withId(R.id.detailAlamat_noTelp)).perform(ViewActions.typeText("081345678921"))
        closeSoftKeyboard()

        onView(withId(R.id.detailAlamat_provinsi)).perform(ViewActions.typeText("Sumatera U"))
        closeSoftKeyboard()
        onView(withText("SUMATERA UTARA")).inRoot(RootMatchers.isPlatformPopup()).perform(ViewActions.click())

        onView(withId(R.id.detailAlamat_kabupatenKota)).perform(ViewActions.typeText("Medan"))
        closeSoftKeyboard()
        onView(withText("KOTA MEDAN")).inRoot(RootMatchers.isPlatformPopup()).perform(ViewActions.click())

        onView(withId(R.id.detailAlamat_kecamatan)).perform(ViewActions.typeText("Medan"))
        closeSoftKeyboard()
        onView(withText("MEDAN KOTA")).inRoot(RootMatchers.isPlatformPopup()).perform(ViewActions.click())

        onView(withId(R.id.detailAlamat_detailAlamat)).perform(ViewActions.typeText("Jl. ABCDE No. 12345"))
        closeSoftKeyboard()
        onView(withId(R.id.detailAlamat_btnExecute)).perform(ViewActions.click())

        onView(withId(R.id.daftarAlamat_rvInstanceAlamat)).check(matches(isDisplayed()))
    }

    @Test
    fun addressTest_editAlamat() {
        val user = User("Tester", "tester12345@gmail.com", "081236448954", "tester")
        user.alamat.add(Alamat("Rumah", "Budi", "082354689512", "MEDAN KOTA", "KOTA MEDAN", "SUMATERA UTARA", "Jl. ABCDE No. 12345"))
        intent.putExtra(EXTRA_USER, user)
        activityTestRule.launchActivity(intent)


    }
}