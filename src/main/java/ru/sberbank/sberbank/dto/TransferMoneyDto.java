package ru.sberbank.sberbank.dto;

import lombok.Data;
@Data
public class TransferMoneyDto {
    private Integer sourceAccountId;
    private Integer targetAccountId;
    private Double  quantity;
}
