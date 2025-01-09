package com.saba.data.network

import com.saba.base_android.network.NetworkErrorData
import com.saba.base_android.network.NetworkErrorHandler
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class     AppErrorHandlerInterceptor @Inject constructor(
    private val networkErrorHandler: NetworkErrorHandler,
    private val sharedPreferences: PreferencesStorageImpl,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val networkData = when (response.code) {
                in 200..299 -> {
                    val body = response.body
                    urlCodes.entries.firstOrNull {
                        response.request.url.toString().contains(it.key)
                    }?.let {
                        NetworkErrorData(
                            text = "200..299",
                            message = it.value,
                            isError = false
                        )
                    }
                }
                401 -> {
                    sharedPreferences.signOut()
                    NetworkErrorData(
                        text = "",
                        internalText = 0,
                        message = "خروج",
                        isError = true,
                        isSignOut = true
                    )
                }

                422 -> {
                    val res = JSONObject(response.body!!.string())
                    if (res["status"] == "VALIDATION_ERROR") {
                        val errorArrays = res.getJSONObject("errors")
                        var message = ""
                        for (key in errorArrays.keys()) {
                            message += errorArrays.getJSONArray(key)[0]
                            break
                        }
                        NetworkErrorData(
                            text = "",
                            internalText = 0,
                            message = message,
                            isError = true
                        )
                    } else {
                        NetworkErrorData(
                            text = "422",
                            isError = true
                        )
                    }
                }

                in 400..600 -> {
                    try {
                        val res = JSONObject(response.body!!.string())
                        NetworkErrorData(
                            text = res["msg"] as String,
                            isError = true
                        )
                    }catch (e:Exception){
                        NetworkErrorData(
                            text = "مشکلی رخ داده است",
                            isError = true
                        )
                    }

                }

                else -> NetworkErrorData(
                    text = "مشکلی رخ داده است",
                    isError = true
                )
            }
            networkData?.let {
                networkErrorHandler.emitEvent(it)
            }
            return response
        } catch (e: UnknownHostException) {
            networkErrorHandler.emitEvent(
                NetworkErrorData(
                    text = "لطفاً اتصال اینترنت خود را بررسی کنید.",
                    isError = true
                )
            )
        } catch (e: IllegalStateException) {
            networkErrorHandler.emitEvent(
                NetworkErrorData(
                    text = "لطفاً بعداً دوباره تلاش کنید.",
                    isError = true
                )
            )
        } catch (e: JSONException) {
            networkErrorHandler.emitEvent(
                NetworkErrorData(
                    text = e.localizedMessage?:e.message?:"",
                    isError = true
                )
            )
        }
        catch (e: SocketTimeoutException) {
            // Handle timeout exception and print HHHHHHHHHHHH
            networkErrorHandler.emitEvent(
                NetworkErrorData(
                    text = "اتصال برقرار نشد",
                    isError = true
                )
            )
        }
        catch (e: ConnectException) {
            networkErrorHandler.emitEvent(
                NetworkErrorData(
                    text = "اتصال به سرور ناموفق بود",
                    isError = true
                )
            )
        }
        return chain.proceed(chain.request())
    }
}
