package com.example.agroapp.app;

import com.example.agroapp.repository.AgroRepository;
import com.example.agroapp.repository.source.HttpDataSource;
import com.example.agroapp.repository.source.LocalDataSource;
import com.example.agroapp.http.HttpDataSourceImpl;
import com.example.agroapp.http.RetrofitClient;
import com.example.agroapp.http.service.DemoApiService;
import com.example.agroapp.repository.source.local.LocalDataSourceImpl;


/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。
 * Created by goldze on 2019/3/26.
 */
public class Injection {
    public static AgroRepository provideDemoRepository() {
        //网络API服务
        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return AgroRepository.getInstance(httpDataSource, localDataSource);
    }



}
