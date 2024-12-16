package com.example.delivery.application.dto;

import java.util.List;

public record DirectionsResponseDto(
	Route route
) {
	public record Route(
		List<RouteSegment> routeSegmentList,
		double distance,
		int duration
	){
		public record RouteSegment(
			String summary,
			List<Point> path
		) {
			public record Point(double longitude, double latitude) {}
		}
	}
}
