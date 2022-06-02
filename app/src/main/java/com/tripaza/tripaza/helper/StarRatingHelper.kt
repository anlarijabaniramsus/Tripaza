package com.tripaza.tripaza.helper

import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.StarRatingBinding

object StarRatingHelper {
    fun setStarRating(item: StarRatingBinding, rating: Int){
        val ratingStar = arrayOf(
            item.star1,
            item.star2,
            item.star3,
            item.star4,
            item.star5
        )
        for (i in ratingStar.indices){
            if ( i+1 <= rating )
                ratingStar[i].setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
            else
                ratingStar[i].setImageResource(R.drawable.ic_baseline_star_rate_24)
        }
    }
}