package com.zsp.storeapp.net;

import com.zsp.storeapp.bean.CarBean;
import com.zsp.storeapp.bean.UserBean;
import com.zsp.storeapp.entity.BannerResult;
import com.zsp.storeapp.entity.BaseEntity;
import com.zsp.storeapp.entity.CategoryEntity;
import com.zsp.storeapp.entity.ListResultEntity;
import com.zsp.storeapp.entity.ProductEntity;
import com.zsp.storeapp.entity.StoreUserEntity;
import com.zsp.storeapp.entity.TokenEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * description:
 * author:created by Andy on 2020/12/1 0001 16:17
 * email:zsp872126510@gmail.com
 */
public interface ApiService {
    //    public static final String SERVER_ADDRESS = "http://127.0.0.1:8080/";
//    public static final String SERVER_ADDRESS = "http://10.0.2.2:8080"; //模拟器访问本地服务器地址
    public static final String SERVER_ADDRESS = "http://192.168.0.24:8081/zsp/"; //手机访问本地服务器地址（本机电脑IP）
//    public static final String SERVER_ADDRESS = "http://外网IP:8080/"; //外网

    int DEFAULT_TIMEOUT = 10 * 1000;

    @GET("banner/getBanner")
    Observable<BaseEntity<ListResultEntity<BannerResult>>> getBanner();

    @GET("product/getProductList")
    Observable<BaseEntity<ListResultEntity<ProductEntity>>> getProductList(@Query("pageSize") int pageSize, @Query("pageNo") int pageNo);

    @GET("product/getProductListById/{categoryId}")
    Observable<BaseEntity<ListResultEntity<ProductEntity>>> getProductListById(@Path(value = "categoryId") int categoryId);

    @GET("category/getCategoryList")
    Observable<BaseEntity<ListResultEntity<CategoryEntity>>> getCategoryList();

    @POST("login/getToken")
    Observable<BaseEntity<String>> getToken(@Body UserBean user);

    @POST("login/getUserInfo")
    Observable<BaseEntity<StoreUserEntity>> getUserInfo(@Body UserBean user);

    @POST("login/register")
    Observable<BaseEntity<Object>> register(@Body UserBean user);

    @POST("car/addProduct")
    Observable<BaseEntity<Object>> addProduct(@Body CarBean car);

    @GET("car/getCartList/{userId}")
    Observable<BaseEntity<ListResultEntity<ProductEntity>>> getCartList(@Path(value  ="userId") int userId);
}
