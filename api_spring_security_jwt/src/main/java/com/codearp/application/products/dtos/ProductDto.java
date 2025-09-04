package com.codearp.application.products.dtos;

import com.codearp.application.commons.deserializes.BigDecimalDeserializer;
import com.codearp.application.commons.dtos.AuditableDto;
import com.codearp.application.commons.validators.IsExistsDb;
import com.codearp.application.commons.validators.IsRequired;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductDto extends AuditableDto {

    private Long id;

    //@NotBlank
    @IsRequired
    @Size(min=3,max=20)
    private String name;

    @NotNull @PositiveOrZero
    @JsonDeserialize(using = BigDecimalDeserializer.class ) // Si se recibe un Json en lugar de un application/x-www-form-urlencoded, en ese caso usar BidngiResutl
    private BigDecimal price;

    @NotNull(message = "{NotNull.productDto.releaseAt}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseAt;

    @NotBlank
    private String description;

    @IsRequired
    @IsExistsDb(fieldName = "sku")
    private String sku;

    @Version
    private Long version;
}
