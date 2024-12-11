package com.example.delivery.domain.model.vo;

import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DistanceAndDuration {

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private int duration;

    public static DistanceAndDuration of(double distance, int duration) {
        validateDistance(distance);
        validateDuration(duration);

        return new DistanceAndDuration(distance, duration);
    }

    @Override
    public String toString() {
        return "Distance : " + distance + "km,  Duration : " + duration + "mins";
    }

    private static void validateDuration(int duration) {
        if (duration < 0) {
            throw new CustomException(ErrorCode.INVALID_DURATION_NOT_BELOW_ZERO);
        }
    }

    private static void validateDistance(double distance) {
        if (distance < 0) {
            throw new CustomException(ErrorCode.INVALID_DISTANCE_NOT_BELOW_ZERO);
        }
    }

}
