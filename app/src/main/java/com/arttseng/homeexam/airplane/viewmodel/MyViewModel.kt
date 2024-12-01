package com.arttseng.homeexam.airplane.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttseng.homeexam.airplane.tools.RetrofitFactory
import com.arttseng.homeexam.airplane.datamodel.Attraction
import com.arttseng.homeexam.airplane.datamodel.Attractions
import com.arttseng.homeexam.airplane.datamodel.News
import com.arttseng.homeexam.airplane.datamodel.NewsData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import java.util.Locale

class MyViewModel : ViewModel() {
    private val apiClient = RetrofitFactory.WebAccess.API;
    private var userPickLanguage:String

    val attractData = MutableLiveData<List<Attraction>>();
    val newsData = MutableLiveData<List<NewsData>>()
    val userLang = MutableLiveData<String>()

    private val testStr = """
{
  "total": 484,
  "data": [
    {
      "id": 2170,
      "name": "新新公園",
      "name_zh": null,
      "open_status": 1,
      "introduction": "新新公園面積2萬5,408平方公尺，位於南港路3段149巷，土地由國營臺灣鐵路股份有限公司捐贈，屬東區門戶計畫範圍，公園以「生態濕地公園」為規劃方向。\r\n\r\n栽植原生與誘蝶、誘鳥植物，提供生物棲地，提升環境生物多樣性，同時也提供環境教育機會。因應全球暖化、氣候爆烈化，考量滯洪功能，打造海棉城市，開闢蓄水滯洪空間，以達基地保水。以鐵路地下化後的空間軸帶，由西向東串聯公私有土地，發展多核心走廊型都市空間結構，做為民眾休閒及防災多功能的公園。\r\n\r\n包含以東北角疊山地形為基盤設計的兒童攀岩場、溜滑梯、溜索、平衡木等兒童遊戲設施，讓大人與小孩可一同在起伏的地景中活動及賞景。兒童遊戲場滑梯平台是公園最高點，為眺望公園全景熱門地點。\r\n\r\n(資料來源:臺北市政府工務局公園路燈工程管理處)",
      "open_time": "開放空間",
      "zipcode": "115",
      "distric": "南港區",
      "address": "115 臺北市南港區南港路3段47巷26號",
      "tel": "+886-2-27853819",
      "fax": "",
      "email": "",
      "months": "01,07,02,08,03,09,04,10,05,11,06,12",
      "nlat": 25.05136,
      "elong": 121.5903,
      "official_site": "https://play4u.gov.taipei/News_Content.aspx?n=4773608F226124D4&sms=7B56BA5392EB632C&s=E5D8BCD9895C9B61",
      "facebook": "",
      "ticket": "",
      "remind": "",
      "staytime": "",
      "modified": "2024-01-15 16:57:38 +08:00",
      "url": "https://www.travel.taipei/zh-tw/attraction/details/2170",
      "category": [
        {
          "id": 16,
          "name": "戶外踏青"
        },
        {
          "id": 19,
          "name": "親子共遊"
        }
      ],
      "target": [
        {
          "id": 61,
          "name": "親子共學"
        }
      ],
      "service": [
        {
          "id": 141,
          "name": "無障礙設施"
        }
      ],
      "friendly": [],
      "images": [
        {
          "src": "https://www.travel.taipei/image/180478",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/180479",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/180480",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/180481",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/180483",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/180484",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/181707",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/181708",
          "subject": "",
          "ext": ".jpg"
        }
      ],
      "files": [],
      "links": []
    },
    {
      "id": 2576,
      "name": "國立臺灣博物館_鐵道部園區",
      "name_zh": null,
      "open_status": 1,
      "introduction": "國定古蹟臺灣總督府鐵道部位於忠孝西路、塔城街、鄭州路、延平北路間，屬國營臺灣鐵路股份有限公司管轄。1885年臺灣巡撫劉銘傳聘請英國與德國顧問，於淡水河碼頭內建造機器局，組裝槍砲彈藥及鑄造貨幣，內有鎔鐵所、鍛工場等。1895年日本軍方接收機器局，改為臺北兵器修理所，製造並修理陸軍所管兵器，可製造彈丸、信管、小銃丸及藥筴、船舶、鐵道鐵橋等，之後改稱為臺灣砲兵工廠，1900年由陸軍省移交鐵道部，同年改為臺北工場。1908年臺灣西部縱貫鐵路開通，鐵路交通運量與車輛維修需求大增，1909年臺北工場向東擴張，新建車輛修理工場、塗工場。1915年起拆除基地南邊原有建物，1918年新建鐵道部廳舍，與臺北工場並存，成為南廳舍、北工廠配置，直到1934年臺北工場搬遷至松山(今國定古蹟臺北機廠)為止，街廓內原有將近四十棟建物，歷經2005年興建捷運以及2013古蹟修復拆除不保存者，目前尚餘十棟，其中包含八處法定文化資產：鐵道部、食堂、八角樓男廁、電源室、工務室、戰時指揮中心、清代機器局遺構以及臺北工場(不在鐵道部園區範圍內)。\r\n\r\n戰後鐵道部改為臺灣鐵路管理局。因都市計畫劃道路塔城街於1967年開通，切斷與西側鐵道部官舍群連結形成今日所見的街廓。1992年臺北市政府指定鐵道部廳舍為三級古蹟，1993年臺鐵總局遷移至臺北車站新廈。2005年文建會委託東海大學、中原大學進行古蹟調查研究及再利用規劃，2006年交通部與臺灣鐵路管理局，以及文建會與國立臺灣博物館簽訂臺灣博物館系統聯盟協議書，鐵道部古蹟修復再利用工作正式開始，以「鐵道部博物館園區」為基礎概念，目標為建置現代性展示主題之複合使用園區，並整合周邊都市及歷史涵構。2007年文建會指定為國定古蹟，並納入八角樓、食堂、電源室、工務室、戰時指揮中心為古蹟範圍，臺北工場及清代機器局遺址則分別由臺北市於2008年及2010年指定為市定古蹟，2009年起由臺博館代管此區土地建物。規劃中的鐵道博物館展示主題將主要架構在古蹟與基地、鐵道文化及現代性等相關議題之上，未來也將涉及整合西側原機器局工廠範圍內E1E2街廓。\r\n\r\n(資料來源：國立臺灣博物館)",
      "open_time": "",
      "zipcode": "103",
      "distric": "大同區",
      "address": "103 臺北市大同區延平北路一段2號",
      "tel": "+886-2-25589790",
      "fax": "",
      "email": "",
      "months": "01,07,02,08,03,09,04,10,05,11,06,12",
      "nlat": 25.04868,
      "elong": 121.5113,
      "official_site": "https://www.ntm.gov.tw/submenu_373.html",
      "facebook": "https://www.facebook.com/NTMuseum",
      "ticket": "",
      "remind": "1.定時導覽服務：週二至週日 10:30、14:30\r\n鐵道部園區導覽主題：認識鐵道部\r\n2.預約團體導覽服務時段：週二至週五，每場40～60分鐘 （國定假日及連假恕不開放團體預約導覽）\r\n請於導覽14天前至鐵道部團體預約導覽服務網站申請",
      "staytime": "",
      "modified": "2024-01-15 16:52:29 +08:00",
      "url": "https://www.travel.taipei/zh-tw/attraction/details/2576",
      "category": [
        {
          "id": 13,
          "name": "歷史建築"
        },
        {
          "id": 15,
          "name": "藝文館所"
        },
        {
          "id": 19,
          "name": "親子共遊"
        }
      ],
      "target": [
        {
          "id": 61,
          "name": "親子共學"
        },
        {
          "id": 62,
          "name": "校外教學"
        }
      ],
      "service": [],
      "friendly": [],
      "images": [
        {
          "src": "https://www.travel.taipei/image/207889",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207890",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207891",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207892",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207893",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207894",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207895",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207896",
          "subject": "",
          "ext": ".jpg"
        },
        {
          "src": "https://www.travel.taipei/image/207888",
          "subject": "",
          "ext": ".jpg"
        }
      ],
      "files": [],
      "links": [
        {
          "src": "https://www.ntm.gov.tw/content_374.html",
          "subject": "開放時間與票價"
        }
      ]
  }]
  }
    """.trimIndent()

    private fun genTestData():Attractions? {
        val moshi = Moshi.Builder().build()
        //val type = Types.newParameterizedType(Attractions::class.java)
        val adapter = moshi.adapter(Attractions::class.java)
        return adapter.fromJson(testStr)
    }

    private fun defaultLang():String {
        var deviceLang = Locale.getDefault().language
        if(deviceLang=="zh") {
            deviceLang = deviceLang + "-" + Locale.getDefault().country
            deviceLang = deviceLang.lowercase(Locale.ROOT)
        }
        //toast(deviceLang.lowercase(Locale.ROOT))
        return deviceLang;
    }

    init {
        userPickLanguage = defaultLang()
        userLang.value = userPickLanguage
    }

    fun getAttract(lang: String = userPickLanguage) {
        viewModelScope.launch {
            try {
                val response = apiClient.getAttractionsAll(lang, 1)
                //Log.e("", "art attract :" + response.body())
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    //Attractions(0, emptyList())
                    genTestData()
                }
                attractData.value = note?.data
                //Log.e("","art attract size:" + attractData.value?.size)
            } catch (e: Exception) {
                Log.e("","art attract err:" + e.message)
            }
        }
    }

    fun getNews(lang: String = userPickLanguage) {
        viewModelScope.launch {
            val response = apiClient.getEventNews(lang, 1)
            val note = if (response.isSuccessful) {
                response.body()
            } else {
                News(0, emptyList())
            } as News
            newsData.value = note.data
        }
    }

}