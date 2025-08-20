package com.rodrigo.login.contract.user.response;

import java.util.List;

public record GetAllUsersResponse(
        List<UserResponse> users
) {
}
