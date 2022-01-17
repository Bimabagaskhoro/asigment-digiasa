package com.bimabagaskhoro.asigmentdigiasa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bimabagaskhoro.asigmentdigiasa.R
import com.bimabagaskhoro.asigmentdigiasa.adapter.SectionPagerAdapter
import com.bimabagaskhoro.asigmentdigiasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabMode.setupWithViewPager(viewPager)
            supportActionBar?.elevation = 0f
        }
    }
}