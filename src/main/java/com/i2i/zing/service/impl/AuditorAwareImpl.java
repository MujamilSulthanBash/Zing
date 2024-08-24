package com.i2i.zing.service.impl;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        return Optional.of("Admin");
    }
}
