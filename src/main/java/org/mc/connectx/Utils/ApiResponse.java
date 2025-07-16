package org.mc.connectx.Utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    public String message;
    public boolean status;
}
