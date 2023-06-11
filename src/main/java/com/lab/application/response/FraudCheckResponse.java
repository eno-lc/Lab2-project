package com.lab.application.response;

import lombok.Builder;

@Builder
public record FraudCheckResponse(Boolean isFraudster) {
}
