package com.xspace.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
	Product product;
	int quantity;
}
