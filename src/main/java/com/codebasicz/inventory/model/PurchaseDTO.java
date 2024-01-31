package com.codebasicz.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer supplierId;
    private String supplierContactName;
    private Integer quantity;
    private Date purchasedDate;
}
