package io.eblock.eos4j.api.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.eblock.eos4j.api.exception.ApiError;
import io.eblock.eos4j.api.exception.ApiException;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Generator {

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

	private static Retrofit.Builder builder = new Retrofit.Builder()
			.addConverterFactory(JacksonConverterFactory.create());

	private static Retrofit retrofit;

	public static <S> S createService(Class<S> serviceClass, String baseUrl) {
		builder.baseUrl(baseUrl);
		builder.client(httpClient.build());
		builder.addConverterFactory(JacksonConverterFactory.create());
		retrofit = builder.build();
		return retrofit.create(serviceClass);
	}

	public static <T> T executeSync(Call<T> call) {
		try {
			Response<T> response = call.execute();
			if (response.isSuccessful()) {
				return response.body();
			} else {
				ApiError apiError = getApiError(response);
                System.out.println("msg: " + apiError.getMessage());
                System.out.println("code: " + apiError.getCode());
                System.out.println("err code: " + apiError.getError().getCode());
                System.out.println("err name: " + apiError.getError().getName());
                System.out.println("err what: " + apiError.getError().getWhat());
                if (apiError.getError().getDetails().length > 0)
                {
                    System.out.println("err detail: " + apiError.getError().getDetails()[0].getMessage());
                }
				throw new ApiException(apiError);
			}
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	private static ApiError getApiError(Response<?> response) throws IOException, ApiException {
		return (ApiError) retrofit.responseBodyConverter(ApiError.class, new Annotation[0]).convert(response.errorBody());
	}
}
