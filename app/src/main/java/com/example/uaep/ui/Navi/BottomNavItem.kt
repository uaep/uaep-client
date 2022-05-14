package com.example.uaep.ui.Navi

import com.example.uaep.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24,"home")
    object MyNetwork: BottomNavItem("My Network",R.drawable.ic_baseline_person_pin_24,"my_network")
    object AddPost: BottomNavItem("Post",R.drawable.ic_baseline_home_24,"add_post")
    object Notification: BottomNavItem("Notification",R.drawable.ic_baseline_person_pin_24,"notification")
    //object Jobs: BottomNavItem("Jobs",R.drawable.ic_job,"jobs")
}