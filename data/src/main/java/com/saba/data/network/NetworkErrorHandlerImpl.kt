package com.saba.data.network
import com.saba.base_android.network.NetworkErrorData
import com.saba.base_android.network.NetworkErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkErrorHandlerImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
) : NetworkErrorHandler {
    private val _event = MutableSharedFlow<NetworkErrorData>()
    override val event: SharedFlow<NetworkErrorData> = _event

    override fun emitEvent(networkErrorData: NetworkErrorData) {
        coroutineScope.launch(Dispatchers.IO) {
            _event.emit(networkErrorData)
        }
    }
}