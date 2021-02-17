import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (

    var longi : String = "Defaultvalue" ,
    var lati: String = "Defaultvalue" ,
    var Name : String = "Defaultvalue",
    var Destination :String = "Defaultvalue"

): Parcelable


