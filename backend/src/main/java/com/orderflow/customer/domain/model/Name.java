package com.orderflow.customer.domain.model;

import java.util.Objects;

public final class Name {
    private final String value;


    public Name(String value) {
        Objects.requireNonNull(value, "Name não pode ser nulo");

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Name não pode ser vazio");
        }

        if (normalized.length() < 2) {
            throw new IllegalArgumentException("Name deve ter pelo menos 2 caracteres");
        }

        if (normalized.length() > 150) {
            throw new IllegalArgumentException("Name deve ter no máximo 150 caracteres");
        }

        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name name)) return false;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
