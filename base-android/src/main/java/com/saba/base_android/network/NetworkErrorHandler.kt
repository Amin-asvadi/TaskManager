package com.saba.base_android.network
import com.saba.base_android.R
import com.saba.base_android.uiles.Constant
import kotlinx.coroutines.flow.SharedFlow

data class NetworkErrorData(
    val text: String ="",
    val internalText:Int = R.string.internal_server_error,
    val isError: Boolean,
    val isWarning: Boolean = false,
    val message: String? = null,
    val isSignOut:Boolean = false,
    val errorMode: Constant.ERROR_MODE = Constant.ERROR_MODE.SERVER_ERROR,
    val bottomSheetError:Boolean =false
)

interface NetworkErrorHandler {
    val event: SharedFlow<NetworkErrorData>
    fun emitEvent(networkErrorData: NetworkErrorData)
}