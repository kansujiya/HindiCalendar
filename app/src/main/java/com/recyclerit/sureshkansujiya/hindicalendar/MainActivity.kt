package com.recyclerit.sureshkansujiya.hindicalendar

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        MobileAds.initialize(this, "ca-app-pub-7486954764491752~5265347270")

        val adRequest = AdRequest.Builder()
                .build()

        // Start loading the ad in the background.
        ad_view.loadAd(adRequest)

        // Initialize a new pager adapter instance with list

        val dataItemsImages = listOf(R.drawable.jan,R.drawable.fab,R.drawable.march,R.drawable.april,R.drawable.may,R.drawable.june,R                                   .drawable.july,R.drawable.aug,R.drawable.sep,R.drawable.oct,R.drawable.nov,R.drawable.dec)

        val adapter = CustomPageAdaptor(dataItemsImages)
        // Finally, data bind the view pager widget with pager adapter
        container.adapter = adapter

        tabs.addOnTabSelectedListener( TabLayout.ViewPagerOnTabSelectedListener(container))
        container.addOnPageChangeListener( TabLayout.TabLayoutOnPageChangeListener(tabs))
        container.currentItem = Date().month

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        toolbar.title = "$currentDate"

        //ads configration
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-7486954764491752/8342435748"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //throw UnsupportedOperationException()
                // your code
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //throw UnsupportedOperationException()
                // your code
                adapter.attacher = null
                if (mInterstitialAd.isLoaded){
                    if (position == 2 || position == 4 || position == 6 || position == 8 || position == 10 || position == 12) {
                        mInterstitialAd.show()
                    }
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }

            }
            // 6. when some views conflict with swipe back , you should do these, for example:
            override fun onPageSelected(position: Int) {
                //tabs.setupWithViewPager(container)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        openWhatsApp()
        return true
    }

    private fun openWhatsApp() {
        val smsNumber = "7****" //without '+'
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            var shareMessage = "\nBest hindu calendar application for daily use.\n"
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            sendIntent.putExtra("2019", "$smsNumber@s.whatsapp.net") //phone number without "+" prefix
            sendIntent.`package` = "com.whatsapp"
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}
