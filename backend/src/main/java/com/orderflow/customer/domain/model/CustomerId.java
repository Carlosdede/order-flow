package com.orderflow.customer.domain.model;

import java.util.UUID;
import java.util.Objects;

public final class CustomerId {
    private final UUID value;

    public CustomerId(final UUID value) {
        this.value = Objects.requireNonNull(value, "CustomerId não pode ser nulo");
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId from(String value) {
        return new CustomerId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerId that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
