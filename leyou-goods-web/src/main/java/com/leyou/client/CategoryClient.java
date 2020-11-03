package com.leyou.client;
import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;
/**
 * @author xuqiangsheng
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}