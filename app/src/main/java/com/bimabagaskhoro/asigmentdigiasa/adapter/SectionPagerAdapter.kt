@file:Suppress("DEPRECATION")

package com.bimabagaskhoro.asigmentdigiasa.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bimabagaskhoro.asigmentdigiasa.R
import com.bimabagaskhoro.asigmentdigiasa.ui.favorite.FavoriteFragment
import com.bimabagaskhoro.asigmentdigiasa.ui.popular.PopularFragment
import com.bimabagaskhoro.asigmentdigiasa.ui.toprated.TopRatedFragment

class SectionPagerAdapter(
    private val mContex: Context,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val tabName = intArrayOf(R.string.popular, R.string.toprated, R.string.favorite)
    }

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> PopularFragment()
            1 -> TopRatedFragment()
            2 -> FavoriteFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContex.resources.getString(tabName[position])
    }

}