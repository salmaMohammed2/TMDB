package com.example.mymovieapp.data.remote.api

import com.example.mymovieapp.data.remote.Endpoints.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request
        val originalRequest = chain.request()

        // Build the new request with the API key added as a query parameter
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language","en-US")
            .build()

        // Create a new request with the modified URL
        val newRequest = originalRequest.newBuilder().url(url).build()

        // Proceed with the new request
        return chain.proceed(newRequest)
    }
}