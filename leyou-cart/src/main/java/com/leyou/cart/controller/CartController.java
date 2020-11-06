package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * description:
 *
 * @author xuqiangsheng
 * @date 2020/11/6 9:35
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @author XuQiangsheng
     * @date 2020/11/6 9:38
     * @param cart
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
    */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }
}
