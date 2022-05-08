package com.ismail.creatvt.passguard.model

import com.ismail.creatvt.passguard.R

object WebsiteFactory {

    const val OTHER = "Other"

    private val iconsMap = mapOf(
        "amazon" to R.drawable.amazon,
        "facebook" to R.drawable.facebook,
        "twitter" to R.drawable.twitter,
        "twilio" to R.drawable.twilio,
        "twitch" to R.drawable.twitch,
        "github" to R.drawable.github,
        "gaana" to R.drawable.gaana,
        "google" to R.drawable.google,
        "instagram" to R.drawable.instagram,
        "snapchat" to R.drawable.snapchat,
        "bankofindia" to R.drawable.bankofindia,
        "hsbc" to R.drawable.hsbc,
        "hdfc" to R.drawable.hdfc,
        "sbi" to R.drawable.sbi,
        "ola" to R.drawable.ola,
        "oracle" to R.drawable.oracle,
        "skype" to R.drawable.skype,
        "slack" to R.drawable.slack,
        "soundcloud" to R.drawable.soundcloud,
        "spotify" to R.drawable.spotify,
        "stackoverflow" to R.drawable.stackoverflow,
        "swagger" to R.drawable.swagger,
        "zeplin" to R.drawable.zeplin,
        "telegram" to R.drawable.telegram,
        "wordpress" to R.drawable.wordpress,
        "yahoo" to R.drawable.yahoo,
        "linkedin" to R.drawable.linkedin,
        "ujjivan" to R.drawable.ujjivan,
        "punjab_national" to R.drawable.punjab_national,
        "icici" to R.drawable.icici,
        "idbi" to R.drawable.idbi,
        "epfo" to R.drawable.epfo,
        "kotak" to R.drawable.kotak,
        "yes_bank" to R.drawable.yes_bank,
        "axis_bank" to R.drawable.axis_bank,
        "central_bank" to R.drawable.central_bank,
        "citi_bank" to R.drawable.citi_bank,
        "union_bank" to R.drawable.union_bank,
    )

    fun getAllWebsites(): List<Website> = arrayListOf(
        Website("amazon", "Amazon"),
        Website("facebook", "Facebook"),
        Website("twitter", "Twitter"),
        Website("twilio", "Twilio"),
        Website("twitch", "Twitch"),
        Website("github", "Github"),
        Website("gaana", "Gaana"),
        Website("google", "Google"),
        Website("instagram", "Instagram"),
        Website("snapchat", "Snapchat"),
        Website("bankofindia", "Bank of India"),
        Website("hsbc", "HSBC Bank"),
        Website("hdfc", "HDFC Bank"),
        Website("sbi", "State Bank of India"),
        Website("ola", "OLA"),
        Website("oracle", "Oracle"),
        Website("skype", "Skype"),
        Website("slack", "Slack"),
        Website("soundcloud", "Soundcloud"),
        Website("spotify", "Spotify"),
        Website("stackoverflow", "Stackoverflow"),
        Website("swagger", "Swagger"),
        Website("zeplin", "Zeplin"),
        Website("telegram", "Telegram"),
        Website("wordpress", "Wordpress"),
        Website("yahoo", "Yahoo"),
        Website("linkedin", "Linkedin"),
        Website("ujjivan", "Ujjivan Small Finance Bank"),
        Website("punjab_national", "Punjab National Bank"),
        Website("icici", "ICICI Bank"),
        Website("idbi", "IDBI Bank"),
        Website("epfo", "EPFO"),
        Website("kotak", "Kotak Mahindra Bank"),
        Website("yes_bank", "Yes Bank"),
        Website("axis_bank", "Axis Bank"),
        Website("central_bank", "Central Bank of India"),
        Website("citi_bank", "Citi Bank"),
        Website("union_bank", "Union Bank"),
        Website("", OTHER)
    ).sortedWith(Comparator { o1, o2 ->
        if(o1.name == OTHER) return@Comparator 1
        if(o2.name == OTHER) return@Comparator -1
        else o1.name.compareTo(o2.name)
    })

    fun getIcon(iconString: String): Int {
        return iconsMap[iconString] ?:R.drawable.other
    }
}