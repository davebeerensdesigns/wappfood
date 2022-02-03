package com.wappstars.wappfood.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItemInputDto extends RepresentationModel<LineItemInputDto> {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;
}
