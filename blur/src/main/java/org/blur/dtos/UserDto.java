package org.blur.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.RoleEnums;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDto {
    Integer userId;
    String username;
    String firstName;
    String lastName;
    String imageUrl;
    String email;
    RoleEnums role;
}
