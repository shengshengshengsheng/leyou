package com.leyou.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xuqiangsheng
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}