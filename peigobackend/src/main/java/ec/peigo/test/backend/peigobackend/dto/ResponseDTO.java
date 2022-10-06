package ec.peigo.test.backend.peigobackend.dto;

import ec.peigo.test.backend.peigobackend.annotations.Generated;

@Generated
public record ResponseDTO<T>(boolean success, T data) {
}
