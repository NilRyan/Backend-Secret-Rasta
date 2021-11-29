package com.rastatech.secretrasta.dto;

import com.rastatech.secretrasta.model.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class WishRequest {

    @NotEmpty
    private String wishName;

    @NotEmpty
    private String imageUrl;

    @NotEmpty
    private String description;

    @NotEmpty
    private String reasonForWish;

    @NotEmpty
    @Pattern(regexp = "[0-9]+]")
    private int rastagemsRequired;
}
