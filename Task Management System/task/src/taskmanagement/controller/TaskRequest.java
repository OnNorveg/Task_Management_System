package taskmanagement.controller;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(@NotBlank String title, @NotBlank String description) {
}
