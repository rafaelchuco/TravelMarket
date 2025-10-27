package com.example.travelmarket.core.utils

import com.example.travelmarket.core.network.NetworkResult
import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.toNetworkResult(): NetworkResult<T> {
    return try {
        if (this.isSuccessful) {
            val body = this.body()
            if (body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error("Response body is null", this.code())
            }
        } else {
            // ✅ Extraer mensaje específico del errorBody
            val errorMessage = parseErrorMessage(this)
            NetworkResult.Error(errorMessage, this.code())
        }
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Exception occurred")
    }
}

private fun <T> parseErrorMessage(response: Response<T>): String {
    return try {
        val errorBody = response.errorBody()?.string()

        android.util.Log.e("PARSE_ERROR", "===== Error Body =====")
        android.util.Log.e("PARSE_ERROR", "Raw errorBody: $errorBody")

        if (!errorBody.isNullOrEmpty()) {
            val jsonObject = JSONObject(errorBody)

            android.util.Log.e("PARSE_ERROR", "JSON parsed: $jsonObject")

            when {
                // ✅ Español: "mensaje" + "errores"
                jsonObject.has("mensaje") && !jsonObject.isNull("mensaje") -> {
                    val mensaje = jsonObject.getString("mensaje")

                    // Si hay errores específicos, extraer el primero
                    if (jsonObject.has("errores") && !jsonObject.isNull("errores")) {
                        val erroresObj = jsonObject.getJSONObject("errores")
                        val primerError = extractFirstError(erroresObj)
                        if (primerError != null) {
                            android.util.Log.e("PARSE_ERROR", "Usando primer error de 'errores': $primerError")
                            return primerError
                        }
                    }

                    android.util.Log.e("PARSE_ERROR", "Usando 'mensaje': $mensaje")
                    mensaje
                }

                // ✅ Inglés: "message"
                jsonObject.has("message") && !jsonObject.isNull("message") -> {
                    val msg = jsonObject.getString("message")
                    android.util.Log.e("PARSE_ERROR", "Usando 'message': $msg")
                    msg
                }

                // ✅ Español/Inglés: "error"
                jsonObject.has("error") && !jsonObject.isNull("error") -> {
                    val error = jsonObject.get("error")
                    val msg = if (error is String) error else error.toString()
                    android.util.Log.e("PARSE_ERROR", "Usando 'error': $msg")
                    msg
                }

                // ✅ Inglés: "detail"
                jsonObject.has("detail") && !jsonObject.isNull("detail") -> {
                    val msg = jsonObject.getString("detail")
                    android.util.Log.e("PARSE_ERROR", "Usando 'detail': $msg")
                    msg
                }

                else -> {
                    android.util.Log.e("PARSE_ERROR", "No encontró campos conocidos, usando message()")
                    response.message() ?: "Error desconocido"
                }
            }
        } else {
            android.util.Log.e("PARSE_ERROR", "ErrorBody es null o vacío")
            response.message() ?: "Error desconocido"
        }
    } catch (e: Exception) {
        android.util.Log.e("PARSE_ERROR", "Exception parseando: ${e.message}")
        e.printStackTrace()
        response.message() ?: "Error del servidor"
    }
}

/**
 * Extrae el primer error del objeto "errores"
 * Maneja tanto arrays como strings
 */
private fun extractFirstError(erroresObj: JSONObject): String? {
    return try {
        val keys = erroresObj.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = erroresObj.get(key)

            // Si es un JSONArray, tomar el primer elemento
            if (value is org.json.JSONArray && value.length() > 0) {
                return value.getString(0)
            }

            // Si es un string, devolverlo directamente
            if (value is String) {
                return value
            }
        }
        null
    } catch (e: Exception) {
        android.util.Log.e("PARSE_ERROR", "Error extrayendo primer error: ${e.message}")
        null
    }
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = apiCall.invoke()
        response.toNetworkResult()
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Network error occurred")
    }
}
