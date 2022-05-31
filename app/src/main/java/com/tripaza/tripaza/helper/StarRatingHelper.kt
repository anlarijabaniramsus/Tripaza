package com.tripaza.tripaza.helper

import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.StarRatingBinding

object StarRatingHelper {
    fun setStarRating(item: StarRatingBinding, rating: Int){
        if (rating >= 1) item.star1.setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
        if (rating >= 2) item.star2.setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
        if (rating >= 3) item.star3.setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
        if (rating >= 4) item.star4.setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
        if (rating >= 5) item.star5.setImageResource(R.drawable.ic_baseline_star_rate_24_gold)
    }
}