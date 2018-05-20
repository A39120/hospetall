import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log

class MenuFragment : FragmentActivity() {
    companion object {
        val TAG = "MenuFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "OnCreate called;")



    }

}