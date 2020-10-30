package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xuqiangsheng
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}