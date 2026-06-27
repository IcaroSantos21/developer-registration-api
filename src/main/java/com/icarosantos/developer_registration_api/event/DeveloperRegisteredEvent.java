package com.icarosantos.developer_registration_api.event;

import com.icarosantos.developer_registration_api.model.TypeContract;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeveloperRegisteredEvent {
    private String fullName;
    private String enterprise;
    private TypeContract typeContract;
}
