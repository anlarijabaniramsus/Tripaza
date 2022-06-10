package com.tripaza.tripaza.helper

import com.tripaza.tripaza.BuildConfig

object Constants {
    
    const val USER_DATA_MIN_PASSWORD_LENGTH = 6
    const val USER_DATA_MIN_PHONE_LENGTH = 6
    const val API_BASE_URL = "https://tripaza-352307.uc.r.appspot.com/"
    const val MAP_API_BASE_URL = "https://maps.googleapis.com/"
    
    // Dummy Images urls
    const val DUMMY_IMAGE_PLACE = "https://images.hdqwalls.com/download/small-memory-8k-2a-1920x1080.jpg"
    const val DUMMY_IMAGE_FOOD = "https://images.pling.com/img/00/00/63/48/00/1646778/39857213912be2a47096d2983bad03a3756de42ad83b245ca7e5f3e75f62d0f910df.jpg"
//    const val DUMMY_IMAGE_FEATURED = "https://cdnb.artstation.com/p/assets/images/images/024/538/827/original/pixel-jeff-clipa-s.gif?1582740711"
    const val DUMMY_IMAGE_FEATURED = "https://media.istockphoto.com/photos/retro-futuristic-city-flythrough-background-80s-scifi-landscape-in-picture-id1248619427?b=1&k=20&m=1248619427&s=170667a&w=0&h=svQPXt8hCva0D5xKx4l7LHW9v9zjZfBJwfWWWUMvFWw="
    const val DUMMY_IMAGE_PROFILE = "https://wallpaperaccess.com/full/4958080.jpg"



    const val MAP_API_KEY = BuildConfig.MAPS_API_KEY
    const val MAP_FIT_TO_MARKER_PADDING = 150
    const val MAP_BOUNDARY_TOP_LONGITUDE = 6.020783894579806
    const val MAP_BOUNDARY_TOP_LATITUDE = 95.0279631472171
    const val MAP_BOUNDARY_BOTTOM_LONGITUDE = -12.952934231330184
    const val MAP_BOUNDARY_BOTTOM_LATITUDE = 141.58526255474823
    const val MAP_ICON_MARKER_COLOR = "#C12E2E"
}