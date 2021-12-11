package com.rastatech.secretrasta.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WishPageResponse {

    private Long wishId;
    private String wishName;
    private String imageUrl;
    private String description;
    private int rastagemsRequired;
    private int rastagemsDonated;
    private boolean isLiked;
    private int upvotes;
    private int downvotes;
}
