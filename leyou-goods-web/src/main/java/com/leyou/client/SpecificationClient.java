package com.leyou.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xuqiangsheng
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}